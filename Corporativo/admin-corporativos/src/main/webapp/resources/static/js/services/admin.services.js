(function() {
    'use strict';

    angular.module('servicesModule', []).service('adminService', adminService).filter('tipoDocumento', function() {
        return function(item) {
            if (documentos[item]) {
                return documentos[item]
            } else
                return item;
        }
    });

    adminService.$inject = [ '$http', '$q', '$location', 'storageProvider'];

	function adminService($http, $q, $location, storageProvider) {
		var self = this;
		var protocol = $location.protocol();
		var host = $location.host();
		var port = $location.port();
		var address = protocol + '://' + host + ':' + port + '/Admin/';

		self.getConvenios = getConvenios;
		self.getConveniosByDoc = getConveniosByDoc;
		self.getConveniosByCode = getConveniosByCode;
		self.getConveniosBySede = getConveniosBySede;
		self.getConveniosByState = getConveniosByState;
		self.getEmpresas = getEmpresas;
		self.getMember = getMember;
		self.getEmpleados = getEmpleados;
		self.checkAvailableCodes = checkAvailableCodes;
		self.logout = logout;
		self.getAddress = getAddress;
		self.goPath = goPath;

		/**
		 * Metodos del modulo empresas con convenio de precios 39 - 49 - 59
		 */
		self.getEmpresasConvenio = getEmpresasConvenio;
		self.getAfiliadosEmpresaConvenio = getAfiliadosEmpresaConvenio;
		self.getAfiliadosByRange = getAfiliadosByRange;
		self.getMembresias = getMembresias;
		self.getDocumentBase64 = getDocumentBase64;
		self.getInformationInitialToCreateCompany = getInformationInitialToCreateCompany;
		self.createPlan = createPlan;
		self.asignarPlanAEmpresa = asignarPlanAEmpresa;
		self.deleteAfiliadoById = deleteAfiliadoById;
		self.getPlanByEmpresa = getPlanByEmpresa;

		/**
		 * Funcion que retorna el contexto de la direccion
		 *
		 * @returns Objeto con la configuracion de la direccion
		 */
		function getAddress() {
			return address;
		}

		/**
		 * Funcion que retorna la configuracion para la cabecera de la peticion
		 *
		 * @returns Objeto con la configuracion de la cabecera
		 */
		function getConfig() {
			var token = storageProvider.getVar('token') || $("meta[name='session_token']").attr("content");
			if (!storageProvider.getVar('token')) {
				storageProvider.addVar('token', token);
			}
			var user = null;
			try {
				user = document.getElementById("username").innerHTML;
			} catch (err) {
				alert("Tu sesión ha expirado");
				window.location.replace(address);
			}
			var config = {
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded',
					'user': user,
					'sessionToken': token
				}
			};
			return config;
		}

		/**
		 * Funcion que realiza logout de la app
		 *
		 */
		function logout() {
			storageProvider.clear();
			document.getElementById("logoutForm").submit();
		}

		/**
		 * Funcion que consulta una pogina especofica de convenios
		 *
		 * @param numberPage -
		 *            Int - Nomero de la pagina a obtener
		 *
		 * @returns Informacion de convenios
		 */
		function getConvenios(numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerConvenios/' + numberPage, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo convenios');
				}
			}, function (error) {
				deferred.reject('Error obteniendo convenios');
			});
			return deferred.promise;
		}

		/**
		 * Funcion que consulta una pagina especifica de convenios por documento
		 *
		 * @param document -
		 *            Int - Numero del documento a filtrar convenios
		 *
		 * @returns Informacion de convenios filtrados por el documento
		 */
		function getConveniosByDoc(document) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerConveniosPorDocumento/' + document, config).then(
				function (response) {
					if (response.status == 200) {
						if (response.data.codigoRespuesta == "403") {
							logout();
						} else {
							deferred.resolve(response.data);
						}
					} else {
						deferred.reject('Error obteniendo convenios');
					}
				}, function (error) {
					deferred.reject('Error obteniendo convenios');
				});

			return deferred.promise;
		}

		/**
		 * Funcion que consulta una pagina especifica de convenios por codigo
		 *
		 * @param code -
		 *            String - Codigo a filtrar convenios
		 * @param numberPage -
		 *            Int - Numero de la pagina a obtener
		 * @returns Informacion de convenios filtrados por el codigo
		 */
		function getConveniosByCode(code, numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerConveniosPorCodigo/' + code + '/' + numberPage, config).then(
				function (response) {
					if (response.status == 200) {
						if (response.data.codigoRespuesta == "403") {
							logout();
						} else {
							deferred.resolve(response.data);
						}
					} else {
						deferred.reject('Error obteniendo convenios');
					}
				}, function (error) {
					deferred.reject('Error obteniendo convenios');
				});

			return deferred.promise;

		}

		/**
		 * Funcion que consulta una pagina especifica de convenios por sede
		 *
		 * @param sede -
		 *            String - Sede a filtrar convenios
		 * @param numberPage -
		 *            Int - Numero de la pagina a obtener
		 * @returns Informacion de convenios filtrados por la sede
		 */
		function getConveniosBySede(sede, numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerConveniosPorSede/' + sede + '/' + numberPage, config).then(
				function (response) {
					if (response.status == 200) {
						if (response.data.codigoRespuesta == "403") {
							logout();
						} else {
							deferred.resolve(response.data);
						}
					} else {
						deferred.reject('Error obteniendo convenios');
					}
				}, function (error) {
					deferred.reject('Error obteniendo convenios');
				});

			return deferred.promise;

		}

		/**
		 * Funcion que consulta una pagina especifica de convenios por estado de convenio
		 *
		 * @param state -
		 *            boolean - Estado a filtrar convenios
		 * @param numberPage -
		 *            Int - Numero de la pagina a obtener
		 * @returns Informacion de convenios filtrados por el estado
		 */
		function getConveniosByState(state, numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerConveniosPorEstadoAsignado/' + state + '/' + numberPage, config).then(
				function (response) {
					if (response.status == 200) {
						if (response.data.codigoRespuesta == "403") {
							logout();
						} else {
							deferred.resolve(response.data);
						}
					} else {
						deferred.reject('Error obteniendo convenios');
					}
				}, function (error) {
					deferred.reject('Error obteniendo convenios');
				});

			return deferred.promise;

		}

		/**
		 * Funcion que consulta una pagina especifica de empresas
		 *
		 * @param numberPage -
		 *            Int - Numero de la pagina a obtener
		 *
		 * @returns Informacion de empresas
		 */
		function getEmpresas(numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerEmpresas/' + numberPage, config).then(
				function (response) {
					if (response.status == 200) {
						if (response.data.codigoRespuesta == "403") {
							logout();
						} else {
							deferred.resolve(response.data);
						}
					} else {
						deferred.reject('Error obteniendo empresas');
					}
				}, function (error) {
					deferred.reject('Error obteniendo empresas');
				});
			return deferred.promise;
		}

		/**
		 * Funcion que consulta un afiliado
		 *
		 * @param tyeDoc -
		 *            Int - Numero del tipo de documento del afiliado
		 * @param numberDoc -
		 *            Int - Numero de documento del afiliado
		 *
		 * @returns Informacion del afiliado
		 */
		function getMember(typeDoc, numberDoc) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'consultarAfiliado/' + typeDoc + '_' + numberDoc, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo afiliado');
				}
			}, function (error) {
				deferred.reject('Error obteniendo afiliado');
			});
			return deferred.promise;
		}

		/**
		 * Funcion que consulta una pagina especifica de empleados
		 *
		 * @param username -
		 *            String - Username del usuario
		 * @param numberPage -
		 *            Int - Numero de la pagina a obtener
		 *
		 * @returns Informacion de empleados
		 */
		function getEmpleados(username, numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'listarEmpleadosEmpresa/' + username + '/' + numberPage, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo empleados');
				}
			}, function (error) {
				deferred.reject('Error obteniendo empleados');
			});
			return deferred.promise;
		}

		/**
		 * Funcion que consulta la disponibilidad de codigos de la empresa
		 *
		 * @param username -
		 *            String - Username del usuario
		 *
		 * @returns Informacion de disponibilidad de codigos
		 */
		function checkAvailableCodes(username) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'verificarCodigosDisponibles/' + username, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error verificando disponibilidad');
				}
			}, function (error) {
				deferred.reject('Error verificando disponibilidad');
			});
			return deferred.promise;
		}

		/**
		 * Funcion que consulta la lista de empresas registradas con el convenio
		 *
		 * @param numberPage -
		 *            Int - Numero de la pagina a obtener
		 *
		 * @returns Lista de empresas
		 */
		function getEmpresasConvenio(numberPage) {
			var deferred = $q.defer();
			var config = getConfig();
				console.info('ENTRO getEmpresasConvenio');
				if (!storageProvider.getVar('getEmpresasConvenio')) {
				$http.get(address + 'obtenerConvenioEmpresas', config).then(function (response) {
					if (response.status == 200) {
						if (response.data.codigoRespuesta == "403") {
							logout();
						} else {
							storageProvider.addVar('getEmpresasConvenio', response.data);
							deferred.resolve(response.data);
						}
					} else {
						deferred.reject('Error obteniendo empresas');
					}
				}, function (error) {
					deferred.reject('Error obteniendo empresas');
				});
			} else {
				deferred.resolve(storageProvider.getVar('getEmpresasConvenio'));
			}

			return deferred.promise;
		}

		/**
		 * Funcion que consulta la lista afiliadas a una empresa registrada con el convenio
		 *
		 * @param {*} tipo tipo de documento de la empresa
		 * @param {*} id id de la empresa
		 * @param {*} startDate [Opcional] fecha de inicio del el rango de fechas
		 * @param {*} endDate [Opcional] fecha final del el rango de fechas
		 *
		 * @returns Lista de afiliados
		 */
		function getAfiliadosEmpresaConvenio(tipo, id, membresiaId, startDate, endDate) {
			var url = createUrlGetAfiliados(tipo, id, membresiaId, startDate, endDate);
			return getAfiliadosEmpresaConvenioUrl(url);
		}

		/**
		 * Funcion que conulta una lista de afiliados por rango de
		 * fecha de asignacion de codigos.
		 *
		 * @param {*} startDate fecha de inicio del el rango de fechas
		 * @param {*} endDate fecha final del el rango de fechas
		 */
		function getAfiliadosByRange(startDate, endDate) {
			var url = "obtenerCodigosAsignados/" + startDate + '/' + endDate;
			return getAfiliadosEmpresaConvenioUrl(url);
		}

		/**
		 * Funcion que consulta una lista de afiliados generica
		 *
		 * @param url -
		 *            Url a la que se realiza la peticion dependiendo del requerimiento
		 *
		 * @returns Lista de afiliados
		 */
		function getAfiliadosEmpresaConvenioUrl(url) {
			var deferred = $q.defer();
			var config = getConfig();

			$http.get(address + url, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo afiliados');
				}
			}, function (error) {
				deferred.reject('Error obteniendo afiliados');
			});
			return deferred.promise;
		}

		/**
		 * Funcion que consulta las membresias
		 *
		 * @returns Lista de tarifas
		 */
		function getMembresias() {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'obtenerMembresias', config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo membresias');
				}
			}, function (error) {
				deferred.reject('Error obteniendo membresias');
			});
			return deferred.promise;
		}

		function getInformationInitialToCreateCompany()
		{
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'getInformationInitialToCreateCompany', config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta != "200") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo informacion inicial para gestionar empresa');
				}
			}, function (error) {
				deferred.reject('Error obteniendo informacion inicial para gestionar empresa');
			});
			return deferred.promise;

		}

		function createPlan(nombre,periocidad)
		{
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'createPlan/'+nombre+'/'+periocidad, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error creando plan');
				}
			}, function (error) {
				deferred.reject('Error creando plan');
			});
			return deferred.promise;

		}

		function asignarPlanAEmpresa(idPlan,IdEmpresa)
		{
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + 'asignarPlanAEmpresa/'+idPlan+'/'+IdEmpresa, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error Asignando plan a empresa');
				}
			}, function (error) {
				deferred.reject('Error Asignando plan a empresa');
			});
			return deferred.promise;

		}

		/**
		 * Funcion que obtiene un documento en base64
		 *
		 * @returns documento base64
		 */
		function getDocumentBase64(url) {
			var deferred = $q.defer();
			var config = getConfig();
			$http.get(address + url, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo documento');
				}
			}, function (error) {
				deferred.reject('Error obteniendo documento');
			});
			return deferred.promise;
		}

		/**
		 * Metodo para manejar la navegacion de AdminConvenios
		 *
		 * @param path -
		 *            Ruta que el navegador va a cargar
		 */
		function goPath(path) {
			window.location.href = '/Admin/' + path;
		}

		function createUrlGetAfiliados(tipo, id, membresiaId, startDate, endDate) {
			var url = "obtenerConvenioAfiliados/" + tipo + '/' + id + '/' + membresiaId;
			if (startDate) {
				url = url + "/" + startDate + "/" + endDate;
			}
			return url;
		}

		function deleteAfiliadoById(id) {
			var deferred = $q.defer();
			var data = $.param({
				id: id,
			});
			var config = getConfig();

			$http.post(address + 'eliminarAfiliado', data, config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error eliminando afiliado');
				}
			}, function (error) {
				deferred.reject('Error eliminando afiliado');
			});
			return deferred.promise;
		}

		function getPlanByEmpresa(formCrearPlan) {
			var deferred = $q.defer();
			var data = $.param({
				name: formCrearPlan.nombrePlan,
				days: formCrearPlan.dias,
			});
			var config = getConfig();
			$http.get(address + 'getPlanByEmpresa', config).then(function (response) {
				if (response.status == 200) {
					if (response.data.codigoRespuesta == "403") {
						logout();
					} else {
						deferred.resolve(response.data);
					}
				} else {
					deferred.reject('Error obteniendo membresias');
				}
			}, function (error) {
				deferred.reject('Error obteniendo membresias');
			});
			console.info('finish call service');
			return deferred.promise;
		}
	}

	var documentos = {
		CC: 'Cedula',
		TI: 'Tarjeta de Identidad',
		RC: 'Registro Civil',
		CE: 'Cedula de extranjeria',
		NT: 'NIT'
	}

})();