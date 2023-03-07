

package com.myomi.point.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myomi.point.dto.PointDetailDto;
import com.myomi.point.entity.PointDetail;
import com.myomi.point.entity.PointDetailEmbedded;

public interface PointDetailRepository extends JpaRepository<PointDetail, PointDetailEmbedded> {
 
	@Query("select pd from PointDetail pd join pd.point where pd.point.userId.id=:username")
	public List<PointDetail> findAllByUser(@Param("username")String username, Pageable pageable);

	public void save(PointDetailDto pDto);
}