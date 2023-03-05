package com.myomi.board.control;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.entity.Board;
import com.myomi.board.service.BoardService;
import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.exception.RemoveException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins="*")
@RestController
//@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "게시판")
public class BoardController {
	private final BoardService service;

	@ApiOperation(value = "게시판 | 글 목록 보기 ")
	@GetMapping("/board/list")
	public ResponseEntity<?> boardAll(
			//size = 한 페이지당 글 개수 
			@PageableDefault(sort = { "createdDate" }, direction = Direction.DESC) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.getBoard(pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 | 제목으로 검색")
	@GetMapping("/board/list/{keyword}")
	public ResponseEntity<?> boardAllByTitle (@PathVariable String keyword,
			@PageableDefault(sort = { "createdDate" }, direction = Direction.DESC) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.getByTitle(keyword, pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 | 제목 + 카테고리로 검색 ")
	@GetMapping("/board/list/{category}/{title}")
	public ResponseEntity<?> boardAllByTitleAndCategory (@PathVariable String category,
			@PathVariable String title,
			@PageableDefault(sort = { "createdDate" }, direction = Direction.DESC) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.getByCategoryAndTitle(category, title, pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 | 글 작성 ")
	@PostMapping("/board/add")  //로그인필요 
	public ResponseEntity<?> boardAdd(@RequestBody BoardReadResponseDto addDto,
			Authentication user) throws AddException{
		service.addBoard(addDto, user);
		return new ResponseEntity<>( HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 | 글 상세 + 댓글 보기 ")
	@GetMapping("/board/{boardNum}")
	public ResponseEntity<?> boardDetail(@PathVariable Long boardNum){
		BoardReadResponseDto dto = service.detailBoard(boardNum);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 | 글 수정 ")
	@PutMapping("/board/{boardNum}") //로그인필요 
	public ResponseEntity<?> boardModify(@RequestBody BoardReadResponseDto editDto, 
			@PathVariable Long boardNum, Authentication user) throws AddException{
		service.modifyBoard(editDto, boardNum, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 | 글 삭제 ")
	@DeleteMapping("/board/{boardNum}") //로그인필요 
	public ResponseEntity<?> boardDelete (@PathVariable Long boardNum, Authentication user) throws RemoveException{
		service.deleteBoard(boardNum, user);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "마이페이지 | 내가 작성한 글 보기 ")
	@GetMapping("/mypage/boardList")
	public ResponseEntity<?> myBoardList(Authentication user,
			@PageableDefault(size=4) Pageable pageable) throws FindException{
		List<BoardReadResponseDto> list = service.findBoardListByUser(user,pageable);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


}