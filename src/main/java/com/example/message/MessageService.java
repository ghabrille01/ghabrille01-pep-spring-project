package com.example.message;

import java.sql.SQLDataException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements MessageInterfaceService {
    
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addNewMessage(int postedBy, String messageText, long timePostedEpoch) throws SQLDataException {
        int result = messageRepository.newMessage(postedBy, messageText, timePostedEpoch);

        if (result == 1) {
            return messageRepository.getAllMessage(postedBy, messageText, timePostedEpoch);
        } else {
            return null;
        }
    }

    public List<Message> selectAllMessages() throws SQLDataException {
        return messageRepository.getAllMessages();
    }

    public Message selectMessageById(int messageId) throws SQLDataException {
        return messageRepository.getMessageByID(messageId);
    }

    public Message deleteMessageById(int messageId) throws SQLDataException {
        Message message = messageRepository.getMessageByID(messageId);

        if (messageRepository.deleteMessage(messageId)==1) {
            return message;
        }

        return new Message();
    }

    public Message updateMessage(int postedBy, String messageText) throws SQLDataException {
        if (messageRepository.updateMessage(messageText, postedBy)==1) {
            return messageRepository.getMessageByID(postedBy);
        }

        return new Message();
    }

    public List<Message> selectAllMessagesByAccount(int accountId) throws SQLDataException {
        return messageRepository.getMessageByAccount(accountId);
    }
}
