package com.myomi.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "membership_num")
	private Membership membership;

	@OneToOne(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Point point;

	@OneToOne(mappedBy = "sellerId", fetch = FetchType.LAZY)
	private Seller seller;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Board> boards;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Review> reviews;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Qna> qnas;

	@JsonIgnore
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Follow> follows;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Cart> cart;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Coupon> coupons;
}
