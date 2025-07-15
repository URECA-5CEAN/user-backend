package com.ureca.ocean.jjh.common.exception;

import com.ureca.ocean.jjh.common.BaseResponseDto;
import com.ureca.ocean.jjh.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.ureca.ocean.jjh")
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public BaseResponseDto<ErrorResponseDto> handleUserException(UserException e){
        log.error(e.getMessage(),e);
        return BaseResponseDto.fail(e.getErrorCode());
    }

}
