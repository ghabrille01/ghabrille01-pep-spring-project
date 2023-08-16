package com.example.account;

import java.sql.SQLDataException;

public interface AccountInterfaceService {
    
    public Account addAccountService(String username, String password) throws SQLDataException;

    public Account validateAccount(String username, String password) throws SQLDataException;

    public Boolean validateAccount(int accountId) throws SQLDataException;
}
