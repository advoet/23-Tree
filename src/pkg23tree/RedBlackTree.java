/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg23tree;

/**
 * Implementation of a 2-3 tree via left leaning Red/Black tree. 
 * @author advoet
 */
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class RedBlackTree {
    /**
     * @param args the command line arguments
     */
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    
    public RedBlackTree(){
    }
    
    protected Node getRoot(){
        return root;
    }
    
    private void initializeRoot(int key){
        this.root = new Node(key, false);
    }
    
    private void setRoot(Node newRoot){
        this.root = newRoot;
    }
    
    //prints the tree depth first, should return an ordered list of numbers
    private void depthPrint(Node node){
        if (node == null) return;
        depthPrint(node.getLeft());
        System.out.print("/");
        System.out.print(node.getKey());
        System.out.print("\\");
        depthPrint(node.getRight());
    }
    
    //I would like this to print the tree with a new line for each depth
    // such that red connections don't count and are printed next to each other
    private void breadthPrint(){
        Queue<Node> q = new LinkedList<>();
        if (root==null) return;
        
        //Root could have a red child, which we treat as on the same level
        //Probably should have designed tree with this in mind
        /*
        *
        * We declared it was left leaning, so it would have made sense 
        * to call PARENT nodes red to avoid extra fn calls
        *
        */
        if (root.getLeft() != null && root.getLeft().isRed()){
            q.add(root.getLeft());
        }
        q.add(this.getRoot());
        
        
        int levelLength = 1;
        int remaining = 1;
        int redCount = 0;
        
        while (!q.isEmpty()){
            if (remaining == 0){
                levelLength = 2*levelLength + redCount;
                remaining = levelLength;
                System.out.println();
            }
            
            Node n = q.remove();
            //Print red pairs as #-#
            if (n.isRed()){
                redCount++;
                System.out.print("[" + n.getKey() + ",");
                //Red ones don't drop the count
                addBlackLeft(q, n);
                n = q.remove(); //This is the partner key
                System.out.print(n.getKey()+ "]  ");
                remaining--;
                //Should extend Queue or add a good way to check grandchildren
                if(n.getRight()!=null){
                    if(n.getRight().getLeft()!=null && n.getRight().getLeft().isRed()){
                        q.add(n.getRight().getLeft());
                    }
                    q.add(n.getRight());
                }
            }else{
                //The red ones show up immediately before their partners
                //and both get dealt with in the previous case. So this case
                //is for a black node whose left child is black or null
                System.out.print(n.getKey()+ "  ");
                remaining--;
                addBlackLeft(q,n);
            }
        }
    }
    
    //We have just scanned and printed n. We know it has a black left child
    //and wish to add its children to the q appropriately.
    private void addBlackLeft(Queue<Node> q, Node n){
        assert !n.getLeft().isRed();
        if (n.getRight() != null){
            //Right is nonempty, left is nonempty by balanced
            //Need to check if left grandchild is red
            if (n.getLeft().getLeft() != null && n.getLeft().getLeft().isRed()){
                q.add(n.getLeft().getLeft());
            }
            q.add(n.getLeft());
            //Check if right grandchild is red
            if (n.getRight().getLeft() != null && n.getRight().getLeft().isRed()){
                q.add(n.getRight().getLeft());
            }
            q.add(n.getRight());
        }
        // return q;    //Unnecessary because of Java reference passing
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        RedBlackTree rbt = new RedBlackTree();
        rbt.console();
    }
    
    private void console(){
        Scanner keyboard = new Scanner(System.in);
        
        int input;
        do{
            System.out.println("Press 1 to insert");
            System.out.println("Press 2 to print DFS");
            System.out.println("Press 3 to print BFS");
            System.out.println("Press 4 to clear tree");
            System.out.println("Press 5 to quit");
        
            input = keyboard.nextInt();
            
            switch (input){
                case 1:
                    System.out.println("Type a number to insert, or "
                            + "type 9999 to exit");
                    int cmd = keyboard.nextInt();
                    
                    if (cmd == 9999) break;
                    
                    if (getRoot()==null) initializeRoot(cmd);
                    else{
                        setRoot(getRoot().put(cmd));
                        root.setColor(BLACK);
                    }
                    
                    break;
                case 2:
                    if (root == null) System.out.println("The tree is empty.");
                    else{
                        System.out.println("PRINTING");
                        depthPrint(root);
                        System.out.println();
                    }
                    break;
                case 3:
                    if (root == null) System.out.println("The tree is empty.");
                    else{
                        System.out.println("PRINTING");
                        breadthPrint();
                        System.out.println();
                    }
                    break;
                case 4:
                    root = null;
                    System.out.println("The tree is now empty");
                    break;
                case 5: 
                    System.out.println("TERMINATING");
                    break;
                default:
                    System.out.println("Not a valid input.");
            }
        }while (input!=5);
    }
    
}
