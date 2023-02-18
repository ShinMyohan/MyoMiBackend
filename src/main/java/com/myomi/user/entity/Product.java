package com.myomi.user.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.seller.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name= "product") //어떤 테이블과 영속성을 유지할지 
@DynamicInsert
@DynamicUpdate
public class Product {
	@Id
	@Column(name="num")
	private Integer pNum;
	
//	@Column(name="seller_id")
//	private Seller seller;
	
	@Column(name="category")
	private String category;
	
	@Column(name="name")
	private String name;
	
	@Column(name="origin_price")
	private Integer originPrice;
	
	@Column(name="percentage")
	private Integer percentage;
	
	@Column(name="week")
	private Integer week;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="detail")
	private String detail;
	
	@Column(name="review_cnt")
	private Integer reviewCnt;
	
	@Column(name="stars")
	private Integer stars;
	
	@Column(name="fee")
	private Integer fee;
	
	
//	private List<Qna> qnas;
//	private List<Review> reviews;
}
