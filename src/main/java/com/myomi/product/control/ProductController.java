package com.myomi.product.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Api(tags = "상품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private final ProductService productService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ApiOperation(value = "셀러| 상품등록")
	@PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productsave(@RequestBody ProductSaveDto productSaveDto, 
//			@AuthenticationPrincipal User user
			Authentication seller) {
		return new ResponseEntity<>(productService.addProduct(productSaveDto, seller),HttpStatus.OK);
	}
	
	@ApiOperation(value = "사용자| 판매자 상품 리스트 조회")
	@ResponseBody
	@GetMapping(value = "list/{seller}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDto> prodList(@PathVariable String seller){
		return productService.selectBySellerId(seller);
	}
	
	@ApiOperation(value = "사용자| 특정 상품 정보+리뷰+문의 조회")
	@ResponseBody
	@GetMapping(value = "{prodNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> prodInfo(@PathVariable Long prodNum) {
		return new ResponseEntity<>(productService.sellectOneProd(prodNum),HttpStatus.OK);
	}
	
	@ApiOperation(value = "셀러| 특정 상품 정보 수정")
	@PutMapping(value = "{prodNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> prodUpdate(@PathVariable Long prodNum, @RequestBody ProductUpdateDto productUpdateDto, Authentication seller) {
		return new ResponseEntity<>(productService.updateProd(prodNum, productUpdateDto, seller),HttpStatus.OK);
	}
	
	@ApiOperation(value = "셀러| 특정 상품 삭제")
	@DeleteMapping(value = "{prodNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> prodDelete(@PathVariable Long prodNum, Authentication seller) {
		return new ResponseEntity<>(productService.deleteProd(prodNum, seller),HttpStatus.OK);
	}
}
