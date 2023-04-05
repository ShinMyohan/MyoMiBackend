package com.myomi.point.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
@Entity
//@DynamicInsert
//@DynamicUpdate
public class Point {
	@Id
	@Column(name = "user_id")
	private String id;
	
	@MapsId
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable = false)
	@JsonIgnore
	private User userId;
	
	@Column(name = "total_point")
	@Min(0) //최소값 
	private int totalPoint;
	
	@OneToMany(mappedBy = "point")
	private List<PointDetail> pointDetails;

	
	@Builder
	public Point(String id, User userId, @Min(0) int totalPoint, List<PointDetail> pointDetails) {
		
		this.id = id;
		this.userId = userId;
		this.totalPoint = totalPoint;
		this.pointDetails = pointDetails;
	}
	
	
}