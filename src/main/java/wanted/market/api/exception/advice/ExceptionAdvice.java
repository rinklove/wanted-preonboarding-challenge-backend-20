package wanted.market.api.exception.advice;


import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wanted.market.api.exception.ErrorResult;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class,IllegalStateException.class,
            DataIntegrityViolationException.class, NullPointerException.class,
    RuntimeException.class, NoResultException.class})
    public ErrorResult illegalExceptionHandler(RuntimeException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ErrorResult accessDeniedExceptionHandler(RuntimeException e) {
        return new ErrorResult(HttpStatus.FORBIDDEN.value(), e.getMessage(), LocalDateTime.now());
    }


}
