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
<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-base64/2.0.5/angular-base64.min.js" var="base64js" />
<spring:url value="/resources/js/library/angular-datepicker.min.js" var="datePickerJs" />
<spring:url value="/webjars/angularjs/1.6.4/angular-resource.js"
var="angularresJs" />
<spring:url value="/resources/js/app.module.convenios.js" var="starterJs" />
<spring:url value="/resources/js/providers/csv.provider.js" var="csvProvider" />
<spring:url
value="/resources/js/controllers/listar_empleados_por_empresa_convenio.controller.js"
var="adminJs" />
<spring:url value="/resources/js/services/admin.services.js"
var="servicesJs" />
<spring:url value="/resources/js/providers/storage.provider.js"
var="providerJs" />
<spring:url value="/resources/img/logoFooter.png" var="logoFooter" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de empleados</title>
<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
crossorigin="anonymous">

<link href="${datePickerCss}" rel="stylesheet" />
<link href="${mainCss}" rel="stylesheet" />
<link href="${secondCss}" rel="stylesheet" />

</head>
<body data-ng-app="app">
<div data-ng-controller="listarEmpleadosEmpresaController as adminCtrl">
    <jsp:include page="header.jsp"></jsp:include>
    <section>
        <div class="panel panel-default">
            <div class="panel-body">
                <ol class="breadcrumb section-menu">
                    <li class="active" ng-click="adminCtrl.changeView('crear_convenio_empresa')"><a>Crear Empresa</a></li>
                    <li ng-click="adminCtrl.changeView('listar_convenio_empresas')"><a>Listar Empresas</a></li>
                    <li><a ng-click="adminCtrl.changeView('validar_documentos')">Validar</a></li>
                </ol>
            </div>
        </div>
    </section>
    <section class="generic-container">
    <div class="col-md-12">
        <div class="panel panel-default" id="divCrearEmp">
            <div class="panel-heading">
                <span class="lead">Lista de empleados</span>
                <a class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalEmpleImport" style="margin-left: 0.5em;">Importar empleados</a>
                <a class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalEmpleExport" style="margin-left: 0.5em;">Exportar empleados</a>
                <br/><br/>
                <a class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalCodImport" style="margin-left: 0.5em;">Importar códigos</a>
                <br/><br/>
            </div>
            <div class="panel-body">
            	<div class="row">
            		<div class="form-group col-xs-8">
		            	<div class="row">
			                <div class="form-group col-xs-6">
			                    <label>Raz&oacute;n social: </label>
			                    <label class="data">{{adminCtrl.empresa.razonSocial}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>Tipo de documento: </label>
			                    <label class="data">{{adminCtrl.empresa.documentoTipoNombre}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>N&uacute;mero de documento: </label>
			                    <label class="data">{{adminCtrl.empresa.documentoNumero}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>Email: </label>
			                    <label class="data">{{adminCtrl.empresa.email}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>Telefono: </label>
			                    <label class="data">{{adminCtrl.empresa.telefono}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>Representante Legal: </label>
			                    <label class="data">{{adminCtrl.empresa.representanteLegal}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>Membresia: </label>
			                    <label class="data">{{adminCtrl.empresa.membresiaNombre}}</label>
			                </div>
			                <div class="form-group col-xs-6">
			                    <label>Texto Email: </label>
			                    <label class="data">{{adminCtrl.empresa.textoEmail}}</label>
			                </div>
		                </div>
	                </div>
	                <div class="form-group col-xs-4">
	                	<div class="row">
	                		<div class="form-group col-xs-12">
			                    <label>Códigos disponibles: </label>
			                    <label class="data">{{adminCtrl.numeroCodigosDisponibles}}</label>
			                </div>
	                	</div>
	                	<div class="row">
	                		<div class="form-group col-xs-12">
			                    <label>Logo: </label>
			                    <br/>
			                    <img class="thumb" alt="Imágen del logo" style="max-width: 150px; max-height: 150px;" ng-src="/Admin/upload/image/{{adminCtrl.empresa.logo}}/">
			                </div>
	                	</div>
	                </div>
                </div>
                
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>N&uacute;mero documento</th>
                            <th>Nombre</th>
                            <th>email</th>
                            <th>Fecha asignaci&oacute;n</th>
                            <th>C&oacute;digo</th>
                            <th>periodicidad</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody data-ng-repeat="empleado in adminCtrl.data">
                        <tr data-ng-repeat="codigos in empleado.codigosAsignados">
                            <td>{{empleado.documentoNumero}}</td>
                            <td>{{empleado.nombre}}</td>
                            <td>{{empleado.email}}</td>
                            <td>{{codigos.fechaAsignacion | date :  "dd-MM-yyyy"}}</td>
                            <td>{{codigos.codigo}}</td>
                            <td>{{codigos.periodicidad}}</td>
                            <td>
                            	<button class="btn btn-danger" type="button" data-toggle="modal" data-target="#modalEmpleDelete" ng-click="adminCtrl.assignAfiliado(empleado, codigos.codigo)" data-backdrop="static" data-keyboard="false" title="Eliminar">
									<i class="glyphicon glyphicon-trash" title="Eliminar"></i>
								</button>
							</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </section>
    
    <!-- START Modal Afiliado Import -->
    <div class="modal fade" id="modalEmpleImport" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Importar empleados</h4>
                </div>
                <div class="modal-body">
                    <form class="form_crear_empresa" role="form" name="formEmpleImport" id="formEmpleImport" method="post" action="procesarCsvConvenioAfiliados">
                        <div class="row">
	                        <div class="form-group col-md-12">
	                            <label for="employees">Archivo CSV</label>
	                            <input id="csv" type="file" class="form-control" ng-model-instant onchange="angular.element(this).scope().adminCtrl.loadEmployees()" required>
	                        </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input id="empresaTipoDoc" type="hidden" name="empresaTipoDoc" value="{{adminCtrl.formData.empresaTipoDoc}}"/>
                        <input id="empresaNumDoc" type="hidden" name="empresaNumDoc" value="{{adminCtrl.formData.empresaNumDoc}}"/>
                        <input id="empresaMembr" type="hidden" name="empresaMembr" value="{{adminCtrl.formData.empresaMembr}}"/>
                        <input id="afiliadosCsv" type="hidden" name="afiliadosCsv" value="{{adminCtrl.formData.afiliadosCsv}}"/>
                    </form>
                </div>
                <div class="modal-footer">
                	<button type="submit" class="btn btn-primary" form="formEmpleImport"
                            ng-disabled="formEmpleImport.$invalid || !adminCtrl.formData.afiliadosCsv || !adminCtrl.bottomImport.active"
                            ng-click="adminCtrl.clickImport()">{{adminCtrl.bottomImport.value}}</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
            
        </div>
    </div>
    <!-- END Modal Afiliado Import -->
    
    <!-- START Modal Afiliado Export -->
    <div class="modal fade" id="modalEmpleExport" role="dialog">
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
    <!-- END Modal Afiliado Export -->
    
    <!-- START Modal Afiliado Delete -->
    <div class="modal fade hmodal-danger" id="modalEmpleDelete" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Eliminar empleado</h4>
                </div>
                <div class="modal-body">
                	<form id="formDelete" role="form" method="post" action="eliminarAfiliado">
                		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input id="afiliadoId" type="hidden" name="afiliadoId" value="{{adminCtrl.afiliadoSelected.id}}"/>
                        <input id="codigoAsignado" type="hidden" name="codigoAsignado" value="{{adminCtrl.afiliadoSelected.codigoAsignado}}"/>
                    </form>

                    <p>Estas seguro de eliminar el empleado: <strong>{{adminCtrl.afiliadoSelected.documentoNumero}} - {{adminCtrl.afiliadoSelected.nombre}}</strong></p>
                </div>
                <div class="modal-footer">
                	<button class="btn btn-danger" form="formDelete" type="submit">Eliminar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
            
        </div>
    </div>
    <!-- END Modal Afiliado Delete -->
    
    <!-- START Modal CodigoDescuento Import -->
    <div class="modal fade" id="modalCodImport" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Importar códigos</h4>
                </div>
                <div class="modal-body">
                    <form class="form_crear_empresa" role="form" name="formCodImport" id="formCodImport" method="post" action="procesarCsvConvenioCodigosDescuento">
                        <div class="row">
	                        <div class="form-group col-md-12">
	                            <label for="employees">Archivo CSV</label>
	                            <input id="csvDiscountCodes" type="file" class="form-control" ng-model-instant onchange="angular.element(this).scope().adminCtrl.loadDiscountCodes()" required>
	                        </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input id="empresaTipoDoc" type="hidden" name="empresaTipoDoc" value="{{adminCtrl.formData.empresaTipoDoc}}"/>
                        <input id="empresaNumDoc" type="hidden" name="empresaNumDoc" value="{{adminCtrl.formData.empresaNumDoc}}"/>
                        <input id="empresaMembr" type="hidden" name="empresaMembr" value="{{adminCtrl.formData.empresaMembr}}"/>
                        <input id="codigosCsv" type="hidden" name="codigosCsv" value="{{adminCtrl.formData.codigosCsv}}"/>
                    </form>
                </div>
                <div class="modal-footer">
                	<button type="submit" class="btn btn-primary" form="formCodImport" ng-disabled="formCodImport.$invalid || !adminCtrl.formData.codigosCsv">Importar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </div>
            
        </div>
    </div>
    <!-- END Modal CodigoDescuento Import -->
    
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
