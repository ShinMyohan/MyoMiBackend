package com.myomi.cart.repository;

import com.myomi.cart.entity.Cart;
import com.myomi.cart.entity.CartEmbedded;
import com.myomi.product.entity.Product;
import com.myomi.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, CartEmbedded> {
    @EntityGraph(attributePaths = {"user", "product"}) // N+1 문제
//    @Query("select c from Cart c join fetch c.user join fetch c.product")
    public List<Cart> findByUserId(String userId);

    public Optional<Cart> findByUserAndProduct(User user, Product product);

    @Modifying
    @Query("UPDATE Cart c SET c.prodCnt = c.prodCnt + :prodCnt WHERE c.id.userId = :userId AND c.product.prodNum = :pNum")
    public void updateCart(@Param("userId")String UserId, @Param("pNum") Long pNum, @Param("prodCnt") int prodCnt);


    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id.userId = :userId AND c.product.prodNum = :pNum")
    public void deleteCartByUserIdAndProduct(@Param("userId")String UserId, @Param("pNum") Long pNum);

}
