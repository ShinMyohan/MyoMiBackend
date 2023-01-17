package com.myomi.order.vo;

import com.myomi.product.vo.ProductVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetailVo {
	private int num;
	private ProductVo product;
	private int prodCnt;
}
