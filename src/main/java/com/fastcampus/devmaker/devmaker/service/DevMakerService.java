package com.fastcampus.devmaker.devmaker.service;

import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.repository.DeveloperRepository;
import com.fastcampus.devmaker.devmaker.type.DevelopSkillType;
import com.fastcampus.devmaker.devmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DevMakerService {

    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper() {

    }
}
