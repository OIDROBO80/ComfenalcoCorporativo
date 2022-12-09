package co.com.smartfit.web.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import javax.ws.rs.core.Context;

import co.com.smartfit.web.business.rq.*;
import co.com.smartfit.web.business.rs.*;
import co.com.smartfit.web.entities.CantidadCodigosPorPlan;
import co.com.smartfit.web.entities.ErrorGeneral;
import co.com.smartfit.web.entities.Planes;
import co.com.smartfit.web.entities.PlanesEmpresa;
import co.com.smartfit.web.service.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import co.com.smartfit.web.model.EmpresaEmpleadorModel;
import co.com.smartfit.web.model.MembresiaModel;
import co.com.smartfit.web.util.Util;
import co.com.smartfit.web.util.Security;

/**
 * @author alejandro.areiza
 * @version 1.0
 */
@Controller
@RequestMapping(method={RequestMethod.POST,RequestMethod.GET})
public class MainController {

    private static final Logger LOG = Logger.getLogger(MainController.class);

    private static final int TAMANO_REGISTROS_PAGINA = 20;
    private static final String ROL_ADMINISTRATIVO_PAGE = "listar_empresas_convenio";
    private static final String ROL_CORPORATIVO_PAGE = "crear_empresa_convenio";
    private static final String ROL_COMPANY_PAGE = "vista_empresa";
    private static final String ROL_NODEFINIDO_PAGE = "403";
    private static final String CONSUMO_SUCESS = "200";
    private static final String CONSUMO_DENIED = "403";
    private static final String CONSUMO_ERROR = "500";

    // codigos de error en respuestas
    private static final String ERROR_GENERICO = "ERR_GENERIC"; // error generico
    private Security security;

    @Inject
    Validator validator;

    @Autowired
    ConvenioAdminService convenioAdminService;


    @Autowired
    EmpresaService empresaService;

    @Autowired
    PlanesPorEmpresaService planesPorEmpresaService;

    @Autowired
    AsignarPlanAEmpresaService asignarPlanAEmpresaService;

