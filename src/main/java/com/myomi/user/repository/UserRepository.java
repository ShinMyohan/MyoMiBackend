package com.myomi.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myomi.user.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	//JWT 로그인시 사용 - 유저 찾기
//	@EntityGraph(attributePaths = "user")
	Optional<User> findById(String username); //id
	//휴대폰 번호 중복 방지를 위해 번호가 존재하는지 확인
	boolean existsUserByTel(String tel);
	//등록된 아이디인지 중복체크
	boolean existsUserById(String id);
	//이메일로 유저 찾기
	Optional<User> findByEmail(String email);
	//OAuth 시 필요
	User findByIdAndPwd(String email, String password);

}
