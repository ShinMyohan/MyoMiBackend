package com.myomi.notice.entity;

import com.myomi.admin.entity.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@DynamicUpdate
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "notice")
@SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ", // 매핑할 데이터베이스 시퀀스 이름
initialValue = 1, allocationSize = 1)

public class Notice {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQ_GENERATOR")
	private Long noticeNum;

	@ManyToOne
	@JoinColumn(name = "admin_id")
	private Admin admin;

	@NotNull
	private String title;

	@NotNull
	private String content;

	@Column(name = "created_date",updatable = false)
	private LocalDateTime createdDate;

	@Builder
	public Notice(Long noticeNum, Admin admin, String title, String content, LocalDateTime createdDate) {
		this.noticeNum = noticeNum;
		this.admin = admin;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
	}
	public void update(String title,String content) {
		this.title=title;
		this.content=content;
	}
}
