package Lab_2;

import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TreeNode tree = new TreeNode();

        tree.AltInsert(10);
        tree.AltInsert(4);
        tree.AltInsert(8);
        tree.AltInsert(3);
        tree.AltInsert(5);
        tree.AltInsert(6);
        tree.AltInsert(5);
        tree.AltInsert(1);
        tree.AltInsert(1);

        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());
        System.out.println(tree.AltDeleteMax());


        tree.AltInsert(3);
        tree.AltInsert(6);
        tree.AltInsert(5);
        tree.AltInsert(5);
        tree.AltInsert(1);
        tree.AltInsert(1);
        tree.AltInsert(7);


//        Consumer_1_Thread cons1 = new Consumer_1_Thread(tree);
//        Consumer_2_Thread cons2 = new Consumer_2_Thread(tree);
//
//
//        Producer_1_Thread prod1 = new Producer_1_Thread(tree);
//        Producer_2_Thread prod2 = new Producer_2_Thread(tree);
//        n1.add(10);
//        n1.add(8);
//        n1.add(5);
//        n1.add(1);
//        n1.add(2);
//        n1.add(6);
//        n1.add(5);
//        n1.add(5);
//        n1.add(5);
//        n1.add(1);
//        n1.add(1);
//        n1.add(1);
//        n1.add(1);
//        n1.add(4);


//        System.out.println(Arrays.toString(n1.buildResultArray()));
//
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());
//        System.out.println(Arrays.toString(n1.buildResultArray()));
//        System.out.println(n1.deleteMax());


//        cons1.join();
//        cons2.join();
//        prod1.join();
//        prod2.join();

        System.out.println("strop");
    }


}


