package com.skch.skch_multidb_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
	
	private int statusCode;
	private String successMessage;
	private String errorMessage;
	private Object data;
	
	public Result(Object data) {
		this.data = data;
	}

}
