package interpreter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum TokenType {
    PLUS("+"), MOINS("-"), FOIS("*"), DIVISE("/"), EGAL("=="), PLUS_GRAND(">"),
    PLUS_PETIT("<"), GRAND_EGAL(">="), PETIT_EGAL("<="), NON("!"), DIFFERENT("!="),
    ASSIGNER("<-"), PARENTHESE_GAUCHE("("), PARENTHESE_DROIT(")"), 
    SI("si"), POUR("pour"), RETOURNER("retourner"), AFFICHER("afficher"),
    VRAI("vrai"), FAUX("faux"),
    NOMBRE(),
    CHAINE(),
    IDENTIFIANT();

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
