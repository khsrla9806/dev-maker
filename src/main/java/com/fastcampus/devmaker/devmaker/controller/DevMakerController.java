package com.fastcampus.devmaker.devmaker.controller;

import com.fastcampus.devmaker.devmaker.dto.*;
import com.fastcampus.devmaker.devmaker.exception.DevMakerException;
import com.fastcampus.devmaker.devmaker.service.DevMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<DeveloperDto> getEmployedDevelopers() {
        log.info("GET: /developers HTTP/1.1");

        return devMakerService.getEmployedDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloper(@PathVariable String memberId) {
        log.info("GET: /developers/:memberId HTTP/1.1");
        return devMakerService.getDeveloper(memberId);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable String memberId, @Valid @RequestBody EditDeveloper.Request request) {
        log.info("PUT: /developer/:memberId HTTP/1.1");

        return devMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable String memberId) {
        log.info("DELETE: /developer/:memberId HTTP/1.1");

        return devMakerService.deleteDeveloper(memberId);
    }
}
