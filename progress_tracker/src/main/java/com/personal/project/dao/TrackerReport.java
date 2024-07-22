package com.personal.project.dao;

public class TrackerReport {
    private Tracker entry;
    private int completed;
    private int inProgress;
    private int notCompleted;

    public TrackerReport(Tracker entry, int completed, int inProgress, int notCompleted) {
        this.entry = entry;
        this.completed = completed;
        this.inProgress = inProgress;
        this.notCompleted = notCompleted;
    }

    public Tracker getEntry() {
        return entry;
    }

    public void setEntry(Tracker entry) {
        this.entry = entry;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getInProgress() {
        return inProgress;
    }

    public void setInProgress(int inProgress) {
        this.inProgress = inProgress;
    }

    public int getNotCompleted() {
        return notCompleted;
    }

    public void setNotCompleted(int notCompleted) {
        this.notCompleted = notCompleted;
    }

    
    
}
