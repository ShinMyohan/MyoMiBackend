package com.myomi.point.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Point implements Serializable {
	@Id
	@OneToOne
	@JoinColumn(name = "user_id")
	private User userId;
	
	@Column(name = "total_point")
	private Integer totalPoint;
}
