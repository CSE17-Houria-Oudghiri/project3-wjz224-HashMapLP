import java.util.ArrayList;
import java.util.LinkedList;
/***
 * Class to model the entity HashMapQp class
 * @author Wilson Zheng
 * @version 1.53 (Visual Studio Code)
 * Date of creation: May 3rd 2021
 * Last Date Modified: May 3rd, 2021
 */
public class HashMapQP<K,V> {
    private int size;
    private double loadFactor;
    private MapEntry<K,V>[] hashTable;
    // Inner class for hash map entry

    // Constructors
    public HashMapQP(){ // O(log n)
        this(100,0.9);
    }
    public HashMapQP(int c){ // O(log n)
        this (c,0.9);
    }
    public HashMapQP(int c, double lf){ // O(log n)
        hashTable = new MapEntry[trimToPowerOf2(c)];
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
        hashTable = new MapEntry[hashTable.length << 1]; // * 2
        size = 0;
        for(MapEntry<K,V> entry: hashTable){
            put(entry.getKey(),entry.getValue()); // clear LL at index i //O(1)
        }
    } 
    // public interface
    public int size(){ //O(1)
        return size;
    }
    public void clear(){ // O(n)4
        hashTable = new MapEntry[trimToPowerOf2(100)];
        loadFactor = 0.9;
        size = 0;
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
        int bucketIndex = hash(key.hashCode());
        HashMaps.QPIterations= 0; // Iterations using Quadratic Probing which can be referenced to in the main class per key being searched for
        if (hashTable[bucketIndex] != null) { // checks if the first bucketIndex value is null
            HashMaps.QPIterations++; // Iterations increment using Quadratic probing in the while loop
            while(!hashTable[bucketIndex].getKey().equals(key)) {
                int j = 1;
                bucketIndex = hash(bucketIndex+ j^2); // Quadratic probing bucketIndex increase.
                HashMaps.QPIterations++;
            }
        }
        return null; // if the first bucketIndex value is null
    }
    // remove a key if found
    public void remove(K key){ // O(1)
        int bucketIndex = hash(key.hashCode()); 
        if(hashTable[bucketIndex] != null) { 
            while(!hashTable[bucketIndex].getKey().equals(key)) { // checks if the hashTable value at bucketIndex is equal to key
                int j = 1;
                bucketIndex = hash(bucketIndex + j^2); // incrementing the bucketIndex using quadratic probing
                if(hashTable[bucketIndex] == null){ // if the value at bucketIndex is null before finding the value, then the value doesn't exist in the hashTable
                    break;
                }
                else if(hashTable[bucketIndex].getKey().equals(key)) // checks if the hashTable value at bucketIndex is equal to the key
                hashTable[bucketIndex] = null; // removes the bucketIndex value in the hashTable by setting it to null
                size--; // decrementing the size
                break;
            }
        }
    }
    // adds a new key or modifies an existing key
    public V put(K key, V value){ // The key is in the hash map  O(1)
        int bucketIndex = hash(key.hashCode());
        if (hashTable[bucketIndex] != null) {
            while(!hashTable[bucketIndex].getKey().equals(null) && !hashTable[bucketIndex].getKey().equals(key)){
                int j = 1;
                bucketIndex = hash(bucketIndex+ j^2);   // quadratic probing 
                if(hashTable[bucketIndex] == null){ // checks if the value in hashTable at bucketIndex is null
                    hashTable[bucketIndex] = new MapEntry<K,V>(key,value); // if the value at bucketIndex is empty, add the MapEntry
                    size ++; // incrementing the size
                    return value;
                }
                else if(hashTable[bucketIndex].getKey().equals(key)){ // CHecks if the value in the hashTable already exists, if it does nothing changes.
                    V old = hashTable[bucketIndex].getValue();
                    hashTable[bucketIndex].value = value;
                    return value;
                }  
            }
        }
        
        else{ // if first value at bucketIndex is null, then add the MapEntry
            hashTable[bucketIndex] = new MapEntry<K,V>(key,value);
            size ++; // increment the size
            return value;
        }
        return value;
    }
    // returns the elements of the hash map as a string
    public String toString(){ // O(n)
        String out = "[";
        for(int i = 0; i < hashTable.length; i ++){
            if(hashTable[i] != null){
                for(MapEntry<K,V> entry: hashTable){
                    if(entry != null){
                        out += entry.toString();
                    }
                }
                out += "\n";
            }
        }
        out += "]";
        return out; // return toList().toString()
    }
}