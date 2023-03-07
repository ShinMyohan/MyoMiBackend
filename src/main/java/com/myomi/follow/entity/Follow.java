package com.myomi.follow.entity;

import com.myomi.seller.entity.Seller;
import com.myomi.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="follow")
@DynamicInsert
@DynamicUpdate
public class Follow {
	@EmbeddedId
	private FollowEmbedded id = new FollowEmbedded();
	
	@MapsId("uId")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;
	
	@MapsId("sId")
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller sellerId;
	
	public Follow(User user, Seller seller) {
		this.userId = user;
		this.sellerId = seller;
		
	}	
}


