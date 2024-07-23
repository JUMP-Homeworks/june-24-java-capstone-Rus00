package com.personal.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.personal.project.connection.ConnectionManager;

public class ProgressTracker implements TrackerDao{

    private Connection connection = null;
    private String userName = null;
    private int userId = -1;
    
	@Override
	public void establishConnection() throws ClassNotFoundException, SQLException {
		
		if(connection == null) {
			connection = ConnectionManager.getConnection();
		}
	}
	
	@Override
	public void closeConnection() throws SQLException {
		connection.close();
	}

    @Override
    public boolean login(String userName, String password) {
        PreparedStatement pstmt;
        ResultSet rs;
        String truePassWord;

        try {
            pstmt = connection.prepareStatement("select * from users where username = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if(!rs.next()){
                System.out.println("\nCould not find an account associated with the user name. Please try again.");
                return false;
            }

            truePassWord = rs.getString("password");

            if(!truePassWord.equals(password)){
                System.out.println("\nPassword incorrect. Please try again.");
                return false;
            }

            System.out.println("\nWelcome " + userName + "!");
            this.userName = userName;
            this.userId = rs.getInt("user_id");
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createAccount(String userName, String passWord) throws noIdException{
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher matcher;
        PreparedStatement pstmt;
        ResultSet rs;
        
        matcher = pattern.matcher(userName);

        if(!matcher.matches()){
            System.out.println("\nOnly letters, numbers and underscores are allowed. Please try a different user name.");
            return false;
        }

        if(passWord.length() < 8 || passWord.contains(" ")){
            System.out.println("\nThe password must be 8 digits long and can not contain white spaces.");
            return false;
        }
        
        try {
            pstmt = connection.prepareStatement("insert into users (username, password) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, userName);
            pstmt.setString(2, passWord);

            if(pstmt.executeUpdate() == 0){
                System.out.println("\nAccount could not be created.");
                return false;
            }

            rs = pstmt.getGeneratedKeys();
        
            if(rs.next()){
                System.out.println("\nYour account has been created!");

                this.userId = rs.getInt(1);
                this.userName = userName;
                return true;
            }else{
                throw new noIdException("user");
                
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Topic> getUserTopics() {
        List<Topic> topics = new ArrayList<Topic>();

        try{
            Statement stmt;
            ResultSet rs;
            String sql;
            int id;
            String topicName;

            stmt = connection.createStatement();
            sql = "select distinct topics.topic_id, topics.topic_name from user_entries join entries on user_entries.entry_id = entries.entry_id join topics on entries.topic_id = topics.topic_id where user_id = ";
            sql = sql + this.userId;
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                
                id = rs.getInt("topic_id");
                topicName = rs.getString("topic_name");
                Topic topic = new Topic(id, topicName);
                topics.add(topic);
            }

            if(topics.isEmpty()){
                return null;
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }

        return topics;
    }

    @Override
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<Topic>();

        try{
            Statement stmt;
            ResultSet rs;
            String sql;
            int id;
            String topicName;

            stmt = connection.createStatement();
            sql = "SELECT * FROM topics;";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                
                id = rs.getInt("topic_id");
                topicName = rs.getString("topic_name");
                
                Topic topic = new Topic(id, topicName);
                topics.add(topic);
            }

            if(topics.isEmpty()){
                return null;
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }

        return topics;
    }

    @Override
    public List<Tracker> getUserEntries() {
        List<Tracker> entries = new ArrayList<Tracker>();
        
        try{
            Statement stmt;
            ResultSet rs;
            String sql;
            int topic_id, entry_id, progress;
            String topic_name, name, description;

            stmt = connection.createStatement();
            sql = "select entries.topic_id, topics.topic_name, entries.entry_id, entries.name, entries.description, user_entries.progress from user_entries join entries on entries.entry_id = user_entries.entry_id join topics on entries.topic_id = topics.topic_id where user_id = ";
            sql = sql + this.userId;
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                    
                topic_id = rs.getInt("topic_id");
                topic_name = rs.getString("topic_name");
                entry_id = rs.getInt("entry_id");
                name = rs.getString("name");
                description = rs.getString("description");
                progress = rs.getInt("progress");
                Tracker tracker = new Tracker(entry_id, topic_id, topic_name, name, description, progress);
                entries.add(tracker);
            }

            if(entries.isEmpty()){
                return null;
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
        return entries;
    }

    @Override
    public List<Tracker> getAllEntries() {
        List<Tracker> entries = new ArrayList<Tracker>();
        
        try{
            Statement stmt;
            ResultSet rs;
            String sql;
            int topic_id, entry_id;
            String topic, name, description;

            stmt = connection.createStatement();
            sql = "SELECT entries.entry_id, entries.name, entries.description, entries.topic_id, topics.topic_name FROM entries join topics on entries.topic_id = topics.topic_id";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                    
                topic_id = rs.getInt("topic_id");
                topic = rs.getString("topic_name");
                entry_id = rs.getInt("entry_id");
                name = rs.getString("name");
                description = rs.getString("description");
                Tracker tracker = new Tracker(entry_id, topic_id, topic, name, description, -1);
                entries.add(tracker);
            }

            if(entries.isEmpty()){
                return null;
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
        return entries;
    }


    @Override
    public List<TrackerReport> getTrackerReports(){
        List<TrackerReport> reports = new ArrayList<TrackerReport>();
        
        try{
            Statement stmt;
            ResultSet rs;
            String sql;
            int topic_id, entry_id, notCompleted, inProgress, completed;
            String topic_name, name, description;

            stmt = connection.createStatement();
            sql = "select user_entries.entry_id, entries.topic_id, topics.topic_name, name, description, sum(case when progress = 1 then 1 else 0 end) as completed, sum(case when progress = 0 then 1 else 0 end) as inProgress, sum(case when progress = -1 then 1 else 0 end) as notCompleted from user_entries join entries on entries.entry_id = user_entries.entry_id join topics on topics.topic_id = entries.topic_id group by entry_id;";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                entry_id = rs.getInt("entry_id");
                topic_id = rs.getInt("topic_id");
                topic_name = rs.getString("topic_name");
                name = rs.getString("name");
                description = rs.getString("description");
                completed = rs.getInt("completed");
                inProgress = rs.getInt("inProgress");
                notCompleted = rs.getInt("notCompleted");

                Tracker tracker = new Tracker(entry_id, topic_id, topic_name, name, description, -1);
                TrackerReport tReport = new TrackerReport(tracker, completed, inProgress, notCompleted);
                reports.add(tReport);
            }

            if(reports.isEmpty()){
                return null;
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
        return reports;
    }


    @Override
    public boolean update(Tracker entry) {
        // try{

        //     PreparedStatement pstmt = connection.prepareStatement("UPDATE chef SET name = ?, best_dish = ?, restaurant = ?, rating = ? WHERE chef_id = ?");
        //     pstmt.setString(1,  chef.getName());
        //     pstmt.setString(2,  chef.getBestDish());
        //     pstmt.setString(3, chef.getRestaurant());
        //     pstmt.setInt(4, chef.getRating());
        //     pstmt.setInt(5, chef.getId());
        // } catch(SQLException e) {
		
		// 	System.out.println(e.getMessage());
		// 	return false;
		// }
        return false;
    }

    @Override
    public boolean updateProgress(Tracker entry) {
        try{

            PreparedStatement pstmt = connection.prepareStatement("update user_entries set progress = ? where user_id = ? and entry_id = ?");
            pstmt.setInt(1, entry.getProgress());
            pstmt.setInt(2, this.userId);
            pstmt.setInt(3, entry.getId());

            int i = pstmt.executeUpdate();

            if(i > 0) {
				return true;
			}else{
				System.out.println("user entry not found.");
				return false;
			}

        } catch(SQLException e) {
		
			System.out.println(e.getMessage());
			return false;
		}
    }

    @Override
    public boolean untrackEntry(Tracker entry) {
        try{
            PreparedStatement pstmt = connection.prepareStatement("delete from user_entries where user_id = ? and entry_id = ?");
            pstmt.setInt(1, this.userId);
            pstmt.setInt(2, entry.getId());
        
            int i = pstmt.executeUpdate();
			
			if(i > 0) {
				return true;
			}else{
				System.out.println("Could not delete entry from user_entries.");
				return false;
			}

        } catch(SQLException e) {
		
			System.out.println(e.getMessage());
			return false;
		}

    }

    @Override
    public boolean trackEntry(Tracker entry){
        try{
            PreparedStatement pstmt = connection.prepareStatement("insert into user_entries values(?, ?, ?)");
            pstmt.setInt(1, this.userId);
            pstmt.setInt(2, entry.getId());
            pstmt.setInt(3, entry.getProgress());
        
            int i = pstmt.executeUpdate();
			
			if(i > 0) {
				return true;
			}else{
				System.out.println("Could not insert into user_entries");
				return false;
			}

        } catch(SQLException e) {
		
			System.out.println(e.getMessage());
			return false;
		}
    }

    @Override
    public List<Tracker> getEntriesByTopicExcludingUser(Topic topic) {
        List<Tracker> entries = new ArrayList<Tracker>();
        
        try{
            PreparedStatement pstmt;
            ResultSet rs;
            int entry_id;
            String name, description;

            pstmt = connection.prepareStatement("select entry_id, name, description from entries where topic_id = ? and entry_id not in (select entry_id from user_entries where user_id = ?)");
            pstmt.setInt(1, topic.getTopic_id());
            pstmt.setInt(2, this.userId);
            rs = pstmt.executeQuery();

            while(rs.next()){
                
                entry_id = rs.getInt("entry_id");
                name = rs.getString("name");
                description = rs.getString("description");
                
                Tracker tracker = new Tracker(entry_id, topic, name, description, -1);
                entries.add(tracker);
            }

            if(entries.isEmpty()){
                return null;
            }

        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
        return entries;
    }

    @Override
    public Topic createNewTopic(String topicName) {
        topicName = topicName.trim().replaceAll("  +", " ");

        if(!topicName.matches("[ a-zA-Z]+")){
            System.err.println("Topic name can only contain letters and space.");
            return null;
        }

        if(topicName.length() > 20){
            System.err.println("Topic name too long. Try a shorter name");
            return null;
        }else if(topicName.isEmpty()){
            System.err.println("Topic name empty");
            return null;
        }

        try{
            ResultSet rs;
            PreparedStatement pstmt = connection.prepareStatement("insert into topics values(null, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, topicName);

            int i = pstmt.executeUpdate();

            if(i == 0) {
				System.err.println("Failed To Insert New Topic");
				return null;
			}

            rs = pstmt.getGeneratedKeys();

            Topic newTopic;
            if(rs.next()){
                newTopic = new Topic(rs.getInt(1), topicName);
                return newTopic;
            }else{
                System.err.println("Failed To Get New topic_id");
                return null;
            }

        } catch(SQLException e) {
		
			System.out.println(e.getMessage());
			return null;
		}
    }

    @Override
    public Tracker createNewEntry(String name, String description, Topic topic) throws failedToInsertException{
        name = name.trim().replaceAll("  +", " ");

        if(name.length() > 100){
            System.err.println("Topic name too long. Try a shorter name");
            return null;
        }else if(name.isEmpty()){
            System.err.println("Topic name empty");
            return null;
        }

        description = description.trim().replaceAll("  +", " ");

        if(description.length() > 1000){
            System.err.println("Description too long. Try a shorter description");
            return null;
        }else if(description.isEmpty()){
            System.err.println("Description empty");
            return null;
        }

        try{
            ResultSet rs;
            PreparedStatement pstmt = connection.prepareStatement("insert into entries (topic_id, name, description) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, topic.getTopic_id());
            pstmt.setString(2, name);
            pstmt.setString(3, description);

            int i = pstmt.executeUpdate();

            if(i == 0) {
				throw new failedToInsertException("new entry");
			}

            rs = pstmt.getGeneratedKeys();

            Tracker newEntry;
            if(rs.next()){
                newEntry = new Tracker(rs.getInt(1), topic, name, description, -1);
                return newEntry;
            }else{
                System.err.println("Failed To Get New Entry id");
                newEntry = new Tracker(-1, topic, name, description, -1);
                return newEntry;
            }

        } catch(SQLException e) {
		
			System.out.println(e.getMessage());
			return null;
		}
    }
    
    
}
