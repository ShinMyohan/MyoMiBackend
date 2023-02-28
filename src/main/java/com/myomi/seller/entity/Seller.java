package com.myomi.seller.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.follow.entity.Follow;
import com.myomi.product.entity.Product;
import com.myomi.qna.entity.Qna;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private User sellerId;
	
	@Column(name = "company_name")
	@NotNull
	private String companyName;
	
	@Column(name = "company_num")
	@NotNull
	private String companyNum;
	
	@Column(name = "internet_num")
	@NotNull
	private String internetNum;
	
	@Column(name = "addr")
	@NotNull
	private String addr;
	
	@Column(name = "manager")
	@NotNull
	private String manager;
	
	@Column(name = "bank_account")
	@NotNull
	private String bank_account;
	
	@ColumnDefault("'0'")
	@Column(name = "status")
	private int status;
	
	@ColumnDefault("'0'")
	@Column(name = "follow_cnt")
	private Long followCnt;
	
	@OneToMany(mappedBy = "sellerId")
	private List<Follow> follows;
	
	@OneToMany(mappedBy ="seller",cascade = CascadeType.REMOVE)
	private List<Product> products;

	
	
	@Builder
	public Seller(String id, User sellerId, @NotNull String companyName, @NotNull String companyNum,
			@NotNull String internetNum, @NotNull String addr, @NotNull String manager, @NotNull String bank_account,
			int status, Long followCnt, List<Follow> follows, List<Product> products) {
		this.id = id;
		this.sellerId = sellerId;
		this.companyName = companyName;
		this.companyNum = companyNum;
		this.internetNum = internetNum;
		this.addr = addr;
		this.manager = manager;
		this.bank_account = bank_account;
		this.status = status;
		this.followCnt = followCnt;
		this.follows = follows;
		this.products = products;
	}
	
}
