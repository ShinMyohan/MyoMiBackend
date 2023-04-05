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
                .selectFrom(orderDetail)
                .join(order).on(order.eq(orderDetail.order))
                .join(product).on(orderDetail.product.eq(product))
                .leftJoin(review).on(orderDetail.eq(review.orderDetail))
                .where(order.user.id.eq(userId))
                .orderBy(orderDetail.order.orderNum.desc())
                .transform(groupBy(orderDetail.order.orderNum).as(list(Projections.fields(OrderListResponseDto.class,
                        orderDetail.order.orderNum, product.prodNum, product.name.as("pName"), order.totalPrice, order.createdDate, order.payCreatedDate, order.canceledDate, review.reviewNum.as("reviewNum")))));
        return result;
    }


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