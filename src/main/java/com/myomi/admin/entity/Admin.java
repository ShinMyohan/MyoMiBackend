package com.myomi.admin.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.myomi.notice.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
@DynamicInsert
@DynamicUpdate
public class Admin {
	@Id
	@Column(name = "admin_id", nullable = false)
	private String adminId;

	private String pwd;

	@OneToMany(fetch = FetchType.EAGER,mappedBy = "admin",cascade = CascadeType.ALL)
	private List<Notice> notices;
}
