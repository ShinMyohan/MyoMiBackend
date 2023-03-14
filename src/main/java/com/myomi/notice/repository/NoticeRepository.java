package com.myomi.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myomi.notice.entity.Notice;

public interface NoticeRepository extends CrudRepository<Notice, Long> {
	@Query(value="SELECT * FROM notice n ORDER BY n.num desc",nativeQuery = true)
	public List<Notice> findAll();
	public List<Notice> findByTitleContainingOrderByNoticeNumDesc(String keyword);
}
