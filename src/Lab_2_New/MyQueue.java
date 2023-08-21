package Lab_2_New;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue implements PriorityQueue {

    private final int SIZE = 10;  // инициализируем размер очереди
    private int[] queue;        // объявляем массив очереди
    private int currSize = 0;    // инициализируем переменную отражения текущей заполненности массива
    Lock lock = new ReentrantLock();  // инициализируем замок
    Condition condition = lock.newCondition(); // инициализируем класс отражающий состояние замка

    // инициализирую массив
    public MyQueue() {
        queue = new int[SIZE];
    }

    // метод вставки нового элемента
    @Override
    public void insert(int val) {
        // закрываем замок
        lock.lock();
        try {
            // если очередь полная все потоки producer ждут
            while (full()) {
                condition.await();
            }
            // находим индекс первого пустого элемента
            int idxForInsert = findEmptyIdx();
            // добавляем этот индекс и значение в метод permutation
            // где новое значение находит себе место, перестраивая при этом массив сообразное его логике
            permutation(idxForInsert, val);
            // увеличиваем значение текущей емкости
            currSize++;
            System.out.println(this);
            // будим потоки, которые возможно ждали хоть какого-то значения в этом массиве
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //открываем замок
            lock.unlock();
        }
    }

    // находим индекс первого пустого элемента
    private int findEmptyIdx() {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == 0) {
                return i;
            }
        }
        return 10;
    }

    // возвращаем удаляемый максимальный элемент
    @Override
    public int deleteMax() {
        int result = 0;  // инициализируем переменную для ответа
        lock.lock();     // закрываем замок
        try {
            while (empty()) {   // если очередь пуста, то работающие с этим методом потоки пусть ждут
                condition.await();
            }
            // обрабатываем случай если в массиве только 1 элемент
            result = queue[0];
            if (currSize == 1) {
                queue[0] = 0;
                currSize--;
                System.out.println(this);
                // запускаем перестройку массива если элементов больше 1
            } else {
                // переиспользуем метод findEmptyIdx(), только с шагом назад, чтобы
                // найти значение последнего элемента
                int lastValue = queue[findEmptyIdx() - 1];
                // обнуляем последний элемент содержащий значние
                queue[findEmptyIdx() - 1] = 0;
                // кидаем на вершину значение удаленного элемента
                queue[0] = lastValue;
                // этот метод спустит вниз поставленный нами элемент, где он найдет свое место, согласно
                // логике массива
                deletePermutation();
                // уменьшаем на 1 значение текущей заполненности
                currSize--;
                System.out.println(this);
            }

            condition.signalAll();  // будим потоки, которые возможно хотели что-то добавить.
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();    // открываем замок
        }

        return result;  // возвращаем результат
    }

    // перестраиваем массив в ввиду добавления нового элемента
    // idxForInsert - позиция куда будет вставлен элемент
    // val - значение для этого элемента.
    private void permutation(int idxForInsert, int val) {
        // ставим "в край" добавляемый элемент
        queue[idxForInsert] = val;

        while (true) {
            // находим родителя
            int parent = (idxForInsert + 1) / 2;
            // если родитель больше 0 и значение родителя меньше чем наша вставка, производим обмен.
            if (parent > 0 && queue[parent - 1] < queue[idxForInsert]) {
                swap(idxForInsert, parent - 1);
                // обновляем индекс для вставки, поднимаемся выше
                idxForInsert = parent - 1;
            } else {
                // если упремся в начало или родитель будет больше или равен текущему элементу останавливаем работу.
                break;
            }
        }
    }

    // метод работат с момента когда вершину заменили на последний элемент
    // суть - надо этот элемент спустить вниз, заменяя его на максимальные значения одного из дочерних элементов
    private void deletePermutation() {
        // начинаем с первой позиции
        int currPos = 0;
        while (true) {
            // находим индекс максимального действующего сына
            int maxIdx = getMaxIdx(currPos);
            // если такого нет, то прекращаем работу
            if (maxIdx == 0) {
                return;
            }
            // если такой есть, то меняем местами их
            swap(currPos, maxIdx);
            // обновляем текущую позицию, начинаем "проваливаться вниз"
            currPos = maxIdx;
        }
    }

    // служебный метод для обмена значений
    private void swap(int currPos, int maxIdx) {
        int temp;
        temp = queue[currPos];
        queue[currPos] = queue[maxIdx];
        queue[maxIdx] = temp;
    }

    // находим максимальный дочерний элемент (нужен под замену для ф-ии deletePermutation)
    private int getMaxIdx(int currPos) {
        // определяем предполагаемые индексы
        int leftChild = (currPos + 1) * 2;
        int rightChild = (currPos + 1) * 2 + 1;

        // если все вышли за диапазон SIZE
        if (leftChild >= SIZE && rightChild >= SIZE) {
            return 0;
        }
        // если только левый вышел за пределы диапазона
        if (leftChild >= SIZE) {
            return 0;
        }
        // если правый вышел за диапазон, а левый имеет в себе значение отличное от 0
        if (rightChild >= SIZE && queue[leftChild - 1] != 0) {
            return leftChild - 1;
        }

        // если оба дочерних элемента меньше чем отец
        if (queue[leftChild - 1] <= queue[currPos] && queue[rightChild - 1] <= queue[currPos]) {
            return 0;
        }

        // если оба дочерних элемента не несут в себе значения
        if (queue[leftChild - 1] == 0 && queue[rightChild - 1] == 0) {
            return 0;
        }

        // возвращаем индекс максимального по значению дочернего элемента
        if (queue[leftChild - 1] >= queue[rightChild - 1]) {
            return leftChild - 1;
        } else return rightChild - 1;


    }

    // если текущий размер равен SIZE, то очередь полная
    @Override
    public boolean full() {
        return currSize == SIZE;
    }

    // если текущая заполненность 0, значит очередь пустая
    @Override
    public boolean empty() {

        return currSize == 0;
    }

    // вывод очереди
    @Override
    public String toString() {
        String resultString = "[ ";
        for (int element : queue) {
            resultString += element + " -- ";
        }
        resultString += " ]";
        return resultString;
    }

    // поток для изъятия элементов до 5 включительно
    public class Consumer_1_Thread extends Thread {
        public Consumer_1_Thread() {
            this.start();           // запускаем поток при создании экземпляра
        }

        @Override
        public void run() {
            for (; ; ) {         // запускаем бесконечный цикл потребления
                try {
                    // если очередь кончилась останавливаем поток
                    if (currSize == 0) {
                        this.stop();
                    }
                    Thread.sleep(1000);   // делаем задержку в 1 секунду
                    if (!empty() && queue[0] < 6) {    // если первый элемент меньше 6 запускаем метод  deleteMax()
                        System.out.println("Поток потребитель_1 изъял значение: " + deleteMax());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // поток для изъятия элементов больше 5
    public class Consumer_2_Thread extends Thread {

        public Consumer_2_Thread() {
            this.start();
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    if (currSize == 0) {
                        this.stop();
                    }
                    Thread.sleep(1000);
                    if (!empty() && queue[0] > 5) {
                        System.out.println("Поток потребитель_2 изъял значение: " + deleteMax());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // поток для вставки элементов
    public class Producer_1_Thread extends Thread {
        public Producer_1_Thread() {
            this.start();              // запускаем поток при создании экземпляра
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);   // делаем задержку в 1 секунду
                // генрируем случайное число добавление от 1 до 3
                int numberOccurrences = (int) (Math.random() * 3) + 1;
                // запускаем цикл с 1 до рандомного значения
                for (int i = 0; i < numberOccurrences; i++) {
                    // генерируем случайное число
                    int randomtemp = (int) (Math.random() * 5) + 1;
                    // добавляем в очередь случайное число
                    insert(randomtemp);
                    System.out.println("Поток производитель_1 положил в очередь значение: " + randomtemp);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class Producer_2_Thread extends Thread {
        public Producer_2_Thread() {
            this.start();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                int numberOccurrences = (int) (Math.random() * 4);
                for (int i = 0; i < numberOccurrences; i++) {
                    int randomtemp = (int) (6 + Math.random() * 5);
                    insert(randomtemp);
                    System.out.println("Поток производитель_2 положил в очередь значение: " + randomtemp);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
