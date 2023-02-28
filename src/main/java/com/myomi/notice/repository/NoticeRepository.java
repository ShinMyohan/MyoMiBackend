package com.myomi.notice.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.myomi.notice.entity.Notice;

public interface NoticeRepository extends CrudRepository<Notice, Long> {
	public List<Notice> findAll(Pageable pageable);
	public List<Notice> findByTitleContaining(String keyword,Pageable pageable);
}
