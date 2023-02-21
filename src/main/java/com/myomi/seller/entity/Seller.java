package com.myomi.seller.entity;

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

import com.myomi.follow.entity.Follow;
import com.myomi.user.entity.Product;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "seller")
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
	

	 
}
