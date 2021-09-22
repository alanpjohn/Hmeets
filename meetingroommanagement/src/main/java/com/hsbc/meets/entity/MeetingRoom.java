package com.hsbc.meets.entity;

import java.util.Arrays;

/**
 * The is the entity class for the Meeting Room 
 * which is the basic logical component
 * of the application. 
 * 
 * @author PavleenKaur
 * @author ShubhraBhuniaGhosh
 *
 */

public class MeetingRoom {

	private int meetingRoomId;
	private String meetingRoomName;
	private int seatingCapacity;
	private float rating;
	private String[] Amenities;
	private int creditsPerHour;
	private int noOfFeedbacks;
	/*
	using the below data type only for implementing JDBC, 
	because my local DB has amenities in String Type : this needs to be deleted later
	when procedures/triggers will be implemented. 
    */
	String amenities; 
	// constructors

	public MeetingRoom(String meetingRoomName, int seatingCapacity, String[] amenities,
			int creditsPerHour, int rating, int noOfFeedbacks) {
		this.meetingRoomName = meetingRoomName;
		this.seatingCapacity = seatingCapacity;
		this.Amenities = amenities;
		this.creditsPerHour = creditsPerHour;
		this.rating = rating;
		this.noOfFeedbacks = noOfFeedbacks;
	}
	
	public MeetingRoom(int meetingRoomId, String meetingRoomName, int seatingCapacity, float rating, String[] amenities,
			int creditsPerHour, int noOfFeedbacks) {
		super();
		this.meetingRoomId = meetingRoomId;
		this.meetingRoomName = meetingRoomName;
		this.seatingCapacity = seatingCapacity;
		this.rating = rating;
		this.Amenities = amenities;
		this.creditsPerHour = creditsPerHour;
		this.noOfFeedbacks = noOfFeedbacks;
	}
	
	public MeetingRoom(int meetingRoomId, String meetingRoomName, int seatingCapacity,String[] amenities,
			int creditsPerHour) {
		super();
		this.meetingRoomId = meetingRoomId;
		this.meetingRoomName = meetingRoomName;
		this.seatingCapacity = seatingCapacity;
		this.rating = 0;
		this.Amenities = amenities;
		this.creditsPerHour = creditsPerHour;
		this.noOfFeedbacks =0;
		}
	/*
	using the below  constructor only for implementing JDBC(List all meeting rooms)
	because my local DB has amenities in String Type : this needs to be deleted later
	when procedures/triggers will be implemented. 
    */
		public MeetingRoom(int meetingRoomId, String meetingRoomName, int seatingCapacity, float rating, String amenities,
				int creditsPerHour, int noOfFeedbacks) {
			
			this.meetingRoomId = meetingRoomId;
			this.meetingRoomName = meetingRoomName;
			this.seatingCapacity = seatingCapacity;
			this.rating = rating;
			this.amenities = amenities;
			this.creditsPerHour = creditsPerHour;
			this.noOfFeedbacks = noOfFeedbacks;
		
	}
		
		/*
		using the below  constructor only for implementing JDBC(Creating a new Meeting room)
		because my local DB has amenities in String Type : this needs to be deleted later
		when procedures/triggers will be implemented. 
	    */
		public MeetingRoom(int meetingRoomId, String meetingRoomName, int seatingCapacity, String amenities,
				int creditsPerHour) {
			super();
			this.meetingRoomId = meetingRoomId;
			this.meetingRoomName = meetingRoomName;
			this.seatingCapacity = seatingCapacity;
			this.rating = 0;
			this.amenities = amenities;
			this.creditsPerHour = creditsPerHour;
			this.noOfFeedbacks =0;
			}

	// getters & setters

	/*
	 * the following getter/setter for String amenities, needs to be deleted.  
	 */
	
	public String getamenities() {
			return amenities;
		}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}
	
	/*
	 * delete the above getter/setter later 
	 */
	public int getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(int meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}
	
	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String[] getAmenities() {
		return Amenities;
	}

	public void setAmenities(String[] amenities) {
		Amenities = amenities;
	}

	public int getCreditsPerHour() {
		return creditsPerHour;
	}

	public void setCreditsPerHour(int creditsPerHour) {
		this.creditsPerHour = creditsPerHour;
	}

	public int getNoOfFeedbacks() {
		return noOfFeedbacks;
	}

	public void setNoOfFeedbacks(int noOfFeedbacks) {
		this.noOfFeedbacks = noOfFeedbacks;
	}

	@Override
	public String toString() {
		return "MeetingRoom [meetingRoomId=" + meetingRoomId + ", meetingRoomName=" + meetingRoomName
				+ ", seatingCapacity=" + seatingCapacity + ", rating=" + rating + ", Amenities="
				+ Arrays.toString(Amenities) + ", creditsPerHour=" + creditsPerHour + ", noOfFeedbacks=" + noOfFeedbacks
				+ "]";
	}

	

	

}
