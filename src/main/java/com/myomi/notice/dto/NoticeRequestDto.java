package com.myomi.notice.dto;

import java.time.LocalDateTime;



import com.myomi.admin.dto.AdminDto;
import com.myomi.admin.entity.Admin;
import com.myomi.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class NoticeRequestDto {
	
	private String title;
	private String content;
	
	@Builder
	public NoticeRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
		
	}

	public Notice toEntity( 
			NoticeRequestDto noticeRequestDto
			,Admin admin) {
		LocalDateTime date=LocalDateTime.now();
		return Notice.builder()
					.admin(admin)
					.title(title)
					.content(content)
					.createdDate(date)
					.build();
				
	}
}