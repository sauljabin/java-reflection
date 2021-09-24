package logbook.demo.reflection;

import com.github.javafaker.Faker;
import logbook.demo.annotations.DummyClassAnnotation;
import org.junit.jupiter.api.Test;
import testutils.reflection.ReflectionException;

import static org.assertj.core.api.Assertions.assertThat;
import static testutils.annotation.Annotations.assertClassAnnotation;
import static testutils.reflection.Reflection.*;

class DummyClassTest {

    public static final String FIELD_NAME = "dummyField";
    public static final String STATIC_FIELD_NAME = "staticDummyField";

    @Test
    void setPrivateFieldValue() throws ReflectionException {
        Faker faker = new Faker();
        DummyClass dummyClass = new DummyClass();
        String expectedValue = faker.lorem().sentence();

        setFieldValue(dummyClass, FIELD_NAME, expectedValue);
        assertThat(getFieldValue(dummyClass, FIELD_NAME)).isEqualTo(expectedValue);
    }

    @Test
    void setPrivateStaticFieldValue() throws ReflectionException {
        Faker faker = new Faker();
        String expectedValue = faker.lorem().sentence();

        setStaticFieldValue(DummyClass.class, STATIC_FIELD_NAME, expectedValue);
        assertThat(getStaticFieldValue(DummyClass.class, STATIC_FIELD_NAME)).isEqualTo(expectedValue);
    }

    @Test
    void assertClassAnnotationForDummyClass() {
        assertClassAnnotation(DummyClass.class, DummyClassAnnotation.class);
    }
}