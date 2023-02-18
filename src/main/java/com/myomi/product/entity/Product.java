package com.myomi.product.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.order.entity.OrderDetail;
import com.myomi.seller.entity.Seller;

//import com.myomi.user.entity.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class Product {
	@Id
	@Column(name = "num")
	private Long pNum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Seller seller;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "origin_price")
	private Long originPrice;
	
	@Column(name = "percentage")
	private int percentage;
	
	@Column(name = "week")
	private int week;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "detail")
	private String detail;

	@Column(name = "review_cnt")
	private Long reviewCnt;
	
	@Column(name = "stars")
	private int stars; //테스트 끝나고 float로 바꾸기..ㅠㅠ.까먹음
	
	@Column(name = "fee")
	@ColumnDefault("9") //default 9
	private int fee;

	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;
	
	
//	private List<Cart> cart;
//	

//
//	private List<Qna> qnas;
}
