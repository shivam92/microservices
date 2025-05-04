package com.smartmind.ms.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartmind.ms.account.dto.LoansDto;
@FeignClient("loans")
public interface LoansFeignClient {

	 @GetMapping("/api/fetch")
	    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("custom-uuid")String correlationId,@RequestParam String mobileNumber );
	                                                              
	                                                               
}
