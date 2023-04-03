package com.fastcampus.devmaker.devmaker.service;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDetailDto;
import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.exception.DevMakerException;
import com.fastcampus.devmaker.devmaker.repository.DeveloperRepository;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fastcampus.devmaker.devmaker.constant.DevMakerContant.MAX_JUNIOR_EXPERIENCE_YEARS;
import static com.fastcampus.devmaker.devmaker.constant.DevMakerContant.MIN_SENIOR_EXPERIENCE_YEARS;
import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;
import static com.fastcampus.devmaker.devmaker.type.DevelopSkillType.BACK_END;
import static com.fastcampus.devmaker.devmaker.type.DevelopSkillType.FRONT_END;
import static com.fastcampus.devmaker.devmaker.type.DeveloperLevel.*;
import static com.fastcampus.devmaker.devmaker.type.StateCode.EMPLOYED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @InjectMocks // <-- Mock은 '가짜'라는 뜻, 가짜 devMakerService를 주입해준다.
    private DevMakerService devMakerService;

    private final Developer defaultDeveloper = Developer.builder() // <-- 반복되어서 사용되는 부분은 이렇게 빼놓는 것이 좋다.
            .developerLevel(JUNIOR)
            .developSkillType(BACK_END)
            .experienceYears(3)
            .stateCode(EMPLOYED)
            .name("김훈섭")
            .age(26)
            .build();

    private CreateDeveloper.Request defaultCreateDeveloperRequest(DeveloperLevel level, DevelopSkillType skillType, Integer experienceYears) {
        return CreateDeveloper.Request.builder()
                .developerLevel(level)
                .developSkillType(skillType)
                .memberId("hunsope")
                .name("김훈섭")
                .age(26)
                .experienceYears(experienceYears)
                .build();
    }

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

        // 기존에 같은 이름을 가진 개발자가 존재하는지 확인하는 로직을 Mocking
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        // repository.save()를 했을 때, Developer 타입의 객체가 반환되는 로직을 Mocking
        given(developerRepository.save(any()))
                .willReturn(defaultDeveloper);

        // Mockito에서 제공하는 또하나의 기능 : createDeveloper()가 실행되는 과정에서 매개변수로 주어지는 Developer 타입의 데이터를 잡아채게 된다.
        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);


        // when (test 하고자하는 동작)
        devMakerService.createDeveloper(defaultCreateDeveloperRequest(JUNIOR, BACK_END, 3));


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

        // when (test 하고자하는 동작)
        DevMakerException exception = assertThrows(DevMakerException.class,
                () -> devMakerService.createDeveloper(defaultCreateDeveloperRequest(JUNIOR, BACK_END, 3)));

        assertEquals(DUPLICATED_MEMBER_ID, exception.getDMakerErrorCode());
    }

    @Test
    @DisplayName("개발자 생성 실패 - 연차가 적은 시니어")
    void createDeveloper_fail_low_senior() {
        // 7년차 개발자가 시니어로 등록되면 예외를 발생시킨다.
        // 실패나 성공하는 테스트를 할때는 경계값을 사용하자.
        DevMakerException seniorException = assertThrows(DevMakerException.class,
                () -> devMakerService.createDeveloper(defaultCreateDeveloperRequest(SENIOR, FRONT_END, MIN_SENIOR_EXPERIENCE_YEARS - 1)));

        // 예외 코드가 동일한지 확인
        assertEquals(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, seniorException.getDMakerErrorCode());

        // 주니어 테스트
        DevMakerException juniorException = assertThrows(DevMakerException.class,
                () -> devMakerService.createDeveloper(defaultCreateDeveloperRequest(JUNIOR, FRONT_END, MAX_JUNIOR_EXPERIENCE_YEARS + 1)));

        assertEquals(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, juniorException.getDMakerErrorCode());

        // 중니어 테스트
        DevMakerException jungniorException = assertThrows(DevMakerException.class,
                () -> devMakerService.createDeveloper(defaultCreateDeveloperRequest(JUNGNIOR, FRONT_END, MIN_SENIOR_EXPERIENCE_YEARS + 1)));

        assertEquals(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED, jungniorException.getDMakerErrorCode());
    }
}