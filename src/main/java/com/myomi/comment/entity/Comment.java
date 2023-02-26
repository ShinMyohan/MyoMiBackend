package com.myomi.comment.entity;

import com.myomi.board.entity.Board;
import com.myomi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="comments")
@SequenceGenerator(
name = "COMMENTS_SEQ_GENERATOR",
sequenceName = "COMMENTS_SEQ", 
initialValue = 1, allocationSize = 1 )
@DynamicInsert
@DynamicUpdate
public class Comment {
	@Id
	@Column(name = "comment_num",  updatable =  false)
	@GeneratedValue(
				strategy = GenerationType.SEQUENCE,
				generator = "COMMENTS_SEQ_GENERATOR") 
	private Long commentNum;
	
    @ManyToOne
    @JoinColumn(name="board_num", nullable = false ,
    		                      updatable =  false)
	private Board board;
	//게시판 글 번호 -> 마이페이지에서 글 정보 필요함 
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false,
			                      updatable =  false)
	private User user;
	
	@Column(name = "content")
	@NotNull
	private String content;
	
	@Column(name = "parent", updatable =  false)
	private int parent;
	//부모 댓글 번호 
	
	@Column(name = "created_date", updatable =  false)
	 private LocalDateTime createdDate;
}
