package com.fastcampus.devmaker.devmaker.controller;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDetailDto;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDto;
import com.fastcampus.devmaker.devmaker.dto.EditDeveloper;
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

    private final DevMakerService devMakerService; /* Dependency Injection(DI) + Inversion of Control(IoC)의 원리 */

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDeveloper(@Valid @RequestBody final CreateDeveloper.Request request) {
        log.info("POST: request : {}", request);

        return devMakerService.createDeveloper(request);
    }

    @GetMapping("/developers")
    public List<DeveloperDto> getAllEmployedDevelopers() {
        log.info("GET: /developers HTTP/1.1");

        return devMakerService.getEmployedDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloper(@PathVariable final String memberId) {
        log.info("GET: /developers/:memberId HTTP/1.1");
        return devMakerService.getDeveloper(memberId);
    }

    @PutMapping("/developer/{memberId}") /* validation(@Valid)과 data binding(path parameter) */
    public DeveloperDetailDto editDeveloper(@PathVariable final String memberId, @Valid @RequestBody final EditDeveloper.Request request) {
        log.info("PUT: /developer/:memberId HTTP/1.1");

        return devMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable final String memberId) {
        log.info("DELETE: /developer/:memberId HTTP/1.1");

        return devMakerService.deleteDeveloper(memberId);
    }
}
