package be.twofold.tinydbf;

import java.time.*;
import java.util.*;

public final class DbfHeader implements Iterable<DbfField> {

    private final LocalDate lastModified;
    private final int numberOfRecords;
    private final int headerLength;
    private final int recordLength;
    private final List<DbfField> fields;
    private transient final Map<String, Integer> fieldIndex;

    public DbfHeader(LocalDate lastModified, int numberOfRecords, int headerLength, int recordLength, List<DbfField> fields) {
        this.lastModified = Objects.requireNonNull(lastModified, "lastModified");
        this.numberOfRecords = numberOfRecords;
        this.headerLength = headerLength;
        this.recordLength = recordLength;
        this.fields = new ArrayList<>(fields);
        this.fieldIndex = createFieldIndex(fields);
    }

    @Override
    public Iterator<DbfField> iterator() {
        return Collections.unmodifiableList(fields).iterator();
    }


    public LocalDate getLastModified() {
        return lastModified;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public int getRecordLength() {
        return recordLength;
    }

    public int getFieldCount() {
        return fields.size();
    }

    public DbfField getField(int index) {
        return fields.get(index);
    }

    public OptionalInt getFieldIndex(String fieldName) {
        Integer index = fieldIndex.get(fieldName);
        return index == null ? OptionalInt.empty() : OptionalInt.of(index);
    }


    private Map<String, Integer> createFieldIndex(List<DbfField> fields) {
        Map<String, Integer> fieldIndex = new HashMap<>();
        for (int i = 0; i < fields.size(); i++) {
            fieldIndex.put(fields.get(i).getName(), i);
        }
        return fieldIndex;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DbfHeader)) return false;

        DbfHeader other = (DbfHeader) obj;
        return lastModified.equals(other.lastModified)
            && numberOfRecords == other.numberOfRecords
            && headerLength == other.headerLength
            && recordLength == other.recordLength
            && fields.equals(other.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastModified, numberOfRecords, headerLength, recordLength, fields);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
            .append("DbfHeader(lastModified=").append(lastModified)
            .append(", numberOfRecords=").append(numberOfRecords)
            .append(", headerLength=").append(headerLength)
            .append(", recordLength=").append(recordLength)
            .append(", fields=[");

        for (DbfField field : fields) {
            builder.append("\n\t").append(field);
        }

        return builder.append("\n])").toString();
    }

}
