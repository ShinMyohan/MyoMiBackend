package com.myomi.qna.control;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myomi.exception.AddException;
import com.myomi.product.entity.Product;
import com.myomi.qna.dto.PageBean;
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
	
	//문의작성
	@PostMapping("product/qna/{prodNum}")
	public ResponseEntity<?> qnaSave(String queTitle, String queContent,Authentication user,MultipartFile file, @PathVariable Long prodNum)throws IOException{
		QnaAddRequestDto dto = QnaAddRequestDto.builder()
				.queTitle(queTitle)
				.queContent(queContent)
				.file(file)
				.build();
		return new ResponseEntity<>(qnaService.addQna(dto, prodNum,user),HttpStatus.OK);
	}
		
	//회원문의 조회
	@GetMapping("mypage/qna/list")
	public ResponseEntity<?> qnaList(@RequestParam(required = false, defaultValue="1") int currentPage, Authentication user) {
		PageBean<QnaUReadResponseDto> pb = qnaService.getAllUserQnaList(user, currentPage);
		return new ResponseEntity<>(pb,HttpStatus.OK);
	}
		
	//회원문의 상세조회
	@GetMapping("mypage/qna/detail/{qnaNum}")
	public QnaUReadResponseDto qnaDetail(Authentication user, @PathVariable Long qnaNum){
		return qnaService.getUserQna(user,qnaNum);
	}
	
	//상품별 문의 조회
	@GetMapping("product/qna/{prodNum}")
	public List<QnaPReadResponseDto> qnaAllByProdList(@PathVariable Product prodNum){
		return qnaService.getAllQnaProductList(prodNum);
	}
	
	//회원 문의 수정
	@PutMapping("mypage/qna/detail/{qnaNum}")
	public void qnaModify(@RequestBody QnaEditRequestDto requestDto, @PathVariable Long qnaNum, Authentication user) {
		qnaService.modifyQna(requestDto,qnaNum,user);
	}
	
	//회원 문의 삭제
	@DeleteMapping("mypage/qna/detail/{qnaNum}")
	public void qnaDelete (@PathVariable Long qnaNum, Authentication user) {
		qnaService.removeQna(qnaNum,user);
	}
	
	//셀러가 스토어 상품문의 조회하기 (SQL)
	@GetMapping("sellerpage/qna/list")
	public List<QnaUReadResponseDto> qnaAllBySellerList(Authentication user){
		return qnaService.getAllSellerQnaList(user);
	}
		
	//셀러가 스토어 상품문의 상세조회하기
	@GetMapping("sellerpage/qna/detail/{qnaNum}")
	public QnaUReadResponseDto qnaBySellerDetail(Authentication user,@PathVariable Long qnaNum) {
		return qnaService.getSellerQna(user,qnaNum);
	}
	
	//셀러가 상품문의 답변하기
	@PutMapping("sellerpage/qna/detail/{qnaNum}")
	public void qnaAnsSave(@RequestBody QnaAnsRequestDto requestDto, @PathVariable Long qnaNum, Authentication user) {
		qnaService.addAnsQna(requestDto,qnaNum,user);
	}
}
