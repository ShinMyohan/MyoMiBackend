package com.myomi.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myomi.follow.entity.Follow;
import com.myomi.follow.entity.FollowEmbedded;

public interface FollowRepository extends CrudRepository<Follow, FollowEmbedded> {
	
//	@Query("SELECT user_id,seller_id FROM Follow follow")
//	public List<Follow> FindTest();
	
	

}
