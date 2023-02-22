package com.myomi.admin.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

//import com.myomi.chat.entity.ChatRoom;
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
	@Column(name = "admin_id")
	private String adminId;
	
	@NotNull
	private String pwd;

	@OneToMany(mappedBy = "admin")
	private List<Notice> notices;
	
//	@OneToMany(mappedBy = "admin")
//	private List<ChatRoom> rooms;
}
