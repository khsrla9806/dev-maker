package com.fastcampus.devmaker.devmaker.service;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDetailDto;
import com.fastcampus.devmaker.devmaker.dto.DeveloperDto;
import com.fastcampus.devmaker.devmaker.dto.EditDeveloper;
import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.exception.DevMakerException;
import com.fastcampus.devmaker.devmaker.repository.DeveloperRepository;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.*;

@RequiredArgsConstructor
@Service
public class DevMakerService {

    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developSkillType(request.getDevelopSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .build();

        developerRepository.save(developer);

        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        validateDeveloperLevel(request.getExperienceYears(), request.getDeveloperLevel());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DevMakerException(DUPLICATED_MEMBER_ID);
                });
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity).collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloper(String memberId) {
        return developerRepository.findByMemberId(memberId).map(DeveloperDetailDto::fromEntity).orElseThrow(() -> {
            throw new DevMakerException(NO_DEVELOPER);
        });
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateUpdateDeveloperRequest(request, memberId);

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(() -> {
            throw new DevMakerException(NO_DEVELOPER);
        });

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDevelopSkillType(request.getDevelopSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }

    private void validateUpdateDeveloperRequest(EditDeveloper.Request request, String memberId) {
        validateDeveloperLevel(request.getExperienceYears(), request.getDeveloperLevel());
    }

    private static void validateDeveloperLevel(Integer experienceYears, DeveloperLevel developerLevel) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DevMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DevMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DevMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }
}
