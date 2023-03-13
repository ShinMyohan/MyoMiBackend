package com.myomi.review.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.common.status.AddException;
import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.ExceedMaxUploadSizeException;
import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.ResponseDetails;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.entity.OrderDetailEmbedded;
import com.myomi.order.repository.OrderDetailRepository;
import com.myomi.review.dto.ReviewDetailResponseDto;
import com.myomi.review.dto.ReviewSaveRequestDto;
import com.myomi.review.dto.ReviewUpdateRequestDto;
import com.myomi.review.entity.BestReview;
import com.myomi.review.entity.Review;
import com.myomi.review.repository.BestReviewRepository;
import com.myomi.review.repository.ReviewRepository;
import com.myomi.s3.FileUtils;
import com.myomi.s3.S3UploaderReview;
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
    @Autowired
    private S3UploaderReview s3Uploader;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BestReviewRepository bestreviewRepsitory;
    private final OrderDetailRepository orderdetailRepository;

    public List<ReviewDetailResponseDto> getProdReviewList(Long prodNum, Authentication seller) {
        List<Review> reviews = reviewRepository.findAllByProdNum(prodNum);
        List<ReviewDetailResponseDto> list = new ArrayList<>();
        if (reviews.size() == 0) {
        	log.info("리뷰가 없습니다.");
        } else {
            for (Review review : reviews) {
                ReviewDetailResponseDto dto = ReviewDetailResponseDto.builder()
                        .userName(review.getUser().getName())
                        .prodName(review.getOrderDetail().getProduct().getName())
                        .reviewNum(review.getReviewNum())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .sort(review.getSort())
                        .createdDate(review.getCreatedDate())
                        .stars(review.getStars())
                        .file(review.getReviewImgUrl())
                        .build();
                list.add(dto);
            }
        }
        return list;

    }

    @Transactional
    public List<ReviewDetailResponseDto> getMyReviewList(Authentication user) {
        String username = user.getName();
        List<Review> reviews = reviewRepository.findAllByUserId(username);
        List<ReviewDetailResponseDto> list = new ArrayList<>();
        if (reviews.size() == 0) {
            log.info("리뷰가 없습니다.");
        } else {
            for (Review review : reviews) {
                ReviewDetailResponseDto dto = ReviewDetailResponseDto.builder()
                        .userName(review.getUser().getName())
                        .prodName(review.getOrderDetail().getProduct().getName())
                        .reviewNum(review.getReviewNum())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .createdDate(review.getCreatedDate())
                        .stars(review.getStars())
                        .file(review.getReviewImgUrl())
                        .build();
                list.add(dto);
            }
        }
        return list;

    }


    @Transactional
    public ResponseEntity<?> addReview(ReviewSaveRequestDto reviewSaveDto, Authentication user
    ) throws IOException {

        String username = user.getName();
        Optional<User> optU = userRepository.findById(username);
        OrderDetailEmbedded ode = OrderDetailEmbedded.builder().orderNum(reviewSaveDto.getOrderNum())
                .prodNum(reviewSaveDto.getProdNum()).build();
        Optional<OrderDetail> od = orderdetailRepository.findById(ode);
        MultipartFile file = reviewSaveDto.getFile();
        if (file != null) {
            InputStream inputStream = file.getInputStream();
            boolean isValid = FileUtils.validImgFile(inputStream);
            if (!isValid) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String fileUrl = s3Uploader.upload(file, "리뷰이미지", user, reviewSaveDto);
           
            Review review = reviewSaveDto.toEntity(reviewSaveDto, optU.get(), od.get(), fileUrl);
            	reviewRepository.save(review);
        }else{
        	Review review = reviewSaveDto.toEntity(reviewSaveDto, optU.get(), od.get(), null);
        	reviewRepository.save(review);
        }
        return new ResponseEntity<>(HttpStatus.OK);

        }
    
    

    //리뷰상세보기
    @Transactional
    public ReviewDetailResponseDto detailReview(Long reviewNum, Authentication user) {
        String username = user.getName();
        Review review = reviewRepository.findReviewByReviewNumAndUserId(reviewNum, username);
        return new ReviewDetailResponseDto(review);
    }

    //베스트리뷰 선정
    @Transactional
    public ResponseDetails addBestReview(Long reviewNum, Authentication seller) throws AddException {
        String path = "/sellerpage/review/select/{reviewNum}";
    	Optional<Review> Optr = reviewRepository.findById(reviewNum);
        Optional<BestReview> Optb = bestreviewRepsitory.findById(reviewNum);
        LocalDateTime date = LocalDateTime.now();
        if (Optb.isPresent()) {
        	log.info("이미 베스트리뷰.");
            return new ResponseDetails("이미 베스트리뷰입니다.",500,path);
        }
        else if (Optr.get().getSort() == 3) {
        	throw new AddException(ErrorCode.BAD_REQUEST,"NOT_PHOTO_REVIEW");
        }  
        BestReview bestReview = BestReview.builder()
        		.review(Optr.get())
        		.reviewNum(Optr.get().getReviewNum())
        		.createdDate(date)
        		.build();
        bestreviewRepsitory.save(bestReview);
        return new ResponseDetails(bestReview.getReview().getReviewNum(), 200,path);
        
    }

    @Transactional
    public ResponseDetails modifyReview(ReviewUpdateRequestDto updateDto, Long rNum, Authentication user) throws AddException {
        String path = "/mypage/review/{reviewNum}";
    	String username = user.getName();
        Review review = reviewRepository.findById(rNum).get();
        Optional<BestReview> Optb = bestreviewRepsitory.findById(rNum);
        if (Optb.isPresent()) {
        	return new ResponseDetails("베스트리뷰는 수정할 수 없습니다.",500,path);
        } else if (!(review.getUser().getId().equals(username))) {
        	throw new AddException(ErrorCode.BAD_REQUEST,"NOT_EQUAL_REVIEWER");
        } else if(updateDto.getContent().length()>200) {
        	throw new AddException(ErrorCode.BAD_REQUEST, "EXCEED_MAX_CHAR");
        }
        review.update(updateDto.getTitle(), updateDto.getContent());
        return new ResponseDetails(updateDto, 200, path);
    }
}