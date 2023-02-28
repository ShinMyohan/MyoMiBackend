package com.myomi.cart.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.cart.entity.Cart;
import com.myomi.cart.entity.CartEmbedded;

public interface CartRepository extends CrudRepository<Cart, CartEmbedded> {

}

