package com.myomi.follow.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.seller.entity.Seller;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="follow")
@DynamicInsert
@DynamicUpdate
public class Follow {
	@EmbeddedId
	private FollowEmbedded id = new FollowEmbedded();
	
	@MapsId("uId")
	@ManyToOne
	@JoinColumn(name = "user_id",nullable=false)
	private User userId;
	
	@MapsId("sId")
	@ManyToOne
	@JoinColumn(name = "seller_id",nullable=false)
	private Seller sellerId;
}