package com.myomi.coupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
