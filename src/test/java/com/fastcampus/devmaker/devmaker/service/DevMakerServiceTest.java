package com.fastcampus.devmaker.devmaker.service;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDetailDto;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDto;
import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode;
import com.fastcampus.devmaker.devmaker.exception.DevMakerException;
import com.fastcampus.devmaker.devmaker.repository.DeveloperRepository;
import com.fastcampus.devmaker.devmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import com.fastcampus.devmaker.devmaker.type.StateCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.devmaker.devmaker.type.DevelopSkillType.*;
import static com.fastcampus.devmaker.devmaker.type.DeveloperLevel.JUNIOR;
import static com.fastcampus.devmaker.devmaker.type.StateCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// @SpringBootTest // <-- SpringBoot에서 관리하는 모든 Bean을 등록한 후에 테스트를 시작
@ExtendWith(MockitoExtension.class) // Mockito를 사용하기 위한 어노테이션 (SpringBootTest는 삭제해줘야 한다.)
class DevMakerServiceTest {
    @Mock // <-- DevMakerService가 가지고 있는 의존성을 Mock으로 가져와준다
    DeveloperRepository developerRepository;

    @Mock
    RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks // <-- Mock은 '가짜'라는 뜻, 가짜 devMakerService를 주입해준다.
    private DevMakerService devMakerService;

    private final Developer defaultDeveloper = Developer.builder() // <-- 반복되어서 사용되는 부분은 이렇게 빼놓는 것이 좋다.
            .developerLevel(JUNIOR)
            .developSkillType(FRONT_END)
            .experienceYears(3)
            .stateCode(EMPLOYED)
            .name("hun")
            .age(12)
            .build();

    private final CreateDeveloper.Request defaultCreateDeveloperRequest = CreateDeveloper.Request.builder()
            .developerLevel(JUNIOR)
            .developSkillType(BACK_END)
            .memberId("hunsope")
            .name("김훈섭")
            .age(26)
            .experienceYears(3)
            .build();

    @Test
    @DisplayName("테스트 연습")
    void testSomething() {
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

    @Test
    @DisplayName("개발자 생성 성공")
    void createDeveloper_success() {
        // given (mock 정의)

        // 필요한 과정에서 mocking 작업을 정의 (검증해야되는 과정은 직접 정의해줘야 한다.)
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        // Mockito에서 제공하는 또하나의 기능 : createDeveloper()가 실행되는 과정에서 매개변수로 주어지는 Developer 타입의 데이터를 잡아채게 된다.
        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);


        // when (test 하고자하는 동작)
        devMakerService.createDeveloper(defaultCreateDeveloperRequest);


        // then (예상한 동작을 검증)
        verify(developerRepository, times(1)) // <-- developerRepositroy가 몇 번 호출되었는지 확인
                .save(captor.capture());                             // <-- save() 메서드가 호출될 때, 직접 Developer 타입을 캡처

        Developer developer = captor.getValue();

        assertEquals(JUNIOR, developer.getDeveloperLevel());
        assertEquals(BACK_END, developer.getDevelopSkillType());
        assertEquals("hunsope", developer.getMemberId());
        assertEquals("김훈섭", developer.getName());
        assertEquals(26, developer.getAge());
        assertEquals(3, developer.getExperienceYears());
    }

    @Test
    @DisplayName("개발자 생성 실패 - 반복되는 개발자")
    void createDeveloper_failure_with_duplicated() {
        // given (mock 정의)

        // 필요한 과정에서 mocking 작업을 정의 (검증해야되는 과정은 직접 정의해줘야 한다.)
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        // Mockito에서 제공하는 또하나의 기능 : createDeveloper()가 실행되는 과정에서 매개변수로 주어지는 Developer 타입의 데이터를 잡아채게 된다.
        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);


        // when (test 하고자하는 동작)
        DevMakerException exception = assertThrows(DevMakerException.class,
                () -> devMakerService.createDeveloper(defaultCreateDeveloperRequest));

        assertEquals(DUPLICATED_MEMBER_ID, exception.getDMakerErrorCode());
    }
}