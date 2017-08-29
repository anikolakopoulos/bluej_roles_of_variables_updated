package java_program;

import javax.swing.*;   
import javax.swing.tree.*;   
   
public class TreeExample2 {   
    public static void main(String[] args) {   
        JFrame f = new JFrame("Tree Example 2");   
           
        TreeExample1 t = new TreeExample1();   
        t.putClientProperty("JTree.lineStyle", "Angled");   
        t.expandRow(0);   
   
        f.getContentPane().add(new JScrollPane(t));   
        f.setSize(300, 300);   
        f.setVisible(true);    
           
        try {   
            for (;;) {   
                Thread.sleep(30000);   
   
                // Get TreePath for row 4   
                TreePath p = t.getPathForRow(4);   
                if (p == null) {   
                    System.out.println("Nothing on row 4!");   
                    continue;   
                }   
   
                // Print the official description of "p"   
                System.out.println("===================\n" + p);   
   
                // Now look inside   
                Object[] o = p.getPath();   
                for (int i = 0; i < o.length; i++) {   
                    System.out.println("Class: " + o[i].getClass() + "; value: " + o[i]);   
                    if (o[i] instanceof DefaultMutableTreeNode) {   
                        Object uo = ((DefaultMutableTreeNode)o[i]).getUserObject(); //%%uo%%most recent holder%%
                        if (uo != null) {   
                            System.out.println("\tUser object class: " + uo.getClass() + "; value: " + uo);   
                        }   
                    }   
                }   
            }   
        } catch (InterruptedException e) {   
        }   
    }   
   
}   