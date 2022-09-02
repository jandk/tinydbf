package be.twofold.tinydbf;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class DbfRecord implements Iterable<DbfValue> {

    private final DbfHeader header;
    private final List<DbfValue> values;

    DbfRecord(DbfHeader header, List<DbfValue> values) {
        this.header = Objects.requireNonNull(header, "header");
        this.values = List.copyOf(values);
    }

    @Override
    public Iterator<DbfValue> iterator() {
        return values.iterator();
    }


    public int size() {
        return values.size();
    }

    public DbfValue get(int fieldIndex) {
        return values.get(fieldIndex);
    }

    public DbfValue get(String fieldName) {
        int fieldIndex = header.getFieldIndex(fieldName)
            .orElseThrow(() -> new IllegalArgumentException("Invalid fieldName: '" + fieldName + "'"));

        return values.get(fieldIndex);
    }


    public String getString(int fieldIndex) {
        return get(fieldIndex).asCharacter();
    }

    public String getString(int fieldIndex, String defaultValue) {
        DbfValue value = get(fieldIndex);
        return value.isNull() ? defaultValue : value.asCharacter();
    }

    public String getString(String fieldName) {
        return get(fieldName).asCharacter();
    }

    public String getString(String fieldName, String defaultValue) {
        DbfValue value = get(fieldName);
        return value.isNull() ? defaultValue : value.asCharacter();
    }


    public LocalDate getDate(int fieldIndex) {
        return get(fieldIndex).asDate();
    }

    public LocalDate getDate(int fieldIndex, LocalDate defaultValue) {
        DbfValue value = get(fieldIndex);
        return value.isNull() ? defaultValue : value.asDate();
    }

    public LocalDate getDate(String fieldName) {
        return get(fieldName).asDate();
    }

    public LocalDate getDate(String fieldName, LocalDate defaultValue) {
        DbfValue value = get(fieldName);
        return value.isNull() ? defaultValue : value.asDate();
    }


    public Number getNumber(int fieldIndex) {
        return get(fieldIndex).asNumeric();
    }

    public Number getNumber(int fieldIndex, Number defaultValue) {
        DbfValue value = get(fieldIndex);
        return value.isNull() ? defaultValue : value.asNumeric();
    }

    public Number getNumber(String fieldName) {
        return get(fieldName).asNumeric();
    }

    public Number getNumber(String fieldName, Number defaultValue) {
        DbfValue value = get(fieldName);
        return value.isNull() ? defaultValue : value.asNumeric();
    }


    public boolean getBoolean(int fieldIndex) {
        return get(fieldIndex).asLogical();
    }

    public boolean getBoolean(int fieldIndex, boolean defaultValue) {
        DbfValue value = get(fieldIndex);
        return value.isNull() ? defaultValue : value.asLogical();
    }

    public boolean getBoolean(String fieldName) {
        return get(fieldName).asLogical();
    }

    public boolean getBoolean(String fieldName, boolean defaultValue) {
        DbfValue value = get(fieldName);
        return value.isNull() ? defaultValue : value.asLogical();
    }


    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof DbfRecord
            && values.equals(((DbfRecord) obj).values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return values.toString();
    }

}
