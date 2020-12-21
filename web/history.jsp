<%-- 
    Document   : history
    Created on : Dec 12, 2020, 2:33:11 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History - Hotel Booking</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="orderList" value="${requestScope.ORDER_LIST}" />
        <c:set var="searchOrder" value="${requestScope.SEARCH_ORDER}" />
        <c:set var="orderDate" value="${requestScope.ORDER_DATE}" />
        <c:set var="currentDate" value="${requestScope.CURRENT_DATE}" />

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
            <h4 class="book-title text-center text-uppercase pb-5">my history</h4>
        </div>


        <!--Start of filter-->
        <form action="history" method="GET">
            <div class="pt-3 pb-3">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-1">
                            <h4 class="text-uppercase">Filter: </h4>
                        </div>

                        <div class="col-sm-2">
                            <p class="mt-1 text-right">Order ID: </p>
                        </div>

                        <div class="col-sm-2">
                            <input type="text" name="orderId" value="${searchOrder}"/>
                        </div>

                        <div class="col-sm-2">
                            <p class="mt-1 text-right">Booking Date: </p>
                        </div>

                        <div class="col-sm-2">
                            <input type="date" id="filterDate" name="orderDate" />
                        </div>

                        <div class="col-sm-3">
                            <input class="btn btn-outline-dark float-right" type="submit" value="Filter Order"/>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <!--End of filter-->


        <c:choose>
            <c:when test="${not empty orderList}">
                <!-- Start of List of cart items-->
                <div class="container-fluid pb-5">
                    <div class="row">
                        <div class="col-sm-12">
                            <table class="table" id="history">
                                <thead>
                                    <tr>
                                        <th class="text-center" scope="col">Order ID</th>
                                        <th class="text-center" scope="col">Total Price</th>
                                        <th class="text-center" scope="col">Discount Code</th>
                                        <th class="text-center" scope="col">After Discount</th>
                                        <th class="text-center" scope="col">Booking Date</th>
                                        <th class="text-center" scope="col">Check In</th>
                                        <th class="text-center" scope="col">Check Out</th>
                                        <th class="text-center" scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${orderList}" varStatus="counter">
                                        <tr>
                                            <th class="text-center">#${order.ordersDTO.orderId}</th>
                                            <td class="text-center" id="price-${counter.count}">${order.ordersDTO.totalPrice}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${not empty order.ordersDTO.discountCode}">
                                                        ${order.ordersDTO.discountCode}
                                                    </c:when>
                                                    <c:otherwise>
                                                        -   
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center" id="after-${counter.count}">${order.afterDiscountPrice}</td>
                                            <td class="text-center">${order.ordersDTO.createdAt}</td>
                                            <td class="text-center">${order.ordersDTO.checkIn}</td>
                                            <td class="text-center">${order.ordersDTO.checkOut}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${order.ordersDTO.activationCode == ''}">
                                                        <c:choose>
                                                            <c:when test="${order.action == 'Delete'}">
                                                                <a onclick="deleteOrder('${order.ordersDTO.orderId}')" class="btn btn-outline-danger btn-sm">Delete Order</a>
                                                            </c:when>
                                                            <c:when test="${order.action == 'Wait feedback'}">
                                                                <a disable class="btn btn-primary btn-sm">Feedback</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="feedback?orderId=${order.ordersDTO.orderId}" class="btn btn-outline-primary btn-sm">Feedback</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <a href="order-detail?orderId=${order.ordersDTO.orderId}" class="btn btn-outline-secondary btn-sm">View Detail</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p>Waiting for active</p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--End of list of cart items--> 
                </c:when>

                <c:when test="${searchOrder != null || orderDate != null}">
                    <h4 class="text-center text-uppercase">No result!</h4>
                    <div class="text-center pt-4">
                        <a href="history" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to History</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <h4 class="text-center text-uppercase">You have not placed any orders yet!</h4>
                    <div class="text-center pt-4">
                        <a href="home" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to Shopping</a>
                    </div>
                </c:otherwise>
            </c:choose>

            <!--Bootstrap JS-->
            <script src="assets/js/jquery-3.5.1.min.js"></script>
            <script src="assets/js/popper.min.js"></script>
            <script src="assets/js/bootstrap.min.js"></script>
            <script src="assets/js/fontawesome.min.js"></script>

            <script>
                                                                    document.getElementById('filterDate').valueAsDate = new Date();

                                                                    $(function () {
                                                                        for (var i = 1; i <= ${fn:length(orderList)}; i++) {
                                                                            $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
                                                                            $('#after-' + i).text($('#after-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
                                                                        }


                <c:choose>
                    <c:when test="${orderDate != null}">
                                                                        document.getElementById("filterDate").value = "${orderDate}";
                    </c:when>
                    <c:otherwise>
                                                                        $('#filterDate').val(new Date().toDateInputValue());
                    </c:otherwise>
                </c:choose>
                                                                    });

                                                                    function deleteOrder(orderId) {
                                                                        if (confirm("Are you sure you want to delete order '" + orderId + "'?")) {
                                                                            window.location.href = "delete-order?orderId=" + orderId;
                                                                        }
                                                                    }
                                                                    ;
            </script>
    </body>

</html>