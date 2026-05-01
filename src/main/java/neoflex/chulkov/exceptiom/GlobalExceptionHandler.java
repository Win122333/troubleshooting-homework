package neoflex.chulkov.exceptiom;

import neoflex.chulkov.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientNotFoundException.class)
    public ErrorDto clientNotFoundExceptionHandle(ClientNotFoundException e) {
        return new ErrorDto(
            HttpStatus.NOT_FOUND.value(),
            e.getMessage()
        );
    }
}
