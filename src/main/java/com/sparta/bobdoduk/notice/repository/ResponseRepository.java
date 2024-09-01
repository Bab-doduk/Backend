package com.sparta.bobdoduk.notice.repository;

import com.sparta.bobdoduk.notice.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResponseRepository extends JpaRepository<Response, UUID> {
    Optional<Response> findByInquiryId(UUID inquiryId);
}
