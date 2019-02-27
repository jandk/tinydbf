package be.tjoener.tinydbf;

public enum DbfType {
    CHAR('C'),
    DATE('D'),
    FLOATING('F'),
    LOGICAL('L'),
    NUMERIC('N');

    private final char code;

    DbfType(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public static DbfType valueOf(char code) {
        for (DbfType value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
