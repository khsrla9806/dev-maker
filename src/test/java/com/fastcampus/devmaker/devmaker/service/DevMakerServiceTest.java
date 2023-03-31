package com.fastcampus.devmaker.devmaker.service;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDetailDto;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDto;
import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.repository.DeveloperRepository;
import com.fastcampus.devmaker.devmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import com.fastcampus.devmaker.devmaker.type.StateCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.fastcampus.devmaker.devmaker.type.DevelopSkillType.*;
import static com.fastcampus.devmaker.devmaker.type.DeveloperLevel.JUNIOR;
import static com.fastcampus.devmaker.devmaker.type.StateCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

// @SpringBootTest // <-- SpringBoot에서 관리하는 모든 Bean을 등록한 후에 테스트를 시작
@ExtendWith(MockitoExtension.class) // Mockito를 사용하기 위한 어노테이션 (SpringBootTest는 삭제해줘야 한다.)
class DevMakerServiceTest {
    @Mock // <-- DevMakerService가 가지고 있는 의존성을 Mock으로 가져와준다
    DeveloperRepository developerRepository;

    @Mock
    RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks // <-- Mock은 '가짜'라는 뜻, 가짜 devMakerService를 주입해준다.
    private DevMakerService devMakerService;

    @Test
    public void testSomething() {
        // Mock들의 동작을 정의
        // devMakerService가 실행되었을 때 -> developerRepository의 findByMemberId()가 실행되기 때문에 그 결과 값을 미리 정의해놔야 한다.
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                        .developerLevel(JUNIOR)
                        .developSkillType(FRONT_END)
                        .experienceYears(3)
                        .stateCode(EMPLOYED)
                        .name("hun")
                        .age(12)
                        .build()));

        DeveloperDetailDto developer = devMakerService.getDeveloper("memberId");

        assertEquals(JUNIOR, developer.getDeveloperLevel());
        assertEquals(FRONT_END, developer.getDevelopSkillType());
        assertEquals("hun", developer.getName());
    }
}