package com.myomi.review.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="best_review")
@DynamicInsert
@DynamicUpdate
public class BestReview {
	@Id
	@Column(name = "review_num")
	private Long rNum;
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "review_num")
	private Review review;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
}
