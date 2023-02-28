package com.myomi.notice.dto;

import java.time.LocalDateTime;

import com.myomi.notice.entity.Notice;

import lombok.Getter;
@Getter
public class NoticeDetailResponseDto {
	private Long noticeNum;
	private String adminId;
	private String title;
	private String content;
	private LocalDateTime createdDate;
}
