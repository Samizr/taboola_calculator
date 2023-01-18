package main.java.calculator;

import main.java.calculator.node.IntegerNode;
import main.java.calculator.node.Node;
import main.java.calculator.node.OperatorNode;
import main.java.calculator.node.RootNode;
import main.java.calculator.token.Token;
import main.java.calculator.token.Tokenizer;

import java.util.*;

public class Calculator {

    private final VariableStore variables;

    public Calculator() {
        variables = new VariableStore();
    }
    public void parse(String line) throws Exception {
        //TODO: enabled support for both assignable and increments.
        Tokenizer tokenizer = new Tokenizer(line);
        Node root = new Node();
        Node curr = root;
        Token token;
        while ((token = tokenizer.nextToken()) != null) {
//            If the current token is a '(', add a new node as the left child of the current node, and descend to the left child.
            if (token.isOpenBracket()) {
                Node newNode = new Node();
                curr.setLeft(newNode);
                newNode.setParent(curr);
                curr = newNode;
            }
//            If the current token is in the list ['+','-','/','*'], set the root value of the current node to the operator represented by the current token. Add a new node as the right child of the current node and descend to the right child.
            else if (token.isOperator()) {
//                IntegerNode newNode = new IntegerNode();
                Node newNode = new Node();
                curr.setToken(token);
                curr.setRight(newNode);
                newNode.setParent(curr);
                curr = newNode;
            }
//            If the current token is a number, set the root value of the current node to the number and return to the parent.

            else if (token.isEvaluable()) {
                curr.setToken(token);
                curr = curr.getParent();

                //                if (!curr.settable()) {
//                    IntegerNode newRoot = new IntegerNode();
//                    root = newRoot;
//                    curr = newRoot;
//                }

            }

            else { //Token is closing bracket
                curr = curr.getParent();
            }
        }

    }
    //TODO: Are we supporting x = -5 or y = -3 + 1
    public String getState() {
        List<String> variablesAsStrings = new ArrayList<>();
        for (String key: variables.keySet()) {
            variablesAsStrings.add(String.format("%s=%d", key, variables.get(key)));
        }
        return String.format("(%s)", String.join(",",variablesAsStrings));
    }

    public void clearState() {
        variables.clear();
    }
}