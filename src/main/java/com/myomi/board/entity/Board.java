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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="board")
@SequenceGenerator(
		 name = "BOARD_SEQ_GENERATOR",
		 sequenceName = "BOARD_SEQ", //매핑할 데이터베이스 시퀀스 이름
		 initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Board {
   @Id
   @Column(name="num")
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
   generator = "BOARD_SEQ_GENERATOR")
   private Integer bNum;
   
   @ManyToOne
   @JoinColumn(name="user_id")
   private User user;
   
   @Column(name = "category")
   private String category;
   
   @Column(name = "title")
   private String title;
   
   @Column(name = "content")
   private String content;
   
   @Column(name = "created_date")
   private LocalDateTime createdDate;
   
   @Column(name = "hits")
   private Integer hits;
   
   @OneToMany(fetch = FetchType.EAGER,
		      cascade = CascadeType.REMOVE, 
		      mappedBy = "board")
   private List<Comment> comments;
   
}
