/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg23tree;

/**
 * This is a node in a binary search tree. The links to the child nodes can
 * be either "Red" - true or "Black" - false. If there is a red link, it will
 * always be to the left, and no child of a red link will have a red link of its
 * own.
 * @author advoet
 */

public class Node {
    private final static boolean RED = true;
    private final static boolean BLACK = false;
    private int key;
    private Node left, right;
    private boolean color;                   //Color is color of link to parent.
    
    public Node(int key, boolean color){
        this.key = key;
        this.color = color;
    }
    
    protected boolean getColor(){
        if (this==null) return BLACK;
        return color;
    }
    
    // :( had to reset root to black after put
    protected void setColor(boolean color){
        this.color = color;
    }
    
    protected int getKey(){
        return key;
    }
    
    private void setKey(int key){
        this.key = key;
    }
    
    protected boolean isRed(){
        if (this == null) return BLACK; //Empty nodes are black.
        return this.getColor() == RED;
    }
    
    private void makeRed(){
        color = RED;
    }
    
    private void makeBlack(){
        color = BLACK;
    }
    
    private void takeColor(Node h){
        setColor(h.getColor());
    }
    
    protected Node getLeft(){
        return left;
    }
    protected Node getRight(){
        return right;
    }
    
    private void assignLeft(Node l){
        left = l;
    }
    
    private void assignRight(Node r){
        right = r;
    }
    
    //If a node has red child on the right, perform local operation to rebalance
    //This does NOT fix the link from the parent
    private Node rotateLeft(Node h){
        assert h.getRight().isRed();
        Node x = h.getRight();
        h.assignRight(x.getLeft()); //The left child of x becomes the right of h
        x.assignLeft(h); //THEN h becomes the left child of x
        x.takeColor(h);
        h.makeRed();
        return x;
    }
    
    //Could be necessary, exactly symmetric to above
    private Node rotateRight(Node h){
        assert h.getLeft().isRed();
        Node x = h.getLeft();
        h.assignLeft(x.getRight());
        x.assignRight(h);
        x.takeColor(h);
        h.makeRed();
        return x;
    }
    
    /*  A black node with two red children is made into a red node with two 
    *   black children. The original node is made red to preserve height
    */
    private void split(){
        assert !this.isRed();
        assert getLeft().isRed();
        assert getRight().isRed();
        makeRed();
        getLeft().makeBlack();
        getRight().makeBlack();
    }
    
    // h is the node we are comparing to
    private Node put(Node h, int key){
        if (h == null) return new Node(key, RED); //We are at the bottom, insert as red
        
        if (key<h.getKey()) h.assignLeft(put(h.left, key));
        else if (key>h.getKey()) h.assignRight(put(h.right, key));
        else if (key==h.getKey()) return h;
        
        if (h.getRight() != null && h.getRight().isRed()
                && (h.getLeft()==null || !h.getLeft().isRed())){
            h = rotateLeft(h);
        }
        if (h.getLeft().isRed() 
                && h.getLeft().getLeft() != null
                && h.getLeft().getLeft().isRed()){
            h = rotateRight(h);
        }
        if (h.getLeft().isRed() 
                && h.getRight() !=null
                && h.getRight().isRed()){
            h.split();
        }
        
        return h;
    }
    
    protected Node put(int key){
        return put(this, key);
    }
    
}


