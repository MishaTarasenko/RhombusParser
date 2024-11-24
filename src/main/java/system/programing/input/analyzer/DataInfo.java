package system.programing.input.analyzer;

import java.util.Objects;

public class DataInfo {

    private String value;
    private String name;
    private DataType type;

    public DataInfo(String value, String name, DataType type) {
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataInfo dataInfo = (DataInfo) o;
        return Objects.equals(value, dataInfo.value) && Objects.equals(name, dataInfo.name) && type == dataInfo.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name, type);
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
