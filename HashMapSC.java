import java.util.ArrayList;
import java.util.LinkedList;
/***
 * Class to model the entity HashMapSC class
 * @author Wilson Zheng
 * @version 1.53 (Visual Studio Code)
 * Date of creation: May 3rd 2021
 * Last Date Modified: May 3rd, 2021
 */
public class HashMapSC<K,V> {
    private int size;
    private double loadFactor;
    private LinkedList<MapEntry<K,V>>[] hashTable;
    // Inner class for hash map entry

    // Constructors
    public HashMapSC(){ // O(log n)
        this(100,0.9);
    }
    public HashMapSC(int c){ // O(log n)
        this (c,0.9);
    }
    public HashMapSC(int c, double lf){ // O(log n)
        hashTable = new LinkedList[trimToPowerOf2(c)];
        loadFactor = lf;
    }
    private int trimToPowerOf2(int c){ // O(log n)
        int capacity = 1;
        while(capacity < c){
            capacity = capacity << 1; // * 2
        }
        return capacity;
    }
    private int hash(int hashCode){ // (1)
        return hashCode & (hashTable.length - 1);
    }
    private void rehash(){ // O(n)
        ArrayList<MapEntry<K,V>> list = toList();
        hashTable = new LinkedList[hashTable.length << 1]; // * 2
        size = 0;
        for(MapEntry<K,V> entry :list){
            put(entry.getKey(),entry.getValue()); // clear LL at index i //O(1)
        }
    } 
    // public interface
    public int size(){ //O(1)
        return size;
    }
    public void clear(){ // O(n)
        size = 0;
        for(int i = 0; i < hashTable.length; i++){
            if(hashTable[i] != null){
                hashTable[i].clear(); // clear LL at index i
            }
        }
    }
    public boolean isEmpty(){ // O(1)
        return (size == 0);
    }
    // search or key in the hash map, returns true if found
    public boolean containsKey(K key){ // O(1)
        if (get(key) != null){
            return true;
        }
        else{
            return false;
        }
    }
    // returns the value of key if found, otherwise null
    public V get(K key){ // search method O(1)
        HashMaps.SCIterations=0;
        int bucketIndex = hash(key.hashCode());
        if(hashTable[bucketIndex] != null){
            LinkedList<MapEntry<K,V>> bucket = hashTable[bucketIndex];
            for(MapEntry<K,V> entry: bucket){ // O(1)
                HashMaps.SCIterations++;
                if(entry.getKey().equals(key)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }
    // remove a key if found
    public void remove(K key){ // O(1)
        int bucketIndex = hash(key.hashCode());
        if(hashTable[bucketIndex] != null){ // key is in the hash map
            LinkedList<MapEntry<K,V>> bucket = hashTable[bucketIndex];
            for(MapEntry<K,V> entry: bucket){  // O(1)
                if(entry.getKey().equals(key)){
                    bucket.remove(entry); // O(1)
                    size--;
                    break;
                }
            }
        }
    }
    // adds a new key or modifies an existing key
    public V put(K key, V value){ // The key is in the hash map  O(1)
        if(get(key) != null){
            int bucketIndex = hash(key.hashCode());
            LinkedList<MapEntry<K,V>> bucket = hashTable[bucketIndex];
            for(MapEntry<K,V> entry: bucket){  // O(1)
                if(entry.getKey().equals(key)){
                    V old = entry.getValue();
                    entry.value = value;
                    return old;
                }
            }
        }
        // key not in the hash map - check load factor
        if(size >= hashTable.length * loadFactor){
            rehash();
        }
        int bucketIndex = hash(key.hashCode());
        // create a new bucket if bucket is empty
        if(hashTable[bucketIndex] == null){ // new element at index i
            hashTable[bucketIndex] = new LinkedList<MapEntry<K,V>>();
        }
        hashTable[bucketIndex].add(new MapEntry<K,V>(key,value));// collision
        size++;
        return value;
    }
    public ArrayList<MapEntry<K,V>> toList(){ // O(n)
        ArrayList<MapEntry<K,V>> list = new ArrayList<>();
        for(int i=0;i < hashTable.length; i ++){
            if(hashTable[i] != null){
                LinkedList<MapEntry<K,V>> bucket = hashTable[i];
                for(MapEntry<K,V> entry: bucket){
                    list.add(entry);
                }
            }
        }
        return list;
    }
    // returns the elements of the hash map as a string
    public String toString(){ // O(n)
        String out = "[";
        for(int i = 0; i < hashTable.length; i ++){
            if(hashTable[i] != null){
                for(MapEntry<K,V> entry: hashTable[i]){
                    out += entry.toString();
                }
                out += "\n";
            }
        }
        out += "]";
        return out; // return toList().toString()
    }
    public int collisions(){ // O(n)
        int max = 0; 
        for(int i = 0; i < hashTable.length; i ++){

            if(hashTable[i] != null){
                if(hashTable[i].size() > max){
                    max = hashTable[i].size();
                }
            }
        }
        return max;
    }
}

