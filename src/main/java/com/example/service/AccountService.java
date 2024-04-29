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
     * Adds account. Returns null if not possible otherwise returns the newly minted account
     * The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
     * and an Account with that username does not already exist.
     *  If all these conditions are met, the response body should contain a JSON of the Account, 
     * including its accountId. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
     * @param username
     * @param password
     * @return the newly created account
     */
    public Account addAccount(String username, String password){
        if (username.length() != 0 && password.length() >= 4){
            // does the account's username already exist?
            if (accountRepository.findAccountByUsername(username) != null)
                return accountRepository.save(new Account(username, password));
        }
        else
        {
            return null;
        }
    }

    /**
     * Login with a given username and password. Returns the account with its correct userID if correct and null if the password is wrong or the 
     * account does not exist
     * @param account
     * @return
     */
    public Account login(String username, String password){
        // have to manually thumb through all accounts to find a matching username
        // // List<Account> allAccounts = accountRepository.findAll();
        // // for (Account e : allAccounts){
        // //     if (e.getUsername().equals(account.getUsername())){
        // //         if (e.getPassword().equals(account.getPassword())){
        // //             return e;
        // //         }
        // //         else{
        // //             // wrong password
        // //             return null;
        // //         }
        // //     }
        // // }
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null){
            if (account.getPassword().equals(password)){
                return account;
            }
        }
        // username did not exist in repository
        return null;
    }



}
