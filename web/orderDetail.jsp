<%-- 
    Document   : orderDetail
    Created on : Dec 18, 2020, 9:48:22 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail - Hotel Booking</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="orderList" value="${requestScope.ORDER_LIST}" />
        <c:set var="orderId" value="${requestScope.ORDER_ID}" />

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
            <h4 class="book-title text-center text-uppercase pb-5">Order detail #${orderId}</h4>
        </div>


        <!-- Start of List of cart items-->
        <div class="container pb-5">
            <div class="row">
                <div class="col-sm-12">
                    <table class="table" id="orderDetail">
                        <thead>
                            <tr>
                                <th class="text-center" scope="col">No.</th>
                                <th class="text-center" scope="col">Room Number</th>
                                <th class="text-center" scope="col">Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${orderList}" varStatus="counter">
                                <tr>
                                    <th class="text-center">#${counter.count}</th>
                                    <td class="text-center">${product.roomName}</td>
                                    <td class="text-center" id="price-${counter.count}">
                                        ${product.orderDetailsDTO.price}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!--End of list of cart items--> 

            <div class="container">
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <a href="history" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to History</a>
                    </div>
                </div>
            </div>

            <!--Bootstrap JS-->
            <script src="assets/js/jquery-3.5.1.min.js"></script>
            <script src="assets/js/popper.min.js"></script>
            <script src="assets/js/bootstrap.min.js"></script>
            <script src="assets/js/fontawesome.min.js"></script>

            <script>
                $(function () {
                    for (var i = 1; i <= ${fn:length(orderList)}; i++) {
                        $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
                    }
                });
            </script>
    </body>

</html>