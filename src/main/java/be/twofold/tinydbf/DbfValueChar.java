package be.twofold.tinydbf;

import java.util.*;

final class DbfValueChar extends DbfValue {

    private final String value;

    DbfValueChar(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public String asCharacter() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfValueChar
            && value.equals(((DbfValueChar) obj).value);
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
