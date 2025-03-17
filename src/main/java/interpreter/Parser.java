package interpreter;

import java.util.List;
import java.util.Set;

import interpreter.expression.BinaryOperation;
import interpreter.expression.Expression;
import interpreter.expression.Grouping;
import interpreter.expression.Literal;
import interpreter.expression.UBinaryOperator;
import interpreter.expression.UUnaryOperator;
import interpreter.expression.UnaryOperation;
import interpreter.expression.Variable;

// expression     → equality ;
// equality       → comparison ( ( "!=" | "==" ) comparison )* ;
// comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
// term           → factor ( ( "-" | "+" ) factor )* ;
// factor         → unary ( ( "/" | "*" ) unary )* ;
// unary          → ( "!" | "-" ) unary
//                | primary ;
// primary        → NUMBER | STRING | "true" | "false" | "nil"
//                | "(" expression ")" ;

public class Parser {
    private final List<Token> tokens;
    private int i = 0;
    
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        return expression();
    }

    private Expression expression() {
        return equality();
    }

    private Expression equality() {
        Expression expr = comparaison();
        Set<TokenType> operators = Set.of(TokenType.EGAL, TokenType.DIFFERENT);

        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = comparaison();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression comparaison() {
        Expression expr = term();
        Set<TokenType> operators = Set.of(TokenType.GRAND_EGAL, TokenType.PLUS_GRAND, TokenType.PETIT_EGAL, TokenType.PLUS_PETIT);
        
        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = term();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression term() {
        Expression expr = factor();
        Set<TokenType> operators = Set.of(TokenType.MOINS, TokenType.PLUS);
        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = factor();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression factor() {
        Expression expr = unary();
        Set<TokenType> operators = Set.of(TokenType.FOIS, TokenType.DIVISE);
        while (i < tokens.size() && operators.contains(current().type())) {
            Token operator = current();
            advance();
            Expression right = unary();
            expr = new BinaryOperation(expr, right, new UBinaryOperator(operator));
        }
        return expr;
    }

    private Expression unary() {
        if (Set.of(TokenType.NON, TokenType.MOINS).contains(current().type())) {
            Token operator = current();
            advance();
            return new UnaryOperation(unary(), new UUnaryOperator(operator));
        }

        return primary();
    }

    private Expression primary() {
        Token token = current();
        TokenType type = token.type();
        advance();

        switch (type) {
            case TokenType.NOMBRE:
                return new Literal(new ZorvexValue((int)token.data()));
            case TokenType.CHAINE:
                return new Literal(new ZorvexValue((String)token.data()));
            case TokenType.IDENTIFIANT:
                return new Variable((String)token.lexeme());
            case TokenType.VRAI:
                return new Literal(new ZorvexValue(1));
            case TokenType.FAUX:
                return new Literal(new ZorvexValue(0));
        }

        if (type == TokenType.PARENTHESE_GAUCHE) {
            Expression expr = expression();
            
            if (current().type() == TokenType.PARENTHESE_DROIT) {
                advance();
                return new Grouping(expr);
            }
                
        }
        return null;
        
    }


    private Token current() {
        return tokens.get(i);
    }

    private Token peekNext() {
        return i+1 < tokens.size() ? tokens.get(i+1) : null;
    }

    private Token getPrevious() {
        return i-1 >= 0 ? tokens.get(i-1) : null;
    }

    private void advance() {
        i += 1;
    }
}
