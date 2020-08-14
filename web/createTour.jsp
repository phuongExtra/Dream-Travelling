<%-- 
    Document   : createTour
    Created on : Jun 22, 2020, 3:17:20 PM
    Author     : nhatp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Tour</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/shop-homepage.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container">
                <a class="navbar-brand" href="TourSearchController">Dream Traveling</a>
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
        <c:if test="${not empty requestScope.INVALID}" var="checkError">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>${requestScope.INVALID.nameErr}!</strong> 
                <strong>${requestScope.INVALID.desErr}!</strong> 
                <strong>${requestScope.INVALID.priceErr}!</strong> 
                <strong>${requestScope.INVALID.quotaErr}!</strong> 
                <strong>${requestScope.INVALID.dateErr}!</strong> 
            </div>
        </c:if>
        <c:if test="${!checkError}"></c:if>
        <div class="col-md-4">
            <form action="CreateTourController" method="POST">
                <div class="form-group">
                    <label>Tour name</label>
                    <input type="text" class="form-control" name="tourName"/>
                </div>
                <div class="form-group">
                    <label>Destination</label>
                    <input type="text" class="form-control" name="destination"/>
                </div>
                <div class="form-group">
                    <label>Price</label>
                    <input type="text" class="form-control" name="price"/>
                </div>
                <div class="form-group">
                    <label>Quota</label>
                    <input type="text" class="form-control" name="quota"/>
                </div>
                <div class="form-group">
                    <label>Departure date</label>
                    <input type="date" class="form-control" name="fromDate" required="true"/>
                </div>
                <div class="form-group">
                    <label>Finish Date</label>
                    <input type="date" class="form-control" name="toDate" required="true"/>
                </div>
                <div class="form-group">
                    <label>Image</label>
                    <select name="img" id="selectImg" onchange="changeImg()">
                        <c:forEach items="${requestScope.IMG}" var="img">
                            <option value="${img}">${img}</option>
                        </c:forEach>
                    </select>
                    <img src="img/australia.png" id="img" height="300" width="600"/>
                </div>
                <input class="btn btn-outline-primary" type="submit" name="action" value="Create"/>
            </form>
        </div>
    </body>
    <script>
        function changeImg() {
            var img = document.getElementById("selectImg").value;
            var dir = "img/" + img;
            document.getElementById("img").src = dir;
        }
    </script>
    <script src="jquery/jquery.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
</html>
