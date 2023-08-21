package Lab_1;

import java.util.Objects;

public class ListString {

    private StringItem head; // объявляем блок вложенного списка

    public ListString() {
        this.head = new StringItem(); // инициализируем первый блок вложенного списка
    }

    // добавляем в конец текущего экземпляра ListString строку
    public void append(String string) {
        char[] chars = string.toCharArray(); // разбиваем строку на символьный массив
        StringItem tail = findLast(this.head); // находим хвост
        fillForwards(tail, chars); // находим в хвосте пустую позицию и с нее начинаем заполнять блок
    }

    // Добавляем в конец текущего экземпляра, новый экземпляр ListString
    public void append(ListString string) {
        StringItem tail = findLast(this.head); // находим хвост
        tail.setNext(string.copyList().getHead()); // цепляем к хвосту голову string
    }

    // добавляем один символ в конец последнего блока экземпляра ListString
    public void append(char ch) {
        StringItem tail = findLast(this.head); // находим хвост

        if (tail.getSize() == tail.getMaxSize()) { // если блок заполнен
            tail.setNext(new StringItem());        // создаем новый
            tail = tail.getNext();                 // смещаем указатель вперед
            tail.setElementChars(tail.getSize(), ch); // втыкаем символ на нулевой индекс.

        } else {
            tail.setElementChars((tail.getSize()), ch);// втыкаем символ на нулевой индекс.
        }
    }


    // метод возвращает символ с указанного индекса
    public char charAt(int index) {
        checkCorrectIndex(index);
        // формируем массив dataCurrentBlock с данными:
        //[0] - нужный нам индекс в найденном блоке
        //[1] - блок в котором находиться необходимый индекс
        //[2] - блок стоящий перед блоком [1]
        Object[] dataCurrentBlock = findBlockAtIndex(index, this.getHead());
        StringItem tail = (StringItem) dataCurrentBlock[1];
        int currentPosInBlock = (int) dataCurrentBlock[0];

        return tail.getSymbols()[currentPosInBlock]; // возвращаем символ
    }

    // Тоже самое, что и charAt, только идет замена
    // index - указатель на индекс
    // ch - символ, что должен встать на требуемый индекс
    public void setCharAt(int index, char ch) {
        checkCorrectIndex(index);


        Object[] dataCurrentBlock = findBlockAtIndex(index, this.getHead());
        StringItem tail = (StringItem) dataCurrentBlock[1];
        int currentPosInBlock = (int) dataCurrentBlock[0];

        tail.getSymbols()[currentPosInBlock] = ch; // присваиваем новое значение
    }

