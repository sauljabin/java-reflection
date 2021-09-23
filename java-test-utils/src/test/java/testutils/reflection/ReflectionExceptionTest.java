package testutils.reflection;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionExceptionTest {

    @Test
    void shouldGetMessage() {
        String expectedMessage = "expectedMessage";
        ReflectionException reflectionException = new ReflectionException(expectedMessage);

        assertThat(reflectionException.getMessage())
                .isEqualTo(expectedMessage);
    }

    @Test
    void shouldGetCause() {
        RuntimeException expectedCause = new RuntimeException();
        ReflectionException reflectionException = new ReflectionException(expectedCause);

        assertThat(reflectionException.getCause())
                .isSameAs(expectedCause);
    }

}
