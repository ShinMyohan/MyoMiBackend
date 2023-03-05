package com.myomi.order.repository;

import com.myomi.order.dto.OrderListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.myomi.order.entity.QOrder.order;
import static com.myomi.order.entity.QOrderDetail.orderDetail;
import static com.myomi.product.entity.QProduct.product;
import static com.myomi.review.entity.QReview.review;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    /* select od.order_num, p.name, o.total_price, o.canceled_date, r.num from orders_detail od
    inner join orders o on o.num = od.order_num
    inner join product p on od.prod_num = p.num
    left join review r on od.order_num = r.order_num and od.prod_num = r.prod_num where o.user_id='id1'; */

    // QueryDsl
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
}