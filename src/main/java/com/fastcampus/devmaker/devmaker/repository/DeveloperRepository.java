package com.fastcampus.devmaker.devmaker.repository;

import com.fastcampus.devmaker.devmaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
