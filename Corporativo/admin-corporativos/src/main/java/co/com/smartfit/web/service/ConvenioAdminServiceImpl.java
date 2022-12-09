package co.com.smartfit.web.service;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import co.com.smartfit.web.business.rs.*;
import co.com.smartfit.web.dao.*;
import co.com.smartfit.web.entities.*;
import co.com.smartfit.web.enums.Periodicidad;
import co.com.smartfit.web.model.*;
import co.com.smartfit.web.util.Util;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.smartfit.web.business.rq.AsignarCodigoConvenioAfiliadoRq;
import co.com.smartfit.web.business.rq.CrearConvenioEmpresaRq;
import co.com.smartfit.web.business.rq.ObtenerCodigosAsignadosRq;
import co.com.smartfit.web.business.rq.ObtenerConvenioAfiliadosRq;
import co.com.smartfit.web.business.rq.ObtenerConvenioEmpresasRq;
import co.com.smartfit.web.business.rq.ProcesarCsvConvenioAfiliadosRq;
import co.com.smartfit.web.business.rq.ProcesarCsvConvenioCodigosRq;
import co.com.smartfit.web.shipping.EmailShipping;
import co.com.smartfit.web.util.CsvUtil;

/**
 * @author alejandro.areiza
 * @since 15/09/2017
 * @version 1.0
 */
@Service("convenioAdminService")
@Transactional
public class ConvenioAdminServiceImpl implements ConvenioAdminService {

	private static final Logger LOG = Logger.getLogger(ConvenioAdminServiceImpl.class);

	private static final String CONSUMO_SUCESS = "200";
	private static final String CONSUMO_ERROR = "500";
	private static final String CONSUMO_ERROR_NEGOCIO = "700";
	private static final String TIPO_DOC_ID_PLUS = "01";
	private static final int DB_ERROR = -1;
	private CsvUtil csvUtil;
	// codigos de error en respuestas
	private static final String ERROR_GENERICO = "ERR_GENERIC"; // error generico
	private static final String ERROR_CREAR_EMPRESA = "ERR_CA_CCE"; // error ocurrrido al crear empresa
	private static final String ERROR_ASIGNAR_CODIGO = "ERR_CA_ACC"; // error ocurrrido al asignar codigo del convenio
	private static final String ERROR_ACTUALIZAR_CODIGO = "ERR_CA_ACC_ACT"; // error ocurrrido al actualizar codigo del
																			// convenio (ya
																			// asignado)
	private static final String ERROR_CSV_READ = "ERROR_CSV_READ"; // error al procesar el CSV recibido
	private static final String ERR_NEG_REQ_NULL = "ERR_NEG_REQ_NULL"; // error al identificar que un objeto de request
																		// requerido es null
	private static final String ERROR_NEG_NULL = "ERR_NEG_NULL"; // error al identificar que un objeto de negocio
																	// importante es nulo
	private static final String ERROR_NEG_CODIGONODIS = "ERR_NEG_CODDIS"; // error al identificar que no hay codigos
																			// disponibles
	// error al identificar que la entidad que se itnenta crear ya existe en DB
	private static final String ERROR_NEG_ENTIDAD_EXISTENTE_EN_BD = "ERR_NEG_EXIST";
	// error al registrar los afiliados
	private static final String ERR_CA_CCE_REGAFIL = "ERR_CA_CCE_REGAFIL";
	// datos para CSV de codigos
	private static final int CSV_CODIGO_COL_CODIGO = 0;
	private static final int CSV_CODIGO_COL_NOMBRE_PLAN = 1;
	// datos para CSV de afiliados
	private static final int CSV_AFILIADO_COL_TIPODOCID = 0;
	private static final int CSV_AFILIADO_COL_DOCNUMERO = 1;
	private static final int CSV_AFILIADO_COL_NOMBRE = 2;
	private static final int CSV_AFILIADO_COL_CORREO = 3;
	private static final int CSV_AFILIADO_COL_ID_NOMBRE_PLAN= 4;

	public ConvenioAdminServiceImpl() {
		csvUtil = new CsvUtil();
	}


