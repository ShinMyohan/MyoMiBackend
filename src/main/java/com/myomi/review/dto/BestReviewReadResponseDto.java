package com.myomi.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BestReviewReadResponseDto {
    private Long reviewNum;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    private LocalDateTime createdDate;
}
