package com.hsbc.meets.factory;

import com.hsbc.meets.dao.MeetingDao;
import com.hsbc.meets.dao.impl.MeetingDbDaoImpl;
import com.hsbc.meets.entity.User;
import com.hsbc.meets.service.MeetingService;
import com.hsbc.meets.service.impl.MeetingServiceImpl;
/**
 * This class is to create an instance of MeetingDao implementing classes.
 * @author ShubhraBhuniaGhosh
 *
 */
public abstract class MeetingFactory {
	/**
	 * @author ShubhraBhuniaGhosh
	 * @return object of {@link MeetingDbDaoImpl}
	 */
	public static MeetingDao getMeetingDaoObject() {
		return new MeetingDbDaoImpl();
	}
	
	/**
	 * @author ShubhraBhuniaGhosh
	 * @return object of {@link MeetingServiceImpl}
	 */
	public static MeetingService getMeetingServiceObject(User manager) {
		return new MeetingServiceImpl(manager);
	}
}
