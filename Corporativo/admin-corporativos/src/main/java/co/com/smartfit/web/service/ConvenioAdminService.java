package co.com.smartfit.web.service;

import java.util.List;

import co.com.smartfit.web.business.rq.CrearConvenioEmpresaRq;
import co.com.smartfit.web.business.rq.ObtenerCodigosAsignadosRq;
import co.com.smartfit.web.business.rq.ObtenerConvenioAfiliadosRq;
import co.com.smartfit.web.business.rq.ObtenerConvenioEmpresasRq;
import co.com.smartfit.web.business.rq.ProcesarCsvConvenioAfiliadosRq;
import co.com.smartfit.web.business.rq.ProcesarCsvConvenioCodigosRq;
import co.com.smartfit.web.business.rs.*;
import co.com.smartfit.web.entities.PlanesEmpresa;
import co.com.smartfit.web.model.MembresiaModel;

/**
 * Service de Spring (Bean) encargado de realizar las operaciones de Administracion de Convenios para Empresa
 * 
 * @author alejandro.areiza
 * @since 15/09/2017
 * @version 1.0
 */
public interface ConvenioAdminService {

    /**
     * Metodo que permite crear una empresa empleador en base de datos registrar sus afiliados (empleados) y asignar los codigos
     * correspondientes
     * 
     * @param request
     *            petición de la solicitud, que indica los datos de la empresa
     * @return CrearConvenioEmpresaRs: respuesta de la solicitud, genera los datos del registro
     */
    CrearConvenioEmpresaRs crearConvenioEmpresa(CrearConvenioEmpresaRq request);

    /**
     * Metodo que permite obtener el tototal de empresas registradas
     * 
     * @param request
     *            generico con la información de la solicitud
     * @return ObtenerConvenioEmpresasRs lista de empresas obtenidas
     */
    ObtenerConvenioEmpresasRs obtenerConvenioEmpresas(ObtenerConvenioEmpresasRq request);

    /**
     * Metodo que permite obtener los afiliados de determinada empresa
     * 
     * @param request
     *            que indica los afiliados de que empresa busca, si fechaInicial y fechaFinal != null busca los registrados en este rango de
     *            fechas
     * @return ObtenerConvenioAfiliadosRs
     */
    ObtenerConvenioAfiliadosRs obtenerConvenioAfiliados(ObtenerConvenioAfiliadosRq request);

    /**
     * Metodo que permite procesar y registrar afiliados de empresa a partir de un csv
     * 
     * @param request
     *            que el csv a procesar, la empresa a la que pertenecen los afiliados y el tipo de descuento a asignar
     * @return ProcesarCsvConvenioAfiliadosRs
     */
    ProcesarCsvConvenioAfiliadosRs procesarCsvConvenioAfiliados(ProcesarCsvConvenioAfiliadosRq request);

    /**
     * Metodo que permite obtener los codigos asignados
     * 
     * @param
     * @return ObtenerCodigosAsignadosRs
     */
    ObtenerCodigosAsignadosRs obtenerCodigosAsignados(ObtenerCodigosAsignadosRq rq);

    InformationInitialToCreateCompanyRs getInitialInformationToCreateCorporative();
    /*CrearPlanRs createPlan(String nombrePlan,Integer dias);*/
    /**
     * Metodo que permite obtener las membresias en BD
     *
     * @return List<MembresiaModel> lista de membresias
     */
    List<MembresiaModel> obtenerMembresias();
    
    /**
     * Método que crea codigos de descuentos a un empleador, por medio de CSV
     */
    ProcesarCsvConvenioCodigosRs crearCodigosCsv(ProcesarCsvConvenioCodigosRq request);

    /**
	 * Método que permite eliminar un afiliadoXcodDesc por ID y codigo
	 * @return boolean El status de la transacción
	 */
	boolean eliminarCodigoAsignado(int afiliadoId, String codigoAsignado);
}
