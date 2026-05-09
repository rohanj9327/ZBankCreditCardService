package com.zbank.creditcard.repository;

import com.zbank.creditcard.entity.Applicants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantsRepository extends JpaRepository<Applicants, Long> {

    boolean existsByEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email);
}
