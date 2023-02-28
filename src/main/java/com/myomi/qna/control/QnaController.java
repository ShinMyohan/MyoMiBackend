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
@RequestMapping
@RequiredArgsConstructor
public class QnaController {
	private final QnaService qnaService;

	

    //문의 작성
	@PostMapping("productDetail/{pNum}")
	public ResponseEntity<QnaAddRequestDto> qnaAdd(@RequestBody QnaAddRequestDto qnaAddRequestDto, @PathVariable Long prodNum, Authentication user) {		
		return qnaService.addQna(qnaAddRequestDto, prodNum, user);
	}
	
	//회원 문의 조회
	@GetMapping("myPage/qna/detail")
	public List<QnaUReadResponseDto> qnaList(Authentication user, @PageableDefault(size=5) Pageable pageable){
		return qnaService.findUserQnaList(user,pageable);
	}
	
	//상품별 문의 조회
	@GetMapping("productDetail/{pNum}")
	public List<QnaPReadResponseDto> qnaByProdList(@PathVariable Product pNum,@PageableDefault(size=5) Pageable pageable){
		return qnaService.findPQnaList(pNum,pageable);
	}
	
	//회원 문의 수정
	@PutMapping("myPage/qna/{qNum}")
	public void qnaModify(@RequestBody QnaEditRequestDto requestDto, @PathVariable Long qnaNum, Authentication user) {
		qnaService.modifyQna(requestDto,qnaNum,user);
	}
	
	//회원 문의 삭제
	@DeleteMapping("myPage/qna/{qNum}")
	public void qnaDelete (@PathVariable Long qnaNum, Authentication user) {
		qnaService.deleteQna(qnaNum,user);
	}
	
	//셀러가 스토어 상품문의 조회하기
	@GetMapping("seller/qna")
	public List<QnaUReadResponseDto> qnaSellerList(Authentication user,@PageableDefault(size=5) Pageable pageable){
		return qnaService.findSellerQnaList(user,pageable);
	}
	
	//셀러가 상품문의 답변하기
	@PutMapping("seller/{qNum}")
	public void qnaAnsAdd(@RequestBody QnaAnsRequestDto requestDto, @PathVariable Long qnaNum, Authentication user) {
		qnaService.addAnsQna(requestDto,qnaNum,user);
	}
	
	//회원문의 상세조회
	
	
}
