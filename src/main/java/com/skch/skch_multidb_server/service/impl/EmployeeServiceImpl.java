package com.skch.skch_multidb_server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skch.skch_multidb_server.dto.EmployeeDTO;
import com.skch.skch_multidb_server.dto.Result;
import com.skch.skch_multidb_server.postgres.dao.EmployeeDAO;
import com.skch.skch_multidb_server.postgres.model.Employee;
import com.skch.skch_multidb_server.service.EmployeeService;
import com.skch.skch_multidb_server.sql.dao.EmployeeSqlDAO;
import com.skch.skch_multidb_server.sql.model.EmployeeSql;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private EmployeeSqlDAO employeeSqlDAO;

	@Override
	public Result findById(Long empId) {
		
		Optional<Employee> emp = employeeDAO.findById(empId);
		
		Optional<EmployeeSql> empSql = employeeSqlDAO.findById(empId);
		
		List<EmployeeDTO> empList = new ArrayList<>();
		
		if(emp.isPresent()) {
			EmployeeDTO pgEmp = new EmployeeDTO();
			BeanUtils.copyProperties(emp.get(), pgEmp);
			empList.add(pgEmp);
		}
		
		if(empSql.isPresent()) {
			EmployeeDTO sqlEmp = new EmployeeDTO();
			BeanUtils.copyProperties(empSql.get(), sqlEmp);
			empList.add(sqlEmp);
		}
		
		Result result = new Result(empList);
		
		return result;
	}
	

}
