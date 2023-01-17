package com.myomi.product;

import java.util.List;

import com.myomi.qna.QnaVo;
import com.myomi.seller.SellerVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductVo {
	private int num;
	//private String sellerId; //seller_info 도 참조하는 걸로
	//판매자 입장에서 상품 검색? 상품입장에서 셀러를 검색하는게 많은지
	//개인적으로는 상품입장에서 셀러!
	private SellerVo seller;
	private String category;
	private String name;
	private int originPrice;
	private int percentage;
	private int week;
	private int status;
	private String detail;
	private int reviewCnt;
	private int stars;
	private int fee;
	private List<QnaVo> qnas;
}
