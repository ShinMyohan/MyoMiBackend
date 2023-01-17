package com.myomi.order;

import java.util.Date;
import java.util.List;

import com.myomi.user.UsersVo;

public class OrderVo {
	private int num; //주문번호-주문상세 참조.
	//주문에서 주문상세를 바라볼까? 주문상세들을 주문을 바라볼까? 전자가 맞음.
	//private String userId;
	private UsersVo user;
	//제이슨포맷 잊지말기
	private Date createadDate;
	private int options;
	private String msg;
	private int couponNum;
	//하나의 주문에, 여러 주문상세가 있으므로 List로!
	private List<OrderdetailVo> orderDetails;
	private DeliveryVo delivery;
	
}
