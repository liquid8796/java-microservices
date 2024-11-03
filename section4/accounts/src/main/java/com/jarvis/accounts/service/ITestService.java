package com.jarvis.accounts.service;

import com.jarvis.accounts.entity.Accounts;

public interface ITestService {
    void createArbitraryAccount(Accounts account, boolean isThrow);
}
