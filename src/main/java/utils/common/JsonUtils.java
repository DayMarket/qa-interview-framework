package utils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Collection;

@UtilityClass
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(value);
    }

    public static String writeValueAsPrettyString(Object value) throws JsonProcessingException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    public static <T> T readValue(String content, Class<T> valueType) throws IOException {
        return OBJECT_MAPPER.readValue(content, valueType);
    }

    public static <T> T readValue(String content, JavaType valueType) throws IOException {
        return OBJECT_MAPPER.readValue(content, valueType);
    }

    public static JavaType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return OBJECT_MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }
}
