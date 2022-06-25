<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<spring:url value="/resources/css/administrativo.css" var="mainCss" />
<spring:url value="/webjars/angularjs/1.6.4/angular.js" var="angularJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-resource.js"
	var="angularresJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-route.js"
	var="angularrouteJs" />
<spring:url value="/resources/js/app.module.js" var="starterJs" />
<spring:url value="/resources/js/services/admin.services.js"
	var="servicesJs" />
<spring:url value="/resources/js/providers/storage.provider.js"
	var="providerJs" />
<spring:url value="/resources/img/logoFooter.png" var="logoFooter" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smartfit - Mensaje</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<link href="${mainCss}" rel="stylesheet" />

</head>
<body>
	<div>
		<jsp:include page="header.jsp"></jsp:include>

		<section>
		<div class="panel panel-default">
			<div class="panel-body">
				<ol class="breadcrumb">
					<li><a href="/Admin/crear_convenio_empresa">Crear Empresa</a></li>
					<li><a href="/Admin/listar_convenio_empresas">Listar Empresas</a></li>
				</ol>
			</div>
		</div>
		</section>

		<section class="generic-container">
		<div class="jumbotron center_text_align">
			<h1>Respuesta:</h1>
			<p>${mensaje}</p>
		</section>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
	<script src="${jQueryXls}"></script>
	<script src="${angularJs}"></script>
	<script src="${servicesJs}"></script>
	<script src="${providerJs}"></script>
	<script src="${angularresJs}"></script>
	<script src="${angularrouteJs}"></script>
	<script src="${starterJs}"></script>
	<script src="${adminJs}"></script>
</body>
</html>