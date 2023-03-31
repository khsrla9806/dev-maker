package com.fastcampus.devmaker.devmaker.dto;

import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import com.fastcampus.devmaker.devmaker.type.StateCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;

    private DevelopSkillType developSkillType;

    private Integer experienceYears;

    private StateCode stateCode;
    private String memberId;
    private String name;
    private Integer age;

    public static DeveloperDetailDto fromEntity(Developer developer) {
        return DeveloperDetailDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developSkillType(developer.getDevelopSkillType())
                .experienceYears(developer.getExperienceYears())
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .stateCode(developer.getStateCode())
                .build();
    }
}
