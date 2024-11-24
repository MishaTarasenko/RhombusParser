package system.programing.input.analyzer;

public enum DataType {

    DIAMOND,
    SIDE,
    HEIGHT,
    ANGLE,
    ACUTE_ANGLE,
    OBTUSE_ANGLE,
    DIAGONAL,
    SQUARE,
    PERIMETER,
    SEARCH,
    OTHER;

    public static DataType fromString(String type) {
        switch (type.toUpperCase()) {
            case "РОМБ":
                return DIAMOND;
            case "СТОРОН":
                return SIDE;
            case "ВИСОТ":
                return HEIGHT;
            case "КУТ":
                return ANGLE;
            case "ГОСТРИЙ КУТ":
                return ACUTE_ANGLE;
            case "ТУПИЙ КУТ":
                return OBTUSE_ANGLE;
            case "ДІАГОНАЛЬ":
                return DIAGONAL;
            case "ПЕРИМЕТР":
                return PERIMETER;
            case "ПЛОЩ":
                return SQUARE;
            case "ЗНАЙТ", "ОБЧИСЛИТ":
                return SEARCH;
            default:
                return OTHER;
        }
    }
}
