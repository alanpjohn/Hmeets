package com.hsbc.meets.service;

import java.io.IOException;
import java.util.List;

import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.exception.MeetingRoomAlreadyExistsException;
import com.hsbc.meets.exception.MeetingRoomAmenitiesInvalidException;
import com.hsbc.meets.exception.MeetingRoomDoesNotExistsException;
import com.hsbc.meets.exception.MeetingRoomNameInvalidException;
import com.hsbc.meets.exception.MeetingRoomSeatingCapacityInvalidException;

public interface MeetingRoomService{
	
	public List<MeetingRoom> showAllMeetingRooms()
			throws IOException;
	
	public List<String> getAllAmenities();
	
	public void addMeetingRoom(String roomName , int capacity , List<String> amenities)
			throws IOException, MeetingRoomAlreadyExistsException, MeetingRoomAmenitiesInvalidException;

	/**
	 * This method gets instance of {@link com.hsbc.meets.validation.MeetingRoomValidation#validateMeetingRoom(com.hsbc.meets.dao.MeetingRoomDao, int, String, int, List) validateMeetingRoom}. 
	 * It checks if all the meeting room details are according to validation rules using {@link validateMeetingRoom}.
	 * If all the meeting room details are according to validation rules then we edit the meeting details to new details.
	 * 
	 * @author ShubhraBhuniaGhosh
	 * @param meetingRoomId
	 * @param meetingRoomName
	 * @param seatingCapacity
	 * @param amenities
	 * @return no of rows updated
	 * @throws MeetingRoomNameInvalidException
	 * @throws MeetingRoomSeatingCapacityInalidException
	 * @throws MeetingRoomAmenitiesInvalidException
	 * @throws MeetingRoomDoesNotExistsException
	 * @throws MeetingRoomNameAlreadyExistException
	 */
	public int editMeetingRoom(int meetingRoomId, String meetingRoomName, int seatingCapacity, List<String> amenities) throws MeetingRoomNameInvalidException, MeetingRoomSeatingCapacityInvalidException, MeetingRoomAmenitiesInvalidException, MeetingRoomDoesNotExistsException, MeetingRoomAlreadyExistsException;

	public MeetingRoom getMeetingRoom(int meetingRoomId) throws MeetingRoomDoesNotExistsException;
}
