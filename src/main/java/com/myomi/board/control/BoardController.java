package com.myomi.board.control;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.board.dto.BoardDetailResponseDto;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.entity.Board;
import com.myomi.board.service.BoardService;
import com.myomi.comment.dto.CommentDto;
import com.myomi.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j


public class BoardController {
      private final BoardService service;
      
  	// 임시 회원정보
      User userId = User.builder().id("id1").build();;
	
	@GetMapping("")
	  public List<BoardReadResponseDto> boardList(
			                     //size = 한 페이지당 글 개수 
			       @PageableDefault(size=4) Pageable pageable) {
		
		return service.findBoard(pageable);
	}
	
	@GetMapping("{keyword}")
	public List<BoardReadResponseDto> boardListByTitle (@PathVariable String keyword,
			@PageableDefault(size=4) Pageable pageable) {
		return service.findByTitle(keyword, pageable);
	}
	
	@GetMapping("{category}/{keyword}")
	public List<BoardReadResponseDto> boardListByTitleAndCategory (@PathVariable String category,
																   @PathVariable String keyword,
																   @PageableDefault(size=4) Pageable pageable) {
		return service.findByCategoryAndTitle(category,keyword, pageable);
	}
	
	@PostMapping("")  //로그인필요 
	public ResponseEntity<BoardReadResponseDto> boardAdd(@RequestBody BoardReadResponseDto addDto,
			//엔티티<디티오>
			//@AuthenticationPrincipal User user
			Authentication user) {
		return service.addBoard(addDto, user);
	}
	
	@GetMapping("detail/{bNum}")
	public BoardReadResponseDto boardDetail(@PathVariable Long bNum) {
	    return service.detailBoard(bNum);
	}

	@PutMapping("{bNum}") //로그인필요 
	public void boardModify(@RequestBody BoardReadResponseDto editDto, 
			@PathVariable Long bNum, Authentication user) {
		service.modifyBoard(editDto, bNum, user);
	}
	
	@DeleteMapping("{bNum}") //로그인필요 
	public void boardDelete (@PathVariable Long bNum, Authentication user) {
		service.deleteBoard(bNum, user);
	}

	
//	@GetMapping("mypage/{principal}")
//	public List<BoardReadResponseDto> myBoardList(@PathVariable Principal principal,
//			                           @PageableDefault(size=4) Pageable pageable) {
//		//String user = principal.getName();
//		return service.findBoardListByUser(principal, pageable);
//	}
	
	//=======================댓글===========================
	
	@PostMapping("{bNum}")
	public ResponseEntity<CommentDto> commentAdd(@RequestBody CommentDto cDto, 
												 Authentication user,		
												 @PathVariable Long bNum ) {
		return service.addComment(cDto,user,bNum);
	}
	
	@PutMapping("/{bNum}/{cNum}")
	public void commentModify (@RequestBody CommentDto cDto, 
							   Authentication user,	
							   @PathVariable Long bNum,
							   @PathVariable Long cNum) {
		service.modifyComment(cDto, user, bNum, cNum);
		
	}
	
	@DeleteMapping("/{bNum}/{cNum}")
	public void commentDelete (Authentication user,	
							   @PathVariable Long bNum,
							   @PathVariable Long cNum) {
	    service.deleteComment(user, bNum, cNum);
	}

	
}