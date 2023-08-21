package Lab_2_New;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyQueue myQueue = new MyQueue();

        System.out.println("Производители");
        System.out.println();

        MyQueue.Producer_1_Thread producer_1_thread = myQueue.new Producer_1_Thread();
        MyQueue.Producer_2_Thread producer_2_thread = myQueue.new Producer_2_Thread();
        MyQueue.Producer_1_Thread producer_3_thread = myQueue.new Producer_1_Thread();
        MyQueue.Producer_2_Thread producer_4_thread = myQueue.new Producer_2_Thread();

        producer_1_thread.join();
        producer_2_thread.join();
        producer_3_thread.join();
        producer_4_thread.join();

        System.out.println();
        System.out.println("Потребление");
        System.out.println();

        MyQueue.Consumer_1_Thread consumer_1_thread = myQueue.new Consumer_1_Thread();
        MyQueue.Consumer_2_Thread consumer_2_thread = myQueue.new Consumer_2_Thread();


    }
}
