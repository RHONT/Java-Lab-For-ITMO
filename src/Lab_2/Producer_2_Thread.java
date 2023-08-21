package Lab_2;

public class Producer_2_Thread extends Thread {
    TreeNode treeNode;

    public Producer_2_Thread(TreeNode treeNode) {

        this.treeNode = treeNode;
        this.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int numberOccurrences = (int) (Math.random() * 4);
        for (int i = 0; i < numberOccurrences; i++) {
            int randomtemp = (int) (6 + Math.random() * 5);
            treeNode.insert(randomtemp);
            System.out.println("Поток производитель_2 положил в очередь значение: " + randomtemp);
        }

    }
}
