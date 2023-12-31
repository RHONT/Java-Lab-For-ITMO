package Lab_2_New;

public interface PriorityQueue {
    void insert(int val); //помещает значение val в очередь

    int deleteMax(); //удаляеет максимальное значение-приоритет из

    //очереди и возвращает его как результат
    boolean full(); //возвращает true, если очередь полная, иначе false

    boolean empty(); //возвращает true, если очередь пустая, иначе false
}
