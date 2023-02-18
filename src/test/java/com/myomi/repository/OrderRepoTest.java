//package com.myomi.repository;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.order.entity.Delivery;
//import com.myomi.order.entity.Order;
//import com.myomi.order.entity.OrderDetail;
//import com.myomi.product.entity.Product;
//import com.myomi.review.repository.OrderRepository;
//import com.myomi.user.entity.User;
//
//@SpringBootTest
//public class OrderRepoTest {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private OrderRepository or;
//
//    @Test // 주문 추가
//    @DisplayName("주문 추가")
//    void testSave() {
//        // 주문 정보 추가
//        Order o = new Order();
//        o.setONum(1L);
//
//        User u = new User();
//        u.setId("id1");
//        o.setUser(u);
//
//        Date date = new Date();
//        o.setCreatedDate(date);
//
//        // 주문 상세 추가
//        List<OrderDetail> list = new ArrayList<>();
//        for(long i=1; i<=2L; i++) {
//            OrderDetail detail = new OrderDetail();
//            detail.setOrder(o);
//            Product p = new Product();
//            p.setNum(i);
//            detail.setProduct(p);
//            detail.setProdCnt(3L);
//            logger.error("상세 hashCode=" + detail.hashCode());
//            list.add(detail);
//        }
//
//        // 배송 정보 추가
//        Delivery d = new Delivery();
//        d.setOrder(o);
//        d.setName("장");
//        d.setTel("010-0000-0000");
//        d.setAddr("서울");
//        d.setReceiveDate("월요일");
//
//        o.setOrderDetail(list);
//        o.setDelivery(d);
//        or.save(o);
//    }
//
////
////    @Test
////    void testFindAll() {
////        Iterable<Order> o = or.findAll();
////        o.forEach(order -> {
////            logger.info("주문번호 : " + order.getONum() + "" + order.getOrderDetail().get);
////        });
////    }
//}
