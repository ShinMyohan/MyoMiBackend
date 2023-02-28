package com.myomi.review.dto;
import com.myomi.order.entity.OrderDetail;
import com.myomi.review.entity.Review;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter@NoArgsConstructor

public class ReviewSaveRequestDto {
	
	private String title;
	private String content;
	private float stars;
	
	private Long orderNum;
	private Long prodNum;
	
	@Builder
	public ReviewSaveRequestDto(String title, String content, float stars, Long orderNum, Long prodNum) {
		this.title=title;
		this.content=content;
		this.stars=stars;
		this.orderNum = orderNum;
		this.prodNum = prodNum;
	}
	
//	public ProductDto toDto(Product product) {
//		return ProductDto.builder()
//				.seller(product.getSeller())
//				.category(product.getCategory())
//				.name(product.getName())
//				.originPrice(product.getOriginPrice())
//				.percentage(product.getPercentage())
//				.week(product.getWeek())
//				.detail(product.getDetail())
////				.fee(9) //기본값 9로 넣어주려고 셋팅
//				.build();
//	}
	
//	public Product toEntity(ProductSaveDto productSaveDto
//			, Seller seller
//			) {
//		return Product.builder()
//				.seller(seller)
//				.category(category)
//				.name(name)
//				.originPrice(originPrice)
//				.percentage(percentage)
//				.week(week)
//				.detail(detail)
//				.fee(9) //기본값 9로 넣어주려고 셋팅
//				.build();
//	}
	public Review toEntity(ReviewSaveRequestDto reviewSaveRequestDto, User user, OrderDetail orderDetail) {
		return Review.builder()
				.user(user)
				.title(reviewSaveRequestDto.getTitle())
				.content(reviewSaveRequestDto.getContent())
				.sort(3)
				.stars(reviewSaveRequestDto.getStars())
				.orderDetail(orderDetail)
				.build();
	}
}
