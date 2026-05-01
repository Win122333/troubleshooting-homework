package neoflex.chulkov.exceptiom;

import lombok.extern.slf4j.Slf4j;
import neoflex.chulkov.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientNotFoundException.class)
    public ErrorDto clientNotFoundExceptionHandle(ClientNotFoundException e) {
        return new ErrorDto(
            HttpStatus.NOT_FOUND.value(),
            e.getMessage()
        );
    }
    @ExceptionHandler(Exception.class)
    public ErrorDto exceptionHandle(Exception e) {
        log.error("Произошла непредвиденная ошибка", e);
        return new ErrorDto(
            HttpStatus.BAD_REQUEST.value(),
            e.getMessage()
        );
    }
}
