package com.example.message;

import java.sql.SQLDataException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (:posted_by, :message_text, :time_posted_epoch)", nativeQuery = true)
    public int newMessage(@Param("posted_by") int postedBy, @Param("message_text") String messageText, @Param("time_posted_epoch") long timePostedEpoch) throws SQLDataException;

    @Query(value = "SELECT * FROM message WHERE posted_by = :posted_by AND message_text = :message_text AND time_posted_epoch = :time_posted_epoch", nativeQuery = true)
    public Message getAllMessage(@Param("posted_by") int postedBy, @Param("message_text") String messageText, @Param("time_posted_epoch") long timePostedEpoch) throws SQLDataException;

    @Query(value = "SELECT * FROM message WHERE posted_by = :posted_by", nativeQuery = true)
    public Message getAllMessage(@Param("posted_by") int postedBy) throws SQLDataException;

    @Query(value = "SELECT * FROM message", nativeQuery = true)
    public List<Message> getAllMessages() throws SQLDataException;

    @Query(value = "SELECT * FROM message WHERE message_id = :message_id", nativeQuery = true)
    public Message getMessageByID(@Param("message_id") int messageId) throws SQLDataException;

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM message WHERE message_id = :message_id", nativeQuery = true)
    public int deleteMessage(@Param("message_id") int messageId) throws SQLDataException;

    @Modifying
    @Transactional
    @Query(value = "UPDATE message SET message_text = :message_text WHERE message_id = :message_id", nativeQuery = true)
    public int updateMessage(@Param("message_text") String messageText, @Param("message_id") int messageId) throws SQLDataException;

    @Query(value = "SELECT * FROM message WHERE posted_by = :posted_by", nativeQuery = true)
    public List<Message> getMessageByAccount(@Param("posted_by") int postedBy) throws SQLDataException;

}
