package be.twofold.tinydbf;

final class DbfValueLogical extends DbfValue {

    static final DbfValueLogical True = new DbfValueLogical(true);
    static final DbfValueLogical False = new DbfValueLogical(false);

    private final boolean value;

    private DbfValueLogical(boolean value) {
        this.value = value;
    }

    static DbfValueLogical valueOf(boolean value) {
        return value ? True : False;
    }

    @Override
    public boolean asLogical() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfValueLogical
            && value == ((DbfValueLogical) obj).value;
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
