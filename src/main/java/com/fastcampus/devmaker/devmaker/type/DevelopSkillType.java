package com.fastcampus.devmaker.devmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DevelopSkillType {
    BACK_END("백엔드 개발자"),
    FRONT_END("프론트 개발자"),
    FULL_STACK("풀스택 개발자");

    private final String description;
}
