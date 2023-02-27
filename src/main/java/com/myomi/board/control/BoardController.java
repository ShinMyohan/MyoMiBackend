package com.myomi.board.control;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.service.BoardService;
import com.myomi.comment.dto.CommentDto;
import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.exception.RemoveException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "게시글과 댓글")

public class BoardController {
      private final BoardService service;
      

    @ApiOperation(value = "게시판 | 글 목록 보기 ")
	@GetMapping("/list")
	  public ResponseEntity<?> boardList(
			                     //size = 한 페이지당 글 개수 
			       @PageableDefault(size=4) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.findBoard(pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
    @ApiOperation(value = "게시판 | 제목으로 검색")
	@GetMapping("/list/{keyword}")
	public ResponseEntity<?> boardListByTitle (@PathVariable String keyword,
			@PageableDefault(size=4) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.findByTitle(keyword, pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
    @ApiOperation(value = "게시판 | 제목 + 카테고리로 검색 ")
	@GetMapping("/list/{category}/{title}")
	public ResponseEntity<?> boardListByTitleAndCategory (@PathVariable String category,
																   @PathVariable String title,
																   @PageableDefault(size=4) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.findByCategoryAndTitle(category, title, pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
    @ApiOperation(value = "게시판 | 글 작성 ")
	@PostMapping("/add")  //로그인필요 
	public ResponseEntity<?> boardAdd(@RequestBody BoardReadResponseDto addDto,
			Authentication user) throws AddException{
		service.addBoard(addDto, user);
		return new ResponseEntity<>( HttpStatus.OK);
		
	}
	
    @ApiOperation(value = "게시판 | 글 상세 + 댓글 보기 ")
	@GetMapping("/detail/{bNum}")
	public ResponseEntity<?> boardDetail(@PathVariable Long bNum) throws FindException{
		BoardReadResponseDto dto = service.detailBoard(bNum);
	    return new ResponseEntity<>(dto, HttpStatus.OK);
	}

    @ApiOperation(value = "게시판 | 글 수정 ")
	@PutMapping("/detail/{bNum}") //로그인필요 
	public ResponseEntity<?> boardModify(@RequestBody BoardReadResponseDto editDto, 
			@PathVariable Long bNum, Authentication user) throws AddException{
		service.modifyBoard(editDto, bNum, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    @ApiOperation(value = "게시판 | 글 삭제 ")
	@DeleteMapping("/detail{bNum}") //로그인필요 
	public ResponseEntity<?> boardDelete (@PathVariable Long bNum, Authentication user) throws RemoveException{
		service.deleteBoard(bNum, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

    @ApiOperation(value = "마이페이지 | 내가 작성한 글 보기 ")
	@GetMapping("/mypage/boardList")
	public ResponseEntity<?> myBoardList(Authentication user,
			@PageableDefault(size=4) Pageable pageable) throws FindException{
		 List<BoardReadResponseDto> list = service.findBoardListByUser(user,pageable);
		 return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//=======================댓글===========================
	
    @ApiOperation(value = "댓글 | 댓글 작성 ")
	@PostMapping("/detail/{boardNum}")
	public ResponseEntity<?> commentAdd(@RequestBody CommentDto cDto, 
												 Authentication user,		
												 @PathVariable Long boardNum ) throws AddException{
		service.addComment(cDto,user,boardNum);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    @ApiOperation(value = "댓글 | 댓글 수정 ")
	@PutMapping("/detail/{boardNum}/{commentNum}")
	public ResponseEntity<?> commentModify (@RequestBody CommentDto cDto, 
							   Authentication user,	
							   @PathVariable Long boardNum,
							   @PathVariable Long commentNum) throws AddException{
		
		service.modifyComment(cDto, user, boardNum, commentNum);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
    @ApiOperation(value = "댓글 | 댓글 삭제 ")
	@DeleteMapping("/detail/{boardNum}/{commentNum}")
	public ResponseEntity<?>  commentDelete (Authentication user,	
							   @PathVariable Long boardNum,
							   @PathVariable Long commentNum) throws RemoveException{
	    service.deleteComment(user, boardNum, commentNum);
	    return new ResponseEntity<>(HttpStatus.OK);
	}
}