package com.myomi.qna.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PageBean<T> {
	//static : 이 키워드를 사용한 변수는 객체생성없이 사용가능 
	public final static int CNT_PER_PAGE = 10; //상수 선언  //상수 : 변하지 않는 값 
	public final static int CNT_PER_PAGE_GROUP = 2;

	private List<T> list;  //타입제네릭: T는 타입의 약자 

	private int totalCnt;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int currentPage;

	public PageBean(int CurrentPage, List<T>list, int totalCnt) {
		this.currentPage = currentPage;
		this.list =list;
		this.totalCnt = totalCnt;
		totalPage = (int)Math.ceil((double)totalCnt/CNT_PER_PAGE);
		startPage = (currentPage-1)/CNT_PER_PAGE*CNT_PER_PAGE_GROUP +1;
		endPage = startPage + CNT_PER_PAGE_GROUP -1 ;
	}

}