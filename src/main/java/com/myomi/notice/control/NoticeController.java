package com.myomi.notice.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myomi.notice.dto.NoticeDto;
import com.myomi.notice.dto.NoticeRequestDto;
import com.myomi.notice.dto.NoticeUpdateDto;
import com.myomi.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/notice/")
@RequiredArgsConstructor
@Slf4j

public class NoticeController {
	@Autowired
	private final NoticeService noticeService;
	
	//공지리스트출력
	@GetMapping("")
	public List<NoticeDto> noticeList(
		@PageableDefault(size=4) Pageable pageable){
		return noticeService.getAllNotice(pageable);
	}
	//작성
	@PostMapping("add")
	public void noticeSave(@RequestBody NoticeRequestDto noticeDto,Authentication admin){
		noticeService.addNotice(noticeDto,admin);
	}
	//수정
	@PutMapping("{noticeNum}")
	public void noticeModify(@RequestBody NoticeUpdateDto noticeDto, @PathVariable Long noticeNum
			,Authentication admin) { 
		noticeService.modifyNotice(noticeDto,noticeNum,admin);
	}
	
	//상세보기
	@GetMapping("{noticeNum}")
	public NoticeDto noticeDetail(@PathVariable Long noticeNum) {
		return noticeService.getOneNotice(noticeNum);
	}
	
	//삭제
	@DeleteMapping("{noticeNum}")
	public void noticeDelete(@PathVariable Long noticeNum) {
		noticeService.RemoveNotice(noticeNum);
	}
	
	//제목으로 검색
	@GetMapping("title/{keyword}")
	public List<NoticeDto> NoticeListByTitle(@PathVariable String keyword,
			@PageableDefault(size=4) Pageable pageable
			){
		return noticeService.getAllNoticeByTitle(keyword,pageable);
	}
}
