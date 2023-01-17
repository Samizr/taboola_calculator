package main.java.calculator;

import java.util.regex.Pattern;

enum TokenType { //TODO: Enum or hashtable?
    // Order dictates matching priority
    // Below types have no underscores to support Regex name-catching
    POSTINC("[a-zA-Z]\\w*\\+\\+"),
    PREINC("\\+\\+[a-zA-Z]\\w*"),
    VARIABLE("[a-zA-Z]\\w*"),
    INTEGER("\\d+"),
    ASSIGN("[+-]?="),
    OPERATOR("[\\*/\\+-]"),
    OPENBRACKET("\\("),
    CLOSEDBRACKET("\\)");

    private final Pattern pattern;
    TokenType(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
public class Token {
    private final TokenType type;
    private final String value;
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return String.format("%s: %s", type, value);
    }


    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }
    public boolean isInteger() {
        return type == TokenType.INTEGER;
    }
    public boolean isOperator() {
        return type == TokenType.OPERATOR;
    }
    public boolean isAssign() {
        return type == TokenType.ASSIGN;
    }
    public boolean isPostIncrement() {
        return type == TokenType.POSTINC;
    }
    public boolean isPreIncrement() {
        return type == TokenType.PREINC;
    }
    public boolean isOpenBracket() {
        return type == TokenType.OPENBRACKET;
    }
    public boolean isClosedBracket() {
        return type == TokenType.CLOSEDBRACKET;
    }

    public String getValue() {
        return value;
    }

    public boolean isNumeric() {
        return isVariable() || isInteger() || isPreIncrement() || isPostIncrement();
    }

}


//TODO: Unit test tokenizer