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
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Adds a message. Assumes it's valid by following these parameters:
     * 1. messageText is not empty nor greater than 255 characters
     * 2. postedBy refers to a real existing account
     * @return the newly minted message 
     */
    public Message postMessage(Message message){
        return messageRepository.save(message);
    }

    /**
     * @return all the messages 
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * Get a message by the unique ID. Returns null if none found.
     * @param id
     * @return
     */
    public Message getMessageByID(long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()){
            return messageOptional.get();
        }
        else{
            return null;
        }
    }

    /**
     * Deletes message by its ID. Does not check if it actually exists.
     */
    public void deleteMessageByID(long id){
        messageRepository.deleteById(id);
    }

    public Message updateMessage(Message message){
        if (getMessageByID(message.getMessageId()) != null){

        }
        else{
            return null;
        }
    }
}
