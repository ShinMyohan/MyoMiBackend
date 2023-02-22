package com.myomi.qna.entity;

import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(
name =
"QNA_SEQ_GENERATOR", // 사용할 sequence 이름
sequenceName =
"QNA_SEQ", // 실제 데이터베이스 sequence 이름
initialValue = 1, allocationSize = 1 )


@Entity //엔티티 객체
@Table(name= "qna") //어떤 테이블과 영속성을 유지할지 
@DynamicInsert
@DynamicUpdate

public class Qna implements Serializable{
	@Id
	@Column(name = "num")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = 
			"QNA_SEQ_GENERATOR") // 위의 sequence 이름
	private Long qNum;
	
	
	@ManyToOne
	@JoinColumn(name="qna_user",nullable=false)
	@NotNull
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="prod_num",nullable=false)
	@NotNull
	private Product prodNum;
	
	
	@Column(name="que_title")
	@NotNull
	private String queTitle;
	
	@Column(name="que_content")
	@NotNull
	private String queContent;
	
	@Column(name="que_created_date", updatable=false)
	@NotNull
	private LocalDateTime queCreatedDate;

	
	@Column(name="ans_content")
	private String ansContent;
	
	@Column(name="ans_created_date")
	private LocalDateTime ansCreatedDate;
	
	
	
}
