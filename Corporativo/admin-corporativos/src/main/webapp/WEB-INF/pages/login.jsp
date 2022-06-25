<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>

<!DOCTYPE html>
<html>
<head>
<spring:url value="/resources/css/app.css" var="mainCss" />
<spring:url value="/resources/css/login.css" var="loginCss" />
<spring:url value="/resources/img/logoFooter.png" var="logoFooter" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Autenticación Convenios</title>
<!-- Manifest -->
<link rel="manifest" href="manifest.webmanifest">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
    
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
<link href="${mainCss}" rel="stylesheet" />
<link href="${loginCss}" rel="stylesheet" />
</head>
<body onload='document.loginForm.username.focus();'>
    <header>
        <div class="col-md-12 imgHeaderBar">
            <div class="menuHeader">
                <div class="width1150 height100">
                    <div class="col-md-12 col-sm-3 height100 containerCenter">
                        <a href="#"><img class="width100 logoFooter center-block" src="${logoFooter}"></a>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <div id="login-box">
        <div class="login-container">
            <div class="login-card">
                <div class="login-form">
                    <c:if test="${not empty error}">
                        <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="msg">${msg}</div>
                    </c:if>
                    <form name='loginForm' action="<c:url value='/login?${_csrf.parameterName}=${_csrf.token}' />" method='POST'
                        class="form-horizontal">
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label> <input type="text"
                                class="form-control" id="username" name="username" placeholder="Enter Username" required>
                        </div>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> <input type="password"
                                class="form-control" id="password" name="password" placeholder="Enter Password" required>
                        </div>
                        <div class="form-actions">
                            <input type="submit" name="submit" class="btn btn-block btn-default" value="Ingresar">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>