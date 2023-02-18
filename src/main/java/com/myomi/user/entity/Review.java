//package com.myomi.user.entity;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.criteria.Order;
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
//@Table(name="review")
//public class Review {
//	private Integer num;
//	private Order order;
//	private User user;
//	private Integer sort;
//	private String title;
//	private String content;
//	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
//	private Date createdDate;
//	private Integer stars;
//}
