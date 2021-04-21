package au.com.nab.icommerce.product.query.util;

import com.google.common.collect.ImmutableMap;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ESDocumentUtil {
    private static final Map<Class<?>, Map<String, FieldType>> documentFieldType = new ConcurrentHashMap<>();

    public static FieldType getFieldType(Class<?> esClass, String fieldName) {
        Map<String, FieldType> esClassFieldTypes =
                documentFieldType.computeIfAbsent(esClass, aClass -> getClassFieldTypes(esClass));

        return esClassFieldTypes.get(fieldName);
    }

    private static Map<String, FieldType> getClassFieldTypes(Class<?> esClass) {
        ImmutableMap.Builder<String, FieldType> fieldTypeMap = ImmutableMap.builder();
        Field[] fields = esClass.getDeclaredFields();
        for (Field field : fields) {
            boolean isESField = field.isAnnotationPresent(org.springframework.data.elasticsearch.annotations.Field.class);
            if (!isESField) {
                continue;
            }

            String fieldName = field.getName();
            org.springframework.data.elasticsearch.annotations.Field annotation = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
            fieldTypeMap.put(fieldName, annotation.type());
        }
        return fieldTypeMap.build();
    }
}
