package com.fastcampus.devmaker.devmaker.controller;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.service.DevMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
