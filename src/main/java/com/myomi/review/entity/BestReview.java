package com.myomi.review.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//fk를 갖는 클래스
@Getter
@NoArgsConstructor
@Entity
@Table(name = "best_review")
public class BestReview implements Serializable {
	@Id
	@Column(name = "review_num")
	private Long reviewNum;

	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn( name = "review_num")
	@NotNull
	@JsonIgnore
	private Review review;

	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@Builder
	public BestReview(Review review,Long reviewNum, LocalDateTime createdDate) {
		this.review=review;
		this.reviewNum = reviewNum;
		this.createdDate = createdDate;
	}
	
	
}
