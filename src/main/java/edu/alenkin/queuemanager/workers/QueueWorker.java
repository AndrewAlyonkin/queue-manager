package edu.alenkin.queuemanager.workers;

import com.amazonaws.services.sqs.model.Message;
import edu.alenkin.queuemanager.entity.Notification;
import edu.alenkin.queuemanager.listeners.QueueListener;
import edu.alenkin.queuemanager.notesservices.NotesService;
import edu.alenkin.queuemanager.parsers.MessageParser;
import edu.alenkin.queuemanager.queuemanagers.QueueManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

@Service
@Slf4j
public class QueueWorker {

    private final QueueListener listener;
    private final MessageParser parser;
    private final NotesService notesService;
    private final QueueManager queueManager;

    public QueueWorker(QueueListener listener, MessageParser parser,
                       NotesService notesService, QueueManager queueManager) {
        this.listener = listener;
        this.parser = parser;
        this.notesService = notesService;
        this.queueManager = queueManager;
    }

    public void checkQueues() {
        log.info("Check queues");
        var queuesMessages = getMessagesFromQueues(queueManager.getTrackedQueueUrls());
        parseMessages(queuesMessages).forEach(notesService::save);
    }

    private List<Message> getMessagesFromQueues(List<String> trackedQueues) {
        return trackedQueues
                .stream()
                .flatMap((queueUrl) -> listener.getMessages(queueUrl).stream())
                .collect(Collectors.toList());
    }

    private List<Notification> parseMessages(List<Message> messages) {
        return messages
                .stream()
                .map(parser::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
