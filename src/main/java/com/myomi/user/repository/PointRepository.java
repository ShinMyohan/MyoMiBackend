package com.myomi.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.point.entity.Point;

public interface PointRepository extends CrudRepository<Point, String>{

}
