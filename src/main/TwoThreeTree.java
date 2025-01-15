package main;

public class TwoThreeTree<V, K extends Comparable<K>> { // Ensuring K implements Comparable
    Node<V, K> root; // Root of the tree
    int size; // Number of elements in the tree

    // Default tree constructor
    public TwoThreeTree() {
        this.root = null; // Initialize the root of the tree as null
        this.size = 0; // Initialize the number of elements as 0
    }

    // Initializing the tree
    public void init(K rootKey, V rootValue, K leftKey, V leftValue, K middleKey, V middleValue) {
        this.root = new Node<>(rootKey, rootValue);
        System.out.println(this.root);
        Node<V, K> leftChild = new Node<>(leftKey, leftValue);
        Node<V, K> middleChild = new Node<>(middleKey, middleValue);
        root.setChildren(leftChild, middleChild,null);
        leftChild.setParent(this.root);
        System.out.println(leftChild.getParent());
        middleChild.setParent(this.root);
        this.size = 3; // Correctly set the initial size
    }
    public Node<V,K> search(Node<V,K> x , K k){
        if (x.isLeaf()){
            if (x.getKey()==k){return x;}
            else return null;
        }
        if (k.compareTo(x.getLeft().getKey()) <= 0) { return search(x.getLeft() , k);}
        else if (k.compareTo(x.getMiddle().getKey())<=0){ return search(x.getMiddle(), k);}
        else return search(x.getRight(),k);
    }



//    public Node<V,K> successor(Node<V,K> x){
//        Node<V,K> z = x.getParent();
//        Node<V,K> y;
//        while(x == z.getRight() || (z.getRight() == null && x == z.getMiddle())){
//            x = z;
//            z = z.getParent();
//        }
//        if(x == z.getLeft()){
//            y = z.getMiddle();
//        }
//        else{
//            y = z.getRight();
//        }
//        while(!y.isLeaf()){
//            y = y.getLeft();
//        }
//        if(y.getKey() < "999999999999999999999999999999999999"){
//            return y;
//        }
//        else{
//            return null;
//        }
//    }
    // Returns the rank of the leaf
    public int rank(Node<V, K> leaf) {
        int rank = 1; // Initialize rank starting from 1
        Node<V, K> y = leaf.getParent(); // Start with the parent of the given leaf

        while (y != null) {
            // Add size of left subtree if the leaf is in the middle or right
            if (leaf == y.getMiddle()) {
                rank += (y.getLeft() != null ? y.getLeft().getSize() : 0);
            } else if (leaf == y.getRight()) {
                rank += (y.getLeft() != null ? y.getLeft().getSize() : 0) +
                        (y.getMiddle() != null ? y.getMiddle().getSize() : 0);
            }

            // Move up the tree
            leaf = y;
            y = y.getParent();
        }

        return rank; // Return the calculated rank
    }


    // Insert and split logic
    public Node<V, K> insert_and_split(Node<V, K> x, Node<V, K> z) {
        Node<V, K> l = x.getLeft();
        Node<V, K> r = x.getRight();
        Node<V, K> m = x.getMiddle();
        if (r == null) { // Handle 2-node case
            if (z.getKey().compareTo(l.getKey()) < 0) {
                x.setChildren(z, l, m);
            } else if (z.getKey().compareTo(m.getKey()) < 0) {
                x.setChildren(l, z, m);
            } else {
                x.setChildren(l, m, z);
            }
            return null;
        }
        // Handle 3-node split
        Node<V, K> y = new Node<>(null,null);
        if (z.getKey().compareTo(l.getKey()) < 0) {
            x.setChildren(z, l, null);
            y.setChildren(m, r, null);
        } else if (z.getKey().compareTo(m.getKey()) < 0) {
            x.setChildren(l, z, null);
            y.setChildren(m, r, null);
        } else if (z.getKey().compareTo(r.getKey()) < 0) {
            x.setChildren(l, m, null);
            y.setChildren(z, r, null);
        } else {
            x.setChildren(l, m, null);
            y.setChildren(r, z, null);
        }
        return y;
    }

    public void insert(Node<V, K> z) {
        Node<V, K> y = this.root;
        System.out.println(y);
        System.out.println("99999989y678687678");
        while (y!=null && !y.isLeaf()) { // Traverse to the appropriate leaf

            System.out.println("--------------------------------");
            System.out.println("00000000");
            if (z.getKey().compareTo(y.getLeft().getKey()) < 0) {
                y = y.getLeft();
                System.out.println(y);
                System.out.println("---======-=-=-=-=-");
            } else if (z.getKey().compareTo(y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
                System.out.println(y);
                System.out.println("at middle");
            } else {
                y = y.getRight();
                System.out.println(y);
                System.out.println("-at right");
            }
        }
        System.out.println(y);

        assert y != null;
        Node<V,K> tt = y.getParent();
        Node<V, K> x = tt;
        assert x!=null;
        z = insert_and_split(x, z);
        while (x != this.root) {
            x = x.getParent();
            if (z != null) {
                z = insert_and_split(x, z);
            } else {
                x.updateKey();
            }
        }
        if (z != null) {
            Node<V, K> w = new Node<>(null,null);
            w.setChildren(x, z, null);
            this.root = w;
        }
        this.size++; // Increment tree size after insertion
    }
    public Node<V,K> borrow_or_merge(Node<V,K> y){
        Node<V,K> z = y.getParent();
        if(y == z.getLeft()){
            Node<V,K> x = z.getMiddle();
            if(x.getRight() != null){
                y.setChildren(y.left, x.left, null);
                x.setChildren(x.middle, x.right, null);
            }
            else{
                x.setChildren(y.getLeft(), x.getLeft(), x.getMiddle());
                y = null;
                z.setChildren(x,z.getRight(), null);
            }
            return z;
        }
        if(y == z.getMiddle()){
            Node<V,K> x = z.getLeft();
            if(x.getRight() != null){
                y.setChildren(x.getRight(), y.getLeft(), null);
                x.setChildren(x.getLeft(), x.getMiddle(), null);
            }
            else{
                x.setChildren(x.getLeft(), x.getMiddle(), y.getLeft());
                y = null;
                z.setChildren(x,z.getRight(), null);
            }
        }
        Node<V,K> x = z.getMiddle();
        if(x.getRight() != null){
            y.setChildren(x.getRight(), y.getLeft(), null);
            x.setChildren(x.getLeft(), x.getMiddle(), null);
        }
        else{
            y = null;
            z.setChildren(z.getLeft(), x, null);
        }
        return z;
    }



    public void delete(Node<V,K> x) {
        Node<V, K> y = x.getParent();
        if (x == y.getLeft()) {
            y.setChildren(y.getMiddle(), y.getRight(), null);
        }
        else if(x == y.getMiddle()){
            y.setChildren(y.getLeft(), y.getRight(), null);
        }
        else{
            y.setChildren(y.getLeft(), y.getMiddle(), null);
        }
        x = null;
        while(y != null){
            if(y.getMiddle() != null){
                y.updateKey();
                y = y.getParent();
            }
            else if(y != this.root){
                y = borrow_or_merge(y);
            }
            else{
                this.root = y.getLeft();
                y.getLeft().setParent(null);
                y = null;
            }
        }

    }


    // Get the root of the tree
    public Node<V, K> getRoot() {
        return this.root;
    }

    // Get the size of the tree
    public int getSize() {
        return this.size;
    }

    // Check if the tree is empty
    public boolean isEmpty() {
        return this.root == null;
    }
}
