package hayk.abajyan.rest.api.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);



    public static <T> T deserialize(String payload, TypeReference<T> type) throws IOException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        return objectMapper.readValue(payload, type);
    }


    public static String serialize(Object obj) {
        String value = "";
        try {
            value = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("ERROR:", e);
        }
        return value;
    }
}
