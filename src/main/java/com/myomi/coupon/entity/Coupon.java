package com.myomi.coupon.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="coupon")
@SequenceGenerator(
name = "COUPON_SEQ_GENERATOR",
sequenceName = "COUPON_SEQ", 
initialValue = 1, allocationSize = 1 )

@DynamicUpdate
@DynamicInsert
public class Coupon {
	@Id
	@Column(name="num")
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "COUPON_SEQ_GENERATOR") 
	private Integer CpNum;
	
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false
    		, updatable =  false)
	private User user;
	
	@Column(name="sort"
			, updatable =  false)
	private Integer sort;
	
	@Column(name="percentage"
			, updatable =  false)
	private Integer percentage;
	
	@Column(name="created_date"
			, updatable =  false)
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	private LocalDateTime createdDate;
	
	@Column(name="used_date")
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	private LocalDateTime usedDate;
	
	@Column(name="status")
	@ColumnDefault("'0'")
	private Integer status;
	//status:0 -> 사용 전 
}
