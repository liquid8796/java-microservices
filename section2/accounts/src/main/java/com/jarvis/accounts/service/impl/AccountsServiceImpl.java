package com.jarvis.accounts.service.impl;

import com.jarvis.accounts.constants.AccountsConstants;
import com.jarvis.accounts.dto.AccountsDto;
import com.jarvis.accounts.dto.CustomerDto;
import com.jarvis.accounts.entity.Accounts;
import com.jarvis.accounts.entity.Customer;
import com.jarvis.accounts.exception.CustomerAlreadyExistsException;
import com.jarvis.accounts.exception.ResourceNotFoundException;
import com.jarvis.accounts.mapper.AccountsMapper;
import com.jarvis.accounts.mapper.CustomerMapper;
import com.jarvis.accounts.repository.AccountsRepository;
import com.jarvis.accounts.repository.CustomerRepository;
import com.jarvis.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     *
     * @param customer - Customer Object
     * @return the new account details
     */
    public Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     *
     * @param customerDto - CustomerDto Object
     * @return
     */
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

    @Override
    @Transactional
    public void test(String type) {
    }

    private void createArbitraryAccount(Accounts account, boolean isThrow) {
        account.setCustomerId(3L);

        accountsRepository.save(account);

        if(isThrow) throw new RuntimeException("Test exception");
    }

    private void updateArbitraryAccount(long accountNumber, String type, boolean isThrow) {
        Accounts account = accountsRepository.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountType(type);

        accountsRepository.save(account);

        if(isThrow) throw new RuntimeException("Test exception");
    }

    private void readAccount(long accountNumber) {
        Accounts account = accountsRepository.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        System.out.println(String.format("Account type %s", account.getAccountType()));
    }
}
