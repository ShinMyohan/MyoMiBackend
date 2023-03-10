package com.myomi.review.dto;

import com.myomi.order.entity.OrderDetail;
import com.myomi.review.entity.Review;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class ReviewSaveRequestDto {
    private String title;
    private String content;
    private float stars;
    private Long orderNum;
    private Long prodNum;
    private int sort;
    private LocalDateTime createdDate;
    private MultipartFile file;

    @Builder
    public ReviewSaveRequestDto(String title, String content, float stars, Long orderNum, Long prodNum, LocalDateTime createdDate, MultipartFile file,int sort) {
        this.title = title;
        this.content = content;
        this.stars = stars;
        this.orderNum = orderNum;
        this.prodNum = prodNum;
        this.createdDate = createdDate;
        this.file = file;
        this.sort=sort;
    }

    public Review toEntity(ReviewSaveRequestDto reviewSaveRequestDto, User user, OrderDetail orderDetail, String fileUrl) {
        LocalDateTime date = LocalDateTime.now();
        return Review.builder()
                .user(user)
                .title(reviewSaveRequestDto.getTitle())
                .content(reviewSaveRequestDto.getContent())
                .sort(reviewSaveRequestDto.getSort())
                .stars(reviewSaveRequestDto.getStars())
                .createdDate(date)
                .orderDetail(orderDetail)
                .reviewImgurl(fileUrl)
                .build();

    }
}
