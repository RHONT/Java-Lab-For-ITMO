package Lab_3;

import java.lang.reflect.Array;

public class MySequance<T> {
    private T[] array;  // объявляю обобщенный массив
    private T last;    // объявляею переменную в которой будет храниться последнее валидное значение массива
    private int size;  // объявляю вспомогательную переменную, где будет лежать размер массива


    //Конструктор!
    // Class<T> clazz - переменная которая обозначает тип будущего массива
    // int capacity - размерность массива
    public MySequance(Class<T> clazz, int capacity) {
        // посредством рефлекции инициализируем массив типа Т
        this.array = (T[]) Array.newInstance(clazz, capacity);
        size = capacity;
    }

    // Insert(x, p, L). вставим x в позицию p
    public void insert(T element, int position) throws ElementNotFoundException {

        // если позиция больше чем end b позиция выходит за пределы массива ничего не делаем
        if (position > array.length || position > end() || position < 1) {
            return;
        }
        // делаем переменную, которая служит концом для цикла for
        int endPosition = end() == array.length ? array.length : end();

        // запоминаем текущий элемент
        T temp = array[position - 1];
        // меняем его местами с новым вставляемым
        array[position - 1] = element;

        // если вставляемый элемент приходиться на end, обновляем last
        if (position == endPosition) {
            last = array[position - 1];
        }

        // циклом переписываем значение, сдвигая массив вправо.
        for (int i = position; i < endPosition; i++) {
            T temp_2 = array[i];
            array[i] = temp;
            temp = temp_2;
        }

    }