    // Вставка экземпляра ListString на указанный индекс
    // index - указатель на индекс
    // string - экземпляра ListString для вставки
    public void insert(int index, ListString string) {
        // convertIdx на тот случай, если вставка будет с начала строки
        // Общая проверка выдаст проброс исключения (-1 индекс)
        // Помогаем нулевому значению на этапе проверки не стать отрицательным.

        int convertIdx = index == 0 ? 1 : index;
        // отнимаю единицу, так как нужно, чтобы вставка была возможна в конец
        // input="1233"  - >insert(4,"вставка") -> result = 1233вставка
        // макс. индекс = 3, а вносим мы 4, что выходи за пределы
        checkCorrectIndex(convertIdx - 1);

        ListString copiedList = string.copyList(); // копируем экз. ListString

        // если вставка в начало
        if (index == 0) {
            StringItem tailString = findLast(copiedList.getHead()); // ищем хвост string
            tailString.setNext(this.head); // цепляю к хвосту голову
            this.head = copiedList.getHead(); // обновляем голову
            return;
        }
        // если вставка в конец
        if (index == length()) {
            StringItem tail = findLast(this.getHead()); // ищем хвост this
            tail.setNext(copiedList.getHead()); // цепляем к хвосту голову string
            return;
        }

        StringItem start; // блок скорее всего будет располовинен. Это первая часть его.
        StringItem end = new StringItem(); // вторая часть дробленого блока
        byte countForEnd = 0; // инкримент для заполнения end-блока

        Object[] dataCurrentBlock = findBlockAtIndex(index, this.getHead()); //
        start = (StringItem) dataCurrentBlock[1];         // блок в котором будет происходить разбиение
        int currentPosInBlock = (int) dataCurrentBlock[0]; // позиция для разбиения

        // если индекс находиться на стыке
        // если индекс = 0, то значит вставка попала на стык
        // нам пригодиться dataCurrentBlock[2] - в нем лежит предыдущий блок
        if (currentPosInBlock == 0) {
            StringItem beforeTail = (StringItem) dataCurrentBlock[2]; // берем предыдущий блок
            beforeTail.setNext(copiedList.getHead()); // цепляем к нему голову string
            StringItem stringTail = findLast(copiedList.getHead()); // находим хвост string
            stringTail.setNext(start); // целпяем к хвосту наш найденный блок (start)
            return;
        }
        // заполняем новый end второй половиной значений из исходного блока start
        for (int i = currentPosInBlock; i < start.getSize(); i++) {
            end.setElementChars(countForEnd++, start.getSymbols()[i]);
        }
        // обнулить элементы в массиве символов мы не можем, поэтому создаем новый массив
        // и заполняем его символами из start, до позиции currentPosInBlock
        char[] charsForStart = new char[start.getMaxSize()];
        start.setSize((byte) 0);
        for (int i = 0; i < currentPosInBlock; i++) {
            charsForStart[i] = start.getSymbols()[i]; // назначем новое значение
            start.setSize((byte) (start.getSize() + 1)); // увеличиваем емкость
        }
        start.setSymbols(charsForStart); // заменяем симв. массив на новый

        end.setNext(start.getNext()); // цепляем к второй половине разбитого блока следующий блок из start
        StringItem tailInsertList = findLast(copiedList.getHead()); // находим хвост string
        tailInsertList.setNext(end); // цепляем к нему end
        start.setNext(copiedList.getHead()); // к первой половине цепляем голову string
    }

    // аналогичный метод, строку переводим в ListString, а после передаем его в одноименный метод insert
    // index - указатель на индекс
    // string - экземпля String для вставки
    public void insert(int index, String string) {
        checkCorrectIndex(index);  // проверка на вхождение индекса
        ListString convertString = new ListString();
        convertString.append(string);
        this.insert(index, convertString);
    }

    // берем подстроку из ListString
    // start - начальный индекс
    // end - конечный индекс, в результирующую строку он не включен
    public ListString substring(int start, int end) {
        // проверяем не только start, но и возможность некорректного end (к примеру если он меньше start)
        try {
            checkCorrectIndex(start);
            checkIndex(start, end);
        } catch (IndexException e) {
            return new ListString();
        }

        if (end > length()) end = length(); // обновляем end, если он вышел за пределы диапазона

        int sumChars = end - start; // находим количество нужным символов
        ListString resultStringItem = new ListString();

        Object[] dataCurrentBlock = findBlockAtIndex(start, this.getHead());
        StringItem tail = (StringItem) dataCurrentBlock[1];
        int currentPosInBlock = (int) dataCurrentBlock[0];  // начальный индекс с которого будут браться значения

        // заполняем пустой блок символами в количестве sumChars
        while (sumChars > 0) {
            // проверка индекса на вхождение в диапазон индексов текущего массива блока
            if (isValidIndex(tail.getSymbols(), currentPosInBlock)) {
                // добавляем символ с текущего блока, увеличиваем currentPosInBlock на 1
                resultStringItem.append(tail.getSymbols()[currentPosInBlock++]);
                sumChars--;  // уменьшаем кол-во
            } else {
                tail = tail.getNext(); // создаем новый блок
                currentPosInBlock = 0; // обнуляем текущий указатель на индекс
            }
        }
        return resultStringItem;
    }


    // поиск последнего блока
    // head - начало связанного списка
    private StringItem findLast(StringItem head) {
        StringItem tail = head;  // создаю хвост
        while (tail.getNext() != null) {  // иду пока конец не достигнут
            tail = tail.getNext();
        }
        return tail;
    }

