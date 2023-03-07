package com.myomi.qna.control;

import com.myomi.product.entity.Product;
import com.myomi.qna.dto.*;
import com.myomi.qna.service.QnaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
	public List<QnaUReadResponseDto> qnaList(Authentication user, @PageableDefault(size=5) Pageable pageable){
		return qnaService.getAllUserQnaList(user,pageable);
	}
	
	//회원문의 상세조회
	@GetMapping("mypage/qna/detail/{qnaNum}")
	public QnaUReadResponseDto qnaDetail(Authentication user, @PathVariable Long qnaNum){
		return qnaService.getUserQna(user,qnaNum);
	}
	
	//상품별 문의 조회
	@GetMapping("product/qna/{prodNum}")
	public List<QnaPReadResponseDto> qnaAllByProdList(@PathVariable Product prodNum,@PageableDefault(size=5) Pageable pageable){
		return qnaService.getAllQnaProductList(prodNum,pageable);
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
	
	//셀러가 스토어 상품문의 조회하기
	@GetMapping("sellerpage/qna/list")
	public List<QnaUReadResponseDto> qnaAllBySellerList(Authentication user,@PageableDefault(size=5) Pageable pageable){
		return qnaService.getAllSellerQnaList(user,pageable);
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
