
package com.smartmind.ms.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {

     private String name;

    private String email;

       private String mobileNumber;

   
    private AccountsDto accountsDto;

    
    private LoansDto loansDto;

    
    private CardsDto cardsDto;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public AccountsDto getAccountsDto() {
		return accountsDto;
	}


	public void setAccountsDto(AccountsDto accountsDto) {
		this.accountsDto = accountsDto;
	}


	public LoansDto getLoansDto() {
		return loansDto;
	}


	public void setLoansDto(LoansDto loansDto) {
		this.loansDto = loansDto;
	}


	public CardsDto getCardsDto() {
		return cardsDto;
	}


	public void setCardsDto(CardsDto cardsDto) {
		this.cardsDto = cardsDto;
	}


}
