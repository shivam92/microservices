package com.smartmind.ms.account.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartmind.ms.account.constants.AccountsConstants;
import com.smartmind.ms.account.dto.AccountsDto;
import com.smartmind.ms.account.dto.CustomerDto;
import com.smartmind.ms.account.entity.Accounts;
import com.smartmind.ms.account.entity.Customer;
import com.smartmind.ms.account.exception.ResourceNotFoundException;
import com.smartmind.ms.account.mapper.AccountsMapper;
import com.smartmind.ms.account.mapper.CustomerMapper;
import com.smartmind.ms.account.repo.AccountsRepository;
import com.smartmind.ms.account.repo.CustomerRepository;
import com.smartmind.ms.account.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService{

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Override
	public void createAccount(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		Customer customer=CustomerMapper.mapToCustomer(customerDto, new Customer());
		customer.setCreatedAt(LocalDateTime.now());
		customer.setCreatedBy("system");
		Customer saveCustomer=	customerRepository.save(customer);
	accountsRepository.save(createAccount(saveCustomer));
	}
	private Accounts createAccount(Customer saveCustomer) {
		
		Accounts newAccount=new Accounts();
		newAccount.setCustomerId(saveCustomer.getCustomerId());
		long newRandomAcc=10000000l+new Random().nextInt(9000000);
		newAccount.setAccountNumber(newRandomAcc);
		newAccount.setCreatedAt(LocalDateTime.now());
		newAccount.setCreatedBy("system");
		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
		return newAccount;
	}
	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		 Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
	        );
	        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
	                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
	        );
	        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
	        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
	        return customerDto;
	        
	}
	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		 boolean isUpdated = false;
	        AccountsDto accountsDto = customerDto.getAccountsDto();
	        if(accountsDto !=null ){
	            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
	                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
	            );
	            AccountsMapper.mapToAccounts(accountsDto, accounts);
	            accounts = accountsRepository.save(accounts);

	            Long customerId = accounts.getCustomerId();
	            Customer customer = customerRepository.findById(customerId).orElseThrow(
	                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
	            );
	            CustomerMapper.mapToCustomer(customerDto,customer);
	            customerRepository.save(customer);
	            isUpdated = true;
	        }
	        return  isUpdated;
	}
	@Override
	public boolean deleteAccount(String mobileNumber) {
		 Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
	        );
	        accountsRepository.deleteByCustomerId(customer.getCustomerId());
	        customerRepository.deleteById(customer.getCustomerId());
	        return true;
	}

}
