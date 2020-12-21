<%-- 
    Document   : cart
    Created on : Dec 8, 2020, 8:19:21 PM
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
        <title>Cart - Hotel Booking</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="roomsList" value="${requestScope.ROOMS_LIST}" />
        <c:set var="checkIn" value="${requestScope.CHECK_IN}"/>
        <c:set var="checkOut" value="${requestScope.CHECK_OUT}"/>
        <c:set var="totalPrice" value="${requestScope.TOTAL_PRICE}" />
        <c:set var="discountCode" value="${requestScope.DISCOUNT_CODE}" />
        <c:set var="discountPercent" value="${requestScope.DISCOUNT_PERCENT}" />
        <c:set var="err" value="${requestScope.ERROR}" />
        <c:set var="quantityError" value="${requestScope.QUANTITY_ERROR}" />
        <c:set var="typeIdError" value="${requestScope.TYPE_ID_ERROR}" />
        <c:set var="quantityCheckoutError" value="${requestScope.QUANTITY_CHECKOUT_ERROR}" />


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
            <h4 class="book-title text-center text-uppercase pb-5">my cart</h4>
        </div>

        <c:choose>
            <c:when test="${not empty roomsList}">
                <!-- Start of List of cart items-->
                <div class="container pb-5">
                    <div class="row">
                        <c:forEach var="room" items="${roomsList}" varStatus="counter">
                            <div class="col-sm-12">
                                <div class="card">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <p class="cart-book-name">
                                                    ${room.typesDTO.typeName}
                                                </p>
                                            </div>
                                            <div class="col-sm-2">
                                                <p class="cart-book-price" id="price-${counter.count}">
                                                    ${room.typesDTO.price}
                                                </p>
                                            </div>
                                            <div class="col-sm-3">
                                                <form action="update-cart" method="GET">
                                                    <input class="cart-book-quantity" type="number" name="quantity" value="${room.roomAmount}" />
                                                    <input type="hidden" name="typeId" value="${room.typesDTO.typeId}" />
                                                    <input class="btn btn-outline-success btn-sm text-uppercase text-center ml-3 mb-1" type="submit" value="Update"/>
                                                </form>
                                                <c:if test="${typeIdError == room.typesDTO.typeId && quantityCheckoutError != null}">
                                                    <p class="cart-quantity-error">${quantityCheckoutError.roomOutOfStock}</p>
                                                </c:if>
                                            </div>
                                            <div class="col-sm-1">
                                                <a onclick="deleteRoom('${room.typesDTO.typeName}', '${room.typesDTO.typeId}')" href="#" class="cart-book-delete">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <!--End of list of cart items--> 

                <!--Start of discount code-->
                <form action="discount" method="POST">
                    <div class="container">
                        <div class="row">
                            <c:if test="${not empty quantityError}">
                                <div class="offset-sm-7 col-sm-5">
                                    <p class="error-cart">${quantityError}</h5>
                                </div>
                            </c:if>
                            <div class="offset-sm-7 col-sm-5">
                                <h5 class="title-discount ml-3 mr-4">Discount code: </h5>
                                <input class="discount-code" type="text" name="discountCode" value="${discountCode}">
                                <input class="btn btn-outline-info apply-button float-right mb-5 text-uppercase" type="submit" value="Apply" name="btnAction" />
                            </div>
                            <c:if test="${not empty err}">
                                <div class="offset-sm-7 col-sm-5">
                                    <p class="error-cart">${err.emptyDiscount}</h5>
                                    <p class="error-cart">${err.discountNotValid}</h5>
                                    <p class="error-cart">${err.expiredDiscount}</h5>
                                    <p class="error-cart">${err.alreadyUsed}</h5>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>
            <!--End of discount code-->

            <!--Start of price and control-->
            <form action="checkout">
                <div class="container">
                    <div class="row">
                        <c:if test="${not empty discountPercent}">
                            <div class="offset-sm-8 col-sm-4 text-right">
                                <p class="discount-title">Discount: <span class="cart-discount" id="discount">${discountPercent}%</span></p>
                            </div>
                        </c:if>
                        <div class="offset-sm-8 col-sm-4 text-right pb-3">
                            <h5>Total price: <span class="cart-total-price" id="total">${totalPrice}</span></h5>
                        </div>
                        <div class="col-sm-3 d-sm-inline-block">
                            <a href="home" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to Shopping</a>
                        </div>
                        <div class="col-sm-3">
                            <c:if test="${not empty checkIn}">
                                <p class="d-sm-inline-block">Check in</p>
                                <input type="date" name="checkIn" id="checkIn">
                            </c:if>
                        </div>
                        <div class="col-sm-3">
                            <c:if test="${not empty checkOut}">
                                <p class="d-sm-inline-block">Check out</p>
                                <input type="date" name="checkOut" id="checkOut">
                            </c:if>
                        </div>
                        <div class="offset-sm-1 col-sm-2 text-right">
                            <button type="submit" class="btn btn-outline-info text-uppercase text-center">Checkout <i class="fas fa-long-arrow-alt-right"></i></button>
                        </div>
                    </div>
                </div>
            </form>
            <!--End of price and control-->        
        </c:when>

        <c:otherwise>
            <h4 class="text-center text-uppercase">There is nothing your cart right now!</h4>
            <div class="text-center pt-4">
                <a href="home" class="btn btn-outline-secondary text-uppercase text-center"><i class="fas fa-long-arrow-alt-left"></i> Back to shopping</a>
            </div>
        </c:otherwise>
    </c:choose>

    <!--Bootstrap JS-->
    <script src="assets/js/jquery-3.5.1.min.js"></script>
    <script src="assets/js/popper.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/fontawesome.min.js"></script>

    <script>
        $(function () {
            $('#total').text($('#total').text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
            for (var i = 1; i <= ${fn:length(roomsList)}; i++) {
                $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
            }
        });

        function deleteRoom(roomName, roomId) {
            if (confirm("Are you sure you want to delete '" + roomName + "'?")) {
                window.location.href = "delete-cart?typeId=" + roomId;
            }
        }
        ;

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
</body>

</html>