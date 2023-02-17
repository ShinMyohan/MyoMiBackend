package com.myomi.review.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.myomi.order.entity.OrderDetail;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="review")
@SequenceGenerator(
		 name = "REVIEW_SEQ_GENERATOR",
		 sequenceName = "REVIEW_SEQ", //매핑할 데이터베이스 시퀀스 이름
		 initialValue = 1, allocationSize = 1)
public class Review {
	@Id
	@JoinColumn(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	   generator = "REVIEW_SEQ_GENERATOR")
	private Long rNum;
	
	@ManyToOne
//	(
//			cascade = {
//					CascadeType.PERSIST,
//					CascadeType.MERGE //머지쓰면 update된다. 즉 유저 한명당 리뷰 하나만...가능..!
//					})
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "sort")
	private int sort;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@Column(name = "stars")
	private float stars;
	
	@OneToOne
	@JoinColumns({@JoinColumn(name = "order_num"),
		@JoinColumn(name = "prod_num")})
	private OrderDetail orderDetail;
	
	@OneToOne(mappedBy = "review")
	private BestReview bestReview;
	
}
