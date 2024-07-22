package com.personal.project.dao;

public class Tracker {
    // Entry id
    private int id;
    private String name;
    private String description;
    // 1 - Not Completed  2 - In Progress  3 - Completed
    private int progress;

    private Topic topic;

    // Constructor for creating a Tracker object with topic name and topic id
    public Tracker(int id, int topicId, String topicName, String name, String description, int progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        
        this.topic = new Topic(topicId, topicName);

        if(progress >= -1 && progress <= 1){
            this.progress = progress;
        }else{
            System.err.println("Status has to be within -1 to 1 range. Setting status to default value -1.");
            this.progress = -1;
        }
    }

    // Constructor for creating a Tracker object with a Topic object
    public Tracker(int id, Topic topic, String name, String description, int progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        
        this.topic = topic;

        if(progress >= -1 && progress <= 1){
            this.progress = progress;
        }else{
            System.err.println("Status has to be within -1 to 1 range. Setting status to default value -1.");
            this.progress = -1;
        }
    }

    @Override
    public String toString() {
        return "Tracker [id=" + id + ", name=" + name + ", description=" + description + ", status=" + progress + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress >= -1 && progress <= 1){
            this.progress = progress;
        }else{
            System.err.println("Progress has to be within -1 to 1 range. Progress unchanged.");
        }
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getTopic_id() {
        return topic.getTopic_id();
    }
}
