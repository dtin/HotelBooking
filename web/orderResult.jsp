<%-- 
    Document   : orderResult
    Created on : Dec 11, 2020, 1:15:26 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout - Hotel Booking</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="orderId" value="${requestScope.ORDER_ID}" />
        <c:set var="orderStatus" value="${requestScope.ORDER_STATUS}" />

        <!-- Start of header -->
        <div class="header sticky-top fixed-top">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-3">
                        <c:choose>
                            <c:when test="${empty userFullName}">
                                <p class="welcome">Hello Guest!</p>                                
                            </c:when>
                            <c:otherwise>
                                <p class="welcome">Hello ${userFullName}!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-1">
                        <a class="home float-left" href="home"><i class="fas fa-home"></i></a>
                    </div>
                    <div class="col-sm-5">
                    </div>
                    <div class="col-sm-1">
                        <a href="cart" class="view-cart-icon float-left">
                            <i class="fas fa-shopping-cart"></i>
                        </a>
                    </div>
                    <div class="col-sm-1">
                        <a href="history" class="view-cart-icon float-left">
                            <i class="fas fa-history"></i>
                        </a>
                    </div>

                    <div class="col-sm-1">
                        <c:choose>    
                            <c:when test="${empty userFullName}">
                                <a href="login-page" class="sign-out-icon float-left">
                                    <i class="fas fa-sign-in-alt"></i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="sign-out" class="sign-out-icon float-left">
                                    <i class="fas fa-sign-out-alt"></i>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <!-- End of Header -->

        <c:choose>
            <c:when test="${not empty orderStatus}">
                <c:choose>
                    <c:when test="${orderStatus}">
                        <div class="pt-5">
                            <h4 class="text-center text-uppercase pb-5">Your order #${orderId} has been activated successfully!</h4>
                        </div>
                    </c:when>
                    <c:when test="${!orderStatus}">
                        <div class="pt-5">
                            <h4 class="text-center text-uppercase pb-5">Your order was not found or was already activated before!</h4>
                        </div>
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${empty orderStatus}">
                <c:choose>
                    <c:when test="${not empty orderId}">
                        <div class="pt-5">
                            <h4 class="text-center text-uppercase pb-5">Your order #${orderId} has been placed successfully!</h4>
                            <h5 class="text-center text-uppercase pb-5">A verification email has been sent to you.</h5>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="pt-5">
                            <h4 class="text-center text-uppercase pb-3">Can not create order right now!</h4>
                            <h5 class="text-center text-uppercase pb-5">Please try again later!</h5>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>


        <div class="container">
            <div class="row">
                <div class="col-sm-12 text-center">
                    <a href="home" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to Booking</a>
                </div>
            </div>

        </div>

        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
    </body>

</html>