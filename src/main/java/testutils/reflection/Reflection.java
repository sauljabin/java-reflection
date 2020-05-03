package testutils.reflection;

import java.lang.reflect.Field;

/**
 * Clase utilitaria que permite asignar o extraer un valor de campo privado o público dentro de una objeto.
 */
public final class Reflection {

    public static final String EXCEPTION_MESSAGE = "No such field: ";

    private Reflection() {
        throw new IllegalStateException("Utility class");
    }

    private static Field getField(Class<?> affectedClass, String fieldName) throws ReflectionException {
        if (affectedClass == null) {
            throw new ReflectionException("No such field: " + fieldName);
        }

        if (fieldName == null) {
            throw new ReflectionException("Null field");
        }

        try {
            return affectedClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return getField(affectedClass.getSuperclass(), fieldName);
        }
    }

    /**
     * Extrae el valor de un campo privado o público, sin la necesidad de invocar un método get.
     * Buscará el campo en el objeto en cuestion o en sus padres.
     *
     * @param affectedObject Objeto que contiene el campo
     * @param fieldName      Nombre del campo al que se le extraerá el valor
     * @return Valor del campo
     * @throws ReflectionException En caso de no existir el campo
     */
    public static Object getFieldValue(Object affectedObject, String fieldName) throws ReflectionException {
        Class<?> affectedClass = affectedObject == null ? null : affectedObject.getClass();
        Field affectedField = getField(affectedClass, fieldName);
        return getObjectFromField(affectedField, affectedObject);
    }

    /**
     * Asigna un valor a un campo privado o público de un objeto.
     * El campo será buscado en el objeto o en sus padres.
     *
     * @param affectedObject Objeto que contiene el campo
     * @param fieldName      Nombre del campo a asignar
     * @param newValue       Valor a asignar
     * @throws ReflectionException En caso de no existir el campo
     */
    public static void setFieldValue(Object affectedObject, String fieldName, Object newValue) throws ReflectionException {
        Class<?> affectedClass = affectedObject == null ? null : affectedObject.getClass();
        Field affectedField = getField(affectedClass, fieldName);
        setObjectToField(affectedField, affectedObject, newValue);
    }

    /**
     * Asigna un valor a un campo statico
     *
     * @param clazz     Clase afectada
     * @param fieldName Nombre del campo
     * @param newValue  Nuevo valor
     * @throws ReflectionException Si no existe el campo
     */
    public static void setStaticFieldValue(Class<?> clazz, String fieldName, Object newValue) throws ReflectionException {
        Field affectedField = getField(clazz, fieldName);
        setObjectToField(affectedField, null, newValue);
    }

    private static void setObjectToField(Field affectedField, Object affectedObject, Object newValue) throws ReflectionException {
        try {
            affectedField.setAccessible(true);
            affectedField.set(affectedObject, newValue);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Obtiene el valor de un campo static
     *
     * @param clazz     Clase afectada
     * @param fieldName Nombre del campo
     * @return Valor del campo
     * @throws ReflectionException Si no existe el campo
     */
    public static Object getStaticFieldValue(Class<?> clazz, String fieldName) throws ReflectionException {
        Field affectedField = getField(clazz, fieldName);
        return getObjectFromField(affectedField, null);
    }

    private static Object getObjectFromField(Field affectedField, Object affectedObject) throws ReflectionException {
        try {
            affectedField.setAccessible(true);
            return affectedField.get(affectedObject);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }
}
