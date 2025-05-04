package com.smartmind.ms.account.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartmind.ms.account.controller.CustomerController;
import com.smartmind.ms.account.dto.AccountsDto;
import com.smartmind.ms.account.dto.CardsDto;
import com.smartmind.ms.account.dto.CustomerDetailsDto;
import com.smartmind.ms.account.dto.LoansDto;
import com.smartmind.ms.account.entity.Accounts;
import com.smartmind.ms.account.entity.Customer;
import com.smartmind.ms.account.exception.ResourceNotFoundException;
import com.smartmind.ms.account.mapper.AccountsMapper;
import com.smartmind.ms.account.mapper.CustomerMapper;
import com.smartmind.ms.account.repo.AccountsRepository;
import com.smartmind.ms.account.repo.CustomerRepository;
import com.smartmind.ms.account.service.ICustomersService;
import com.smartmind.ms.account.service.client.CardsFiegnClient;
import com.smartmind.ms.account.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {
 @Autowired
    private AccountsRepository accountsRepository;
 @Autowired
    private CustomerRepository customerRepository;
 @Autowired
    private CardsFiegnClient cardsFeignClient;
 @Autowired
    private LoansFeignClient loansFeignClient;
 private static final Logger logger = LoggerFactory.getLogger(CustomersServiceImpl.class);
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
    	logger.debug("fetchCustomerDetails custom uuid found: {} ", correlationId);
    	Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}