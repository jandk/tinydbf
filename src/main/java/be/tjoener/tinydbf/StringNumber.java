package be.tjoener.tinydbf;

import static java.util.Objects.requireNonNull;

final class StringNumber extends Number {
    private final String value;

    StringNumber(String value) {
        this.value = requireNonNull(value, "value");
    }

    @Override
    public int intValue() {
        return Integer.parseInt(value);
    }

    @Override
    public long longValue() {
        return Long.parseLong(value);
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
        return this == obj || obj instanceof StringNumber
                && value.equals(((StringNumber) obj).value);
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
