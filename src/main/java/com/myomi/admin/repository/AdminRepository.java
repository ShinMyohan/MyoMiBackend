package com.myomi.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.myomi.admin.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, String> {

}