    // Заполняем блоки символами. Когда блок заполняется, а символы еще есть, создаем следующий блок.
    // tail - блок с которого начинаем осуществлять вставку
    // chars - массив для последовательной вставки в tail и дальше с созданием следующих блоков при необходимости
    private void fillForwards(StringItem tail, char[] chars) {
        // идем по массиву символов
        for (int i = 0; i < chars.length; i++) {
            if (tail.getSize() < tail.getMaxSize()) {     // если объем не заполнен полностью
                tail.setElementChars((tail.getSize()), chars[i]);   // вставляем в пустое место символ

            } else {
                tail.setNext(new StringItem()); // в хвосте создаем новый блок для поля next
                tail = tail.getNext();         // обновляем хвост
                tail.setElementChars((tail.getSize()), chars[i]); // вставляем символ на нулевой индекс
            }
        }
    }

    public StringItem getHead() {
        return head;
    }

    // создаем глубокую копию текущего экземпляра ListString
    public ListString copyList() {
        ListString copyList = new ListString();
        StringItem tailCopy = copyList.getHead();
        StringItem tail = this.head;

        if (tail == null) { // отрабатываем null
            return copyList;
        }

        while (tail.getSize() > 0 || tail.getNext() != null) {  // пока в текущем блоке есть хоть один элемент или есть ссылка на следующий блок
            for (byte i = 0; i < tail.getMaxSize(); i++) {
                if (tail.getSymbols()[i] == 0) { // если наткнулись на пустой символ, завершаем цикл
                    break;
                } else tailCopy.setElementChars(i, tail.getSymbols()[i]); // копируем элемент
            }

            if (tail.getNext() == null) { // если следующий null заканчиваем цикл
                break;
            } else {                      // иначе создаем новый блок
                tailCopy.setNext(new StringItem());
                tailCopy = tailCopy.getNext();
                tail = tail.getNext();
            }
        }
        return copyList;
    }

    // Находим тот блок, в котором хранится требуемый индекс
    // index - желаемый указатель на индекс строки
    // head - блок, с которого начинается поиск
    private Object[] findBlockAtIndex(int index, StringItem head) {
        StringItem tail = head;                  // создаем хвост
        StringItem beforeTail = new StringItem(); // создаем экземпляр для блока, что перед хвостом.
        Object[] result = new Object[3];          // создаем массив где будем хранить нужные нам данные
        int sumLengths = 0;                       // накапливаем сумму пройденных блоков
        // вычитаем из этой переменной ёмоксти пройденных блоков
        // в итоге получается значение индекса в конкретном найденном блоке, он и есть искомый
        int currentIndex = index;

        for (; ; ) {
            sumLengths += tail.getSize();
            if (sumLengths <= index) {
                currentIndex -= tail.getSize();
                beforeTail = tail;
                tail = tail.getNext();
            } else {
                result[0] = currentIndex; // нашли искомый индекс для найденного блока
                result[1] = tail;      // занесли найденный блок
                result[2] = beforeTail; // занесли его предыдущий блок
                break;
            }
        }
        return result;
    }

    // вспомогательный метод
    // Начальный блок делаем пустым
    public void clear() {
        this.head = new StringItem();
    }

    public void setHead(StringItem head) {
        this.head = head;
    }

    // так как нельзя хранить длинну символов возникла необходимость в создании метода, который
    // будет вынужден каждый раз при обращении проходиться по всему списку для вычисления
    // кол-ва символов и количества блоков (нужно для оптимизации)
    // Создал его для того, чтобы к нему могли обращаться методы toString() и length()
    //
    // sumSymbolsAndBlocks[0] = кол-во символов
    // sumSymbolsAndBlocks[1] = кол-во блоков в объекте;
    private int[] sumSymbols() {
        int lenght = 0; // накопитель длинны
        int counter = 0; // инкремент для подсчета кол-ва блоков
        int[] sumSymbolsAndBlocks = new int[2];

        StringItem tail = this.head; // создаем хвост

        while (tail.getNext() != null) {
            lenght += tail.getSize();
            counter++;
            tail = tail.getNext();
        }
        // добавляем остаток, что остался за пределами блока while
        lenght += tail.getSize();

        sumSymbolsAndBlocks[0] = lenght;
        sumSymbolsAndBlocks[1] = counter;

        return sumSymbolsAndBlocks;

    }

