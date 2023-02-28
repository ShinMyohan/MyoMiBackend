package com.myomi.seller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.myomi.order.entity.Order;
import com.myomi.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerOrderDetailDto {
	@JsonIgnore
	private Order order;
	@JsonIgnore
	private Product product;
	
	private int prodCnt;
	private Long prodNum;
	private String name;
	private Long orderNum;
	private String user;
	private int week;
	private String msg;
	private String deliveryName;
	private String deliveryTel;
	private String deliveryAddr;
	private String deliveryMsg;
	private String receiveDate;
	
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdDate;

	@Builder
	public SellerOrderDetailDto(Order order, Product product, int prodCnt, Long prodNum, String name, Long orderNum,
			String user, int week, String msg, String deliveryName, String deliveryTel, String deliveryAddr,
			String deliveryMsg, String receiveDate, LocalDateTime createdDate) {
		this.order = order;
		this.product = product;
		this.prodCnt = prodCnt;
		this.prodNum = prodNum;
		this.name = name;
		this.orderNum = orderNum;
		this.user = user;
		this.week = week;
		this.msg = msg;
		this.deliveryName = deliveryName;
		this.deliveryTel = deliveryTel;
		this.deliveryAddr = deliveryAddr;
		this.deliveryMsg = deliveryMsg;
		this.receiveDate = receiveDate;
		this.createdDate = createdDate;
	}
	
	
	
	
	
	
	
	
	

}
