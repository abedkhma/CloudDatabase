package de.tum.i13.server.caches;

import de.tum.i13.server.kv.KVMessage;
import de.tum.i13.server.kv.KVPair;

public class LRU extends Cache {
    public LRU(int capacity) {
        super();
        this.capacity = capacity;
    }


    //--------------------------------------PUT----------------------------------------------------------------
    public KVMessage.StatusType put(KVPair<String, String> newPair){
        if (newPair == null)
            throw new NullPointerException();

        try {
            if (!isContains(newPair)) {
                if (this.cash.size() >=  this.capacity)
                    this.cash.pop();

                if (this.cash.offerLast(newPair))
                    return KVMessage.StatusType.PUT_SUCCESS;
                return KVMessage.StatusType.PUT_ERROR;
            }
        }catch (Exception e){
            return KVMessage.StatusType.PUT_ERROR;
        }
        return updateCash(newPair);
    }
    //--------------------------------------PUT----------------------------------------------------------------

    //------------------------------------UPDATE---------------------------------------------------------------
    @Override
    public KVMessage.StatusType updateCash(KVPair<String, String> newPair){
        if (newPair == null)
            throw new NullPointerException();

        int i = 0, size = this.cash.size();
        KVPair<String, String> kvPair;

        try {
        while (i < size){
            kvPair = this.cash.pop();
            if (!kvPair.getKey().equals(newPair.getKey()))
                this.cash.offerLast(kvPair);
            i++;
        }
            this.cash.offerFirst(newPair);
        }catch (Exception e){
            return KVMessage.StatusType.PUT_ERROR;
        }

        return KVMessage.StatusType.PUT_SUCCESS;
    }
    //------------------------------------UPDATE---------------------------------------------------------------

    //-------------------------------------GET-----------------------------------------------------------------

    public String get(String key){
        if (key == null)
            throw new NullPointerException();

        KVPair<String, String> kvPair, getPair = null;
        int i = 0, size = this.cash.size();

        try {
            while (i < size) {
                kvPair = this.cash.pop();
                if (kvPair.getKey().equals(key))
                    getPair = kvPair;
                else this.cash.offerLast(kvPair);
                i++;
            }
            if (getPair != null)
                this.cash.offerFirst(getPair);
        } catch (Exception e) {
            throw new RuntimeException("Problem in get method in LRU class");
        }
        if (getPair != null)
            return getPair.getValue();
        return null;
    }

    //-------------------------------------GET-----------------------------------------------------------------


}
