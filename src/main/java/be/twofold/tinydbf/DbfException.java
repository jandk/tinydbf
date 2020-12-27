package be.twofold.tinydbf;

public final class DbfException extends RuntimeException {
    public DbfException() {
    }

    public DbfException(String message) {
        super(message);
    }

    public DbfException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbfException(Throwable cause) {
        super(cause);
    }
}
