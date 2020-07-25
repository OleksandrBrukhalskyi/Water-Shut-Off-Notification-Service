package com.dev.notification_service.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SNSController {
    @Autowired
    private AmazonSNSClient snsClient;

    @GetMapping("/api/v1/topics")
    public List<Topic> getTopics(){
        ListTopicsResult listTopicsResult = snsClient.listTopics();
        List<Topic> topics = listTopicsResult.getTopics();
        return topics;
    }
}
