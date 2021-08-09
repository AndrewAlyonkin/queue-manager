package edu.alenkin.queuemanager.parsers;

import com.amazonaws.services.sqs.model.Message;
import edu.alenkin.queuemanager.entity.Notification;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface MessageParser {
    List<Notification> parse(List<Message> messages);

    Notification parse(Message message);
}
