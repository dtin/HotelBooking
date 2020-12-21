<%-- 
    Document   : register
    Created on : Sep 19, 2020, 12:39:26 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register - Hotel Booking</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/register.css" />
    </head>
    <body>
        <c:set var="error" value="${requestScope.ERR}"/>
        
        <div class="container">
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 form-register">
                    <h3 class="text-center">Register Page</h3>
                    <!-- Start of Register Form -->
                    <form action="sign-up" method="POST">
                        <div class="form-group">
                            <label>Full name <font color="red">* Required</font></label>
                            <input type="text" class="form-control" name="txtName" value="${param.txtName}">
                        </div>

                        <c:if test="${not empty error.lengthName}">
                            <font color="red">${error.lengthName}</font>
                            <br>
                        </c:if>

                        <div class="form-group">
                            <label>Email address <font color="red">* Required</font></label>
                            <input type="email" class="form-control" name="txtEmail" value="${param.txtEmail}">
                        </div>

                        <c:if test="${not empty error.duplicateEmail}">
                            <font color="red">${error.duplicateEmail}</font>
                            <br>
                        </c:if>
                        <c:if test="${not empty error.formatEmail}">
                            <font color="red">${error.formatEmail}</font>
                            <br>
                        </c:if>    

                        <div class="form-group">
                            <label>Password <font color="red">* Required</font></label>
                            <input type="password" class="form-control" name="txtPassword">
                        </div>

                        <c:if test="${not empty error.lengthPassword}">
                            <font color="red">${error.lengthPassword}</font>
                            <br>
                        </c:if>

                        <div class="form-group">
                            <label>Confirm Password <font color="red">* Required</font></label>
                            <input type="password" class="form-control" name="txtConfirm">
                        </div>

                        <c:if test="${not empty error.confirmPassword}">
                            <font color="red">${error.confirmPassword}</font>
                            <br>
                        </c:if>
                            
                        <div class="form-group">
                            <label>Address <font color="red">* Required</font></label>
                            <input type="text" class="form-control" name="txtAddress">
                        </div>
                        <c:if test="${not empty error.lengthAddress}">
                            <font color="red">${error.lengthAddress}</font>
                            <br>
                        </c:if>

                        <div class="form-group">
                            <label>Phone number <font color="red">* Required</font></label>
                            <input type="text" class="form-control" name="txtPhone">
                        </div>
                        <c:if test="${not empty error.lengthPhone}">
                            <font color="red">${error.lengthPhone}</font>
                            <br>
                        </c:if>

                        <button type="submit" class="btn btn-outline-secondary register-button">Create Account</button>
                    </form>
                    <!-- End of Register Form -->

                    <!-- Start of Login link -->
                    <a href="login-page" class="d-flex justify-content-center login-link">Back To Login</a>
                    <!-- End of Login -->

                    <div class="col-md-3"></div>
                </div>
            </div>

            <!--Bootstrap JS-->
            <script src="assets/js/jquery-3.5.1.min.js"></script>
            <script src="assets/js/popper.min.js"></script>
            <script src="assets/js/bootstrap.min.js"></script>
            <script src="assets/js/fontawesome.min.js"></script>

    </body>
</html>