    public MainController() {
        security = new Security();
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(ModelMap model) {
        LOG.info("request to: /home");
        return "home";
    }

    @RequestMapping(value = { "/gestionar" }, method = RequestMethod.GET)
    public String gestionarAcceso(ModelMap model, @Context HttpServletRequest request) {
        LOG.info("request to: /gestionar");
        String pageTipoRol = ROL_NODEFINIDO_PAGE;
        String username = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                username = userDetail.getUsername();
                GestionadorService gestionadorService = new GestionadorServiceImpl();
                if (gestionadorService.esRolAdministrativo(username) || gestionadorService.esRolAdministrativo2(username) ) {
                    pageTipoRol = ROL_ADMINISTRATIVO_PAGE;
                } else if (gestionadorService.esRolCorporativo(username)) {
                        pageTipoRol = ROL_CORPORATIVO_PAGE;
                } else if (gestionadorService.esRolEmpresa(username)) {
                    pageTipoRol = ROL_COMPANY_PAGE;
                }
                String sessionId = request.getRequestedSessionId();
                LOG.info("sessionId" +sessionId);
                String token = security.createToken(username, sessionId);
                LOG.info("token" +token);
                model.addAttribute("sessionToken", token);
            }
        } catch (Exception e) {
            LOG.info("Error en gestionarAcceso " + e.toString());
        }
        model.addAttribute("empresaUsername", username);
        LOG.info("NAME PAGE "+pageTipoRol);
        return pageTipoRol;
    }

    @RequestMapping(value = { "/consulta_afiliado" }, method = RequestMethod.GET)
    public String consultaAfiliadoRedireccion(ModelMap model) {
        String username = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                username = userDetail.getUsername();
            }
        } catch (Exception e) {
            LOG.info("Error en gestionarAcceso " + e.toString());
        }
        model.addAttribute("empresaUsername", username);
        return "consulta_afiliado";
    }

    @RequestMapping(value = { "/listar_convenios" }, method = RequestMethod.GET)
    public String listarConveniosRedireccion(ModelMap model) {
        String username = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                username = userDetail.getUsername();
            }
        } catch (Exception e) {
            LOG.info("Error en gestionarAcceso " + e.toString());
        }
        model.addAttribute("empresaUsername", username);
        return "listar_convenios";
    }

    @RequestMapping(value = { "/listar_empresas" }, method = RequestMethod.GET)
    public String listarEmpresasRedireccion(ModelMap model) {
        return "listar_empresas";
    }

    @RequestMapping(value = { "/crear_empresa" }, method = RequestMethod.GET)
    public String consultarConveniosRedireccion(ModelMap model) {
        return "crear_empresa";
    }

    @RequestMapping(value = { "/listar_empleados" }, method = RequestMethod.GET)
    public String listarEmpleadosRedireccion(ModelMap model) {
        String username = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                username = userDetail.getUsername();
            }
        } catch (Exception e) {
            LOG.info("Error en gestionarAcceso " + e.toString());
        }
        model.addAttribute("empresaUsername", username);
        return "listar_empleados";
    }

    @RequestMapping(value = { "/activar_plan_empleados" }, method = RequestMethod.GET)
    public String activarPlanEmpleadoRedireccion(ModelMap model) {
        String username = "";
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                username = userDetail.getUsername();
            }
        } catch (Exception e) {
            LOG.info("Error en gestionarAcceso " + e.toString());
        }
        model.addAttribute("empresaUsername", username);
        return "activar_plan_empleado";
    }

    @RequestMapping(value = { "/cambiar_contrasena" }, method = RequestMethod.GET)
    public String cambiarContrasenaRedireccion(ModelMap model) {
        return "cambiar_contrasena";
    }

    @RequestMapping(value = { "/mensaje" }, method = RequestMethod.GET)
    public String mensajeRedireccion(ModelMap model) {
        return "mensaje";
    }

    @RequestMapping(value = { "/mensaje_convenio" }, method = RequestMethod.GET)
    public String mensaje_convenioRedireccion(ModelMap model) {
        return "mensaje_convenio";
    }
    
    @RequestMapping(value = { "/mensaje_codigos_import" }, method = RequestMethod.GET)
    public String mensaje_codigosImportRedireccion(ModelMap model) {
        return "mensaje_codigos_import";
    }

    /**
     * Metodo que redirecciona a la vista de cerar empresa de convenio para la parte de administracion de convenios
     * 
     * @param model
     * @return String
     */
    @RequestMapping(value = { "/crear_convenio_empresa" }, method = RequestMethod.GET)
    public String crearConvenioEmpresaRedireccion(ModelMap model) {
        return "crear_empresa_convenio";
    }
    


    /**
     * Metodo que permite crear un nuevo convenio de empresa para la parte de administracion de convenios
     *
     * @param {HttpServletRequest}
     *            rq con los parametros setiados desde front, a partir de auqio se pueden obtener datos de sesión
     * @return ModelAndView: Objeto de SpringMVC que contiene la informaciópn que se intereza mostrar en la vista
     */
    @RequestMapping(value = "/crearConvenioEmpresa", method = RequestMethod.POST)
    public String crearConvenioEmpresa(HttpServletRequest rq, ModelMap map) throws ServletRequestBindingException {
        // inicializamos el response
        CrearConvenioEmpresaRs res = new CrearConvenioEmpresaRs();        
        
        try {
            LOG.info("REQUEST [" + rq.getRequestedSessionId() + "] to: " + new Object() {}.getClass().getEnclosingMethod().getName() + ", metodo POST");
            
            // obtenemos los datos enviados desde el sitio
            String razonSocial = ServletRequestUtils.getStringParameter(rq, "razonSocial");
            int tipoDocumento = ServletRequestUtils.getIntParameter(rq, "tipoDocumento");
            String NIT = ServletRequestUtils.getStringParameter(rq, "NIT");
            String telefono = ServletRequestUtils.getStringParameter(rq, "telefono");
            String email = ServletRequestUtils.getStringParameter(rq, "email");
            String nombreRepresentante = ServletRequestUtils.getStringParameter(rq, "nombreRepresentante");
            String membresia = ServletRequestUtils.getStringParameter(rq, "membresia");
            String textoEmail = ServletRequestUtils.getStringParameter(rq, "textoEmail");
            String logoImagen = ServletRequestUtils.getStringParameter(rq, "logoImagen");
            String nombreLogoImagen = Util.guardarBase64Archivo(logoImagen);
            
            URL requestURL = new URL(rq.getRequestURL().toString());
            String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
            //String urlLogo = requestURL.getProtocol() + "://" + requestURL.getHost() + port + "/Admin/upload/image/" + nombreLogoImagen + "/";
            String urlLogo = nombreLogoImagen;
    		
            String codigosCsv = ServletRequestUtils.getStringParameter(rq, "codigosCsv");
            if (null != codigosCsv && codigosCsv.contains("base64,")) {
            	codigosCsv = codigosCsv.substring(codigosCsv.indexOf("base64,") + "base64,".length());
            }
            String afiliadosCsv = ServletRequestUtils.getStringParameter(rq, "afiliadosCsv");
            if (null != afiliadosCsv && afiliadosCsv.contains("base64,")) {
                afiliadosCsv = afiliadosCsv.substring(afiliadosCsv.indexOf("base64,") + "base64,".length());
            }
            
            // datos basicos
            CrearConvenioEmpresaRq request = new CrearConvenioEmpresaRq();
            request.setRqIpOrigen(rq.getRemoteAddr());
            request.setRqUsuario(rq.getRemoteUser());
            request.setRqFecha(new Date());
            request.setSessionId(rq.getRequestedSessionId());
            // datos de negocio
            EmpresaEmpleadorModel empresaAux = new EmpresaEmpleadorModel();
            empresaAux.setRazonSocial(razonSocial);
            empresaAux.setDocumentoTipo(tipoDocumento);
            empresaAux.setDocumentoNumero(NIT);
            empresaAux.setTelefono(telefono);
            empresaAux.setEmail(email);
            empresaAux.setRepresentanteLegal(nombreRepresentante);
            empresaAux.setMembresia(Integer.parseInt(membresia));
            empresaAux.setLogo(urlLogo);
            empresaAux.setTextoEmail(textoEmail);
            request.setEmpresa(empresaAux);
            request.setCsvCodigos(codigosCsv);
            request.setCsvAfiliados(afiliadosCsv);
            res = convenioAdminService.crearConvenioEmpresa(request);
            // mapeamos datos de respuesta
            map.addAttribute("accion", "EmpresaCrear");
            map.addAttribute("codigoRespuesta", res.getCodigoRespuesta());
            map.addAttribute("codigoError", res.getCodigoError());
            map.addAttribute("csvAfiliadosFallidos", res.getCsvAfiliadosFallidos());
            map.addAttribute("csvAfiliadosCompletos", res.getCsvAfiliadosCompletos());
            map.addAttribute("afiliadosAsignados", res.getAfiliadosAsignados());
            map.addAttribute("codigosAsignados", res.getCodigosAsignados());
            map.addAttribute("afiliadosError", res.getAfiliadosError());
            map.addAttribute("mensajesError", res.getMensajesError());
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición crearConvenioEmpresa." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE [" + rq.getRequestedSessionId() + "] from: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " " + res.getCodigoRespuesta());
        return "mensaje_convenio";
    }

    @RequestMapping(value = "/crearEmpresaV2", method = RequestMethod.POST)
    public String crearEmpresaNew(HttpServletRequest rq, ModelMap map) throws ServletRequestBindingException {
        CrearEmpresaRs res = new CrearEmpresaRs();
        LOG.info("REQUEST [" + rq.getRequestedSessionId() + "] to: " + new Object() {}.getClass().getEnclosingMethod().getName() + ", metodo POST");
        res = empresaService.crearEmpresa(rq);
        // mapeamos datos de respuesta
        map.addAttribute("accion", "EmpresaCrear");
        map.addAttribute("codigoRespuesta", res.getCodigoRespuesta());
        map.addAttribute("codigoError", res.getCodigoError());
        LOG.info("RESPONSE [" + rq.getRequestedSessionId() + "] from: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " " + res.getCodigoRespuesta());
        return "mensaje_convenio";
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getInformationInitialToCreateCompany/{userName}", method = RequestMethod.GET)
    public InformationInitialToCreateCompanyRs getInformationInitialToCreateCompany(@Context HttpServletRequest rq,
                                                                                    @PathVariable String userName) throws ServletRequestBindingException {
        LOG.info("INIT RESPONSE from: /getInformationInitialToCreateCompany");
        InformationInitialToCreateCompanyRs res = new InformationInitialToCreateCompanyRs();
        try {
            res  = convenioAdminService.getInitialInformationToCreateCorporative(userName);
            res.setCodigoRespuesta(200);
        } catch (ErrorGeneral e) {
            res.setCodigoRespuesta(e.getStatusCode());
            res.setDescription(e.getMessage());
        }
        LOG.info("RESPONSE from: /getInformationInitialToCreateCompany, codigo: " + res.getCodigoRespuesta());
        return res;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/createPlan/{nombrePlan}/{dias}", method = RequestMethod.GET)
    public CrearPlanRs createPlan(@Context HttpServletRequest rq,
                                  @PathVariable String nombrePlan,
                                  @PathVariable Integer dias) throws ServletRequestBindingException {
        LOG.info("INIT RESPONSE from: /CrearPlan");
        CrearPlanRs res = new CrearPlanRs();
        try {
            List<Planes> listPlanesActuales = planesPorEmpresaService.createPlan(nombrePlan,dias);
            res.setDescription("plan guardado");
            res.setCodigoRespuesta(200);
            res.setListPlanesActuales(listPlanesActuales);
        } catch (ErrorGeneral e ) {
            res.setCodigoRespuesta(e.getStatusCode());
            res.setDescription(e.getMessage());
        }
        LOG.info("RESPONSE from: /CrearPlan, codigo: " + res.getCodigoRespuesta());
        return res;
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getlistCodeByPlan/{documentoEmpresa}", method = RequestMethod.GET)
    public CantidadCodigosPorPlanRs getlistCodeByPlan(@Context HttpServletRequest rq,
                                  @PathVariable String documentoEmpresa) throws ServletRequestBindingException {
        LOG.info("INIT RESPONSE from: /getlistCodeByPlan");
        CantidadCodigosPorPlanRs res = new CantidadCodigosPorPlanRs();
        try {
            List<CantidadCodigosPorPlan> listCantidadDeCodigosPorPlan = planesPorEmpresaService.getlistCodeByPlan(documentoEmpresa);
            res.setDescription("Cantidad de codigos resueltos");
            res.setCodigoRespuesta(200);
            res.setListCantidadDeCodigosPorPlan(listCantidadDeCodigosPorPlan);
        } catch (ErrorGeneral e ) {
            res.setCodigoRespuesta(e.getStatusCode());
            res.setDescription(e.getMessage());
        }
        LOG.info("RESPONSE from: /getlistCodeByPlan, codigo: " + res.getCodigoRespuesta());
        return res;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/asignarPlanAEmpresa/{idPlan}/{IdEmpresa}", method = RequestMethod.GET)
    public AsignarPlanAEmpresaRs asignarPlanAEmpresa(@Context HttpServletRequest rq,
                                  @PathVariable Integer idPlan,
                                  @PathVariable Integer IdEmpresa) throws ServletRequestBindingException {
        LOG.info("INIT RESPONSE from: /asignarPlanAEmpresa");
        AsignarPlanAEmpresaRs res = new AsignarPlanAEmpresaRs();
        try {
            List<PlanesEmpresa> listPlanesPorEmpresa = asignarPlanAEmpresaService.asignarPlanAEmpresa(idPlan,IdEmpresa);
            res.setDescription("Asignacion Exitosa");
            res.setCodigoRespuesta(200);
            res.setListPlanesPorEmpresaActualizado(listPlanesPorEmpresa);
        } catch (ErrorGeneral e ) {
            res.setCodigoRespuesta(e.getStatusCode());
            res.setDescription(e.getMessage());
        }
        LOG.info("RESPONSE from: /asignarPlanAEmpresa, codigo: " + res.getCodigoRespuesta());
        return res;
    }

    /**
     * Metodo que redirecciona a la vista de listar empresas de convenio para la parte de administracion de convenios
     * 
     * @param model
     * @return String
     */
    @RequestMapping(value = { "/listar_convenio_empresas" }, method = RequestMethod.GET)
    public String listarConvenioEmpresasRedireccion(ModelMap model) {

        return "listar_empresas_convenio";
    }

    @RequestMapping(value = { "/vista_empresa" }, method = RequestMethod.GET)
    public String vistaEmpresaRedireccion(ModelMap model) {

        return "vista_empresa";
    }

    /**
     * Metodo que redirecciona a la vista de otro
     *
     * @param model
     * @return String
     */
    @RequestMapping(value = { "/otro" }, method = RequestMethod.GET)
    public String otroRedireccion(ModelMap model) {
        return "listar_empleados_por_empresa";
    }



    /**
     * Metodo que redirecciona a la vista de Validar documentos para la parte de administracion de convenios
     * 
     * @param model
     * @return String
     */
    
    @RequestMapping(value = { "/validar_documentos" }, method = RequestMethod.GET)
    public String validarDocumentosRedireccion(ModelMap model) {
        return "validar_documentos";
    }

    /**
     * Metodo que permite obtener todas las empresas de convenio registradas (EmpresaEmpleador)
     *
     * @param rq
     * @Context HttpServletRequest que indica los datos de solicitud y el contexto del cual se realiza
     * @param usuario
     * @RequestHeader String cabezera para seguridad
     * @param token
     * @RequestHeader String cabezera para seguridad
     * @return ObtenerConvenioEmpresasRs respuesta con las empresas que se han creado
     * @throws ServletRequestBindingException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/obtenerConvenioEmpresas", method = RequestMethod.GET)
    public ObtenerConvenioEmpresasRs obtenerConvenioEmpresas(@Context HttpServletRequest rq, @RequestHeader(value = "user") String usuario,
            @RequestHeader(value = "sessionToken") String token) throws ServletRequestBindingException {
        ObtenerConvenioEmpresasRs res = new ObtenerConvenioEmpresasRs();
        try {
            LOG.info("REQUEST to: /obtenerConvenioEmpresas, metodo GET");
            // creamos el request
            ObtenerConvenioEmpresasRq request = new ObtenerConvenioEmpresasRq();
            // datos basicos
            request.setRqIpOrigen(rq.getRemoteAddr());
            request.setRqUsuario(rq.getRemoteUser());
            request.setRqFecha(new Date());
            request.setSessionId(rq.getRequestedSessionId());
            // llamamos el service
            res = convenioAdminService.obtenerConvenioEmpresas(request);
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición obtenerConvenioEmpresas." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /obtenerConvenioEmpresas, codigo: " + res.getCodigoRespuesta());
        return res;
    }

    /**
     * Metodo que permite la redirección a la vista de listar afiliados de empresa
     *
     * @param model
     * @param empresaTipo
     * @param empresaId
     * @return String que indica la vista de redirección
     */
    @RequestMapping(value = { "/listar_convenio_afiliado/&{empresaTipo}&{empresaId}&{membresiaId}" }, method = RequestMethod.GET)
    public String listarConvenioAfiliadosRedireccion(ModelMap model, @PathVariable String empresaTipo, @PathVariable String empresaId) {
        return "listar_empleados_por_empresa_convenio";
    }

    /**
     * Metodo que permite obtener los afiliados pertenecientes a una empresa (empleador)
     *
     * @param empresaTipo
     * @PathVariable String que indica el tipo de documento d ela empresa
     * @param empresaId
     * @PathVariable String que indica el numero de identificación de la empresa
     * @param request
     * @Context HttpServletRequest request con el contexto de la aplicación
     * @param usuario
     * @RequestHeader String cabezera para seguridad
     * @param token
     * @RequestHeader String cabezera para seguridad
     * @return ObtenerConvenioAfiliadosRs response con los datos de afiliados:
     *         <li><i>empresa:</i> modelo con los datos de la empresa cerada</li>
     *         <li><i>empleados:</i> lista de modelos de empleados, cada uno con sus codigos asignados</li>
     *         <li><i>csvEmpleados:</i> cadena de texto que indica el csv de empleados</li>
     * @throws ServletRequestBindingException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/obtenerConvenioAfiliados/{empresaTipo}/{empresaId}/{membresiaId}", method = RequestMethod.GET)
    public ObtenerConvenioAfiliadosRs obtenerConvenioAfiliados(@PathVariable String empresaTipo, @PathVariable String empresaId, @PathVariable int membresiaId,
            @Context HttpServletRequest request, @RequestHeader(value = "user") String usuario,
            @RequestHeader(value = "sessionToken") String token) throws ServletRequestBindingException {
        LOG.info("REQUEST to: /obtenerConvenioAfiliados/" + empresaTipo + "/" + empresaId);
        ObtenerConvenioAfiliadosRs res = new ObtenerConvenioAfiliadosRs();
        try {
            ObtenerConvenioAfiliadosRq rq = new ObtenerConvenioAfiliadosRq();
            rq.setTipoDocumentoEmpresa(empresaTipo);
            rq.setNumeroDocumentoEmpresa(empresaId);
            rq.setMembresia(membresiaId);
            res = convenioAdminService.obtenerConvenioAfiliados(rq);
            
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición obtenerConvenioEmpresas." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /obtenerConvenioAfiliados/" + empresaTipo + "/" + empresaId + ", codigo: " + res.getCodigoRespuesta());
        return res;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/obtenerVistaEmpresa", method = RequestMethod.GET)
    public ObtenerConvenioAfiliadosRs obtenerConvenioAfiliados(@Context HttpServletRequest request, @RequestHeader(value = "user") String usuario,
                                                               @RequestHeader(value = "sessionToken") String token) throws ServletRequestBindingException {
        LOG.info("REQUEST to: /obtenerVistaEmpresa to " + usuario );
        ObtenerConvenioAfiliadosRs res = new ObtenerConvenioAfiliadosRs();
        try {
            res = empresaService.vistaEmpresa(usuario);
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición obtenerConvenioEmpresas." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /obtenerVistaEmpresa to " + usuario + ", codigo: " + res.getCodigoRespuesta());
        return res;
    }
    /**
     * Metodo que permite obtener los afiliados pertenecientes a una empresa dando un rango de fechas a cada afiliado se le asignan los
     * repectivos codigos asignados
     * 
     * @param fechaInicio
     *            String @PathVariable que indica la fecha inicial en formato yyyy-MM-dd
     * @param fechaFin
     *            String @PathVariable que indica la fecha final en formato yyyy-MM-dd
     * @param empresaTipo
     *            String @PathVariable tipo de identificación de la empresa
     * @param empresaId
     *            String @PathVariable numero de identifación de la empresa
     * @param request
     *            HttpServletRequest @Context indica el http context del request
     * @param usuario
     *            String @RequestHeader user
     * @param token
     *            String @RequestHeader sessionToken
     * @return ObtenerConvenioAfiliadosRs respuesta con los afiliados y la empresa consultados
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/obtenerConvenioAfiliados/{empresaTipo}/{empresaId}/{membresiaId}/{fechaInicio}/{fechaFin}", method = RequestMethod.GET)
    public ObtenerConvenioAfiliadosRs obtenerConvenioAfiliadosPorFechas(@PathVariable String fechaInicio, @PathVariable String fechaFin,
            @PathVariable String empresaTipo, @PathVariable String empresaId, @PathVariable int membresiaId, @Context HttpServletRequest request,
            @RequestHeader(value = "user") String usuario, @RequestHeader(value = "sessionToken") String token)
            throws ServletRequestBindingException {
        LOG.info("request to: /obtenerConvenioAfiliados/" + empresaTipo + "/" + empresaId + "/" + membresiaId + "/" + fechaInicio + "/" + fechaFin);
        ObtenerConvenioAfiliadosRs res = new ObtenerConvenioAfiliadosRs();
        try {
            ObtenerConvenioAfiliadosRq rq = new ObtenerConvenioAfiliadosRq();
            rq.setRqIpOrigen(request.getRemoteAddr());
            rq.setRqUsuario(request.getRemoteUser());
            rq.setRqFecha(new Date());
            rq.setSessionId(request.getRequestedSessionId());
            // transformamos las fechas de ingreso
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateInicial = format.parse(fechaInicio);
            Date dateFinal = format.parse(fechaFin);
            rq.setFechaInicial(dateInicial);
            rq.setFechaFinal(dateFinal);
            rq.setTipoDocumentoEmpresa(empresaTipo);
            rq.setNumeroDocumentoEmpresa(empresaId);
            rq.setMembresia(membresiaId);
            res = convenioAdminService.obtenerConvenioAfiliados(rq);
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición obtenerConvenioAfiliados." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /obtenerConvenioAfiliados/" + empresaTipo + "/" + empresaId + "/" + membresiaId + "/" + fechaInicio + "/" + fechaFin + ", codigo: " + res.getCodigoRespuesta());
        return res;
    }

    /**
     * Metodo que permite obtener las membresias
     *
     * @param {rq}
     * @return
     * @throws ServletRequestBindingException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/obtenerMembresias", method = RequestMethod.GET)
    public ObtenerMembresiasRs obtenerMembresias(@Context HttpServletRequest rq) throws ServletRequestBindingException {
        // inicializamos el response
        LOG.info("INIT RESPONSE from: /obtenerMembresias");
        ObtenerMembresiasRs res = new ObtenerMembresiasRs();
        try {
            List<MembresiaModel> membresias = convenioAdminService.obtenerMembresias();
            res.setMembresias(membresias);
            res.setCodigoRespuesta(CONSUMO_SUCESS);
        } catch (Exception e) {
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError("ERROR-GEN");
        }
        LOG.info("RESPONSE from: /obtenerMembresias, codigo: " + res.getCodigoRespuesta());
        return res;
    }

    /**
     * Metodo que permite añadir afiliados al empleador, asignando codigos de descuentos por medio de CSV.
     *
     * @param {rq}
     *            HttpServletRequest que indica los datos del request, puede obtenerse los parametros ingresados
     * @param {map}
     *            ModelMap ModelMap que perite mapear el modelo (respuesta) final para procesar luego en la respuesta
     * @return {String} ruta de redirección a respuesta
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "/listar_convenio_afiliado/procesarCsvConvenioAfiliados", method = RequestMethod.POST)
    public String procesarCsvConvenioAfiliados(HttpServletRequest rq, ModelMap map) throws ServletRequestBindingException {
        // inicializamos el response
        ProcesarCsvConvenioAfiliadosRs res = new ProcesarCsvConvenioAfiliadosRs();
        try {
            LOG.info("REQUEST to: /procesarCsvConvenioAfiliados, metodo POST");
            // obtenemos los datos enviados desde el sitio
            int empresaTipoDoc = ServletRequestUtils.getIntParameter(rq, "empresaTipoDoc");
            String empresaNumDoc = ServletRequestUtils.getStringParameter(rq, "empresaNumDoc");
            int empresaMembr = ServletRequestUtils.getIntParameter(rq, "empresaMembr");
            String afiliadosCsv = ServletRequestUtils.getStringParameter(rq, "afiliadosCsv");
            if (null != afiliadosCsv && afiliadosCsv.contains("base64,")) {
                afiliadosCsv = afiliadosCsv.substring(afiliadosCsv.indexOf("base64,") + "base64,".length());
            }
            // creamos el request hacia el bean
            // datos basicos
            ProcesarCsvConvenioAfiliadosRq request = new ProcesarCsvConvenioAfiliadosRq();
            request.setRqIpOrigen(rq.getRemoteAddr());
            request.setRqUsuario(rq.getRemoteUser());
            request.setRqFecha(new Date());
            request.setSessionId(rq.getRequestedSessionId());
            // datos de negocio
            request.setCsvAfiliados(afiliadosCsv);
            EmpresaEmpleadorModel empresa = new EmpresaEmpleadorModel();
            empresa.setDocumentoTipo(empresaTipoDoc);
            empresa.setDocumentoNumero(empresaNumDoc);
            empresa.setMembresia(empresaMembr);
            request.setEmpresa(empresa);
            // llamamos el service
            res = convenioAdminService.procesarCsvConvenioAfiliados(request);
            // mapeamos datos de respuesta
            map.addAttribute("accion", "AfiliadoImport");
            map.addAttribute("codigoRespuesta", res.getCodigoRespuesta());
            map.addAttribute("codigoError", res.getCodigoError());
            map.addAttribute("csvAfiliadosFallidos", res.getCsvAfiliadosFallidos());
            map.addAttribute("csvAfiliadosCompletos", res.getCsvAfiliadosCompletos());
            //
            map.addAttribute("afiliadosAsignados", res.getAfiliadosAsignados());
            map.addAttribute("codigosAsignados", res.getCodigosAsignados());
            map.addAttribute("afiliadosError", res.getAfiliadosError());
            map.addAttribute("mensajesError", res.getMensajesError());
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición asignarConvenioAfiliados." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /procesarCsvConvenioAfiliados, codigo: " + res.getCodigoRespuesta());
        return "mensaje_convenio";
    }
    
    /**
     * Metodo que permite añadir codigos de descuento al empleador por medio de CSV
     *
     * @param rq
     *            HttpServletRequest que indica los datos del request, puede obtenerse los parametros ingresados
     * @param map
     *            ModelMap ModelMap que perite mapear el modelo (respuesta) final para procesar luego en la respuesta
     * @return String ruta de redirección a respuesta
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "/listar_convenio_afiliado/procesarCsvConvenioCodigosDescuento", method = RequestMethod.POST)
    public String procesarCsvConvenioCodigosDescuento(HttpServletRequest rq, ModelMap map) throws ServletRequestBindingException {
        // inicializamos el response
    	ProcesarCsvConvenioCodigosRs res = new ProcesarCsvConvenioCodigosRs();
        try {
            LOG.info("REQUEST to: /procesarCsvConvenioCodigosDescuento, metodo POST");
            // obtenemos los datos enviados desde el sitio
            int empresaTipoDoc = ServletRequestUtils.getIntParameter(rq, "empresaTipoDoc");
            String empresaNumDoc = ServletRequestUtils.getStringParameter(rq, "empresaNumDoc");
            int empresaMembr = ServletRequestUtils.getIntParameter(rq, "empresaMembr");
            String codigosCsv = ServletRequestUtils.getStringParameter(rq, "codigosCsv");
            if (null != codigosCsv && codigosCsv.contains("base64,")) {
                codigosCsv = codigosCsv.substring(codigosCsv.indexOf("base64,") + "base64,".length());
            }
            
            // creamos el request hacia el bean
            // datos basicos
            ProcesarCsvConvenioCodigosRq request = new ProcesarCsvConvenioCodigosRq();
            request.setRqIpOrigen(rq.getRemoteAddr());
            request.setRqUsuario(rq.getRemoteUser());
            request.setRqFecha(new Date());
            request.setSessionId(rq.getRequestedSessionId());
            // datos de negocio
            request.setCsvCodigos(codigosCsv);
            EmpresaEmpleadorModel empresa = new EmpresaEmpleadorModel();
            empresa.setDocumentoTipo(empresaTipoDoc);
            empresa.setDocumentoNumero(empresaNumDoc);
            empresa.setMembresia(empresaMembr);
            request.setEmpresa(empresa);
            // llamamos el service
            res = convenioAdminService.crearCodigosCsv(request);
            // mapeamos datos de respuesta
            map.addAttribute("accion", "CodigoDescuentoImport");
            map.addAttribute("codigoRespuesta", res.getCodigoRespuesta());
            map.addAttribute("codigoError", res.getCodigoError());
            map.addAttribute("csvCodigosFallidos", res.getCsvCodigosFallidos());
            map.addAttribute("csvCodigosCompletos", res.getCsvCodigosCompletos());
            //
            map.addAttribute("afiliadosError", res.getCodigosError());
            map.addAttribute("mensajesError", res.getMensajesError());
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición procesarCsvConvenioCodigosDescuento." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /procesarCsvConvenioCodigosDescuento, codigo: " + res.getCodigoRespuesta());
        return "mensaje_codigos_import";
    }

    /**
     * Metodo que permite obtener un CSV con los codigos asignados a los empleados de todas las empresas, filtrado con un rango de fecha
     *
     * @param fechaInicio
     * @param fechaFin
     * @param request
     * @param usuario
     * @param token
     * @return
     * @throws ServletRequestBindingException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/obtenerCodigosAsignados/{fechaInicio}/{fechaFin}", method = RequestMethod.GET)
    public ObtenerCodigosAsignadosRs obtenerCodigosAsignados(@PathVariable String fechaInicio, @PathVariable String fechaFin,
            @Context HttpServletRequest request, @RequestHeader(value = "user") String usuario,
            @RequestHeader(value = "sessionToken") String token) throws ServletRequestBindingException {
        LOG.info("REQUEST to: /obtenerCodigosAsignados/" + fechaInicio + "/" + fechaFin);
        ObtenerCodigosAsignadosRs res = new ObtenerCodigosAsignadosRs();
        try {
            // datos basicos
            ObtenerCodigosAsignadosRq rq = new ObtenerCodigosAsignadosRq();
            rq.setRqIpOrigen(request.getRemoteAddr());
            rq.setRqUsuario(request.getRemoteUser());
            rq.setRqFecha(new Date());
            rq.setSessionId(request.getRequestedSessionId());

            // transformamos las fechas de ingreso
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateInicial = format.parse(fechaInicio);
            Date dateFinal = format.parse(fechaFin);
            rq.setFechaInicial(dateInicial);
            rq.setFechaFinal(dateFinal);
            res = convenioAdminService.obtenerCodigosAsignados(rq);
        } catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la petición obtenerCodigosAsignados." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            res.setCodigoRespuesta(CONSUMO_ERROR);
            res.setCodigoError(ERROR_GENERICO);
            res.setFechaRespuesta(new Date());
        }
        LOG.info("RESPONSE from: /obtenerCodigosAsignados/" + fechaInicio + "/" + fechaFin);
        return res;
    }

    @RequestMapping(value = "/Admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        LOG.info("request to: /Admin");
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is protected page!");
        model.setViewName("admin");
        return model;
    }

    @RequestMapping(value = "/Admin", method = RequestMethod.GET)
    public ModelAndView index() {
        LOG.info("request to: /Admin");
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        LOG.info("request to: /login");
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Usuario y contraseña invalidos!");
        }

        if (logout != null) {
            model.addObject("msg", "Has cerrado sesión correctamente.");
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {
    	LOG.info("request to: /403");
        ModelAndView model = new ModelAndView();
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (!(auth instanceof AnonymousAuthenticationToken)) {
	            UserDetails userDetail = (UserDetails) auth.getPrincipal();
	            model.addObject("username", userDetail.getUsername());
	        }
	        model.setViewName("403");
	        return model;
    	} catch (Exception ex) {
    		model.setViewName("403");
    	}
    	return model;
    }
    
    /**
     * Método que permite visualizar imagenes
     */
    @RequestMapping(value = { "/upload/image/{name}/" }, method = RequestMethod.GET)
    public void findImageByName(@PathVariable String name, HttpServletResponse response) throws IOException  {
        LOG.info("request to: //upload/image/" + name);
        
        String pathImage = Util.getStaticImagesDir() + name;
        InputStream in = new BufferedInputStream(new FileInputStream( pathImage ));
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());

    }

    /**
     * Método que permite eliminar un convenio de un afiliado
     */
    @RequestMapping(value = "/listar_convenio_afiliado/eliminarAfiliado", method = RequestMethod.POST)
    public String eliminarConvenioAfiliadoPorId(HttpServletRequest request, ModelMap map) throws ServletRequestBindingException {
    	LOG.info("request to: /listar_convenio_afiliado/eliminarAfiliado");
    	int afiliadoId = 0;
    	String codigoAsignado = null;
    	
    	try {
    		afiliadoId = ServletRequestUtils.getIntParameter(request, "afiliadoId");
    		codigoAsignado = ServletRequestUtils.getStringParameter(request, "codigoAsignado");
    		
	    	boolean isDelCode = this.convenioAdminService.eliminarCodigoAsignado(afiliadoId, codigoAsignado);
	    	if( !isDelCode )
	    		throw new Exception();
	    	
	    	// mapeamos datos de respuesta
	        map.addAttribute("mensaje", "El código "+codigoAsignado+" fué eliminado exitosamente");
	        
    	} catch (Exception e) {
            String errorMsg = "Error en Controlador, al realizar la eliminacion de un EmpresaAfiliadoXCodDes." + "\n";
            LOG.error(errorMsg + e.toString(), e);
            map.addAttribute("mensaje", "Error al intentar eliminar el EmpresaAfiliadoXCodDes");
        }
        LOG.info("RESPONSE from: /listar_convenio_afiliado/eliminarAfiliado, afiliadoId: " + afiliadoId);
    	
    	return "mensaje_general";
    }
}

