<%-- 
    Document   : home
    Created on : Dec 15, 2020, 10:02:11 PM
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
        <title>Home Page - Hotel Booking</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="err" value="${requestScope.ERROR}" />
        <c:set var="checkIn" value="${requestScope.CHECK_IN}" />
        <c:set var="checkOut" value="${requestScope.CHECK_OUT}" />
        <c:set var="roomType" value="${requestScope.ROOM_TYPE}" />
        <c:set var="listTypes" value="${requestScope.LIST_TYPES}" />
        <c:set var="amountRoom" value="${requestScope.AMOUNT_ROOM}" />
        <c:set var="listRooms" value="${requestScope.LIST_ROOMS}" />

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
                    <!--Search section-->
                    <div class="col-sm-5"></div>


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
            <c:when test="${not empty checkIn}">
                <div class="pt-5">
                    <h4 class="book-title text-center text-uppercase pb-5">search result</h4>
                </div>
            </c:when>

            <c:otherwise>    
                <div class="pt-5">
                    <h4 class="book-title text-center text-uppercase pb-5">What we have...</h4>
                </div>
            </c:otherwise>
        </c:choose>


        <!--Start of filter-->
        <form action="search" method="GET">
            <div class="pt-3 pb-3">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-2 mt-4">
                            <h5 class="text-uppercase">Search rooms: </h5>
                        </div>

                        <div class="col-sm-1"></div>

                        <div class="col-sm-2 text-center">
                            <p class="mt-1 types-title">Room Types</p>
                            <select class="custom-select mt-2 text-center" name="typesId" id="typesId">
                                <option value="0" selected>All</option>
                                <c:forEach var="type" items="${listTypes}">
                                    <option value="${type.typeId}">${type.typeName}</option>
                                </c:forEach> 
                            </select>
                        </div>

                        <div class="col-sm-2 text-center">
                            <p class="text-center">Check-in date</p> 
                            <input type="date" name="checkIn" class="text-center" id="checkIn">
                        </div>

                        <div class="col-sm-2 text-center">
                            <p class="text-center">Check-out date</p>
                            <input type="date" name="checkOut" class="text-center" id="checkOut">
                        </div>

                        <div class="col-sm-2 text-center">
                            <p class="text-center">Amount Room</p>
                            <input type="number" name="amountRoom" class="text-center" value="${amountRoom == null ? 1 : amountRoom}" id="roomAmount">
                        </div>

                        <div class="col-sm-1 mt-4">
                            <input class="btn btn-outline-dark" type="submit" value="Lookup"/>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <!--End of filter-->



        <c:choose>
            <c:when test="${not empty listRooms}">
                <!--Start of rooms-->
                <div class="container pt-5 pb-5">
                    <div class="row">
                        <c:forEach var="room" items="${listRooms}" varStatus="counter">
                            <div class="col-sm-6">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <img class="home-image" src="assets/images/${room.typesDTO.picture}" alt="Card image cap">

                                        </div>
                                        <div class="col-sm-6">
                                            <h5 class="home-title">${room.typesDTO.typeName}</h5>
                                            <p class="home-price" id="price-${counter.count}">${room.typesDTO.price}</p>

                                            <c:choose>
                                                <c:when test="${empty checkIn}">
                                                    <p>We got <span class="home-amount">${room.quantity}</span> room(s).</p>                                     
                                                </c:when>

                                                <c:otherwise>
                                                    <p>Available <span class="home-amount">${room.quantity}</span> room(s).</p>
                                                    <a href="add-cart?addType=${room.typesDTO.typeId}&pageBefore=Search&typesId=${roomType}&checkIn=${checkIn}&checkOut=${checkOut}&amountRoom=${amountRoom}" class="btn btn-outline-primary">Add To Cart</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <!--End of rooms-->
            </c:when>

            <c:when test="${not empty err}">
                <div class="container pt-5">
                    <div class="row">
                        <div class="col-sm-12">
                            <h4 class="text-center text-uppercase" style="color: red;">${err.amountPositive}</h4>
                            <h4 class="text-center text-uppercase" style="color: red;">${err.roomTypeNumberic}</h4>
                            <h4 class="text-center text-uppercase" style="color: red;">${err.roomNumberNumberic}</h4>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container pt-5">
                    <div class="row">
                        <div class="col-sm-12">
                            <h4 class="text-center text-uppercase">No result...</h4>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>



        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    </body>

    <script>
        $(function () {
            $("#typesId").val(${roomType == null ? 0 : roomType});

            for (var i = 1; i <= ${fn:length(listRooms)}; i++) {
                $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
            }
        });

        document.getElementById('checkIn').valueAsDate = document.getElementById('checkOut').valueAsDate = new Date();
        <c:choose>
            <c:when test="${checkIn != null}">
                document.getElementById("checkIn").value = "${checkIn}";
            </c:when>
            <c:otherwise>
                $('#checkIn').val(new Date().toDateInputValue());
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${checkOut != null}">
                document.getElementById("checkOut").value = "${checkOut}";
            </c:when>
            <c:otherwise>
                $('#checkOut').val(new Date().toDateInputValue());
            </c:otherwise>
        </c:choose>
    </script>

</html>
