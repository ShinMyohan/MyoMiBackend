package com.myomi.user.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myomi.board.vo.BoardVo;
import com.myomi.cart.vo.CartVo;
import com.myomi.comment.vo.CommentVo;
import com.myomi.coupon.vo.CouponVo;
import com.myomi.follow.vo.FollowVo;
import com.myomi.membership.vo.MembershipVo;
import com.myomi.order.vo.OrderVo;
import com.myomi.point.vo.PointVo;
import com.myomi.qna.vo.QnaVo;
import com.myomi.review.vo.ReviewVo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserVo {
	private String id;
	private String pwd;
	private String name;
	private String tel;
	private String email;
	private String addr;
	private int role;
	private MembershipVo membership;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date createdDate;
	@JsonFormat(timezone = "Asia/Seoul", pattern = "yy-MM-dd")
	private Date signoutDate;
	private List<CartVo> cart;
	private List<OrderVo> order;
	private List<BoardVo> board;
	private List<CommentVo> comment;
	private PointVo point;
	//private List<PointDetailVo> pointDetail;
	private List<QnaVo> qna;
	private List<FollowVo> follow; 
	private List<ReviewVo> review;
	private List<CouponVo> coupon;
}

