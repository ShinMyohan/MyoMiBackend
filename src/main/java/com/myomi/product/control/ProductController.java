package com.myomi.product.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.product.dto.ProdReadOneDto;
import com.myomi.product.dto.ProductDto;
import com.myomi.product.dto.ProductSaveDto;
import com.myomi.product.service.ProductService;
import com.myomi.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	@Autowired
	private final ProductService productService;
	
	private final UserRepository ur;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductSaveDto> productsave(@RequestBody ProductSaveDto productSaveDto, 
//			@AuthenticationPrincipal User user
			Authentication user) {

		return productService.addProduct(productSaveDto, user);
	}
	
	@GetMapping(value = "list/{seller}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDto> prodList(@PathVariable String seller){
		return productService.selectBySellerId(seller);
	}
	
	@ResponseBody
	@GetMapping(value = "info/{prodNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProdReadOneDto> prodInfo(@PathVariable Long prodNum) {
		return productService.sellectOneProd(prodNum);
	}
}
