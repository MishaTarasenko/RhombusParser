package system.programing.lexical.analyzer;

import java.util.Set;

public class Analyzer {
    private String input;
    private int position;
    private char currentChar;

    public Analyzer(String input) {
        this.input = input;
        this.position = 0;
        this.currentChar = input.charAt(position);
    }

    private void advance() {
        position++;
        if (position < input.length()) {
            currentChar = input.charAt(position);
        } else {
            currentChar = '\0';
        }
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private Token identifier() {
        StringBuilder result = new StringBuilder();
        while (isLetter(currentChar)) {
            result.append(currentChar);
            advance();
        }

        String value = result.toString().toUpperCase();
        if (isKeyWord(value)) {
            return new Token(TokenType.KEYWORDS, value);
        } else if (isFunction(value)) {
            return new Token(TokenType.FUNCTION, value);
        } else if (isReservedWord(value)) {
            return new Token(TokenType.RESERVED, value);
        }else if (isUnit(value)){
            return new Token(TokenType.UNIT, value);
        } else {
            Set<Character> allowedLetters = Set.of('A', 'B', 'C', 'D', 'E', 'F', 'K', 'M', 'N', 'T');
            boolean isIdentifier = value.toUpperCase().chars()
                    .mapToObj(c -> (char) c)
                    .allMatch(c -> allowedLetters.contains(c) || Character.isDigit(c));
            if ((isIdentifier && value.length() <= 4 && value.length() > 0) || value.equals("NULL")) {
                return new Token(TokenType.IDENTIFIER, value);
            } else {
                return new Token(TokenType.INVALID, value);
            }
        }
    }

    private Token number() {
        StringBuilder result = new StringBuilder();
        while (isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }

        return new Token(TokenType.NUMBER, result.toString());
    }

    private boolean isUnit(String word) {
        return switch (word) {
            case "ММ", "СМ", "ДМ", "М", "°" -> true;
            default -> false;
        };
    }

    private boolean isKeyWord(String word) {
        return switch (word) {
            case "РОМБ", "ВИСОТА", "ДІАГОНАЛЬ", "КУТ",
                 "ПЛОЩА", "ПЕРИМЕТР", "СТОРОНА", "КУТИ" -> true;

            default -> false;
        };
    }


    private boolean isFunction(String word) {
        return switch (word) {
            case "ПОБУДУВАТИ", "ВИЗНАЧИТИ", "ЗНАЙТИ" -> true;
            default -> false;
        };
    }

    private boolean isReservedWord(String word) {
        return switch (word) {
            case "ГОСТРИЙ", "ТУПИЙ", "ВСІ", "ДОВЖИНА" -> true;
            default -> false;
        };
    }


    public Token getNextToken() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                advance();
                continue;
            }

//            if (currentChar == '.' || currentChar == ',') {
//                char ch = currentChar;
//                advance();
//                return new Token(TokenType.DELIMITERS, String.valueOf(ch));
//            }

            if (isLetter(currentChar)) {
                return identifier();
            }

            if (currentChar == '=') {
                advance();
                return new Token(TokenType.ASSIGNMENT, String.valueOf("="));
            }

            if (isDigit(currentChar)) {
                return number();
            }

            advance();
            return new Token(TokenType.INVALID, String.valueOf(currentChar));
        }

        return null;
    }
}
