package com.myomi.qna.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.product.entity.Product;
import com.myomi.qna.dto.QnaAddRequestDto;
import com.myomi.qna.dto.QnaAnsRequestDto;
import com.myomi.qna.dto.QnaEditRequestDto;
import com.myomi.qna.dto.QnaPReadResponseDto;
import com.myomi.qna.dto.QnaUReadResponseDto;
import com.myomi.qna.service.QnaService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RestController
@RequiredArgsConstructor
public class QnaController {
	private final QnaService qnaService;

	

    //문의 작성
	@PostMapping("product/{prodNum}")
	public ResponseEntity<QnaAddRequestDto> qnaSave(@RequestBody QnaAddRequestDto qnaAddRequestDto, @PathVariable Long prodNum, Authentication user) {		
		return qnaService.addQna(qnaAddRequestDto, prodNum, user);
	}
	
	//회원 문의 조회
	@GetMapping("mypage/qna/list")
	public List<QnaUReadResponseDto> qnaList(Authentication user, @PageableDefault(size=5) Pageable pageable){
		return qnaService.getAllUserQnaList(user,pageable);
	}
	
	//상품별 문의 조회
	@GetMapping("product/list/{prodNum}")
	public List<QnaPReadResponseDto> qnaAllByProdList(@PathVariable Product prodNum,@PageableDefault(size=5) Pageable pageable){
		return qnaService.getAllQnaProductList(prodNum,pageable);
	}
	
	//회원 문의 수정
	@PutMapping("mypage/qna/detail/{qnaNum}")
	public void qnaModify(@RequestBody QnaEditRequestDto requestDto, @PathVariable Long qnaNum, Authentication user) {
		qnaService.modifyQna(requestDto,qnaNum,user);
	}
	
	//회원 문의 삭제
	@DeleteMapping("mypage/qna/{qnaNum}")
	public void qnaDelete (@PathVariable Long qnaNum, Authentication user) {
		qnaService.removeQna(qnaNum,user);
	}
	
	//셀러가 스토어 상품문의 조회하기
	@GetMapping("sellerpage/qna/list")
	public List<QnaUReadResponseDto> qnaAllBySellerList(Authentication user,@PageableDefault(size=5) Pageable pageable){
		return qnaService.getAllSellerQnaList(user,pageable);
	}
	
	//셀러가 상품문의 답변하기
	@PutMapping("sellerpage/qna/{qnaNum}")
	public void qnaAnsSave(@RequestBody QnaAnsRequestDto requestDto, @PathVariable Long qnaNum, Authentication user) {
		qnaService.addAnsQna(requestDto,qnaNum,user);
	}
	
	//회원문의 상세조회
	
	
}
