package com.skch.skch_multidb_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skch.skch_multidb_server.dto.EmployeeDTO;
import com.skch.skch_multidb_server.dto.Result;
import com.skch.skch_multidb_server.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/get-emploees/{empId}")
	@Operation(summary="get Employes",description = "Return the Employees based on Id")
	public ResponseEntity<?> getEmp(@PathVariable("empId") Long empId){
		Result result = employeeService.findById(empId);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/test-post")
	@Operation(summary="save Employee",description = "Save the Emp")
	public ResponseEntity<?> getNav(@RequestBody EmployeeDTO employeeDTO){
		return ResponseEntity.ok("Access :: ");
	}

}
