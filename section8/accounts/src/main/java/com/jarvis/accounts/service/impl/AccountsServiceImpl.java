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
import com.jarvis.accounts.service.ITestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ITestService iTestService;

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
        Accounts acc1 = new Accounts();
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        acc1.setAccountNumber(randomAccNumber);
        acc1.setAccountType(AccountsConstants.SAVINGS);
        acc1.setBranchAddress("HCM");
        iTestService.createArbitraryAccount(acc1, false);

        Accounts acc2 = new Accounts();
        randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        acc2.setAccountNumber(randomAccNumber);
        acc2.setAccountType("Checkings");
        acc2.setBranchAddress("Binh Duong");
        iTestService.createArbitraryAccount(acc2, false);

        Accounts acc3 = new Accounts();
        randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        acc3.setAccountNumber(randomAccNumber);
        acc3.setAccountType("Checkings");
        acc3.setBranchAddress("Ha Noi");
        iTestService.createArbitraryAccount(acc3, false);
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