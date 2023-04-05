package com.myomi.product.control;

import java.io.IOException;

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
import org.springframework.web.multipart.MultipartFile;

import com.myomi.common.status.ExceedMaxUploadSizeException;
import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.ResponseDetails;
import com.myomi.common.status.UnqualifiedException;
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
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private final ProductService productService;
	
	@ApiOperation(value = "셀러| 상품등록")
	@PostMapping(value = "/add")
	public ResponseEntity<?> productSave(String name, String category, int week, int percentage, Long originPrice,
			String detail, Authentication seller, MultipartFile file) throws NoResourceException,IOException,UnqualifiedException,ExceedMaxUploadSizeException {
		if(name.length() > 30) {
			log.error("상품명 30자 초과");
		} else if(detail.length() > 150) {
			log.error("상품 특이사항 150자 초과");
		} 
		
		ProductSaveDto dto = ProductSaveDto.builder()
			.name(name)
			.category(category)
			.detail(detail)
			.originPrice(originPrice)
			.percentage(percentage)
			.week(week)
			.file(file)
			.build();

		ResponseDetails responseDetails = productService.addProduct(dto, seller); 
		return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
	}
	
	@ApiOperation(value = "사용자| 판매자 상품 리스트 조회")
	@ResponseBody
	@GetMapping(value = "list/seller/{seller}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productList(@PathVariable String seller){
		ResponseDetails responseDetails = productService.getProductBySellerId(seller);
		return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
	}
	
	@ApiOperation(value = "사용자| 특정 상품 정보+리뷰+문의 조회")
	@ResponseBody
	@GetMapping(value = "{prodNum}")
	public ResponseEntity<?> prodDetails(@PathVariable Long prodNum) {
		ResponseDetails responseDetails = productService.getOneProd(prodNum);
		return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
	}
	
	@ApiOperation(value = "셀러| 특정 상품 정보 수정")
	@PutMapping(value = "{prodNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productModify(@PathVariable Long prodNum, @RequestBody ProductUpdateDto productUpdateDto, Authentication seller) {
		return new ResponseEntity<>(productService.modifyProduct(prodNum, productUpdateDto, seller),HttpStatus.OK);
	}
	
	@ApiOperation(value = "셀러| 특정 상품 삭제")
	@DeleteMapping(value = "{prodNum}")
	public ResponseEntity<?> productRemove(@PathVariable Long prodNum, Authentication seller) {
		return new ResponseEntity<>(productService.removeProduct(prodNum, seller),HttpStatus.OK);
	}

	@ApiOperation(value = "메인| 모든 상품 리스트")
	@ResponseBody
	@GetMapping(value = "list")
	public ResponseEntity<?> productAllList() {
		return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "메인| 키워드로 상품 검색")
	@GetMapping(value = "list/{keyword}")
	public ResponseEntity<?> productAllByKeyword(String keyword) {
		return new ResponseEntity<>(productService.getAllProduct(keyword),HttpStatus.OK);
	}
	
	@ApiOperation(value = "셀러| 특정 상품 정보 조회")
	@ResponseBody
	@GetMapping(value = "/seller/{prodNum}")
	public ResponseEntity<?> prodDetailsBySeller(@PathVariable Long prodNum, Authentication seller) {
		return new ResponseEntity<>(productService.getOneProdBySeller(prodNum, seller),HttpStatus.OK);
	}
}
