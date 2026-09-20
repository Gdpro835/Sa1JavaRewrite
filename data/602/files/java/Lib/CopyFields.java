package Lib;

import java.lang.reflect.Field;

public class CopyFields {

public static void copyFields(Object source, Object target) {
    if (source == null || target == null) return;

    Class<?> sourceClass = source.getClass();
    Class<?> targetClass = target.getClass();

    while (sourceClass != null) {
        Field[] fields = sourceClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Field targetField;
                try {
                    targetField = targetClass.getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    continue;
                }

                if (!targetField.getType().isAssignableFrom(field.getType())) {
                    continue;
                }

                Object value = field.get(source);
                targetField.set(target, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Falha ao copiar campo: " + field.getName(), e);
            }
        }
        sourceClass = sourceClass.getSuperclass();
    }
}
}