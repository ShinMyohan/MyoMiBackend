package com.myomi.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myomi.order.dto.OrderResponseDto;
import com.myomi.order.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, OrderCustomRepository {
    public List<OrderResponseDto> findAllByUserId(String userId);

    public Optional<Order> findByUserIdAndOrderNum(String userId, Long num);

    //@Query("select o from Order o join o.orderDetail where o.orderDetail.product.prodNum=:prodNum and o.user.id=:userId")
    @Query(value = "select o.* from orders o join orders_detail od on o.num=od.order_num where od.prod_num=:prodNum and o.user_id=:userId", nativeQuery = true)
    public Optional<Order> findByProdNumAndUserId(@Param("prodNum") Long prodNum, @Param("userId") String userId);

    @Query(value = "SELECT o.* From orders o join users u on o.user_id=u.id WHERE num=:orderNum AND user_id=:userId", nativeQuery = true)
    public Optional<Order> findByOrderNumAndUser(@Param(value = "orderNum") Long orderNum, @Param(value = "userId") String userId);

    //select f from follow f join f.sellerId where f.id.uId=:userId
    //select user_id, seller_id from follow where seller_id=:sellerId AND user_id=userId",nativeQuery=true
}
