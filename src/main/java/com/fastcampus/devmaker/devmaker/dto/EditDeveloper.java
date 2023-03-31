package com.fastcampus.devmaker.devmaker.dto;

import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EditDeveloper {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DevelopSkillType developSkillType;

        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;
    }
}
