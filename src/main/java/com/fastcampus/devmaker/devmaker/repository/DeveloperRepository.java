package com.fastcampus.devmaker.devmaker.repository;

import com.fastcampus.devmaker.devmaker.entity.Developer;
import com.fastcampus.devmaker.devmaker.type.StateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByMemberId(String memberId);

    List<Developer> findDevelopersByStateCodeEquals(StateCode stateCode);
}
