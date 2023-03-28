package com.fastcampus.devmaker.devmaker.entity;

import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 사용하기 위한 어노테이션
@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DevelopSkillType developSkillType;

    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    @CreatedDate // Auditing 기능
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
