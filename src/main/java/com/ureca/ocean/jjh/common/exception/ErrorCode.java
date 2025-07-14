package com.ureca.ocean.jjh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(20000, "INTERNAL_SERVER_ERROR",  "서버 내부 오류가 발생했습니다."),
    NOT_FOUND_USER(20001,"NOT_FOUND_USER","해당 사용자가 없습니다.");
    private final int code;
    private final String name;
    private final String message;
}
