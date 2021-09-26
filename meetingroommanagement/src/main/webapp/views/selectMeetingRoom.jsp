<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="e"%>
<%-- @author shalaka --%>

<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.hsbc.meets.entity.MeetingRoom" %>

<%
	List<MeetingRoom> rooms = (List<MeetingRoom>)request.getAttribute("elist");
	pageContext.setAttribute("rooms", rooms);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Welcome manager</h1>
	<h3>Enter meeting details</h3>
	
	hello selectmeeting room .jsp<br>
	
	<form action="SelectMeetingRoomController" method="post">
	<table border=1 >
	
	<tr>
	<td><b>Choose</b></td>
	<td><b>Room name</b></td>
	<td><b>Room capacity</b></td>
	<td><b>Room perhourcost</b></td>
	<td><b>Room rating</b></td>
	<td><b>Room number of feedbacks</b></td>
	<td><b>Amenities</b></td>
	</tr>
	
	<e:forEach items="${rooms}" var="room">
            <e:set var="eid" value="${room.meetingRoomId}" />
        		<tr>
			<td><input type="radio" id="html" name="roomid" value="${room.meetingRoomId}"></td>
			<td >${room.meetingRoomName}</td>
			<td >${room.seatingCapacity}</td>
			<td >${room.rating}</td>
			<td >${room.noOfFeedbacks }</td>
			<td >${room.amenities}</td>
			
			</tr>
	</e:forEach>
	
	</table>
	<br><br>
	<input type="submit" name="submit">
	</form>
</body>
</html>