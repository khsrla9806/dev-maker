package com.fastcampus.devmaker.devmaker.controller;

import com.fastcampus.devmaker.devmaker.dto.DeveloperDto;
import com.fastcampus.devmaker.devmaker.service.DevMakerService;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.fastcampus.devmaker.devmaker.type.DevelopSkillType.BACK_END;
import static com.fastcampus.devmaker.devmaker.type.DevelopSkillType.FRONT_END;
import static com.fastcampus.devmaker.devmaker.type.DeveloperLevel.JUNIOR;
import static com.fastcampus.devmaker.devmaker.type.DeveloperLevel.SENIOR;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // <-- 이거 왜 자동 검색이 안됨?
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is; // <-- 이거 왜 자동 검색이 안됨?

// controller 쪽 bean들만 올려서 원하는 controller의 bean들을 올려서 테스트할 수 있도록 해줌
// 해당 controller에 접근할 때 필요한 부분들까지 같이 따라서 등록된다. (ex) DevMakerExceptionHandler
@WebMvcTest(DevMakerController.class)
class DevMakerControllerTest {
    @Autowired
    private MockMvc mockMvc; // WebMvcTest에서 제공해주는 Bean : 호출을 가상으로 만들어준다.

    @MockBean // DevMakerController가 의존하고 있는 빈을 가짜 Bean으로 등록해준다.
    private DevMakerService devMakerService;

    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception {
        DeveloperDto juniorDev = DeveloperDto.builder()
                        .developerLevel(JUNIOR)
                        .developSkillType(FRONT_END)
                        .memberId("kimh")
                        .build();

        DeveloperDto seniorDev = DeveloperDto.builder()
                .developerLevel(SENIOR)
                .developSkillType(BACK_END)
                .memberId("kimy")
                .build();

        given(devMakerService.getEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDev, seniorDev));


        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(
                        jsonPath("$.[0].developSkillType", is(FRONT_END.name()))
                )
                .andExpect(
                        jsonPath("$.[1].developSkillType", is(BACK_END.name()))
                )
                .andExpect(
                        jsonPath("$.[0].developerLevel", is(JUNIOR.name()))
                )
                .andExpect(
                        jsonPath("$.[1].developerLevel", is(SENIOR.name()))
                )
                .andExpect(
                        jsonPath("$.[0].memberId", is("kimh"))
                )
                .andExpect(
                        jsonPath("$.[1].memberId", is("kimy")
                )
        );
    }

}