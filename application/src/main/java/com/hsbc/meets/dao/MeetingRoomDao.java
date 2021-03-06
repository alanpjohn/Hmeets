package com.hsbc.meets.dao;

import java.util.List;

import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.exception.MeetingRoomAlreadyExistsException;
import com.hsbc.meets.exception.MeetingRoomAmenitiesInvalidException;
import com.hsbc.meets.exception.MeetingRoomDoesNotExistsException;

/**
 * This interface declare all the methods to be implemented in meeting room Dao classes.
 * 
 * @author ShubhraBhuniaGhosh
 * @author alan
 * @author pavleen
 *
 */
public interface MeetingRoomDao {
	
	public int addMeetingRoom(MeetingRoom meetingRoom) throws MeetingRoomAlreadyExistsException;
		
	public List<MeetingRoom> showAllMeetingRooms();
	
	/**
	 * This method updates the meeting room details in the database using meetingRoomId.
	 * If no data with the  given meetingRoomId is found then the method throws 
	 * {@link MeetingRoomDoesNotExistsException}. In case it there is an unexpected exception it returns -1.
	 * 
	 * @param newMeetingRoom
	 * @return no of rows affected
	 * @throws MeetingRoomDoesNotExistsException
	 */
	public int updateMeetingRoomById(MeetingRoom newMeetingRoom) throws MeetingRoomDoesNotExistsException;
	
	/**
	 * This methods deletes all the existing amenities present for the respected Meeting Room from the database. 
	 * In case it there is an unexpected exception it returns -1.
	 * 
	 * @param meetingRoomId
	 * @return no of rows affected
	 */
	public int deleteAmenitiesByMeetingRoomById(int meetingRoomId);
	
	/**
	 * This method gets amenity id or the respective amenity name. In case no such amenity exist in the database 
	 * it throws {@link MeetingRoomAmenitiesInvalidException}.
	 * In case it there is an unexpected event it returns -1.
	 * 
	 * @param amenityName
	 * @return no of rows affected
	 * @throws MeetingRoomAmenitiesInvalidException
	 */
	public int getAmenityIdByAmenityName(String amenityName) throws MeetingRoomAmenitiesInvalidException;
	
	/**
	 * This method inserts amenity using amenity id and meeting room id. it calls 
	 * {@link #getAmenityIdByAmenityName(String amenityName) getAmenityIdByAmenityName} to get the amenity id.
	 * In case it there is an unexpected event it returns -1.
	 * 
	 * @param meetingRoomId
	 * @param amenities
	 * @return no of rows affected
	 * @throws MeetingRoomAmenitiesInvalidException
	 */
	public int insertAmenitiesInAmenityMeetingRoomById(int meetingRoomId, List<String> amenities) throws MeetingRoomAmenitiesInvalidException;
	
	/**
	 * This method checks if meeting room name already exists in the database except in that data itself.
	 * 
	 * @param meetingRoomName
	 * @param meetingRoomId
	 * @return if meeting room name already exists
	 * @throws MeetingRoomAlreadyExistException
	 */
	public boolean checkMeetingRoomNameAlreadyExists(String meetingRoomName,int meetingRoomId) throws MeetingRoomAlreadyExistsException;
	
	/**
	 * This method returns a list of all amenities present in amenity-table in database.
	 * 
	 * @return list of all amenities
	 */
	public List<String> getAllAmenities();
	
	public MeetingRoom getMeetingRoomWithoutAmenities(int meetingRoomId) throws MeetingRoomDoesNotExistsException;
}