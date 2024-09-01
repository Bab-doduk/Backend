package com.sparta.bobdoduk.notice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReqDto {
    private String title;
    private String content;
}
