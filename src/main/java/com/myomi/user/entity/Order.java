package com.myomi.user.entity;
//package com.myomi.user.entity;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.Entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Setter @Getter @AllArgsConstructor @NoArgsConstructor
//@Entity
//public class Order {
//	private Integer num;
//	private User user;
//	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
//	private Date createdDate;
////	private int options;
//	private String msg;
//	private Coupon coupon;
//	private Integer usedPoint;
//	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
//	private Date payCreatedDate;
//	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
//	private Date canceledDate;
//	private Integer totalPrice;
//	private Integer savePoint;
//	// 하나의 주문에, 여러 주문상세가 있으므로 List로!
//	private Point point;
//	private List<OrderDetail> orderDetail;
//	private Delivery delivery;
//}
