package com.personal.project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.personal.project.dao.ProgressTracker;
import com.personal.project.dao.Topic;
import com.personal.project.dao.Tracker;
import com.personal.project.dao.TrackerReport;
import com.personal.project.dao.failedToInsertException;
import com.personal.project.dao.noIdException;

public class Main {

    private static final String[] progressMessages = {"Not Completed", "In Progress", "Completed"};
    private static ProgressTracker pTracker;
    private static List<Tracker> userEntries = null;
    private static Scanner reader;

    public static void viewTrackerReports(){
        List<TrackerReport> tReports = pTracker.getTrackerReports();
        int maxNameSize = 0;
        int maxTopicNameSize = 0;
        Tracker tempTracker;
        int inputI;

        for(TrackerReport temp: tReports){
            if(temp.getEntry().getName().length() > maxNameSize){
                maxNameSize = temp.getEntry().getName().length();
            }

            if(temp.getEntry().getTopic().getTopic().length() > maxTopicNameSize){
                maxTopicNameSize = temp.getEntry().getTopic().getTopic().length();
            }
        }
        maxNameSize += 3;
        maxTopicNameSize += 3;

        while(true){
            System.out.printf("%-" + maxNameSize + "s %-" + maxTopicNameSize + "s %-12s %-12s %-12s %n", "Entry Name", "Topic", "Completed", "In Progress", "Not Completed");
            for(TrackerReport temp: tReports){
                tempTracker = temp.getEntry();
                System.out.printf("%-" + maxNameSize + "s %-" + maxTopicNameSize + "s %-12d %-12d %d %n", tempTracker.getName(), tempTracker.getTopic().getTopic(), temp.getCompleted(), temp.getInProgress(), temp.getNotCompleted());
            }

            System.out.println("1. Go Back");
            inputI = reader.nextInt();

            if(inputI == 1){
                return;
            }else{
                System.err.println("Invalid Input");
            }
        }
    }

    public static boolean trackNewEntry(Topic topic){
        List<Tracker> entries;
        int i, inputI;
        Tracker tempTracker;

        if(topic == null){
            List<Topic> allTopics = pTracker.getAllTopics();

            while(true){
                System.out.println("Which topic would you like to track a new entry in?");

                for(i = 0; i < allTopics.size(); i++){
                    System.out.println(i + 1 + ". " + allTopics.get(i).getTopic());
                }
                System.out.println(i + 1 + ". Create a New Topic");
                System.out.println(i + 2 + ". Cancel");


                inputI = reader.nextInt();

                if(inputI >= 1 && inputI <= i){
                    topic = allTopics.get(inputI - 1);
                    break;

                }else if(inputI == i + 1){
                    String newTopicName;
                    reader.nextLine();
                    
                    do{
                        System.out.println("Please enter new topic name");

                        newTopicName = reader.nextLine();

                        topic = pTracker.createNewTopic(newTopicName);
                    }while(topic == null);
                    break;

                }else if(inputI == i + 2){
                    return false;
                }else{
                    System.out.println("Invalid Input");
                }
            }
        }

        entries = pTracker.getEntriesByTopicExcludingUser(topic);

        int maxSize = 0;

        if(entries != null){
            for(Tracker temp:entries){
                if(temp.getName().length() > maxSize){
                    maxSize = temp.getName().length();
                }
            }

            maxSize += 3;
            for(i = 0; i < entries.size(); i++){
                tempTracker = entries.get(i);
                System.out.printf("%d. %-" + maxSize + "s %.100s %n", i+1, tempTracker.getName(), tempTracker.getDescription());
            }
        }else{
            i = 0;
        }

        System.out.println(i + 1 + ". Create a New Entry");
        System.out.println(i + 2 + ". Cancel");

        inputI = reader.nextInt();

        if(inputI >= 1 && inputI <= i){
            tempTracker = entries.get(inputI - 1);
            
            while(true){
                System.out.println("What is your progress on " + tempTracker.getName());

                System.out.println("1. Not Completed");
                System.out.println("2. In Progress");
                System.out.println("3. Completed");

                inputI = reader.nextInt();

                if(inputI >= 1 && inputI <= 3){
                    tempTracker.setProgress(inputI - 2);
                    userEntries.add(tempTracker);
                    pTracker.trackEntry(tempTracker);
                    break;

                }else{
                    System.err.println("Invalid Input");
                }
            }
        }else if(inputI == i + 1){
            String name, description;
            Tracker newEntry = null;
            reader.nextLine();

            do{
                System.out.println("Please enter new entry name");
                name = reader.nextLine();

                System.out.println("Please enter new entry description");
                description = reader.nextLine();

                try{
                    newEntry = pTracker.createNewEntry(name, description, topic);
                }catch(failedToInsertException e){
                    System.out.println(e.getMessage());
                    return false;

                }

            }while(newEntry == null);

            while(true){
                System.out.println("What is your progress on " + newEntry.getName());

                System.out.println("1. Not Completed");
                System.out.println("2. In Progress");
                System.out.println("3. Completed");

                inputI = reader.nextInt();

                if(inputI >= 1 && inputI <= 3){
                    newEntry.setProgress(inputI - 2);

                    if(userEntries == null){
                        userEntries = new ArrayList<Tracker>();
                    }
                    
                    userEntries.add(newEntry);
                    pTracker.trackEntry(newEntry);
                    break;
                }else{
                    System.err.println("Invalid Input");
                }
            }

        }else if(inputI == i + 2){
            return false;
        }else{
            System.out.println("Invalid Input");
        }

        return false;
    }

