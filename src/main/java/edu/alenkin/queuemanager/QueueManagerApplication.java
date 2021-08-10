package edu.alenkin.queuemanager;

import edu.alenkin.queuemanager.workers.QueueWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class QueueManagerApplication {

    @Autowired
    private QueueWorker worker;

    public static void main(String[] args) {
        SpringApplication.run(QueueManagerApplication.class, args);
    }

    @Scheduled(fixedDelay = 5000)
    public void startCheckQueues() {
        worker.checkQueues();
    }
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {

}
