package com.myomi.order.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.coupon.vo.CouponVo;
import com.myomi.point.vo.PointVo;
import com.myomi.user.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderVo {
	private int num;
	private UserVo user;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
	private int options;
	private String msg;
	private CouponVo coupon;
	private int usedPoint;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date payCreatedDate;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date canceledDate;
	private int totalPrice;
	private int savePoint;
	// 하나의 주문에, 여러 주문상세가 있으므로 List로!
	private PointVo point;
	private List<OrderDetailVo> orderDetail;
	private DeliveryVo delivery;
}
