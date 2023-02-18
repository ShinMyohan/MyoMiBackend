package com.myomi.follow.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor  //(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode  //** 키들사이에서 중복되지 않게하기 위해 설정
@Embeddable   //**
public class FollowEmbedded implements Serializable{
//	//@Column(name="line_no")
//		private Long lineNo;
//		
//		//@Column(name = "line_pno" ) //p클래스의 멤버변수 자료형과 같아야함 //아이디 어노테이션이 없기때문에
//		private String pNo;
	
	private String uId;
	private String sId;
}
