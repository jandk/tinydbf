package be.twofold.tinydbf.value;

final class DbfNull implements DbfValue {

    static final DbfNull Null = new DbfNull();

    private DbfNull() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DbfNull;
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
