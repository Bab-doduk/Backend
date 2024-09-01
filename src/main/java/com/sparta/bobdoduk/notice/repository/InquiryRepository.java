package com.sparta.bobdoduk.notice.repository;

import com.sparta.bobdoduk.notice.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface InquiryRepository extends JpaRepository<Inquiry, UUID> {
    List<Inquiry> findByUserId(UUID id);
//    List<Inquiry> findAllByUserId(UUID userId);
}
