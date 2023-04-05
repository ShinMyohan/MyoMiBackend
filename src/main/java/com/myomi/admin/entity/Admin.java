package com.myomi.admin.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.myomi.notice.entity.Notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "admin")

public class Admin {
	@Id
	@Column(name = "admin_id")
	private String adminId;
	
	@NotNull
	private String pwd;

	@OneToMany(mappedBy = "admin")
	private List<Notice> notices;
	
	@Builder
	public Admin(String adminId, String pwd) {
		this.adminId = adminId;
		this.pwd = pwd;
	}

//	@OneToMany(mappedBy = "admin")
//	private List<ChatRoom> rooms;
	
}