	@Override
	public CrearConvenioEmpresaRs crearConvenioEmpresa(CrearConvenioEmpresaRq request) {
		CrearConvenioEmpresaRs response = new CrearConvenioEmpresaRs();
		try {
			if (null == request.getEmpresa()) {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, error de negocio debido a dato empresa de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (null == request.getEmpresa().getDocumentoNumero()) {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, error de negocio debido a dato empresa.documentoNumero de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (request.getEmpresa().getDocumentoTipo() <= 0) {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, error de negocio debido a dato empresa.documentoTipo de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (null == request.getEmpresa().getRazonSocial()) {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, error de negocio debido a dato empresa.razonSocial de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (null == request.getCsvCodigos()) {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, error de negocio debido a dato csvCodigos de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (null == request.getCsvAfiliados()) {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, error de negocio debido a dato csvAfiliados de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}

			EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
			// obtenemos la entidad a partir del modelo
			EmpresaEmpleador entidad = mapearEmpresaEmpleadorEntidad(request.getEmpresa());
			// seteamos datos no ingresados por usuario
			entidad.setActiva(true);
			entidad.setFechaCreacion(new Date());
			// guardamos la entidad en BD
			int idEmpresa = empresaEmpleadorDao.guardarEntidad(entidad);
			if (DB_ERROR != idEmpresa) {
				EmpresaEmpleadorModel empresa = mapearEmpresaEmpleadorModel(entidad);
		
				ProcesarCsvConvenioCodigosRq procesarCsvCodigosRq = new ProcesarCsvConvenioCodigosRq();
				procesarCsvCodigosRq.setCsvCodigos(request.getCsvCodigos());
				procesarCsvCodigosRq.setEmpresa(empresa);
				
				// llamamos al servicio de asignación de codigos
				ProcesarCsvConvenioCodigosRs rsCodigo = procesarCsvConvenioCodigos(procesarCsvCodigosRq);
				boolean procesoCodigoExitoso = rsCodigo.getCodigoRespuesta().equals(CONSUMO_SUCESS) ? true : false;
				response.setCodigoRespuesta(procesoCodigoExitoso ? CONSUMO_SUCESS : CONSUMO_ERROR);
				if (!procesoCodigoExitoso) {
					response.setCodigoError(ERR_CA_CCE_REGAFIL);
					response.setFechaRespuesta(new Date());
					return response;
				}
				
				ProcesarCsvConvenioAfiliadosRq procesarCsvAfiliadosRq = new ProcesarCsvConvenioAfiliadosRq();
				procesarCsvAfiliadosRq.setCsvAfiliados(request.getCsvAfiliados());
				procesarCsvAfiliadosRq.setEmpresa(empresa);
				
				// llamamos al servicio de asignación de afiliados
				ProcesarCsvConvenioAfiliadosRs rs = procesarCsvConvenioAfiliados(procesarCsvAfiliadosRq);
				boolean procesoExitoso = rs.getCodigoRespuesta().equals(CONSUMO_SUCESS) ? true : false;
				response.setCodigoRespuesta(procesoExitoso ? CONSUMO_SUCESS : CONSUMO_ERROR);
				if (!procesoExitoso) {
					response.setCodigoError(ERR_CA_CCE_REGAFIL);
					response.setFechaRespuesta(new Date());
					return response;
				}
				response.setAfiliadosAsignados(rs.getAfiliadosAsignados());
				response.setAfiliadosError(rs.getAfiliadosError());
				response.setCodigosAsignados(rs.getCodigosAsignados());
				response.setMensajesError(rs.getMensajesError());
				String csvAfiliadosFallidos = obtenerCsvFallidos(rs.getAfiliadosError(), rs.getMensajesError());
				response.setCsvAfiliadosFallidos(csvAfiliadosFallidos);
				String csvAfiliadosCompletos = obtenerCsvExitosos(rs.getAfiliadosAsignados(), rs.getCodigosAsignados());
				response.setCsvAfiliadosCompletos(csvAfiliadosCompletos);
			} else {
				String errorMsg = "Error en Bean-crearConvenioEmpresa, al realizar la operación crearConvenioEmpresa."
						+ " ";
				LOG.error(errorMsg + "No se pudo guardar la entidad en BD");
				response.setCodigoError(ERROR_CREAR_EMPRESA);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setFechaRespuesta(new Date());
				return response;
			}
		} catch (ConstraintViolationException e) {
			String errorMsg = "Error en Bean-crearConvenioEmpresa, la entidad ya existe en BD." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_NEG_ENTIDAD_EXISTENTE_EN_BD);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		} catch (IllegalArgumentException e) {
			String errorMsg = "Error en Bean-crearConvenioEmpresa, al procesar el CSV de afiliados." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_CSV_READ);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		} catch (Exception e) {
			String errorMsg = "Error en Bean-crearConvenioEmpresa, generico en la operación crearConvenioEmpresa."
					+ "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_GENERICO);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		}
		return response;
	}
	
	/**
	 * Metodo que permite obtener CSV de registros exitosos
	 * 
	 * @param
	 * @return String
	 * @throws IOException
	 */
	private String obtenerCsvCodigosExitosos(List<CodigoDescuentoModel> codigosAsignados) throws IOException {
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Codigo" };
		filas.add(cabezera);
		CodigoDescuentoModel model = null;
		String[] fila = null;
		for (int i = 0; i < codigosAsignados.size(); i++) {
			model = codigosAsignados.get(i);
			fila = new String[1];

			fila[0] = model.getCodigo();
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}

	/**
	 * Metodo que permite obtener CSV de registros exitosos
	 * 
	 * @param
	 * @return String
	 * @throws IOException
	 */
	private String obtenerCsvExitosos(List<EmpresaAfiliadoModel> afiliadosAsignados, List<String> codigosAsignados)
			throws IOException {
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Tipo Documento", "Documento", "Nombre", "Correo", "CODIGO ASIGNADO" };
		filas.add(cabezera);
		EmpresaAfiliadoModel model = null;
		String[] fila = null;
		for (int i = 0; i < afiliadosAsignados.size(); i++) {
			model = afiliadosAsignados.get(i);
			String error = codigosAsignados.get(i);
			fila = new String[5];
			String tipoDoc = model.getTipoDocumentoIdentidad();
			if (tipoDoc.contains(TIPO_DOC_ID_PLUS)) {
				tipoDoc = tipoDoc.replaceAll(TIPO_DOC_ID_PLUS, "");
			}
			fila[0] = tipoDoc;
			fila[1] = model.getDocumentoNumero();
			fila[2] = model.getNombre();
			fila[3] = model.getEmail();
			fila[4] = error;
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}
	
	/**
	 * Metodo que permite obtener CSV de codigos registrados exitosamente
	 * 
	 * @param
	 * @return String
	 * @throws IOException
	 */
	private String obtenerCsvExitososCodigoDescuento(List<CodigoDescuentoModel> codigosAsignados)
			throws IOException {
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Codigo" };
		filas.add(cabezera);
		CodigoDescuentoModel model = null;
		String[] fila = null;
		for (int i = 0; i < codigosAsignados.size(); i++) {
			model = codigosAsignados.get(i);
			fila = new String[1];
			
			fila[0] = model.getCodigo();
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}

	/**
	 * Metodo que permite construir un CSV en base a una lista de afiliados y
	 * mensajes de fallo
	 * 
	 * @param afiliadosFallidos
	 * @param errores
	 * @return String CSV de fallidos
	 * @throws IOException
	 */
	private String obtenerCsvFallidos(List<EmpresaAfiliadoModel> afiliadosFallidos, List<String> errores)
			throws IOException {
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Tipo Documento", "Documento", "Nombre", "Correo", "ERROR" };
		filas.add(cabezera);
		EmpresaAfiliadoModel model = null;
		String[] fila = null;
		for (int i = 0; i < afiliadosFallidos.size(); i++) {
			model = afiliadosFallidos.get(i);
			String error = errores.get(i);
			fila = new String[5];
			String tipoDoc = model.getTipoDocumentoIdentidad();
			if (tipoDoc.contains(TIPO_DOC_ID_PLUS)) {
				tipoDoc = tipoDoc.replaceAll(TIPO_DOC_ID_PLUS, "");
			}
			fila[0] = tipoDoc;
			fila[1] = model.getDocumentoNumero();
			fila[2] = model.getNombre();
			fila[3] = model.getEmail();
			fila[4] = error;
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}
	
	/**
	 * Metodo que permite construir un CSV con base a una lista de codigos de descuento y
	 * mensajes de fallo
	 * 
	 * @param codigosFallidos
	 * @param errores
	 * @return String CSV de fallidos
	 * @throws IOException
	 */
	private String obtenerCsvFallidosCodigosDescuento(List<CodigoDescuentoModel> codigosFallidos, List<String> errores)
			throws IOException {
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Codigo", "ERROR" };
		filas.add(cabezera);
		CodigoDescuentoModel model = null;
		String[] fila = null;
		for (int i = 0; i < codigosFallidos.size(); i++) {
			model = codigosFallidos.get(i);
			String error = errores.get(i);
			fila = new String[2];

			fila[0] = model.getCodigo();
			fila[1] = error;
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}
	
	/**
	 * Metodo que permite construir un CSV en base a una lista de codigos y mensajes de fallo
	 * @param {codigossFallidos}
	 * @param errores
	 * @return String CSV de fallidos
	 * @throws IOException
	 */
	private String obtenerCsvCodigosFallidos(List<CodigoDescuentoModel> codigosFallidos, List<String> errores) throws IOException {
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Codigo", "ERROR" };
		filas.add(cabezera);
		CodigoDescuentoModel model = null;
		String[] fila = null;
		for (int i = 0; i < codigosFallidos.size(); i++) {
			model = codigosFallidos.get(i);
			String error = errores.get(i);
			fila = new String[2];

			fila[0] = model.getCodigo();
			fila[1] = error;
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}


	private String obtenerCsvAfiliados(List<EmpresaAfiliadoModel> afiliadosFallidos) throws IOException {
		LOG.info("obtenerCsvAfiliados");
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Tipo Documento", "Documento", "Nombre", "Correo", "Empresa", "Codigo", "Fecha Asignacion" };
		filas.add(cabezera);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (EmpresaAfiliadoModel model : afiliadosFallidos) {
			String tipoDocumentoIdentidad = model.getTipoDocumentoIdentidad();
			if (tipoDocumentoIdentidad.contains(TIPO_DOC_ID_PLUS)) {
				tipoDocumentoIdentidad = tipoDocumentoIdentidad.replaceAll(TIPO_DOC_ID_PLUS, "");
			}
			String documentoNumero = model.getDocumentoNumero();
			String nombre = model.getNombre();
			String email = model.getEmail();
		
			EmpresaEmpleadorModel empresa = model.getEmpresaEmpleador();
			String[] fila = null;
			LOG.info("tipoDocumentoIdentidad: " + tipoDocumentoIdentidad + " documentoNumero: " + documentoNumero );
			// obtenemos los codigos
			List<CodigoDescuentoModel> codigos = model.getCodigosAsignados();
			for (CodigoDescuentoModel codigo : codigos) {
				fila = new String[7];
				fila[0] = tipoDocumentoIdentidad;
				fila[1] = documentoNumero;
				fila[2] = nombre;
				fila[3] = email;
				if (null != empresa) {
					fila[4] = empresa.getRazonSocial();
				}
				fila[5] = codigo.getCodigo();
				fila[6] = codigo.getFechaAsignacion().toString();
				filas.add(fila);
			}
		}
		return (csvUtil.generarCsvBase64(filas));
	}

	
	/**
	 * Metodo que permite obtener un CSV con los codigos asignados a los empleados de todas las empresas, filtrado con un rango de fecha
	 * @param codigosAsignados
	 * @return
	 * @throws IOException
	 */
	private String obtenerCsvCodigosAsignados(List<CodigoDescuentoModel> codigosAsignados) throws IOException {
		LOG.info("obtenerCsvCodigosAsignados");
		CsvUtil csvUtil = new CsvUtil();
		List<String[]> filas = new ArrayList<String[]>();
		String[] cabezera = { "Codigo", "Fecha Asignacion", "Tipo Documento", "Documento", "Nombre", "Correo", "Empresa",
				"Tipo Documento Empresa", "Documento Empresa", "Telefono Empresa", "Correo Empresa", "Representante Legal Empresa" };
		filas.add(cabezera);
		String[] fila = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		EmpresaEmpleadorXPlanDao empresaEmpleadorXPlanDao = new EmpresaEmpleadorXPlanDao();
		EmpresaAfiliadoXCodigoDescuentoDao  empresaAfiliadoXCodigoDescuentoDao = new EmpresaAfiliadoXCodigoDescuentoDao();
		for (CodigoDescuentoModel codigoAsignado : codigosAsignados) {
			EmpresaEmpleadorXPlan empresaEmpleadorXPlan = empresaEmpleadorXPlanDao.getEmpresaEmpleadorXPlanById(codigoAsignado.getIdEmpresaPlan());
			EmpresaEmpleador empresaEmpleador = empresaEmpleadorXPlan.getEmpresaEmpleador();
			EmpresaAfiliadoXCodigoDescuento empresaAfiliadoXCodigoDescuento = empresaAfiliadoXCodigoDescuentoDao.getEmpresaAfiliadoXCodigoDescuentoByCodigoDescuento(codigoAsignado.getId());
			EmpresaAfiliado empresaAfiliado = empresaAfiliadoXCodigoDescuento.getEmpresaAfiliado();
			TipoDocumentoIdentidad tipoDocumentoIdentidad = empresaAfiliado.getTipoDocumentoIdentidad();

			fila = new String[12];
			fila[0] = codigoAsignado.getCodigo();
			fila[1] = format.format(empresaAfiliadoXCodigoDescuento.getFechaAsignacion());
			String valueTipoDocumentoIdentidad = tipoDocumentoIdentidad.getCodigo();
			if (valueTipoDocumentoIdentidad.contains(TIPO_DOC_ID_PLUS)) {
				valueTipoDocumentoIdentidad = valueTipoDocumentoIdentidad.replaceAll(TIPO_DOC_ID_PLUS, "");
			}
			fila[2] = valueTipoDocumentoIdentidad;
			fila[3] = empresaAfiliado.getDocumentoNumero();
			fila[4] = empresaAfiliado.getNombre();
			fila[5] = empresaAfiliado.getEmail();
			fila[6] = empresaEmpleador.getRazonSocial();
			String tipoDocumentoEmpresa = empresaEmpleador.getTipoDocumentoIdentidad().getDescripcion();
			fila[7] = tipoDocumentoEmpresa;
			fila[8] = empresaEmpleador.getDocumentoNumero();
			fila[9] = empresaEmpleador.getTelefono();
			fila[10] = empresaEmpleador.getEmail();
			fila[11] = empresaEmpleador.getRepresentanteNombre();
			filas.add(fila);
		}
		return (csvUtil.generarCsvBase64(filas));
	}
	
	
	/**
	 * Metodo que permite procesar un archivo CSV de codigos y para cada uno:</br>
	 * - Guardar codigo en BD</br>
	 * - Crear CSV de fallidos</br>
	 * - Crear CSV de exitosos
	 * 
	 * @param request
	 *            ProcesarCsvCodigosRq request con datos de CSV
	 * @return ProcesarCsvCodigosRs response que indica si la solicitud fue exitosa y los CSV de error
	 */
	public ProcesarCsvConvenioCodigosRs procesarCsvConvenioCodigos(ProcesarCsvConvenioCodigosRq request) {
		LOG.info("Procensado csv para la empresa: " + request.getEmpresa().getRazonSocial());
		ProcesarCsvConvenioCodigosRs response = new ProcesarCsvConvenioCodigosRs();
		// listas para manejo de asignacion de codigos y errores
		List<CodigoDescuentoModel> codigosAsignados = new ArrayList<CodigoDescuentoModel>();
		List<CodigoDescuentoModel> codigosError = new ArrayList<CodigoDescuentoModel>();
		List<String> mensajesError = new ArrayList<String>();
		try {
			// procesamos el archivo CSV de codigos
			List<CodigoDescuentoModel> codigosCsv = this.obtenerModeloCodigosDeCsv(request.getCsvCodigos());
			LOG.info("Seran procesados "+ codigosCsv.size() +" al modelo CodigoDescuento");
			// creamos cada uno de los codigos en BD
			for (CodigoDescuentoModel codigoModel : codigosCsv) {
				LOG.info("Procesando el codigo "+ codigoModel.getCodigo());
				try {
					CodigoDescuento entidad = this.mapearCodigoDescuentoEntidad(codigoModel,request.getEmpresa());
					CodigoDescuentoDao codigoDescuentoDao = new CodigoDescuentoDao();
					if ((codigoDescuentoDao.guardarEntidad(entidad) == DB_ERROR)) {
						codigosError.add(codigoModel);
					} else {
						codigosAsignados.add(codigoModel);
					}
				} catch (ErrorGeneral eg) {
					codigosError.add(codigoModel);
					mensajesError.add(eg.getMessage());
				} catch (Exception ex) {
					codigosError.add(codigoModel);
					mensajesError.add( "No es posible cargar el codigo");
					LOG.error(ex.getMessage());
				}
			}
			// armamos la respuesta
			response.setCodigosAsignados(codigosAsignados);
			response.setAfiliadosError(codigosError);
			response.setCodigosAsignados(codigosAsignados);
			response.setMensajesError(mensajesError);
			String csvCodigosFallidos = obtenerCsvCodigosFallidos(codigosError, mensajesError);
			response.setCsvCodigosFallidos(csvCodigosFallidos);
			String csvCodigosCompletos = obtenerCsvCodigosExitosos(codigosAsignados);
			response.setCsvCodigosCompletos(csvCodigosCompletos);
			response.setCodigoRespuesta(CONSUMO_SUCESS);
			response.setFechaRespuesta(new Date());
		} catch (IllegalArgumentException e) {
			String errorMsg = "Error en Bean-procesarCsvConvenioCodigos, al realizar la lectura del CSV." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_CSV_READ);
		} catch (Exception e) {
			String errorMsg = "Error en Bean-procesarCsvConvenioCodigos, generico." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_GENERICO);
		}
		return response;
	}


	/**
	 * Metodo que permite procesar un archivo CSV de afiliados y para cada uno asignarle un codigo de descuento:</br>
	 * - Guardar afiliado en BD (si no existe)</br>
	 * - Asignar codigo al afiliado</br>
	 * - Crear CSV de fallidos</br>
	 * - Crear CSV de exitosos
	 * 
	 * @param request
	 *            ProcesarCsvAfiliadosRq request con datos de CSV y empresa de los
	 *            afiliados
	 * @return ProcesarCsvAfiliadosRs response que indica si la solicitud fue
	 *         exitosa y los CSV de error
	 */
	public ProcesarCsvConvenioAfiliadosRs procesarCsvConvenioAfiliados(ProcesarCsvConvenioAfiliadosRq request) {
		LOG.info("Procesando el archivo csv de la empresa "+ request.getEmpresa().getRazonSocial());
		ProcesarCsvConvenioAfiliadosRs response = new ProcesarCsvConvenioAfiliadosRs();
		// listas para manejo de asignacion de codigos y errores
		List<EmpresaAfiliadoModel> afiliadosAsignados = new ArrayList<EmpresaAfiliadoModel>();
		List<String> codigosAsignados = new ArrayList<String>();
		List<EmpresaAfiliadoModel> afiliadosError = new ArrayList<EmpresaAfiliadoModel>();
		List<String> mensajesError = new ArrayList<String>();
		
		try {
			// Find empleador
			int emprTipoDoc = request.getEmpresa().getDocumentoTipo();
			String emprNumDoc = request.getEmpresa().getDocumentoNumero();
			int emprMembr = request.getEmpresa().getMembresia();
			EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
			EmpresaEmpleador empresaEmpleador = empresaEmpleadorDao.obtenerEmpresaPorDocumento(emprTipoDoc, emprNumDoc, emprMembr);
			EmpresaEmpleadorModel empresaEmpleadorModel = this.mapearEmpresaEmpleadorModel(empresaEmpleador);
			request.setEmpresa( empresaEmpleadorModel );
			
			// procesamos el archivo CSV de afiliados
			List<EmpresaAfiliadoModel> afiliadosCsv = this.obtenerModeloAfiliadosDeCsv(request.getCsvAfiliados());
			LOG.info("Seran procesados "+afiliadosCsv.size()+" registros del listado EmpresaAfiliadoModel");
			// creamos cada uno de los afiliados en BD
			for (EmpresaAfiliadoModel afiliadoModel : afiliadosCsv) {
				LOG.info("Procesando el numero de documento "+afiliadoModel.getDocumentoNumero()+" del archivo csv");
				afiliadoModel.setEmpresaEmpleador(request.getEmpresa());
				afiliadoModel.setFechaRegistro(new Date());
				// mapeamos el modelo de afiliado a entidad
				EmpresaAfiliado entidad = this.mapearEmpresaAfiliadoEntidad(afiliadoModel);
				EmpresaAfiliadoDao afiliadoDao = new EmpresaAfiliadoDao();
				LOG.info("Procesando el afiliado con numero: "+entidad.getDocumentoNumero());
				// try control para cada afiliado que falla
				try {
					
					// guardamos la entidad en BD
					int afiliadoId = DB_ERROR;
					int empresaId = (null != entidad.getEmpresaEmpleador()
							&& null != entidad.getEmpresaEmpleador().getId()) ? entidad.getEmpresaEmpleador().getId()
									: -1;
					EmpresaAfiliado entidadAux = afiliadoDao.obtenerAfiliadoPorDocumentoYEmpresa(
							entidad.getTipoDocumentoIdentidad().getId(), entidad.getDocumentoNumero(), empresaId);
					if (null != entidadAux) {
						afiliadoId = entidadAux.getId();
						LOG.info("Actualizando entidad EmpresaAfiliado para el numero de documento: "+entidadAux.getDocumentoNumero());
						afiliadoDao.actualizarEntidad(entidadAux);
					} else {
						LOG.info("Insertando entidad EmpresaAfiliado para el numero de documento: "+entidad.getDocumentoNumero());
						afiliadoId = afiliadoDao.guardarEntidad(entidad);
					}
					if (DB_ERROR != afiliadoId) {
						LOG.info("El numero de documento fue actualizado o creado");
						// asignamos el codigo
						AsignarCodigoConvenioAfiliadoRq asignarCodigoConvenioAfiliadoRq = new AsignarCodigoConvenioAfiliadoRq();
						afiliadoModel.setId(afiliadoId);
						afiliadoModel.setDocumentoNumero(entidad.getDocumentoNumero());
						afiliadoModel.setEmpresaEmpleador(empresaEmpleadorModel);
						afiliadoModel.setEmpresaEmpleadorPlan(empresaId);
						asignarCodigoConvenioAfiliadoRq.setEmpresaAfiliado(afiliadoModel);
						AsignarCodigoConvenioAfiliadoRs rs = new AsignarCodigoConvenioAfiliadoRs();
						boolean condigoAsignado = this.codigoEstaAsignado(afiliadoId);
						if (!condigoAsignado)
						{
							rs = this.asignarCodigoConvenioAfiliado(afiliadoModel.getEmpresaEmpleadorPlan().getId(),
									asignarCodigoConvenioAfiliadoRq);
							if (!CONSUMO_SUCESS.equals(rs.getCodigoRespuesta())) {
								LOG.info("Se presenta un error asignando el codigo al afiliado");
								// indicamos los errores de negocio
								afiliadosError.add(afiliadoModel);
								mensajesError.add(rs.getMensajeError());
							}
							else {
								// enviamos el correo
								LOG.info("Codigo asignado al afiliado correctamente");
								String email = afiliadoModel.getEmail();
								String name = afiliadoModel.getNombre();
								String codigo = rs.getCodigoDescuento().getCodigo();

								boolean respEnvEmail = false;
								if( empresaEmpleador.getMembresia().getNombre().equalsIgnoreCase("VIP")) {
									LOG.info("Enviar Email Membresia Vip email al afiliado con numero: "+entidad.getDocumentoNumero());
									respEnvEmail = EmailShipping.enviarEmailMembresiaVip(email, name, codigo, empresaEmpleador.getLogo(), empresaEmpleador.getTextoEmail());
								} else {
									LOG.info("Enviar Email Membresia Convencional al afiliado con numero: "+entidad.getDocumentoNumero());
									respEnvEmail = EmailShipping.enviarEmailMembresiaConvencional(email, name, codigo, empresaEmpleador.getLogo(), empresaEmpleador.getTextoEmail());
								}
								if ( respEnvEmail ) {
									// asignamos los completos
									afiliadosAsignados.add(rs.getAfiliado());
									codigosAsignados.add(rs.getCodigoDescuento().getCodigo());
								} else {
									afiliadosError.add(afiliadoModel);
									mensajesError.add("No se pudo enviar el correo, puede no ser un correo valido");
								}
							}
						} else {
							LOG.info("El afiliado tiene un convenio asignado");
							// indicamos los errores de negocio
							afiliadosError.add(afiliadoModel);
							mensajesError.add("El afiliado tiene un convenio asignado");
						}
					}
				} catch (RemoteException e) {
					afiliadosError.add(afiliadoModel);
					mensajesError.add("Problemas al consumir el webservice RemoteException");
				} catch (ParseException e) {
					afiliadosError.add(afiliadoModel);
					mensajesError.add("Problemas al consumir el webservice ParseException");
				} catch (ConnectException e) {
					afiliadosError.add(afiliadoModel);
					mensajesError.add("Problemas de comunicacion (timeout) con el Web Service de afiliados");
				}  catch (ErrorGeneral e) {
				afiliadosError.add(afiliadoModel);
				mensajesError.add(e.getMessage());
				} catch (Exception e) {
					afiliadosError.add(afiliadoModel);
					if (null == e.getMessage()) {
						mensajesError.add("Ocurrio un error en el sistema");
						LOG.error(e.getMessage());
					} else if (e.getMessage().equals("NO_AFILIADO")) {
						mensajesError.add("Persona no afiliada a smartfit");
					} else {
						mensajesError.add("Ocurrio un error en el sistema");
						LOG.error(e.getMessage());
					}
				}
			}
			// armamos la respuesta
			response.setAfiliadosAsignados(afiliadosAsignados);
			response.setAfiliadosError(afiliadosError);
			response.setCodigosAsignados(codigosAsignados);
			response.setMensajesError(mensajesError);
			String csvAfiliadosFallidos = obtenerCsvFallidos(afiliadosError, mensajesError);
			response.setCsvAfiliadosFallidos(csvAfiliadosFallidos);
			String csvAfiliadosCompletos = obtenerCsvExitosos(afiliadosAsignados, codigosAsignados);
			response.setCsvAfiliadosCompletos(csvAfiliadosCompletos);
			response.setCodigoRespuesta(CONSUMO_SUCESS);
			response.setFechaRespuesta(new Date());
		} catch (IllegalArgumentException e) {
			String errorMsg = "Error en Bean-procesarCsvConvenioAfiliados, al realizar la lectura del CSV." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_CSV_READ);
		} catch (Exception e) {
			String errorMsg = "Error en Bean-procesarCsvConvenioAfiliados, generico." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_GENERICO);
		}
		return response;
	}

	/**
	 * Metodo que permite asignar un codigo disponible a un afiliado registrado
	 * 
	 * @param request
	 *            AsignarCodigoConvenioAfiliadoRq request con los datos de afiliado
	 *            para asignar codigo
	 * @return AsignarCodigoConvenioAfiliadoRs respuesta con los datos de asignacion
	 *         de codigo
	 * @throws Exception
	 */
	private AsignarCodigoConvenioAfiliadoRs 	asignarCodigoConvenioAfiliado(int idEmpresaPlan,AsignarCodigoConvenioAfiliadoRq request) {
		LOG.info("Asignando convenio a empresa plan "+ idEmpresaPlan+" al afiliado: " +request.getEmpresaAfiliado().getDocumentoNumero());
		AsignarCodigoConvenioAfiliadoRs response = new AsignarCodigoConvenioAfiliadoRs();
		CodigoDescuentoDao codigoDescuentoDao = new CodigoDescuentoDao();
		CodigoDescuento codigoAsignar=null;
		try {
			// obtenemos las entidades necesarias
			EmpresaAfiliadoModel empresaAfiliadoModel = request.getEmpresaAfiliado();

			if (null == empresaAfiliadoModel) {
				String errorMsg = "Error en Bean-asignarCodigoConvenioAfiliado, error de negocio debido a dato empresaAfiliado de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setMensajeError("Ocurrio un error en el sistema");
				response.setFechaRespuesta(new Date());
				return response;
			}

			EmpresaAfiliadoDao empresaAfiliadoDao = new EmpresaAfiliadoDao();
			EmpresaAfiliado empresaAfiliado = empresaAfiliadoDao.obtenerPorId(empresaAfiliadoModel.getId());
			if (null == empresaAfiliado) {
				String errorMsg = "Error en Bean-asignarCodigoConvenioAfiliado, el afiliado no existe en BD." + "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setCodigoError(ERROR_NEG_NULL);
				response.setMensajeError("El afiliado no pudo ser creado en BD");
				response.setFechaRespuesta(new Date());
				return response;
			}
			// obtenemos un codigo disponible
			List<CodigoDescuento> codigosDescuentoDisponible = codigoDescuentoDao.obtenerCodigosDisponiblesPorPlan(true, idEmpresaPlan);
			// asignamos el codigo despues de hacer un Shuffle
			if ( codigosDescuentoDisponible.size()==0) {

				// evitamos null pointer
				String errorMsg = "Error en Bean-asignarCodigoConvenioAfiliado, no hay codigos disponibles para asignar."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERROR_NEG_CODIGONODIS);
				response.setMensajeError("No hay codigos disponibles");
				response.setFechaRespuesta(new Date());

				return response;
			}
			codigoAsignar = codigosDescuentoDisponible.get(0);
			// creamos y guardamos la entidad de empresa_afiliado_x_codigo_descuento
			EmpresaAfiliadoXCodigoDescuento entidad = new EmpresaAfiliadoXCodigoDescuento();
			entidad.setEmpresaAfiliado(empresaAfiliado);
			entidad.setCodigoDescuento(codigoAsignar);
			entidad.setAsignado(true);
			entidad.setFechaAsignacion(new Date());
			LOG.info("Se guardara la entidad EmpresaAfiliadoXCodigoDescuento: para el afiliado: " +empresaAfiliado.getDocumentoNumero()+
					" con el codigo: "+codigoAsignar.getCodigo());
			EmpresaAfiliadoXCodigoDescuentoDao dao = new EmpresaAfiliadoXCodigoDescuentoDao();
			int entidadId = dao.guardarEntidad(entidad);
			if (DB_ERROR == entidadId) {
				String errorMsg = "Error en Bean-asignarCodigoConvenioAfiliado, el codigo no pudo ser asignado en DB."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setCodigoError(ERROR_ASIGNAR_CODIGO);
				response.setMensajeError("El afiliado fue creado pero el codigo no pudo ser asignado");
				response.setFechaRespuesta(new Date());
				return response;
			}
			// asignamos los datos al response
			codigoAsignar.setAsignado(true);
			CodigoDescuentoModel codigoDescuentoModel = this.mapearCodigoDescuentoModel(codigoAsignar, entidad);
			response.setCodigoDescuento(codigoDescuentoModel);
			EmpresaAfiliadoModel empresaAfiliadoModelRs = this.mapearEmpresaAfiliadoModel(empresaAfiliado);
			response.setAfiliado(empresaAfiliadoModelRs);
			// tras guardar la entidad (exitosamente) indicamos que el codigo de descuento
			// ya esta siendo usado (update)
			if (!codigoDescuentoDao.actualizar(codigoAsignar)) {
				// TO-DO Revertir en caso de algun fallo (no estoy seguro como, quizas deletes
				// de DB con mucho cuidado)
				String errorMsg = "Error en Bean-asignarCodigoConvenioAfiliado, el codigo no pudo actualizarse a NO disponible."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setCodigoError(ERROR_ACTUALIZAR_CODIGO);
				response.setMensajeError(
						"El codigo fue asignado pero el codigo no pudo ser actualizado a No Disponible");
				response.setFechaRespuesta(new Date());
				return response;
			}
			response.setCodigoRespuesta(CONSUMO_SUCESS);
			response.setFechaRespuesta(new Date());
		} catch (Exception e) {
			String errorMsg = "Error en Bean-asignarCodigoConvenioAfiliado, generico." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_GENERICO);
			response.setMensajeError("Ocurrio un error en el sistema");
			response.setFechaRespuesta(new Date());
		}
		return response;
	}

	private boolean codigoEstaAsignado ( int afiliadoId ) {
		boolean result =false;
		LOG.info("Validando si el afiliado con Id: "+afiliadoId+" tiene codigos asignados");
		CodigoDescuento lastCodigoDescuentoAsignado=null;
		Periodicidad periodicidadEnum;
		EmpresaAfiliadoXCodigoDescuentoDao empresaAfiliadoXCodigoDescuento = new EmpresaAfiliadoXCodigoDescuentoDao();
		Date hoy = new Date();
		Date fechaInicial = Util.addDays(hoy,-365);
		Date fechaFinal = Util.addDays(hoy,0);
		try {
			LOG.info("Buscando codigos asignados entre : "+fechaInicial+" y "+fechaFinal+" para el afiliado con Id: "+afiliadoId);
			List<EmpresaAfiliadoXCodigoDescuento> listEmpresaAfiliadoXCodigoDescuento =
					empresaAfiliadoXCodigoDescuento
						.obtenerCodigosAsignadosPorEmpresaAfiliado(afiliadoId, fechaInicial,fechaFinal);
			LOG.info("El afiliado tiene: "+listEmpresaAfiliadoXCodigoDescuento.size()+"  codigos asignados y activos");
			/*List<EmpresaAfiliadoXCodigoDescuento> listEmpresaAfiliadoXCodigoDescuentoActivos =
					listEmpresaAfiliadoXCodigoDescuento.stream()
					.filter(registry -> registry.getAsignado().equals(true))
					.collect(Collectors.toList());*/
			/*LOG.info("El afiliado tiene: "+listEmpresaAfiliadoXCodigoDescuentoActivos.size()+"  codigos activos");*/
			if (listEmpresaAfiliadoXCodigoDescuento.size()>0)
			{
				listEmpresaAfiliadoXCodigoDescuento.sort((o1, o2) -> {
					if (o1.getFechaAsignacion() == null || o2.getFechaAsignacion() == null)
						return 0;
					return o2.getFechaAsignacion().compareTo(o1.getFechaAsignacion());
				});
				//Ultimo Codigo asignado
				EmpresaAfiliadoXCodigoDescuento lastEmpresaAfiliadoXCodigoDescuento =listEmpresaAfiliadoXCodigoDescuento.get(0);
				LOG.info("El afiliado tiene el ultimo codigo asignado con fecha de : "+lastEmpresaAfiliadoXCodigoDescuento.getFechaAsignacion());
				CodigoDescuentoDao codigoDescuentoDao = new CodigoDescuentoDao();
				lastCodigoDescuentoAsignado = codigoDescuentoDao.obtenerCodigoDescuentoPorCodigo(lastEmpresaAfiliadoXCodigoDescuento.getCodigoDescuento().getCodigo());
				Date fechadeAsignado =lastEmpresaAfiliadoXCodigoDescuento.getFechaAsignacion();
				Planes planAsignado = lastCodigoDescuentoAsignado.getEmpresaEmpleadorXPlan().getPlan();
				LOG.info("El afiliado tiene el ultimo codigo asignado con plan : "+planAsignado.getNombre()+" y  periocidad de : "+planAsignado.getPeriocidad()+
						", la fecha de es asignacion:"+fechadeAsignado);

				Date fechaInicialToValidate = com.corporativos_smartfit.util.Util.addDays(hoy,-(planAsignado.getPeriocidad()-planAsignado.getDiasValidacion()));
				Date fechaFinalToValidate = com.corporativos_smartfit.util.Util.addDays(hoy,0);
				LOG.info("Las fecha utilizadas para validar fueron : "+fechaInicialToValidate+" y "+fechaFinalToValidate);
				result =    fechadeAsignado.after(fechaInicialToValidate) && fechadeAsignado.before(fechaFinalToValidate);

			}
		} catch ( Exception e) {
			LOG.info("Se presento un error validando si el afiliado tiene codigos asignados");
		}
		LOG.info("El afiliado tiene codigo asignado?: "+result);
		return result;
	}

	private List<CodigoDescuentoModel> obtenerModeloCodigosDeCsv(String csvCodigosB64) throws IllegalArgumentException {
		LOG.info("Convirtiendo el string csv base 64 a el modelo CodigoDescuento ");
		ArrayList<CodigoDescuentoModel> listCodigos = new ArrayList<CodigoDescuentoModel>();
		try {
			// obtenemos las filas del archivo
			List<String[]> filas = csvUtil.obtenerFilasCsvBase64(csvCodigosB64, true);
			// procesamos las filas para crear el objeto que necesitamos
			LOG.info("Seran procesadas "+ filas.size() +" del archivo csv.");
			for (String[] fila : filas) {
				// comprobamos que la fila no tenga sus datos principales nulos
				if (fila[CSV_CODIGO_COL_CODIGO] != null && fila[CSV_CODIGO_COL_NOMBRE_PLAN] != null) {
					CodigoDescuentoModel codigo = new CodigoDescuentoModel();
					codigo.setCodigo(fila[CSV_CODIGO_COL_CODIGO]);
					codigo.setPeriodicidad(fila[CSV_CODIGO_COL_NOMBRE_PLAN]);
					codigo.setAsignado(false);
					LOG.info("Seran ingresado el codigo "+ codigo.getCodigo());
					listCodigos.add(codigo);
				}
			}
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return listCodigos;
	}

	/**
	 * Metodo que permite obtener una lista del modelo de engocio de afiliado a
	 * partir de un CSV en base 64
	 * 
	 * @param {csvAfiliadosB64}
	 *            String en base 64 con el archivo de afiliados
	 * @return List<EmpresaAfiliadoModel> lista de empleados obtenidos del CSV
	 */
	private List<EmpresaAfiliadoModel> obtenerModeloAfiliadosDeCsv(String csvAfiliadosB64)
			throws IllegalArgumentException {
		LOG.info("Obteniendo el listado de entidades  EmpresaAfiliadoModel del string csvBase64");
		ArrayList<EmpresaAfiliadoModel> afiliados = new ArrayList<>();
		try {
			// obtenemos las filas del archivo
			List<String[]> filas = csvUtil.obtenerFilasCsvBase64(csvAfiliadosB64, true);
			LOG.info("Se recuperaron "+filas.size()+" filas del strng csvBase64 ");
			// procesamos las filas para crear el objeto que necesitamos
			String tipoDoc = null;
			for (String[] fila : filas) {
				// comprobamos que la fila no tenga sus datos principales nulos
				if (null != fila[CSV_AFILIADO_COL_TIPODOCID] && null != fila[CSV_AFILIADO_COL_DOCNUMERO]
						&& null != fila[CSV_AFILIADO_COL_NOMBRE] && null != fila[CSV_AFILIADO_COL_CORREO]
						&& null != fila[CSV_AFILIADO_COL_ID_NOMBRE_PLAN]
						) {
					EmpresaAfiliadoModel afiliado = new EmpresaAfiliadoModel();
					// concatenamos el id de tipo doc si es necesario
					tipoDoc = (fila[CSV_AFILIADO_COL_TIPODOCID].contains(TIPO_DOC_ID_PLUS))
							? fila[CSV_AFILIADO_COL_TIPODOCID]
							: fila[CSV_AFILIADO_COL_TIPODOCID].concat(TIPO_DOC_ID_PLUS);
					afiliado.setTipoDocumentoIdentidad(tipoDoc);
					afiliado.setDocumentoNumero(fila[CSV_AFILIADO_COL_DOCNUMERO]);
					afiliado.setEmail(fila[CSV_AFILIADO_COL_CORREO]);
					afiliado.setNombre(fila[CSV_AFILIADO_COL_NOMBRE]);
					afiliado.setNombrePlan(fila[CSV_AFILIADO_COL_ID_NOMBRE_PLAN]);
					LOG.info("Sera agregado el numero de documento: "+afiliado.getDocumentoNumero()+" al listado EmpresaAfiliadoModel ");
					afiliados.add(afiliado);
				}
			}
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return afiliados;
	}

	@Override
	public ObtenerConvenioEmpresasRs obtenerConvenioEmpresas(ObtenerConvenioEmpresasRq request) {
		ObtenerConvenioEmpresasRs response = new ObtenerConvenioEmpresasRs();
		try {
			EmpresaEmpleadorDao dao = new EmpresaEmpleadorDao();
			List<EmpresaEmpleador> entidades = dao.obtenerTodosEntidades();
			List<EmpresaEmpleadorModel> modelos = new ArrayList<>();
			EmpresaEmpleadorModel modelo = null;
			for (EmpresaEmpleador entidad : entidades) {
				modelo = mapearEmpresaEmpleadorModel(entidad);
				modelos.add(modelo);
			}
			response.setEmpresas(modelos);
			response.setCodigoRespuesta(CONSUMO_SUCESS);
			response.setFechaRespuesta(new Date());
		} catch (Exception e) {
			String errorMsg = "Error en Bean-obtenerConvenioEmpresas, generico en la operación obtenerConvenioEmpresas."
					+ "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_GENERICO);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		}
		return response;
	}

	@Override
	public ObtenerConvenioAfiliadosRs obtenerConvenioAfiliados(ObtenerConvenioAfiliadosRq request) {
		ObtenerConvenioAfiliadosRs response = new ObtenerConvenioAfiliadosRs();
		try {
			// validamos datos de negocio necesarios
			if (null == request.getTipoDocumentoEmpresa() || null == request.getNumeroDocumentoEmpresa() || request.getMembresia() <=0) {
				String errorMsg = "Error en Bean-obtenerConvenioEmpresas, error de negocio debido a dato de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			// obtenemos entidades necesarias
			TipoDocumentoIdentidadDao tipoDocumentoIdentidadDao = new TipoDocumentoIdentidadDao();
			TipoDocumentoIdentidad tipoDocumentoIdentidad = tipoDocumentoIdentidadDao
					.obtenerTipoDocumentoPorId(Integer.parseInt(request.getTipoDocumentoEmpresa()));
			if (null == tipoDocumentoIdentidad) {
				String errorMsg = "Error en Bean, el tipo de documento no pudo ser obtenido de BD." + "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setCodigoError(ERROR_NEG_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			
			// Obtener Empleador
			int idTipoDoc = tipoDocumentoIdentidad.getId();
			EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
			EmpresaEmpleador empresaEmpleador = empresaEmpleadorDao.obtenerEmpresaPorDocumento(idTipoDoc,
					request.getNumeroDocumentoEmpresa(), request.getMembresia());
			if (null == empresaEmpleador) {
				String errorMsg = "Error en Bean, la empresa no pudo ser obtenida de BD." + "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setCodigoError(ERROR_NEG_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			
			// Obtener Afiliado
			EmpresaAfiliadoDao empresaAfiliadoDao = new EmpresaAfiliadoDao();
			List<EmpresaAfiliado> listEmpresaAfiliado = empresaAfiliadoDao.obtenerPorEmpresa(empresaEmpleador.getId(), null, null);
			EmpresaAfiliadoModel empresaAfiliadoModel = null;
			
			ArrayList<EmpresaAfiliadoModel> listEmpresaAfiliadoModel = new ArrayList<>();
			for (EmpresaAfiliado entidad : listEmpresaAfiliado) {
				if (null != entidad) {
					empresaAfiliadoModel = mapearEmpresaAfiliadoModel(entidad);
					// buscamos los codigos asignados
					ArrayList<CodigoDescuentoModel> codigosAsignados = new ArrayList<>();
					EmpresaAfiliadoXCodigoDescuentoDao empresaAfiliadoXCodigoDescuentoDao = new EmpresaAfiliadoXCodigoDescuentoDao();
					List<EmpresaAfiliadoXCodigoDescuento> cruces = empresaAfiliadoXCodigoDescuentoDao
							.obtenerCodigosAsignadosPorEmpresaAfiliado(entidad.getId(), request.getFechaInicial(),
									request.getFechaFinal());
					// solo si posee codigos
					if (cruces.size() > 0) {
						for (EmpresaAfiliadoXCodigoDescuento cruce : cruces) {
							CodigoDescuentoModel codigoModel = mapearCodigoDescuentoModel(cruce.getCodigoDescuento(),
									cruce);
							codigosAsignados.add(codigoModel);
						}
						if (codigosAsignados.size() > 0) {
							empresaAfiliadoModel.setCodigosAsignados(codigosAsignados);
							listEmpresaAfiliadoModel.add(empresaAfiliadoModel);
						}
					}
				}
			}
			
			// Obtener Cantidad de Codigos disponibles
			CodigoDescuentoDao codigoDescuentoDao = new CodigoDescuentoDao();
			List<CodigoDescuento> listCodigoDescuento = codigoDescuentoDao.obtenerCodigosDisponiblesPorEmpresaEmpleador(true, empresaEmpleador.getId());
			response.setEmpresa(mapearEmpresaEmpleadorModel(empresaEmpleador));
			response.setCsvEmpleados(obtenerCsvAfiliados(listEmpresaAfiliadoModel));
			response.setEmpleados(listEmpresaAfiliadoModel);
			response.setCodigoRespuesta(CONSUMO_SUCESS);
			response.setFechaRespuesta(new Date());
			response.setNumeroCodigosDisponibles(listCodigoDescuento.size());
		} catch (Exception e) {
			String errorMsg = "Error en Bean, generico." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_GENERICO);
			response.setFechaRespuesta(new Date());
		}
		return response;
	}


	@Override
	public ObtenerCodigosAsignadosRs obtenerCodigosAsignados(ObtenerCodigosAsignadosRq rq) {
		LOG.info("Obtener codigos asignados");
		ObtenerCodigosAsignadosRs response = new ObtenerCodigosAsignadosRs();
		try {
			// validamos datos de negocio necesarios
			// obtenemos los cruces de codigo con afiliado
			EmpresaAfiliadoXCodigoDescuentoDao empresaAfiliadoXCodigoDescuentoDao = new EmpresaAfiliadoXCodigoDescuentoDao();
			List<EmpresaAfiliadoXCodigoDescuento> joins = empresaAfiliadoXCodigoDescuentoDao
					.obtenerCodigosAsignados(rq.getFechaInicial(), rq.getFechaFinal());

			// indicamos los codigos
			List<CodigoDescuentoModel> codigosAsignados = new ArrayList<>();
			CodigoDescuentoModel codigoAsignado = null;
			for (EmpresaAfiliadoXCodigoDescuento join : joins) {

				String codigo = (null != join.getCodigoDescuento()) ? join.getCodigoDescuento().getCodigo() : null;

				// creamos un codigo nuevo, le asignamos los valores y lo ingresamos al arreglo
				codigoAsignado = new CodigoDescuentoModel();
				codigoAsignado.setCodigo(codigo);
				codigoAsignado.setAsignado(join.getAsignado());
				LOG.info("FechaAsignacion : " + join.getFechaAsignacion()+"para el codigo"+codigo);
				codigoAsignado.setFechaAsignacion(join.getFechaAsignacion());

				EmpresaAfiliadoModel afiliado = mapearEmpresaAfiliadoModel(join.getEmpresaAfiliado());
				if (null != afiliado) {
					LOG.info("EmpresaAfiliadoModel: " + afiliado.getDocumentoNumero());
				}
				codigoAsignado.setAfiliado(afiliado);

				codigosAsignados.add(codigoAsignado);
			}
			response.setCodigosAsignados(codigosAsignados);
			response.setCsvCodigosAsignados(obtenerCsvCodigosAsignados(codigosAsignados));
			response.setCodigoRespuesta(CONSUMO_SUCESS);
			response.setFechaRespuesta(new Date());
		} catch (Exception e) {
			String errorMsg = "Error en Bean-obtenerCodigosAsignados, generico." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setCodigoError(ERROR_GENERICO);
			response.setFechaRespuesta(new Date());
		}
		return response;
	}

	@Override
	public InformationInitialToCreateCompanyRs getInitialInformationToCreateCorporative( String username) throws ErrorGeneral {
		LOG.info("INIT getInitialInformationToCreateCorporative" );
		InformationInitialToCreateCompanyRs response  = new InformationInitialToCreateCompanyRs();
		PlanesDao planesDao = new PlanesDao();
		PlanesEmpresaDao planesEmpresaDao = new PlanesEmpresaDao();
		EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
		response.setListMembresias(this.obtenerMembresias());
		response.setListPlanes(planesDao.obtenerTodosEntidades());
		response.setListPlanesPorEmpresa(planesEmpresaDao.obtenerTodosEntidades());
		response.setListEmpresas(this.obtenerEmpresas());
		response.setCanCreatePlan(this.userCanCreatePlan(username));
		LOG.info("END getInitialInformationToCreateCorporative" );
		return response;
	}

	private boolean userCanCreatePlan( String userName){
		GestionadorService gestionadorService = new GestionadorServiceImpl();
		try {
			return gestionadorService.esRolAdministrativo2(userName);
		} catch (Exception e){
			LOG.info("fail validanting if user can create plan -> ",e);
			return false;
		}
	}
	private List<EmpresaEmpleadorModel> obtenerEmpresas() {
		List<EmpresaEmpleadorModel> response = new ArrayList<>();
		try {
			EmpresaEmpleadorDao dao = new EmpresaEmpleadorDao();
			List<EmpresaEmpleador> entidades = dao.obtenerTodosEntidades();
			for (EmpresaEmpleador entidad : entidades) {
				EmpresaEmpleadorModel modelo = mapearEmpresaEmpleadorModel(entidad);
				response.add(modelo);
			}
		} catch (Exception e) {
			LOG.error("Error en bean-obtenerMembresias, al obtener membresias", e);

		}
		return response;
	}


	@Override
	public List<MembresiaModel> obtenerMembresias() {
		List<MembresiaModel> membresias = new ArrayList<>();
		try {
			MembresiaDao dao = new MembresiaDao();
			List<Membresia> entidades = dao.obtenerTodosEntidades();
			MembresiaModel model = null;
			for (Membresia entidad : entidades) {
				model = mapearMembresiaModel(entidad);
				if (null != model) {
					membresias.add(model);
				}
			}
		} catch (Exception e) {
			LOG.error("Error en bean-obtenerMembresias, al obtener membresias", e);
		}
		return membresias;
	}


	private MembresiaModel mapearMembresiaModel(Membresia entidad) {
		if (null == entidad) {
			return null;
		}
		MembresiaModel model = new MembresiaModel();
		model.setId(entidad.getId());
		model.setNombre(entidad.getNombre());
		return model;
	}

	private EmpresaEmpleador mapearEmpresaEmpleadorEntidad(EmpresaEmpleadorModel empresa) {
		LOG.info("Mapeando la entidad EmpresaEmpleador de la empresa "+empresa.getRazonSocial());
		EmpresaEmpleador entidad = new EmpresaEmpleador();
		TipoDocumentoIdentidadDao tipoDocumentoIdentidadDao = new TipoDocumentoIdentidadDao();
		TipoDocumentoIdentidad tipoDocumentoEmpresa = tipoDocumentoIdentidadDao.obtenerTipoDocumentoPorId(empresa.getDocumentoTipo());
		entidad.setTipoDocumentoIdentidad(tipoDocumentoEmpresa);
		entidad.setDocumentoNumero(empresa.getDocumentoNumero());
		entidad.setEmail(empresa.getEmail());
		entidad.setRazonSocial(empresa.getRazonSocial());
		entidad.setRepresentanteNombre(empresa.getRepresentanteLegal());
		entidad.setTelefono(empresa.getTelefono());
		Membresia membresia = new Membresia();
		// asignamos clave foranea sin necesidad de obtener la entidad de BD
		membresia.setId(empresa.getMembresia());
		entidad.setMembresia(membresia);
		// datos no ingresados por usuario
		entidad.setId(empresa.getIdEmpresa());
		entidad.setActiva(empresa.isActivo());
		entidad.setFechaCreacion(empresa.getFechaInicioConvenio());
		entidad.setLogo(empresa.getLogo());
		entidad.setTextoEmail(empresa.getTextoEmail());
		return entidad;
	}

	/*private EmpresaEmpleador mapearEmpresaEmpleadorEntidad(int idEmpresaEmpleadorPlan) {
		LOG.info("Mapeando la entidad EmpresaEmpleador para el idEmpresaEmpleadorPlan "+idEmpresaEmpleadorPlan);
		EmpresaEmpleador entidad = new EmpresaEmpleador();
		EmpresaEmpleadorXPlanDao empresaEmpleadorXPlanDao = new EmpresaEmpleadorXPlanDao();
		EmpresaEmpleadorXPlan empresaEmpleadorXPlan = empresaEmpleadorXPlanDao.getEmpresaEmpleadorXPlanById(idEmpresaEmpleadorPlan);
		return empresaEmpleadorXPlan.getEmpresaEmpleador();
	}*/

	private CodigoDescuento mapearCodigoDescuentoEntidad(CodigoDescuentoModel model,EmpresaEmpleadorModel empresa) {
		LOG.info("Mapeando el codigo "+model.getCodigo()+" al modelo CodigoDescuento");
		CodigoDescuento entidad = new CodigoDescuento();
		//EmpresaEmpleador empresaEmpleador = this.mapearEmpresaEmpleadorEntidad(empresa);
		PlanesAfiliadoService planesAfiliadoService = new PlanesAfiliadoService();
		EmpresaEmpleadorXPlan empresaEmpleadorXPlan =planesAfiliadoService.getPlanByNombrePlan(empresa.getIdEmpresa(),model.getPeriodicidad());
		entidad.setIdEmpresaPlan(empresaEmpleadorXPlan.getId());
		// datos generados por el sistema
		//entidad.setId(model.getId());
		entidad.setCodigo(model.getCodigo());
		entidad.setAsignado(model.getAsignado());
		return entidad;
	}

	private EmpresaAfiliado mapearEmpresaAfiliadoEntidad(EmpresaAfiliadoModel model) {
		LOG.info("Mapeando la entidad EmpresaAfiliado del modelo EmpresaAfiliadoModel para el numero: "+model.getDocumentoNumero());
		EmpresaAfiliado entidad = new EmpresaAfiliado();
		TipoDocumentoIdentidadDao tipoDocumentoIdentidadDao = new TipoDocumentoIdentidadDao();
		TipoDocumentoIdentidad tipoDocumento = tipoDocumentoIdentidadDao.obtenerTipoDocumentoPorCodigo(model.getTipoDocumentoIdentidad());
		entidad.setTipoDocumentoIdentidad(tipoDocumento);
		entidad.setDocumentoNumero(model.getDocumentoNumero());
		entidad.setEmail(model.getEmail());
		entidad.setNombre(model.getNombre());
		// asignamos clave foranea sin necesidad de obtener la entidad de BD
		EmpresaEmpleador empresaEmpleador = this.mapearEmpresaEmpleadorEntidad(model.getEmpresaEmpleador());
		entidad.setEmpresaEmpleador(empresaEmpleador);
		if (null != empresaEmpleador && null == empresaEmpleador.getId()) {
			EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
			int tipoDoc = (null != empresaEmpleador.getTipoDocumentoIdentidad())
					? empresaEmpleador.getTipoDocumentoIdentidad().getId()
					: -1;
			String numDoc = empresaEmpleador.getDocumentoNumero();
			Membresia membresia = empresaEmpleador.getMembresia();
			try {
				empresaEmpleador = empresaEmpleadorDao.obtenerEmpresaPorDocumento(tipoDoc, numDoc, membresia.getId());
			} catch (Exception e) {
				empresaEmpleador = null;
			}
			entidad.setEmpresaEmpleador(empresaEmpleador);
		}
		// datos generados por el sistema
		entidad.setId(model.getId());
		entidad.setFechaCreacion(model.getFechaRegistro());

		return entidad;
	}

	private CodigoDescuentoModel mapearCodigoDescuentoModel(CodigoDescuento entidad,
															EmpresaAfiliadoXCodigoDescuento empresaAfiliadoXCodigoDescuento) {
		LOG.info("Mapenado la entidad CodigoDescuentoModel con el codigo: "+entidad.getCodigo());
		CodigoDescuentoModel model = new CodigoDescuentoModel();
		model.setCodigo(entidad.getCodigo());
		// datos generados por el sistema
		model.setAsignado(entidad.getAsignado());
		Planes plan =entidad.getEmpresaEmpleadorXPlan().getPlan();
		model.setPeriodicidad(plan.getNombre());
		if (null != empresaAfiliadoXCodigoDescuento) {
			model.setFechaAsignacion(empresaAfiliadoXCodigoDescuento.getFechaAsignacion());
		}

		return model;
	}

	private EmpresaAfiliadoModel mapearEmpresaAfiliadoModel(EmpresaAfiliado entidad) {
		LOG.info("Mapeando la entiada EmpresaAfiliadoModel para el afiliado: "+entidad.getDocumentoNumero());
		EmpresaAfiliadoModel model = new EmpresaAfiliadoModel();
		model.setId(entidad.getId());
		model.setDocumentoNumero(entidad.getDocumentoNumero());
		model.setEmail(entidad.getEmail());
		model.setNombre(entidad.getNombre());
		if (null != entidad.getEmpresaEmpleador()) {
			EmpresaEmpleadorModel empresaEmpleador = mapearEmpresaEmpleadorModel(entidad.getEmpresaEmpleador());
			model.setEmpresaEmpleador(empresaEmpleador);
		}
		if (null != entidad.getTipoDocumentoIdentidad()) {
			model.setTipoDocumentoIdentidad(entidad.getTipoDocumentoIdentidad().getCodigo());
		}

		// datos generados por el sistema
		model.setFechaRegistro(entidad.getFechaCreacion());
		return model;
	}

	private EmpresaEmpleadorModel mapearEmpresaEmpleadorModel(EmpresaEmpleador entidad) {
		LOG.info("Obteniendo la entidad EmpresaEmpleadorModel del numero de documento: "+entidad.getDocumentoNumero());
		EmpresaEmpleadorModel model = new EmpresaEmpleadorModel();
		model.setIdEmpresa(entidad.getId());
		model.setDocumentoNumero(entidad.getDocumentoNumero());
		model.setEmail(entidad.getEmail());
		model.setRazonSocial(entidad.getRazonSocial());
		model.setRepresentanteLegal(entidad.getRepresentanteNombre());
		model.setTelefono(entidad.getTelefono());
		// asignamos objetos de clave foranea
		if (null != entidad.getTipoDocumentoIdentidad()) {
			int docTipoId = Integer.parseInt(entidad.getTipoDocumentoIdentidad().getId().toString());
			model.setDocumentoTipo(docTipoId);
			model.setDocumentoTipoNombre(entidad.getTipoDocumentoIdentidad().getDescripcion());
		}
		if (null != entidad.getMembresia()) {
			model.setMembresia(entidad.getMembresia().getId());
			model.setMembresiaNombre(entidad.getMembresia().getNombre());
		}
		// datos generados por el sistema
		model.setActivo(entidad.isActiva());
		model.setFechaInicioConvenio(entidad.getFechaCreacion());
		model.setLogo(entidad.getLogo());
		model.setTextoEmail(entidad.getTextoEmail());
		return model;
	}
	
	@Override
	public ProcesarCsvConvenioCodigosRs crearCodigosCsv(ProcesarCsvConvenioCodigosRq request) {
		ProcesarCsvConvenioCodigosRs response = new ProcesarCsvConvenioCodigosRs();
		try {
			if (null == request.getEmpresa()) {
				String errorMsg = "Error en Bean-crearCodigosCsv, error de negocio debido a dato empresa de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (null == request.getEmpresa().getDocumentoNumero()) {
				String errorMsg = "Error en Bean-crearCodigosCsv, error de negocio debido a dato empresa.documentoNumero de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (request.getEmpresa().getDocumentoTipo() <= 0) {
				String errorMsg = "Error en Bean-crearCodigosCsv, error de negocio debido a dato empresa.documentoTipoId de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			if (null == request.getCsvCodigos()) {
				String errorMsg = "Error en Bean-crearCodigosCsv, error de negocio debido a dato csvCodigos de request nulo."
						+ "\n";
				LOG.error(errorMsg);
				response.setCodigoRespuesta(CONSUMO_ERROR_NEGOCIO);
				response.setCodigoError(ERR_NEG_REQ_NULL);
				response.setFechaRespuesta(new Date());
				return response;
			}
			
			EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
			EmpresaEmpleador empresaEmpleador = empresaEmpleadorDao.obtenerEmpresaPorDocumento(
					request.getEmpresa().getDocumentoTipo(), 
					request.getEmpresa().getDocumentoNumero(), 
					request.getEmpresa().getMembresia());

			if (empresaEmpleador != null) {
				// en caso de ser exitoso procesamos el CSV de codigos y afiliados
				EmpresaEmpleadorModel empresaEmpleadorModel = mapearEmpresaEmpleadorModel(empresaEmpleador);
		
				ProcesarCsvConvenioCodigosRq procesarCsvCodigosRq = new ProcesarCsvConvenioCodigosRq();
				procesarCsvCodigosRq.setCsvCodigos(request.getCsvCodigos());
				procesarCsvCodigosRq.setEmpresa(empresaEmpleadorModel);
				
				// llamamos al servicio de asignación de codigos
				ProcesarCsvConvenioCodigosRs rsCodigo = procesarCsvConvenioCodigos(procesarCsvCodigosRq);
				boolean procesoCodigoExitoso = rsCodigo.getCodigoRespuesta().equals(CONSUMO_SUCESS) ? true : false;
				response.setCodigoRespuesta(procesoCodigoExitoso ? CONSUMO_SUCESS : CONSUMO_ERROR);
				if (!procesoCodigoExitoso) {
					response.setCodigoError(ERR_CA_CCE_REGAFIL);
					response.setFechaRespuesta(new Date());
					return response;
				}
				
				response.setCodigosAsignados(rsCodigo.getCodigosAsignados());
				response.setCodigoError(rsCodigo.getCodigoError());
				response.setCodigosAsignados(rsCodigo.getCodigosAsignados());
				response.setMensajesError(rsCodigo.getMensajesError());
				String csvCodigosFallidos = obtenerCsvFallidosCodigosDescuento(rsCodigo.getCodigosError(), rsCodigo.getMensajesError());
				response.setCsvCodigosFallidos(csvCodigosFallidos);
				String csvCodigosCompletos = obtenerCsvExitososCodigoDescuento(rsCodigo.getCodigosAsignados());
				response.setCsvCodigosCompletos(csvCodigosCompletos);
			} else {
				String errorMsg = "Error en Bean-crearCodigosCsv, al realizar la operación crearCodigosCsv."
						+ " ";
				LOG.error(errorMsg + "No se pudo guardar la entidad en BD");
				response.setCodigoError(ERROR_CREAR_EMPRESA);
				response.setCodigoRespuesta(CONSUMO_ERROR);
				response.setFechaRespuesta(new Date());
				return response;
			}
		} catch (ConstraintViolationException e) {
			String errorMsg = "Error en Bean-crearCodigosCsv, la entidad ya existe en BD." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_NEG_ENTIDAD_EXISTENTE_EN_BD);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		} catch (IllegalArgumentException e) {
			String errorMsg = "Error en Bean-crearCodigosCsv, al procesar el CSV de afiliados." + "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_CSV_READ);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		} catch (Exception e) {
			String errorMsg = "Error en Bean-crearCodigosCsv, generico en la operación crearCodigosCsv."
					+ "\n";
			LOG.error(errorMsg + e.toString(), e);
			response.setCodigoError(ERROR_GENERICO);
			response.setCodigoRespuesta(CONSUMO_ERROR);
			response.setFechaRespuesta(new Date());
		}
		return response;
	}
	
	@Override
	public boolean eliminarCodigoAsignado(int afiliadoId, String codigoAsignado) {
		try {
			// Buscar CodigoDescuento
			CodigoDescuentoDao codigoDescuentoDao = new CodigoDescuentoDao();
			CodigoDescuento codigoDescuento = codigoDescuentoDao.obtenerCodigoDescuentoPorCodigo(codigoAsignado);

			// Eliminar EmpresaAfiliadoXCodigoDescuento
	    	EmpresaAfiliadoXCodigoDescuentoDao emprAfiXCodDesDao = new EmpresaAfiliadoXCodigoDescuentoDao();
	    	EmpresaAfiliadoXCodigoDescuento emprAfiXcodDes = emprAfiXCodDesDao.obtenerPorAfiliadoCodigo(afiliadoId, codigoDescuento.getId());
        	emprAfiXCodDesDao.eliminar(emprAfiXcodDes);
	    	        	
        	// Eliminar CodigoDescuento
        	codigoDescuentoDao.eliminar(codigoDescuento);
        	
        	return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
