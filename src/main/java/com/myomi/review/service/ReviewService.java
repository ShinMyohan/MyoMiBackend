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

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
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

    public List<ReviewDetailResponseDto>getProdReviewList(Long prodNum,Authentication seller)throws FindException{
		List<Review> reviews = reviewRepository.findAllByProdNum(prodNum);
		List<ReviewDetailResponseDto>list= new ArrayList<>(); 
		if(reviews.size()==0) {
			log.info("리뷰가 없습니다.");
		}else if(!(reviews.get(0).getOrderDetail().getProduct().getSeller().getId().equals(seller.getName()))){
			throw new FindException("다른 판매자의 리뷰에 접근할 수 없습니다.");
		}
		else{
			for(Review review:reviews) {
				ReviewDetailResponseDto dto = ReviewDetailResponseDto.builder()
						.userId(review.getUser().getId())
						.prodName(review.getOrderDetail().getProduct().getName())
						.reviewNum(review.getReviewNum())
						.title(review.getTitle())
						.content(review.getContent())
						.sort(review.getSort())
						.createdDate(review.getCreatedDate())
						.stars(review.getStars())
						
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
                        .userId(review.getUser().getId())
                        .prodName(review.getOrderDetail().getProduct().getName())
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
    public ResponseEntity<?> addReview(ReviewSaveRequestDto reviewSaveDto, Authentication user
    		) throws AddException,IOException{
    	
				String username = user.getName();
				Optional<User> optU = userRepository.findById(username);
				OrderDetailEmbedded ode = OrderDetailEmbedded.builder().orderNum(reviewSaveDto.getOrderNum())
						.prodNum(reviewSaveDto.getProdNum()).build();
				MultipartFile file = reviewSaveDto.getFile();
				if (file != null) {
					InputStream inputStream = file.getInputStream();
					boolean isValid = FileUtils.validImgFile(inputStream);
					if (!isValid) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
				String fileUrl = s3Uploader.upload(file, "리뷰이미지", user, reviewSaveDto);
				Optional<OrderDetail> od = orderdetailRepository.findById(ode);
				Review review = reviewSaveDto.toEntity(reviewSaveDto, optU.get(), od.get(), fileUrl);
				
				Optional<Review> Optr=reviewRepository.findByOrderDetail_order_orderNum(ode.getOrderNum());
				if(Optr.isPresent()) {
					throw new AddException("해당 주문상세로 작성한 리뷰가 존재합니다.");	
				}else {
					reviewRepository.save(review);
					return new ResponseEntity<>(HttpStatus.OK);
				}	
    			}
    
    //리뷰상세보기
    @Transactional
    public ReviewDetailResponseDto detailReview(Long reviewNum,Authentication user) {
		String username = user.getName();
		Review review = reviewRepository.findReviewByReviewNumAndUserId(reviewNum, username);
		return new ReviewDetailResponseDto(review);
    }

    //베스트리뷰 선정
    @Transactional
    public void addBestReview(Long reviewNum, Authentication seller)throws AddException {
        Optional<Review> Optr = reviewRepository.findById(reviewNum);
        Optional<BestReview> Optb = bestreviewRepsitory.findById(reviewNum);
        LocalDateTime date = LocalDateTime.now();
        if(Optb.isPresent()) {
        throw new AddException("이미 베스트 리뷰로 선정된 리뷰입니다.");
        }
      if(Optr.get().getSort()==4){
      	
    	  BestReview bestReview = BestReview.builder()
                  .review(Optr.get())
                  .reviewNum(Optr.get().getReviewNum())
                  .createdDate(date)
                  .build();
    	  bestreviewRepsitory.save(bestReview);
        }
        else{
        	throw new AddException("포토리뷰만 선정할 수 있습니다.");
        }
    }

    @Transactional
    public ReviewDetailResponseDto modifyReview(ReviewUpdateRequestDto updateDto, Long rNum, Authentication user)throws AddException {
        String username = user.getName();
        Review review = reviewRepository.findById(rNum).get();
        Optional<BestReview> Optb = bestreviewRepsitory.findById(rNum);
        if (Optb.isPresent()) {
        	throw new AddException("베스트리뷰는 수정불가합니다.");	
        }
          else if(review.getUser().getId().equals(username)){
        	  review.update(updateDto.getTitle(), updateDto.getContent());
        }
        else {
        	throw new AddException("작성자만 수정 가능합니다.");
        }
        return new ReviewDetailResponseDto(review);
    }
   
}