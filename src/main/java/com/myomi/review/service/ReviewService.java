package com.myomi.review.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.entity.OrderDetailEmbedded;
import com.myomi.order.repository.OrderRepository;
import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewReadResponseDto;
import com.myomi.review.dto.ReviewSaveRequestDto;
import com.myomi.review.dto.ReviewUpdateRequestDto;
import com.myomi.review.entity.BestReview;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.BestReviewRepository;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class ReviewService {
	
	private final ReviewRepository rr;
	private final UserRepository ur;
	private final OrderRepository or;
	private final BestReviewRepository brr;
	
	public List<ReviewReadResponseDto>findProdReviewList(Long prodNum){
		List<Review> reviews = rr.findAllByProdNum(prodNum);
		List<ReviewReadResponseDto>list= new ArrayList<>(); 
		if(reviews.size()==0) {
			log.info("리뷰가 없습니다.");
		}else {
			for(Review review:reviews) {
				ReviewReadResponseDto dto = ReviewReadResponseDto.builder()
						.userId(review.getUser().getId())
						.pName(review.getOrderDetail().getProduct().getName())
						.reviewNum(review.getReviewNum())
						.title(review.getTitle())
						.content(review.getContent())
						.createdDate(review.getCreatedDate())
						.stars(review.getStars())
						
						.build();
						list.add(dto);				
			}
		}
		return list;
		 
	}
	
	
	
	@Transactional
	public List<ReviewReadResponseDto>findReviewList(String id){
		List<Review> reviews = rr.findAllByUserId(id);
		List<ReviewReadResponseDto>list= new ArrayList<>(); 
		if(reviews.size()==0) {
			log.info("리뷰가 없습니다.");
		}else {
			for(Review review:reviews) {
				ReviewReadResponseDto dto = ReviewReadResponseDto.builder()
						.userId(review.getUser().getId())
						.pName(review.getOrderDetail().getProduct().getName())
						.reviewNum(review.getReviewNum())
						.title(review.getTitle())
						.content(review.getContent())
						.createdDate(review.getCreatedDate())
						.stars(review.getStars())
						.build();
						list.add(dto);				
			}
		}
		return list;
		 
	}
	
	@Transactional
	public void addReview(ReviewSaveRequestDto reviewSaveDto, Authentication user,Long orderNum,Long prodNum) {
		String username=user.getName();
		LocalDateTime date = LocalDateTime.now(); 
		Optional<User> u=ur.findById(username);
		Optional<Order> o= or.findByOrderNumAndUser(orderNum, username);		
		OrderDetailEmbedded ode = OrderDetailEmbedded.builder()
								  .orderNum(o.get().getOrderNum())
								  .prodNum(prodNum)
								  .build();
		OrderDetail od = OrderDetail.builder()
						 .id(ode)
						 .build();
		Review r = Review.builder()
						.user(u.get())
						.title(reviewSaveDto.getTitle())
						.content(reviewSaveDto.getContent())
						.sort(3)
						.stars(reviewSaveDto.getStars())
						.createdDate(date)
						.orderDetail(od)
						.build();
		
		rr.save(r);
	}
	
	//리뷰상세보기
	@Transactional
	public ReviewDetailResponseDto detailReview(Long reviewNum) {
		Review review = rr.findReviewById(reviewNum);
		return new ReviewDetailResponseDto(review);
		}
	
	
	//판매자별 리뷰검색
	public List<ReviewReadResponseDto> findSellerReviewList(Authentication seller){
		List<Review> reviews = rr.findAllBySeller(seller.getName());
		List<ReviewReadResponseDto>list= new ArrayList<>(); 
		if(reviews.size()==0) {
			log.info("리뷰가 없습니다.");
		}else {
			for(Review review:reviews) {
				ReviewReadResponseDto dto = ReviewReadResponseDto.builder()
						.userId(review.getUser().getId())
						.pName(review.getOrderDetail().getProduct().getName())
						.reviewNum(review.getReviewNum())
						.title(review.getTitle())
						.content(review.getContent())
						.createdDate(review.getCreatedDate())
						.stars(review.getStars())
						.build();
						list.add(dto);				
			}
		}
		return list;
		 
	}
	
	//상품별 베스트리뷰 조회
	public List<ReviewReadResponseDto>findProdBestReviewList(Long prodNum){
		List<Review> reviews = rr.findAllByprodNumandReviewNum(prodNum);
		List<ReviewReadResponseDto>list= new ArrayList<>(); 
		if(reviews.size()==0) {
			log.info("리뷰가 없습니다.");
		}else {
			for(Review review:reviews) {
				ReviewReadResponseDto dto = ReviewReadResponseDto.builder()
						.userId(review.getUser().getId())
						.pName(review.getOrderDetail().getProduct().getName())
						.reviewNum(review.getReviewNum())
						.title(review.getTitle())
						.content(review.getContent())
						.createdDate(review.getCreatedDate())
						.stars(review.getStars())
						
						.build();
						list.add(dto);				
			}
		}
		return list;
		 
	}
	
	//베스트리뷰 선정
	@Transactional
	public void addBestReview(Long reviewNum,Authentication seller) {
		Optional<Review> Optr = rr.findById(reviewNum);
		System.out.println(Optr.get().getReviewNum());
		LocalDateTime date = LocalDateTime.now();
		BestReview bestReview = BestReview.builder()
									.review(Optr.get())
									.reviewNum(Optr.get().getReviewNum())
									.createdDate(date)
									.build();
		brr.save(bestReview);
		
	}
	
	
	@Transactional
	public ReviewDetailResponseDto modifyReview(ReviewUpdateRequestDto updateDto,Long rNum, Authentication user) {
		String username = user.getName();
		Review review = rr.findById(rNum).get();
		if(review.getUser().getId().equals(username)) {
		review.update(updateDto.getTitle(),updateDto.getContent());
		}else {
			log.info("작성자만 수정 가능합니다.");
		}
		return new ReviewDetailResponseDto(review);
	}
	
}
