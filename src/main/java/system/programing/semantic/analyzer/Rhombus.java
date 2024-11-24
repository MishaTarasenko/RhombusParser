package system.programing.semantic.analyzer;

public class Rhombus {
    private String identifier;
    private Double perimeter;
    private String heightName;
    private Double heightValue;
    private Double side;
    private Double area;
    private Double angleA;
    private Double angleB;
    private Double diagonal1;
    private Double diagonal2;
    private String halfDiagonal1Name;
    private Double halfDiagonal1Value;
    private String halfDiagonal2Name;
    private Double halfDiagonal2Value;
    private String query;

    public Rhombus() {}


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    public String getHeightName() {
        return heightName;
    }

    public void setHeightName(String heightName) {
        this.heightName = heightName;
    }

    public Double getHeightValue() {
        return heightValue;
    }

    public void setHeightValue(Double heightValue) {
        this.heightValue = heightValue;
    }

    public Double getSide() {
        return side;
    }

    public void setSide(Double side) {
        this.side = side;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getAngleA() {
        return angleA;
    }

    public void setAngleA(Double angleA) {
        this.angleA = angleA;
    }

    public Double getAngleB() {
        return angleB;
    }

    public void setAngleB(Double angleB) {
        this.angleB = angleB;
    }

    public Double getDiagonal1() {
        return diagonal1;
    }

    public void setDiagonal1(Double diagonal1) {
        this.diagonal1 = diagonal1;
    }

    public Double getDiagonal2() {
        return diagonal2;
    }

    public void setDiagonal2(Double diagonal2) {
        this.diagonal2 = diagonal2;
    }

    public String getHalfDiagonal1Name() {
        return halfDiagonal1Name;
    }

    public void setHalfDiagonal1Name(String halfDiagonal1Name) {
        this.halfDiagonal1Name = halfDiagonal1Name;
    }

    public Double getHalfDiagonal1Value() {
        return halfDiagonal1Value;
    }

    public void setHalfDiagonal1Value(Double halfDiagonal1Value) {
        this.halfDiagonal1Value = halfDiagonal1Value;
    }

    public String getHalfDiagonal2Name() {
        return halfDiagonal2Name;
    }

    public void setHalfDiagonal2Name(String halfDiagonal2Name) {
        this.halfDiagonal2Name = halfDiagonal2Name;
    }

    public Double getHalfDiagonal2Value() {
        return halfDiagonal2Value;
    }

    public void setHalfDiagonal2Value(Double halfDiagonal2Value) {
        this.halfDiagonal2Value = halfDiagonal2Value;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "Rhombus{" + "\n" +
                "identifier='" + identifier + '\'' +
                ", perimeter=" + perimeter +
                ", heightName='" + heightName + '\'' +
                ", heightValue=" + heightValue +
                ", side=" + side +
                ", area=" + area +
                ", angleA=" + angleA +
                ", angleB=" + angleB + ",\n" +
                "diagonal1=" + diagonal1 +
                ", diagonal2=" + diagonal2 +
                ", halfDiagonal1Name='" + halfDiagonal1Name + '\'' +
                ", halfDiagonal1Value=" + halfDiagonal1Value +
                ", halfDiagonal2Name='" + halfDiagonal2Name + '\'' +
                ", halfDiagonal2Value=" + halfDiagonal2Value +
                ", query='" + query + '\'' + "\n" +
                '}';
    }
}