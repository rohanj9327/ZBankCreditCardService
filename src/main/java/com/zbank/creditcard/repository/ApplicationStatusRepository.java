package com.zbank.creditcard.repository;

import com.zbank.creditcard.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationStatusRepository
        extends JpaRepository<ApplicationStatus, Long> {

    Optional<ApplicationStatus> findByApplicantsId(Long applicantId);
}