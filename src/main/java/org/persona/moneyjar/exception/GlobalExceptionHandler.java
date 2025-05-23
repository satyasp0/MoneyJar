package org.persona.moneyjar.exception;

import lombok.extern.slf4j.Slf4j;
import org.persona.moneyjar.model.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Satya
 * @created 05/07/2024 - 14:08
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.builder()
                .status(400)
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(errors)
                .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponseDto> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Long format is not compatible");
        errors.put("detail", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.builder()
                .status(400)
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(errors)
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponseDto> handleValidationExceptions(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "card type enum is not recognized");
        errors.put("detail", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.builder()
                .status(400)
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(errors)
                .build());
    }

    @ExceptionHandler(MoneyJarException.class)
    public ResponseEntity<BaseResponseDto> handleBusinessExceptions(MoneyJarException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("app", ex.getAppCode());
        errors.put("code", ex.getErrorCode());
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.builder()
                .status(400)
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(errors)
                .build());
    }

}
