package de.tum.i13.server.kv;

public class KVPair<K, V> {
    private K key;
    private V value;
    private int priority;

    public KVPair(K key, V value) {
        this.key = key;
        this.value = value;
        priority = 0;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setPriority(){priority++;}

    public int getPriority() {
        return priority;
    }

    public boolean equal (KVPair<K,V> pair){
        return this.value.equals(pair.value) && this.key.equals(pair.getKey());
    }

    public String toString (){
        return "[" + key + ", " + value + "]";
    }
}
