package com.sparta.bobdoduk.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // ------ 4xx ------
    NOT_FOUND(HttpStatus.BAD_REQUEST, "요청사항을 찾지 못했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다"),
    STORE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "가게 생성 권한이 없습니다."),
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 음식 카테고리가 존재하지 않습니다."),
    AREA_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 지역 카테고리가 존재하지 않습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제 내역을 찾을 수 없습니다."),
    PAYMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 결제 내역에 접근할 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    // ------ 5xx ------
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