    // Находим позицию после последнего занятого
    // самая противоречивая функция. Много где задействуется, но проблемы ее, когда список полностью заполнен
    // если массив заполнен, выбрасывать исключение?
    public int end() throws ElementNotFoundException {

        // если последняя ячейка занята, пробрасываем исключение
        if (array[array.length - 1] != null) {
            throw new ElementNotFoundException("Массив заполнен!");
        }

        // идем с конца массива, находим не нулевой элемент и отходим на шаг вперед
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] != null) {
                return i + 2;
            }

        }
        // если список пуст, возвращаем первую его позицию
        return 1;
    }

    // Locate(x, L) – возвращает позицию в списке L объекта x.
    // Если объекта в списке нет, то возвращается позиция End(L).
    // Если несколько значений, совпадает со значением x, то возвращается первая позиция от начала.
    public int locate(T element) throws ElementNotFoundException {

        // ищем первое совпадение циклом
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i])) {
                return i + 1;
            }
        }
        //Если объекта в списке нет, то возвращается позиция End(L)
        return end();
    }

    //Retrieve(p, L) – возвращается объект списка L в позиции p
    public T retrieve(int position) throws ElementNotFoundException {
        // если не позиция не входит в диапазон, выбрасываем исключение

        if (position > size || position < 1 || array[position - 1] == null) {
            throw new ElementNotFoundException("Элемент c позицией " + position + " не найден");
        }

        // иначе возвращаем значение
        return array[position - 1];
    }


    //Delete(p, L) – удалить элемент списка L в позиции p.
    public void delete(int position) throws ElementNotFoundException {
        // если позиция выходит за пределы диапазона массива или идет попытка удалить пустой элемент - ничего не делать
        if (position > array.length || array[position - 1] == null) {
            return;
        }

        // находим крайний индекс для замены элементов после удаления
        // если позиция совпадает с размером массива удаляем последний элемент и выходим из метода.
        if (position == array.length) {
            array[position - 1] = null;
            // обновляем last
            last = array[position - 2];
            return;
        }

        // находим крайний индекс для цикла for
        int endPosition;
        if (array[array.length - 1] != null) {
            endPosition = array.length;
        } else endPosition = end();

        // запускаем цикл, чтобы сместить массив после удаления элемента
        // Вход: 1 5 10,
        // удаляем 1,
        // Выход: 5, 10
        for (int i = position - 1; i < endPosition - 1; i++) {
            if (array[i + 1] == null) {
                array[i] = null;
                break;
            } else {
                array[i] = array[i + 1];
            }
        }
        // Если массив был полностью заполнен, то последний элемент нужно принудительно обнулить.
        if (array[endPosition - 1] != null) {
            array[endPosition - 1] = null;
            last = array[endPosition - 2];
        } else
            // обновляем last
            last = endPosition - 2 >= 1 ? array[endPosition - 3] : array[0];
    }

    // Next(p, L) – возвращает следующую за p позицию в списке L.
    public int next(int position) throws ElementNotFoundException {
        // если позиция совпадает с последним элементом вернем end()
        if (last.equals(array[position - 1])) {
            return end();
        }
        // если позиция пустая, выбросить исключение
        if (array[position - 1] == null) {
            throw new ElementNotFoundException("Позиция = " + position + "пустая");
        }

        // если позиция не пустая, вернуть position+1
        if (array[position - 1] != null) {
            return position + 1;
        }
        return array.length - 1;
    }

    //Previous(p, L) – возвращает предыдущую перед p позицию в списке L.
    public int previous(int position) throws ElementNotFoundException {
        // если позиция крайняя и не равна null, вернем предыдущую
        if (position == array.length && array[position - 1] != null) {
            return position - 1;
        }

        // если позиция равна 0 или позиция больше или равна end() выбросим исключение
        if (position == 0 || position >= end()) {
            throw new ElementNotFoundException("Позиция " + position + " вышла за предел массива");
        }
        return position - 1;
    }

    //Makenull(L) – делает список пустым.
    public void makeNull() {
        // бежим по for и обнуляем непустые элементы
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                array[i] = null;
            } else break;
        }
        last = array[0];
    }

    // возвращает 1-ую позицию в списке L. Если список пустой, то возвращается End(L).
    // самый непонятный метод. Если в списке что-то есть метод вернет первую позицию
    // Позиция - это элемент массива или индекс?
    // Если в списке ничего нет, он тоже вернет первую позицию, только уже там null и к чему тут end() непонятно
    // По логике метод должен вернуть элемент первый, а метод end() возвращает позицию. Два разных типа данных
    // назначение и описание метода непонятны.
    public T first() {
        if (array[0] != null) {
            return array[0];
        } else {
            return null;
        }
    }

    // возвращаем последний элемент
    public T getLast() {
        return last;
    }

    // возвращаем размер массива
    public int getSize() {
        return size;
    }

    //Printlist(L) – вывод списка на печать в порядке расположения элементов в списке.
    public void printlist() {
        for (var element : array) {
            if (element != null) {
                System.out.print(element + " | ");
            } else break;
        }
        System.out.println();
    }

    // Обобщенный метод для удаления дубликатов
    static <T extends Comparable<T>> void deleteDublicates(MySequance<T> mySequance) throws ElementNotFoundException {
        // инициализируем буферную переменную
        T temp = null;
        //объявляем переменную, которая в последствии станет концом для цикла for
        int endPos;
        // верим, что массив не заполнен до конца и метод end() вернет значение индекса элемента, что еще не занят
        try {
            endPos = mySequance.end();
        } catch (ElementNotFoundException e) {
            // если массив заполнен до конца, значит крайний индекс для поиска будет значением размера массива
            endPos = mySequance.getSize();
        }

        // запускаем цикл for до предпоследнего элемента
        for (int i = 1; i <= endPos - 1; i++) {
            // в временную переменную кладем текущий элемент массива
            temp = mySequance.retrieve(i);
            // запускаем внутренний цикл для поиска совпадений от текущей позиции
            for (int j = i; j <= endPos - 1; j++) {
                // инициализируем вторуюу буфферную переменную
                T compairing = null;
                try {
                    // пытаемся получить следующий элемент
                    // если такового нет, значит compairing останется с null
                    compairing = mySequance.retrieve(j + 1);
                } catch (ElementNotFoundException e) {
                }
                // если следующий элемент не пустой
                if (compairing != null) {
                    //то запускаем сравнение их с первым буферным элементом
                    // если они равны, значит мы удаляем этот элемент и уменьшаем endPos на единицу
                    // чтобы не бегать по пустым уже удаленным позициям
                    if (temp.compareTo(compairing) == 0) {
                        mySequance.delete(j + 1);
                        j--;
                        endPos--;
                    }
                    // если же compairing(следующая позиция)  пустая, значит мы обрываем внутренний цикл
                    // Так как мы дошли до его конца
                } else break;
            }
        }

    }

}
