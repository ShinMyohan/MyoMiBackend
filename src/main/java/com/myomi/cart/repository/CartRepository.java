package com.myomi.cart.repository;

import com.myomi.cart.entity.Cart;
import com.myomi.cart.entity.CartEmbedded;
import com.myomi.product.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, CartEmbedded> {
//    @EntityGraph(attributePaths = {"user"})
    public List<Cart> findListByUserId(String userId);

    public Optional<Cart> findByUserIdAndProduct(String UserId, Product product);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id.userId = :userId AND c.product.pNum = :pNum")
    public void deleteCartByUserIdAndProduct(@Param("userId")String UserId, @Param("pNum") Long pNum);

}
