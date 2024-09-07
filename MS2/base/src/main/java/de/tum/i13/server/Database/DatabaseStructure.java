package de.tum.i13.server.Database;

import de.tum.i13.server.kv.KVMessage;
import de.tum.i13.server.kv.KVPair;
import de.tum.i13.server.kv.KVStore;
import de.tum.i13.server.nio.StartSimpleNioServer;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/*
* This Class responsible for the database in higher level
* it takes the data from the file and build a data structure
*/
public class DatabaseStructure implements KVStore {

    private ConcurrentHashMap<String, String> hashMap;
    FileProcessor fileProcessor;

    public DatabaseStructure(Path path) {
        this.fileProcessor = new FileProcessor(path);
        StartSimpleNioServer.logger.info("Load the file to the hashmap in "  + this.getClass().getName());
        this.hashMap = fileProcessor.load();
        if (hashMap == null) {
            StartSimpleNioServer.logger.info("The hashmap is empty in " + this.getClass().getName());
            hashMap = new ConcurrentHashMap<>();
        }

    }

    @Override
    public String put(String key, String value) throws Exception {
        hashMap.put(key, value);
        if (fileProcessor.write(hashMap) == KVMessage.StatusType.PUT_SUCCESS)
            return KVMessage.StatusType.PUT_SUCCESS.toString();
        return KVMessage.StatusType.PUT_ERROR.toString();
    }

    @Override
    public String get(String key) throws Exception {
        if (hashMap == null)
            return KVMessage.StatusType.GET_ERROR.toString();
        return hashMap.get(key);
    }

    @Override
    public String delete(String key) throws Exception {
        if (hashMap == null)
            return KVMessage.StatusType.DELETE_ERROR.toString();
        if(hashMap.remove(key) != null)
            if (fileProcessor.write(hashMap) == KVMessage.StatusType.PUT_SUCCESS)
                return KVMessage.StatusType.DELETE_SUCCESS.toString();
        return KVMessage.StatusType.DELETE_ERROR.toString();
    }
}
