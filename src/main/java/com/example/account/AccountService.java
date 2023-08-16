package com.example.account;

import java.sql.SQLDataException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountInterfaceService {
    
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccountService(String username, String password) throws SQLDataException {
        int result = accountRepository.registerAccount(username,password);

        if (result == 1) {
            return accountRepository.login(username, password);
        } else {
            return new Account();
        }
    }

    public Account validateAccount(String username, String password) throws SQLDataException {
        return accountRepository.login(username, password);
    }

    public Boolean validateAccount(int accountId) throws SQLDataException {
        if (accountRepository.validAccount(accountId).getAccount_id()==0) {
            return false;
        } else {
            return true;
        }
    }
}
