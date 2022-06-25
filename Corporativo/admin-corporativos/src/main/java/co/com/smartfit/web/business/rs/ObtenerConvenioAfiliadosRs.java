package co.com.smartfit.web.business.rs;

import java.util.Date;
import java.util.List;

import co.com.smartfit.web.model.EmpresaAfiliadoModel;
import co.com.smartfit.web.model.EmpresaEmpleadorModel;

/**
 * @author alejandro.areiza
 * @since 23/05/2017
 * @version 1.0
 */
public class ObtenerConvenioAfiliadosRs {
	// codigo de respuesta que puede indicar el estado de la invocación (200 o 500)
	private String codigoRespuesta;
	// codigo de error que puede indicar el tipo de error adquirido debido a una excepcion
	private String codigoError;
	private Date fechaRespuesta;
	// datos de negocio
	private EmpresaEmpleadorModel empresa;
	private List<EmpresaAfiliadoModel> empleados;
	private String csvEmpleados;
	private int numeroCodigosDisponibles;
	
	
	/**
	 * Metodo que obtiene el valor asociado a la propiedad csvEmpleados
	 * @return String el valor asociado a la propiedad csvEmpleados
	 */
	public String getCsvEmpleados() {
		return csvEmpleados;
	}

	/**
	 * Metodo que permite modificar el valor de la propiedad csvEmpleados
	 * @param {String} el nuevo valor para la propiedad csvEmpleados
	 */
	public void setCsvEmpleados(String csvEmpleados) {
		this.csvEmpleados = csvEmpleados;
	}

	/**
	 * Metodo que obtiene el valor asociado a la propiedad empresa
	 * @return {EmpresaEmpleadorModel} el valor asociado a la propiedad empresa
	 */
	public EmpresaEmpleadorModel getEmpresa() {
		return empresa;
	}

	/**
	 * Metodo que permite modificar el valor de la propiedad empresa
	 * @param {EmpresaEmpleadorModel} el nuevo valor para la propiedad empresa
	 */
	public void setEmpresa(EmpresaEmpleadorModel empresa) {
		this.empresa = empresa;
	}

	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigoError
	 * @return {String} el valor asociado a la propiedad codigoError
	 */
	public String getCodigoError() {
		return codigoError;
	}

	/**
	 * Metodo que permite modificar el valor de la propiedad codigoError
	 * @param {String} el nuevo valor para la propiedad codigoError
	 */
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * Metodo que obtiene el valor asociado a la propiedad fechaRespuesta
	 * @return Date el valor asociado a la propiedad fechaRespuesta
	 */
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	/**
	 * Metodo que permite modificar el valor de la propiedad fechaRespuesta
	 * @param {Date} el nuevo valor para la propiedad fechaRespuesta
	 */
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigoRespuesta
	 * 
	 * @return String el valor asociado a la propiedad codigoRespuesta
	 */
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	/**
	 * Metodo que permite modificar el valor de la propiedad codigoRespuesta
	 * 
	 * @param {String} 	 *            el nuevo valor para la propiedad codigoRespuesta
	 */
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * Metodo que obtiene el valor asociado a la propiedad empleados
	 * 
	 * @return List<EmpresaAfiliadoModel> el valor asociado a la propiedad empleados
	 */
	public List<EmpresaAfiliadoModel> getEmpleados() {
		return empleados;
	}

	/**
	 * Metodo que permite modificar el valor de la propiedad empleados
	 * 
	 * @param {List<EmpresaAfiliadoModel>}
	 *            el nuevo valor para la propiedad empleados
	 */
	public void setEmpleados(List<EmpresaAfiliadoModel> empleados) {
		this.empleados = empleados;
	}

	public int getNumeroCodigosDisponibles() {
		return numeroCodigosDisponibles;
	}

	public void setNumeroCodigosDisponibles(int numeroCodigosDisponibles) {
		this.numeroCodigosDisponibles = numeroCodigosDisponibles;
	}
	

}
