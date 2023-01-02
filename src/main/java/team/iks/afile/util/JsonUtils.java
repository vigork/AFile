package team.iks.afile.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import team.iks.afile.exception.ApplicationException;

/**
 * Json 序列化工具类
 *
 * @author vigork
 * At: 2023/1/2
 */
public abstract class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 序列化
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ApplicationException("序列化失败", e);
        }
    }

    /**
     * 反序列化及类型转换
     */
    public static<T> T convert(Object value, TypeReference<T> typeReference) {
        return objectMapper.convertValue(value, typeReference);
    }
}
