package com.myomi.product.dto;

import java.util.List;

import com.myomi.qna.dto.QnaReadOneResponseDto;
import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewReadResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReadOneDto {
    private Long prodNum;
    private String sellerName;
    private String sellerId;
    private String category;
    private String name;
    private Long originPrice;
    private int percentage;
    private int week;
    private String detail;
    private String productImgUrl;
    private int status;
    private List<QnaReadOneResponseDto> qnas;
	private List<ReviewReadResponseDto> reviews;
	private List<ReviewDetailResponseDto> bestReviews;
}
