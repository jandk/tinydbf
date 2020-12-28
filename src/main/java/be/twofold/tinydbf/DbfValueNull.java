package be.twofold.tinydbf;

final class DbfValueNull extends DbfValue {

    static final DbfValueNull Instance = new DbfValueNull();

    private DbfValueNull() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DbfValueNull;
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
