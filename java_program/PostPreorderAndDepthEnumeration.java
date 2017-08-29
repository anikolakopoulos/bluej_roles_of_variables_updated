package java_program;

import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class PostPreorderAndDepthEnumeration {
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode mercury = new DefaultMutableTreeNode("Mercury");
        root.add(mercury);
        DefaultMutableTreeNode venus = new DefaultMutableTreeNode("Venus");
        root.add(venus);
        DefaultMutableTreeNode mars = new DefaultMutableTreeNode("Mars");
        root.add(mars);
        JTree tree = new JTree(root);

        JScrollPane scrollPane = new JScrollPane(tree);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);

        Enumeration e = root.preorderEnumeration(); //%%e%%most recent holder%%
        while(e.hasMoreElements()){
            System.out.println(e.nextElement());
        }

        e = root.postorderEnumeration();
        while(e.hasMoreElements()){
            System.out.println(e.nextElement());
        }

        e = root.depthFirstEnumeration();
        while(e.hasMoreElements()){
            System.out.println(e.nextElement());
        }
    }

}




