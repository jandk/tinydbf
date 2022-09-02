package be.twofold.tinydbf;

import java.time.LocalDate;
import java.util.Objects;

final class DbfValueDate extends DbfValue {

    private final LocalDate value;

    DbfValueDate(LocalDate value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public LocalDate asDate() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfValueDate
            && value.equals(((DbfValueDate) obj).value);
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
