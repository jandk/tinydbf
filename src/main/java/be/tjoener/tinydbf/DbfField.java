package be.tjoener.tinydbf;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class DbfField {
    private final String name;
    private final DbfType type;
    private final int length;
    private final int decimalCount;

    public DbfField(String name, DbfType type, int length, int decimalCount) {
        this.name = requireNonNull(name, "name");
        this.type = requireNonNull(type, "type");
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
        if (obj == null || getClass() != obj.getClass()) return false;

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
        return String.format(
                "DbfField(name='%s', type=%s, length=%d, decimalCount=%d)",
                name, type, length, decimalCount
        );
    }

}
