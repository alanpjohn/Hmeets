package com.hsbc.meets.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hsbc.meets.dao.MeetingRoomDao;
import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.exception.MeetingRoomAlreadyExistsException;
import com.hsbc.meets.exception.MeetingRoomAmenitiesInvalidException;
import com.hsbc.meets.exception.MeetingRoomDoesNotExistsException;
import com.hsbc.meets.factory.LoggerFactory;
import com.hsbc.meets.util.Connectivity;

/**
 *This class implements all the methods declared in {@link MeetingRoomDao}
 * @author ShubhraBhuniaGhosh
 *
 */
public class MeetingRoomDbDaoImpl implements MeetingRoomDao{

	private static final String SELECT_MEETING_ROOM_FROM_ID = "CALL sp_GetMeetingRoom(?)";
	private static final String TO_GET_ALL_AMENITIES_SQL = "call sp_GetAmenityName()";
	private static final String GET_AMENITY_ID_BY_AMENITY_NAME = "call sp_GetAmenityIdByAmenityName(?);";
	private static final String DELETE_AMENITIES_BY_MEETING_ROOM_ID_SQL = "call sp_DeleteAmenitiesByMeetingRoomId(?)";
	private static final String SELECT_MEETINGROOM_ID_FROM_MEETINGROOM_BY_NAME_SQL = "call sp_SelectMeetingRoomIdFromMeetingRoomByName(?)";
	private static final String INSERT_AMENITY_IN_MEETING_ROOM_AMENITIES_SQL = "call sp_InsertAmenityInMeetingRoomAmenities(?,?)";
	private static final String UPDATE_MEETING_ROOM_BY_ID_SQL = "call sp_UpdateMeetingRoomById(?,?,?)";
	private static final String INSERT_MEETING_ROOM_SQL = "call sp_AddMeetingRoom(?,?)";
	private static final String SELECT_ALL_ROOMS_SQL = "CALL sp_ShowAllMeetingRooms();";
	
	private Connection con;
	private Logger logger;
	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public MeetingRoomDbDaoImpl(){
		logger = LoggerFactory.getLogger();
		con = Connectivity.getConnection();
	}
	
	/**
	 * @author ShubhraBhuniaGhosh
	 */

