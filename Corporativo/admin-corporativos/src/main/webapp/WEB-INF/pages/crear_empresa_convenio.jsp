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
<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-base64/2.0.5/angular-base64.min.js" var="base64js" />
<spring:url value="/resources/js/library/angular-datepicker.min.js" var="datePickerJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-resource.js"
var="angularresJs" />
<spring:url value="/resources/js/app.module.convenios.js" var="starterJs" />
<spring:url value="/resources/js/providers/csv.provider.js" var="csvProvider" />
<spring:url
value="/resources/js/controllers/crear_empresa_convenio.controller.js"
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
<div data-ng-controller="crearEmpresaNominaController as adminCtrl">
    <jsp:include page="header.jsp"></jsp:include>

    <section>
		<div class="panel panel-default">
			<div class="panel-body">
				<ol class="breadcrumb section-menu">
                    <li class="active"><a>Crear Empresa</a></li>
					<li><a ng-click="adminCtrl.changeView('listar_convenio_empresas')">Listar Empresas</a></li>
					<!--li><a ng-click="adminCtrl.changeView('validar_documentos')">Validar</a></li-->
				</ol>
			</div>
		</div>
    </section>
    <section class="generic-container">
	<div class="col-md-12" ng-if='adminCtrl.canCreatePlan'>
		<div class="panel panel-default" id="divCrearPlan">
			<div class="panel-heading">
				<span class="lead">Crear Plan</span>
			</div>
			<div class="panel-body">
				<form class="form_crear_plan" role="form" name="formCrearPlan">
					<div class="row">
						<div class="form-group col-xs-12 col-sm-6">
							<label for="nombrePlan">Nombre Plan</label>
							<input type="text"
								   data-ng-pattern="/^[a-zA-ZáéíóúÁÉÍÓÚÑñ0-9\s]+$/"
								   class="form-control" id="nombrePlan" name="nombrePlan"
								   placeholder="Ingresa el Nombre Plan"
								   data-ng-model='adminCtrl.formCrearPlan.nombrePlan' required
								   data-ng-class='{ error: formCrearPlan.nombrePlan.$dirty && formCrearPlan.nombrePlan.$invalid}'>
							<span ng-show="formCrearPlan.nombrePlan.$dirty && formCrearEmp.nombrePlan.$invalid" style="opacity: 0.3;">Descripcion del plan</span>
						</div>
						<div class="form-group col-xs-12 col-sm-6">
							<label for="dias">Dias</label>
							<input type="text"
								   data-ng-pattern="/^[0-9]{1,365}$/"
								   class="form-control" id="dias" name="dias"
								   placeholder="Ingresa el numero de dias por plan"
								   data-ng-model='adminCtrl.formCrearPlan.dias' required
								   data-ng-class='{ error: formCrearPlan.dias.$dirty && formCrearEmp.formCrearPlan.dias.$invalid}'>
							<span ng-show="formCrearPlan.dias.$dirty && formCrearEmp.dias.$invalid" style="opacity: 0.3;">Valor numerico</span>
						</div>
					</div>
					<div class="alert alert-warning" role="alert"
						 data-ng-if="adminCtrl.errorCreatingPlan">
						<h4 class="error_message">{{adminCtrl.errorCreatingPlan}}</h4>
					</div>
					<div class="modal-footer">
						<button class="btn btn-primary"
								ng-disabled="formCrearPlan.$invalid || !adminCtrl.formCrearPlan.nombrePlan || !adminCtrl.formCrearPlan.dias"
								ng-click="adminCtrl.createPlan()">Crear</button>
					</div>
					<div class="form-group col-xs-12">
						<label class="titleEmployees">Lista de Planes</label>
					</div>
					<div data-ng-if="adminCtrl.listPlanes.length > 0" class="form-group col-xs-12 table-employees">
						<table data-ng-if="adminCtrl.listPlanes.length > 0" class="table table-bordered">
							<thead>
							<tr>
								<th>Nombre Plan</th>
								<th>Periocidad</th>
							</tr>
							</thead>
							<tbody>
							<tr data-ng-repeat="listPlanes in adminCtrl.listPlanes">
								<td>{{listPlanes.nombre}}</td>
								<td>{{listPlanes.periocidad}}</td>
							</tr>
							</tbody>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-12">
			<div class="panel panel-default" id="divAsignarPlan">
				<div class="panel-heading">
					<span class="lead">Asignar Plan</span>
				</div>
				<div class="panel-body">
					<form class="form_asignar_plan" role="form" name="formAsignarPlan">
						<div class="row">
							<div class="form-group col-xs-12 col-sm-6">
								<label for="planEmpresa">Empresa</label>
								<select id="planEmpresa"
										class="form-control margin-select" name="planEmpresa" placeholder="empresa" data-ng-model='adminCtrl.formAsignarPlan.planEmpresa' required
										data-ng-class='{ error: formAsignarPlan.planEmpresa.$dirty && formAsignarPlan.planEmpresa.$invalid }'>
									<option style="display:none" value="">Seleccione una empresa</option>
									<option ng-repeat="listEmpresas in adminCtrl.listEmpresas" value="{{listEmpresas.idEmpresa}}">{{listEmpresas.razonSocial}}</option>
								</select>
								<span ng-show="formAsignarPlan.planEmpresa.$dirty && formAsignarPlan.planEmpresa.$invalid" style="opacity: 0.3;">Seleccione una empresa</span>
							</div>
							<div class="form-group col-xs-12 col-sm-6">
								<label for="planAsignar">Asignar Plan</label>
								<select id="planAsignar"
										class="form-control margin-select" name="planAsignar" placeholder="planAsignar" data-ng-model='adminCtrl.formAsignarPlan.planAsignar' required
										data-ng-class='{ error: formAsignarPlan.planAsignar.$dirty && formAsignarPlan.planAsignar.$invalid }'>
									<option style="display:none" value="">Seleccione un plan</option>
									<option ng-repeat="listPlanes in adminCtrl.listPlanes" value="{{listPlanes.id}}">{{listPlanes.nombre}}</option>
								</select>
								<span ng-show="formAsignarPlan.planAsignar.$dirty && formAsignarPlan.planAsignar.$invalid" style="opacity: 0.3;">Seleccione una plan</span>
							</div>
						</div>
						<div class="alert alert-warning" role="alert"
							 data-ng-if="adminCtrl.errorAsignandoPlanAEmpresa">
							<h4 class="error_message">{{adminCtrl.errorAsignandoPlanAEmpresa}}</h4>
						</div>
						<div class="modal-footer">
							<button class="btn btn-primary"
									ng-disabled="formAsignarPlan.$invalid || !adminCtrl.formAsignarPlan.planAsignar || !adminCtrl.formAsignarPlan.planEmpresa"
									ng-click="adminCtrl.asignarPlanAEmpresa()">Asignar</button>
						</div>
						<div class="form-group col-xs-12">
							<label class="titleEmployees">Lista de Planes Por Empresa</label>
						</div>
						<div data-ng-if="adminCtrl.listPlanesPorEmpresa.length > 0" class="form-group col-xs-12 table-employees">
							<table data-ng-if="adminCtrl.listPlanesPorEmpresa.length > 0" class="table table-bordered">
								<thead>
								<tr>
									<th>Empresa</th>
									<th>Nombre de Plan</th>
								</tr>
								</thead>
								<tbody>
								<tr data-ng-repeat="listPlanesPorEmpresa in adminCtrl.listPlanesPorEmpresa">
									<td>{{listPlanesPorEmpresa.nombreEmpresa}}</td>
									<td>{{listPlanesPorEmpresa.nombrePlan}}</td>
								</tr>
								</tbody>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
    <div class="col-md-12">
        <div class="panel panel-default" id="divCrearEmp">
            <div class="panel-heading">
                <span class="lead">Registrar empresa convenio</span>
            </div>
            <div class="panel-body">
                <form class="form_crear_empresa" role="form" name="formCrearEmp" method="post" action="crearEmpresaV2">
					<div class="row">
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="tipoDocumento">Tipo de documento</label>
	                        <select id="tipoDocumento"
	                            class="form-control margin-select"
	                            name="tipoDocumento"
	                            placeholder="Tipo de documento"
	                            data-ng-model='adminCtrl.formData.tipoDocumento'
	                            required
	                            data-ng-class='{ error: formCrearEmp.tipoDocumento.$dirty && formCrearEmp.tipoDocumento.$invalid }'>
		                            <option style="display:none" value="">Seleccione una opci&oacute;n</option>
		                            <option value="6">NIT</option>
		                            <option value="1">C&eacute;dula</option>
		                            <option value="5">C&eacute;dula de extranjer&iacute;a</option>
	                        </select>
	                        <span ng-show="formCrearEmp.tipoDocumento.$dirty && formCrearEmp.tipoDocumento.$invalid" style="opacity: 0.3;">Seleccione una opción</span>
	                    </div>
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="NIT">N&uacute;mero de documento</label>
	                        <input type="text"
	                        	data-ng-pattern="/^[0-9]{5,15}$/"
	                            class="form-control" id="NIT" name="NIT"
	                            placeholder="Ingresa el numero de documento"
	                            data-ng-model='adminCtrl.formData.NIT' required
	                            data-ng-class='{ error: formCrearEmp.NIT.$dirty && formCrearEmp.NIT.$invalid}'>
                            <span ng-show="formCrearEmp.NIT.$dirty && formCrearEmp.NIT.$invalid" style="opacity: 0.3;">Número entre 5 y 15 dígitos</span>
	                    </div>
                    </div>
                    <div class="row">
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="razonSocial">Raz&oacute;n social de la empresa</label>
	                        <input type="text"
	                        	data-ng-pattern="/^[a-zA-ZáéíóúÁÉÍÓÚÑñ0-9\s]+$/" 
	                            class="form-control" id="razonSocial" name="razonSocial"
	                            placeholder="Ingresa la raz&oacute;n social de la empresa"
	                            data-ng-model='adminCtrl.formData.razonSocial' required
	                            data-ng-class='{ error: formCrearEmp.razonSocial.$dirty && formCrearEmp.razonSocial.$invalid }'>
                            <span ng-show="formCrearEmp.razonSocial.$dirty && formCrearEmp.razonSocial.$invalid" style="opacity: 0.3;">Texto alfanumérico con espacios</span>
	                    </div>
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="telefono">Tel&eacute;fono</label>
	                        <input type="text"
	                        	data-ng-pattern="/^[0-9]{7,10}$/"
	                        	oninvalid="setCustomValidity('Formato inválido: Número entre 7 y 10 dígitos')"
	                            class="form-control" id="telefono" name="telefono"
	                            placeholder="Ingresa el tel&eacute;fono de la empresa"
	                            data-ng-model='adminCtrl.formData.telefono' required
	                            data-ng-class='{ error: formCrearEmp.telefono.$dirty && formCrearEmp.telefono.$invalid }'>
                            <span ng-show="formCrearEmp.telefono.$dirty && formCrearEmp.telefono.$invalid" style="opacity: 0.3;">Número entre 7 y 10 dígitos</span>
	                    </div>
                    </div>
                    <div class="row">
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="email">Email</label>
	                        <input type="text"
	                        	data-ng-pattern="/^[a-zA-Z0-9._-]+@[a-z0-9.-]+\.[a-z]{2,4}$/"
	                            class="form-control" id="email" name="email"
	                            placeholder="Ingresa el email del contacto de la empresa"
	                            data-ng-model='adminCtrl.formData.email' required
	                            data-ng-class='{ error: formCrearEmp.email.$dirty && formCrearEmp.email.$invalid }'>
                            <span ng-show="formCrearEmp.email.$dirty && formCrearEmp.email.$invalid" style="opacity: 0.3;">Dirección de correo electrónico</span>
	                    </div>
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="nombreRepresentante">Nombre del contacto comercial</label>
	                        <input
	                        	data-ng-pattern="/^[a-zA-ZáéíóúÁÉÍÓÚÑñ0-9\s]+$/" 
	                            type="text" class="form-control" id="nombreRepresentante" name="nombreRepresentante"
	                            placeholder="Ingresa el nombre del contacto comercial"
	                            data-ng-model='adminCtrl.formData.nombreRepresentante' required
	                            data-ng-class='{ error: formCrearEmp.nombreRepresentante.$dirty && formCrearEmp.nombreRepresentante.$invalid }'>
                            <span ng-show="formCrearEmp.nombreRepresentante.$dirty && formCrearEmp.nombreRepresentante.$invalid" style="opacity: 0.3;">Texto alfanumérico con espacios</span>
	                    </div>
                    </div>
                    <div class="row">
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="membresia">Membresía</label>
	                        <select id="membresia"
	                            class="form-control margin-select" name="membresia" placeholder="Membresía" data-ng-model='adminCtrl.formData.membresia' required
	                            data-ng-class='{ error: formCrearEmp.membresia.$dirty && formCrearEmp.membresia.$invalid }'>
		                            <option style="display:none" value="">Seleccione una opci&oacute;n</option>
		                            <option ng-repeat="membresia in adminCtrl.listMembresias" value="{{membresia.id}}">{{membresia.nombre}}</option>
	                        </select>
	                        <span ng-show="formCrearEmp.membresia.$dirty && formCrearEmp.membresia.$invalid" style="opacity: 0.3;">Seleccione una opción</span>
	                    </div>
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="textoEmail">Texto del envío Email</label>
	                        <input
	                        	type="text" class="form-control" id="textoEmail" name="textoEmail" 
	                        	placeholder="Ingresa el texto que va a ser enviado a los afiliados en el email"
	                            data-ng-model='adminCtrl.formData.textoEmail'>
	                    </div>
                    </div>
                    <div class="row">
	                    <div class="form-group col-xs-12 col-sm-6">
	                        <label for="logo">Logo (jpg, png)</label>
	                        <input 
	                        	id="logo" name="logo" type='file' class="form-control" 
	                        	ng-model-instant onchange="angular.element(this).scope().adminCtrl.imageUpload()" required
	                        	data-ng-class='{ error: formCrearEmp.logo.$dirty && formCrearEmp.logo.$invalid }'/>
                        	<span ng-show="formCrearEmp.logo.$dirty && formCrearEmp.logo.$invalid" style="opacity: 0.3;">Archivo en formato jpg ó png</span>
	                    </div>
	                    <div class="form-group col-xs-12 col-sm-6">
							<img class="thumb" ng-src="{{adminCtrl.formData.logoImagen}}" alt="Imágen del logo" data-ng-if="adminCtrl.formData.logoImagen" style="max-width: 150px; max-height: 150px;"/>
	                    </div>
					</div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input id="logoImagen" type="hidden" name="logoImagen" value="{{adminCtrl.formData.logoImagen}}"/>
                    <div class="form-group col-xs-12">
                        <button type="submit" class="btn btn-primary" ng-disabled="formCrearEmp.$invalid  || !adminCtrl.formData.logoImagen">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </section>
</div>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
    crossorigin="anonymous"></script>
<script src="${angularJs}"></script>
<script src="${base64js}"></script>
<script src="${datePickerJs}"></script>
<script src="${servicesJs}"></script>
<script src="${providerJs}"></script>
<script src="${csvProvider}"></script>
<script src="${angularresJs}"></script>
<script src="${starterJs}"></script>
<script src="${adminJs}"></script>
</body>
</html>