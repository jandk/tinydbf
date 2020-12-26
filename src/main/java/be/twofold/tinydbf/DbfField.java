package be.twofold.tinydbf;

import java.util.*;

public final class DbfField {
    private final String name;
    private final DbfType type;
    private final int length;
    private final int decimalCount;

    public DbfField(String name, DbfType type, int length, int decimalCount) {
        this.name = Objects.requireNonNull(name, "name");
        this.type = Objects.requireNonNull(type, "type");
        this.length = length;
        this.decimalCount = decimalCount;
    }


    public String getName() {
        return name;
    }

    public DbfType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public int getDecimalCount() {
        return decimalCount;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DbfField)) return false;

        DbfField other = (DbfField) obj;
        return name.equals(other.name)
            && type == other.type
            && length == other.length
            && decimalCount == other.decimalCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, length, decimalCount);
    }

    @Override
    public String toString() {
        return "DbfField(" +
            "name='" + name + "', " +
            "type=" + type + ", " +
            "length=" + length + ", " +
            "decimalCount=" + decimalCount +
            ")";
    }

}
