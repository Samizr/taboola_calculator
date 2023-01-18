package main.java.calculator.token;

import main.java.calculator.CalculatorException;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tokenizer {

    private Matcher matcher;
    private String line;
    private int matchEnd;
    public Tokenizer(String line){
        this.line = line;
        this.matcher = compileAllTerminals().matcher(this.line);
        this.matchEnd = 0;
    }
    public Token nextToken() {
        if (matcher.find()) {
            MatchResult matchResultStream=matcher.toMatchResult();
            for (TokenType type : TokenType.values()) {
                String match = matcher.group(type.name());
                if (match != null) {
                    matchEnd = matcher.end();
                    return new Token(type, match);
                }
            }
        }

        else if (matchEnd != matcher.regionEnd()) {
            throw new CalculatorException("Failed to tokenize from " + line.subSequence(matchEnd, line.length()));
        }
        // End of well-tokenized line
        return null;
    }

    private static Pattern compileAllTerminals() {
        String finalRegex = Arrays.stream(TokenType.values()).map(type ->
                "\\G\\s*(?<%s>%s)\\s*".formatted(type.name(), type.getPattern())
        ).collect(Collectors.joining("|"));
        return Pattern.compile(finalRegex);
    }
}

//TODO: this is bad when line is empty


