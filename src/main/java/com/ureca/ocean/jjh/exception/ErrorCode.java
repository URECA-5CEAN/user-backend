package com.ureca.ocean.jjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //auth_backend : 10000
    //user_backend : 20000
    NOT_FOUND_USER(20001,"NOT_FOUND_USER","해당 사용자가 없습니다."),
    USER_ALREADY_EXIST(20002,"USER_ALREADY_EXIST","해당 사용자가 이미 있습니다."),
    USER_STATUS_SAVE_FAIL(20003,"USER_STATUS_SAVE_FAIL","사용자 STATUS를 저장하는 중 오류가 발생"),
    USER_STATUS_NOT_EXIST(20004,"USER_STATUS_NOT_EXIST","해당 사용자 STATUS가 존재하지 않습니다.");
    //map_backend : 30000

    //ai_backend : 40000
    private final int code;
    private final String name;
    private final String message;
}
