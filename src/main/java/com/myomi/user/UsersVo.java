package com.myomi.user;

import java.util.Date;

public class UsersVo {
	//상품에 대한 셀러를 찾아 판매자에대한 유저를 찾아?
	//셀러정보를 보고싶을 때, 셀러 기준으로 찾아? 아니면 회원기준으로 찾아?
	
	//판매자 입장에서는 유저브이오 가지고 있어야함.
	private String id;
	private String pwd;
	private String name;
	private String tel;
	private String email;
	private String addr;
	private int role;
	private int membershipNum;
	private Date createdDate;
	private Date signoutDate;
}
