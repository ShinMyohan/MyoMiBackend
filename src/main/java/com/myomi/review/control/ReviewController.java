package com.myomi.review.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewReadResponseDto;
import com.myomi.review.dto.ReviewSaveRequestDto;
import com.myomi.review.dto.ReviewUpdateRequestDto;
import com.myomi.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/review/*")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
	@Autowired
	private final ReviewService service;

	//내리뷰리스트
	@GetMapping("mypage/{id}")
	public List<ReviewReadResponseDto> MyReviewList(@PathVariable String id) {
		return service.getMyReviewList(id);
	}

	//판매자의 리뷰 검색
	@GetMapping("/sellerpage")
	public List<ReviewReadResponseDto> sellerReviewList(Authentication seller){
		return service.getSellerReviewList(seller);
	}

	//리뷰 상세보기
	@GetMapping("detail/{reviewNum}")
	public ReviewDetailResponseDto reviewDetail(@PathVariable Long reviewNum) {
		return service.detailReview(reviewNum);
	}
	
	//리뷰작성
	@PostMapping("add")
	public void reviewsave(@RequestBody ReviewSaveRequestDto reviewSaveDto,
					Authentication user
//					,		
//					Long orderNum,
//					Long prodNum
			) {
		service.addReview(reviewSaveDto,user
//				,orderNum,prodNum
				);
	}
	
	//판매자리뷰
	@PostMapping("sellerpage")
	public void bestReviewAdd(
		Long reviewNum,Authentication seller) {
		service.addBestReview(reviewNum,seller);
	}
	
	//리뷰수정
	@PutMapping("{reviewNum}")
	public void reviewModify(@RequestBody ReviewUpdateRequestDto updateDto, 
			@PathVariable Long reviewNum,
			Authentication user
			) {
		service.modifyReview(updateDto,reviewNum,user);
	}
}
