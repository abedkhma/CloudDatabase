package de.tum.i13.server.Database;

import de.tum.i13.server.kv.KVMessage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;


/*This Class deal with the file database in a lower level*/
public class FileProcessor {

    private final String fileName = "clientDatabase.ser";
    private final Path path;

    public FileProcessor(Path path) {
        this.path = path;
//        try {
//            new FileOutputStream(path.toString() + File.separator + this.fileName,false);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
    //------------------------------------------------WRITE-----------------------------------------------------
    //Write the Hashmap to the disk
    public KVMessage.StatusType write(ConcurrentHashMap<String, String> hashMap){
        //Initial tha objects
        FileOutputStream fileOut;
        ObjectOutputStream out;
        try {
            fileOut = new FileOutputStream(path.toString() + File.separator + this.fileName,false);
            out = new ObjectOutputStream(fileOut);
        } catch (FileNotFoundException e) {
            System.out.println("Error: by file output stream in: " + this.getClass().getName());
            return KVMessage.StatusType.PUT_ERROR;
        } catch (IOException e) {
            System.out.println("Error: by object output stream in: " + this.getClass().getName());
            return KVMessage.StatusType.PUT_ERROR;
        }
        //Write to the file
        try {
            out.writeObject(hashMap);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error: by writing the objectOutput in: " + this.getClass().getName());
            return KVMessage.StatusType.PUT_ERROR;
        }
        return KVMessage.StatusType.PUT_SUCCESS;
    }
    //------------------------------------------------WRITE-----------------------------------------------------


    //------------------------------------------------LOAD------------------------------------------------------
   //Load the file from the disk to the memory
    public ConcurrentHashMap<String, String> load() {

        if (Files.exists(Path.of(path + File.separator + fileName))) {
            ConcurrentHashMap<String, String> hashMap;
            FileInputStream fileIn;
            ObjectInputStream in;
            //Initial tha objects
            try {
                fileIn = new FileInputStream(path.toString() + File.separator + fileName);
                in = new ObjectInputStream(fileIn);
            } catch (FileNotFoundException e) {
                System.out.println("Error: by file input stream in: " + this.getClass().getName());
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("Error: by object input stream in: " + this.getClass().getName());
                throw new RuntimeException(e);
            }

            try {
                hashMap = (ConcurrentHashMap<String, String>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error: by reloading in class: " + this.getClass().getName());
                throw new RuntimeException(e);
            }
            return hashMap;
        }
        return null;
    }
    //------------------------------------------------LOAD------------------------------------------------------

}
