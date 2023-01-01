package plus.irbis.news.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import plus.irbis.news.dto.ErrorDto;
import plus.irbis.news.exceptions.CustomException;
import plus.irbis.news.web.response.NewsResponse;

@ControllerAdvice
public class ExceptionConfiguration {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<NewsResponse> handleMissingRequestHeaderException(CustomException ex) {
        NewsResponse response = new NewsResponse();

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        errorDto.setDescription(ex.getCause().getMessage());
        response.setError(errorDto);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
