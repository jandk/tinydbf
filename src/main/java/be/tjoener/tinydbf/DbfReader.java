package be.tjoener.tinydbf;

import be.tjoener.tinydbf.value.*;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.time.*;
import java.util.*;

public final class DbfReader {
    private static final int HEADER_SIZE = 32;
    private static final int FIELD_SIZE = 32;
    private static final int FIELD_TERMINATOR = 0x0d;

    private static final int RECORD_PRESENT = 0x20;
    private static final int RECORD_ABSENT = 0x2a;
    private static final int FILE_TERMINATOR = 0x1a;

    private final InputStream inputStream;
    private final Charset charset;

    private final DbfHeader header;

    public DbfReader(InputStream inputStream) {
        this(inputStream, StandardCharsets.ISO_8859_1);
    }

    public DbfReader(InputStream inputStream, Charset charset) {
        this.charset = charset;
        this.inputStream = inputStream.markSupported()
            ? inputStream
            : new BufferedInputStream(inputStream);

        try {
            this.header = readHeader();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public DbfHeader getHeader() {
        return header;
    }


    private DbfHeader readHeader() throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(read(HEADER_SIZE))
            .order(ByteOrder.LITTLE_ENDIAN);

        buffer.get(); // Skip
        int year = Byte.toUnsignedInt(buffer.get());
        int month = Byte.toUnsignedInt(buffer.get());
        int dayOfMonth = Byte.toUnsignedInt(buffer.get());

        LocalDate lastModified = LocalDate.of(1900 + year, month, dayOfMonth);
        int numberOfRecords = buffer.getInt();
        int headerLength = buffer.getShort();
        int recordLength = buffer.getShort();

        List<DbfField> fields = new ArrayList<>();
        while (peek() != FIELD_TERMINATOR) {
            DbfField field = readField();
            fields.add(field);
        }
        skip(1);

        return new DbfHeader(lastModified, numberOfRecords, headerLength, recordLength, fields);
    }

    private DbfField readField() throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(read(FIELD_SIZE));
        String name = new String(buffer.array(), 0, 11, StandardCharsets.US_ASCII).trim();
        DbfType type = DbfType.valueOf((char) buffer.get(11));
        int length = Byte.toUnsignedInt(buffer.get(16));
        int decimalCount = Byte.toUnsignedInt(buffer.get(17));

        return new DbfField(name, type, length, decimalCount);
    }

    public DbfRecord nextRecord() throws IOException {
        Object[] row = nextRow();
        return row == null ? null : new DbfRecord(header, row);
    }

    public DbfValue[] nextRow() throws IOException {
        while (true) {
            int indicator = read();
            switch (indicator) {
                case RECORD_PRESENT:
                    return readRow();
                case RECORD_ABSENT:
                    skip(header.getRecordLength());
                    break;
                case FILE_TERMINATOR:
                    return null;
                default:
                    throw new IOException("Unexpected byte: " + indicator);
            }
        }
    }

    private DbfValue[] readRow() throws IOException {
        DbfValue[] record = new DbfValue[header.getFieldCount()];
        for (int i = 0; i < header.getFieldCount(); i++) {
            DbfField field = header.getField(i);
            record[i] = readValue(field);
        }
        return record;
    }

    private DbfValue readValue(DbfField field) throws IOException {
        byte[] bytes = read(field.getLength());
        String s = new String(bytes, charset).trim();
        if (s.isEmpty()) return null;

        switch (field.getType()) {
            case CHAR:
                return DbfValue.character(s);
            case DATE:
                return DbfValue.date(readDateValue(s));
            case FLOATING:
            case NUMERIC:
                return DbfValue.numeric(new StringNumber(s));
            case LOGICAL:
                return DbfValue.logical("YyTt".indexOf(s.charAt(0)) >= 0);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private LocalDate readDateValue(String s) {
        int year = Integer.parseInt(s.substring(0, 4));
        int month = Integer.parseInt(s.substring(4, 6));
        int dayOfMonth = Integer.parseInt(s.substring(6, 8));
        return LocalDate.of(year, month, dayOfMonth);
    }

    // region Helpers

    private int read() throws IOException {
        int result = inputStream.read();
        if (result == -1) {
            throw new IOException("EOF reached");
        }
        return result;
    }

    private byte[] read(int size) throws IOException {
        byte[] bytes = new byte[size];
        if (inputStream.read(bytes) != size) {
            throw new IOException("Not enough bytes read");
        }
        return bytes;
    }

    private void skip(int size) throws IOException {
        if (inputStream.skip(size) != size) {
            throw new IOException("Not enough bytes skipped");
        }
    }

    private int peek() throws IOException {
        inputStream.mark(1);
        int result = inputStream.read();
        inputStream.reset();
        return result;
    }

    // endregion

}
