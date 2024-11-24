package system.programing.semantic.analyzer;

public enum Unit {
    MM("ММ", 0.1),
    CM("СМ", 1.0),
    M("М", 100.0),
    ДМ("ДМ", 10.0);

    private String symbol;
    private double toCmFactor;

    Unit(String symbol, double toCmFactor) {
        this.symbol = symbol.toLowerCase();
        this.toCmFactor = toCmFactor;
    }

    public double toCentimeters(double value) {
        return value * toCmFactor;
    }

    public static Unit fromString(String text) {
        for (Unit unit : Unit.values()) {
            if (unit.symbol.equalsIgnoreCase(text)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Непідтримувана одиниця вимірювання: " + text);
    }
}