    public static List<Tracker> filterByTopic(Topic topic){
        List<Tracker> topicEntries = new ArrayList<Tracker>();

        for(Tracker temp:userEntries){
            if(temp.getTopic_id() == topic.getTopic_id()){
                topicEntries.add(temp);
            }
        }

        return topicEntries;
    }

    public static void manageEntries(Topic topic){
        Tracker tempTracker;
        int inputI;
        int i = 0;
        List<Tracker> entries = null;

        while(true){
            if(topic != null){
                System.out.println(topic.getTopic() + ": ");

                entries = filterByTopic(topic);
            }else{
                entries = userEntries;
            }

            if(entries != null){
                int maxSize = 0;

                for(Tracker temp:entries){
                    if(temp.getName().length() > maxSize){
                        maxSize = temp.getName().length();
                    }
                }

                maxSize += 3;

                for(i = 0; i < entries.size(); i++){
                    tempTracker = entries.get(i);
                    System.out.printf("%d. %-" + maxSize + "s %-20s %s %n", i+1, tempTracker.getName(), progressMessages[tempTracker.getProgress() + 1], tempTracker.getTopic());
                }
            }else{
                i = 0;
            }

            System.out.println(i + 1 + ". Track New Entry");
            System.out.println(i + 2 + ". Go Back");

            inputI = reader.nextInt();

            if(inputI >= 1 && inputI <= i){
                tempTracker = entries.get(inputI - 1);

                editEntry(tempTracker);

            }else if(inputI == i + 1){
                trackNewEntry(topic);
                
            }else if(inputI == i + 2){
                break;

            }else{
                System.out.println("Invalid Input");
            }

            
        }
    }

    public static void editEntry(Tracker entry){
        int inputI;

        while(true){
            int numSpace = entry.getName().length() + 3;

            System.out.printf("%-" + numSpace + "s %-16s %s %n", entry.getName(), progressMessages[entry.getProgress() + 1], entry.getTopic());
            System.out.println("Description: " + entry.getDescription());

            System.out.println("1. Update Progress");
            System.out.println("2. Delete");
            System.out.println("3. Go Back");

            inputI = reader.nextInt();

            if(inputI == 1){
                System.out.println("1. Not Completed");
                System.out.println("2. In Progress");
                System.out.println("3. Completed");
                System.out.println("4. Go Back");

                inputI = reader.nextInt();

                if(inputI == 4){
                    continue;

                }else if(inputI >= 1 && inputI <= 3){
                    entry.setProgress(inputI - 2);
                    pTracker.updateProgress(entry);
                    continue;
                }

            }else if(inputI == 2){
                pTracker.untrackEntry(entry);
                userEntries.remove(entry);
                break;
                
            }else if(inputI == 3){
                break;

            }
        }
    }

