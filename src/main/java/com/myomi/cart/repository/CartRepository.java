package com.myomi.cart.repository;

import com.myomi.cart.entity.Cart;
import com.myomi.user.User;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, User> {
}
