package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scanner {
    private final List<Token> tokens;
    private final String code;

    private int currentPos = 0;

    public static void main(String[] args) {
        String expr = "8*20/5+2*3";
        System.out.println(expr);
        var scanner = new Scanner(expr);
        scanner.scan();
        System.out.println(scanner.getTokens());
        var parser = new Parser(scanner.getTokens());
        Expression result = parser.parse();
        System.out.println(result.printValue());
        System.out.println(result.value(new Context()));
        
        scanner = new Scanner("5+42*2+\"hello world\"");
        scanner.scan();
        System.out.println(scanner.getTokens());


        BinaryOperator plus = new UBinaryOperator(new Token(TokenType.PLUS, "+"));
        BinaryOperator minus = new UBinaryOperator(new Token(TokenType.MOINS, "-"));
        BinaryOperator times = new UBinaryOperator(new Token(TokenType.FOIS, "*"));
        BinaryOperator divided = new UBinaryOperator(new Token(TokenType.DIVISE, "/"));

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
            return new Token(TokenType.NOMBRE, tokenBuilder.toString(), Integer.valueOf(tokenBuilder.toString()));
        }

        if (character == '"') {
            currentPos += 1;
            while (readChar() != '"') {
                tokenBuilder.append(readChar());
                currentPos += 1;
            }
            currentPos += 1;
            return new Token(TokenType.CHAINE, tokenBuilder.toString(), tokenBuilder.toString());
        }

        Set<Character> startOfNewToken = TokenType.startOfNewToken();
        startOfNewToken.add(' ');
        
        while (currentPos < code.length() && !startOfNewToken.contains(readChar())) {
            tokenBuilder.append(readChar());
            currentPos += 1;
        }
        return new Token(TokenType.IDENTIFIANT, tokenBuilder.toString());
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
