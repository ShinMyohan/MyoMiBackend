package com.myomi.board.entity;

import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="board")
@SequenceGenerator(
name = "BOARD_SEQ_GENERATOR",
sequenceName = "BOARD_SEQ", 
initialValue = 1, allocationSize = 1 )
@DynamicInsert
@DynamicUpdate
public class Board {
   @Id
   @Column(name="num", updatable =  false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
   generator = "BOARD_SEQ_GENERATOR")
   private Long boardNum;
   
   @ManyToOne
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
   private LocalDateTime createdDate;
   
   @Column(name = "hits", updatable =  false)
   @ColumnDefault("'0'")
   private Long hits;
   
   @OneToMany(fetch = FetchType.EAGER ,
		      cascade = CascadeType.REMOVE, 
		      mappedBy = "board")
   private List<Comment> comments;
   
}
