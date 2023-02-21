package com.myomi.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.board.entity.Board;
import com.myomi.cart.entity.Cart;
import com.myomi.comment.entity.Comment;
import com.myomi.coupon.entity.Coupon;
import com.myomi.follow.entity.Follow;
import com.myomi.order.entity.Order;
import com.myomi.point.entity.Point;
import com.myomi.qna.entity.Qna;
import com.myomi.review.entity.Review;
import com.myomi.seller.entity.Seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
@Table(name="users")
//@DynamicInsert
//@DynamicUpdate
public class User implements UserDetails {
	@Id
	@NotNull
	@Column(name = "id", updatable = false, unique = true)
	private String id;
	
	@NotNull
	@Column(name = "pwd")
	private String pwd;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
 
    @Column(name = "role")
//	@Enumerated(EnumType.STRING)
	private int role;
    
    @Override
    public String getUsername() {
        return id;
    }
 
    @Override
    public String getPassword() {
        return pwd;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "addr")
	private String addr;
	
	
	
	@Column(name = "created_date")
//	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
//	@ColumnDefault(value = "SYSDATE")
	private LocalDateTime createdDate;
	
	@Column(name = "signout_date")
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date signoutDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "membership_num")
	private Membership membership;
	
	@OneToOne(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Point point;
	
	@OneToOne(mappedBy = "sellerId", fetch = FetchType.LAZY)
	private Seller seller;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Board> boards;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Comment> comments;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Order> orders;
	
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Qna> qnas;
	
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Follow> follows;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Cart> cart;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Coupon> coupons;
}
