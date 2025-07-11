package com.jarvis.accounts.service.impl;

import com.jarvis.accounts.entity.Accounts;
import com.jarvis.accounts.repository.AccountsRepository;
import com.jarvis.accounts.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements ITestService {
    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createArbitraryAccount(Accounts account, boolean isThrow) {
        account.setCustomerId(2L);

        accountsRepository.save(account);

        if(isThrow) throw new RuntimeException("Test exception");
    }
}
