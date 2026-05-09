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

        private String status;

        @OneToOne
        @JoinColumn(name = "application_id")
        private Applicants applicants;
}
