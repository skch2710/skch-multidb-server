package com.skch.skch_multidb_server.service;

import com.skch.skch_multidb_server.dto.Result;

public interface EmployeeService {
	
	Result findById(Long empId);

}
