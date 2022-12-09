package co.com.smartfit.web.service;


import co.com.smartfit.web.business.rq.ObtenerConvenioAfiliadosRq;
import co.com.smartfit.web.business.rs.CrearEmpresaRs;
import co.com.smartfit.web.business.rs.ObtenerConvenioAfiliadosRs;
import co.com.smartfit.web.dao.EmpresaEmpleadorDao;
import co.com.smartfit.web.dao.UsersDao;
import co.com.smartfit.web.entities.EmpresaEmpleador;
import co.com.smartfit.web.entities.ErrorGeneral;
import co.com.smartfit.web.entities.TipoDocumentoIdentidad;
import co.com.smartfit.web.entities.Users;
import co.com.smartfit.web.util.Util;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service("empresaService")
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	private static final Logger LOG = Logger.getLogger(EmpresaServiceImpl.class);

	private static final String CONSUMO_SUCESS = "200";
	private static final String CONSUMO_ERROR = "500";
	private static final String CONSUMO_ERROR_NEGOCIO = "700";
	private static final String TIPO_DOC_ID_PLUS = "01";
	private static final int DB_ERROR = -1;

	// codigos de error en respuestas
	private static final String ERROR_GENERICO = "ERR_GENERIC"; // error generico
																		// disponibles
	// error al identificar que la entidad que se itnenta crear ya existe en DB
	private static final String ERROR_NEG_ENTIDAD_EXISTENTE_EN_BD = "ERR_NEG_EXIST";

	@Autowired
	ConvenioAdminService convenioAdminService;

	private  EmpresaEmpleadorDao empresaEmpleadorDao;

	public EmpresaServiceImpl() {
		empresaEmpleadorDao =  new EmpresaEmpleadorDao();
		convenioAdminService = new ConvenioAdminServiceImpl();
	}


	@Override
	public CrearEmpresaRs crearEmpresa(HttpServletRequest rq) {
		CrearEmpresaRs response = new CrearEmpresaRs();
		response.setFechaRespuesta(new Date());
		try {
			EmpresaEmpleador empresa = this.mapEmpresa(rq);
			empresaEmpleadorDao.guardar(empresa);
			response.setCodigoRespuesta(CONSUMO_SUCESS);
		} catch (ConstraintViolationException e) {
			String errorMsg = "Error en Bean-crearConvenioEmpresa, la entidad ya existe en BD." + "\n";
			response = this.mapError(response,ERROR_NEG_ENTIDAD_EXISTENTE_EN_BD,CONSUMO_ERROR,errorMsg,e);
		} catch (Exception e) {
			String errorMsg = "Error en Bean-crearConvenioEmpresa, generico en la operación crearConvenioEmpresa."
					+ "\n";
			response = this.mapError(response,ERROR_GENERICO,CONSUMO_ERROR,errorMsg,e);
		}
		return response;
	}

	private EmpresaEmpleador mapEmpresa(HttpServletRequest rq)  throws ErrorGeneral {
		EmpresaEmpleador empresa = new EmpresaEmpleador();
		try {
			empresa.setActiva(true);
			empresa.setRazonSocial(ServletRequestUtils.getStringParameter(rq, "razonSocial"));
			empresa.setDocumentoTipo(ServletRequestUtils.getIntParameter(rq, "tipoDocumento"));
			empresa.setDocumentoNumero(ServletRequestUtils.getStringParameter(rq, "NIT"));
			empresa.setTelefono(ServletRequestUtils.getStringParameter(rq, "telefono"));
			empresa.setEmail(ServletRequestUtils.getStringParameter(rq, "email"));
			empresa.setRepresentanteNombre(ServletRequestUtils.getStringParameter(rq, "nombreRepresentante"));
			empresa.setIdMembresia(ServletRequestUtils.getIntParameter(rq, "membresia"));
			empresa.setTextoEmail(ServletRequestUtils.getStringParameter(rq, "textoEmail"));
			empresa.setFechaCreacion(new Date());
			empresa.setLogo(Util.guardarBase64Archivo(ServletRequestUtils.getStringParameter(rq, "logoImagen")));
		} catch (Exception reason) {
			throw new ErrorGeneral(500,reason.getMessage());
		}
		return  empresa;
	}

	private CrearEmpresaRs mapError(CrearEmpresaRs response,
									String codigoError,
									String codigoRespuesta,
									String errorMsg,
									Exception e){
		LOG.error(errorMsg + e.toString(), e);
		response.setCodigoError(codigoError);
		response.setCodigoRespuesta(codigoRespuesta);
		return response;
	}
	@Override
	public ObtenerConvenioAfiliadosRs vistaEmpresa(String username){
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.obtenerUsuario(username);
		ObtenerConvenioAfiliadosRq rq = new ObtenerConvenioAfiliadosRq();
		EmpresaEmpleador company =empresaEmpleadorDao.obtenerEmpresaEmpleadorPorId(user.getIdCompany());
		rq.setTipoDocumentoEmpresa(String.valueOf(company.getTipoDocumentoIdentidad().getId()));
		rq.setNumeroDocumentoEmpresa(company.getDocumentoNumero());
		rq.setMembresia(company.getIdMembresia());
		return convenioAdminService.obtenerConvenioAfiliados(rq);
	}

}
