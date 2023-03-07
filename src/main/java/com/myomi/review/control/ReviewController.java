package com.myomi.review.control;

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewSaveRequestDto;
import com.myomi.review.dto.ReviewUpdateRequestDto;
import com.myomi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    @Autowired
    private final ReviewService service;

    //상품리뷰리스트
    @GetMapping("/sellerpage/review/{prodNum}")
    public List<ReviewDetailResponseDto> reviewprodList(@PathVariable Long prodNum, Authentication seller) throws FindException {
        return service.getProdReviewList(prodNum, seller);
    }

    //내리뷰리스트
    @GetMapping("/mypage/review")
    public List<ReviewDetailResponseDto> MyReviewList(Authentication user) {
        return service.getMyReviewList(user);
    }

    //리뷰 상세보기
    @GetMapping("/mypage/review/{reviewNum}")
    public ReviewDetailResponseDto reviewDetail(@PathVariable Long reviewNum, Authentication user) {
        return service.detailReview(reviewNum, user);
    }

    //리뷰작성
    @PostMapping("/mypage/review/add")
    public ResponseEntity<?> reviewsave(ReviewSaveRequestDto reviewSaveDto,
                                        Authentication user
            , MultipartFile file, Long orderNum, Long prodNum,
                                        String title, String content, float stars

    ) throws AddException, IOException {
        ReviewSaveRequestDto dto = ReviewSaveRequestDto.builder()
                .prodNum(prodNum)
                .orderNum(orderNum)
                .title(title)
                .content(content)
                .stars(stars)
                .file(file)
                .build();
        return new ResponseEntity<>(service.addReview(dto, user), HttpStatus.OK);
    }

    //판매자리뷰선정
    @PostMapping("/sellerpage/review/select/{reviewNum}")
    public void bestReviewAdd(
            @PathVariable long reviewNum,
            Authentication seller) throws AddException {
        service.addBestReview(reviewNum, seller);
    }

    //리뷰수정
    @PutMapping("/mypage/review/{reviewNum}")
    public void reviewModify(@RequestBody ReviewUpdateRequestDto updateDto,
                             @PathVariable Long reviewNum,
                             Authentication user
    ) throws AddException {
        service.modifyReview(updateDto, reviewNum, user);
    }
}