package com.dev.notification_service.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.*;
import com.dev.notification_service.services.CreateTopic;
import com.dev.notification_service.services.DeleteTopic;
import com.dev.notification_service.services.GetTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

import javax.print.URIException;
import java.util.List;

@RestController
public class SNSController implements CreateTopic, GetTopics, DeleteTopic {

    @Autowired
    private AmazonSNSClient snsClient;
    @Override
    @GetMapping("/api/v1/topics")
    public List<Topic> getTopics(){
        ListTopicsResult listTopicsResult = snsClient.listTopics();
        String nextToken = listTopicsResult.getNextToken();
        List<Topic> topics = listTopicsResult.getTopics();
        while(nextToken != null){
            listTopicsResult = snsClient.listTopics(nextToken);
            nextToken = listTopicsResult.getNextToken();
            topics.addAll(listTopicsResult.getTopics());
        }
      return topics;
    }
    @Override
    @PostMapping("/api/v1/createTopic/")
    public String createTopic(@RequestParam("topicName") String topicName){
        CreateTopicResult result = snsClient.createTopic(topicName);
            return "Топік створено успішно!" + result.getTopicArn();
    }
    @PostMapping("/api/v1/pubToTopic")
    public String publishToTopic(@RequestParam("message") String message,
                                 @RequestParam("topicArn") String topicArn,
                                 @RequestParam("subject") String subject
                            ){
      snsClient.publish(topicArn,message,subject);
      return "Message sent to " + topicArn;
    }
    @PostMapping("/api/v1/subscribe")
    public String subscribeToTopic(@RequestParam("endpoint") String phoneNumber,
                                   @RequestParam("topicArn") String topicArn)  {
        snsClient.subscribe(topicArn,"sms",phoneNumber);

        return "The phone number "+phoneNumber +" was successfully subscribed to " + topicArn + " using SMS protocol";
    }

    @Override
    @DeleteMapping("api/v1/delete")
    public String deleteTopic(@RequestParam("topicArn") String topicArn) {
        snsClient.deleteTopic(topicArn);
        return topicArn + " was successfully deleted!";
    }
}
