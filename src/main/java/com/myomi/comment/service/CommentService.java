package com.myomi.comment.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myomi.comment.dto.CommentDto;
import com.myomi.comment.entity.Comment;
import com.myomi.comment.repository.CommentRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class CommentService {
	private final CommentRepository cr;
	
	//마이페이지에서 나의 댓글 목록 보기
	@Transactional
	public List<CommentDto> findMyCommentList(Authentication user, Pageable pageable) {
		String username = user.getName();
		List<Comment> list = cr.findAllByComments(username, pageable);
		List<CommentDto> commentList = new ArrayList<>();
		for (Comment cmt : list) {
			CommentDto cDto = CommentDto.builder()
					.board(cmt.getBoard())
					.content(cmt.getContent())
					.createdDate(cmt.getCreatedDate())
					.category(cmt.getBoard().getCategory())
					.title(cmt.getBoard().getTitle())
					.build();
			commentList.add(cDto);
		}
		return commentList;
	}
}
