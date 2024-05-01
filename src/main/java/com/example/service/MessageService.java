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
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * Adds a message. Checks for follwoing validality:
     * 1. messageText is not empty nor greater than 255 characters
     * 2. postedBy refers to a real existing account
     * 
     * @return the newly minted message
     */
    public Message postMessage(int postedBy, String messageBody, long postedOn) {
        if (messageBody.length() > 0 && messageBody.length() <= 255) {
            // message text is valid; is postedBy valid?
            Optional<Account> account = accountRepository.findById(postedBy);
            if (account.isPresent()) {
                // valid postedBy
                return messageRepository.save(new Message(postedBy, messageBody, postedOn));
            }
        }
        return null;
    }

    /**
     * @return all the messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Get a message by the unique ID. Returns null if none found.
     * 
     * @param id
     * @return
     */
    public Message getMessageByID(int id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            return messageOptional.get();
        } else {
            return null;
        }
    }

    /**
     * Deletes message by its ID.
     * 
     * @return did it delete a message?
     */
    public boolean deleteMessageByID(int id) {
        if (messageRepository.findById(id).isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Updates the message by the given id and newMessageText.
     * 
     * @param id
     * @param newMessageText
     * @return did any message get updated?
     */
    public boolean updateMessage(int id, String newMessageText) {
        if (newMessageText.length() == 0 || newMessageText.length() > 255)
            return false;
        Optional<Message> OptMessage = messageRepository.findById(id);
        if (OptMessage.isPresent()) {
            Message message = OptMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get all messages by account ID
     * 
     * @param accountId
     * @return a list of messages with the same account ID
     */
    public List<Message> getAllMessagesByAccountID(int accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }

}
