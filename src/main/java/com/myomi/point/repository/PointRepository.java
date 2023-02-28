package com.myomi.point.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;

import com.myomi.point.entity.Point;

public interface PointRepository extends CrudRepository<Point, String>{
	public Point findAllById(String username);

}