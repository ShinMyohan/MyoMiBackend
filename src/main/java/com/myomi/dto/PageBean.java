package com.myomi.dto;

import java.util.List;

import lombok.Getter;
@Getter //값을 설정할 수는 없고 조회만 가능하다.
public class PageBean<T> { //게시판 공지사항에서만 쓰인다면! -> 14번째줄 -> <T>로 하면
	//PageBean<Product> pb = new PageBean<>(); or PageBean<Board> pb2 = new PageBean<>();
	public final static int CNT_PER_PAGE=3;
	public final static int CNT_PER_PAGE_GROUP=2;
	private List<T> list; //<제네릭만 바뀌어진다.>따라서 10번째줄
	private int totalCnt;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int currentPage;
	public PageBean(int currentPage, List<T>list, int totalCnt) {
		this.currentPage =  currentPage;
		this.list = list;
		this.totalCnt = totalCnt;
		
		totalPage = (int) Math.ceil((double)totalCnt/CNT_PER_PAGE);
		startPage = (currentPage-1)/CNT_PER_PAGE_GROUP*CNT_PER_PAGE_GROUP+1;
		endPage = startPage + CNT_PER_PAGE_GROUP -1;
	}
}
