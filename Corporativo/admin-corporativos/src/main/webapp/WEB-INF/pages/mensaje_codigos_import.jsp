<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<spring:url value="/resources/css/administrativo.css" var="mainCss" />
<spring:url value="/resources/css/convenio.css" var="secondCss" />
<spring:url value="/webjars/angularjs/1.6.4/angular.js" var="angularJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-resource.js"
	var="angularresJs" />
<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-base64/2.0.5/angular-base64.min.js" var="base64js" />
<spring:url value="/resources/js/library/angular-datepicker.min.js" var="datePickerJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-route.js"
	var="angularrouteJs" />
<spring:url value="/resources/js/app.module.convenios.js" var="starterJs" />
<spring:url value="/resources/js/providers/csv.provider.js" var="csvProvider" />
<spring:url
	value="/resources/js/controllers/mensaje_codigos_import.controller.js"
	var="adminJs" />
<spring:url value="/resources/js/services/admin.services.js"
	var="servicesJs" />
<spring:url value="/resources/js/providers/storage.provider.js"
	var="providerJs" />
<spring:url value="/resources/img/logoFooter.png" var="logoFooter" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registrar Empresa Convenio</title>
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
<link href="${secondCss}" rel="stylesheet" />

</head>
<body data-ng-app="app">
	<div data-ng-controller="mensajeCodigosRespuestaController as adminCtrl">
		<jsp:include page="header.jsp"></jsp:include>

		<section>
			<div class="panel panel-default">
				<div class="panel-body">
					<ol class="breadcrumb section-menu">
						<li class="active" ng-click="adminCtrl.changeView('crear_convenio_empresa')"><a>Crear Empresa</a></li>
						<li><a ng-click="adminCtrl.changeView('listar_convenio_empresas')">Listar Empresas</a></li>
					</ol>
				</div>
			</div>
		</section>

		<section class="generic-container">
		<div class="jumbotron center_text_align" data-ng-if="${codigoRespuesta} === 200">
			<h1 data-ng-if="'${accion}' == 'CodigoDescuentoImport'">Importar Códigos</h1>

			<label>Se han realizado {{adminCtrl.tableSuccess.length}} registros y han fallado {{adminCtrl.tableFallidos.length}}.</label>
			<br>
			<label class="titleEmployees title-margin" data-ng-if="adminCtrl.tableFallidos.length > 0">
				Registros fallidos
				<a ng-click="adminCtrl.downloadFallidos()" style="cursor: pointer;">Descargar
				</a>
			</label> 
		</div>
		<div data-ng-if="adminCtrl.tableFallidos.length > 0" class="form-group col-xs-12 table-employees">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Código</th>
						<th>Error</th>
					</tr>
				</thead>
				<tbody>
					<tr data-ng-repeat="error in adminCtrl.tableFallidos">

						<td>{{error | pos:0 }}</td>
						<td>{{error | pos:1}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="jumbotron center_text_align" data-ng-if="adminCtrl.error">
			<p>{{adminCtrl.error}}</p>
		</div>
		</section>
		<input type="hidden" id="codigoRespuesta" name="codigoRespuesta" value="${codigoRespuesta}">
		<input type="hidden" id="codigoError" name="codigoError" value="${codigoError}">
		<input type="hidden" id="csvCodigosFallidos" name="csvCodigosFallidos" value="${csvCodigosFallidos}">
		<input type="hidden" id="csvCodigosCompletos" name="csvCodigosCompletos" value="${csvCodigosCompletos}">
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
	<script src="${base64js}"></script>
	<script src="${datePickerJs}"></script>
	<script src="${servicesJs}"></script>
	<script src="${providerJs}"></script>
	<script src="${angularresJs}"></script>
	<script src="${angularrouteJs}"></script>
	<script src="${starterJs}"></script>
	<script src="${adminJs}"></script>
	<script src="${csvProvider}"></script>
</body>
</html>