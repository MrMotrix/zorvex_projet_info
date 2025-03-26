package interpreter;

public record Token(TokenType type, String lexeme, Object data, int line) {
    public String toString() {
        return type + " " + lexeme;
    }

    public TokenType type() {
        return type;
    }

    public Object data() {
        return data;
    }

    public String lexeme() {
        return lexeme;
    }
}
