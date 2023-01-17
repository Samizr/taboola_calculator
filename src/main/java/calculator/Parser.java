package main.java.calculator;

import main.java.calculator.node.IntegerNode;
import main.java.calculator.node.Node;
import main.java.calculator.node.OperatorNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String endOfPreviousRegex = "\\G";
    private static final String emptyCharsRegex = "[\\s\\t]+";
    private static final Pattern openBracket = compileExact("\\(");
    private static final Pattern closedBracket = compileExact("\\)");
    private static final Pattern evaluable = compileExact(IntegerNode.getMatchingRegex());
    private static final Pattern operation = Pattern.compile("\\G" + OperatorNode.getMatchingRegex());;

    public static Node parse(String line, LinkedHashMap<String, Integer> variables) {
        //TODO: enabled support for both assignable and increments.
        Matcher matcher = closedBracket.matcher(line);


//        If the current token is a '(', add a new node as the left child of the current node, and descend to the left child.

//        If the current token is in the list ['+','-','/','*'], set the root value of the current node to the operator represented by the current token. Add a new node as the right child of the current node and descend to the right child.
//
//        If the current token is a number, set the root value of the current node to the number and return to the parent.
//
//        If the current token is a ')', go to the parent of the current node.

    }


    private static Pattern compileExact(String regex) {
        return Pattern.compile("%s(%s)%s".formatted(endOfPreviousRegex, regex, emptyCharsRegex));
    }
    private static Pattern openBracketOrOperationPattern() {
        String openBracket = Pattern.quote
        return Pattern.compile()
    }

    // Validation:
    // After ( --> Number, (
    // After Number --> op, ), end
    // After op --> Number, (
    // After ) --> op, ), end

    /*
    This gathers all possible token types and creates a big regex that catches them
     */
//    private static Pattern compilePatterns() {
//        StringBuilder finalRegex = new StringBuilder();
//
//    }

}
