package com.hsbc.meets.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.exception.MeetingRoomAlreadyExistsException;

public interface MeetingRoomService{
	
	public List<MeetingRoom> showAllMeetingRooms()
			throws IOException;
	
	public void addMeetingRoom(int roomId,String roomName , int capacity , String[] amenities)
			throws IOException, MeetingRoomAlreadyExistsException;

}
