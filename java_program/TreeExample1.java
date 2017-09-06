package java_program;

import javax.swing.*;   
import javax.swing.tree.*;   
   
public class TreeExample1 extends JTree {   
    public TreeExample1() {        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(); //%%rootNode%%container%%  
        DefaultMutableTreeNode apolloNode =    
            new DefaultMutableTreeNode("Apollo");   
        rootNode.add(apolloNode);   
           
        DefaultMutableTreeNode skylabNode = new DefaultMutableTreeNode("Skylab");   
        rootNode.add(skylabNode);   
   
        DefaultMutableTreeNode n = new DefaultMutableTreeNode("11"); //%%n%%container%%
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("Neil Armstrong"));   
        n.add(new DefaultMutableTreeNode("Buzz Aldrin"));   
        n.add(new DefaultMutableTreeNode("Michael Collins"));   
   
        n = new DefaultMutableTreeNode("12");   
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("Pete Conrad"));   
        n.add(new DefaultMutableTreeNode("Alan Bean"));   
        n.add(new DefaultMutableTreeNode("Richard Gordon"));   
   
        n = new DefaultMutableTreeNode("13");   
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("James Lovell"));   
        n.add(new DefaultMutableTreeNode("Fred Haise"));   
        n.add(new DefaultMutableTreeNode("Jack Swigert"));   
   
        n = new DefaultMutableTreeNode("14");   
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("Alan Shephard"));   
        n.add(new DefaultMutableTreeNode("Edgar Mitchell"));   
        n.add(new DefaultMutableTreeNode("Stuart Roosa"));   
           
        n = new DefaultMutableTreeNode("15");   
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("Dave Scott"));   
        n.add(new DefaultMutableTreeNode("Jim Irwin"));   
        n.add(new DefaultMutableTreeNode("Al Worden"));   
   
        n = new DefaultMutableTreeNode("16");   
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("John Young"));   
        n.add(new DefaultMutableTreeNode("Charlie Duke"));   
        n.add(new DefaultMutableTreeNode("Ken Mattingly"));   
   
        n = new DefaultMutableTreeNode("17");   
        apolloNode.add(n);   
        n.add(new DefaultMutableTreeNode("Eugene Cernan"));   
        n.add(new DefaultMutableTreeNode("Harrison Schmidt"));   
        n.add(new DefaultMutableTreeNode("Ron Evans"));   
   
        n = new DefaultMutableTreeNode("2");   
        skylabNode.add(n);   
        n.add(new DefaultMutableTreeNode("Pete Conrad"));   
        n.add(new DefaultMutableTreeNode("Joseph Kerwin"));   
        n.add(new DefaultMutableTreeNode("Paul Weitz"));   
   
        n = new DefaultMutableTreeNode("3");   
        skylabNode.add(n);   
        n.add(new DefaultMutableTreeNode("Alan Bean"));   
        n.add(new DefaultMutableTreeNode("Owen Garriott"));   
        n.add(new DefaultMutableTreeNode("Jack Lousma"));   
           
        n = new DefaultMutableTreeNode("4");   
        skylabNode.add(n);   
        n.add(new DefaultMutableTreeNode("Gerald Carr"));   
        n.add(new DefaultMutableTreeNode("Edward Gibson"));   
        n.add(new DefaultMutableTreeNode("William Pogue"));   
   
        this.setModel(new DefaultTreeModel(rootNode));   
    }   
   
    public static void main(String[] args) {   
        JFrame f = new JFrame("Tree Example 1");   
           
        TreeExample1 t = new TreeExample1();           
        t.putClientProperty("JTree.lineStyle", "Angled");   
        t.expandRow(0);   
   
        f.getContentPane().add(new JScrollPane(t));   
        f.setSize(300, 300);   
        f.setVisible(true);        
    }   
   
}   