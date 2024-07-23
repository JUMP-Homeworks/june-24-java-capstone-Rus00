package com.personal.project.dao;

import java.sql.SQLException;
import java.util.List;

public interface TrackerDao {
    // needed for later so we make sure that the connection manager gets called
	public void establishConnection() throws ClassNotFoundException, SQLException;
	
	// as well, this method will help with closing the connection
	public void closeConnection() throws SQLException;

    public boolean login(String userName, String password);
	public boolean createAccount(String userName, String passWord) throws noIdException;
	
	public List<Topic> getUserTopics();
	public List<Topic> getAllTopics();

	public List<Tracker> getUserEntries();
	public List<Tracker> getAllEntries();
	public List<Tracker> getEntriesByTopicExcludingUser(Topic topic);

	public List<TrackerReport> getTrackerReports();

	public boolean update(Tracker entry);
	public boolean updateProgress(Tracker entry);

	public boolean untrackEntry(Tracker entry);
	public boolean trackEntry(Tracker entry);

	public Topic createNewTopic(String topicName);
	public Tracker createNewEntry(String name, String description, Topic topic) throws failedToInsertException;
}
