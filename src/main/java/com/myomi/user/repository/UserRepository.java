package com.myomi.user.repository;

import com.myomi.membership.entity.MembershipLevel;
import com.myomi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


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

    // 전체 멤버십 초기화
    @Modifying
    @Query("Update User u set u.membership = :membership")
    public void updateAllMembership(@Param("membership") MembershipLevel membershipLevel);

    // 멤버십 등급 update
    @Modifying
    @Query("UPDATE User u SET u.membership = :membership WHERE u.id = :userId")
    public void updateMembershipByUserId(@Param("userId") String UserId, @Param("membership") MembershipLevel membershipLevel);

}
