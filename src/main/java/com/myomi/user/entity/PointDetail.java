//package com.myomi.user.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MapsId;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Setter @Getter @NoArgsConstructor @AllArgsConstructor
//@Entity
//@Table(name="point_detail")
//
//public class PointDetail{
//	
//	@EmbeddedId	
//	private PointEmbedded embedded = new PointEmbedded();
//
//	@MapsId("createdDate")
//	@Column(name="created_date")
//	private LocalDateTime createdDate;
//	
//	@MapsId("id")
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User id;
//	
//	@Column(name="sort")
//	private Integer sort;
//	
//	@Column(name="amount")
//	private Integer amount;
//	
//	
//}
