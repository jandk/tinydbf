package be.twofold.tinydbf;

import java.util.Objects;

final class DbfValueNumeric extends DbfValue {

    private final Number value;

    DbfValueNumeric(Number value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public Number asNumeric() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfValueNumeric
            && value.equals(((DbfValueNumeric) obj).value);
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
