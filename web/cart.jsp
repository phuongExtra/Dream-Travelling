<%-- 
    Document   : tourBooking
    Created on : Jun 18, 2020, 12:40:43 PM
    Author     : nhatp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container">
                <a class="navbar-brand" href="TourSearchController">Dream Traveling</a>
                <a href="cart.jsp" class="nav-link">My Cart(${sessionScope.CART_SIZE})</a>
                <div  id="navbarResponsive">
                    <a class="nav-link" href="LogoutController">Logout</a>
                </div>
            </div>
        </nav>
        <c:if test="${not empty requestScope.SUCCESS}" var="checkSuccess">
            <div class="alert alert-success alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>${requestScope.SUCCESS}</strong> 
            </div>
        </c:if>
        <c:if test="${!checkSuccess}"></c:if>
        <c:if test="${not empty requestScope.ERROR}" var="checkError">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>${requestScope.ERROR}</strong> 
            </div>
        </c:if>
        <c:if test="${!checkError}"></c:if>
            <div>
                <form action="BookingController" method="POST">
                    <table class="table table-hover table-responsive-md">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Destination</th>
                                <th>Departing time</th>
                                <th>Finish time</th>
                                <th>Booked slot(s)</th>
                                <th>Price</th>
                                <th>Total</th>
                                <th>Delete</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${sessionScope.CART.cart}" var="cart" varStatus="counter">

                            <tr>
                                <td>${counter.count}</td>
                                <td>${cart.value.destination}</td>
                                <td>${cart.value.fromDate}</td>
                                <td>${cart.value.toDate}</td>
                                <td>
                                    <div class="form-group">
                                        <select class="form-control" name="amount${cart.value.tourID}">
                                            <c:forEach begin="1" end="${cart.value.available}" var="amo">
                                                <option value="${amo}"<c:if test="${cart.value.amount == amo}">selected="selected"</c:if>>
                                                    ${amo}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </td>
                                <td>$${cart.value.price}</td>
                                <td>$${cart.value.total}</td>
                                <td><a href="DeleteController?tourID=${cart.value.tourID}" class="btn btn-outline-danger">Delete</a></td>
                                <td><input type="checkbox" class="form-check-input" name="tourID${counter.count}" value="${cart.value.tourID}"/></td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
                <div class="form-group">
                    <input type="submit" class="btn btn-outline-primary" name="action" value="Update"/>
                </div>
                <div class="form-group">
                    Discount code: <input type="text" name="code" value="${requestScope.DISCOUNT_CODE}"/>
                    <input type="submit" class="btn btn-outline-primary" name="action" value="Discount"/>
                    <c:if test="${not empty sessionScope.DISCOUNT_VALUE}">
                        <label>Discount ${sessionScope.DISCOUNT_VALUE}%</label>
                    </c:if>
                    </br>
                    <label><strong>Total cost: $${sessionScope.TOTAL_COST}</strong></label>
                </div>
                <input type="submit" class="btn btn-outline-primary" name="action" value="Confirm"/>
            </form>
        </div>
    </body>
    <script src="jquery/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
</html>
