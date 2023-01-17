package com.myomi.seller;

import com.myomi.user.UsersVo;

public class SellerVo {
	private UsersVo user;
	//회원입장에서 주문한 상품이 있는데 그 주문한 상품의 판매자가 누군지 상호는 누군지...알아야하니
	//관리자입장에서는 상품 주문이 발생했어, 누가 무엇을 어디에서 샀는지! 어디에 대한 정보가 뭔지->판매처->판매처 정보는 그럼 어디서?
	private String companyName;
	//주문기본이 주문상세 여러개//주문상세 입장에서는상품을 가지고있고//상품은 주문자 정보//주문자 정보는 유저에있음!
	
	
}
