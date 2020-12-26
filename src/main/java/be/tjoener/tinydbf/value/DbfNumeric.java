package be.tjoener.tinydbf.value;

import java.math.*;
import java.util.*;

final class DbfNumeric extends Number implements DbfValue {

    private final String value;

    DbfNumeric(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }


    @Override
    public Number asNumeric() {
        return this;
    }


    @Override
    public int intValue() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return (int) longValue();
        }
    }

    @Override
    public long longValue() {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return new BigInteger(value).longValue();
        }
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(value);
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(value);
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
        return value;
    }

}
