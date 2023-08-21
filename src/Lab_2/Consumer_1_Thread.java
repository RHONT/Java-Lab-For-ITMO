package Lab_2;

public class Consumer_1_Thread extends Thread {
    TreeNode treeNode;

    public Consumer_1_Thread(TreeNode treeNode) {
        this.treeNode = treeNode;
        this.start();
    }

    @Override
    public void run() {

        for (; ; ) {
            try {
                Thread.sleep(1000);
                if (!treeNode.empty() && treeNode.root.value < 6) {
                    System.out.println("Поток потребитель_1 изъял значение: " + treeNode.deleteMax());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
