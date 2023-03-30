package com.fastcampus.devmaker.devmaker.controller;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDetailDto;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDto;
import com.fastcampus.devmaker.devmaker.service.DevMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody
public class DevMakerController {

    private final DevMakerService devMakerService;

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDeveloper(@Valid @RequestBody CreateDeveloper.Request request) {
        log.info("POST: request : {}", request);

        return devMakerService.createDeveloper(request);
    }

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        return devMakerService.getAllDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getAllDevelopers(@PathVariable String memberId) {
        return devMakerService.getDeveloper(memberId);
    }
}
