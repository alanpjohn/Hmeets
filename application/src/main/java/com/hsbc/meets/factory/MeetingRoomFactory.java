package com.hsbc.meets.factory;

import com.hsbc.meets.dao.MeetingRoomDao;
import com.hsbc.meets.dao.impl.MeetingRoomDbDaoImpl;
import com.hsbc.meets.service.impl.MeetingRoomServiceImpl;
/**
 * This class is intensity to make object of {@link MeetingRoomDbDaoImpl} class
 * @author ShubhraBhuniaGhosh
 *
 */
public abstract class MeetingRoomFactory {
	
	/**
	 * @author ShubhraBhuniaGhosh
	 * @return meeting room dao
	 */
	public static MeetingRoomDao getMeetingRoomDaoObject() {
		return (MeetingRoomDao) new MeetingRoomDbDaoImpl();
	}
	
	/**
	 * 
	 * @return meeting room service
	 */
	public static MeetingRoomServiceImpl getService() {
		
		MeetingRoomServiceImpl meetService = new MeetingRoomServiceImpl();
		return meetService; 
		
	}

}