package system.programing.syntax.analyzer;

import java.util.List;

public class SyntaxAnalyzer {

    private List<String> tokens;
    private int index = 0;

    public SyntaxAnalyzer(List<String> tokens) {
        this.tokens = tokens;
        System.out.println(tokens);
    }

    public ParseTreeNode parseSentence() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.SENTENCE);
        if (index < tokens.size()) {
            String currentToken = tokens.get(index);
            switch (currentToken) {
                case "ПОБУДУВАТИ":
                    node.addChild(parseSimpleOperator());
                    break;
                case "ВИЗНАЧИТИ":
                    node.addChild(parseDefinition());
                    break;
                case "ЗНАЙТИ":
                    node.addChild(parseQuery());
                    break;
                default:
                    throw new SyntaxException("Несподіваний початок речення: " + currentToken);
            }
            return node;
        } else {
            throw new SyntaxException("Неочікуваний кінець введення.");
        }
    }

    private ParseTreeNode parseSimpleOperator() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.SIMPLE_OPERATOR);
        match("ПОБУДУВАТИ");
        match("РОМБ");
        node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "РОМБ"));
        node.addChild(parseRhombus());
        return node;
    }

    private ParseTreeNode parseDefinition() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.DEFINITION);
        match("ВИЗНАЧИТИ");

        String nextToken = tokens.get(index);
        switch (nextToken) {
            case "СТОРОНА":
                index++;
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "СТОРОНА"));
                node.addChild(parsePairNames());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseLength());
                break;
            case "ДІАГОНАЛЬ":
                index++;
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ДІАГОНАЛЬ"));
                node.addChild(parsePairNames());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseLength());
                break;
            case "КУТ":
                index++;
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "КУТ"));
                node.addChild(parseIdentifier());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseAngle());
                break;
            case "ГОСТРИЙ":
                index++;
                match("КУТ");
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ГОСТРИЙ КУТ"));
                node.addChild(parseIdentifier());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseAngle());
                break;
            case "ТУПИЙ":
                index++;
                match("КУТ");
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ТУПИЙ КУТ"));
                node.addChild(parseIdentifier());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseAngle());
                break;
            case "ВИСОТА":
                index++;
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ВИСОТА"));
                node.addChild(parsePairNames());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseLength());
                break;
            case "ПЕРИМЕТР":
                index++;
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ПЕРИМЕТР"));
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseLength());
                break;
            case "ПЛОЩА":
                index++;
                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ПЛОЩА"));
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseLength());
                break;
            default:
                node.addChild(parsePairNames());
                match("=");
                node.addChild(new ParseTreeNode(TreeNodeType.ASSIGNMENT, "="));
                node.addChild(parseLength());
                break;
        }

        return node;
    }

    private ParseTreeNode parseQuery() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.QUERY);
        match("ЗНАЙТИ");
        node.addChild(parseDataType());
        return node;
    }

    private ParseTreeNode parseDataType() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.DATA_TYPE);
        String currentToken = tokens.get(index);
                // Обробка складних типів даних
                switch (currentToken) {
                    case "ПЕРИМЕТР":
                    case "ПЛОЩА":
                        node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, currentToken));
                        index++;
                        break;
                    case "ДОВЖАНА":
                        index++;
                        String nextToken = tokens.get(index);
                        if (nextToken.equals("СТОРІН") || nextToken.equals("СТОРОНИ")) {
                            match(nextToken);
                            node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, nextToken));
                            node.addChild(parsePairNames());
                        } else if (nextToken.equals("ДІАГОНАЛЬ") || nextToken.equals("ВИСОТА")) {
                            match(nextToken);
                            node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, nextToken));
                            node.addChild(parsePairNames());
                        } else {
                            throw new SyntaxException("Невідомий тип довжини: " + nextToken);
                        }
                        break;
                    case "МІРУ":
                        index++;
                        String measureType = tokens.get(index);
                        switch (measureType) {
                            case "КУТА":
                                match("КУТА");
                                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "МІРУ КУТА"));
                                node.addChild(parseIdentifier());
                                break;
                            case "ГОСТРИЙ":
                                index++;
                                match("КУТ");
                                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "МІРУ ГОСТРИЙ КУТ"));
                                break;
                            case "ТУПИЙ":
                                index++;
                                match("КУТ");
                                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "МІРУ ТУПИЙ КУТ"));
                                break;
                            default:
                                throw new SyntaxException("Невідомий тип міри: " + measureType);
                        }
                        break;
                    case "ДОВЖИНА":
                        index++;
                        String lengthType = tokens.get(index);
                        switch (lengthType) {
                            case "ДІАГОНАЛЬ":
                            case "ВИИСОТА":
                                match(lengthType);
                                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, lengthType));
                                node.addChild(parsePairNames());
                                break;
                            case "СТОРОНА":
                                match(lengthType);
                                node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, lengthType));
                                break;
                            default:
                                throw new SyntaxException("Невідомий тип довжини: " + lengthType);
                        }
                        break;
                    case "ВСІ":
                        index++;
                        match("КУТИ");
                        node.addChild(new ParseTreeNode(TreeNodeType.KEYWORD, "ВСІ КУТИ"));
                        break;
                    default:
                        node.addChild(parsePairNames());
                        break;
                }

        return node;
    }

    private ParseTreeNode parseRhombus() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.RHOMBUS);
        node.addChild(parseIdentifier());
        return node;
    }

    private ParseTreeNode parsePairNames() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.PAIR_NAMES);
        node.addChild(parseIdentifier());
        return node;
    }

    private ParseTreeNode parseIdentifier() throws SyntaxException {
        if (index < tokens.size() && (tokens.get(index).matches("[A-N]*") || tokens.get(index).equals("NULL"))) {
            ParseTreeNode node = new ParseTreeNode(TreeNodeType.IDENTIFIER, tokens.get(index));
            index++;
            return node;
        } else {
            throw new SyntaxException("Невірне ім'я: " + (index < tokens.size() ? tokens.get(index) : "кінець введення"));
        }
    }

    private ParseTreeNode parseLength() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.LENGTH);
        node.addChild(parseNumber());
        node.addChild(parseUnit());
        return node;
    }

    private ParseTreeNode parseAngle() throws SyntaxException {
        ParseTreeNode node = new ParseTreeNode(TreeNodeType.ANGLE);
        node.addChild(parseNumber());
        return node;
    }

    private ParseTreeNode parseNumber() throws SyntaxException {
        if (index < tokens.size() && tokens.get(index).matches("\\d+(\\.\\d+)?")) {
            ParseTreeNode node = new ParseTreeNode(TreeNodeType.NUMBER, tokens.get(index));
            index++;
            return node;
        } else {
            throw new SyntaxException("Очікувалося число, знайдено: " + (index < tokens.size() ? tokens.get(index) : "кінець введення"));
        }
    }

    private ParseTreeNode parseUnit() throws SyntaxException {
        if (index < tokens.size()) {
            String unit = tokens.get(index);
            switch (unit) {
                case "СМ":
                case "М":
                case "ММ":
                case "ДМ":
                    ParseTreeNode node = new ParseTreeNode(TreeNodeType.UNIT, unit);
                    index++;
                    return node;
                default:
                    throw new SyntaxException("Очікувана одиниця вимірювання (см, м, мм, дм), знайдено: " + unit);
            }
        } else {
            throw new SyntaxException("Очікувалася одиниця вимірювання, але знайдено кінець введення.");
        }
    }

    private void match(String expectedToken) throws SyntaxException {
        if (index < tokens.size() && tokens.get(index).equals(expectedToken)) {
            index++;
        } else {
            throw new SyntaxException("Очікувався токен: \"" + expectedToken + "\", але знайдено: " +
                    (index < tokens.size() ? "\"" + tokens.get(index) + "\"" : "кінець введення"));
        }
    }

    public static class SyntaxException extends Exception {
        public SyntaxException(String message) {
            super(message);
        }
    }
}