	public int addMeetingRoom(MeetingRoom meetingRoom) throws MeetingRoomAlreadyExistsException{
		ResultSet rs = null;
		try	
		{
			CallableStatement stmt  = con.prepareCall(INSERT_MEETING_ROOM_SQL);	
			stmt.setString(1, meetingRoom.getMeetingRoomName());
			stmt.setInt(2, meetingRoom.getSeatingCapacity());
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLIntegrityConstraintViolationException se) {
			logger.log(Level.SEVERE,se.getMessage(),se);
			throw new MeetingRoomAlreadyExistsException();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE,e.getMessage(),e);
			}
		}
		return -1;
	}
	
	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public int updateMeetingRoomById(MeetingRoom newMeetingRoom) throws MeetingRoomDoesNotExistsException {
		CallableStatement stmt = null;
		int numberOfRowsUpdate  = -1;
		try {
			stmt = con.prepareCall(UPDATE_MEETING_ROOM_BY_ID_SQL);
			stmt.setString(1, newMeetingRoom.getMeetingRoomName());
			stmt.setInt(2, newMeetingRoom.getSeatingCapacity());
			stmt.setInt(3, newMeetingRoom.getMeetingRoomId());
			numberOfRowsUpdate = stmt.executeUpdate();
			if(numberOfRowsUpdate==0) {
				throw new MeetingRoomDoesNotExistsException();
			}
		}catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}finally
		{
			try
			{
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
		return numberOfRowsUpdate;
	}

	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public int deleteAmenitiesByMeetingRoomById(int meetingRoomId) {
		CallableStatement stmt = null;
		int numberOfRowsUpdate  = -1;
		try {
			stmt = con.prepareCall(DELETE_AMENITIES_BY_MEETING_ROOM_ID_SQL);
			stmt.setInt(1,meetingRoomId);
			numberOfRowsUpdate = stmt.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}finally
		{
			try
			{
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
		return numberOfRowsUpdate;
	}
	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public int getAmenityIdByAmenityName(String amenityName) throws MeetingRoomAmenitiesInvalidException{
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		int amenityId = -1;
		try {
			stmt = con.prepareCall(GET_AMENITY_ID_BY_AMENITY_NAME);
			
			stmt.setString(1, amenityName.toLowerCase());
			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				amenityId = resultSet.getInt(1);
			}else {
				throw new MeetingRoomAmenitiesInvalidException();
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
		return amenityId;

	}
	
	/**
	 * @author alan
	 */
	public int insertAmenitiesInAmenityMeetingRoomById(int meetingRoomId, List<String> amenities) throws MeetingRoomAmenitiesInvalidException{
		int numberOfRowsUpdate  = 0;
		
		try (
			CallableStatement stmt = con.prepareCall(INSERT_AMENITY_IN_MEETING_ROOM_AMENITIES_SQL)
		){
			for(String amenity : amenities ) {
				stmt.setString(1, amenity);
				stmt.setInt(2,meetingRoomId);
				stmt.addBatch();
			}
			int[] updateCountArray = stmt.executeBatch();
			for(int x : updateCountArray) {
				numberOfRowsUpdate += x;
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
			throw new MeetingRoomAmenitiesInvalidException();
		}
		return numberOfRowsUpdate;
	}


	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public boolean checkMeetingRoomNameAlreadyExists(String meetingRoomName,int meetingRoomId) throws MeetingRoomAlreadyExistsException{
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = con.prepareCall(SELECT_MEETINGROOM_ID_FROM_MEETINGROOM_BY_NAME_SQL);
			stmt.setString(1, meetingRoomName);
			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				if(resultSet.getInt(1)!=meetingRoomId) {
					throw new MeetingRoomAlreadyExistsException();
				}
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
		return false;
	}

	/**
	 * @author ShubhraBhuniaGhosh
	 */
	public List<String> getAllAmenities(){
		List<String> amenities = null;
		ResultSet resultSet = null;
		CallableStatement stmt = null;
		try {
			stmt = con.prepareCall(TO_GET_ALL_AMENITIES_SQL);
			resultSet = stmt.executeQuery();
			amenities = new ArrayList<String>();
			while(resultSet.next()) {
				amenities.add(resultSet.getString(1));
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
		return amenities;
	}
	
	@Override
	public List<MeetingRoom> showAllMeetingRooms() {
		List<MeetingRoom> roomList = new ArrayList<>();
		CallableStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			stmt=con.prepareCall(SELECT_ALL_ROOMS_SQL); 
		    rs = stmt.executeQuery();
		    MeetingRoom room = null;
			while (rs.next()) {

				int roomId = rs.getInt(1);
				String roomName = rs.getString(2);
				int roomSeatingCapacity = rs.getInt(3);
				float roomRating = rs.getFloat(4);
				float roomPerHourCost = rs.getFloat(5);
				
				if(room == null) {
					
					room = new MeetingRoom(roomId, roomName, roomSeatingCapacity, roomRating, roomPerHourCost);
					room.addAmenity(rs.getString(6));
				}else {
					if(room.getMeetingRoomId() == roomId) {
						room.addAmenity(rs.getString(6));
					}else {
						roomList.add(room);		
						room = new MeetingRoom(roomId, roomName, roomSeatingCapacity, roomRating, roomPerHourCost);
						room.addAmenity(rs.getString(6));
					}
				}
			}
			roomList.add(room);
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		} finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE,e.getMessage(),e);
				}
            if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE,e.getMessage(),e);
				}
		}
		
		return roomList;
	}

	@Override
	public MeetingRoom getMeetingRoomWithoutAmenities(int meetingRoomId) throws MeetingRoomDoesNotExistsException{
		
		MeetingRoom room = null;
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = con.prepareCall(SELECT_MEETING_ROOM_FROM_ID);
			stmt.setInt(1, meetingRoomId);
			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				int roomId = resultSet.getInt(1);
				String roomName = resultSet.getString(2);
				int roomSeatingCapacity = resultSet.getInt(3);
				return new MeetingRoom(meetingRoomId,roomName, roomSeatingCapacity,new ArrayList<String>());
			}else {
				throw new MeetingRoomDoesNotExistsException();
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
		return room;
	}

}






