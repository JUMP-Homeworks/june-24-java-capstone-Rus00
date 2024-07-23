package com.personal.project.dao;

public class noIdException extends Exception{
    private static final long serialVersionUID = 1L;

    public noIdException(String name){
        super("Could not obtain + " + name + " ID.");
    }
}
