package main;

public class Node<V,K extends Comparable<K>>{
    Node<V,K> parent;
    Node<V,K> left;
    Node<V,K> middle;
    Node<V,K> right;
    K key;
    V value;
    int size;
    Stock stock;
    public Node(K key, V value){
        this.key = key;
        this.value = value;
    }
    public Node(K key, V value, Stock st){
        this.key = key;
        this.value = value;
        this.stock = st;
    }
    public boolean isLeaf(){
        if(this.left == null && this.middle == null){
            return true;
        }
        return false;
    }
    public void setChildren(Node<V,K> left, Node<V,K> middle, Node<V,K> right){
        this.left = left;
        this.middle = middle;
        this.right = right;
        updateKey();
    }
    public void updateKey(){
        this.key = this.left.key;
        if(this.middle != null){
            this.key = this.middle.key;
        }
        if(this.right != null){
            this.key = this.right.key;
        }
    }

    public Node<V,K> getParent(){
        return this.parent;
    }
    public Node<V,K> getLeft(){
        return this.left;
    }
    public Node<V,K> getMiddle(){
        return this.middle;
    }

    public void setParent(Node<V, K> parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node<V, K> getRight() {
        return right;
    }

    public void setRight(Node<V, K> right) {
        this.right = right;
    }

    public void setLeft(Node<V, K> left) {
        this.left = left;
    }

    public void setMiddle(Node<V, K> middle) {
        this.middle = middle;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
