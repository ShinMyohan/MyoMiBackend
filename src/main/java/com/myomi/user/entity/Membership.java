package com.myomi.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "membership")
@SequenceGenerator(name = "MEMBERSHIP_SEQ_GENERATOR", // 사용할 sequence 이름
sequenceName = "MEMBERSHIP_SEQ", // 실제 데이터베이스 sequence 이름
initialValue = 1, allocationSize = 1 )
public class Membership {
	@Id
	@Column(name = "membership_num")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, generator = "MEMBERSHIP_SEQ_GENERATOR") // 위의 sequence 이름 
	private int membershipNum;
	
	@Column(name = "membership_level")
	private String membershipLevel;
	
//	@OneToMany(mappedBy = "membership")
//	private List<User> users;
}
