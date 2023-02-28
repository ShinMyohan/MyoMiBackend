package com.myomi.product.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.product.dto.ProductDto;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.product.dto.ProductUpdateDto;
import com.myomi.product.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "상품")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private final ProductService productService;
	
	//셀러 - 상품 등록
	@ApiOperation(value = "셀러| 상품등록")
	@PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productSave(@RequestBody ProductSaveDto productSaveDto, 
//			@AuthenticationPrincipal User user
			Authentication seller) {
		if(productSaveDto.getName().length() > 30) {
			log.error("상품명 30자 초과");
		} else if(productSaveDto.getDetail().length() > 150) {
			log.error("상품 특이사항 150자 초과");
		} 
		return new ResponseEntity<>(productService.addProduct(productSaveDto, seller),HttpStatus.OK);
	}
	
	//셀러 - 특정 판매자 상품 리스트 조회
	@ApiOperation(value = "사용자| 판매자 상품 리스트 조회")
	@ResponseBody
	@GetMapping(value = "list/{seller}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDto> productList(@PathVariable String seller){
		return productService.getProductBySellerId(seller);
	}
	
	//사용자 - 상품 정보 + 리뷰 + 문의 조회
	@ApiOperation(value = "사용자| 특정 상품 정보+리뷰+문의 조회")
	@ResponseBody
	@GetMapping(value = "{prodNum}")
	public ResponseEntity<?> prodDetails(@PathVariable Long prodNum) {
		return new ResponseEntity<>(productService.getOneProd(prodNum),HttpStatus.OK);
	}
	
	//셀러 - 특정 상품 정보 수정
	@ApiOperation(value = "셀러| 특정 상품 정보 수정")
	@PutMapping(value = "{prodNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productModify(@PathVariable Long prodNum, @RequestBody ProductUpdateDto productUpdateDto, Authentication seller) {
		return new ResponseEntity<>(productService.modifyProduct(prodNum, productUpdateDto, seller),HttpStatus.OK);
	}
	
	//셀러 - 특정 상품 삭제 
	@ApiOperation(value = "셀러| 특정 상품 삭제")
	@DeleteMapping(value = "{prodNum}")
	public ResponseEntity<?> productRemove(@PathVariable Long prodNum, Authentication seller) {
		return new ResponseEntity<>(productService.removeProduct(prodNum, seller),HttpStatus.OK);
	}
	
	//사용자 - 상품 리스트
	@ApiOperation(value = "메인| 모든 상품 리스트")
	@ResponseBody
	@GetMapping(value = "list")
	public ResponseEntity<?> productAllList(int status) {
		return new ResponseEntity<>(productService.getAllProduct(status), HttpStatus.OK);
	}
	
	//사용자 - 키워드로 상품 검색
	@ApiOperation(value = "메인| 키워드로 상품 검색")
	@GetMapping(value = "list/{keyword}")
	public ResponseEntity<?> productAllByKeyword(String keyword) {
		return new ResponseEntity<>(productService.getAllProduct(keyword),HttpStatus.OK);
	}
}
