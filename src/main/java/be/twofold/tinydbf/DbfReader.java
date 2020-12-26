package be.twofold.tinydbf;

import be.twofold.tinydbf.value.*;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.time.*;
import java.util.*;

public final class DbfReader implements Iterator<DbfRecord>, AutoCloseable {
    private static final int HEADER_SIZE = 32;
    private static final int FIELD_SIZE = 32;
    private static final int FIELD_TERMINATOR = 0x0d;

    private static final int RECORD_PRESENT = 0x20;
    private static final int RECORD_ABSENT = 0x2a;
    private static final int FILE_TERMINATOR = 0x1a;

    private final Charset charset;
    private final DbfHeader header;
    private InputStream inputStream;

    public DbfReader(InputStream inputStream) throws IOException {
        this(inputStream, StandardCharsets.ISO_8859_1);
    }

    public DbfReader(InputStream inputStream, Charset charset) throws IOException {
        this.charset = charset;
        this.inputStream = inputStream.markSupported()
            ? inputStream
            : new BufferedInputStream(inputStream);

        this.header = readHeader();
    }


    public DbfHeader getHeader() {
        return header;
    }

    // region Reading header

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

    private DbfField readField() {
        ByteBuffer buffer = ByteBuffer.wrap(read(FIELD_SIZE));
        String name = new String(buffer.array(), 0, 11, StandardCharsets.US_ASCII).trim();
        DbfType type = DbfType.valueOf((char) buffer.get(11));
        int length = Byte.toUnsignedInt(buffer.get(16));
        int decimalCount = Byte.toUnsignedInt(buffer.get(17));

        return new DbfField(name, type, length, decimalCount);
    }

    // endregion


    @Override
    public boolean hasNext() {
        try {
            while (true) {
                switch (peek()) {
                    case RECORD_PRESENT:
                        return true;
                    case RECORD_ABSENT:
                        skip(header.getRecordLength() + 1);
                        break;
                    case FILE_TERMINATOR:
                        return false;
                    default:
                        throw new IOException("Unexpected byte: " + peek());
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public DbfRecord next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more rows available");
        }
        return new DbfRecord(header, readRow());
    }

    private List<DbfValue> readRow() {
        skip(1);
        List<DbfValue> record = new ArrayList<>(header.getFieldCount());
        for (DbfField field : header) {
            record.add(readValue(field));
        }
        return record;
    }


    // region Reading values

    private DbfValue readValue(DbfField field) {
        byte[] bytes = read(field.getLength());

        switch (field.getType()) {
            case CHAR:
                return DbfValue.character(parseCharacter(bytes));
            case DATE:
                return DbfValue.date(parseDate(bytes));
            case FLOATING:
            case NUMERIC:
                return DbfValue.numeric(parseNumber(bytes));
            case LOGICAL:
                return DbfValue.logical(parseBoolean(bytes));
            default:
                throw new UnsupportedOperationException();
        }
    }

    private String parseCharacter(byte[] bytes) {
        int index = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] != 0x20) {
                index = i + 1;
                break;
            }
        }

        return index == 0 ? null
            : new String(bytes, 0, index, charset);
    }

    private LocalDate parseDate(byte[] bytes) {
        for (byte b : bytes) {
            if (b < '0' || b > '9') {
                return null;
            }
        }

        int year = parseInt(bytes, 0, 4);
        int month = parseInt(bytes, 4, 6);
        int dayOfMonth = parseInt(bytes, 6, 8);
        return LocalDate.of(year, month, dayOfMonth);
    }

    private int parseInt(byte[] bytes, int from, int to) {
        int result = 0;
        for (int i = from; i < to; i++) {
            result = result * 10 + (bytes[i] - '0');
        }
        return result;
    }

    private Number parseNumber(byte[] bytes) {
        int offset = bytes.length;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != 0x20) {
                offset = i;
                break;
            }
        }

        if (offset == bytes.length) {
            return null;
        }
        int length = bytes.length - offset;
        return new StringNumber(new String(bytes, offset, length, StandardCharsets.US_ASCII));
    }

    private Boolean parseBoolean(byte[] bytes) {
        switch (bytes[0]) {
            case 'Y':
            case 'y':
            case 'T':
            case 't':
                return true;
            case 'N':
            case 'n':
            case 'F':
            case 'f':
                return false;
            default:
                return null;
        }
    }

    // endregion

    // region InputStream Helpers

    private int read() throws IOException {
        int result = inputStream.read();
        if (result == -1) {
            throw new IOException("EOF reached");
        }
        return result;
    }

    private byte[] read(int size) {
        try {
            byte[] bytes = new byte[size];
            if (inputStream.read(bytes) != size) {
                throw new IOException("Not enough bytes read");
            }
            return bytes;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void skip(int size) {
        try {
            if (inputStream.skip(size) != size) {
                throw new IOException("Not enough bytes skipped");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private int peek() throws IOException {
        inputStream.mark(1);
        int result = inputStream.read();
        inputStream.reset();
        return result;
    }

    @Override
    public void close() throws IOException {
        if (inputStream == null)
            return;
        try {
            inputStream.close();
        } finally {
            inputStream = null;
        }
    }

    // endregion

}
