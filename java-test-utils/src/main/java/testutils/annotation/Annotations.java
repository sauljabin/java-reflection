package testutils.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Annotations {

    private Annotations() {
        throw new IllegalStateException("Utility class");
    }

    private static void assertAnnotations(String fieldOrClass, List<Annotation> actualAnnotations, List<Class<?>> expectedAnnotation, String parameterName, Object parameterValue) {
        if (expectedAnnotation.size() != actualAnnotations.size()) {
            throw new AssertionError(
                    String.format("%s: Expected %d annotations, but found %d", fieldOrClass, expectedAnnotation.size(), actualAnnotations.size())
            );
        }
        expectedAnnotation.forEach(clazz -> {
            List<Annotation> annotations = actualAnnotations.stream()
                    .filter(annotation -> annotation.annotationType().isAssignableFrom(clazz))
                    .collect(Collectors.toList());

            if (annotations.isEmpty()) {
                throw new AssertionError(String.format("No annotation found for %s", clazz.getName()));
            }

            if (parameterName != null) {

                if (parameterName.isEmpty()) {
                    throw new AssertionError(String.format("Parameter name not allowed", parameterName));
                }

                annotations.forEach(annotation -> {

                    List<Method> methods = Arrays.asList(annotation.annotationType().getDeclaredMethods()).stream()
                            .filter(method -> method.getName().equals(parameterName))
                            .collect(Collectors.toList());

                    if (methods.isEmpty()) {
                        throw new AssertionError(String.format("No parameter %s found", parameterName));
                    }

                    methods.stream().forEach(method -> {

                        Object result;

                        try {
                            result = method.invoke(annotation, (Object[]) null);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new AssertionError(e);
                        }

                        if (!result.equals(parameterValue)) {
                            throw new AssertionError(String.format("Expected: %s and was %s for parameter %s", parameterValue, result, parameterName));
                        }

                    });
                });
            }

        });
    }

    public static void assertClassAnnotation(Class<?> classUnderTest, Class<?>... expectedAnnotation) {
        assertAnnotations(
                classUnderTest.toString(),
                Arrays.asList(classUnderTest.getAnnotations()),
                Arrays.asList(expectedAnnotation),
                null, null);
    }

    public static void assertFieldAnnotation(Class<?> classUnderTest, String fieldUnderTest, Class<?>... expectedAnnotation) {
        try {
            assertAnnotations(
                    fieldUnderTest,
                    Arrays.asList(classUnderTest.getDeclaredField(fieldUnderTest).getAnnotations()),
                    Arrays.asList(expectedAnnotation),
                    null, null);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    public static void assertMethodAnnotation(Class<?> classUnderTest, String methodUnderTest, Class<?>... expectedAnnotation) {
        try {
            assertAnnotations(
                    methodUnderTest,
                    Arrays.asList(classUnderTest.getDeclaredMethod(methodUnderTest).getAnnotations()),
                    Arrays.asList(expectedAnnotation),
                    null, null);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    public static void assertClassAnnotationParameter(Class<?> classUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        assertAnnotations(
                classUnderTest.toString(),
                Arrays.asList(classUnderTest.getAnnotations()),
                Arrays.asList(expectedAnnotation),
                parameterName, expectedParameterValue);
    }

    public static void assertMethodAnnotationParameter(Class<?> classUnderTest, String methodUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        try {
            assertAnnotations(
                    methodUnderTest,
                    Arrays.asList(classUnderTest.getDeclaredMethod(methodUnderTest).getAnnotations()),
                    Arrays.asList(expectedAnnotation),
                    parameterName,
                    expectedParameterValue
            );
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    public static void assertFieldAnnotationParameter(Class<?> classUnderTest, String fieldUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        try {
            assertAnnotations(
                    fieldUnderTest,
                    Arrays.asList(classUnderTest.getDeclaredField(fieldUnderTest).getAnnotations()),
                    Arrays.asList(expectedAnnotation),
                    parameterName,
                    expectedParameterValue);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }
}
