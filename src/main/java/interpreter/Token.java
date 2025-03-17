package interpreter;

public class Token {
    
    private final TokenType type;
    private final String lexeme;
    private final Object data; // très dangereux
    
    public Token(TokenType type, String lexeme) {
        this(type, lexeme, null);
    }

    public Token(TokenType type, String lexeme, Object data) {
        this.lexeme = lexeme;
        this.type = type;
        this.data = data;
    }

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
