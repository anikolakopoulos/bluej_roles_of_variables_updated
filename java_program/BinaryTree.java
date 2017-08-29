package java_program;

import java.util.Stack;  

public class BinaryTree {  

    class TreeNode  
    {  
        int data;  //%%data%%gatherer%%
        TreeNode left;  //%%left%%walker%%
        TreeNode right;  
        TreeNode(int data)  
        {  
            this.data = data;  
        }  
    }  

    // Recursive Solution  
    public void postOrder(TreeNode root) {  
        if(root !=  null) {  
            postOrder(root.left);  
            postOrder(root.right);  
            //Visit the node by Printing the node data    
            System.out.printf("%d ",root.data);  
        }  
    }  

    // Iterative solution  
    public void postorderIter(TreeNode root)
    {  
        if(root == null) return;  

        Stack<TreeNode> s = new Stack<TreeNode>( );  //%%s%%container%%
        TreeNode current = root; //%%current%%walker%%

        while(true) {  

            if(current != null) {  
                if(current.right != null)   
                    s.push(current.right);  
                s.push(current);  
                current = current.left;  
                continue;  
            }  

            if(s.isEmpty( ))   
                return;  
            current = s.pop( );  

            if(current.right != null && ! s.isEmpty( ) && current.right == s.peek( )) {  
                s.pop( );  
                s.push( current );  
                current = current.right;  
            } else {  
                System.out.print( current.data + " " );  
                current = null;  
            }  
        }  
    }  

}
