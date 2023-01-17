package main.java.calculator.node;

abstract public class Node {
    Node left;
    Node right;
    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
//    public abstract String getMatchingRegex();
    public abstract int eval();
}

