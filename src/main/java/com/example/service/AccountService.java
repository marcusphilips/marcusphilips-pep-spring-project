package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Adds account. Assumes it's formatted correctly.
     * @param account
     * @return the newly created account
     */
    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    /**
     * Login with a given username and password. Returns the account with its correct userID if correct and null if the password is wrong or the 
     * account does not exist
     * @param account
     * @return
     */
    public Account login(Account account){
        // have to manually thumb through all accounts to find a matching username
        List<Account> allAccounts = accountRepository.findAll();
        for (Account e : allAccounts){
            if (e.getUsername().equals(account.getUsername())){
                if (e.getPassword().equals(account.getPassword())){
                    return e;
                }
                else{
                    // wrong password
                    return null;
                }
            }
        }
        // username did not exist in repository
        return null;
    }



}
