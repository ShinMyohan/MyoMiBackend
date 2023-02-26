package com.myomi.product.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.cart.entity.Cart;
import com.myomi.order.entity.OrderDetail;
import com.myomi.qna.entity.Qna;
import com.myomi.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
//@DynamicInsert
//@DynamicUpdate
@SequenceGenerator(
		 name = "PRODUCT_SEQ_GENERATOR",
		 sequenceName = "PRODUCT_SEQ", //매핑할 데이터베이스 시퀀스 이름
		 initialValue = 1, allocationSize = 1)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	   generator = "PRODUCT_SEQ_GENERATOR")
	private Long prodNum;
	
	@ManyToOne
//	(fetch = FetchType.LAZY) //지연로딩
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
	private float stars; //테스트 끝나고 float로 바꾸기..ㅠㅠ.까먹음
	
	@Column(name = "fee")
	@ColumnDefault("9") //default 9
	private int fee;

	@OneToMany(mappedBy = "product"
//			, fetch = FetchType.LAZY
			)
	@JsonIgnore
	private List<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "prodNum", 
			fetch = FetchType.LAZY, 
			cascade = CascadeType.REMOVE)
	private List<Qna> qnas;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Cart> cart;
	
	@Builder
	public Product(
			Long prodNum,
			Seller seller, String category, String name,
			Long originPrice, int percentage, int week, int status,
			String detail, int fee, List<OrderDetail> orderDetails) {
		this.prodNum = prodNum;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.originPrice = originPrice;
		this.percentage = percentage;
		this.week = week;
		this.status = status;
		this.detail = detail;
		this.fee = fee;
		this.orderDetails = orderDetails;
	}
	
	//상품 등록한 셀러
	public void registerSeller(Seller seller) {this.seller = seller;}
}
