package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }


    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private Set<K> keySet;
    private double loadFactor;
    private int initialSize;
    /** Constructors */
    public MyHashMap() {
        this(16,0.75);
    }



    public MyHashMap(int initialSize) {
        this(initialSize,0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.loadFactor = maxLoad;

        this.buckets = this.createTable(this.initialSize);
        for(int i=0; i<initialSize; ++i){
            buckets[i] = this.createBucket();
        }

        this.keySet = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key,value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    /** Removes all of the mappings from this map. */
    public void clear(){
        this.keySet = new HashSet<K>();
        int currBucketLength = this.buckets.length;
        this.buckets = this.createTable(currBucketLength);
        for(int i=0; i<currBucketLength; ++i){
            this.buckets[i] = this.createBucket();
        }

        return;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        return this.keySet.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key){
        int bucketIndex = this.getBucketIndexByKey(key,this.buckets.length);
        Collection<Node>currBucket = this.buckets[bucketIndex];
        for(Node n:currBucket){
            if(n.key.equals(key)){
                return n.value;
            }
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size(){
        return keySet.size();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value){

        int bucketIndex = this.getBucketIndexByKey(key,this.buckets.length);
        Collection<Node>currBucket = this.buckets[bucketIndex];
        boolean existed = false;
        for(Node n:currBucket){
            if(n.key == key){
                existed = true;
                n.value = value;
                break;
            }
        }
        if(!existed){
            currBucket.add(new Node(key,value));
            this.keySet.add(key);
        }


        if((double)this.keySet.size()/(double)this.buckets.length > this.loadFactor){

            rehash();
        }
        return;
    }

    private void rehash(){
        int newBucketsSize = Math.multiplyExact(this.buckets.length,2);
        Collection<Node>[] newBuckets = this.createTable(newBucketsSize);
        for(int i=0; i<newBuckets.length; ++i){
            newBuckets[i] = this.createBucket();
        }
        for(K key: this.keySet){
            Node currNode = new Node(key,this.get(key));
            int bucketIndex = getBucketIndexByKey(key,newBucketsSize);
            newBuckets[bucketIndex].add(currNode);
        }

        this.buckets = newBuckets;

    }

    private int getBucketIndexByKey(K key,int bucketSize){
        int currBucketSize = bucketSize;
        int index = Math.floorMod(key.hashCode(),currBucketSize);
        return index;
    }
    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet(){
        return this.keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key){
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet.iterator();
    }


}
