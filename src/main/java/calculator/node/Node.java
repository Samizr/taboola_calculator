package main.java.calculator.node;

import main.java.calculator.VariableStore;
import main.java.calculator.token.Token;

public class Node {
    Node left;
    Node right;
    Node parent;
    Token token;
    public Node(Node left, Node right, Node parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public Node () {
        this(null, null, null);
    }
//    abstract public Integer eval();

//    abstract public void setValue (String string);
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
     }

     public void setRight(Node right) {
         this.right = right;
     }

     public void setParent(Node parent) {
         this.parent = parent;
     }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int eval(VariableStore variables) {
        if (token.isOperator()) {
            return evalExpression();
        }
        return evalNumeric(variables);
    }

    private Integer evalExpression() {
        switch (token.getValue()) {
            case "*" -> {
                return left.eval() * right.eval();
            }
            case "/" -> {
                return left.eval() / right.eval();
            }
            case "+" -> {
                return left.eval() + right.eval();
            }
            case "-" -> {
                return left.eval() - right.eval();
            }
            default -> throw new IllegalStateException("Expected operator but got: " + token.getValue());

        }
    }

    private Integer evalNumeric(VariableStore variables) {
        String tokenValue = token.getValue();
        if (token.isInteger()) {
            return Integer.valueOf(tokenValue);
        } else if (token.isVariable()) {
            return variables.get(tokenValue);
        } else if (token.isPreInc()) {
            String var = tokenValue.replaceAll("\\+\\+","");
            return variables.addToVariable(var, 1);
        } else if (token.isPreDec()) {
            String var = tokenValue.replaceAll("\\+\\+","");
            return variables.subtractFromVariable(var, 1);
        } else if (token.isPostInc()) {
            String var = tokenValue.replaceAll("\\+\\+","");
            Integer returnValue = variables.get(var);
            variables.addToVariable(var, 1);
            return returnValue;
        } else {//if (token.isPostInc()) {
            String var = tokenValue.replaceAll("\\+\\+","");
            Integer returnValue = variables.get(var);
            variables.subtractFromVariable(var, 1);
            return returnValue;
        }
    }
}

