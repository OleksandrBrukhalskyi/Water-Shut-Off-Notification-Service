package com.dev.notification_service.services;



import com.amazonaws.services.sns.model.Topic;

import java.util.List;

public interface GetTopics {
    List<Topic> getTopics();
}
