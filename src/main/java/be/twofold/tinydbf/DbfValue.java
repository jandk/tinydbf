package be.twofold.tinydbf;

import java.time.*;

public abstract class DbfValue {

    DbfValue() {
    }

    public final boolean isNull() {
        return this instanceof DbfValueNull;
    }

    public final boolean isCharacter() {
        return this instanceof DbfValueChar;
    }

    public final boolean isDate() {
        return this instanceof DbfValueDate;
    }

    public final boolean isLogical() {
        return this instanceof DbfValueLogical;
    }

    public final boolean isNumeric() {
        return this instanceof DbfValueNumeric;
    }


    public String asCharacter() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public LocalDate asDate() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean asLogical() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number asNumeric() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

}
