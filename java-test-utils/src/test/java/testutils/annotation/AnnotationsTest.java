package testutils.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationsTest {

    @Test
    void shouldThrowsExceptionWhenInstanceReflectionClass() {
        Throwable exception = assertThrows(
                InvocationTargetException.class, () -> {
                    Constructor<Annotations> declaredConstructor = Annotations.class.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    declaredConstructor.newInstance();
                });

        assertThat(exception.getCause())
                .isInstanceOf(IllegalStateException.class);

        assertThat(exception.getCause().getMessage())
                .isEqualTo("Utility class");
    }

    @Test
    void shouldThrowExceptionIfClassHaveNotAnnotations() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertClassAnnotation(DummyWithoutAnnotation.class, DummyClassAnnotation.class)
        );

        assertThat(exception.getMessage())
                .contains("Expected 1 annotations, but found 0");
    }

    @Test
    void shouldThrowExceptionIfClassAnnotationDoesNotExists() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertClassAnnotation(Dummy.class, DummyClassAnnotationUnused.class)
        );

        assertThat(exception.getMessage())
                .contains("No annotation found for testutils.annotation.AnnotationsTest$DummyClassAnnotationUnused");
    }

    @Test
    void shouldNotThrowsExceptionIfClassAnnotationExists() {
        Annotations.assertClassAnnotation(Dummy.class, DummyClassAnnotation.class);
    }

    @Test
    void shouldThrowExceptionIfFieldHaveNotAnnotations() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotation(DummyWithoutAnnotation.class, "dummyWithoutAnnotation", DummyFieldAnnotation.class)
        );

        assertThat(exception.getMessage())
                .contains("Expected 1 annotations, but found 0");
    }

    @Test
    void shouldThrowExceptionIfFieldAnnotationDoesNotExists() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotation(Dummy.class, "dummy", DummyFieldAnnotationUnused.class)
        );

        assertThat(exception.getMessage())
                .contains("No annotation found for testutils.annotation.AnnotationsTest$DummyFieldAnnotationUnused");
    }

    @Test
    void shouldThrowExceptionIfFieldDoesNotExists() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotation(Dummy.class, "no_dummyField")
        );

        assertThat(exception.getMessage())
                .contains("java.lang.NoSuchFieldException: no_dummyField");
    }

    @Test
    void shouldNotThrowsExceptionIfFieldAnnotationExists() {
        Annotations.assertFieldAnnotation(Dummy.class, "dummy", DummyFieldAnnotation.class);
    }

    @Test
    void shouldThrowExceptionIfMethodHaveNotAnnotations() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertMethodAnnotation(DummyWithoutAnnotation.class, "dummyMethodWithoutAnnotation", DummyMethodAnnotation.class)
        );

        assertThat(exception.getMessage())
                .contains("Expected 1 annotations, but found 0");
    }

    @Test
    void shouldThrowExceptionIfMethodAnnotationDoesNotExists() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertMethodAnnotation(Dummy.class, "dummyMethod", DummyMethodAnnotationUnused.class)
        );

        assertThat(exception.getMessage())
                .contains("No annotation found for testutils.annotation.AnnotationsTest$DummyMethodAnnotationUnused");
    }

    @Test
    void shouldThrowExceptionIfMethodDoesNotExists() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertMethodAnnotation(Dummy.class, "no_dummyMethod")
        );

        assertThat(exception.getMessage())
                .contains("java.lang.NoSuchMethodException: testutils.annotation.AnnotationsTest$Dummy.no_dummyMethod()");
    }

    @Test
    void shouldNotThrowsExceptionIfMethodAnnotationExists() {
        Annotations.assertMethodAnnotation(Dummy.class, "dummyMethod", DummyMethodAnnotation.class);
    }

    @Test
    void shouldThrowExceptionIfFieldInAnnotationDoesNotExists() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotation(Dummy.class, "dummy", DummyFieldAnnotationUnused.class)
        );

        assertThat(exception.getMessage())
                .contains("No annotation found for testutils.annotation.AnnotationsTest$DummyFieldAnnotationUnused");
    }

    @Test
    void shouldThrowExceptionIfAnnotationHaveNotParameter() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertClassAnnotationParameter(Dummy.class, DummyClassAnnotation.class, "noParameter", null)
        );

        assertThat(exception.getMessage())
                .contains("No parameter noParameter found");
    }

    @Test
    void shouldThrowExceptionIfClassAnnotationHaveANotAllowedParameter() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertClassAnnotationParameter(Dummy.class, DummyClassAnnotation.class, "", null)
        );

        assertThat(exception.getMessage())
                .contains("Parameter name not allowed");
    }

    @Test
    void shouldThrowExceptionIfAnnotationParameterIsNotEqual() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertClassAnnotationParameter(Dummy.class, DummyClassAnnotation.class, "value", "value")
        );

        assertThat(exception.getMessage())
                .contains("Expected: value and was test_value for parameter value");
    }

    @Test
    void shouldNotThrowsExceptionIfClassAnnotationParameterIsEqual() {
        Annotations.assertClassAnnotationParameter(Dummy.class, DummyClassAnnotation.class, "value", "test_value");
    }

    @Test
    void shouldThrowExceptionIfMethodAnnotationHaveNotParameter() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertMethodAnnotationParameter(Dummy.class, "dummyMethod", DummyMethodAnnotation.class, "noParameter", null)
        );

        assertThat(exception.getMessage())
                .contains("No parameter noParameter found");
    }

    @Test
    void shouldThrowExceptionIfMethodAnnotationHaveANotAllowedParameter() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertMethodAnnotationParameter(Dummy.class, "dummyMethod", DummyMethodAnnotation.class, "", null)
        );

        assertThat(exception.getMessage())
                .contains("Parameter name not allowed");
    }

    @Test
    void shouldThrowExceptionIfMethodAnnotationParameterIsNotEqual() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertMethodAnnotationParameter(Dummy.class, "dummyMethod", DummyMethodAnnotation.class, "value", "value")
        );

        assertThat(exception.getMessage())
                .contains("Expected: value and was test_value for parameter value");
    }

    @Test
    void shouldNotThrowsExceptionIfMethodAnnotationParameterIsEqual() {
        Annotations.assertMethodAnnotationParameter(Dummy.class, "dummyMethod", DummyMethodAnnotation.class, "value", "test_value");
    }

    @Test
    void shouldThrowExceptionIfFiledAnnotationHaveNotParameter() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotationParameter(Dummy.class, "dummy", DummyFieldAnnotation.class, "noParameter", null)
        );

        assertThat(exception.getMessage())
                .contains("No parameter noParameter found");
    }

    @Test
    void shouldThrowExceptionIfFiledAnnotationHaveANotAllowedParameter() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotationParameter(Dummy.class, "dummy", DummyFieldAnnotation.class, "", null)
        );

        assertThat(exception.getMessage())
                .contains("Parameter name not allowed");
    }

    @Test
    void shouldThrowExceptionIfFiledAnnotationParameterIsNotEqual() {
        Throwable exception = assertThrows(
                AssertionError.class,
                () -> Annotations.assertFieldAnnotationParameter(Dummy.class, "dummy", DummyFieldAnnotation.class, "value", "value")
        );

        assertThat(exception.getMessage())
                .contains("Expected: value and was test_value for parameter value");
    }

    @Test
    void shouldNotThrowsExceptionIfFiledAnnotationParameterIsEqual() {
        Annotations.assertFieldAnnotationParameter(Dummy.class, "dummy", DummyFieldAnnotation.class, "value", "test_value");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface DummyClassAnnotation {
        String value() default "test_value";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface DummyClassAnnotationUnused {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface DummyFieldAnnotation {
        String value() default "test_value";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface DummyFieldAnnotationUnused {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface DummyMethodAnnotation {
        String value() default "test_value";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface DummyMethodAnnotationUnused {

    }

    @DummyClassAnnotation
    class Dummy {
        @DummyFieldAnnotation
        String dummy;

        @DummyMethodAnnotation
        void dummyMethod() {

        }
    }

    class DummyWithoutAnnotation {
        String dummyWithoutAnnotation;

        void dummyMethodWithoutAnnotation() {

        }
    }

}
