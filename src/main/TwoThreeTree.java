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
        Node<V, K> leftChild = new Node<>(leftKey, leftValue);
        Node<V, K> middleChild = new Node<>(middleKey, middleValue);
        root.setChildren(leftChild, middleChild,null);
        leftChild.setParent(this.root);
        middleChild.setParent(this.root);
        this.size = 3; // Correctly set the initial size
    }

    // Returns the rank of the leaf
    public int rank(Node<V, K> leaf) {
        int rank = 1;
        Node<V, K> y = leaf.getParent();
        while (y != null) {
            if (leaf == y.getMiddle()) {
                rank = rank + (y.getLeft() != null ? y.getLeft().getSize() : 0);
            } else if (leaf == y.getRight()) {
                rank = rank + (y.getLeft() != null ? y.getLeft().getSize() : 0) +
                        (y.getMiddle() != null ? y.getMiddle().getSize() : 0);
            }
            leaf = y;
            y = y.getParent();
        }
        return rank;
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
        while (!y.isLeaf()) { // Traverse to the appropriate leaf
            if (z.getKey().compareTo(y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if (z.getKey().compareTo(y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            } else {
                y = y.getRight();
            }
        }
        Node<V, K> x = y.getParent();
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
                //delete y
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
                //delete y
                z.setChildren(x,z.getRight(), null);
            }
        }
        y = z.getRight();//$$$$$$
        Node<V,K> x = z.getMiddle();
        if(x.getRight() != null){
            y.setChildren(x.getRight(), y.getLeft(), null);
            x.setChildren(x.getLeft(), x.getMiddle(), null);
        }
        else{
            //delete y
            z.setChildren(z.getLeft(), x, null);
        }
        return z;
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
