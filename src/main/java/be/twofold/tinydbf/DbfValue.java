package be.twofold.tinydbf;

import java.time.*;
import java.util.*;

public abstract class DbfValue {

    static DbfValue character(String value) {
        if (value == null || value.isEmpty()) {
            return Null.Instance;
        }
        return new Char(value);
    }

    static DbfValue date(LocalDate value) {
        if (value == null) {
            return Null.Instance;
        }
        return new Date(value);
    }

    static DbfValue logical(Boolean value) {
        if (value == null) {
            return Null.Instance;
        }
        return Logical.valueOf(value);
    }

    static DbfValue numeric(Number value) {
        if (value == null) {
            return Null.Instance;
        }
        return new Numeric(value);
    }


    public final boolean isNull() {
        return this instanceof Null;
    }

    public final boolean isCharacter() {
        return this instanceof Char;
    }

    public final boolean isDate() {
        return this instanceof Date;
    }

    public final boolean isLogical() {
        return this instanceof Logical;
    }

    public final boolean isNumeric() {
        return this instanceof Numeric;
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


    static final class Char extends DbfValue {
        private final String value;

        Char(String value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public String asCharacter() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Char
                && value.equals(((Char) obj).value);
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

    static final class Date extends DbfValue {
        private final LocalDate value;

        Date(LocalDate value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public LocalDate asDate() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Date
                && value.equals(((Date) obj).value);
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

    static final class Logical extends DbfValue {
        static final Logical True = new Logical(true);
        static final Logical False = new Logical(false);

        private final boolean value;

        private Logical(boolean value) {
            this.value = value;
        }

        static Logical valueOf(boolean value) {
            return value ? True : False;
        }

        @Override
        public boolean asLogical() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Logical
                && value == ((Logical) obj).value;
        }

        @Override
        public int hashCode() {
            return Boolean.hashCode(value);
        }

        @Override
        public String toString() {
            return Boolean.toString(value);
        }
    }

    static final class Null extends DbfValue {
        static final DbfValue.Null Instance = new Null();

        private Null() {
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof DbfValue.Null;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return "null";
        }
    }

    static final class Numeric extends DbfValue {
        private final Number value;

        Numeric(Number value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public Number asNumeric() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof Numeric
                && value.equals(((Numeric) obj).value);
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
}
