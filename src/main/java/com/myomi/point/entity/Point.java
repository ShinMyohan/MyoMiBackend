package com.myomi.point.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
public class Point {
	@Id
	@Column(name = "user_id")
	private String id;
	
	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable = false)
	private User userId;
	
	@Column(name = "total_point")
	private Integer totalPoint;
}
