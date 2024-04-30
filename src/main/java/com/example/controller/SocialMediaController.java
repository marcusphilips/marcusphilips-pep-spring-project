package com.example.controller;

import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

import com.example.entity.Account;

import javax.swing.Spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody String jsonAccount) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account registered = mapper.readValue(jsonAccount, Account.class);
        Account account = accountService.addAccount(registered.getUsername(), registered.getPassword());
        
        if (account == null){
            // I don't know the error so . . .
            return ResponseEntity.status(409).body(null);
        }
        else{
            String json = mapper.writeValueAsString(account);
            return ResponseEntity.status(200).body(json);
        }
    }

}
