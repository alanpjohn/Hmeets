/**
 * 
 */
package com.hsbc.meets.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hsbc.meets.entity.Meeting;
import com.hsbc.meets.entity.User;
import com.hsbc.meets.factory.FeedBackFactory;
import com.hsbc.meets.service.FeedBackService;
import com.hsbc.meets.util.Role;

/**
 * @author alan
 *
 */

@WebServlet("/manager")
public class ManagerController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User currentUser = (User) req.getSession().getAttribute("user");
		if(currentUser == null || currentUser.getRole() != Role.MANAGER) {
			resp.sendRedirect("/meetingroommanagement/login");
			return;
		}
		
		List<Meeting> upcoming = new ArrayList<Meeting>();
		List<Meeting> recent = new ArrayList<Meeting>();
		
		FeedBackService fbs = FeedBackFactory.getFeedBackService();
		try {
			upcoming= fbs.getUpcomingMeeting(currentUser.getEmail());
			recent = fbs.getRecentMeeting(currentUser.getEmail());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		req.setAttribute("upcoming", upcoming);
		req.setAttribute("recent", recent);
		
		req.getRequestDispatcher("/views/manager.jsp").forward(req, resp);
		
	}
}

