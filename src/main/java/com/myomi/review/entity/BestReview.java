package com.myomi.review.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
	@JoinColumn( name = "review_num")
	@NotNull
	private Review review;

	@Column(name = "created_date",updatable = false)
	private LocalDateTime createdDate;
}
