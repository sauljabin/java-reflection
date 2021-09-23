package testutils.reflection;

import java.lang.reflect.Field;

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

    public static Object getFieldValue(Object affectedObject, String fieldName) throws ReflectionException {
        Class<?> affectedClass = affectedObject == null ? null : affectedObject.getClass();
        Field affectedField = getField(affectedClass, fieldName);
        return getObjectFromField(affectedField, affectedObject);
    }

    public static void setFieldValue(Object affectedObject, String fieldName, Object newValue) throws ReflectionException {
        Class<?> affectedClass = affectedObject == null ? null : affectedObject.getClass();
        Field affectedField = getField(affectedClass, fieldName);
        setObjectToField(affectedField, affectedObject, newValue);
    }


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
