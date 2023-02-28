package com.myomi.coupon.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
@Entity
@Table(name="coupon")
@SequenceGenerator(
name = "COUPON_SEQ_GENERATOR",
sequenceName = "COUPON_SEQ", 
initialValue = 1, allocationSize = 1 )

/**
 * @쿠폰sort
 * 회원가입 쿠폰 : 0
 * 골드 : 1
 * 플래티넘 : 2
 * 다이아몬드 : 3
 */

/**
 * @쿠폰status
 * 사용 전 : 0
 * 사용됨 : 1
 * 만료됨 : 2 
 */
public class Coupon {
	@Id
	@Column(name="num")
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "COUPON_SEQ_GENERATOR") 
	private Long couponNum;
	
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false
    		, updatable =  false)
	private User user;
	
	@Column(name="sort"
			, updatable =  false)
	//@NotNull
	private int sort;
	
	@Column(name="percentage"
			, updatable =  false)
	//@NotNull
	private int percentage;
	
	@Column(name="created_date"
			, updatable =  false)
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	//@NotNull
	private LocalDateTime createdDate;
	
	@Column(name="used_date")
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	private LocalDateTime usedDate;
	
	@Column(name="status")
	@ColumnDefault("'0'")
	private int status;
	//status:0 -> 사용 전 

	@Builder
	public Coupon(User user, int sort, int percentage, LocalDateTime createdDate, LocalDateTime usedDate, int status,
			Long couponNum) {
		this.user = user;
		this.sort = sort;
		this.percentage = percentage;
		this.createdDate = createdDate;
		this.usedDate = usedDate;
		this.status = status;
		this.couponNum = couponNum;
	}

	//더티체킹
	public void update (LocalDateTime usedDate, int status) {
		this.usedDate = usedDate;
		this.status = status;
	}

	@PrePersist
	public void prePersist() {
		this.status = 0 ;
	}
	
	
}
