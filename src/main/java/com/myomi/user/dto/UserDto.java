package com.myomi.user.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.board.entity.Board;
import com.myomi.cart.entity.Cart;
import com.myomi.comment.entity.Comment;
import com.myomi.coupon.entity.Coupon;
import com.myomi.follow.entity.Follow;
import com.myomi.membership.entity.Membership;
import com.myomi.order.entity.Order;
import com.myomi.point.entity.Point;
import com.myomi.qna.entity.Qna;
import com.myomi.review.entity.Review;
import com.myomi.seller.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String id;
	
	private String pwd;
	
	@JsonIgnore
    private List<String> roles = new ArrayList<>();
 
	@JsonIgnore
	private int role;
	
	private String name;
	
	private String tel;
	
	private String email;
	
	private String addr;
	
	private LocalDateTime createdDate;
	
	private Date signoutDate;
	
	@JsonIgnore
	private Membership membership;
	
	@JsonIgnore
	private Point point;
	
	@JsonIgnore
	private Seller seller;
	
	@JsonIgnore
	private List<Board> boards;
	
	@JsonIgnore
	private List<Comment> comments;

	@JsonIgnore
	private List<Review> reviews;
	
	@JsonIgnore
	private List<Order> orders;
	
	@JsonIgnore
	private List<Qna> qnas;
	
	@JsonIgnore
	private List<Follow> follows;
	
	@JsonIgnore
	private List<Cart> cart;
	
	@JsonIgnore
	private List<Coupon> coupons;
	
	@Builder
	public UserDto(String id, String pwd,
			int role,
//			UserRole role,
			List<String> roles,
			String name, String tel, String email, 
			String addr, LocalDateTime createdDate,
			Membership membership , 
			Point point,
			Seller seller,
			List<Board> boards, List<Comment> comments, List<Review> reviews,
			List<Order> orders, List<Qna> qnas, List<Follow> follows,
			List<Cart> cart, List<Coupon> coupons
			) {
		this.id = id;
		this.pwd = pwd;
		this.role = role;
		this.roles = roles;
		this.name = name;
		this.tel = tel;
		this.email = email;
		this.addr = addr;
		this.createdDate = createdDate;
		this.membership = membership;
		this.point = point;
		this.seller = seller;
		this.boards = boards;
		this.comments = comments;
		this.reviews = reviews;
		this.orders = orders;
		this.qnas = qnas;
		this.follows = follows;
		this.cart = cart;
		this.coupons = coupons;
	}
}
