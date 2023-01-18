package main.java.calculator;

import main.java.calculator.node.Node;
import main.java.calculator.token.Token;
import main.java.calculator.token.Tokenizer;

import java.util.*;

public class Calculator {
    private Tokenizer tokenizer;
    private final VariableStore variables;

    public Calculator() {
        variables = new VariableStore();
    }

    public void calculate(String line) throws Exception {
        tokenizer = new Tokenizer(line);

        String leftVar =  tokenizer.nextToken().getValue();
        String assignType =  tokenizer.nextToken().getValue();

        Node parseTree = parse();
        variables.assign(leftVar, assignType, parseTree.eval(variables));
    }
    private Node parse() throws Exception {
        Node root = new Node();
        Node curr = root;
        Token token;
        while ((token = tokenizer.nextToken()) != null) {
            if (token.isOperator()) {
                Token currToken = curr.getToken();
                if (currToken == null) {
                    curr.setToken(token);
                } else if (token.isPriorityOperator()) {
                    while (curr != null && curr.getToken().isPriorityOperator()) {
                        curr = curr.getParent();
                    }
                    if (curr != null) {
                        Node node = new Node(curr.getRight(), null, token);
                        curr.setRight(node);
                        curr = node;
                    } else {
                        Node node = new Node(root, null, token);
                        curr = root = node;
                    }
                } else {
                    Node node = new Node(root, null, token);
                    curr = root = node;
                }
            }

            else if (token.isEvaluable()) {
                // Open brackets open new trees!
                Node node = token.isOpenBracket() ? parse() : new Node(token);

                if (curr.getLeft() == null) {
                    curr.setLeft(node);
                }
                else if (curr.getRight() == null) {
                    curr.setRight(node);
                }
                else {
                    throw new CalculatorException("Two consecutive evaluables", token.getValue());
                }
            }

            else { //Token is closing bracket
                return root;
            }
        }
        return root;
    }
    //TODO: should support x = -5 or y = -3 + 1
    public String getState() {
        List<String> variablesAsStrings = new ArrayList<>();
        for (String key: variables.keySet()) {
            variablesAsStrings.add(String.format("%s=%d", key, variables.get(key)));
        }
        return String.format("(%s)", String.join(",",variablesAsStrings));
    }

}