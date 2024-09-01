package com.sparta.bobdoduk.notice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResDto {
    private UUID noticeId;
    private UUID userId;
    private String title;
    private String content;
}
