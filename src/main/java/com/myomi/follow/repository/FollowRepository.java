package com.myomi.follow.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myomi.follow.entity.Follow;
import com.myomi.follow.entity.FollowEmbedded;

public interface FollowRepository extends CrudRepository<Follow, FollowEmbedded> {
	public List<Follow> findByUserId(String user_id);
}