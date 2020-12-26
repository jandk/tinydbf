package be.tjoener.tinydbf.value;

final class DbfLogical implements DbfValue {

    static final DbfLogical True = new DbfLogical(true);
    static final DbfLogical False = new DbfLogical(false);

    private final boolean value;

    private DbfLogical(boolean value) {
        this.value = value;
    }

    static DbfLogical valueOf(boolean value) {
        return value ? True : False;
    }


    @Override
    public boolean asLogical() {
        return value;
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfLogical
            && value == ((DbfLogical) obj).value;
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
