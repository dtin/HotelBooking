<%-- 
    Document   : feedback
    Created on : Dec 17, 2020, 10:12:54 PM
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
        <c:set var="feedbackResult" value="${requestScope.FEEDBACK_RESULT}" />
        <c:set var="userFeedback" value="${requestScope.USER_FEEDBACK}" />
        <c:set var="err" value="${requestScope.ERROR}" />

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

        <div class="pt-5">
            <h4 class="text-center text-uppercase pb-5">Feedback for order #${orderId}</h4>
        </div>
        
        <!--Start of stars-->
        <div class="pt-3 text-center star-rating">
            <c:choose>
                <c:when test="${userFeedback != 0}">
                    <c:forEach begin="1" end="${userFeedback}" step="1">
                        <a><i class="fas fa-star"></i></a>
                    </c:forEach>
                    <c:forEach begin="${userFeedback + 1}" end="5" step="1">
                        <a><i class="far fa-star"></i></a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" end="5" step="1" varStatus="counter">
                        <a href="feedback?orderId=${orderId}&feedbackStar=${counter.index}"><i class="far fa-star"></i></a>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <!--End of Stars-->

        <c:if test="${err != null}">
            <div class="pt-5">
                <h5 class="text-center text-uppercase pb-5">${err.starNotLessThanOne}</h5>
                <h5 class="text-center text-uppercase pb-5">${err.feedbackNotAuthorized}</h5>
                <h5 class="text-center text-uppercase pb-5">${err.feedbackBefore}</h5>
            </div>
        </c:if>



        <c:if test="${not empty feedbackResult}">
            <div class="pt-5">
                <c:choose>
                    <c:when test="${feedbackResult}">
                        <h5 class="text-center text-uppercase pb-5">You have sent your feedback successfully.</h5>
                    </c:when>
                    <c:otherwise>
                        <h5 class="text-center text-uppercase pb-5">Something wrong happened. Please try again later.</h5>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <div class="container">
            <div class="row">
                <div class="pt-5 col-sm-12 text-center">
                    <a href="history" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to History</a>
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