package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scanner {
    private final List<Token> tokens;
    private final String code;

    private int currentPos = 0;

    public static void main(String[] args) {
        var scanner = new Scanner("print 512+test-moi*256");
        scanner.scan();
        System.out.println(scanner.getTokens());

        scanner = new Scanner("print \"hello world\"");
        scanner.scan();
        System.out.println(scanner.getTokens());
    } 

    public Scanner(String code) {
        this.code = code;
        this.tokens = new ArrayList<Token>();
    }

    private void scan() {
        while (currentPos < code.length()) {
            tokens.add(scanToken());
        }
    }

    private Token scanToken() {
        // Pourrait être fait de façon plus intelligente
        // Je pense que la façon actuelle est lente

        // virer les whitespaces au début
        while (readChar() == ' ') {
            currentPos += 1;
        }

        for (TokenType tokenType : TokenType.multiCharTokenTypes()) {
            String tokenCharacters = tokenType.characters();
            String codeCharacters = readString(tokenCharacters.length());

            if (codeCharacters.equals(tokenCharacters)) {
                currentPos += codeCharacters.length();
                return new Token(tokenType, codeCharacters);
            }
        }

        char character = readChar();
        for (TokenType tokenType : TokenType.singleCharTokenTypes()) {
            if ((character+"").equals(tokenType.characters())) {
                currentPos += 1;
                return new Token(tokenType, character+"");
            }
        }

        StringBuilder tokenBuilder = new StringBuilder();
        if (Character.isDigit(character)) {
            while (currentPos < code.length() && Character.isDigit(readChar())) {
                tokenBuilder.append(readChar());
                currentPos += 1;
            }
            return new Token(TokenType.NUMBER, tokenBuilder.toString(), Integer.valueOf(tokenBuilder.toString()));
        }

        if (character == '"') {
            currentPos += 1;
            while (readChar() != '"') {
                tokenBuilder.append(readChar());
                currentPos += 1;
            }
            currentPos += 1;
            return new Token(TokenType.STRING, tokenBuilder.toString(), tokenBuilder.toString());
        }

        Set<Character> startOfNewToken = TokenType.startOfNewToken();
        startOfNewToken.add(' ');
        
        while (currentPos < code.length() && !startOfNewToken.contains(readChar())) {
            tokenBuilder.append(readChar());
            currentPos += 1;
        }
        return new Token(TokenType.IDENTIFIER, tokenBuilder.toString());
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
