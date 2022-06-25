(function() {
    'use strict';

    angular.module('servicesModule').controller('mensajeCodigosImportController', mensajeCodigosImportController).controller(
            'mensajeCodigosRespuestaController', mensajeCodigosRespuestaController).filter('pos', function() {
        return function(input, position) {
            var data = input.split(",")[position];
            if (data)
                data = data.replace(/["']/g, "");
            return data;
        }
    });

    mensajeCodigosImportController.$inject = [ '$scope', 'adminService', '$location', 'csvProvider' ];

    function mensajeCodigosImportController($scope, adminService, $location, csvProvider) {
        var vm = this;
        vm.formData = {};
        vm.membresias = {};
        vm.tableData = [];
        vm.tableDataDiscountCodes = [];

        vm.logout = logout;
        vm.loadDiscountCodes = loadDiscountCodes;
        vm.loadEmployees = loadEmployees;
        vm.changeView = adminService.goPath;
        vm.imageUpload = imageUpload;

        onInit();

        function onInit() {
            getMembresias();
        }

        function logout() {
            adminService.logout();
        }
        
        function loadDiscountCodes() {
            var fileInput = document.getElementById("csvDiscountCodes");
            
            csvProvider.loadCsv(fileInput).then(function(result) {
                vm.formData.codigosCsv = result;
            }).catch(function(fallback) {
            	vm.formData.codigosCsv = '';
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

        function getMembresias() {
            adminService.getMembresias().then(function(response) {
                if (response.codigoRespuesta === "500") {
                    alert("Ha ocurrido un error interno, por favor intenta de nuevo mas tarde.")
                    return;
                }
                vm.membresias = response.membresias;
            });
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

    }

    mensajeCodigosRespuestaController.$inject = [ 'adminService', 'csvProvider' ];

    function mensajeCodigosRespuestaController(adminService, csvProvider) {
        var vm = this;
        vm.tableFallidos = [];
        vm.tableSuccess = [];

        vm.logout = logout;
        vm.downloadFallidos = downloadFallidos;
        vm.changeView = adminService.goPath;

        onInit();

        function onInit() {
            var codigoError = document.getElementById("codigoError").value;
            var csvCodigosFallidos = document.getElementById("csvCodigosFallidos").value;
            var csvCodigosCompletos = document.getElementById("csvCodigosCompletos").value;

            if (csvCodigosFallidos) {
                vm.csvCodigosFallidos = csvCodigosFallidos;
                var tableFallidos = csvProvider.decodeCsv(csvCodigosFallidos);
                if (tableFallidos[tableFallidos.length - 1] === '\n') {
                    tableFallidos = tableFallidos.slice(0, -1);
                }
                tableFallidos = tableFallidos.split('\n');
                tableFallidos.shift();
                vm.tableFallidos = tableFallidos;
            }
            if (csvCodigosCompletos) {
                var tableSuccess = csvProvider.decodeCsv(csvCodigosCompletos);
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
            if (vm.csvCodigosFallidos) {
                var encodedUri = "data:text/csv;base64," + vm.csvCodigosFallidos;
                csvProvider.download("codigosFallidos.csv", encodedUri);
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
