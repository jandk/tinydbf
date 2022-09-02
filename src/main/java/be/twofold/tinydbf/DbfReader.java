package be.twofold.tinydbf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class DbfReader implements Iterator<DbfRecord>, AutoCloseable {
    private static final int HeaderSize = 32;
    private static final int FieldSize = 32;
    private static final int FieldTerminator = 0x0d;

    private static final int RecordActive = 0x20;
    private static final int RecordDeleted = 0x2a;
    private static final int EndOfFile = 0x1a;

    private final Charset charset;
    private final DbfHeader header;
    private InputStream inputStream;

    public DbfReader(InputStream inputStream) {
        this(inputStream, StandardCharsets.ISO_8859_1);
    }

    public DbfReader(InputStream inputStream, Charset charset) {
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

    private DbfHeader readHeader() {
        ByteBuffer buffer = ByteBuffer.wrap(read(HeaderSize))
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
        while (peek() != FieldTerminator) {
            DbfField field = readField();
            fields.add(field);
        }
        skip(1);

        return new DbfHeader(lastModified, numberOfRecords, headerLength, recordLength, fields);
    }

    private DbfField readField() {
        byte[] rawField = read(FieldSize);
        String name = parseFieldName(rawField);
        DbfType type = DbfType.valueOf((char) rawField[11]);
        int length = Byte.toUnsignedInt(rawField[16]);
        int decimalCount = Byte.toUnsignedInt(rawField[17]);

        return new DbfField(name, type, length, decimalCount);
    }

    private String parseFieldName(byte[] bytes) {
        int index = 0;
        for (int i = 10; i >= 0; i--) {
            if (bytes[i] != 0x00) {
                index = i + 1;
                break;
            }
        }

        return new String(bytes, 0, index, StandardCharsets.US_ASCII);
    }

    // endregion


    @Override
    public boolean hasNext() {
        while (true) {
            switch (peek()) {
                case RecordActive:
                    return true;
                case RecordDeleted:
                    skip(header.getRecordLength() + 1);
                    break;
                case EndOfFile:
                    return false;
                default:
                    throw new DbfException("Unexpected byte: " + peek());
            }
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
            case Char:
                return parseCharacter(bytes);
            case Date:
                return parseDate(bytes);
            case Floating:
            case Numeric:
                return parseNumber(bytes);
            case Logical:
                return parseBoolean(bytes);
        }
        throw new UnsupportedOperationException();
    }

    private DbfValue parseCharacter(byte[] bytes) {
        int index = 0;
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] != 0x20) {
                index = i + 1;
                break;
            }
        }

        if (index == 0) {
            return DbfValueNull.Instance;
        }

        String value = new String(bytes, 0, index, charset);
        return new DbfValueChar(value);
    }

    private DbfValue parseDate(byte[] bytes) {
        for (byte b : bytes) {
            if (b < '0' || b > '9') {
                return DbfValueNull.Instance;
            }
        }

        int year = parseInt(bytes, 0, 4);
        int month = parseInt(bytes, 4, 6);
        int dayOfMonth = parseInt(bytes, 6, 8);

        LocalDate value = LocalDate.of(year, month, dayOfMonth);
        return new DbfValueDate(value);
    }

    private int parseInt(byte[] bytes, int from, int to) {
        int result = 0;
        for (int i = from; i < to; i++) {
            result = result * 10 + (bytes[i] - '0');
        }
        return result;
    }

    private DbfValue parseNumber(byte[] bytes) {
        int offset = bytes.length;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != 0x20) {
                offset = i;
                break;
            }
        }

        if (offset == bytes.length) {
            return DbfValueNull.Instance;
        }
        int length = bytes.length - offset;
        String value = new String(bytes, offset, length, StandardCharsets.US_ASCII);
        return new DbfValueNumeric(new StringNumber(value));
    }

    private DbfValue parseBoolean(byte[] bytes) {
        switch (bytes[0]) {
            case 'Y':
            case 'y':
            case 'T':
            case 't':
                return DbfValueLogical.True;
            case 'N':
            case 'n':
            case 'F':
            case 'f':
                return DbfValueLogical.False;
            default:
                return DbfValueNull.Instance;
        }
    }

    // endregion

    // region InputStream Helpers

    private byte[] read(int size) {
        try {
            byte[] bytes = new byte[size];
            if (inputStream.read(bytes) != size) {
                throw new DbfException("Not enough bytes left");
            }
            return bytes;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void skip(int size) {
        try {
            if (inputStream.skip(size) != size) {
                throw new DbfException("Not enough bytes left");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private int peek() {
        try {
            inputStream.mark(1);
            int result = inputStream.read();
            inputStream.reset();
            return result;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
