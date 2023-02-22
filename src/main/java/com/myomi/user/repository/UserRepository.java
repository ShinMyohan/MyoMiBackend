package com.myomi.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myomi.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//JWT 로그인시 사용 - 유저 찾기
	Optional<User> findById(String username);
	//휴대폰 번호 중복 방지를 위해 번호가 존재하는지 확인
	boolean existsUserByTel(String tel);
	
	//User findByTel(String tel);
}
