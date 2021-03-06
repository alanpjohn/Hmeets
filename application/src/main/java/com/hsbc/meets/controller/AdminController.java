/**
 * 
 */
package com.hsbc.meets.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.meets.entity.MeetingRoom;
import com.hsbc.meets.entity.User;
import com.hsbc.meets.exception.MeetingRoomDoesNotExistsException;
import com.hsbc.meets.factory.LoggerFactory;
import com.hsbc.meets.factory.MeetingRoomFactory;
import com.hsbc.meets.service.MeetingRoomService;
import com.hsbc.meets.util.Role;

/**
 * Handles admin page and redirects to other pages
 * 
 * @author alan
 *
 */
@WebServlet("/admin")
public class AdminController extends HttpServlet {
	
	private Logger logger = LoggerFactory.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User currentUser = (User) req.getSession().getAttribute("user");
		if(currentUser == null || currentUser.getRole() != Role.ADMIN) {
			resp.sendRedirect("/meetingroommanagement/login");
			return;
		}
		
		resp.setContentType("application/json;charset=UTF-8");
		MeetingRoomService meetingRoomService= MeetingRoomFactory.getService();
		String option = req.getParameter("option");
		
		if (option == null) {
			req.getRequestDispatcher("/views/admin.jsp").forward(req, resp);
		} else if( option.equals("list")) {
			req.getRequestDispatcher("/meetingroom").forward(req, resp);
		} else if( option.equals("create") ) {
			List<String> amenities = meetingRoomService.getAllAmenities();
			req.setAttribute("amenities", amenities);
			req.getRequestDispatcher("/views/addNewRoom.jsp").forward(req, resp);
		} else if( option.equals("edit") ) {
			int RoomId = Integer.parseInt(req.getParameter("room"));
			try {
				MeetingRoom room = meetingRoomService.getMeetingRoom(RoomId);
				req.setAttribute("room",room);
				List<String> amenities = meetingRoomService.getAllAmenities();
				req.setAttribute("amenities", amenities);
				
				req.getRequestDispatcher("/views/editRoom.jsp").forward(req, resp);
			} catch (MeetingRoomDoesNotExistsException e) {
				logger.log(Level.SEVERE,"Meeting does not exists",e);
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		
	}
	
}
