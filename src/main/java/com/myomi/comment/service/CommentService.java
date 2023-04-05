package com.myomi.comment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.board.entity.Board;
import com.myomi.board.repository.BoardRepository;
import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;
import com.myomi.common.status.AddException;
import com.myomi.common.status.ErrorCode;
import com.myomi.common.status.TokenValidFailedException;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class CommentService {
	private final CommentRepository cr;
	private final BoardRepository br; 
	private final UserRepository ur;

	//마이페이지에서 나의 댓글 목록 보기
	@Transactional
	public List<CommentDto> getMyCommentList(Authentication user) {
		String path = "/api/myboardlist";
		User u = ur.findById(user.getName())
				.orElseThrow(() -> new TokenValidFailedException(ErrorCode.UNAUTHORIZED, "로그인한 회원만 이용가능한 서비스입니다."));
		String username = user.getName();
		List<Comment> list = cr.findAllByComments(username);

		List<CommentDto> commentList = new ArrayList<>();
		for (Comment cmt : list) {
			CommentDto cDto = CommentDto.builder()
					.userName(username)
					.boardNum(cmt.getBoard().getBoardNum())
					.commentNum(cmt.getCommentNum())
					.content(cmt.getContent())
					.createdDate(cmt.getCreatedDate())
					.category(cmt.getBoard().getCategory())
					.title(cmt.getBoard().getTitle())
					.build();
			commentList.add(cDto);
		}
		return commentList;
	}

	//댓글 작성 
	@Transactional
	public ResponseEntity<CommentDto> addComment(CommentDto cDto, Authentication user, Long boardNum){
		String username = user.getName();
		System.out.println("서비스아이디~~~~~~~: "+ username);
		Optional<Board> optB = br.findById(boardNum);
		Optional<User> optU = ur.findById(username);
		LocalDateTime date = LocalDateTime.now();
		Comment comment = Comment.builder()
				.commentNum(cDto.getCommentNum())
				.content(cDto.getContent())
				.user(optU.get())
				.board(optB.get())
				.createdDate(date)
				.parent(cDto.getParent())
				.build();
		cr.save(comment);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//댓글 수정
	@Transactional
	public CommentDto modifyComment (CommentDto cDto, Authentication user, Long boardNum,
			Long commentNum) throws AddException{
		String username = user.getName();
		Optional<Board> optB = br.findById(boardNum);
		Optional<Comment> optC = cr.findById(commentNum);
		Comment comment = optC.get();
		if (comment.getUser().getId().equals(username)) {
			comment.update(cDto.getContent());

		}
		return cDto;
	}

	//댓글 삭제
	@Transactional
	public void deleteComment (Authentication user, Long boardNum,
			Long commentNum) {
		String username = user.getName();
		Optional<Board> optB = br.findById(boardNum);
		Optional<Comment> optC = cr.findById(commentNum);
		Comment comment = optC.get();
		if (comment.getUser().getId().equals(username)) {
			cr.delete(comment);

		}
	}
}
