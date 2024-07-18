package com.personal.project.dao;

public class Topic {
    private int topic_id;
    private String topic;
    
    public Topic(int topic_id, String topic) {
        this.topic_id = topic_id;
        this.topic = topic;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(int topic_id, String topic) {
        this.topic_id = topic_id;
        this.topic = topic;
    }

}
