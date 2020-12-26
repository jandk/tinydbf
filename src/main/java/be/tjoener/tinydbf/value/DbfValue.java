package be.tjoener.tinydbf.value;

import java.time.*;

public interface DbfValue {

    DbfValue Null = DbfNull.Null;

    static DbfValue character(String value) {
        return new DbfCharacter(value);
    }

    static DbfValue date(LocalDate value) {
        return new DbfDate(value);
    }

    static DbfValue logical(boolean value) {
        return DbfLogical.valueOf(value);
    }

    static DbfValue numeric(Number value) {
        return new DbfNumeric(value);
    }


    default boolean isNull() {
        return this instanceof DbfNull;
    }

    default boolean isCharacter() {
        return this instanceof DbfCharacter;
    }

    default boolean isDate() {
        return this instanceof DbfDate;
    }

    default boolean isLogical() {
        return this instanceof DbfLogical;
    }

    default boolean isNumeric() {
        return this instanceof DbfNumeric;
    }


    default String asCharacter() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    default LocalDate asDate() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    default boolean asLogical() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    default Number asNumeric() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

}
