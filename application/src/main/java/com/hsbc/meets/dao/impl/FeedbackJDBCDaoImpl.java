package com.hsbc.meets.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.hsbc.meets.dao.FeedbackDao;
import com.hsbc.meets.entity.Meeting;
import com.hsbc.meets.factory.LoggerFactory;
import com.hsbc.meets.util.Connectivity;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Implements Dao class to Add feedback.
 * 
 * @author Ajay
 * @author Muskan
 *
 */
public class FeedbackJDBCDaoImpl implements FeedbackDao {
	
	private static final String ADD_FEEDBACK_BY_USERID_SQL = "call sp_InsertFeedback(?,?,?,?)";
	private static final String GET_UPCOMING_MEETINGS_BY_EMAIL_SQL="call sp_GetUpcomingMeetings(?)"; 
	private static final String GET_RECENT_FEEDBACKPENDING_MEETINGS="call sp_GetRecent_FeedbackPendingMeetings(?)";
	
	private Logger logger;
	
	public FeedbackJDBCDaoImpl() {
		logger = LoggerFactory.getLogger();
	}
	
	@Override
	public void addFeedback(int rating,String comments,int userId,int meetingRoomId) throws SQLException{
		Connection connection = Connectivity.getConnection();
		CallableStatement statement = connection.prepareCall(ADD_FEEDBACK_BY_USERID_SQL);
		statement.setInt(1,rating);
		statement.setString(2,comments);
		statement.setInt(3,userId);
		statement.setInt(4,meetingRoomId);
		statement.execute();
	}
	
	@Override
	public List<Meeting> getUpcomingMeeting(String emailInput)
	{
		Connection connection = Connectivity.getConnection();
		List<Meeting> upComingMeetings = null;
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		try {
			stmt = connection.prepareCall(GET_UPCOMING_MEETINGS_BY_EMAIL_SQL);
			stmt.setString(1, emailInput);
			resultSet = stmt.executeQuery();
			upComingMeetings = new ArrayList<Meeting>();
			while(resultSet.next()) {
				
				String title = resultSet.getString(1);
				String typeOfMeeting = resultSet.getString(2);
				String name = resultSet.getString(3);
			    int meetingRoomId = resultSet.getInt(4);
			    String organizedBy = resultSet.getString(5);
			    String time = resultSet.getString(6);
			    upComingMeetings.add(new Meeting(title,typeOfMeeting,name,meetingRoomId,organizedBy,time));
			    
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
		finally
		{
			try
			{	
				if(resultSet!=null)
				{
					resultSet.close();
				}

				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch (SQLException e) 
			{
				logger.log(Level.SEVERE,e.getMessage(),e);
			}
		}
		return upComingMeetings;

	}
	
	@Override
	public List<Meeting> getRecentMeetings(String emailInput)
	{
		Connection connection = Connectivity.getConnection();
		List<Meeting> recentMeetings = null;
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		try {
			stmt = connection.prepareCall(GET_RECENT_FEEDBACKPENDING_MEETINGS);
			stmt.setString(1, emailInput);
			resultSet = stmt.executeQuery();
			recentMeetings = new ArrayList<Meeting>();
			while(resultSet.next()) {
				
				String title = resultSet.getString(1);
				String typeOfMeeting = resultSet.getString(2);
				String name = resultSet.getString(3);
			    int meetingRoomId = resultSet.getInt(4);
			    String organizedBy = resultSet.getString(5);
			    String time = resultSet.getString(6);
			    recentMeetings.add(new Meeting(title,typeOfMeeting,name,meetingRoomId,organizedBy,time));
			    
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
		finally
		{
			try
			{	
				if(resultSet!=null)
				{
					resultSet.close();
				}

				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch (SQLException e) 
			{
				logger.log(Level.SEVERE,e.getMessage(),e);
			}
		}
		return recentMeetings;

	}

}
