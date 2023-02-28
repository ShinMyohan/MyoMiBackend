package com.myomi.notice.entity;

import com.myomi.admin.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice")
@SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ", // 매핑할 데이터베이스 시퀀스 이름
initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Notice {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQ_GENERATOR")
	private Long noticeNum;

	@ManyToOne
	@JoinColumn(name = "admin_id",updatable = false)
	private Admin admin;

	@NotNull
	private String title;

	@NotNull
	private String content;

	@Column(name = "created_date",updatable = false)
	private LocalDateTime createdDate;
}
