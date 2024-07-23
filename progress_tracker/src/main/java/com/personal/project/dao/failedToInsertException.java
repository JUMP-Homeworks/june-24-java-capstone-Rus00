package com.personal.project.dao;

public class failedToInsertException extends Exception{
    private static final long serialVersionUID = 1L;

    public failedToInsertException(String name){
        super("Failed to insert " + name);
    }
}
