<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="e"%>
<html>
<body>
<%@page import="java.util.*, com.hsbc.meets.entity.*" %>
<%
    List<MeetingRoom> room = (List<MeetingRoom>)request.getAttribute("elist");
    pageContext.setAttribute("room", room);
    
%>

<h2>All Available Rooms</h2>
    <table border="1">
        <tr>
            <th>Room Name</th>
            <th>Rating</th>
            <th>Amenities Available</th>
            <th>Credits Per Hour</th>
            <th>Number of Feedbacks Received</th>
        </tr>
        <e:forEach items="${room}" var="room">
            <e:set var="eid" value="${room.meetingRoomId}" />
            <tr>
               
                <td>${room.meetingRoomName}</td>
                <td>${room.rating}</td>
  				<td>${room.amenities}</td>
                <td>${room.creditsPerHour}</td>
                <td>${room.noOfFeedbacks}</td>                

                <td><a href="/admin?option=edit" >${room.meetingRoomName}Edit Room Details</a></td>

            </tr>
        </e:forEach>
    </table>
</body>
</html>