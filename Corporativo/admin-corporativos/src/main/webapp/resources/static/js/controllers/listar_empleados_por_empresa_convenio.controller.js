(function() {
	'use strict';

	angular.module('servicesModule').controller('listarEmpleadosEmpresaController',
			listarEmpleadosEmpresaController);
 
	listarEmpleadosEmpresaController.$inject = [ 'adminService', '$location', 'csvProvider'];

	function listarEmpleadosEmpresaController(adminService, $location, csvProvider) {
		var vm = this;
		vm.dates = {};
		vm.tarifas = {};
		vm.formData = {};
		vm.bottomImport = {};
		vm.afiliadoSelected = null;
		vm.logout = logout;
		vm.downloadDoc = downloadDoc;
		vm.loadEmployees = loadEmployees;
		vm.loadDiscountCodes = loadDiscountCodes;
		vm.changeView = adminService.goPath;
		vm.assignAfiliado = assignAfiliado;
		vm.deleteAfiliado = deleteAfiliado;
		vm.clickImport = clickImport;

		onInit();

		function onInit() {
			getParameters(window.location.pathname);
			vm.bottomImport.value = "Importar";
			vm.bottomImport.active = true;
		}
		
		function logout() {
			adminService.logout();
		}
		
		function assignAfiliado(item, codigoAsignado){
			//vm.afiliadoSelected = Object.assign({}, item);
			vm.afiliadoSelected = item;
			vm.afiliadoSelected.codigoAsignado = codigoAsignado;
		}
		
		function getParameters(pathName){
			var indexInit = pathName.indexOf('&');
			var indexEnd = pathName.length;
			if(indexInit > 0) {
				indexInit+=1;
				var subStr = pathName.substring(indexInit, indexEnd);
				if(subStr.length > 0){
					var data = subStr.split('&');
					vm.dataCompany = {
						tipo: data[0],
						id: data[1],
						membresia: data[2],
					};
					console.info('vm.dataCompany',vm.dataCompany);
					adminService.getAfiliadosEmpresaConvenio(data[0], data[1], data[2])
					.then(function(response) {
						if(response.codigoRespuesta === "500"){
							alert("Ha ocurrido un error interno, por favor intentelo de nuevo mas tarde.")
							return;
						}
						vm.data = response.empleados;
						vm.empresa = response.empresa;
						vm.formData.empresaTipoDoc = response.empresa.documentoTipo;
						vm.formData.empresaNumDoc = response.empresa.documentoNumero;
						vm.formData.empresaMembr = response.empresa.membresia;
						vm.numeroCodigosDisponibles = response.numeroCodigosDisponibles;
					});
				}
			}
		}

		function loadDiscountCodes() {
            var fileInput = document.getElementById("csvDiscountCodes");
            
            csvProvider.loadCsv(fileInput).then(function(result) {
                vm.formData.codigosCsv = result;
            }).catch(function(fallback) {
            	vm.formData.codigosCsv = '';
            	fileInput.value = '';
            });
            
            csvProvider.loadCsvAsString(fileInput).then(function(result) {
            	let numColum = result[0].split(',').length;
            	if( numColum != 1 ) {
            		alert('Error en la cantidad de columnas del archivo.');
            		vm.formData.codigosCsv = '';
            		fileInput.value = '';
            		return;
            	}
                vm.tableDataDiscountCodes = result;
            });
        }
		
		function loadEmployees() {
            var fileInput = document.getElementById("csv");
            
            csvProvider.loadCsv(fileInput).then(function(result) {
                vm.formData.afiliadosCsv = result;
            }).catch(function(fallback) {
            	vm.formData.afiliadosCsv = '';
            	fileInput.value = '';
            });
            
            csvProvider.loadCsvAsString(fileInput).then(function(result) {
            	let numColum = result[0].split(',').length;
				if( numColum != 5 ) {
            		alert('Error en la cantidad de columnas del archivo.');
            		vm.formData.afiliadosCsv = '';
            		fileInput.value = '';
            		return;
            	}
            });
        }

		function downloadDoc() {
			if(vm.dates.start && vm.dates.end) {
				if(vm.dates.end.localeCompare(vm.dates.start) === 1) {
					vm.errorDOwnload = undefined;
					adminService.getAfiliadosEmpresaConvenio(vm.dataCompany.tipo, vm.dataCompany.id, vm.dataCompany.membresia, vm.dates.start,vm.dates.end)
						.then(function(response){
							if(response.codigoRespuesta === "500"){
								alert("Ha ocurrido un error interno, por favor intentelo de nuevo mas tarde.")
								return;
							}
							var encodedUri = "data:text/csv;base64," + response.csvEmpleados;	
							csvProvider.download("empleados.csv", encodedUri);
						});
				} else {
					vm.errorDOwnload = "La fecha final debe ser posterior a la fecha inicial";
				}
			} else {
				vm.errorDOwnload = "Se debe seleccionar un rango de fechas";
			}
		}
		
		function deleteAfiliado() {
			adminService.deleteAfiliadoById(vm.afiliadoSelected.id)
			.then(function(response) {
				if(response.codigoRespuesta === "500"){
					alert("Ha ocurrido un error interno, por favor intentelo de nuevo mas tarde.");
					return;
				} else {
					alert("El afiliado " + item.nombre + "fué eliminado correctamente");
					vm.afiliadoSelected = null;
				}

			}).catch(function(err){
                 alert('Error. Por favor toma un pantallazo y comunicate con el administrador: ' + err);
            });
		}

		function clickImport() {
			vm.bottomImport.active = false;
			vm.bottomImport.value = "Cargando ...";
		}
	}
})();
