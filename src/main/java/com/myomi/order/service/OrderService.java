package com.myomi.order.service;

import com.myomi.order.dto.DeliveryDto;
import com.myomi.order.dto.OrderDto;
import com.myomi.order.entity.Delivery;
import com.myomi.order.entity.Order;
import com.myomi.order.entity.OrderDetail;
import com.myomi.order.repository.OrderRepository;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import com.myomi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /* TODO: 1. 주문서 작성 (배송, 상세)
             2. 장바구니에서 가져옴
             3. 배송정보 입력
             4. 상세정보 입력
             5. 결제 API
             6. 결제시 포인트 차감/적립/쿠폰적용
             7. 내 주문 목록 조회
             8. 주문 상세 조회
             9. 배송일 전에 주문 취소
    */

    // 주문서 작성
    public void addOrder(Authentication user, OrderDto orderDto) {
        User u = userRepository.findById(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 사용자만 이용 가능합니다."));

        Order order = orderDto.toEntity(u, orderDto);
//        orderDto.getOrder().registerDelivery(orderDto.getDelivery());
//        Order or = orderDto.getOrder();
//        orderDto.getDelivery().getOrder().registerDelivery(deli);
        orderDto.getDelivery().registerOrder(order); // orderDto.getOrder()은 안되고 order entity를 넣으니까 됨..

        int size = orderDto.getOrderDetail().size() - 1;
        for (int i = 0; i <= size; i++) {
            orderDto.getOrderDetail().get(i).registerOrder(order);
//            Product p = orderDto.getOrderDetail().get(i).getProduct();
//            orderDto.getOrderDetail().get(i).registerOrder(p);
        }

        List<OrderDetail> orderDetailList = orderDto.getOrderDetail();

        for (int i = 0; i <= size; i++) {
            Product prod = Product.builder().pNum(orderDetailList.get(i).getProduct().getPNum()).build();
            OrderDetail od2 = OrderDetail.builder()
//                    .order(orderDetail.getOrder())
                    .product(prod)
                    .prodCnt(orderDetailList.get(i).getProdCnt())
                    .build();
        }

        DeliveryDto delDto = new DeliveryDto();
        Delivery delivery = delDto.createDelivery(orderDto);

//        System.out.println("order : " + order.toString());
//        System.out.println("orderdetail : " + orderDetailList.get(0).toString());

//        order.addOrderDetails(odList);
//        order.addDelivery(delivery);

        orderRepository.save(order);
    }


    public List<OrderDto> findOrderListByUserId(Authentication user) {
        return orderRepository.findAllByUserId(user.getName());
    }


}
