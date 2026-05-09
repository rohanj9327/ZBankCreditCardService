package com.zbank.creditcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_card_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicants applicants;

    private Integer creditScore;

    private String applicationStatus;

    private String cardType;

    private BigDecimal creditLimit;

    private Boolean additionalDocumentsRequired;

    private LocalDateTime appliedAt;
}
