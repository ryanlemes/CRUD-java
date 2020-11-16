package com.crud.crudjava.exceptions;

import com.crud.crudjava.dto.FormErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle to get some error and convert it to the Form Error Dto.
 */
@RestControllerAdvice
public class ErrorCheckException {
    private final MessageSource messageSource;

    public ErrorCheckException(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorDto> handle(MethodArgumentNotValidException exception) {
       List<FormErrorDto> dto = new ArrayList<>();

       List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
       fieldErrors.forEach(e -> {
           String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
           FormErrorDto error = new FormErrorDto(e.getField(), message);
           dto.add(error);
       });

       return dto;
    }
}
