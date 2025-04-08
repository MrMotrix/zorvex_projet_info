package interpreter.exceptions;

import interpreter.Token;
import interpreter.TokenType;

public class ExpectedCharacterNotFound extends SyntaxErrorException {
    private final char expectedCharacter;
    public ExpectedCharacterNotFound(int lineNumber, char expectedCharacter) {
        super(lineNumber);
        this.expectedCharacter = expectedCharacter;
    }

    @Override
    public String toString() {
        return "Caractère attendu en ligne " + lineNumber + ": " + expectedCharacter + " non trouvé."; 
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
