package testutils.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase con métodos utilitarios para las pruebas de clases, métodos y campos con anotaciones.
 */
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
                throw new AssertionError(String.format("No testutils.annotation %s found", clazz.getName()));
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

    /**
     * Permite probar si una clase contiene una o varias anotaciones.
     *
     * @param classUnderTest     Clase que se desea verificar
     * @param expectedAnnotation Anotaciones que deberían estar presentes en la clase
     */
    public static void assertClassAnnotation(Class<?> classUnderTest, Class<?>... expectedAnnotation) {
        assertAnnotations(
                classUnderTest.toString(),
                Arrays.asList(classUnderTest.getAnnotations()),
                Arrays.asList(expectedAnnotation),
                null, null);
    }

    /**
     * Verifica si un campo de una clase contiene una anotación
     *
     * @param classUnderTest     Clase que se desea verificar
     * @param fieldUnderTest     Campo de la clase a verificar
     * @param expectedAnnotation Anotaciones que deberían estar presentes en el campo
     */
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

    /**
     * Verifica si existe una anotación en un método
     *
     * @param classUnderTest     Clase que contiene el método
     * @param methodUnderTest    Método a verificar
     * @param expectedAnnotation Anotaciones esperadas
     */
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

    /**
     * Verifica si existe una anotación para una clase pero además, prueba que un parametro de la
     * anotación exista y sea igual al esperado
     *
     * @param classUnderTest         Clase que contiene la anotación
     * @param expectedAnnotation     Anotación esperada
     * @param parameterName          Nombre del parámetre de la anotación
     * @param expectedParameterValue Valor esperado del parámetro de la anotación
     */
    public static void assertClassAnnotationParameter(Class<?> classUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        assertAnnotations(
                classUnderTest.toString(),
                Arrays.asList(classUnderTest.getAnnotations()),
                Arrays.asList(expectedAnnotation),
                parameterName, expectedParameterValue);
    }

    /**
     * Verifica si existe una anotación en un método, además verifica el valor de un parámetro de la anotación
     *
     * @param classUnderTest         Clase que contiene el método
     * @param methodUnderTest        Método a verificar
     * @param expectedAnnotation     Anotación esperada
     * @param parameterName          Parámetro a verificar
     * @param expectedParameterValue Valor del parámetro esperado
     */
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

    /**
     * Verifica si existe una anotación en un campo y incluye la verificación de un parámetro
     * de la anotación
     *
     * @param classUnderTest         Clase que contiene el campo
     * @param fieldUnderTest         Campo a verificar
     * @param expectedAnnotation     Anotación esperada
     * @param parameterName          Parámetro de la anotación
     * @param expectedParameterValue Valor del parámetro esperado
     */
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
