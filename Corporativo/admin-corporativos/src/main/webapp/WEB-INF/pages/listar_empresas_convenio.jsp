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
<spring:url value="/resources/css/angular-datepicker.min.css" var="datePickerCss" />
<spring:url value="/webjars/angularjs/1.6.4/angular.js" var="angularJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-resource.js" var="angularresJs" />
<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-base64/2.0.5/angular-base64.min.js" var="base64js" />
<spring:url value="/resources/js/library/angular-datepicker.min.js" var="datePickerJs" />
<spring:url value="/resources/js/app.module.convenios.js" var="starterJs" />
<spring:url value="/resources/js/providers/csv.provider.js" var="csvProvider" />
<spring:url
value="/resources/js/controllers/listar_empresas_convenio.controller.js"
var="adminJs" />
<spring:url value="/resources/js/services/admin.services.js"
var="servicesJs" />
<spring:url value="/resources/js/providers/storage.provider.js"
var="providerJs" />
<spring:url value="/resources/img/logoFooter.png" var="logoFooter" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listar Empresas Convenio</title>
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
<link href="${datePickerCss}" rel="stylesheet" />

</head>
<body data-ng-app="app">
<div data-ng-controller="listarEmpresaConvenioController as adminCtrl">
    <jsp:include page="header.jsp"></jsp:include>

    <section>
        <div class="panel panel-default">
            <div class="panel-body">
                <ol class="breadcrumb section-menu">
                    <li class="active"><a ng-click="adminCtrl.changeView('crear_convenio_empresa')">Crear Empresa</a></li>
                    <li><a>Listar Empresas</a></li>
                    <li><a ng-click="adminCtrl.changeView('validar_documentos')">Validar</a></li>
                </ol>
            </div>
        </div>
    </section>
    <section class="generic-container">
    <div class="col-md-12">
        <div class="panel panel-default" id="divCrearEmp">
            <div class="panel-heading">
                <span class="lead">Listar Empresas Convenio</span>
                <a class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalEmpleExportAll">Exportar empleados</a>
            </div>
            <div class="panel-body">
            	<input type="text"
                    class="form-control" name="document"
                    placeholder="Buscar..."
                    data-ng-model='adminCtrl.formData.search'>

                    <div class="companies">
                        <table data-ng-if="adminCtrl.companies.length > 0" class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Raz&oacute;n social</th>
                                    <th>Membresía</th>
                                    <th>Tipo de documento</th>
                                    <th>Documento</th>
                                    <th>Ver empleados</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr data-ng-repeat="company in adminCtrl.companies | filter: { razonSocial: adminCtrl.formData.search }">
                                    <td>{{company.razonSocial}}</td>
                                    <td>{{company.membresiaNombre}}</td>
                                    <td>{{company.documentoTipoNombre}}</td>
                                    <td>{{company.documentoNumero}}</td>
                                    <td>
                                        <a ng-click="adminCtrl.goDetail('listar_convenio_afiliado/&',company)" style="cursor: pointer;"><span class="glyphicon glyphicon-chevron-right"></span></a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>
    </div>
    </section>
    
    <!-- START Modal Afiliado Export All -->
    <div class="modal fade" id="modalEmpleExportAll" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Exportar empleados</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                    	<div class="col-md-4">
                    		<h4>Rango de fechas:</h4>
                   		</div>
                   		<div class="col-md-4">
		                    <datepicker class="" date-format="yyyy-MM-dd">
		                        <input ng-model="adminCtrl.dates.start" type="text" placeholder="Fecha Inicial"/>
		                    </datepicker>
	                    </div>
                   		<div class="col-md-4">
		                    <datepicker class="" date-format="yyyy-MM-dd">
		                        <input ng-model="adminCtrl.dates.end" type="text" placeholder="Fecha Final"/>
		                    </datepicker>
	                    </div>
                    </div>
                    <div class="row">
                    	<div class="col-md-12">
                    		<p class="bg-danger" ng-if="adminCtrl.errorDOwnload">{{adminCtrl.errorDOwnload}}</p>
                   		</div>
               		</div>
                </div>
                <div class="modal-footer">
                	<button class="btn btn-primary" ng-click="adminCtrl.downloadDoc()">Exportar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
        </div>
    </div>
    <!-- END Modal Afiliado Export All -->
    
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
<script src="${angularresJs}"></script>
<script src="${starterJs}"></script>
<script src="${adminJs}"></script>
<script src="${csvProvider}"></script>
</body>
</html>