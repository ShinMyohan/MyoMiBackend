package com.myomi.qna.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.product.entity.Product;
import com.myomi.product.repository.ProductRepository;
import com.myomi.qna.dto.QnaAddRequestDto;
import com.myomi.qna.dto.QnaAnsRequestDto;
import com.myomi.qna.dto.QnaEditRequestDto;
import com.myomi.qna.dto.QnaPReadResponseDto;
import com.myomi.qna.dto.QnaUReadResponseDto;
import com.myomi.qna.entity.Qna;
import com.myomi.qna.repository.QnaRepository;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaService {
	private final QnaRepository qr;
	private final UserRepository ur;
	private final ProductRepository pr;


	/* TODO : 1.회원아이디로 나의상품문의 불러오기@
	 * 		  2.회원이 상품문의 작성하기 @
	 *        3.회원이 나의상품문의 수정하기@
	 *        4.회원이 나의상품문의 삭제하기@
	 *        5.셀러가 상품문의 답변달기@
	 *        6.상품별 상품문의 조회하기@
	 *        7.셀러가 나의스토어 상품문의 불러오기@
	 *        8.회원 상품문의 상세보기 
	 */

	//회원문의작성
	@Transactional
	public ResponseEntity<QnaAddRequestDto> addQna(QnaAddRequestDto addDto, Long prodNum,Authentication user) {
		LocalDateTime date = LocalDateTime.now();
		String username = user.getName(); 
		Optional<User> optU = ur.findById(username);
		Optional<Product> optP = pr.findById(prodNum);

		Qna qna = Qna.builder()
				.userId(optU.get())
				.prodNum(optP.get())
				.queTitle(addDto.getQueTitle())
				.queContent(addDto.getQueContent())
				.queCreatedDate(date)
				.build();
		qr.save(qna);	

		return new ResponseEntity<>(HttpStatus.OK);
	}


	//회원의 나의상품문의 조회
	@Transactional
	public List<QnaUReadResponseDto> getAllUserQnaList(Authentication user,Pageable pageable){
		String username = user.getName();
		Optional<User> optU = ur.findById(username);
		List<Qna> qnas = qr.findAllByUserId(optU.get().getId(),pageable);
		List<QnaUReadResponseDto> list = new ArrayList<>();
		if(qnas.size() == 0) {
			log.info("상품문의가 없습니다");
		}else {
			for(Qna qna : qnas) {
				QnaUReadResponseDto dto = QnaUReadResponseDto.builder()
						.qnaNum(qna.getQnaNum())
						.id(qna.getUserId().getId())
						.pName(qna.getProdNum().getName())
						.category(qna.getProdNum().getCategory())
						.detail(qna.getProdNum().getDetail())
						.week(qna.getProdNum().getWeek())
						.queTitle(qna.getQueTitle())
						.queContent(qna.getQueContent())
						.queCreatedDate(qna.getQueCreatedDate())
						.ansContent(qna.getAnsContent())
						.ansCreatedDate(qna.getAnsCreatedDate())
						.build();
				list.add(dto);
			}
		}
		return list;

	}

	//상품별 상품문의 조회하기
	@Transactional
	public List<QnaPReadResponseDto> getAllQnaProductList(Product prodNum,Pageable pageable){
		Long pd = prodNum.getProdNum();  //id
		List<Qna> qnas = qr.findAllByProdNum(pd,pageable);
		List<QnaPReadResponseDto> list = new ArrayList<>();

		if(qnas.size() == 0) {
			log.info("상품문의가 없습니다");
		}else {
			for(Qna qna : qnas) {
				QnaPReadResponseDto dto = QnaPReadResponseDto.builder()
						.qnaNum(qna.getQnaNum())
						.userId(qna.getUserId().getId())
						.pName(qna.getProdNum().getName())
						.queTitle(qna.getQueTitle())
						.queContent(qna.getQueContent())
						.queCreatedDate(qna.getQueCreatedDate())
						.ansContent(qna.getAnsContent())
						.ansCreatedDate(qna.getAnsCreatedDate())
						.build();
				list.add(dto);
			}
		}
		return list;
	}
	

	//회원이 나의상품문의 수정
	@Transactional
	public QnaUReadResponseDto modifyQna(QnaEditRequestDto editDto, Long qnaNum, Authentication user){
		String username = user.getName();
		Qna qna = qr.findById(qnaNum).get();

		if (qna.getUserId().getId().equals(username)) {
			if(qna.getAnsCreatedDate() == null) {
				qna.update(editDto.getQueTitle(), editDto.getQueContent());
			}else {
				log.info("수정이 불가능합니다.");
			}
		}else {
			log.info("수정권한이 없습니다");
		}
		return new QnaUReadResponseDto(qna);
	}


	//회원이 나의상품문의 삭제
	@Transactional
	public void removeQna(Long qnaNum, Authentication user) {
		String username = user.getName();
		Qna qna = qr.findById(qnaNum).get();

		if(qna.getUserId().getId().equals(username)) {
			if(qna.getAnsCreatedDate() == null) {
				qr.delete(qna);
			}else {
				log.info("삭제가 불가능합니다.");
			}
		}else {
			log.info("삭제권한이 없습니다");
		}
	}

	//셀러가 나의스토어 상품문의 조회
	@Transactional
	public List<QnaUReadResponseDto> getAllSellerQnaList(Authentication user,Pageable pageable){
		String username = user.getName();
		List<Qna> qnas = qr.findAllBySellerId(username,pageable);
		List<QnaUReadResponseDto> list = new ArrayList<>();
		if(qnas.size() == 0) {
			log.info("상품문의가 없습니다");
		}else {
			for(Qna qna : qnas) {
				QnaUReadResponseDto dto = QnaUReadResponseDto.builder()
						.qnaNum(qna.getQnaNum())
						.id(qna.getUserId().getId())
						.pName(qna.getProdNum().getName())
						.category(qna.getProdNum().getCategory())
						.detail(qna.getProdNum().getDetail())
						.week(qna.getProdNum().getWeek())
						.queTitle(qna.getQueTitle())
						.queContent(qna.getQueContent())
						.queCreatedDate(qna.getQueCreatedDate())
						.ansContent(qna.getAnsContent())
						.ansCreatedDate(qna.getAnsCreatedDate())
						.build();
				list.add(dto);
			}
		}
		return list;
	}


	//셀러가 나의스토어 상품문의 답변
	@Transactional
	public void addAnsQna(QnaAnsRequestDto addAnsDto, Long qnaNum, Authentication user) {
		String username = user.getName();
		LocalDateTime date = LocalDateTime.now();
		Qna qna = qr.findById(qnaNum).get();
		QnaUReadResponseDto dto = QnaUReadResponseDto.builder()
				.ansCreatedDate(date)
				.build();
		
		if(qna.getProdNum().getSeller().getId().equals(username)) {
			if(qna.getAnsCreatedDate() == null) {
				qna.updateAns(addAnsDto.getAnsContent(),dto.getAnsCreatedDate());
			}else {
				log.info("이미 답변완료된 문의글 입니다.");
			}
		}else {
			log.info("접근 권한이 없습니다.");
		}
	}

}
