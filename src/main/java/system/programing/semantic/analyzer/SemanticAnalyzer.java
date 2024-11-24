package system.programing.semantic.analyzer;

import system.programing.syntax.analyzer.ParseTreeNode;
import system.programing.syntax.analyzer.TreeNodeType;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {

    public Rhombus analyze(List<ParseTreeNode> parseTrees) throws SemanticException {
        Rhombus rhombus = new Rhombus();

        for (ParseTreeNode tree : parseTrees) {
            processNode(tree, rhombus);
        }

        return rhombus;
    }

    private void processNode(ParseTreeNode node, Rhombus rhombus) throws SemanticException {
        if (node.getType() != TreeNodeType.SENTENCE) {
            return;
        }

        for (ParseTreeNode child : node.getChildren()) {
            switch (child.getType()) {
                case SIMPLE_OPERATOR:
                    processSimpleOperator(child, rhombus);
                    break;
                case DEFINITION:
                    processDefinition(child, rhombus);
                    break;
                case QUERY:
                    processQuery(child, rhombus);
                    break;
                default:
                    break;
            }
        }
    }

    private void processSimpleOperator(ParseTreeNode node, Rhombus rhombus) {
        rhombus.setIdentifier(node.getChildren().get(1).getChildren().getFirst().getValue());
    }

    private void processDefinition(ParseTreeNode node, Rhombus rhombus) throws SemanticException {
        List<ParseTreeNode> children = node.getChildren();
        if (children.isEmpty()) return;

        ParseTreeNode keywordNode = children.get(0);
        String keyword = keywordNode.getValue();
        if (keyword == null) keyword = "";

        switch (keyword) {
            case "ПЕРИМЕТР":
                Double perimeter = extractLength(children);
                rhombus.setPerimeter(perimeter);
                break;
            case "ВИСОТА":
                processHeightDefinition(children, rhombus);
                break;
            case "СТОРОНА":
                Double side = extractLength(children);
                rhombus.setSide(side);
                break;
            case "ПЛОЩА":
                Double area = extractLength(children);
                rhombus.setArea(area);
                break;
            case "КУТ":
                Double angle = extractAngle(children);
                if (rhombus.getAngleA() == null) {
                    rhombus.setAngleA(angle);
                    rhombus.setAngleB(180 - angle);
                }
                break;
            case "ДІАГОНАЛЬ":
                Double diagonal = extractLength(children);
                if (rhombus.getDiagonal1() == null) {
                    rhombus.setDiagonal1(diagonal);
                } else {
                    rhombus.setDiagonal2(diagonal);
                }
                break;
            case "ПОЛОВИНА ДІАГОНАЛІ":
                processHalfDiagonalDefinition(children, rhombus);
                break;
            case "ГОСТРИЙ КУТ":
                Double acuteAngle = extractAngle(children);
                rhombus.setAngleA(acuteAngle);
                rhombus.setAngleB(180 - acuteAngle);
                break;
            default:
                handleDefaultDefinition(children, rhombus);
                break;
        }
    }

    private void handleDefaultDefinition(List<ParseTreeNode> children, Rhombus rhombus) throws SemanticException {
        String identifierName = null;
        Double lengthValue = null;

        for (ParseTreeNode child : children) {
            if (child.getType() == TreeNodeType.PAIR_NAMES) {
                identifierName = child.getChildren().get(0).getValue();
            } else if (child.getType() == TreeNodeType.LENGTH) {
                lengthValue = extractLength(children);
            }
        }

        if (identifierName != null && lengthValue != null) {
            String rhombusId = rhombus.getIdentifier();
            if (rhombusId == null || rhombusId.length() < 2) {
                throw new SemanticException("Ромб не визначено або некоректний ідентифікатор для обробки.");
            }

            rhombusId = rhombusId.toUpperCase();

            List<String> sides = new ArrayList<>();
            for (int i = 0; i < rhombusId.length(); i++) {
                String side = "" + rhombusId.charAt(i) + rhombusId.charAt((i + 1) % rhombusId.length());
                sides.add(side);
            }

            if (sides.contains(identifierName)) {
                rhombus.setSide(lengthValue);
            } else if (identifierName.length() == 2) {
                char firstChar = identifierName.charAt(0);
                char secondChar = identifierName.charAt(1);
                boolean firstMatch = rhombusId.indexOf(firstChar) != -1;
                boolean secondMatch = rhombusId.indexOf(secondChar) != -1;

                if ((firstMatch && !secondMatch) || (!firstMatch && secondMatch)) {
                    if (rhombus.getHalfDiagonal1Name() == null) {
                        rhombus.setHalfDiagonal1Name(identifierName);
                        rhombus.setHalfDiagonal1Value(lengthValue);
                    } else if (rhombus.getHalfDiagonal2Name() == null) {
                        rhombus.setHalfDiagonal2Name(identifierName);
                        rhombus.setHalfDiagonal2Value(lengthValue);
                    } else {
                        throw new SemanticException("Попередження: Перевищено кількість підтримуваних половин діагоналей.");
                    }
                } else {
                    throw new SemanticException("Попередження: Невідома назва визначення: " + identifierName);
                }
            } else {
                throw new SemanticException("Попередження: Невідома назва або некоректний формат: " + identifierName);
            }
        } else {
            throw new SemanticException("Визначення відсутня назва або значення.");
        }
    }

    private void processHeightDefinition(List<ParseTreeNode> children, Rhombus rhombus) throws SemanticException {
        String heightName = null;
        Double heightValue = null;

        for (ParseTreeNode child : children) {
            switch (child.getType()) {
                case PAIR_NAMES:
                    if (!child.getChildren().isEmpty()) {
                        ParseTreeNode identifierNode = child.getChildren().get(0);
                        heightName = identifierNode.getValue();
                    }
                    break;
                case ASSIGNMENT:
                    break;
                case LENGTH:
                    heightValue = extractLength(children);
                    break;
                default:
                    break;
            }
        }

        if (heightName != null && heightValue != null) {
            rhombus.setHeightName(heightName);
            rhombus.setHeightValue(heightValue);
        } else if (heightValue != null) {
            throw new SemanticException("Висота без назви ігнорується.");
        }
    }

    private void processHalfDiagonalDefinition(List<ParseTreeNode> children, Rhombus rhombus) throws SemanticException {
        String halfDiagonalName = null;
        Double halfDiagonalValue = null;

        for (ParseTreeNode child : children) {
            switch (child.getType()) {
                case PAIR_NAMES:
                    if (!child.getChildren().isEmpty()) {
                        ParseTreeNode identifierNode = child.getChildren().get(0);
                        halfDiagonalName = identifierNode.getValue();
                    }
                    break;
                case ASSIGNMENT:
                    break;
                case LENGTH:
                    halfDiagonalValue = extractLength(child.getChildren());
                    break;
                default:
                    break;
            }
        }

        if (halfDiagonalName != null && halfDiagonalValue != null) {
            if (rhombus.getHalfDiagonal1Name() == null) {
                rhombus.setHalfDiagonal1Name(halfDiagonalName);
                rhombus.setHalfDiagonal1Value(halfDiagonalValue);
            } else if (rhombus.getHalfDiagonal2Name() == null) {
                rhombus.setHalfDiagonal2Name(halfDiagonalName);
                rhombus.setHalfDiagonal2Value(halfDiagonalValue);
            } else {
                throw new SemanticException("Попередження: Перевищено кількість підтримуваних половин діагоналей.");
            }
        } else if (halfDiagonalValue != null) {
            throw new SemanticException("Попередження: Половина діагоналі без назви ігнорується.");
        }
    }

    private void processQuery(ParseTreeNode node, Rhombus rhombus) {
        List<ParseTreeNode> children = node.getChildren();
        if (children.isEmpty()) return;

        ParseTreeNode dataTypeNode = children.get(0);
        String queryItem = null;

        for (ParseTreeNode child : dataTypeNode.getChildren()) {
            if (child.getType() == TreeNodeType.KEYWORD) {
                queryItem = child.getValue();
                break;
            } else if (child.getType() == TreeNodeType.PAIR_NAMES) {
                queryItem = child.getChildren().getFirst().getValue();
                break;
            }
        }

        if (queryItem != null) {
            rhombus.setQuery(queryItem);
        }
    }

    private Double extractLength(List<ParseTreeNode> children) {
        for (ParseTreeNode child : children) {
            if (child.getType() == TreeNodeType.LENGTH) {
                Double number = null;
                String unit = null;
                for (ParseTreeNode subChild : child.getChildren()) {
                    if (subChild.getType() == TreeNodeType.NUMBER) {
                        number = Double.parseDouble(subChild.getValue());
                    } else if (subChild.getType() == TreeNodeType.UNIT) {
                        unit = subChild.getValue();
                    }
                }
                return convertToCentimeters(number, unit );
            }
        }
        return null;
    }

    private double convertToCentimeters(double value, String unitStr) {
        try {
            Unit unit = Unit.fromString(unitStr);
            return unit.toCentimeters(value);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    private Double extractAngle(List<ParseTreeNode> children) {
        for (ParseTreeNode child : children) {
            if (child.getType() == TreeNodeType.ANGLE) {
                for (ParseTreeNode subChild : child.getChildren()) {
                    if (subChild.getType() == TreeNodeType.NUMBER) {
                        return Double.parseDouble(subChild.getValue());
                    }
                }
            }
        }
        return null;
    }

    public static class SemanticException extends Exception {
        public SemanticException(String message) {
            super(message);
        }
    }
}