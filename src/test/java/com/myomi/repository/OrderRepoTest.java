//
//package com.myomi.repository;
//
//<<<<<<< HEAD
//import com.myomi.order.entity.Order;
//import com.myomi.order.repository.OrderRepository;
//=======
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//>>>>>>> develop
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//<<<<<<< HEAD
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//=======
//import com.myomi.order.entity.Delivery;
//import com.myomi.order.entity.Order;
//import com.myomi.order.entity.OrderDetail;
//import com.myomi.order.repository.OrderRepository;
//import com.myomi.product.entity.Product;
//import com.myomi.user.entity.User;
//import com.myomi.user.repository.UserRepository;
//>>>>>>> develop
//
//@SpringBootTest
//public class OrderRepoTest {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private OrderRepository or;
//    
//    @Autowired
//    private UserRepository ur;
//
//<<<<<<< HEAD
////    @Test // 주문 추가
////    @DisplayName("주문 추가")
////    void testSave() {
////        // 주문 정보 추가
////        Order o = new Order();
////        o.setONum(4L);
////
////        User u = new User();
////        u.setId("id7");
////        o.setUser(u);
////
////        Date date = new Date();
////        o.setCreatedDate(date);
////
////        // 주문 상세 추가
////        List<OrderDetail> list = new ArrayList<>();
////        for (long i = 1; i <= 2L; i++) {
////            OrderDetail detail = new OrderDetail();
////            detail.setOrder(o);
////            Product p = new Product();
////            p.setNum(i);
////            detail.setProduct(p);
////            detail.setProdCnt(3L);
////            logger.error("상세 hashCode=" + detail.hashCode());
////            list.add(detail);
////        }
////
////        // 배송 정보 추가
////        Delivery d = new Delivery();
////        d.setOrder(o);
////        d.setName("장");
////        d.setTel("010-0000-0000");
////        d.setAddr("서울");
////        d.setReceiveDate("월요일");
////
////        o.setOrderDetail(list);
////        o.setDelivery(d);
////        or.save(o);
////    }
//
//
////    @Test
////    @DisplayName("주문, 상세, 배송정보 찾기")
////    void testFindAll() {
////        Iterable<Order> o = or.findAll();
////        o.forEach(order -> {
////            logger.info("주문 번호 : " + order.getONum()
////                    + ", 상품 번호 : " + order.getOrderDetail().get(1).getProduct().getpNum()
////                    + ", 배송 정보: " + order.getDelivery().getName());
////        });
////    }
//
////    @Test
////    @DisplayName("id로 본인의 주문내역 모두 찾기")
////    void testFindAllByUserId() {
////        Iterable<Order> o = or.findAllByUserId("id6");
////        o.forEach(order -> {
////            logger.info("주문번호 : " + order.getONum());
////            List<OrderDetail> od = order.getOrderDetail();
////            od.forEach(detail -> {
////                logger.info("상품번호 : " + detail.getProduct().getpNum()
////                        + ", 상품수량" + detail.getProdCnt());
////            });
////
////        });
////    }
//=======

package com.myomi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.myomi.order.entity.Order;
import com.myomi.order.repository.OrderRepository;
import com.myomi.user.repository.UserRepository;

@SpringBootTest
public class OrderRepoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderRepository or;
    
    @Autowired
    private UserRepository ur;

//    @Test // 주문 추가
//    @DisplayName("주문 추가")
//    void testSave() {
//        // 주문 정보 추가
//        Order o = new Order();
//        o.setONum(4L);
//
////        User u = new User();
////        u.setId("id7");
//        Optional<User> u = ur.findById("id2");
//        o.setUser(u.get());
//
//        Date date = new Date();
//        o.setCreatedDate(date);
//
//        // 주문 상세 추가
//        List<OrderDetail> list = new ArrayList<>();
//        for (long i = 1; i <= 2L; i++) {
//            OrderDetail detail = new OrderDetail();
//            detail.setOrder(o);
//            Product p = new Product();
//            p.setPNum(1L);
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
//
//    @Test
//    @DisplayName("주문, 상세, 배송정보 찾기")
//    void testFindAll() {
//        Iterable<Order> o = or.findAll();
//        o.forEach(order -> {
//            logger.info("주문 번호 : " + order.getONum()
//                    + ", 상품 번호 : " + order.getOrderDetail().get(1).getProduct().getPNum()
//                    + ", 배송 정보: " + order.getDelivery().getName());
//        });
//    }
//
//    @Test
//    @DisplayName("id로 본인의 주문내역 모두 찾기")
//    void testFindAllByUserId() {
//        Iterable<Order> o = or.findAllByUserId("id6");
//        o.forEach(order -> {
//            logger.info("주문번호 : " + order.getONum());
//            List<OrderDetail> od = order.getOrderDetail();
//            od.forEach(detail -> {
//                logger.info("상품번호 : " + detail.getProduct().getPNum()
//                        + ", 상품수량" + detail.getProdCnt());
//            });
//
//        });
//    }
//
//
//    @Test
//    @Transactional
//    void testDeleteByNum() {
//        Optional<Order> o = or.findById(1L); //optA라는 엔티티객체와 매핑
//        assertTrue(o.isPresent()); // 지우기전에 진짜 있는지 확인하기
//        String userId = o.get().getUser().getId();
//        assertEquals("id6", userId);
//        Order order = o.get();
//        or.delete(order);
//    }
//
//}
//

    @Test
    @Transactional
    void testDeleteByNum() {
        Optional<Order> o = or.findById(1L); //optA라는 엔티티객체와 매핑
        assertTrue(o.isPresent()); // 지우기전에 진짜 있는지 확인하기
        String userId = o.get().getUser().getId();
        assertEquals("id6", userId);
        Order order = o.get();
        or.delete(order);
    }

}

