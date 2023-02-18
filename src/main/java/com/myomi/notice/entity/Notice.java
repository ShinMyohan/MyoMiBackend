package com.myomi.notice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.admin.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice")
@DynamicInsert
@DynamicUpdate
public class Notice {
	@Id
	@Column(nullable = false, name = "num")
	private Long nNum;

	@ManyToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private Admin admin;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(name = "created_date")
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private LocalDateTime createdDate;
}
