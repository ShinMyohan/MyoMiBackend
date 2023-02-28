package com.myomi.point.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

 @Getter @NoArgsConstructor
@Entity
@Table(name="point_detail")

/**
 * @포인트sort
 * 가입 : 0
 * 구매 : 1
 * 결제차감 : 2
 * 일반리뷰 : 3
 * 포토리뷰 : 4
 * 베스트리뷰 : 5
 * 등급업 : 6
 */
 
public class PointDetail{
   @EmbeddedId
   private PointDetailEmbedded pointEmbedded;
  
    @Column(name="sort" ,updatable = false)
    @NotNull
	private int sort;
	
	@Column(name="amount" ,updatable = false)
	@NotNull
	private int amount;
	
	@ManyToOne
	@JoinColumn(name="user_id",insertable = false ,updatable = false)
	private Point point;

	
	@Builder
	public PointDetail(PointDetailEmbedded pointEmbedded, @NotNull int sort, @NotNull int amount, Point point) {
		this.pointEmbedded = pointEmbedded;
		this.sort = sort;
		this.amount = amount;
		this.point = point;
	}


	
	
	
}