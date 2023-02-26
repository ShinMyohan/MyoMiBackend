package com.myomi.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.cart.entity.Cart;
import com.myomi.order.entity.OrderDetail;
import com.myomi.qna.entity.Qna;
import com.myomi.seller.entity.Seller;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(
		 name = "PRODUCT_SEQ_GENERATOR",
		 sequenceName = "PRODUCT_SEQ", //매핑할 데이터베이스 시퀀스 이름
		 initialValue = 1, allocationSize = 1)
public class Product {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	   generator = "PRODUCT_SEQ_GENERATOR")
	private Long prodNum;
	
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
	private float stars; //테스트 끝나고 float로 바꾸기..ㅠㅠ.까먹음
	
	@Column(name = "fee")
	@ColumnDefault("9") //default 9
	private int fee;

	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "prodNum", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Qna> qnas;

	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Cart> cart;
}
