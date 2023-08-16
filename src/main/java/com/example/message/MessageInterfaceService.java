package com.example.message;

import java.sql.SQLDataException;
import java.util.List;

public interface MessageInterfaceService {
    
    public Message addNewMessage(int postedBy, String messageText, long timePostedEpoch) throws SQLDataException;

    public List<Message> selectAllMessages() throws SQLDataException;

    public Message selectMessageById(int messageId) throws SQLDataException;

    public Message deleteMessageById(int messageId) throws SQLDataException;

    public Message updateMessage(int postedBy, String messageText) throws SQLDataException;

    public List<Message> selectAllMessagesByAccount(int accountId) throws SQLDataException;
}
