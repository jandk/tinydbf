package be.tjoener.tinydbf;

import java.time.*;
import java.util.*;

public final class DbfRecord {
    private final DbfHeader header;
    private final Object[] row;

    public DbfRecord(DbfHeader header, Object[] row) {
        this.header = header;
        this.row = row;
    }

    public String getString(String fieldName) {
        return (String) get(fieldName);
    }

    public LocalDate getDate(String fieldName) {
        return (LocalDate) get(fieldName);
    }

    public Number getNumber(String fieldName) {
        return (Number) get(fieldName);
    }

    public Boolean getBoolean(String fieldName) {
        return (Boolean) get(fieldName);
    }

    private Object get(String fieldName) {
        return row[header.getFieldIndex(fieldName)];
    }

    // region Helpers

    public byte getByte(String fieldName) {
        return getNumber(fieldName).byteValue();
    }

    public short getShort(String fieldName) {
        return getNumber(fieldName).shortValue();
    }

    public int getInt(String fieldName) {
        return getNumber(fieldName).intValue();
    }

    public long getLong(String fieldName) {
        return getNumber(fieldName).longValue();
    }

    public float getFloat(String fieldName) {
        return getNumber(fieldName).floatValue();
    }

    public double getDouble(String fieldName) {
        return getNumber(fieldName).doubleValue();
    }

    // endregion


    @Override
    public String toString() {
        return Arrays.toString(row);
    }
}
