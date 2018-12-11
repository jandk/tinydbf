package be.tjoener.tinydbf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class DbfHeader {

    private final LocalDate lastModified;
    private final int numberOfRecords;
    private final int headerLength;
    private final int recordLength;
    private final List<DbfField> fields;

    public DbfHeader(LocalDate lastModified, int numberOfRecords, int headerLength, int recordLength, List<DbfField> fields) {
        this.lastModified = requireNonNull(lastModified, "lastModified");
        this.numberOfRecords = numberOfRecords;
        this.headerLength = headerLength;
        this.recordLength = recordLength;
        this.fields = new ArrayList<>(fields);
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

    public List<DbfField> getFields() {
        return Collections.unmodifiableList(fields);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

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
        return String.format(
                "DbfHeader(lastModified=%s, numberOfRecords=%d, headerLength=%d, recordLength=%d, fields=%s)",
                lastModified, numberOfRecords, headerLength, recordLength, fields
        );
    }

}
