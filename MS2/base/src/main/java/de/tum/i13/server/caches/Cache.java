package de.tum.i13.server.caches;

import de.tum.i13.server.kv.KVMessage;
import de.tum.i13.server.kv.KVPair;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

public abstract class Cache {
    public static Logger logger = Logger.getLogger(Cache.class.getName());

    public final Deque<KVPair<String, String>> cash;
    protected int capacity;

    protected Cache() {
        this.cash = new ConcurrentLinkedDeque<>();
    }
    //insert a new pair of key value, if the key already in the cash, it will be updated
    public abstract KVMessage.StatusType put(KVPair<String, String> newPair);

    // update the cash for an existing pair of Key value
    public abstract KVMessage.StatusType updateCash(KVPair<String, String> newPair);

    //return the KVPair from the List, in not found, it will return null
    public abstract String get(String key);


    //----------------------------------IS_CONTAINS------------------------------------------------------------
    //check if the cash contains a specific pair
    public boolean isContains(KVPair<String, String> pair){

        boolean bol = false;
        int i = 0;
        KVPair<String, String> kvPair;
        try {
            while (i < this.cash.size()) {
                kvPair = cash.pop();
                if (kvPair.getKey().equals(pair.getKey()))
                    bol = true;
                cash.offerLast(kvPair);
                i++;
            }
        }catch (Exception e ) {
            System.out.println(this.getClass().getName() + ": Problem occurred with isContains method");
        }
        return bol;
    }
    //----------------------------------IS_CONTAINS------------------------------------------------------------

    //------------------------------------REMOVE---------------------------------------------------------------
    // delete an existing pair of ket value
    public KVMessage.StatusType delete(String key) {
        int i = 0 , size = cash.size();
        KVPair<String, String> kvPair;
        boolean bol = false;

        try {
            while (i < size) {
                kvPair = cash.pop();
                if (!kvPair.getKey().equals(key))
                    cash.offerLast(kvPair);
                else bol = true;
                i++;
            }
            }catch(Exception e){
                return KVMessage.StatusType.DELETE_ERROR;
            }
        if (!bol)
            return KVMessage.StatusType.DELETE_ERROR;
        return KVMessage.StatusType.DELETE_SUCCESS;
        }
    //------------------------------------REMOVE---------------------------------------------------------------

    //----------------------------------TO_STRING--------------------------------------------------------------

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (KVPair<String, String> kvp: cash) {
            str.append(kvp.toString()).append("\n");
        }
        return str.toString();
    }
    //----------------------------------TO_STRING--------------------------------------------------------------

}
