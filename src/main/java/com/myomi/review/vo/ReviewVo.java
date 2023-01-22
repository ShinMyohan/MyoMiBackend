package com.myomi.review.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.order.vo.OrderVo;
import com.myomi.product.vo.ProductVo;
import com.myomi.user.vo.UserVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor

@ToString
public class ReviewVo {
	private int num;
	private OrderVo order;
	private UserVo user;
	private int sort;
	private String title;
	private String content;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
	private int stars;
	
	public ReviewVo(int num, OrderVo order, UserVo user, int sort, String title, String content, Date createdDate,
			int stars) {
		super();
		this.num = num;
		this.order = order;
		this.user = user;
		this.sort = sort;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.stars = stars;
	}
	
	
}
