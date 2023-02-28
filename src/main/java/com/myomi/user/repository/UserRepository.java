package com.myomi.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myomi.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findById(String username);
}
