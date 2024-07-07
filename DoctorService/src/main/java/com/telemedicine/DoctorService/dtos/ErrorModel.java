package com.telemedicine.DoctorService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {


	private String errorUrl;
	private String errorMessage;
}
