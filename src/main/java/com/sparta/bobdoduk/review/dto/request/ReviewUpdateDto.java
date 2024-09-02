package com.sparta.bobdoduk.review.dto.request;

import lombok.Data;

@Data
public class ReviewUpdateDto {
    private Integer rating;
    private String comment;
}
