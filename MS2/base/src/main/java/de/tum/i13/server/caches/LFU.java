package de.tum.i13.server.caches;

import de.tum.i13.server.kv.KVMessage;
import de.tum.i13.server.kv.KVPair;

public class LFU extends Cache {
    public LFU(int capacity) {
        super();
        this.capacity = capacity;
    }

    //--------------------------------------PUT----------------------------------------------------------------
    @Override
    public KVMessage.StatusType put(KVPair<String, String> newPair) {
        if (newPair == null)
            throw new NullPointerException();

        if (!isContains(newPair)) {
            if (this.cash.size() >= capacity)
                removeMin(newPair.getKey());
            if (this.cash.offerLast(newPair))
                return KVMessage.StatusType.PUT_SUCCESS;
            return KVMessage.StatusType.PUT_ERROR;
        }
        return updateCash(newPair);
    }
    //--------------------------------------PUT----------------------------------------------------------------

    //------------------------------------UPDATE---------------------------------------------------------------
    @Override
    public KVMessage.StatusType updateCash(KVPair<String, String> newPair) {
        if (newPair == null)
            throw new NullPointerException();

        int i = 0, size = this.cash.size();;
        KVPair<String, String> kvPair, temp = null;

        try {
            while (i < size) {
                kvPair = this.cash.pop();
                if (kvPair.getKey().equals(newPair.getKey())) {
                    kvPair.setValue(newPair.getValue());
                    kvPair.setPriority();
                    temp = kvPair;
                }
                else this.cash.offerLast(kvPair);
                i++;
            }
            if (temp != null)
                this.cash.offerLast(temp);
        } catch (Exception e) {
            return KVMessage.StatusType.PUT_ERROR;
        }
        return KVMessage.StatusType.PUT_SUCCESS;
    }
    //------------------------------------UPDATE---------------------------------------------------------------

    //-------------------------------------GET-----------------------------------------------------------------
    @Override
    public String get(String key) {
        if (key == null)
            throw new NullPointerException();

        KVPair<String, String> kvPair, temp = null;
        int i = 0, size = this.cash.size();;

        try {
            while (i < size) {
                kvPair = this.cash.pop();
                if (kvPair.getKey().equals(key)) {
                    kvPair.setPriority();
                    temp = kvPair;
                }
               else this.cash.offerLast(kvPair);
                i++;
            }
            if (temp != null)
                this.cash.offerLast(temp);
        } catch (Exception e) {
            throw new RuntimeException("Problem in get method in LRU class");
        }
        assert temp != null;
        return temp.getValue();
    }
    //-------------------------------------GET-----------------------------------------------------------------

    //---------------------------------REMOVE_MIN--------------------------------------------------------------
    private boolean removeMin(String key) {
        int min = findMin(key);
        if (min == -1)
            return false;

        KVPair<String, String> kvPair;
        int i = 0, size = this.cash.size();
        boolean bol = true;
        try {
            while (i < size) {
                kvPair = this.cash.pop();
                if (kvPair.getPriority() == min && bol) {
                    bol = false;
                    i++;
                    continue;
                } else this.cash.offerLast(kvPair);
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException("Problem in removeMin method in LFU class");
        }
        return true;
    }
    //---------------------------------REMOVE_MIN--------------------------------------------------------------


    //----------------------------------FIND_MIN---------------------------------------------------------------
    private int findMin(String key) {
        if (key == null)
            throw new NullPointerException();
        KVPair<String, String> kvPair;
        int i = 0, min = Integer.MAX_VALUE, size = this.cash.size();
        try {
            while (i < size) {
                kvPair = this.cash.pop();
                if (kvPair.getPriority() < min)
                    min = kvPair.getPriority();
                this.cash.offerLast(kvPair);
                i++;
            }
        } catch (Exception e) {
            return -1;
        }
        return min;
    }
    //----------------------------------FIND_MIN---------------------------------------------------------------

}



