package java_program;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

public class MainClass extends JPanel {

    public static void main(String[] args) {
        // create a hierarchy of nodes
        MutableTreeNode root = new DefaultMutableTreeNode("A"); 
        MutableTreeNode bNode = new DefaultMutableTreeNode("B");
        MutableTreeNode cNode = new DefaultMutableTreeNode("C");
        root.insert(bNode, 0);
        root.insert(cNode, 1);
        bNode.insert(new DefaultMutableTreeNode("1"), 0);
        bNode.insert(new DefaultMutableTreeNode("2"), 1);
        cNode.insert(new DefaultMutableTreeNode("1"), 0);
        cNode.insert(new DefaultMutableTreeNode("2"), 1);

        final DefaultTreeModel model = new DefaultTreeModel(root);
        final JTree tree = new JTree(model);

        final JTextField nameField = new JTextField("Z");
        final JButton button = new JButton("Add");
        button.setEnabled(false);
        button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TreePath tp = tree.getSelectionPath();
                    MutableTreeNode insertNode = (MutableTreeNode) tp.getLastPathComponent(); //%%insertNode%%most recent holder%%
                    System.out.println(tp.getLastPathComponent());
                    int insertIndex = 0;
                    if (insertNode.getParent() != null) {
                        MutableTreeNode parent = (MutableTreeNode) insertNode.getParent(); 
                        insertIndex = parent.getIndex(insertNode) + 1;
                        insertNode = parent;
                    }
                    MutableTreeNode node = new DefaultMutableTreeNode(nameField.getText());
                    model.insertNodeInto(node, insertNode, insertIndex);
                }
            });
        JPanel addPanel = new JPanel(new GridLayout(2, 1));
        addPanel.add(nameField);
        addPanel.add(button);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    TreePath tp = e.getNewLeadSelectionPath();
                    button.setEnabled(tp != null);
                }
            });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.getContentPane().add(new JScrollPane(tree));
        frame.getContentPane().add(addPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}