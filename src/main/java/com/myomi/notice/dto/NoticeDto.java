package com.myomi.notice.dto;

import java.time.LocalDateTime;

import com.myomi.admin.dto.AdminDto;
import com.myomi.admin.entity.Admin;
import com.myomi.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
@Getter
public class NoticeDto {
	
	private Long noticeNum;
	private String adminId;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	
	@Builder
	public NoticeDto(Long noticeNum, String adminId, String title, String content, LocalDateTime createdDate) {
		this.noticeNum = noticeNum;
		this.adminId = adminId;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
	}
	public NoticeDto(Notice entity) {
		this.noticeNum=entity.getNoticeNum();
		this.adminId=entity.getAdmin().getAdminId();
		this.title=entity.getTitle();
		this.content=entity.getContent();
		this.createdDate=entity.getCreatedDate();
	}

}
