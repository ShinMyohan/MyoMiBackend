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
import org.springframework.web.bind.annotation.RestController;

import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.service.BoardService;
import com.myomi.comment.dto.CommentDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j


public class BoardController {
      private final BoardService service;
      
    //글 목록 보기 
	@GetMapping("board/list")
	  public List<BoardReadResponseDto> boardList(
			                     //size = 한 페이지당 글 개수 
			       @PageableDefault(size=4) Pageable pageable) {
		
		return service.findBoard(pageable);
	}
	
	//제목으로 검색 
	@GetMapping("board/list/{keyword}")
	public List<BoardReadResponseDto> boardListByTitle (@PathVariable String keyword,
			@PageableDefault(size=4) Pageable pageable) {
		return service.findByTitle(keyword, pageable);
	}
	
	//제목과 카테고리로 검색 
	@GetMapping("board/list/{category}/{title}")
	public List<BoardReadResponseDto> boardListByTitleAndCategory (@PathVariable String category,
																   @PathVariable String title,
																   @PageableDefault(size=4) Pageable pageable) {
		return service.findByCategoryAndTitle(category, title, pageable);
	}

	
	//글 작성 
	@PostMapping("board/add")  //로그인필요 
	public ResponseEntity<BoardReadResponseDto> boardAdd(@RequestBody BoardReadResponseDto addDto,
			Authentication user) {
		return service.addBoard(addDto, user);
	}
	
	//글 상세보기 + 댓글 리스트 같이 
	@GetMapping("board/detail/{bNum}")
	public BoardReadResponseDto boardDetail(@PathVariable Long bNum) {
	    return service.detailBoard(bNum);
	}

	//글 수정
	@PutMapping("board/detail/{bNum}") //로그인필요 
	public void boardModify(@RequestBody BoardReadResponseDto editDto, 
			@PathVariable Long bNum, Authentication user) {
		service.modifyBoard(editDto, bNum, user);
	}
	
	//글 삭제 
	@DeleteMapping("board/detail{bNum}") //로그인필요 
	public void boardDelete (@PathVariable Long bNum, Authentication user) {
		service.deleteBoard(bNum, user);
	}

	//마이페이지에서 내가 작성한 글 보기 
	@GetMapping("mypage/boardList")
	public List<BoardReadResponseDto> myBoardList(Authentication user,
			@PageableDefault(size=4) Pageable pageable){
		return service.findBoardListByUser(user,pageable);
	}
	
	//=======================댓글===========================
	
	//댓글 작성 
	@PostMapping("board/detail/{bNum}")
	public ResponseEntity<CommentDto> commentAdd(@RequestBody CommentDto cDto, 
												 Authentication user,		
												 @PathVariable Long bNum ) {
		return service.addComment(cDto,user,bNum);
	}
	
	//댓글 수정 
	@PutMapping("board/detail/{bNum}/{cNum}")
	public void commentModify (@RequestBody CommentDto cDto, 
							   Authentication user,	
							   @PathVariable Long bNum,
							   @PathVariable Long cNum) {
		service.modifyComment(cDto, user, bNum, cNum);
		
	}
	
	//댓글 삭제 
	@DeleteMapping("board/detail/{bNum}/{cNum}")
	public void commentDelete (Authentication user,	
							   @PathVariable Long bNum,
							   @PathVariable Long cNum) {
	    service.deleteComment(user, bNum, cNum);
	}
}