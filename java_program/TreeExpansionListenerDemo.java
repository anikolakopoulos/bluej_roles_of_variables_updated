package java_program;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

class MyTreeModelListener implements TreeModelListener {
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node; //%%node%%walker%%
        node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

        try {
            int index = e.getChildIndices()[0]; //%%index%%fixed value%%
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        } catch (NullPointerException exc) {
        }

        System.out.println("The user has finished editing the node.");
        System.out.println("New value: " + node.getUserObject());
        }
        
    public void treeNodesInserted(TreeModelEvent e) {
        System.out.println(e);
    }

    public void treeNodesRemoved(TreeModelEvent e) {
        System.out.println(e);
    }

    public void treeStructureChanged(TreeModelEvent e) {
        System.out.println(e);
    }
}

public class TreeExpansionListenerDemo
{
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");//%%root%%most recent holder%%
        JTree tree = new JTree(root);

        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.addTreeModelListener(new MyTreeModelListener());

        treeModel.insertNodeInto(new DefaultMutableTreeNode("A"), root, 0);

        root.add(new DefaultMutableTreeNode("B"));
        root.add(new DefaultMutableTreeNode("C"));

        JScrollPane scrollPane = new JScrollPane(tree);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);

    }
}