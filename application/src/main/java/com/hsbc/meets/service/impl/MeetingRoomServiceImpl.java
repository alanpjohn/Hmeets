package com.hsbc.meets.service.impl;

import java.io.IOException;
import java.util.List;

import com.hsbc.meets.dao.MeetingRoomDao;
import com.hsbc.meets.dao.impl.MeetingRoomDbDaoImpl;
import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.exception.MeetingRoomAlreadyExistsException;
import com.hsbc.meets.exception.MeetingRoomAmenitiesInvalidException;
import com.hsbc.meets.exception.MeetingRoomDoesNotExistsException;
import com.hsbc.meets.exception.MeetingRoomNameInvalidException;
import com.hsbc.meets.exception.MeetingRoomSeatingCapacityInvalidException;
import com.hsbc.meets.factory.MeetingRoomFactory;
import com.hsbc.meets.service.MeetingRoomService;
import com.hsbc.meets.validation.MeetingRoomValidation;

public class MeetingRoomServiceImpl implements MeetingRoomService{
	MeetingRoomDbDaoImpl dao;

	public MeetingRoomServiceImpl(){
		dao = new MeetingRoomDbDaoImpl();
	}

	@Override
	public List<MeetingRoom> showAllMeetingRooms()
			throws IOException {
		
		List<MeetingRoom> elist = dao.showAllMeetingRooms();
		return elist;
	}

	@Override
	public void addMeetingRoom(String roomName , int roomCapacity , List<String> roomAmenities)
			throws IOException, MeetingRoomAlreadyExistsException, MeetingRoomAmenitiesInvalidException {

		int roomCredits = 0; 
		int creditsPerHour = 0;
 
		if(roomCapacity <=5)
			roomCredits = 0; 
		else if(roomCapacity >5 && roomCapacity <=10)
			roomCredits = 10; 
		else 
			roomCredits = 20; 
				
		
		creditsPerHour = roomCredits; 
		
		MeetingRoom room = new MeetingRoom(roomName, roomCapacity, roomAmenities);
		int roomId = dao.addMeetingRoom(room);
		dao.insertAmenitiesInAmenityMeetingRoomById(roomId, roomAmenities);
	}

	@Override
	public int editMeetingRoom(int meetingRoomId, String meetingRoomName, int seatingCapacity, List<String> amenities)
			throws MeetingRoomNameInvalidException, MeetingRoomSeatingCapacityInvalidException,
			MeetingRoomAmenitiesInvalidException, MeetingRoomDoesNotExistsException, MeetingRoomAlreadyExistsException {
		
		MeetingRoomDao dao = MeetingRoomFactory.getMeetingRoomDaoObject();
		int numberOfRowsUpdate = 0;
		MeetingRoomValidation validator = new MeetingRoomValidation(meetingRoomId,meetingRoomName,seatingCapacity,amenities); 
		MeetingRoom room = validator.getRoom();
		numberOfRowsUpdate += dao.updateMeetingRoomById(room);
		dao.deleteAmenitiesByMeetingRoomById(meetingRoomId);
		dao.insertAmenitiesInAmenityMeetingRoomById(meetingRoomId, amenities);
		
		return numberOfRowsUpdate;
	}

	@Override
	public MeetingRoom getMeetingRoom(int meetingRoomId) throws MeetingRoomDoesNotExistsException {
		MeetingRoomDao dao = MeetingRoomFactory.getMeetingRoomDaoObject();
		return dao.getMeetingRoomWithoutAmenities(meetingRoomId);
	}

	@Override
	public List<String> getAllAmenities() {
		MeetingRoomDao dao = MeetingRoomFactory.getMeetingRoomDaoObject();
		return dao.getAllAmenities();
	}

}
