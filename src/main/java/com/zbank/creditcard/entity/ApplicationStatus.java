package com.zbank.creditcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_card_application_status")
public class ApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        PENDING
        APPROVED
        REJECTED
     */
    private String status;

    /*
        Credit score from credit-score-service
     */
    private Integer creditScore;

    /*
        GOLD / PLATINUM / SILVER
     */
    private String cardType;

    /*
        Failure reason if rejected
     */
    private String failureReason;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Applicants applicants;
}