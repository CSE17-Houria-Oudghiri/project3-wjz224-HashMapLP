import java.util.ArrayList;
import java.util.LinkedList;
/***
 * Class to model the entity HashMapLP class
 * @author Wilson Zheng
 * @version 1.53 (Visual Studio Code)
 * Date of creation: May 3rd 2021
 * Last Date Modified: May 3rd, 2021
 */
public class HashMapLP<K,V> {
    private int size;
    private double loadFactor;
    private MapEntry<K,V>[] hashTable;
    // Inner class for hash map entry

    // Constructors
    public HashMapLP(){ // O(log n)
        this(100,0.9);
    }
    public HashMapLP(int c){ // O(log n)
        this (c,0.9);
    }
    public HashMapLP(int c, double lf){ // O(log n)
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
        HashMaps.LPIterations=0;  // Iterations using Linear Probing which can be referenced in the main class for each key being searched for
        if (hashTable[bucketIndex] != null) {
            HashMaps.LPIterations++; // Increment the Linear probing while loop iterations 
            while(!hashTable[bucketIndex].getKey().equals(key)) {
                bucketIndex = hash(bucketIndex+1); // linear probing increasing by 1
                HashMaps.LPIterations++;
            }
        }
        return null;
    }
    // remove a key if found
    public void remove(K key){ // O(1)
        int bucketIndex = hash(key.hashCode());
        if(hashTable[bucketIndex] != null) {
            while(!hashTable[bucketIndex].getKey().equals(key)) { //Checks if the hashTable value at bucketIndex equals the key being searched for
                bucketIndex = hash(bucketIndex + 1); // linear probing increasing by 1
                if(hashTable[bucketIndex] == null){ // if there is an empty spot before finding the value then it doesn't exist in the hashtable
                    break;
                }
                else if(hashTable[bucketIndex].getKey().equals(key)) // sets the hashTable at index to null to remove the value
                hashTable[bucketIndex] = null;
                size--; // decrements the size.
                break;
            }
        }
    }
    // adds a new key or modifies an existing key
    public V put(K key, V value){ // The key is in the hash map  O(1)
        int bucketIndex = hash(key.hashCode());
        if (hashTable[bucketIndex] != null) {
            while(!hashTable[bucketIndex].getKey().equals(null) && !hashTable[bucketIndex].getKey().equals(key)){ // checks if the value at hashTable at bucketIndex is equal to null or keys
                bucketIndex = hash(bucketIndex+1);  // linear probing increasing by 1
                if(hashTable[bucketIndex] == null){  // if the first bucketIndex found by linear probing is null, then insert the value into the hashTable
                    hashTable[bucketIndex] = new MapEntry<K,V>(key,value);
                    size ++; // increment the size
                    return value; // return the value being inserted
                }
                else if(hashTable[bucketIndex].getKey().equals(key)){ // if the bucketIndex value is equal to key, then nothing happens and return the value of the key
                    V old = hashTable[bucketIndex].getValue();
                    hashTable[bucketIndex].value = value;
                    return value;
                }  
            }
        }
        
        else{ // if the first hashTable value using linear probing is null then insert the MapEntry. 
            hashTable[bucketIndex] = new MapEntry<K,V>(key,value);
            size ++;  // increment the size
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