package be.twofold.tinydbf;

public enum DbfType {
    Char('C'),
    Date('D'),
    Floating('F'),
    Logical('L'),
    Numeric('N');

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
