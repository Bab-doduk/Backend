package com.sparta.bobdoduk.notice.repository;

import com.sparta.bobdoduk.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {

}
