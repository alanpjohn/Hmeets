package com.hsbc.meets.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.meets.factory.MeetingServiceFactory;

/**
 * @author alan
 *
 */
@WebServlet("/meeting/*")
public class MeetingBookingController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * meeting/name=?
		 * UserService.searchUser
		 */

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * meeting name, meeting type, starttime, endtime 
		 * get the available rooms from the service and return 
		 * to selectMeetingRoom
		 */
		
		/**
		 * meeting room 
		 * dispatch selectAndAddMembers
		 * 
		 */
		
		/**
		 * members list
		 * service
		 */
		
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		if (req.getPathInfo() != null) {
			String path = req.getPathInfo().substring(1);
			
			if (path.equals("AvailableRooms")) {
				
		
			}
			
			else if(path.equals("select")) {
				
			}
			
			else {
					
				//members list	
			}
		super.doPost(req, resp);
	}
}
	
}
