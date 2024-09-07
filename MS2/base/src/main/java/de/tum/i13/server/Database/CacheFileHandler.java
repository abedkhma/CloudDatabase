package de.tum.i13.server.Database;

import de.tum.i13.server.caches.Cache;
import de.tum.i13.server.caches.FIFO;
import de.tum.i13.server.caches.LFU;
import de.tum.i13.server.caches.LRU;
import de.tum.i13.server.kv.KVMessage;
import de.tum.i13.server.kv.KVPair;
import de.tum.i13.server.kv.KVStore;
import de.tum.i13.server.nio.StartSimpleNioServer;

import java.nio.file.Path;
/*
* deal with put, get and delete commands to caches and database structure
*/
public class CacheFileHandler implements KVStore {

    private Cache cache;
    private DatabaseStructure databaseStructure;


    public CacheFileHandler(int cacheCapacity, String cacheDisplacement, Path path) {
        StartSimpleNioServer.logger.info("The cache displacement is: " + cacheDisplacement  + this.getClass().getName());
        switch (cacheDisplacement) {
            case "FIFO" -> cache = new FIFO(cacheCapacity);
            case "LFU" -> cache = new LFU(cacheCapacity);
            default -> cache = new LRU(cacheCapacity);
        }
        databaseStructure = new DatabaseStructure(path);
    }

    @Override
    public String put(String key, String value) throws Exception {
        if (databaseStructure.put(key, value).equals(KVMessage.StatusType.PUT_SUCCESS.toString())) {
            if (cache.put(new KVPair<>(key, value)) == KVMessage.StatusType.PUT_SUCCESS)
                return "put_success " + key + " " + value;
            else return "put_error " + key + " " + value;
        }return "put_error " + key + " " + value;
    }

    @Override
    public String get(String key) throws Exception {
        String value = cache.get(key);
        if (value == null)
            value = databaseStructure.get(key);
        if (value == null)
            return "get_error " + key;
        return value;
    }

    @Override
    public String delete(String key) throws Exception {
        if (databaseStructure.delete(key) == KVMessage.StatusType.DELETE_SUCCESS.toString()) {
            if (cache.delete(key) != KVMessage.StatusType.DELETE_SUCCESS)
                System.out.println("Was Not In The Cache");
            return "delete_success " + key;
        }return "delete_error " + key;
    }
}
