package com.smartmind.ms.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartmind.ms.account.dto.CardsDto;

@FeignClient("cards")
public interface CardsFiegnClient {
	 @GetMapping("/api/fetch")
	    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("custom-uuid")String correlationId,@RequestParam String mobileNumber) ;
}
