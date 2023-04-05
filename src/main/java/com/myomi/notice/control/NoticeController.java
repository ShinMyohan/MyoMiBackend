package com.myomi.notice.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.myomi.common.status.NoResourceException;
import com.myomi.common.status.ResponseDetails;
import com.myomi.notice.dto.NoticeDto;
import com.myomi.notice.dto.NoticeRequestDto;
import com.myomi.notice.dto.NoticeUpdateDto;
import com.myomi.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/notice/*")
@RequiredArgsConstructor
@Slf4j

public class NoticeController {
	@Autowired
	private final NoticeService noticeService;
	
	
	//공지리스트출력
	@GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoticeDto> noticeList(){
		return noticeService.getAllList();
	}
	
	//작성
	@PostMapping("add")
	public ResponseEntity<?> noticeSave(@RequestBody NoticeRequestDto noticeDto,Authentication admin)throws NoResourceException{
		ResponseDetails responseDetails=noticeService.addNotice(noticeDto,admin);
		return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
	}
	//수정
	@PutMapping("{noticeNum}")
	public ResponseEntity<?> noticeModify(@RequestBody NoticeUpdateDto noticeDto, @PathVariable Long noticeNum
			,Authentication admin)throws NoResourceException { 
		ResponseDetails responseDetails=noticeService.modifyNotice(noticeDto,noticeNum,admin);
		return new ResponseEntity<>(responseDetails, HttpStatus.valueOf(responseDetails.getHttpStatus()));
	}
	
	//상세보기
	@GetMapping("{noticeNum}")
	public NoticeDto noticeDetail(@PathVariable Long noticeNum) {
		return noticeService.getOneNotice(noticeNum);
	}
	
	//삭제
	@DeleteMapping("{noticeNum}")
	public ResponseEntity<?> noticeRemove(@PathVariable Long noticeNum,Authentication admin){
		ResponseDetails responseDetails=noticeService.removeNotice(noticeNum,admin);
		return new ResponseEntity<>(responseDetails,HttpStatus.valueOf(responseDetails.getHttpStatus()));
	}
	
	//제목으로 검색
	@GetMapping(value ="title/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoticeDto> noticeListByTitle(@PathVariable String keyword
			){
		return noticeService.getAllNoticeByTitle(keyword);
	}
}
