package com.hsbc.meets.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hsbc.meets.dao.MeetingDao;
import com.hsbc.meets.entity.Meeting;
import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.entity.User;
import com.hsbc.meets.exception.MeetingTitleInvalidException;
import com.hsbc.meets.exception.NotEnoughSeatsException;
import com.hsbc.meets.exception.SlotNotAvailableException;
import com.hsbc.meets.exception.SomethingWentWrongException;
import com.hsbc.meets.util.Connectivity;

/**
 * This class implements {@link MeetingDao}
 * @author ShubhraBhuniaGhosh
 *
 */
public class MeetingDbDaoImpl implements MeetingDao{
	
	private Connection con;
	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public MeetingDbDaoImpl(){
		con = Connectivity.getConnection();
	}
	
	public List<User> getAllUsers(){
		List<User> attendees = null;
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = con.prepareCall("call sp_GetAllUsers()");
			resultSet = stmt.executeQuery();
			attendees = new ArrayList<User>();
			while(resultSet.next()) {
				User atnde = new User();
				atnde.setName(resultSet.getString(1));
				atnde.setEmail(resultSet.getString(2));
				attendees.add(atnde);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return attendees;
	}

	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public List<MeetingRoom> getMeetingRooms(Calendar startDateTime, Calendar endDateTime, String type, int capacity) {
		ArrayList<MeetingRoom> meetingRooms = null;
		ResultSet resultSet = null;
		CallableStatement stmt = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strStartDateTime = sdf.format(startDateTime.getTime());
		String strEndDateTime = sdf.format(endDateTime.getTime());

		try {
			stmt = con.prepareCall("call sp_getAvailableMeetingRooms(?,?,?,?)");
			stmt.setString(1,strStartDateTime);
			stmt.setString(2,strStartDateTime);
			stmt.setInt(3, capacity);
			stmt.setString(4, type);

			resultSet = stmt.executeQuery();
			meetingRooms = new ArrayList<MeetingRoom>();
			while(resultSet.next()) {
				meetingRooms.add(
						new MeetingRoom(
								resultSet.getInt(1),
								resultSet.getString(2),
								resultSet.getInt(3),
								getMeetingRoomAmenityByMeetingRoomId(resultSet.getInt(1)),
								resultSet.getInt(4),
								resultSet.getFloat(5),
								resultSet.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return meetingRooms;
	}
	/**
	 * @author ShubhraBhuniaGhosh
	 * @throws SlotNotAvailableException 
	 */
	public boolean checkMeetingSlotIsFreeByMeetingRoomId(int meetingRoomId) throws SlotNotAvailableException {
		int notAvalable = 0;
		if(notAvalable == 1)
			throw new SlotNotAvailableException();
		return true;
	}
	/**
	 * 
	 * @author ShubhraBhuniaGhosh
	 */
	public boolean checkMeetingNameAlreadyExists(String meetingName) {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = con.prepareCall("call sp_CheckIfMeetingTitleAlreadyExists(?)");
			stmt.setString(1, meetingName);
			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return false;	
	}

	/**
	 * @author ShubhraBhuniaGhosh
	 * @throws NotEnoughSeatsException 
	 */
	public boolean checkSeatCapacityByMeetingRoomId(int MeetingRoomId,int noOfAttendees) throws NotEnoughSeatsException {

		ResultSet resultSet = null;
		CallableStatement stmt = null;

		try {
			stmt = con.prepareCall("call sp_getSeatingCapacityInMeetingRoom(?)");
			stmt.setInt(1, MeetingRoomId);

			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				if(resultSet.getInt(1)<noOfAttendees) {
					throw new NotEnoughSeatsException();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * @author ShubhraBhuniaGhosh 
	 */
	public int insertValueOfMeeting(Meeting bookedMeeting, int managerId, int meetingRoomId) throws MeetingTitleInvalidException{
		CallableStatement stmt  = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strStartDateTime = sdf.format(bookedMeeting.getStartDateTime().getTime());
		String strEndDateTime = sdf.format(bookedMeeting.getStartDateTime().getTime());
		int meetingId = -1;
		try {
			stmt  = con.prepareCall("call sp_InsertIntoMeeting(?,?,?,?,?,?)");	
			stmt.setString(1, bookedMeeting.getMeetingTitle());
			stmt.setInt(2, managerId);			
			stmt.setString(3, strStartDateTime);
			stmt.setString(4, strEndDateTime);
			stmt.setString(5, bookedMeeting.getMeetingType());
			stmt.setInt(6, meetingRoomId);

			rs = stmt.executeQuery();
			
			if(rs.next()){
				meetingId = rs.getInt(1);
			}
			
		}catch (SQLIntegrityConstraintViolationException e) {
			throw new MeetingTitleInvalidException();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return meetingId;	
	}
	/**
	 * @author ShubhraBhuniaGhosh
	 * @throws SomethingWentWrongException 
	 */
	public int addAttendees(List<User> attendees,int meetingId) throws SomethingWentWrongException {
		int numberOfRowsUpdated = 0;
		for(User attendee:attendees) {
			numberOfRowsUpdated+=addAttendeeByUserIdAndMeetingId(attendee.getEmail(),meetingId);
		}
		return numberOfRowsUpdated;
	}
	/**sp_AddAttendeeByUserEmailIdAndMeetingId
	 * @author ShubhraBhuniaGhosh
	 * @param emailId
	 * @param meetingIdGotFromInsertValueInMeeting
	 * @return
	 * @throws SomethingWentWrongException 
	 */
	private int addAttendeeByUserIdAndMeetingId(String emailId,int meetingId) throws SomethingWentWrongException{
		CallableStatement stmt  = null;
		int noOfRowsUpdated = -1;
		try {
			stmt  = con.prepareCall("call sp_AddAttendeeByUserEmailIdAndMeetingId(?,?)");	
			stmt.setString(1, emailId);
			stmt.setInt(2, meetingId);

			noOfRowsUpdated = stmt.executeUpdate();
		}catch (SQLIntegrityConstraintViolationException e) {
			throw new SomethingWentWrongException();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try
			{	
				if(stmt != null)
				{
					stmt.close();
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return noOfRowsUpdated;		
	}


	public List<String> getMeetingRoomAmenityByMeetingRoomId(int meetingRoomId) {
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		ArrayList<String> amenities = null;

		try {
			stmt = con.prepareCall("call sp_getAmenitiesInMeetingRoom(?)");
			stmt.setInt(1, meetingRoomId);

			amenities = new ArrayList<String>();

			resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				amenities.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return amenities;
	}

	public static void main(String[] args) throws MeetingTitleInvalidException, SomethingWentWrongException, SQLException {

//		List<MeetingRoom> rooms = new MeetingDbDaoImpl().getMeetingRooms(Calendar.getInstance(), Calendar.getInstance(), "BUSINESS", 2);
//
//		Meeting bookedMeeting = new Meeting("TestMain2", Calendar.getInstance(), Calendar.getInstance().add(Calendar.HOUR, 3), "BUSINESS");
//		bookedMeeting.setEndDateTime(Calendar.getInstance());
//		int meetingId = new MeetingDbDaoImpl().insertValueOfMeeting(bookedMeeting, 3, rooms.get(0).getMeetingRoomId());
//		System.out.println(new MeetingDbDaoImpl().addAttendeeByUserIdAndMeetingId("sakshi.kumar@hscc.co.in", meetingId));

	}
}
