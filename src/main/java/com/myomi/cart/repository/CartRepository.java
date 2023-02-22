package com.myomi.cart.repository;

import com.myomi.cart.entity.Cart;
import com.myomi.cart.entity.CartEmbedded;
import com.myomi.product.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, CartEmbedded> {
//    @EntityGraph(attributePaths = {"user"})
//@Query("select u from Users u join fetch u.")
    public List<Cart> findByUserId(String userId);

//    @Query("SELECT c.user FROM Cart c WHERE c.id.userId = user_id")
    public Optional<Cart> findByUserIdAndProduct(String UserId, Product product);
}
