package be.tjoener.tinydbf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class DbfReader {
    private static final int HEADER_SIZE = 32;
    private static final int FIELD_SIZE = 32;
    private static final int FIELD_TERMINATOR = 0x0d;

    private final InputStream inputStream;
    final DbfHeader header;

    public DbfReader(InputStream inputStream) {
        this.inputStream = inputStream.markSupported()
                ? inputStream
                : new BufferedInputStream(inputStream);
        try {
            this.header = readHeader();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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

    // region Helpers

    private byte[] read(int size) throws IOException {
        byte[] bytes = new byte[size];
        if (inputStream.read(bytes) != size) {
            throw new IOException("Not enough bytes read");
        }
        return bytes;
    }

    private int peek() throws IOException {
        inputStream.mark(1);
        int result = inputStream.read();
        inputStream.reset();
        return result;
    }

    // endregion

}
