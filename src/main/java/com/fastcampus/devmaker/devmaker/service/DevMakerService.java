package com.fastcampus.devmaker.devmaker.service;

import com.fastcampus.devmaker.devmaker.dto.CreateDeveloper;
import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode;
import com.fastcampus.devmaker.devmaker.exception.DevMakerException;
import com.fastcampus.devmaker.devmaker.repository.DeveloperRepository;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import java.util.Optional;

import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.devmaker.devmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

@RequiredArgsConstructor
@Service
public class DevMakerService {

    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        Integer experienceYears = request.getExperienceYears();
        DeveloperLevel developerLevel = request.getDeveloperLevel();

        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DevMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DevMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DevMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DevMakerException(DUPLICATED_MEMBER_ID);
                });
    }
}
