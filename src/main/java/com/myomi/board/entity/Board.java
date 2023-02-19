package com.myomi.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="board")
//@SequenceGenerator(
//name = "board_seq",
//sequenceName = "board_seq", 
//initialValue = 1, allocationSize = 1 )

public class Board {
   @Id
   @Column(name="num")
//   @GeneratedValue(
//			strategy = GenerationType.SEQUENCE,
//			generator = "board_seq") 
   private Integer bNum;
   
   @ManyToOne
   @JoinColumn(name="user_id", nullable = false)
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
   
   @OneToMany(fetch = FetchType.EAGER ,
		      cascade = CascadeType.ALL, 
		      mappedBy = "board")
   private List<Comment> comments;
   
}
