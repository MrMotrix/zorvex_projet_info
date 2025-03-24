package interpreter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.sun.org.apache.xpath.internal.compiler.Token;

public class ScannerTest {

    @Test
    void testStings() {
       Scanner scanner = new Scanner("\"hello\" \"world\\\"some\\\"thing\"");
        List<Token> tokens = scanner.tokenize(input);

        assertEquals(TokenType.STRING, tokens.get(0).getType());
        assertEquals("hello", tokens.get(0).getLexeme());

        assertEquals(TokenType.STRING, tokens.get(1).getType());
        assertEquals("world\"some\"thing", tokens.get(1).getLexeme());


    }

    @Test
    void testNumbers() {
        Scanner scanner = new Scanner("123 3.14");
        List<Token> tokens = scanner.tokenize(input);

        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals(123, tokens.get(0).getLexeme());

        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals(3.14, tokens.get(1).getLexeme());
    }


    @Test
    void testKeywords() {
        Scanner scanner = new Scanner("if else while return");
        List<Token> tokens = scanner.tokenize(input);

        assertEquals(TokenType.IF, tokens.get(0).getType());
        assertEquals(TokenType.ELSE, tokens.get(1).getType());
        assertEquals(TokenType.WHILE, tokens.get(2).getType());
        assertEquals(TokenType.RETURN, tokens.get(3).getType());
    }

    @Test
    void testSymbolsAndOperators() {
        Scanner scanner = new Scanner("= + - * / == != < <= > >= ( ) { } ; ,");
        List<Token> tokens = scanner.tokenize(input);

        assertEquals(TokenType.ASSIGN, tokens.get(0).getType());
        assertEquals(TokenType.PLUS, tokens.get(1).getType());
        assertEquals(TokenType.MINUS, tokens.get(2).getType());
        assertEquals(TokenType.MULTIPLY, tokens.get(3).getType());
        assertEquals(TokenType.DIVIDE, tokens.get(4).getType());
        assertEquals(TokenType.EQUAL, tokens.get(5).getType());
        assertEquals(TokenType.NOT_EQUAL, tokens.get(6).getType());
        assertEquals(TokenType.LESS, tokens.get(7).getType());
        assertEquals(TokenType.LESS_EQUAL, tokens.get(8).getType());
        assertEquals(TokenType.GREATER, tokens.get(9).getType());
        assertEquals(TokenType.GREATER_EQUAL, tokens.get(10).getType());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(11).getType());
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(12).getType());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(13).getType());
        assertEquals(TokenType.RIGHT_BRACE, tokens.get(14).getType());
        assertEquals(TokenType.SEMICOLON, tokens.get(15).getType());
        assertEquals(TokenType.COMMA, tokens.get(16).getType());
    }

    @Test
    void testIdentifiers(){
        Scanner scanner = new Scanner("variable x1 _bar");
        List<Token> tokens = scanner.tokenize(input);

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("variable", tokens.get(0).getLexeme());

        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("x1", tokens.get(1).getLexeme());

        assertEquals(TokenType.IDENTIFIER, tokens.get(2).getType());
        assertEquals("_bar", tokens.get(2).getLexeme());
    }
    

    // ici pas mal de choses peuvent mal se passer clairement
    // faut faire attention avec les string, les identifiants, les espaces et autres trucs du genre
    // les nombres aussi c'est important
}

