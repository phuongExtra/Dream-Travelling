<%-- 
    Document   : tourSearch
    Created on : Jun 9, 2020, 1:38:59 PM
    Author     : nhatp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Discover Sites</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container">
                <a class="navbar-brand" href="#">Dream Traveling</a>
                <c:if test="${sessionScope.USER.roleID == 1}">
                    <a href="CreateTourController?action=LoadImage" class="nav-link">Create Tour</a>
                </c:if>
                <c:if test="${sessionScope.USER.roleID == 2}">
                    <a href="cart.jsp" class="nav-link">My Cart
                        <c:if test="${sessionScope.CARR_SIZE != 0}">
                            (${sessionScope.CART_SIZE})
                        </c:if>
                    </a>
                </c:if>
                <div  id="navbarResponsive">
                    <c:if test="${sessionScope.USER != null}" var="checkSession">
                        <a class="nav-link" href="LogoutController">Logout</a>
                    </c:if>
                    <c:if test="${!checkSession}">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </c:if>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <h1 class="my-4">Dream Traveling </h1>
                    <div >
                        <form action="TourSearchController" method="POST">
                            <div class="form-group">
                                <label>Search</label>
                                <input type="text" class="form-control" name="search" value="${requestScope.SEARCH}"/></br>
                                <label>Start Date</label>

                                <input type="date" class="form-control" name="startDate" value="${requestScope.START_DATE}"/></br>
                                <label>End Date</label>
                                <input type="date" class="form-control" name="endDate" value="${requestScope.END_DATE}"/></br>
                                <label>Price</label>
                                <select name="price" class="form-control" >
                                    <c:forEach items="${requestScope.PRICE_TAG}" var="price">
                                        <option  value="${price}" <c:if test="${price == requestScope.PRICE}">selected="selected"</c:if>>
                                            ${price}
                                        </option>
                                    </c:forEach>
                                </select></br>
                                <input type="hidden" name="page" value="1"/>
                                <input type="submit" class="btn btn-primary" value="Search"/></br>
                            </div>
                        </form>
                    </div>

                </div>
                <div class="col-lg-9">

                    <div id="carouselIndicators" class="carousel slide my-4" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselIndicators" data-slide-to="0" class="active"></li>
                            <li data-target="#carouselIndicators" data-slide-to="1"></li>
                            <li data-target="#carouselIndicators" data-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner" role="listbox">
                            <div class="carousel-item active">
                                <img class="d-block " height="350" width="900" src="img\greece.png" alt="First slide">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block " height="350" width="900" src="img\thai.png" alt="Second slide">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block" height="350" width="900" src="img\australia.png" alt="Third slide">
                            </div>
                        </div>
                        <a class="carousel-control-prev" href="#carouselIndicators" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselIndicators" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                    <div class="row">
                        <c:forEach items="${requestScope.TOUR_LIST}" var="list">

                            <div class="col-lg-4 col-md-6 mb-4">

                                <div class="card h-100">
                                    <a href="#"><img class="card-img-top" src="${list.img}"  height="150"></a>
                                    <form action="BookingController" method="POST">
                                        <div class="card-body">
                                            <h4 class="card-title">
                                                <a href="#">${list.tourName}</a>
                                            </h4>
                                            Destination: ${list.destination}</br>
                                            <p name="fromDate" value="${list.fromDate}">Departure date: ${list.fromDate}</p></br>
                                            <p name="toDate" value="${list.toDate}">End date: ${list.toDate}</p></br>
                                            Total slot(s): ${list.quota}</br>
                                            Available slot(s): ${list.availableSlots}</br>
                                            Select slot(s):
                                            <select name="amount">
                                                <c:forEach begin="1" end="${list.availableSlots}" var="amo">
                                                    <option value="${amo}" >${amo}</option>
                                                </c:forEach>
                                            </select>
                                            <h5 name="price" value="${list.price}">$${list.price}</h5>
                                        </div>
                                        <input type="hidden" name="tourID" value="${list.tourID}"/>
                                        <input type="hidden" name="search" value="${requestScope.SEARCH}"/>
                                        <input type="hidden" name="startDate" value="${requestScope.START_DATE}"/>
                                        <input type="hidden" name="endDate" value="${requestScope.END_DATE}"/>
                                        <input type="hidden" name="price" value="${requestScope.PRICE}"/>
                                        <div class="card-footer">
                                            <c:if test="${list.statusID == 1}">
                                                <c:if test="${list.availableSlots == 0}" var="checkEmpty">
                                                    <strong> Out of slot!</strong>
                                                </c:if>
                                                <c:if test="${!checkEmpty}">
                                                    <c:if test="${sessionScope.USER == null}" var="checkSession">
                                                        <a class="btn btn-primary" href="login.jsp">Login to book</a>
                                                    </c:if>
                                                    <c:if test="${sessionScope.USER.roleID != 1}">
                                                        <c:if test="${!checkSession}">
                                                            <input type="submit" name="action" class="btn btn-primary" value="Booking"/>
                                                        </c:if>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${list.statusID == 2}">
                                                <strong>This tour is no longer available</strong>
                                            </c:if>


                                        </div>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <c:if test="${ not empty requestScope.TOUR_LIST}">
                        <form  action="TourSearchController" method="POST">

                            <c:if test="${requestScope.PAGE != 1}">
                                <div class="info-prev" style="float:  left">
                                    <input type="hidden" name="page" value="${requestScope.PAGE}"/>
                                    <input type="hidden" name="search" value="${requestScope.SEARCH}"/>
                                    <input type="hidden" name="price" value="${requestScope.PRICE}"/>
                                    <input type="hidden" name="startDate" value="${requestScope.START_DATE}"/>
                                    <input type="hidden" name="endDate" value="${requestScope.END_DATE}"/>
                                    <input class="btn btn-outline-primary" type="submit" name="action" value="Previous Page"/>
                                </div>
                            </c:if>

                            <c:if test="${requestScope.PAGE != requestScope.TPAGE }">
                                <div class="info-next" style="float: right">
                                    <input type="hidden" name="page" value="${requestScope.PAGE}"/>
                                    <input type="hidden" name="search" value="${requestScope.SEARCH}"/>
                                    <input type="hidden" name="price" value="${requestScope.PRICE}"/>
                                    <input type="hidden" name="startDate" value="${requestScope.START_DATE}"/>
                                    <input type="hidden" name="endDate" value="${requestScope.END_DATE}"/>
                                    <input class="btn btn-outline-primary" type="submit" name="action" value="Next Page"/>
                                </div>
                            </c:if>
                        </form>
                    </c:if>


                </div>

            </div>

        </div>
    </body>
    <script src="jquery/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
</html>
