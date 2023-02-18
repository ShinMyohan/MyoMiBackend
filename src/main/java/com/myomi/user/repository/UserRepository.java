package com.myomi.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.user.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}
