package interpreter.exceptions;

import interpreter.Token;
import interpreter.TokenType;

public class UnexpectedTokenException extends SyntaxErrorException {
    private final TokenType expected;
    private final Token got; 

    public UnexpectedTokenException(Token got, TokenType expected) {
        super(got.line());
        this.expected = expected;
        this.got = got;
    }

    public UnexpectedTokenException(Token got) {
        super(got.line());
        this.expected = null;
        this.got = got;
    }

    @Override
    public String toString() {
        return  "Jeton inattendu en ligne " + lineNumber + " obtenu " + got + (this.expected != null ? (" attendait " + expected) : "") ;
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
