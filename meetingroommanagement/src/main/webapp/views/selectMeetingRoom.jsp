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
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Available Rooms</title>
</head>

<body id="page-container">
    <header>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg bg-dark" style="height: 8vh">
            <div class="container">
                <!-- Logo -->
                <img src="images/logo.png" height="30" alt="" />
                <h4 class="text-white ms-4 my-auto">HMeets</h4>
                <!-- Logo -->

                <!-- Menu button -->
                <button class="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarButtons" aria-controls="navbarButtons" aria-expanded="true" aria-label="Toggle navigation">
                    <img src="images/icon_menu.png" height="22" alt="" class="me-1" />
                </button>
                <!-- Menu button -->

                <!-- Nav Items -->
                <div class="collapse navbar-collapse align-items-center" id="navbarButtons">
                    <div class="me-auto"></div>
                    <!-- <small class="text-info me-4">Link</small> -->
                    <ul class="align-nav-item">
                        <img src="images/icon_user.png" height="16" alt="" class="me-1" />
                        <small class="text-white me-4">Hi! Amit Kumar</small>
                    </ul>
                    <ul class="align-nav-item">
                        <a href="">
                            <button type="button" class="btn btn-outline-info" data-mdb-ripple-color="dark">
                                Logout
                            </button>
                        </a>
                    </ul>
                    <!-- </div> -->
                </div>
                <!-- Nav Items -->
            </div>
        </nav>
        <!-- Navbar -->
    </header>

    <main id="content-wrap">
        <div class="container-fluid h-custom">
            <div class="row">
                <section class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
                    <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
                        <p class="lead mx-3">Available Meeting Rooms</p>
                    </div>
                    <!-- Meeting Rooms -->
                    <e:forEach items="${rooms}" var="room">
                        <e:set var="eid" value="${room.meetingRoomId}" />
                        <div class="card-container">
                            <div class="card-body rounded-4">
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <h5 class="card-title">${meeting.meetingRoomName}</h5>
                                    </div>
                                    <div class="col-md-3">
                                        <h6 class="card-subtitle text-black">Capacity</h6>
                                        <p>${room.seatingCapacity} members</p>
                                    </div>
                                    <div class="col-md-3">
                                        <h6 class="card-subtitle text-black">Rating</h6>
                                        <p>${room.rating} stars</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <h6 class="card-subtitle text-black">Amenities: </h6>
                                        <p class="text-muted">${room.amenities}</p>
                                    </div>
                                    <div class="col-md-3">
                                        <h6 class="card-subtitle text-black">Per Hour Cost</h6>
                                        <p>${room.perHourCost} Credits</p>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn btn-primary">
                                            Select Room
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </e:forEach>
                    <!-- Meeting Rooms -->
                </section>
            </div>
        </div>
    </main>

    <!--Footer-->
    <footer id="footer">
        <div>
            <hr class="my-2">
        </div>
        <div class="footer-copyright d-flex align-items-center justify-content-center">
            � 2021 Copyright: HSCC Meettings
        </div>
    </footer>
    <!--/.Footer-->

    <!--scripts-->
    <script src="scripts.js"></script>
    <!--scripts-->
</body>

</html>