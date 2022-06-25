(function() {
	'use strict';

	angular.module('servicesModule').controller('listarEmpresaConvenioController',
			listarEmpresaConvenioController);
 
	listarEmpresaConvenioController.$inject = [ 'adminService', '$location', 'csvProvider' ];

	function listarEmpresaConvenioController(adminService, $location, csvProvider) {
		var vm = this;
		vm.companies = [];
		vm.dates = {};
		vm.goDetail = goDetail;
		vm.logout = logout;
		vm.changeView = adminService.goPath;
		vm.goDetail = goDetail;
		vm.downloadDoc = downloadDoc;

		onInit();

		function onInit() {
			adminService.getEmpresasConvenio()
				.then(function(response){
					if(response.codigoRespuesta === "500"){
						alert("Ha ocurrido un error interno, por favor intenta de nuevo mas tarde.")
						return;
					}
					vm.companies = response.empresas;
				});
		}
 
		function goDetail(path, company) {
			adminService.goPath(path + company.documentoTipo + '&' + company.documentoNumero + '&' + company.membresia);
		}
		
		function logout() {
			adminService.logout();
		}

		function downloadDoc() {
			if(vm.dates.start && vm.dates.end) {
				if(vm.dates.end.localeCompare(vm.dates.start) === 1) {
					vm.errorDOwnload = undefined;
					adminService.getAfiliadosByRange(vm.dates.start,vm.dates.end)
						.then(function(response){
							if(response.codigoRespuesta === "500"){
								alert("Ha ocurrido un error interno, por favor intenta de nuevo mas tarde.")
								return;
							}
							var encodedUri = "data:text/csv;base64," + response.csvCodigosAsignados;					
							csvProvider.download("empleados.csv", encodedUri);
						});
				} else {
					vm.errorDOwnload = "La fecha final debe ser posterior a la fecha inicial";
				}
				
			} else {
				vm.errorDOwnload = "Se debe seleccionar un rango de fechas";
			}
			
		}		
	}
})();
