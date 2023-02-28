package com.myomi.order.dto;

import com.myomi.order.entity.Order;
import com.myomi.product.entity.Product;

public class OrderDetailDto {
	private Order oNum;
    private Product pNum;
    private Long prodCnt;
	
    public OrderDetailDto(Order oNum, Product pNum, Long prodCnt) {
		this.oNum = oNum;
		this.pNum = pNum;
		this.prodCnt = prodCnt;
	}
}
