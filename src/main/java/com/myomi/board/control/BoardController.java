package com.myomi.board.control;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.board.dto.BoardAddRequestDto;
import com.myomi.board.dto.BoardDetailResponseDto;
import com.myomi.board.dto.BoardEditRequestDto;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.service.BoardService;
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
	  public List<BoardReadResponseDto> BoardList(
			                     //size = 한 페이지당 글 개수 
			       @PageableDefault(size=4) Pageable pageable) {
		
		return service.findBoard(pageable);
	}
	
	@PostMapping("")
	public void BoardAdd(BoardAddRequestDto addDto) {
		service.addBoard(addDto);
	}
	
	@GetMapping("{bNum}")
	public BoardDetailResponseDto BoardDetail(@PathVariable Long bNum) {
	    return service.detailBoard(bNum);
	}

	@PutMapping("{bNum}")
	public void BoardModify(BoardEditRequestDto editDto, @PathVariable Long bNum) {
		service.modifyBoard(editDto, bNum);
	}
	
	@DeleteMapping("{bNum}")
	public void BoardDelete (@PathVariable Long bNum) {
		service.deleteBoard(bNum);
	}
	
}