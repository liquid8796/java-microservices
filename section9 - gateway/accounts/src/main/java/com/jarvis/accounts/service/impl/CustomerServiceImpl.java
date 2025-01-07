package com.jarvis.accounts.service.impl;

import com.jarvis.accounts.dto.AccountsDto;
import com.jarvis.accounts.dto.CardsDto;
import com.jarvis.accounts.dto.CustomerDetailsDto;
import com.jarvis.accounts.dto.LoansDto;
import com.jarvis.accounts.entity.Accounts;
import com.jarvis.accounts.entity.Customer;
import com.jarvis.accounts.exception.ResourceNotFoundException;
import com.jarvis.accounts.mapper.AccountsMapper;
import com.jarvis.accounts.mapper.CustomerMapper;
import com.jarvis.accounts.repository.AccountsRepository;
import com.jarvis.accounts.repository.CustomerRepository;
import com.jarvis.accounts.service.ICustomerService;
import com.jarvis.accounts.service.client.CardsFeignClient;
import com.jarvis.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
