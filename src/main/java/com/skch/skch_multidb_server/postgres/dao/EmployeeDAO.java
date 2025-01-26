package com.skch.skch_multidb_server.postgres.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skch.skch_multidb_server.postgres.model.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {

}
