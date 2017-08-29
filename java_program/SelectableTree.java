package java_program;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.JTree.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SelectableTree extends JFrame {
    private JTree tree;
    private JButton showSelectionButton = new JButton("Show Selection");
    public static void main(String[] args) {
        new SelectableTree();
    }

    public SelectableTree() {
        super("JTree Selections");
        Container content = getContentPane();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode child;
        DefaultMutableTreeNode grandChild;
        for(int childIndex=1; childIndex<4; childIndex++) {
            child = new DefaultMutableTreeNode("Child " + childIndex);
            root.add(child);
            for(int grandChildIndex=1; grandChildIndex<6; grandChildIndex++) {
                grandChild =
                new DefaultMutableTreeNode("Grandchild " + childIndex + "." + grandChildIndex);
                child.add(grandChild);
            }
        }
        tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        content.add(new JScrollPane(tree), BorderLayout.CENTER);
        showSelectionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    TreePath[] treePaths = tree.getSelectionPaths();
                    int [] treeRows = tree.getSelectionRows();
                    for (int i = 0; i < treePaths.length; i++) {
                        DefaultMutableTreeNode Node
                        = (DefaultMutableTreeNode)treePaths[i].getLastPathComponent(); //%%Node%%most recent holder%%
                        System.out.println(Node.toString());
                    }
                    System.out.println("Before sorting");
                    for (int i = 0; i < treeRows.length; i++) {
                        TreePath treePathx = tree.getPathForRow(treeRows[i]);
                        DefaultMutableTreeNode Nodex
                        = (DefaultMutableTreeNode)treePathx.getLastPathComponent();
                        System.out.println(Nodex.toString());
                    }
                    System.out.println("After sorting");
                    java.util.Arrays.sort(treeRows);
                    for (int i = 0; i < treeRows.length; i++) {
                        TreePath treePathi = tree.getPathForRow(treeRows[i]);
                        DefaultMutableTreeNode Nodei
                        = (DefaultMutableTreeNode)treePathi.getLastPathComponent();
                        System.out.println(Nodei.toString());
                    }
                }
            });
        content.add(showSelectionButton, BorderLayout.SOUTH);
        setSize(250, 275);
        setVisible(true);
    }
}