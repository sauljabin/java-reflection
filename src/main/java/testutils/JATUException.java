package testutils;

/**
 * Excepción estándar.
 */
public class JATUException extends Exception {
    public JATUException(Throwable throwable) {
        super(throwable);
    }

    public JATUException(String s) {
        super(s);
    }
}
