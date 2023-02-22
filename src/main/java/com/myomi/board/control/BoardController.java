package com.myomi.board.control;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.board.dto.BoardAddRequestDto;
import com.myomi.board.dto.BoardReadResponseDto;
import com.myomi.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j

public class BoardController {
      private final BoardService service;
	
	@GetMapping("/list")
	  public List<BoardReadResponseDto> BoardList(
			                     //size = 한 페이지당 글 개수 
			       @PageableDefault(size=4) Pageable pageable) {
		return service.findBoard(pageable);
	}
	
	@PostMapping("/add")
	public void BoardAdd(BoardAddRequestDto addDto) {
		service.addBoard(addDto);
	}
}