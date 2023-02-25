
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.order.entity.OrderDetail;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(name = "REVIEW_SEQ_GENERATOR", sequenceName = "REVIEW_SEQ", // 매핑할 데이터베이스 시퀀스 이름
		initialValue = 1, allocationSize = 1)
public class Review {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
	private Long rvNum;

	@ManyToOne
	@JoinColumn(name = "user_id",updatable = false)
	@JsonIgnore
	private User user;

	@Column(name = "sort",updatable = false)
	private int sort;

	@Column(name = "title")
	@NotNull
	private String title;

	@Column(name = "content")
	@NotNull
	private String content;

	@Column(name = "created_date",updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "stars",updatable = false)
	private float stars;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "order_num",updatable = false), 
	@JoinColumn(name = "prod_num",updatable = false) })
	@JsonIgnore
	private OrderDetail orderDetail;

	@OneToOne(mappedBy = "review")
	private BestReview bestReview;
}