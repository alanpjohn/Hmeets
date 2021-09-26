<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Add members</title>
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
        <div class="container-fluid">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-10 col-sm-8 col-md-9 col-lg-6 col-xl-5 offset-xl-1">
                    <!--Welcome Text-->
                    <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start my-4">
                        <h4 class="text-muted">Add Members to your meeting</h4><br>
                    </div>
                    <!--/ Welcome Text-->

                    <!--Search-->
                    <div class="input-group">
                        <div class="form-outline">
                            <input type="search" id="searchBar" placeholder="Member name" class="form-control" />
                            <label class="form-label" for="searchBar">Search</label>
                        </div>
                        <button type="button" class="btn btn-primary" onclick="search()">
                            <img src="images/icon_search.png" alt="search" style="width: 15px;">
                        </button>
                    </div>
                    <!--Search-->

                    <!--Search Result-->
                    <div class="col-md-6" id="searchResults"></div>
                    <!--Search Result-->

                </div>
                <!--Added members-->
                <div class="col-10 col-sm-8 col-md-9 col-lg-6 col-xl-5">
                    <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start my-4 mt-5">
                        <h4 class="text-muted">Added Members</h4><br>
                    </div>
                    <!--Added Members-->
                    <div class="col-md-8" id="membersAdded"></div>
                    <!--Added Members-->
                </div>
                <!--Added members-->
            </div>
        </div>
    </main>

    <!--Footer-->
    <footer id="footer">
        <div>
            <hr class="my-2">
        </div>
        <div class="footer-copyright d-flex align-items-center justify-content-center">
            © 2021 Copyright: HSCC Meettings
        </div>
    </footer>
    <!--/.Footer-->

    <!--scripts-->
    <script src="scripts.js"></script>
    <script>
        let capacity = 2;
        let searchedMembers = [];
        let addedMembers = [];

        function search() {
            // temp JSON array
            let resultSet = [{
                "ID": 1,
                "Name": "Amit",
                "Email": "amit.kumar@hscc.co.in"
            }, {
                "ID": 2,
                "Name": "Raj",
                "Email": "raj.kumar@hscc.co.in"
            }, {
                "ID": 3,
                "Name": "Sachin",
                "Email": "sachin.kumar@hscc.co.in"
            }, {
                "ID": 4,
                "Name": "Rahul",
                "Email": "rahul.kumar@hscc.co.in"
            }];
            // temp JSON array

            let resultsDiv = document.getElementById('searchResults');
            resultsDiv.innerHTML = `<h6>searching...</h6>`;

            let searchString = document.getElementById('searchBar').value;

            let xhttp = new XMLHttpRequest();
            let method = "GET";
            let url = "http://localhost:8080/meetingroommanagement/?search=" + searchString;
            xhttp.open(method, url);
            // xhttp.send();

            xhttp.onload = function() {
                let resultSet = JSON.parse(xhttp.responseText);
                if (resultSet.length > 0) {
                    searchedMembers = resultSet;
                    let htmlString = "";
                    searchedMembers.forEach(user => {
                        htmlString += `<a href="" style="text-decoration: none;">
                                            <div id=${user.ID} onclick="validateSelectedUser(event, this.id)" class="user-card-body px-4 p-0">
                                                <h6 class="user-name-searched text-bold pt-1">${user.Name}</h6>
                                                <p class="user-name-searched pb-1">${user.Email}</p>
                                            </div>
                                        </a>`;
                    });
                    resultsDiv.innerHTML = htmlString;
                } else {
                    resultsDiv.innerHTML = `<h6>0 results</h6>`;
                }
            }

            if (resultSet.length > 0) {
                searchedMembers = resultSet;
                let htmlString = "";
                resultSet.forEach(user => {
                    htmlString += `<a href="" style="text-decoration: none;">
                                        <div id=${user.ID} onclick="validateSelectedUser(event, this.id)" class="user-card-body px-4 p-0">
                                            <h6 class="user-name-searched text-bold pt-1">${user.Name}</h6>
                                            <p class="user-name-searched pb-1">${user.Email}</p>
                                        </div>
                                    </a>`;
                });
                resultsDiv.innerHTML = htmlString;
            } else {
                resultsDiv.innerHTML = `<h6>0 results</h6>`;
            }
        }

        function validateSelectedUser(event, id) {
            event.preventDefault();
            let resultsDiv = document.getElementById('searchResults');
            resultsDiv.innerHTML = "";

            if (capacity > addedMembers.length) {
                let canAdd = addedMembers.find(member => member.ID == id);
                if (canAdd == undefined) {
                    let addThisMember = searchedMembers.find(member => member.ID == id);
                    addedMembers.push(addThisMember);
                    addToMeeting(addThisMember);
                } else {
                    resultsDiv.innerHTML = `<h6 style="color:red;">Member already added</h6>`;
                }
            } else {
                resultsDiv.innerHTML = `<h6 style="color:red;">Meeting room capacity is full</h6>`;
            }
        }

        function addToMeeting(member) {
            let addedMemberHtml = "";
            addedMemberHtml = `<div class="user-card-body px-4 py-2">
                            <h6 class="user-name-searched text-bold pt-1">${member.Name}</h6>
                            <p class="user-name-searched pb-1">${member.Email}</p>
                        </div>
                        <hr>`;
            document.getElementById("membersAdded").insertAdjacentHTML('beforeend', addedMemberHtml);
        }
    </script>
    <!--scripts-->
</body>

</html>