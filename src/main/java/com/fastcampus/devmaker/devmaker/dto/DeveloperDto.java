package com.fastcampus.devmaker.devmaker.dto;

import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperDto {
    private DeveloperLevel developerLevel;
    private DevelopSkillType developSkillType;
    private String memberId;

    public static DeveloperDto fromEntity(Developer developer) {
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developSkillType(developer.getDevelopSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}
