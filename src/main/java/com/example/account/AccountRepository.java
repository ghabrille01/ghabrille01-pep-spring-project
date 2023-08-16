package com.example.account;

import java.sql.SQLDataException;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO account(username, password) VALUES (:username, :password)", nativeQuery = true)
    public int registerAccount(@Param("username") String username, @Param("password") String password) throws SQLDataException;

    @Query(value = "SELECT * FROM account WHERE username = :username AND password = :password", nativeQuery = true)
    public Account login(@Param("username") String username, @Param("password") String password) throws SQLDataException;

    @Query(value = "SELECT * FROM account WHERE account_id = :account_id", nativeQuery = true)
    public Account validAccount(@Param("account_id") int accountId) throws SQLDataException;
}
