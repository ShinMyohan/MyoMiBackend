package com.myomi.notice.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.notice.entity.Notice;

public interface NoticeRepository extends CrudRepository<Notice, Long> {

}
