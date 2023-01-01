package plus.irbis.news.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import plus.irbis.news.dto.ErrorDto;
import plus.irbis.news.web.response.NewsResponse;


@UtilityClass
public class ErrorUtil {
    @SneakyThrows
    public static void sendError(HttpServletResponse httpServletResponse, ObjectMapper objectMapper, int code,
                                 String description) {
        NewsResponse responseDto = new NewsResponse();

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(String.valueOf(code));
        errorDto.setDescription(description);
        responseDto.setError(errorDto);

        httpServletResponse.getOutputStream().write(objectMapper.writeValueAsBytes(responseDto));
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(code);
    }
}
