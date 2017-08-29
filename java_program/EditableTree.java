package java_program;

/**
 * Terms of use:  You may use or modify this code for CSE132 assignments, but
 * you must provide full documentation according to CSE132 documentation standards.
 * 
 * @author Ken Goldman, March 2006
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

public class EditableTree extends JTree {
    private static final long serialVersionUID = 1L;
    static final int OFFSET = 5;
    static final Cursor USUAL_CURSOR =
        Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    static final Cursor ACCEPT_CURSOR = createCursor("accept32x32.gif",16,0);
    static final Cursor REJECT_CURSOR = createCursor("reject32x32.gif",16,0);
    static final Font FONT = new Font("helvetica", Font.BOLD, 18);

    static Cursor createCursor(String filename, int x, int y) {
        Image img = (new ImageIcon(filename)).getImage();
        return Toolkit.getDefaultToolkit().createCustomCursor(img,new Point(x,y),filename);
    }

    public EditableTree(MutableTreeNode root) {
        super(root);
        setFont(FONT);
        new TreeEditor(this);
    }

    static class TreeEditor extends MouseAdapter implements MouseMotionListener {
        JTree tree; //%%tree%%container%%
        DefaultTreeModel model;
        TreePath mouseSelection, dropTarget;
        JLabel draggingFeedback = new JLabel("");
        JPopupMenu popup;
        AbstractAction cut;

        TreeEditor(JTree tree) {
            this.tree = tree;
            model = (DefaultTreeModel) tree.getModel();
            tree.addMouseListener(this);
            tree.addMouseMotionListener(this);
            draggingFeedback.setVisible(false);
            draggingFeedback.setFont(tree.getFont());
            tree.add(draggingFeedback);
            tree.setEditable(true);  // allows node names to be edited within the tree
        }

        public void mousePressed(MouseEvent me) {
            if (handlePopupTrigger(me))
                return;
            Point p = me.getPoint();
            mouseSelection = tree.getPathForLocation(p.x,p.y);
            if (mouseSelection != null) {
                draggingFeedback.setText(mouseSelection.getLastPathComponent().toString());
                draggingFeedback.setLocation(p.x+OFFSET,p.y);
                draggingFeedback.setVisible(true);
                draggingFeedback.setSize(draggingFeedback.getPreferredSize());
            }
        }

        public void mouseDragged(MouseEvent me) {
            if (draggingFeedback.isVisible()) {
                Point p = me.getPoint();
                dropTarget  = tree.getPathForLocation(p.x,p.y);
                draggingFeedback.setLocation(p.x+OFFSET,p.y);
                if (isDropLegal()) {
                    tree.setCursor(ACCEPT_CURSOR);
                    draggingFeedback.setForeground(Color.GREEN);
                } else {
                    tree.setCursor(REJECT_CURSOR);
                    draggingFeedback.setForeground(Color.RED);
                }
            }
        }

        public void mouseReleased(MouseEvent me) {
            draggingFeedback.setVisible(false);
            tree.setCursor(USUAL_CURSOR);
            if (handlePopupTrigger(me) || mouseSelection == null)
                return;
            if (isDropLegal()) {
                Node srcs = (Node) mouseSelection.getLastPathComponent();
                model.removeNodeFromParent(srcs);
                Node dest = (Node) dropTarget.getLastPathComponent();
                model.insertNodeInto(srcs, dest, dest.getChildCount());

                //tree.expandPath(dropTarget);
                //tree.scrollPathToVisible(new TreePath(src.getPath()));
                tree.setSelectionPath(new TreePath(srcs.getPath()));
            }
            mouseSelection = null;
        }

        public boolean isDropLegal() {
            return mouseSelection != null && dropTarget != null &&
                // don't drop on self (makes no sense, would create a cycle)
            dropTarget != mouseSelection &&
                // don't drop on a descendant (would create a cycle or sever the tree)
            !mouseSelection.isDescendant(dropTarget) &&
                // don't drop on the immediate parent (since already there)
            !mouseSelection.getParentPath().equals(dropTarget);
        }

        public void mouseMoved(MouseEvent arg0) { }

        boolean handlePopupTrigger(MouseEvent me) {
            if (me.isPopupTrigger()) {			
                Point p = me.getPoint();
                mouseSelection = tree.getPathForLocation(p.x,p.y);
                if (mouseSelection != null) {
                    showPopup(p.x,p.y);
                }
                return true;
            }
            return false;
        }

        void showPopup(int x, int y) {
            if (popup == null) {
                popup = new JPopupMenu();
                popup.add(new AbstractAction("new") {
                        public void actionPerformed(ActionEvent ae) {
                            Node srcs = (Node) mouseSelection.getLastPathComponent(); 
                            Node child = new Node("new",srcs);
                            model.insertNodeInto(child, srcs, srcs.getChildCount()-1);
                            tree.setSelectionPath(new TreePath(child.getPath()));
                        }
                    });
                popup.add(cut = new AbstractAction("cut") {
                        public void actionPerformed(ActionEvent ae) {
                            Node src = (Node) mouseSelection.getLastPathComponent(); //%%src%%most recent holder%%
                            if (src.getParent() != null)
                                model.removeNodeFromParent(src);	
                        }		
                    });
            }
            cut.setEnabled(((Node) mouseSelection.getLastPathComponent()).getParent() != null);
            popup.show(tree,x,y);
        }
    }

    public static void main(String[] args) {
        Node root = new Node("root",null);
        new Node("aa", new Node("a",root));
        new Node("bb", new Node("b",root));
        final JTree tree = new EditableTree(root);
        tree.setEditable(true);
        JFrame f = new JFrame("MyTree");
        f.add(new JScrollPane(tree));
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static class Node extends DefaultMutableTreeNode {
        public Node(Object contents, Node parent) {
            super(contents);
            if (parent != null) {
                parent.add(this);
                setParent(parent);
            }
        }

        public void setUserObject(Object x) {
            super.setUserObject(x);
            System.out.println("Node name changed to " + x);
        }
    }
}