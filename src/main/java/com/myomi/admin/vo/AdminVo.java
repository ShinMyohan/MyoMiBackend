package com.myomi.admin.vo;

import java.util.List;

import com.myomi.notice.vo.NoticeVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminVo {
	private String adminId;
	private String pwd;
	private List<NoticeVo> notice;
}
