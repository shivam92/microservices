package com.smartmind.ms.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartmind.ms.account.constants.AccountsConstants;
import com.smartmind.ms.account.dto.AccountsContactInfoDto;
import com.smartmind.ms.account.dto.CustomerDto;
import com.smartmind.ms.account.dto.ResponseDto;
import com.smartmind.ms.account.service.IAccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api")
public class AccountController {
	@Autowired
	IAccountService iAccountService;
@Value("${build.version}")
private String buildVersion;
@Autowired
private Environment environment;
@Autowired
private AccountsContactInfoDto accountsContactInfoDto;
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iAccountService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
			  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	String mobileNumber) {
		CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}
	@PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid  @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
	
	 @DeleteMapping("/delete")
	    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
	    		  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	                                                                String mobileNumber) {
	        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
	        if(isDeleted) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
	        }else{
	            return ResponseEntity
	                    .status(HttpStatus.EXPECTATION_FAILED)
	                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
	        }
	    }

	
	    @GetMapping("/build-info")
	    public ResponseEntity<String> getBuildInfo() {
	        return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(buildVersion);
	    }

	    
	    @GetMapping("/java-version")
	    public ResponseEntity<String> getJavaVersion() {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(environment.getProperty("JAVA_HOME"));
	    }

	    
	    
	    @GetMapping("/contact-info")
	    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(accountsContactInfoDto);
	    }
}
