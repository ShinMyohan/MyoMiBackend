package com.myomi.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReviewDetailResponseDto {

    private String prodName;
    private Long reviewNum;
    private String userName;
    private int sort;
    private String title;
    private String content;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    private LocalDateTime createdDate;
    private float stars;
    private String file; 

    @Builder
    public ReviewDetailResponseDto(String userName, String prodName, Long reviewNum, String title, String content, int sort, LocalDateTime createdDate, float stars, String file) {
        this.userName = userName;
        this.prodName = prodName;
        this.reviewNum = reviewNum;
        this.title = title;
        this.content = content;
        this.sort = sort;
        this.createdDate = createdDate;
        this.stars = stars;
        this.file = file;
    }

    public ReviewDetailResponseDto(Review entity) {
        this.prodName = entity.getOrderDetail().getProduct().getName();
        this.reviewNum = entity.getReviewNum();
        this.userName = entity.getUser().getName();
        this.sort = entity.getSort();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.stars = entity.getStars();
        this.file = entity.getReviewImgUrl();
    }


}
