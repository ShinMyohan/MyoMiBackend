//
//package com.myomi.repository;
//
//<<<<<<< HEAD
//import com.myomi.cart.entity.Cart;
//import com.myomi.cart.repository.CartRepository;
//import com.myomi.product.entity.Product;
//import com.myomi.user.entity.User;
//=======
//>>>>>>> develop
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.myomi.cart.entity.Cart;
//import com.myomi.cart.repository.CartRepository;
//import com.myomi.product.entity.Product;
//import com.myomi.user.entity.User;
//
//@SpringBootTest
//public class CartRepoTest {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private CartRepository cr;
//
//    @Test
//    void testSave() {
//        Cart cart = new Cart();
//        User user = new User();
//        user.setId("id1");
//        cart.setUser(user);
//        Product p = new Product();
//        p.setPNum(1L);
//        cart.setProduct(p);
//        cart.setProdCnt(2);
//
//        cr.save(cart);
//    }
//
//    @Test
//    void testFindAll() {
//        Iterable<Cart> carts = cr.findAll();
//        carts.forEach(cart -> {
//            logger.info("상품번호: " + cart.getProduct().getPNum() + ", 주문자Id: " + cart.getUser().getId() + ", 수량: " + cart.getProdCnt());
//        });
//    }
//}
//
