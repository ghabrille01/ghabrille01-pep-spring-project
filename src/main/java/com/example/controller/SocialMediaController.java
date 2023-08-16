package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.account.Account;
import com.example.account.AccountInterfaceService;
import com.example.account.AccountService;
import com.example.message.Message;
import com.example.message.MessageInterfaceService;
import com.example.message.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 *
 * You are expected to have at least the following methods: getAllMessages, getMessageByID, getMessageByAccount, registerAccount, newMessage, login, deleteMessage.
 * 
 * These methods should follow proper naming conventions, and include appropriate arguments. Refer to the tests provided for more details.
 */
@RestController
public class SocialMediaController {

    private AccountInterfaceService accountInterfaceService;
    private MessageInterfaceService messageInterfaceService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountInterfaceService = accountService;
        this.messageInterfaceService = messageService;
    }

    @PostMapping(value = "register")
    public ResponseEntity<Account> registerAccount(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {

            if (username.isEmpty() || password.isEmpty() || password.length() < 4) {
                return ResponseEntity.badRequest().body(null);
            }

            Account resultAccount = accountInterfaceService.addAccountService(username, password);

            if (resultAccount.getAccount_id() != 0) {
                return ResponseEntity.ok(resultAccount);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
        
    }

    @PostMapping(value = "login", params = {"username","password"})
    public ResponseEntity<Account> login(@RequestParam String username, @RequestParam String password) {
        try {
            
            if (username.isEmpty() || password.isEmpty() || password.length() < 4) {
                return ResponseEntity.badRequest().body(null);
            }

            Account resultAccount = accountInterfaceService.validateAccount(username, password);

            if (resultAccount.getAccount_id() != 0) {
                return ResponseEntity.ok(resultAccount);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "messages", params = {"posted_by","message_text","time_posted_epoch"})
    public ResponseEntity<Message> newMessage(@RequestParam int postedBy, @RequestParam String messageText, @RequestParam long timePostedEpoch) {
        try {
            if (messageText.isEmpty() || messageText.length() > 255 || !accountInterfaceService.validateAccount(postedBy)) {
                return ResponseEntity.badRequest().body(null);
            }

            Message resultMessage = messageInterfaceService.addNewMessage(postedBy, messageText, timePostedEpoch);

            if (resultMessage.getMessage_id() != 0) {
                return ResponseEntity.ok(resultMessage);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        try {

            List<Message> resultMessages = messageInterfaceService.selectAllMessages();

            if (resultMessages.size() != 0) {
                return ResponseEntity.ok(resultMessages);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "messages/{message_id}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int messageId) {
        try {

            Message resultMessage = messageInterfaceService.selectMessageById(messageId);

            if (resultMessage.getMessage_id() != 0) {
                return ResponseEntity.ok(resultMessage);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(value = "messages/{message_id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable int messageId) {
        try {

            Message resultMessage = messageInterfaceService.deleteMessageById(messageId);

            if (resultMessage.getMessage_id() != 0) {
                return ResponseEntity.ok(resultMessage);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping(value = "messages/{message_id}", params = {"message_text"})
    public ResponseEntity<Message> updateMessage(@PathVariable int messageId, @RequestParam String messageText) {
        try {
            if (messageText.isEmpty() || messageText.length() > 255 || messageInterfaceService.selectMessageById(messageId).getMessage_id()==0) {
                return ResponseEntity.badRequest().body(null);
            }

            Message resultMessage = messageInterfaceService.updateMessage(messageId, messageText);

            if (resultMessage.getMessage_id() != 0) {
                return ResponseEntity.ok(resultMessage);
            }

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageByAccount(@PathVariable int accountId) {
        try {

            List<Message> resultMessages = messageInterfaceService.selectAllMessagesByAccount(accountId);

            if (resultMessages.size() != 0) {
                return ResponseEntity.ok(resultMessages);
            }

            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("health")
    public String healthCheck() {
        return "Ok";
    }
}
