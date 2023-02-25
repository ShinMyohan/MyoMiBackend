package com.myomi.notice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ", // 매핑할 데이터베이스 시퀀스 이름
initialValue = 1, allocationSize = 1)
@DynamicInsert
@DynamicUpdate
public class Notice {
	@Id
	@Column(name = "num")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQ_GENERATOR")
	private Long ntNum;

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
