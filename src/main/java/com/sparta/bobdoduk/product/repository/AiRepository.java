package com.sparta.bobdoduk.product.repository;

import com.sparta.bobdoduk.product.domain.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AiRepository extends JpaRepository<Ai, UUID> {
}
