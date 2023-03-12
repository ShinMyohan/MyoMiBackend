package com.myomi.follow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.follow.entity.Follow;
import com.myomi.follow.entity.FollowEmbedded;

public interface FollowRepository extends CrudRepository<Follow, FollowEmbedded> {
	
	//팔로우 확인여부
	@Query(value = "SELECT user_id, seller_id FROM follow WHERE seller_id = :sellerId AND user_id = :userId", nativeQuery = true)
	public Optional<Follow> findByUserIdAndSellerId(@Param(value = "userId")String userId, @Param(value = "sellerId")String sellerId);

	//언팔로우 
	@Modifying
	@Query(value = "DELETE FROM follow WHERE seller_id = :sellerId AND user_id = :userId" , nativeQuery = true)
	public void deleteFollowByUserIdAndSellerId(@Param(value = "userId")String userId, @Param(value = "sellerId") String sellerId);

	//팔로우 목록 조회
	@Query("SELECT f FROM Follow f join f.sellerId WHERE f.id.uId =:userId")
	public List<Follow> findAllByUserId(@Param("userId")String user,Pageable pageable);
	
	//내 팔로우 수 조회
	@Query(value = "SELECT count(*) FROM follow WHERE user_id=:userId",nativeQuery=true)
	public Long findAllFollowByUserId(@Param("userId")String user);
	
}
