package org.sopt.global.api.exception;

import org.sopt.global.api.code.GlobalErrorCode;
import org.sopt.global.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {

        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof BaseException baseException) {
                return ResponseEntity
                        .status(baseException.getErrorCode().getStatus())
                        .body(ApiResponse.error(baseException.getMessage()));
            }
            cause = cause.getCause();
        }
        return ResponseEntity
                .status(GlobalErrorCode.INVALID_REQUEST.getStatus())
                .body(ApiResponse.error(GlobalErrorCode.INVALID_REQUEST.getMessage()));

    }
    //그밖의예외드르
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}