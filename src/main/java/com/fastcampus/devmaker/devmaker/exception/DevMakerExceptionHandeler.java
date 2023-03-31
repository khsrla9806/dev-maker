package com.fastcampus.devmaker.devmaker.exception;

import com.fastcampus.devmaker.devmaker.dto.DevMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice // <-- Global Exception 핸들링을 하기 위한 클래스
public class DevMakerExceptionHandeler {
    @ResponseStatus(value = HttpStatus.CONFLICT) // <-- 그에 맞는 Http Status를 넘겨주는 방법
    @ExceptionHandler(DevMakerException.class) // <-- Controller에서 발생하는 DevMakerException은 모두 여기 메서드에서 처리
    public DevMakerErrorResponse handelerException(DevMakerException e, HttpServletRequest request) {
        log.error("errorCode: {}, url {}, message {}", e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DevMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class, // <-- 정해놓은 url에 다른 Request 요청을 했을 때 발생
            MethodArgumentNotValidException.class // <-- 요청으로 들어온 @Valid에서 유효성 검사가 통과되지 못 했을 때
    })
    public DevMakerErrorResponse handlerBadRequest(Exception e, HttpServletRequest request) {
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage());

        return DevMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(INVALID_REQUEST.getMessage())
                .build();
    }

    // 최후의 보류로서 모든 Exception을 처리
    @ExceptionHandler(Exception.class)
    public DevMakerErrorResponse handleException(Exception e, HttpServletRequest request) {
        log.error("errorCode: {}, url {}, message {}", request.getRequestURI(), e.getMessage());

        return DevMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
