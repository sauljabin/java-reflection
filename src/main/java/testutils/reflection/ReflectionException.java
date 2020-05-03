package testutils.reflection;

/**
 * Excepción para para representar errores en la clase {@link Reflection}.
 */
public class ReflectionException extends Exception {
    public ReflectionException(String s) {
        super(s);
    }

    public ReflectionException(Throwable throwable) {
        super(throwable);
    }
}
