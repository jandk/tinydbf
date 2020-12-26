package be.tjoener.tinydbf.value;

import java.time.*;
import java.util.*;

final class DbfDate implements DbfValue {

    private final LocalDate value;

    DbfDate(LocalDate value) {
        this.value = Objects.requireNonNull(value, "value");
    }


    @Override
    public LocalDate asDate() {
        return value;
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfDate
            && value.equals(((DbfDate) obj).value);
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
