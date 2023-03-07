package com.myomi.seller.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.follow.entity.Follow;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @NoArgsConstructor
@Entity(name = "Seller")
@Table(name = "seller_info")
@DynamicInsert
@DynamicUpdate
public class Seller {
	@Id
	@Column(name = "seller_id")
	private String id;
	
	@MapsId
	@OneToOne(cascade = {CascadeType.PERSIST ,CascadeType.MERGE})
	@JoinColumn(name = "seller_id", insertable = false)
	@JsonIgnore
	private User sellerId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "company_num")
	private String companyNum;
	
	@Column(name = "internet_num")
	private String internetNum;
	
	@Column(name = "addr")
	private String addr;
	
	@Column(name = "manager")
	private String manager;

	@Column(name = "bank_account")
	@NotNull
	private String bankAccount;

	@ColumnDefault("'0'")
	@Column(name = "status")
	private int status;
	
	@ColumnDefault("'0'")
	@Column(name = "follow_cnt")
	private Long followCnt;
	
	@JsonIgnore
	@OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
	private List<Product> products;
	
	@Builder
	public Seller(String id, User sellerId, String companyName, String companyNum,
				  String internetNum, String addr, String manager, String bankAccount,
				  int status, Long followCnt, List<Follow> follows, List<Product> products) {
		this.id = id;
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.companyNum = companyNum;
		this.internetNum = internetNum;
		this.addr = addr;
		this.manager = manager;
		this.bankAccount = bankAccount;
		this.status = status;
		this.followCnt = followCnt;
		this.follows = follows;
		this.products = products;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "sellerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Follow> follows;
}
