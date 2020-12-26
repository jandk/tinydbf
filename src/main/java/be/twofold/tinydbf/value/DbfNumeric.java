package be.twofold.tinydbf.value;

import java.util.*;

final class DbfNumeric implements DbfValue {

    private final Number value;

    DbfNumeric(Number value) {
        this.value = Objects.requireNonNull(value, "value");
    }


    @Override
    public Number asNumeric() {
        return value;
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfNumeric
            && value.equals(((DbfNumeric) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
