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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//fk를 갖는 클래스
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "best_review")
public class BestReview implements Serializable {
	@Id
	@Column(name = "review_num")
	private Long rNum;

	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, name = "review_num")
	private Review review;

	@Column(nullable = false, name = "created_date")
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
}
