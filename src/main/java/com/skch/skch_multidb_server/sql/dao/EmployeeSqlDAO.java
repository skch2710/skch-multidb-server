package com.skch.skch_multidb_server.sql.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skch.skch_multidb_server.sql.model.EmployeeSql;

public interface EmployeeSqlDAO extends JpaRepository<EmployeeSql, Long> {

}
