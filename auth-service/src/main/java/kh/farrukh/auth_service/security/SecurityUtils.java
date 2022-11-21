package kh.farrukh.auth_service.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SecurityUtils {

    public static <T> void writeToResponse(
            T responseBody,
            HttpServletResponse response,
            ObjectMapper objectMapper
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> data = objectMapper.convertValue(
                responseBody,
                new TypeReference<>() {
                }
        );
        objectMapper.writeValue(response.getOutputStream(), data);
    }
}
