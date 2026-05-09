package com.zbank.creditcard.repository;

import com.zbank.creditcard.entity.IdentityDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument, Long> {
}
