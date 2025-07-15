package com.ureca.ocean.jjh.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserException extends RuntimeException{
    private final ErrorCode errorCode;
}
