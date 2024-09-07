import java.nio.file.Paths;

public class Main {

    public static int compStringsAlpha(char[] ch1, char[] ch2){
        int minStringLength = Math.min(ch1.length, ch2.length);

        for (int i = 0; i <minStringLength; i++) {
            if (ch1[i] < 91 && ch1[i] > 64)
                ch1[i] += 32;
            if (ch2[i] < 91 && ch2[i] > 64)
                ch2[i] += 32;
            if (ch1[i] < ch2[i])
                return -1;
            else if (ch1[i] > ch2[i])
                return 1;
        }
        if (ch1.length > ch2.length)
            return 1;
        else if(ch1.length < ch2.length)
            return -1;
        return 0;
    }

    public static void main(String[] args) {

        LogSetup.setupLogging(Paths.get("hahaha"));

//        BTree t = new BTree(2); // A B-Tree with minium degree 2
//        t.insert(1);
//        t.insert(3);
//        t.insert(7);
//        t.insert(10);
//        t.insert(11);
//        t.insert(13);
//        t.insert(14);
//        t.insert(15);
//        t.insert(18);
//        t.insert(16);
//        t.insert(19);
//        t.insert(24);
//        t.insert(25);
//        t.insert(26);
//        t.insert(21);
//        t.insert(4);
//        t.insert(5);
//        t.insert(20);
//        t.insert(22);
//        t.insert(2);
//        t.insert(17);
//        t.insert(12);
//        t.insert(6);
//
//        System.out.println("Traversal of tree constructed is");
//        t.traverse();
//        System.out.println();
//
//        t.remove(6);
//        System.out.println("Traversal of tree after removing 6");
//        t.traverse();
//        System.out.println();
//
//        t.remove(13);
//        System.out.println("Traversal of tree after removing 13");
//        t.traverse();
//        System.out.println();
//
//        t.remove(7);
//        System.out.println("Traversal of tree after removing 7");
//        t.traverse();
//        System.out.println();
//
//        t.remove(4);
//        System.out.println("Traversal of tree after removing 4");
//        t.traverse();
//        System.out.println();
//
//        t.remove(2);
//        System.out.println("Traversal of tree after removing 2");
//        t.traverse();
//        System.out.println();
//
//        t.remove(16);
//        System.out.println("Traversal of tree after removing 16");
//        t.traverse();
//        System.out.println();
    }
}
