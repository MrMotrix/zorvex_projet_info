package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import interpreter.exceptions.ExpectedCharacterNotFound;
import interpreter.exceptions.SyntaxErrorException;
import interpreter.exceptions.UnexpectedTokenException;

public class Scanner {
    private final List<Token> tokens;
    private final String code;

    private int currentPos = 0;
    private int currentLine = 1;

    public Scanner(String code) {
        this.code = code;
        this.tokens = new ArrayList<Token>();
    }

    public static List<Token> tokenize(String input) throws SyntaxErrorException {
        Scanner scanner = new Scanner(input);
        scanner.scan();
        return scanner.getTokens();
    }

    private void scan() throws SyntaxErrorException {
        while (currentPos < code.length()) {
            tokens.add(scanToken());
        }
    }

    private Token scanToken() throws SyntaxErrorException {
        // Pourrait être fait de façon plus intelligente
        // Je pense que la façon actuelle est lente

        // virer les whitespaces au début
        int line = currentLine;
        while (readChar() == ' ' || readChar() == '\t') 
            currentPos += 1;
        

        for (TokenType tokenType : TokenType.multiCharTokenTypes()) {
            String tokenCharacters = tokenType.characters();
            String codeCharacters = readString(tokenCharacters.length());

            if (codeCharacters.equals(tokenCharacters)) {
                currentPos += codeCharacters.length();
                return new Token(tokenType, codeCharacters, null, line);
            }
        }
        
        char character = readChar();
        if (character == '\n')
            currentLine += 1;
        for (TokenType tokenType : TokenType.singleCharTokenTypes()) {
            if ((character+"").equals(tokenType.characters())) {
                currentPos += 1;
                return new Token(tokenType, character+"", null, line);
            }
        }

        StringBuilder tokenBuilder = new StringBuilder();
        if (Character.isDigit(character)) {
            while (currentPos < code.length() && Character.isDigit(readChar())) {
                tokenBuilder.append(readChar());
                currentPos += 1;
            }
            return new Token(TokenType.NOMBRE, tokenBuilder.toString(), Integer.valueOf(tokenBuilder.toString()), line);
        }

        if (character == '"') {
            currentPos += 1;
            while (currentPos < code.length() && readChar() != '"') {
                tokenBuilder.append(readChar());
                currentPos += 1;
            }

            if (currentPos == code.length()) {
                throw new ExpectedCharacterNotFound(line, '"');
            }
            currentPos += 1;
            return new Token(TokenType.CHAINE, tokenBuilder.toString(), tokenBuilder.toString(), line);
        }

        Set<Character> startOfNewToken = TokenType.startOfNewToken();
        startOfNewToken.add(' ');
        
        while (currentPos < code.length() && !startOfNewToken.contains(readChar())) {
            tokenBuilder.append(readChar());
            currentPos += 1;
        }
        return new Token(TokenType.IDENTIFIANT, tokenBuilder.toString(), null, line);
    }
    public List<Token> getTokens() {
        return tokens;
    } 

    private String readString(int length) {
        return code.substring(currentPos, currentPos + length <= code.length() ? currentPos+length : code.length());
    }

    private Character readChar() {
        return code.charAt(currentPos);
    }
}