    public static void main(String[] args) {
        // Creating the Progress Tracker object and making a connection to MySQL database
        pTracker = new ProgressTracker();
        
        try {
            pTracker.establishConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
            return;
        }

        reader = new Scanner(System.in);
        int inputI;

        // Outermost loop used for logging in or creating accounts
        while(true){
            System.out.println("\nWelcome to Progress Tracker!");
            System.out.println("1. Log in to existing account.");
            System.out.println("2. Create a new account.");
            System.out.println("3. Exit");
            
            inputI = reader.nextInt();
            reader.nextLine();

            // Used for logging in
            if(inputI == 1){
                String userName;
                String passWord;

                do{
                    System.out.println("Please enter your user name.");
                    userName = reader.nextLine();

                    System.out.println("Please enter your password.");
                    passWord = reader.nextLine();
                    
                }while(!pTracker.login(userName, passWord));

            // Creating an account
            }else if(inputI == 2){
                String userName;
                String passWord;
                boolean ifSuccessful;

                do{
                    System.out.println("Please enter a user name you would like to use. Only letters, numbers and underscores are allowed.");
                    userName = reader.nextLine();
                    
                    System.out.println("Please enter a password. The password must be 8 digits long and can not contain white spaces.");
                    passWord = reader.nextLine();

                    try{
                        ifSuccessful = pTracker.createAccount(userName, passWord);
                    }catch(noIdException e){
                        System.out.println(e.getMessage());
                        break;
                    }

                }while(!ifSuccessful);

            // Quits the main loop and therefore the application
            }else if(inputI == 3){
                break;
            }else{
                System.out.println("Invalid Input");
            }

            // The main functionality loop
            while(true){
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. View/Manage Progress");
                System.out.println("2. View Tracker reports");
                System.out.println("3. Log Out");        

                inputI = reader.nextInt();

                // View or manage progress
                if(inputI == 1){
                    while(true){

                        System.out.println("1. View All");
                        System.out.println("2. View by Topics");
                        System.out.println("3. Go Back");

                        if(userEntries == null){
                            userEntries = pTracker.getUserEntries();
                        }

                        inputI = reader.nextInt();

                        if(inputI == 1){
                            manageEntries(null);

                        }else if(inputI == 2){
                            int i = 0;
                            Topic chosenTopic = null;
                            List<Topic> userTopics = pTracker.getUserTopics();

                            while(true){
                                System.out.println("Your Topics: ");

                                if(userTopics != null){
                                    for(i = 0; i < userTopics.size(); i++){
                                        System.out.println(i + 1 + ". " + userTopics.get(i).getTopic());
                                    }
                                }else{
                                    i = 0;
                                }
                                System.out.println(i + 1 + ". Go Back");

                                inputI = reader.nextInt();

                                if(inputI >= 1 && inputI <= i){
                                    chosenTopic = userTopics.get(inputI - 1);

                                    manageEntries(chosenTopic);
                                }else if(inputI == i + 1){
                                    break;
                                }else{
                                    System.out.println("Invalid Input");
                                }
                            }

                        }else if(inputI == 3){
                            break;
                        }else{
                            System.out.println("Invalid Input");
                        }
                    }
                // Viewing stats
                }else if(inputI == 2){
                    viewTrackerReports();
                    
                // Goes back to the authentication loop
                }else if(inputI == 3){
                    break;
                }else{
                    System.out.println("Invalid Input");
                }
            }
        }
        

        reader.close();
    }

}