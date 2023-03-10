package com.myomi.order.repository;

import com.myomi.order.dto.OrderListResponseDto;
import com.myomi.order.dto.OrderSumResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.myomi.order.entity.QOrder.order;
import static com.myomi.order.entity.QOrderDetail.orderDetail;
import static com.myomi.product.entity.QProduct.product;
import static com.myomi.review.entity.QReview.review;
import static com.myomi.user.entity.QUser.user;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 마이페이지 본인의 주문 목록 조회
    @Override
    public Map<Long, List<OrderListResponseDto>> findAllByUserId(String userId) {
        Map<Long, List<OrderListResponseDto>> result = jpaQueryFactory
                .selectFrom(order)
                .from(orderDetail)
                .join(order).on(order.eq(orderDetail.order))
                .join(product).on(orderDetail.product.eq(product))
                .leftJoin(review).on(orderDetail.eq(review.orderDetail))
                .where(order.user.id.eq(userId))
                .orderBy(order.orderNum.desc())
                .transform(groupBy(orderDetail.order.orderNum).as(list(Projections.fields(OrderListResponseDto.class,
                        orderDetail.order.orderNum, product.prodNum, product.name.as("pName"), order.totalPrice, order.createdDate, order.payCreatedDate, order.canceledDate, review.reviewNum.as("reviewNum")))));
        return result;
    }

//    @Override
//    public Page<OrderListResponseDto> searchPageComplex(String userId, Pageable pageable) {
//        List<OrderListResponseDto> contents = jpaQueryFactory
//                .select(Projections.fields(OrderListResponseDto.class,
//                        orderDetail.order.orderNum, product.prodNum, product.name.as("pName"), order.totalPrice, order.createdDate, order.payCreatedDate, order.canceledDate, review.reviewNum.as("reviewNum")))
//                .from(orderDetail)
//                .join(order).on(order.eq(orderDetail.order))
//                .join(product).on(orderDetail.product.eq(product))
//                .leftJoin(review).on(orderDetail.eq(review.orderDetail))
//                .where(order.user.id.eq(userId))
//                .orderBy(order.orderNum.desc())
////                .offset(pageable.get)
////                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPQLQuery<BookPaginationDto> pagination = querydsl().applyPagination(pageable, query);
//
//        if(useSearchBtn) { // 검색 버튼 사용시
//            int fixedPageCount = 10 * pageable.getPageSize(); // 10개 페이지 고정
//            return new PageImpl<>(pagination.fetch(), pageable, fixedPageCount);
//        }
//
//        long totalCount = pagination.fetchCount();
//        Pageable pageRequest = exchangePageRequest(pageable, totalCount); // 데이터 건수를 초과한 페이지 버튼 클릭시 보정
//        return new PageImpl<>(querydsl().applyPagination(pageRequest, query).fetch(), pageRequest, totalCount);
//    }







    // 멤버십, 회원 별 3개월간 결제 총합 조회
    @Override
    public List<OrderSumResponseDto> findOrderTotalPrice() {
        List<OrderSumResponseDto> result = jpaQueryFactory
                .select(Projections.constructor(OrderSumResponseDto.class, order.user.id.as("userId"), order.totalPrice.sum().as("totalPrice")))
                .from(order)
                .where(order.canceledDate.isNull(), user.role.eq(0), order.payCreatedDate.after(LocalDateTime.now().minusMonths(3)))
                .groupBy(order.user.id)
                .fetch();
        return result;
    }
}