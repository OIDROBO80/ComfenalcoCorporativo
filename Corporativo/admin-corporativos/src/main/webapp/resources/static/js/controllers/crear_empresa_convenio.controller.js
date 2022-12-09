(function() {
    'use strict';

    angular.module('servicesModule').controller('crearEmpresaNominaController', crearEmpresaNominaController).controller(
            'crearEmpresaRespuestaController', crearEmpresaRespuestaController).filter('pos', function() {
        return function(input, position) {
            var data = input.split(",")[position];
            if (data)
                data = data.replace(/["']/g, "");
            return data;
        }
    });

    crearEmpresaNominaController.$inject = [ '$scope', 'adminService', '$location', 'csvProvider' ];

    function crearEmpresaNominaController($scope, adminService, $location, csvProvider) {
        var vm = this;
        vm.formData = {};
        vm.formCrearPlan ={}
        vm.formAsignarPlan= {}
        vm.listMembresias = {};
        vm.tableData = [];
        vm.listPlanes = [];
        vm.listEmpresas = [];
        vm.listPlanesPorEmpresa = [];
        vm.tableDataDiscountCodes = [];
        vm.errorCreatingPlan='';
        vm.errorAsignandoPlanAEmpresa='';
        vm.rolesByUser=[];
        vm.canCreatePlan =false;

        vm.logout = logout;
        vm.loadDiscountCodes = loadDiscountCodes;
        vm.loadEmployees = loadEmployees;
        vm.changeView = adminService.goPath;
        vm.imageUpload = imageUpload;
        vm.createPlan = createPlan;
        vm.asignarPlanAEmpresa=asignarPlanAEmpresa;

        onInit();

        function onInit() {
            getInformationInitialToCreateCompany();
        }

        function logout() {
            adminService.logout();
        }
        
        function imageUpload() {
        	var fileInput = document.getElementById("logo");

            csvProvider.loadImage(fileInput)
            .then(function(result) {
                vm.formData.logoImagen = result;
            }).catch(function(fallback) {
            	vm.formData.logoImagen = null;
                fileInput.value = '';
            });
        }

        function createPlan() {
            console.info('Dioclick',vm.formCrearPlan);
            adminService.createPlan(vm.formCrearPlan.nombrePlan,vm.formCrearPlan.dias).then(function(response) {
                if (response.codigoRespuesta === 200) {
                    vm.errorCreatingPlan = '';
                    vm.listPlanes = response.listPlanesActuales;
                } else {
                    vm.errorCreatingPlan=response.description;
                }
            });
        }

        function asignarPlanAEmpresa() {
            console.info('Dioclick asignarPlanAEmpresa',vm.formAsignarPlan);
            adminService.asignarPlanAEmpresa(vm.formAsignarPlan.planAsignar,vm.formAsignarPlan.planEmpresa).then(function(response) {
                console.info('response asignarPlanAEmpresa ',response);
                if (response.codigoRespuesta === 200) {
                    vm.errorAsignandoPlanAEmpresa = '';
                    vm.listPlanesPorEmpresa = response.listPlanesPorEmpresaActualizado;
                } else {
                    vm.errorAsignandoPlanAEmpresa=response.description;
                }
            });

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
            		vm.tableDataDiscountCodes = '';
            		vm.formData.codigosCsv = '';
            		fileInput.value = '';
            		return;
            	}
                vm.tableDataDiscountCodes = result;
            }).catch(function(fallback) {
            	vm.tableDataDiscountCodes = '';
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
            	if( numColum != 4 ) {
            		alert('Error en la cantidad de columnas del archivo.');
            		vm.tableData = '';
            		vm.formData.afiliadosCsv = '';
            		fileInput.value = '';
            		return;
            	}
                vm.tableData = result;
                vm.tableData.shift();
            }).catch(function(fallback) {
            	vm.tableData = '';
            });
        }

        function getInformationInitialToCreateCompany() {
            adminService.getInformationInitialToCreateCompany().then(function(response) {
                if (response.codigoRespuesta === "500") {
                    alert("Ha ocurrido un error interno, por favor intenta de nuevo más tarde.")
                    return;
                }

                vm.listMembresias = response.listMembresias;
                vm.listPlanesPorEmpresa = response.listPlanesPorEmpresa;
                vm.listPlanes = response.listPlanes;
                vm.listEmpresas = response.listEmpresas;
                vm.canCreatePlan = response.canCreatePlan;
            });
        }
    }

    crearEmpresaRespuestaController.$inject = [ 'adminService', 'csvProvider' ];

    function crearEmpresaRespuestaController(adminService, csvProvider) {
        var vm = this;
        vm.tableFallidos = [];
        vm.tableSuccess = [];
        vm.logout = logout;
        vm.downloadFallidos = downloadFallidos;
        vm.changeView = adminService.goPath;

        onInit();

        function onInit() {
            var codigoError = document.getElementById("codigoError").value;
            var csvAfiliadosFallidos = document.getElementById("csvAfiliadosFallidos").value;
            var csvAfiliadosCompletos = document.getElementById("csvAfiliadosCompletos").value;

            if (csvAfiliadosFallidos) {
            	console.log('DEB: ' , tableFallidos);
                vm.csvAfiliadosFallidos = csvAfiliadosFallidos;
                var tableFallidos = csvProvider.decodeCsv(csvAfiliadosFallidos);
                if (tableFallidos[tableFallidos.length - 1] === '\n') {
                    tableFallidos = tableFallidos.slice(0, -1);
                }
                tableFallidos = tableFallidos.split('\n');
                tableFallidos.shift();
                vm.tableFallidos = tableFallidos;
            }
            if (csvAfiliadosCompletos) {
                var tableSuccess = csvProvider.decodeCsv(csvAfiliadosCompletos);
                if (tableSuccess[tableSuccess.length - 1] === '\n') {
                    tableSuccess = tableSuccess.slice(0, -1);
                }
                tableSuccess = tableSuccess.split('\n');
                tableSuccess.shift();
                vm.tableSuccess = tableSuccess;
            }
            if (codigoError) {
                vm.error = errores[codigoError];
                if (!vm.error) {
                    vm.error = errores['ERR_GENERIC'];
                }
            }
        }

        function downloadFallidos() {
            if (vm.csvAfiliadosFallidos) {
                var encodedUri = "data:text/csv;base64," + vm.csvAfiliadosFallidos;
                csvProvider.download("empleadosFallidos.csv", encodedUri);
            }
        }

        function logout() {
            adminService.logout();
        }
    }

    var errores = {
        ERR_NEG_EXIST : 'Ha ocurrido un error, la empresa ya ha sido registrada.',
        ERR_GENERIC : 'Ha ocurrido un error, por favor intentalo de nuevo',
        ERR_CA_CCE : 'Ha ocurrido un error, por favor intentalo de nuevo',
        ERR_CA_ACC : 'Ha ocurrido un error al asignar el codigo de convenio, por favor intentalo de nuevo',
        ERR_CA_ACC_ACT : 'Ha ocurrido un error al asignar el codigo de convenio, por favor intentalo de nuevo',
        ERROR_CSV_READ : 'Ha ocurrido un error al procesar el archivo de empleados, por favor intentalo de nuevo',
        ERR_NEG_REQ_NULL : 'Los datos no han sido diligenciados correctamente, por favor intentalo de nuevo',
        ERR_NEG_NULL : 'Los datos no han sido diligenciados correctamente, por favor intentalo de nuevo',
        ERR_NEG_CODDIS : 'No hay codigos de convenio disponibles para asignar, por favor intentelo de nuevo mas tarde.',
        ERR_CA_CCE_REGAFIL : 'Ha ocurrido un error, por favor intentalo de nuevo'
    }

})();
