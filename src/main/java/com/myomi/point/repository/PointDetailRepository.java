package com.myomi.point.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.point.entity.PointDetail;
import com.myomi.point.entity.PointDetailEmbedded;

public interface PointDetailRepository extends CrudRepository<PointDetail, PointDetailEmbedded> {

}
