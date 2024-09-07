package de.tum.i13.client;

import com.sun.jdi.request.StepRequest;
import de.tum.i13.server.kv.KVMessage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.tum.i13.shared.LogSetup.setupLogging;


public class Stam {
    static final Logger logger = Logger.getLogger(Stam.class.getName());

    public void stam() {
        System.out.println(this.getClass().getName());
    }

    public static void main(String[] args) throws IOException {

        System.out.println(File.separator);
//
        setupLogging(Paths.get("ClientLogReport.log"), Level.ALL);
        logger.setLevel(Level.ALL);

//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("Abdalla", "Mahamid");
//        hashMap.put("Rafeek", "Nasrtalla");
//        hashMap.put("Chino", "Araba");
//        hashMap.put("Abdalla", "dasdsaasda");
//
//        System.out.println(hashMap.put("dasdsa", "dad"));


//        String javaHome = System.getProperty("java.home");
//        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
//        String classpath = System.getProperty("java.class.path");
//        String className = Stam.class.getCanonicalName();
//
//        System.out.println("javaHome: " + javaHome);
//        System.out.println("javaBin:  " + javaBin);
//        System.out.println("classpath: " + classpath);
//        System.out.println("className: " + className);

        File dir = null;
        Path path = Paths.get(System.getProperty("user.dir"));
        if (!Files.exists(Path.of(path + File.separator + "Abood"))) {
            try {
                dir = new File(path + File.separator + "Abood");
                if (dir.mkdir())
                    System.out.println("Directory \"" + File.separator + "data" + "\" created");
            } catch (Exception e) {
                System.out.println("Error by creating the data directory in \"" + File.separator + "data"+ "\"");
                return;
            }
        }

//        System.out.println(dir);
//        System.out.println("-------------------------------");
//        final String hash = "abc";
//        final String fileName = hash + ".txt";
//        final File file = new File(dir, fileName);
//        if (file.createNewFile())
//            System.out.println("The file created");
//
        HashMap newHashMap;
//        HashMap<String, String> foodType = new HashMap<>();
//
//        FileOutputStream myFileOutStream;
//        ObjectOutputStream myObjectOutStream;
//
        FileInputStream fileInput;
        ObjectInputStream objectInput;
//
        Set<Map.Entry<String, String>> set;
        Iterator<Map.Entry<String, String>> iterator;
//
//        //--------------------------------------1_ Write-----------------------------
//
//
//        // storing data in HashMap
//        foodType.put("Burger", "Fastfood");
//        foodType.put("Cherries", "Fruit");
//        foodType.put("Fish", "hahahahv");
//        foodType.put("Spinach", "Vegetables");
//        foodType.put("Chicken", "Protein-Rich");
//
//        // try catch block
//        try {
//             myFileOutStream = new FileOutputStream(file,false);
//             myObjectOutStream = new ObjectOutputStream(myFileOutStream);
//
//             myObjectOutStream.writeObject(foodType);
//
//            // closing FileOutputStream and
//            // ObjectOutputStream
//            myObjectOutStream.close();
//
//            myFileOutStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //--------------------------------------1_ Write-----------------------------

        //--------------------------------------1_ Read-----------------------------


        try {
             fileInput = new FileInputStream("/Users/abedkhma/Desktop/gr3/MS2/base/data/clientDatabase.ser");
             objectInput = new ObjectInputStream(fileInput);

            newHashMap = (HashMap) objectInput.readObject();

            objectInput.close();
            fileInput.close();
        } catch (IOException obj1) {
            obj1.printStackTrace();
            return;
        } catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
            obj2.printStackTrace();
            return;
        }

        System.out.println("Deserializing  HashMap..");

        // Displaying content in "newHashMap.txt" using
        // Iterator
         set = newHashMap.entrySet();
        iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();

            System.out.print("key : " + entry.getKey()
                    + " & Value : ");
            System.out.println(entry.getValue());
        }
        //--------------------------------------1_ Read-----------------------------

//        //--------------------------------------2_ Write-----------------------------
//
//        try {
//
//             myFileOutStream = new FileOutputStream(file,false);
//             myObjectOutStream = new ObjectOutputStream(myFileOutStream);
//            foodType.put("Abdalla", "Mahamid");
//
//            myObjectOutStream.writeObject(foodType);
//
//            // closing FileOutputStream and
//            // ObjectOutputStream
//            myObjectOutStream.close();
//            myFileOutStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //--------------------------------------2_ Write-----------------------------
//
//        //--------------------------------------2_ Read-----------------------------
//        try {
//            fileInput = new FileInputStream(file);
//
//              objectInput
//                    = new ObjectInputStream(fileInput);
//
//            newHashMap = (HashMap) objectInput.readObject();
//
//            objectInput.close();
//            fileInput.close();
//        } catch (IOException obj1) {
//            obj1.printStackTrace();
//            return;
//        } catch (ClassNotFoundException obj2) {
//            System.out.println("Class not found");
//            obj2.printStackTrace();
//            return;
//        }
//
//        System.out.println("Deserializing  HashMap..");
//
//        // Displaying content in "newHashMap.txt" using
//        // Iterator
//         set = newHashMap.entrySet();
//         iterator = set.iterator();
//
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
//
//            System.out.print("key : " + entry.getKey()
//                    + " & Value : ");
//            System.out.println(entry.getValue());
//        }


        //Deque<KVPair> fifo = new LinkedList<>();
//        System.out.println(Integer.MAX_VALUE);
//        Stam s = new Stam();
//        s.stam();
//         LFU fifo = new LFU(5);
//
//
//        //FIFO fifo = new FIFO(5);
//        fifo.put(new KVPair("Abdalla","Mahamid"));
//        fifo.put(new KVPair("Rafeek","Nasralla"));
//        fifo.put(new KVPair("Yousef","Fathe"));
//        fifo.put(new KVPair("1234","12315"));
//        fifo.put(new KVPair("Saeed","Sakas"));
//        fifo.put(new KVPair("Chino","arabe"));
//        fifo.put(new KVPair("Yousef","Fatt"));
//        fifo.remove(new KVPair("1234","12315"));
////
////
//        System.out.println(fifo);
//        System.out.println("--------------------");
////
//
//        System.out.println(fifo.get("Rafeek"));
//        System.out.println("--------------------");
//        System.out.println(fifo);
//        fifo.get("Chino");
//        System.out.println("--------------------");
//        System.out.println(fifo);
//
//        fifo.put(new KVPair("Yousef","Yousef"));
//        fifo.put(new KVPair("Obyda","123"));
//        fifo.put(new KVPair("Yousef","111"));
//        System.out.println(fifo.get("Saeed"));
//        System.out.println("--------------------");
//
//        fifo.put(new KVPair("shady","111"));
//        System.out.println(fifo);
//        fifo.get("shady");
//        fifo.get("shady");
//        fifo.put(new KVPair("Haroun","Khouni"));
//        fifo.put(new KVPair("Abdalla","Khouni"));
//        System.out.println(fifo);

    }
}

