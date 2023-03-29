package com.fastcampus.devmaker.devmaker.exception;

import lombok.Getter;

@Getter
public class DevMakerException extends RuntimeException {
    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    public DevMakerException(DMakerErrorCode errorCode) {
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DevMakerException(DMakerErrorCode errorCode, String detailMessage) {
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }



}
