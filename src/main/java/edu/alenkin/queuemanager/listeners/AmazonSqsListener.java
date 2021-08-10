package edu.alenkin.queuemanager.listeners;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Component
public class AmazonSqsListener implements QueueListener {

    private final AmazonSQS sqs;

    @Value("${aws.deadQueuePrefix}")
    private String deadQueuePrefix;

    public AmazonSqsListener(@Value("${aws.access}") String accessKey,
                             @Value("${aws.secret}") String secretKey,
                             @Value("${aws.region}") String region) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();

        
    }

    @Override
    public List<Message> getMessages(String queueUrl) {
        return sqs.receiveMessage(queueUrl).getMessages()
                .stream()
                .peek((message) -> deleteMessage(message, queueUrl))
                .collect(Collectors.toList());
    }

    public String registerDeadQueue(String queueName) {
        String deadQueueUrl = sqs.createQueue(deadQueuePrefix).getQueueUrl();

        SetQueueAttributesRequest queueAttributesRequest = new SetQueueAttributesRequest()
                .withQueueUrl(queueName)
                .addAttributesEntry("RedrivePolicy",
                        "{\"maxReceiveCount\":\"2\", \"deadLetterTargetArn\":\"" + queueName + "\"}");

        sqs.setQueueAttributes(queueAttributesRequest);
        return deadQueueUrl;
    }

    private void deleteMessage(Message message, String queueUrl) {
        sqs.deleteMessage(new DeleteMessageRequest()
                .withQueueUrl(queueUrl)
                .withReceiptHandle(message.getReceiptHandle()));
    }
}
