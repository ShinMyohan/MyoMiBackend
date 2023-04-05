package com.myomi.product.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.myomi.product.dto.ProductReadOneDto;
import com.myomi.product.entity.QProduct;
import com.myomi.qna.dto.QnaReadOneResponseDto;
import com.myomi.qna.entity.QQna;
import com.myomi.review.dto.BestReviewReadResponseDto;
import com.myomi.review.dto.ReviewReadListResponseDto;
import com.myomi.review.entity.QBestReview;
import com.myomi.review.entity.QReview;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author rimsong
 *
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	QProduct p = new QProduct("p");
	QQna q = new QQna("q");
	QReview r = new QReview("r");
	QBestReview br = new QBestReview("br");
	
	@Override
	public List<ProductReadOneDto> findProdInfo(Long prodNum) {
		List<ProductReadOneDto> result = jpaQueryFactory
				.selectFrom(p)
				.leftJoin(q).on(p.prodNum.eq(q.prodNum.prodNum))
				.leftJoin(r).on(p.prodNum.eq(r.orderDetail.id.prodNum))
				.leftJoin(br).on(r.reviewNum.eq(br.reviewNum))
				.where(p.prodNum.eq(prodNum)).fetchAll()
				.distinct()
				.transform(groupBy(p.prodNum).list(Projections.constructor(ProductReadOneDto.class,
						p.prodNum, p.seller.companyName, p.seller.sellerId.id, p.category, p.name,
						p.originPrice, p.percentage, p.week, p.detail, p.productImgUrl, p.status,
						list(Projections.constructor(QnaReadOneResponseDto.class,
								q.qnaNum, q.userId.id, q.queTitle, q.queContent,q.queCreatedDate,
								q.ansContent, q.ansCreatedDate, q.qnaImgUrl).as("qnas")), 
						list(Projections.constructor(ReviewReadListResponseDto.class,
								r.user.id, r.reviewNum, r.title, r.content, r.sort, r.createdDate, r.stars).as("reviews")), 
						list(Projections.constructor(BestReviewReadResponseDto.class,
								br.reviewNum, br.createdDate)))));
		return result;
	}
}
