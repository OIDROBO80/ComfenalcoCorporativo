<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<spring:url value="/webjars/angularjs/1.6.4/angular.js" var="angularJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-resource.js" var="angularresJs" />
<spring:url value="/resources/js/app.module.js" var="starterJs" />
<spring:url value="/resources/js/controllers/home.controller.js" var="adminJs" />
<spring:url value="/resources/js/services/admin.services.js" var="servicesJs" />
<spring:url value="/resources/js/providers/storage.provider.js" var="providerJs" />
<spring:url value="/resources/css/home.css" var="mainCss" />
<spring:url value="/resources/img/logoFooter.png" var="logoHeader" />

<!-- Manifest -->
<link rel="manifest" href="/resources/manifest.webmanifest">
<link rel="icon" href="/resources/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
    integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />

<link href="${mainCss}" rel="stylesheet" />
<title>Home</title>
</head>
<body data-ng-app="app">
    <div data-ng-controller="adminController as adminCtrl">
        <jsp:include page="header.jsp" ></jsp:include>
        <section class="homeBody">
            <div class="jumbotron">
                <h1>¡Bienvenido!</h1>
                <p>Este sitio es de gestión. Presione el botón para continuar</p>
                <p>
                    <a class="btn btn-primary btn-lg" href="gestionar" role="button">Gestión</a>
                </p>
            </div>
        </section>
    </div>
    <script src="${angularJs}"></script>
    <script src="${servicesJs}"></script>
    <script src="${providerJs}"></script>
    <script src="${angularresJs}"></script>
    <script src="${starterJs}"></script>
    <script src="${adminJs}"></script>
</body>
</html>