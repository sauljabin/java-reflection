package testutils.reflection;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(RandomBeansExtension.class)
class ReflectionTest {

    public static final String FIELD_SUPERCLASS = "fieldSuperclass";
    public static final String FIELD = "field";
    private Dummy dummy;

    @Random
    private String value;

    @BeforeEach
    void setUp() {
        dummy = new Dummy();
    }

    @Test
    void shouldThrowsExceptionWhenInstanceReflectionClass() {
        Throwable exception = assertThrows(
                InvocationTargetException.class, () -> {
                    Constructor<Reflection> declaredConstructor = Reflection.class.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    declaredConstructor.newInstance();
                });

        assertThat(exception.getCause())
                .isInstanceOf(IllegalStateException.class);

        assertThat(exception.getCause().getMessage())
                .isEqualTo("Utility class");
    }

    @Test
    void shouldThrowsExceptionInGetFieldWhenClassIsNull() {
        Throwable exception = assertThrows(
                ReflectionException.class,
                () -> Reflection.getFieldValue(null, null)
        );

        assertThat(exception.getMessage())
                .isEqualTo("No such field: null");
    }

    @Test
    void shouldThrowsExceptionInGetFieldWhenFieldIsNull() {
        Throwable exception = assertThrows(
                ReflectionException.class,
                () -> Reflection.getFieldValue(dummy, null)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Null field");
    }

    @Test
    void shouldThrowsExceptionInGetFieldWhenFieldNotExits() {
        Throwable exception = assertThrows(
                ReflectionException.class,
                () -> Reflection.getFieldValue(dummy, "not_field")
        );

        assertThat(exception.getMessage())
                .isEqualTo("No such field: not_field");
    }

    @Test
    void shouldGetFieldFromDummyClass() throws ReflectionException {
        Object value = Reflection.getFieldValue(dummy, FIELD);
        assertThat(value)
                .isEqualTo("value");
    }

    @Test
    void shouldGetFieldFromDummySuperclass() throws ReflectionException {
        Object field = Reflection.getFieldValue(dummy, FIELD_SUPERCLASS);
        assertThat(field)
                .isEqualTo("valueSuperclass");
    }

    @Test
    void shouldThrowsExceptionInSetFieldWhenClassIsNull() {
        Throwable exception = assertThrows(
                ReflectionException.class,
                () -> Reflection.setFieldValue(null, null, null)
        );

        assertThat(exception.getMessage())
                .isEqualTo("No such field: null");
    }

    @Test
    void shouldThrowsExceptionInSetFieldWhenFieldIsNull() {
        Throwable exception = assertThrows(
                ReflectionException.class,
                () -> Reflection.setFieldValue(dummy, null, null)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Null field");
    }

    @Test
    void shouldGetAndSetField() throws ReflectionException {
        String expected = "expected";
        Reflection.setFieldValue(dummy, FIELD, expected);

        Object field = Reflection.getFieldValue(dummy, FIELD);

        assertThat(field)
                .isEqualTo(expected);
    }

    @Test
    void shouldGetAndSetFieldFromSuperclass() throws ReflectionException {
        String expected = "expected";
        Reflection.setFieldValue(dummy, FIELD_SUPERCLASS, expected);

        Object field = Reflection.getFieldValue(dummy, FIELD_SUPERCLASS);

        assertThat(field)
                .isEqualTo(expected);
    }

    @Test
    void setStaticField() throws ReflectionException {
        Reflection.setStaticFieldValue(DummyStatic.class, "staticField", value);
        Object result = Reflection.getStaticFieldValue(DummyStatic.class, "staticField");
        assertThat(result).isEqualTo(value);
    }

    @Test
    void throwExceptionIfFieldIsNull() {
        Throwable exception = assertThrows(
                ReflectionException.class,
                () -> Reflection.getStaticFieldValue(DummyStatic.class, value)
        );

        assertThat(exception.getMessage()).isEqualTo("No such field: " + value);
    }

    static class DummyStatic {
        private static String staticField = "value";
    }

    class DummySuperclass {
        private final String fieldSuperclass = "valueSuperclass";
    }

    class Dummy extends DummySuperclass {
        private final String field = "value";
    }
}
