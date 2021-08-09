package edu.alenkin.queuemanager.queuemanagers;

import edu.alenkin.queuemanager.listeners.QueueListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
public class QueueManager {

    @Value("${manager.queues}")
    private List<String> trackedQueues;

    @Autowired
    private QueueListener queueListener;

    private List<String> deadQueues;

    public List<String> getTrackedQueueUrls() {
        return trackedQueues;
    }

    public void subscribeToQueue(String queueUrl) {
        this.trackedQueues.add(queueUrl);
        deadQueues.add(queueListener.registerDeadQueue(queueUrl));
    }

    public void unsubscribeFromQueue(String queueUrl) {
        this.trackedQueues.remove(queueUrl);
    }
}
