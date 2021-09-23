package testutils.reflection;

public class ReflectionException extends Exception {
    public ReflectionException(String s) {
        super(s);
    }

    public ReflectionException(Throwable throwable) {
        super(throwable);
    }
}