    // подсчет длинны строки
    public int length() {
        // инициализируем массив где храняться кол-во символов и кол-во объектов
        int[] statistic = sumSymbols();

        // оптимизация
        // если кол-во блоков больше одного и общая длинна меньше SIZE
        if (statistic[1] > 1 && statistic[0] <= StringItem.SIZE) {
            String tempStr = this.toString();  // конвертируем this в строку
            this.setHead(new StringItem());    // обнуляем голову
            this.append(tempStr);              // формируем новый связный список
        }

        return statistic[0];
    }

    private void checkCorrectIndex(int index) {
        if (length() < index + 1 || index < 0) {
            throw new IndexException(
                    "Ошибка по индексу! \n" +
                            "Операция над " + index + " индексом\n" +
                            "Крайний индекс " + (length() - 1));
        }
    }

    // start - начальный индекс
    // end - конечный индекс, в результирующую строку он не включен
    private void checkIndex(int start, int end) throws IndexException {
        if (start >= end)
            throw new IndexException(
                    "Ошибка диапазону вводимых значений \n" +
                            "Начальный индекс " + start + "\n" +
                            "Конечный индекс " + end + "\nПравило: от большего к меньшему");
    }

    // проверка, не выходит ли индекс за пределы указанного массива
    private boolean isValidIndex(char[] arr, int index) {
        try {
            Objects.checkIndex(index, arr.length);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        StringItem tail = this.head; // создаем хвост
        // инициализируем массив где храняться кол-во символов и кол-во объектов
        // сейчас нам нужно только кол-во символов
        int[] statistic = sumSymbols();
        int length = statistic[0];

        char[] result = new char[length];  // создаем массив символов, куда будем заносить значения из объекта

        for (int i = 0; i < length; ) {
            if (tail.getSize() > 0) {    // если в блоке есть символы, то запускаем цикл, что забирает с этого блока символы
                for (int j = 0; j < tail.getSize(); j++) {
                    result[i] = tail.getSymbols()[j];
                    i++;
                }
                tail = tail.getNext();       // после окончания прохода по блоку, переходим на следующий блок
            }
        }
        return String.valueOf(result);
    }


    private class StringItem {
        private final static byte SIZE = 16;  // максимальная емкость блока
        private char[] symbols; // символьный массив для хранения элементов строки
        StringItem next;   // ссылка на следующий блок
        private byte size; // текущая заполненность блока

        // конструктор класса
        private StringItem() {
            this.size = 0; // инициализация емкости
            this.symbols = new char[SIZE]; //инициализация символьного массива емкостью размером в SIZE
        }


        // Вспомогательный метод
        // pos - позиция в массиве
        // value - значение
        // присваивает значение и увеличивает емкость
        private void setElementChars(byte pos, char value) {
            this.symbols[pos] = value;
            this.size += 1;
        }

        // возвращает ссылку на массив текущего экземпляра StringItem
        private char[] getSymbols() {
            return symbols;
        }

        // заменяет ссылку на символьный массив
        private void setSymbols(char[] chars) {
            this.symbols = chars;
        }

        // передает ссылку на следующий блок
        private StringItem getNext() {
            return next;
        }

        // присваивает текущему объекту StringItem ссылку на другой экземпляр StringItem
        private void setNext(StringItem next) {
            this.next = next;
        }

        // возвращает текущую емкость блока
        private byte getSize() {
            return size;
        }

        // заменяет текущую емкость блока
        private void setSize(byte size) {
            this.size = size;
        }

        // возвращает максимальный объем блока
        private byte getMaxSize() {
            return SIZE;
        }

    }
}


