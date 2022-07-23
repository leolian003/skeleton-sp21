package bstmap;


import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    /** Removes all of the mappings from this map. */

    private class Node{
        K key;
        V value;
        Node left_child;
        Node right_child;
        int height;


        public Node(K k, V v){
            key = k;
            value = v;
            height = 1;
        }
    }

    private Node root;


    /** Removes all of the mappings from this map. */

    public BSTMap(){

    }
    public void clear(){
         root = null;
    }



    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        return containHelper(key,root);

    };

    private boolean containHelper(K key,Node node) {
        if (node == null) {
            return false;
        }
        if (key.compareTo(node.key) < 0) {
            return containHelper(key, node.left_child);
        } else if (key.compareTo(node.key) > 0) {
            return containHelper(key, node.right_child);
        }
        return true;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key){
        return getHelper(key,root);
    }

    private V getHelper(K key,Node node){
        if(node == null){return null;}
        if(key.compareTo(node.key)<0){return getHelper(key,node.left_child);}
        else if(key.compareTo(node.key)>0){return getHelper(key,node.right_child);}
        return node.value;

    }

    /* Returns the number of key-value mappings in this map. */
    public int size(){
        if(root == null){return 0;}
        return root.height;
    };

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value){
        root = putHelper(key,value,root);
    };

    private int checkHeight(Node node){
        if(node==null){return 0;}
        return node.height;
    }
    private Node putHelper(K key,V value,Node node){
        if(node == null){return new Node(key,value);}
        if(key.compareTo(node.key)<0){node.left_child = putHelper(key,value,node.left_child);}
        else if(key.compareTo(node.key)>0){node.right_child = putHelper(key,value,node.right_child);}
        node.height = checkHeight(node.left_child) + checkHeight(node.right_child) + 1;
        return node;

    }


    public void printInOrder(){
        printHelper(root);
    }

    private void printHelper(Node node){
        if (node==null){return;}
        printHelper(node.left_child);
        System.out.println(node.key.toString()+"->"+node.value.toString());
        printHelper(node.right_child);
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet(){
        //just leave it there
        throw new UnsupportedOperationException();
    };

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key){
        //just leave it there
        throw new UnsupportedOperationException();
    };

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value){
        //just leave it there
        throw new UnsupportedOperationException();
    };

}
