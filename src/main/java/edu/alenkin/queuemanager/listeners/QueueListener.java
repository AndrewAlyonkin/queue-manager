package edu.alenkin.queuemanager.listeners;

import com.amazonaws.services.sqs.model.Message;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface QueueListener {
    List<Message> getMessages(String queueUrl);

    String registerDeadQueue(String originalQueue);

}
