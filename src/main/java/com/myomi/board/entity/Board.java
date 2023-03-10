package com.myomi.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="board")
@DynamicUpdate
@SequenceGenerator(
name = "BOARD_SEQ_GENERATOR",
sequenceName = "BOARD_SEQ", 
initialValue = 1, allocationSize = 1 )
public class Board {
   @Id
   @Column(name="num", updatable =  false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
   generator = "BOARD_SEQ_GENERATOR")
   private Long boardNum;
   
  
   @ManyToOne (fetch = FetchType.LAZY)
   @JoinColumn(name="user_id", nullable = false,
                               updatable =  false)
   private User user;
   
   @Column(name = "category")
   @NotNull
   private String category;
   
   @Column(name = "title")
   @NotNull
   private String title;
   
   @Column(name = "content")
   @NotNull
   private String content;
   
   @Column(name = "created_date", updatable =  false)
   @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd")
   private LocalDateTime createdDate;
   
   @Column(name = "hits", updatable =  false)
 //  @ColumnDefault("'0'")
   private Long hits;
 
   @JsonIgnore
   @OneToMany(cascade = CascadeType.REMOVE, 
		      mappedBy = "board")
   @OrderBy("createdDate desc")
   private List<Comment> comments;
   
   @Column(name = "board_img_url", nullable = false)
   private String boardImgUrl;

    @Builder
    public Board(Long boardNum, User user, @NotNull String category, @NotNull String title, @NotNull String content,
		LocalDateTime createdDate, Long hits, String boardImgUrl) {
	this.boardNum = boardNum;
	this.user = user;
	this.category = category;
	this.title = title;
	this.content = content;
	this.createdDate = createdDate;
	this.hits = hits;
	this.boardImgUrl = boardImgUrl;
	//this.comments = comments;
}
    //더티체킹 
    public void update(String category, String title, String content) {
		this.category = category;
		this.title = title;
		this.content = content;
    }
    
    public void update(String boardImgUrl) {
    	this.boardImgUrl = boardImgUrl;
    }
    
    @PrePersist
    public void prePersist() {
        this.hits = this.hits == null ? 0 : this.hits;
        //hits가 null값이면 자동으로 0으로 셋팅 (=defaultColumn)
        //defaultColumn 사용하면 DynamicInsert없이 셋팅이 안됨 
    }
}
