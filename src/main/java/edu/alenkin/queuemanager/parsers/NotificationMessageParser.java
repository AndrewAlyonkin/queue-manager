package edu.alenkin.queuemanager.parsers;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.alenkin.queuemanager.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
public class NotificationMessageParser implements MessageParser{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Notification> parse(List<Message> messages) {
        return messages.stream().map(this::parse).collect(Collectors.toList());
    }

    @Override
    public Notification parse(Message message) {
        try {
            return objectMapper.readValue(message.getBody(), Notification.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
