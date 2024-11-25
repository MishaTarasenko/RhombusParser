package system.programing.rhombus.solution;

import system.programing.semantic.analyzer.Rhombus;

public class RhombusSolver {

    public void solve(Rhombus rhombus) {
        String query = rhombus.getQuery().toUpperCase();

        switch (query) {
            case "ПЕРИМЕТР":
                computePerimeter(rhombus);
                break;
            case "ПЛОЩА":
                computeArea(rhombus);
                break;
            case "ВСІ КУТИ":
                computeAngles(rhombus);
                break;
            case "ДІАГОНАЛЬ":
                computeDiagonal(rhombus);
                break;
            case "СТОРОНА":
                computeSide(rhombus);
                break;
            case "ТУПИЙ КУТ":
                computeLargestAngle(rhombus);
                break;
            default:
                computeSpecificLine(rhombus, query);
                break;
        }
    }

    private void computePerimeter(Rhombus rhombus) {
        if (rhombus.getSide() != null) {
            Double perimeter = 4 * rhombus.getSide();
            rhombus.setPerimeter(perimeter);
        } else if (rhombus.getDiagonal1() != null && rhombus.getDiagonal2() != null) {
            Double side = Math.sqrt(Math.pow(rhombus.getDiagonal1() / 2, 2) +
                                    Math.pow(rhombus.getDiagonal2() / 2, 2));
            rhombus.setSide(side);
            rhombus.setPerimeter(4 * side);
        } else {
            System.out.println("Insufficient data to compute perimeter.");
        }
    }

    private void computeArea(Rhombus rhombus) {
        if (rhombus.getDiagonal1() != null && rhombus.getDiagonal2() != null) {
            Double area = (rhombus.getDiagonal1() * rhombus.getDiagonal2()) / 2;
            rhombus.setArea(area);
        } else if (rhombus.getSide() != null && rhombus.getHeightValue() != null) {
            Double area = rhombus.getSide() * rhombus.getHeightValue();
            rhombus.setArea(area);
        } else {
            System.out.println("Insufficient data to compute area.");
        }
    }

    private void computeAngles(Rhombus rhombus) {
        if (rhombus.getAngleA() != null) {
            rhombus.setAngleB(180.0 - rhombus.getAngleA());
        } else if (rhombus.getAngleB() != null) {
            rhombus.setAngleA(180.0 - rhombus.getAngleB());
        } else if ((rhombus.getSide() != null || rhombus.getPerimeter() != null) && rhombus.getHeightValue() != null) {
            if (rhombus.getSide() == null && rhombus.getPerimeter() != null) {
                rhombus.setSide(rhombus.getPerimeter() / 4);
            }

            Double side = rhombus.getSide();
            Double height = rhombus.getHeightValue();

            if (side != 0) {
                double sinTheta = height / side;

                if (sinTheta >= 0 && sinTheta <= 1) {
                    double angleA = Math.toDegrees(Math.asin(sinTheta));
                    double angleB = 180.0 - angleA;

                    rhombus.setAngleA(angleA);
                    rhombus.setAngleB(angleB);
                } else {
                    System.out.println("Invalid height and side values. Height must be <= side.");
                }
            } else {
                System.out.println("Side length cannot be zero.");
            }
        } else {
            System.out.println("Insufficient data to compute angles.");
        }
    }

    private void computeDiagonal(Rhombus rhombus) {
        if (rhombus.getPerimeter() != null && rhombus.getDiagonal1() != null) {
            Double side = rhombus.getPerimeter() / 4;
            Double d2 = 2 * Math.sqrt(Math.pow(side, 2) - Math.pow(rhombus.getDiagonal1() / 2, 2));
            rhombus.setDiagonal2(d2);
        } else if (rhombus.getHalfDiagonal1Value() != null && rhombus.getHalfDiagonal2Value() != null) {
            rhombus.setDiagonal1(rhombus.getHalfDiagonal1Value() * 2);
            rhombus.setDiagonal2(rhombus.getHalfDiagonal2Value() * 2);
        } else {
            System.out.println("Insufficient data to compute diagonal.");
        }
    }

    private void computeSide(Rhombus rhombus) {
        if (rhombus.getDiagonal1() != null && rhombus.getDiagonal2() != null) {
            Double side = Math.sqrt(Math.pow(rhombus.getDiagonal1() / 2, 2) +
                    Math.pow(rhombus.getDiagonal2() / 2, 2));
            rhombus.setSide(side);
        } else if (rhombus.getPerimeter() != null) {
            rhombus.setSide(rhombus.getPerimeter() / 4);
        }else if (rhombus.getHalfDiagonal1Value() != null && rhombus.getHalfDiagonal2Value() != null) {
            Double side = Math.sqrt(Math.pow(rhombus.getHalfDiagonal1Value(), 2) + Math.pow(rhombus.getHalfDiagonal2Value(), 2));
            rhombus.setSide(side);
        }else {
            System.out.println("Insufficient data to compute side.");
        }
    }

    private void computeSpecificLine(Rhombus rhombus, String query) {

        if (isSide(rhombus.getIdentifier(), query)) {
            computeSide(rhombus);
            if (rhombus.getSide() != null) {
                System.out.println("AD = " + rhombus.getSide() + " cm");
            }
        } else {
            System.out.println("Unknown specific line query: " + query);
        }
    }

    private void computeLargestAngle(Rhombus rhombus) {
        if (rhombus.getSide() != null && rhombus.getArea() != null) {
            Double height = rhombus.getArea() / rhombus.getSide();
            rhombus.setHeightValue(height);
            rhombus.setHeightName("height");
            Double sinTheta = rhombus.getArea() / (rhombus.getSide() * rhombus.getSide());
            Double angleA = Math.toDegrees(Math.asin(sinTheta));
            Double angleB = 180.0 - angleA;
            rhombus.setAngleA(angleA);
            rhombus.setAngleB(angleB);
        } else {
            System.out.println("Insufficient data to compute largest angle.");
        }
    }

    private boolean isSide(String vertices, String name) {
        String char1 = String.valueOf(name.charAt(0));
        String char2 = String.valueOf(name.charAt(1));

        return vertices.contains(char1) && vertices.contains(char2);
    }
}