package com.myomi.qna.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
//@SequenceGenerator(
//name =
//"qna_SEQ_GENERATOR", // 사용할 sequence 이름
//sequenceName =
//"qna_seq", // 실제 데이터베이스 sequence 이름
//initialValue = 1, allocationSize = 1 )


@Entity //엔티티 객체
@Table(name= "qna") //어떤 테이블과 영속성을 유지할지 
@DynamicInsert
@DynamicUpdate
@SequenceGenerator(
		 name = "QNA_SEQ_GENERATOR",
		 sequenceName = "QNA_SEQ", //매핑할 데이터베이스 시퀀스 이름
		 initialValue = 1, allocationSize = 1)
public class Qna {
	@Id
	@Column(name = "num")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = 
			"QNA_SEQ_GENERATOR") // 위의 sequence 이름
	private Integer qNum;
	
	@ManyToOne
	@JoinColumn(name="qna_user", nullable = false) //name:fk에 참여할컬럼명 
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="prod_num", nullable = false) //name:fk에 참여할컬럼명 
	private Product prodNum;
	
	
	@Column(name="que_title",nullable = false)
	private String queTitle;
	
	@Column(name="que_content",nullable = false)
	private String queContent;
	
//	@JsonFormat(timezone="Asia/Seoul",pattern="yyyy.MM.dd HH:mm:ss")
//	private Date queCreatedDate;
	@Column(name="que_created_date", nullable = false)
	private LocalDateTime queCreatedDate;

	
	@Column(name="ans_content")
	private String ansContent;
	
//	@JsonFormat(timezone="Asia/Seoul",pattern="yyyy.MM.dd HH:mm:ss")
//	private Date ansCreatedDate;
	@Column(name="ans_created_date")
	private LocalDateTime ansCreatedDate;
}