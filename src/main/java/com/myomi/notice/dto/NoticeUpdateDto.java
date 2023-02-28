package com.myomi.notice.dto;

import java.time.LocalDateTime;

import com.myomi.admin.entity.Admin;
import com.myomi.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class NoticeUpdateDto {
	

	private String title;
	private String content;

	
	@Builder
	public NoticeUpdateDto(String title, String content) {
		this.title = title;
		this.content = content;
	
	}

	public Notice toEntity( 
			NoticeUpdateDto noticeUpdateDto
			,Admin admin,Long noticeNum) {
		LocalDateTime date=LocalDateTime.now();
		return Notice.builder()
					.noticeNum(noticeNum)
					.admin(admin)
					.title(title)
					.content(content)
					.createdDate(date)
					.build();
				
	}
}