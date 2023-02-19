package com.myomi.coupon.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="coupon")
//@SequenceGenerator(
//name = "coupon_seq",
//sequenceName = "coupon_seq", 
//initialValue = 1, allocationSize = 1 )

public class Coupon {
	@Id
	@Column(name="num")
//	@GeneratedValue(
//				strategy = GenerationType.SEQUENCE,
//				generator = "coupon_seq") 
	private Integer CpNum;
	
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
	private User user;
	
	@Column(name="sort")
	private Integer sort;
	
	@Column(name="percentage")
	private Integer percentage;
	
	@Column(name="created_date")
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	private LocalDateTime createdDate;
	
	@Column(name="used_date")
	@JsonFormat(timezone="Asia/Seoul", pattern ="yyyy-MM-dd")
	private LocalDateTime usedDate;
	
	@Column(name="status")
	private Integer status;
}