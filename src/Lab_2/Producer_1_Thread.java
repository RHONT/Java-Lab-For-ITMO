package Lab_2;

public class Producer_1_Thread extends Thread {
    TreeNode treeNode;

    public Producer_1_Thread(TreeNode treeNode) {
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
            int randomtemp = (int) (Math.random() * 6);
            treeNode.insert(randomtemp);
            System.out.println("Поток производитель_1 положил в очередь значение: " + randomtemp);
        }

    }
}
