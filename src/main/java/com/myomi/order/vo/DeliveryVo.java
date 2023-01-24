package com.myomi.order.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryVo {
	//상품이 가장 원데이터! 상품부터 시작
	private int orderNum;
	private String name;
	private String tel;
	private String addr;
	private String deliveryMsg;
	private String receiveDate;
}
