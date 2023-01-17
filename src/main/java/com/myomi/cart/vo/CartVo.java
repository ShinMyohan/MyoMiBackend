package com.myomi.cart.vo;

import com.myomi.product.vo.ProductVo;
import com.myomi.user.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartVo {
	private int num;
	private UserVo user;
	private ProductVo product;
	private int prodCnt;
}
