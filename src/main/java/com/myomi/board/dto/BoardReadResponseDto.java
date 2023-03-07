package com.myomi.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myomi.board.entity.Board;
import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.entity.Comment;
import com.myomi.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardReadResponseDto {
	private Long boardNum;
	@JsonIgnore
    private User user;
    private String category;
    private String title;
    private String content;
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
    private LocalDateTime createdDate;
    private Long hits;
    private String userName;
    private List<CommentDto> comments;
    private List<Comment> reply;
    private MultipartFile file;
    private String boardImgUrl;
    private boolean enableUpdate;
    private boolean enableDelete;
    
    @Builder  
    public BoardReadResponseDto(Long boardNum, User user, String category, String title, String content,
    		LocalDateTime createdDate, Long hits, String userName, List<CommentDto> comments, List<Comment> reply,
    		MultipartFile file, String boardImgUrl, boolean enableUpdate, boolean enableDelete) {
    	super();
    	this.boardNum = boardNum;
    	this.user = user;
    	this.category = category;
    	this.title = title;
    	this.content = content;
    	this.createdDate = createdDate;
    	this.hits = hits;
    	this.userName = userName;
    	this.comments = comments;
    	this.reply = reply;
    	this.file = file;
    	this.boardImgUrl = boardImgUrl;
    	this.enableUpdate = enableUpdate;
    	this.enableDelete = enableDelete;
    }

    public Board toEntity(User user, String fileUrl) {
    	LocalDateTime date = LocalDateTime.now();
    	return Board.builder()
    			.user(user)
    			.category(category)
    			.title(title)
    			.content(content)
    			.createdDate(date)
    			.boardImgUrl(fileUrl)
    			.build();
    }
}
