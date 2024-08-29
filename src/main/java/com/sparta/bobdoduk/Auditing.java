package com.sparta.bobdoduk;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener .class)
public abstract class Auditing {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    @CreatedBy
    private String created_by;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated_at;

    @LastModifiedBy
    private String updated_by;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deleted_at;

    private String deleted_by;


    public boolean isDeletedSoftly() {
        return deleted_at != null;
    }

    public void undoDelete() {
        this.deleted_at = null;
    }
}
