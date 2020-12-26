package be.tjoener.tinydbf.value;

import java.time.*;

public interface DbfValue {

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
