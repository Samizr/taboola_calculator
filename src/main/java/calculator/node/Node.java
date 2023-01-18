package main.java.calculator.node;

import main.java.calculator.VariableStore;
import main.java.calculator.token.Token;

public class Node {
    Node left;
    Node right;
    Node parent;
    Token token;

    public Node () {
        left = right = parent = null;
        token = null;
    }

    public Node(Token token) {
        this();
        this.token=token;
    }

    public Node(Node left, Node right, Token token) {
        setLeft(left);
        setRight(right);
        setToken(token);
        parent = null;
    }

     public Node getLeft() {
         return left;
     }

     public Node getRight() {
         return right;
     }

     public Node getParent() {
         return parent;
     }

     public void setLeft(Node left) {
         this.left = left;
         if (left != null) {
             left.parent = this;
         }
     }

     public void setRight(Node right) {
         this.right = right;
         if (right != null) {
             right.parent = this;
         }
     }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int eval(VariableStore variables) {
        if (token == null) {
            return left.eval(variables);
        }
        if (token.isOperator()) {
            return evalExpression(variables);
        }
        return evalNumeric(variables);
    }


    public boolean hasSons(){
        return left != null || right != null;
    }
    private Integer evalExpression(VariableStore variables) {
        switch (token.getValue()) {
            case "*" -> {
                return left.eval(variables) * right.eval(variables);
            }
            case "/" -> {
                return left.eval(variables) / right.eval(variables);
            }
            case "+" -> {
                return left.eval(variables) + right.eval(variables);
            }
            case "-" -> {
                return left.eval(variables) - right.eval(variables);
            }
            default -> throw new IllegalStateException("Expected operator but got: " + token.getValue());

        }
    }

    private Integer evalNumeric(VariableStore variables) {
        final String increment = "\\+\\+";
        String tokenValue = token.getValue();
        if (token.isInteger()) {
            return Integer.valueOf(tokenValue);
        } else if (token.isVariable()) {
            return variables.get(tokenValue);
        } else if (token.isPreInc()) {
            String var = tokenValue.replaceAll(increment,"");
            return variables.addToVariable(var, 1);
        } else if (token.isPreDec()) {
            String var = tokenValue.replaceAll(increment,"");
            return variables.subtractFromVariable(var, 1);
        } else if (token.isPostInc()) {
            String var = tokenValue.replaceAll(increment,"");
            Integer returnValue = variables.get(var);
            variables.addToVariable(var, 1);
            return returnValue;
        } else {//if (token.isPostInc()) {
            String var = tokenValue.replaceAll(increment,"");
            Integer returnValue = variables.get(var);
            variables.subtractFromVariable(var, 1);
            return returnValue;
        }
    }
}

