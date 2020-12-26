package be.twofold.tinydbf.value;

import java.util.*;

final class DbfCharacter implements DbfValue {

    private final String value;

    DbfCharacter(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }


    @Override
    public String asCharacter() {
        return value;
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfCharacter
            && value.equals(((DbfCharacter) obj).value);
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
