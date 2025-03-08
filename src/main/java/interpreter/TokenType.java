package interpreter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum TokenType {
    PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/"), EQUAL("=="), GREATER_THAN(">"),
    LESS_THAN("<"), GREATER_EQUAL(">="), LESS_EQUAL("<="), NOT("!"), 
    ASSIGN("<-"), LEFT_PAREN("("), RIGHT_PAREN(")"), 
    IF("if"), FOR("for"), RETURN("return"), PRINT("print"),
    NUMBER(),
    STRING(),
    IDENTIFIER();

    private TokenType(String characters) {
        this.characters = characters;
        this.length = characters.length();
    }

    private TokenType() {
        this.characters = "";
        this.length = -1;
    }

    public static List<TokenType> multiCharTokenTypes() {
        return Arrays.stream(TokenType.values())
        .filter(x -> x.length > 1)
        .toList();
    }

    public static List<TokenType> singleCharTokenTypes() {
        return Arrays.stream(TokenType.values())
            .filter(x -> x.length == 1)
            .toList();
    }

    public static Set<Character> startOfNewToken() {
        return new HashSet<>(singleCharTokenTypes().stream().map(x -> x.characters().charAt(0)).toList());
    }

    public String characters() {
        return this.characters;
    }

    private final String characters;
    private final int length;
}
