package com.fastcampus.devmaker.devmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateCode {
    EMPLOYED("고용"),
    RETIRED("퇴직");

    private final String state;
}
