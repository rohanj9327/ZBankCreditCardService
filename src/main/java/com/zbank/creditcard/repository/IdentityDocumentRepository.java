package com.zbank.creditcard.repository;

import com.zbank.creditcard.entity.IdentityDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument, Long> {
}
