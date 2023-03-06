package com.myomi.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.board.entity.Board;
import com.myomi.cart.entity.Cart;
import com.myomi.comment.entity.Comment;
import com.myomi.coupon.entity.Coupon;
import com.myomi.follow.entity.Follow;
import com.myomi.membership.entity.MembershipLevel;
import com.myomi.order.entity.Order;
import com.myomi.point.entity.Point;
import com.myomi.qna.entity.Qna;
import com.myomi.review.entity.Review;
import com.myomi.seller.entity.Seller;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
//@DynamicInsert
@DynamicUpdate
public class User implements UserDetails
//, Persistable<String> 
{
    @Id
    @NotNull
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private String id;

    @NotNull
    @Column(name = "pwd", nullable = false)
    private String pwd;


    @ElementCollection(fetch = FetchType.EAGER)
//    @Builder.Default
    private List<String> roles = new ArrayList<>();


    //	@OneToOne(mappedBy = "roles")
//	private UserRole role;
//	@Enumerated(EnumType.STRING)
    private int role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tel", nullable = false, unique = true)
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "addr")
    private String addr;

    @Column(name = "created_date")
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")

    private LocalDateTime createdDate;

    @Column(name = "signout_date")
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    private Date signoutDate;

//    @ManyToOne(
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    @JoinColumn(name = "membership_num")
//    private Membership membership;

    @Column(name = "membership_level") // , nullable = false
    @Enumerated(EnumType.STRING) // 순서가 DB에 저장
    private MembershipLevel membership;

    @OneToOne(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Point point;

    @OneToOne(mappedBy = "sellerId", fetch = FetchType.LAZY)
    @JsonIgnore
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

    private String provider;

    @Builder
    public User(String id, String pwd,
                int role,
                List<String> roles,
                String name, String tel, String email,
                String addr, LocalDateTime createdDate, MembershipLevel membership) {
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
    }

    //for OAuth!
//	public User update(String name, String provider) {
//        this.name = name;
//        this.provider = provider;
//        return this;
//    }
//
//    public String getRoleKey() {
////        return this.roles.getKey();
//    	return this.getRoleKey();
//    }
}