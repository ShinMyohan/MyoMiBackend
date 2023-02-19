package com.myomi.point.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="point")
@DynamicInsert
@DynamicUpdate
public class Point {
	@Id
	@JoinColumn(name = "user_id")
	private String id;
	
	@MapsId
	@OneToOne
	@JoinColumn(name ="user_id", insertable = false)
	private User userId;

	@Column(name="total_point")
	private Integer totalPoint;
	
//	@OneToMany(fetch = FetchType.EAGER,
//			   cascade = CascadeType.ALL,
//			   mappedBy="")
//	private List<PointDetail> pointDetail;

	
}
