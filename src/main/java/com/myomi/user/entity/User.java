package com.myomi.user.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.follow.entity.Follow;
import com.myomi.qna.entity.Qna;
import com.myomi.seller.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
//@IdClass(Seller.class)
@Table(name="users")
@DynamicInsert
@DynamicUpdate
public class User {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "addr")
	private String addr;
	
	@Column(name = "role")
	private Integer role; //시큐리티 추가 후 UserRole enum 클래스 생성하면 빠질 예정
	
	@Column(name = "created_date")
//	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
//	@ColumnDefault(value = "SYSDATE")
	private LocalDateTime createdDate;
	
	@Column(name = "signout_date")
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date signoutDate;
	
//	@ManyToOne
//	@JoinColumn(name = "membership_num")
//	private Membership membership;
	
//	@OneToOne(mappedBy = "userId")
//	private Point point;
	
	@OneToOne(mappedBy = "sellerId")
	private Seller seller;
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	@JoinColumn(name = "cart_num")
//	private List<Cart> cart;
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	private List<Order> orders;
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	private List<Board> boards;
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	private List<Comment> comments;
//	
//	@OneToMany(fetch = FetchType.EAGER,
//			   mappedBy ="qNum")
//	private List<Qna> qnas;
//	
	@OneToMany(fetch = FetchType.EAGER,
				mappedBy ="userId")
	private List<Follow> follows;
	
	
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	private List<Review> reviews;
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	private List<Coupon> coupons;
}
