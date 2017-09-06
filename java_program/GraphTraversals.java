package java_program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

//@author Sada Kurapati <sadakurapati@gmail.com>
public class GraphTraversals {

    Node<String> targetNode = new Node<String>("V"); //%%targetNode%%walker%%
    Node<String> targetTreeNode = new Node<String>("D");

    public void main(String args[]) {
        //build sample graph.
        Node<String> source = getSampleGraph(); 
        Node<String> root = getSampleTree(); 
        //graphBFSByQueue(source);
        //graphBFSByLevelList(source);
        //treeBFSByQueue(root);
        //treeBFSByLevelList(root);
        //graphDFSByRecersion(source);
        //graphDFSByRecersion(source);
        //treeDFSByRecersion(root);
        treeDFSByStack(root);

    }

    public void treeDFSByStack(Node<String> source) {
        //if empty graph, return
        if (null == source) {
            return; //
        }
        Stack<Node<String>> stack = new Stack<Node<String>>(); //%%stack%%container%%
        //add source to stack
        stack.push(source);
        while (!stack.isEmpty()) {
            Node<String> currentNode = stack.pop();  //%%currentNode%%w%% 
            visitNode(currentNode);
            //check if we reached out target node
            if (currentNode.equals(targetTreeNode)) {
                return; // we have found our target node V.
            }
            //add all of unvisited nodes to stack
            for (Node<String> neighbor : currentNode.neighbors) { //%%neighbor%%most recent holder%%
                stack.push(neighbor);
            }
        }
    }

    public static void visitNode(Node<String> node) {
        System.out.printf(" %s ", node.data);
    }

    public static Node<String> getSampleGraph() {
        //building sample graph.
        Node<String> S = new Node<String>("S");
        Node<String> A = new Node<String>("A");
        Node<String> C = new Node<String>("C");
        Node<String> B = new Node<String>("B");
        Node<String> D = new Node<String>("D");
        Node<String> E = new Node<String>("E");
        Node<String> F = new Node<String>("F");
        Node<String> V = new Node<String>("V");

        S.neighbors.add(A);
        S.neighbors.add(C);

        A.neighbors.add(S);
        A.neighbors.add(B);

        B.neighbors.add(A);

        C.neighbors.add(S);
        C.neighbors.add(D);
        C.neighbors.add(E);

        D.neighbors.add(C);
        D.neighbors.add(E);
        D.neighbors.add(F);

        E.neighbors.add(C);
        E.neighbors.add(D);
        E.neighbors.add(F);
        E.neighbors.add(V);

        F.neighbors.add(D);
        F.neighbors.add(E);
        F.neighbors.add(V);

        V.neighbors.add(E);
        V.neighbors.add(F);

        return S;
    }

    public static Node<String> getSampleTree() {
        //building sample Tree.
        Node<String> A = new Node<String>("A");
        Node<String> B = new Node<String>("B");
        Node<String> C = new Node<String>("C");
        Node<String> D = new Node<String>("D");
        Node<String> E = new Node<String>("E");
        Node<String> F = new Node<String>("F");
        Node<String> G = new Node<String>("G");
        Node<String> H = new Node<String>("H");

        A.neighbors.add(B);
        A.neighbors.add(C);
        A.neighbors.add(D);

        B.neighbors.add(E);
        B.neighbors.add(F);
        B.neighbors.add(G);

        C.neighbors.add(H);
        
        return A;
    }

}

class Node<T> {

    T data;
    ArrayList<Node<T>> neighbors = null;
    boolean visited = false;

    Node(T value) {
        data = value;
        neighbors = new ArrayList<Node<T>>();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node<?> other = (Node<?>) obj;
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }
}