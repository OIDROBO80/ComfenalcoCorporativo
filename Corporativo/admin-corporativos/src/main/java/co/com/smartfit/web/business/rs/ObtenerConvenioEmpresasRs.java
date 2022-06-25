package co.com.smartfit.web.business.rs;

import java.util.Date;
import java.util.List;

import co.com.smartfit.web.model.EmpresaEmpleadorModel;

/**
 * @author alejandro.areiza
 * @since 23/05/2017
 * @version 1.0
 */
public class ObtenerConvenioEmpresasRs {

	// codigo de respuesta que puede indicar el estado de la invocación (200 o 500)
	private String codigoRespuesta;
	// codigo de error que puede indicar el tipo de error adquirido debido a una excepcion
	private String codigoError;
	private Date fechaRespuesta;
	// datos de negocio
	private List<EmpresaEmpleadorModel> empresas;
	private String csvEmpresas;
	
	/**
	 * Metodo que obtiene el valor asociado a la propiedad csvEmpresas
	 * @return {String} el valor asociado a la propiedad csvEmpresas
	 */
	public String getCsvEmpresas() {
		return csvEmpresas;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad csvEmpresas
	 * @param {String} el nuevo valor para la propiedad csvEmpresas
	 */
	public void setCsvEmpresas(String csvEmpresas) {
		this.csvEmpresas = csvEmpresas;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigoRespuesta
	 * @return {String} el valor asociado a la propiedad codigoRespuesta
	 */
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad codigoRespuesta
	 * @param {String} el nuevo valor para la propiedad codigoRespuesta
	 */
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigoError
	 * @return String el valor asociado a la propiedad codigoError
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
	 * Metodo que obtiene el valor asociado a la propiedad empresas
	 * @return {List<EmpresaEmpleadorModel>} el valor asociado a la propiedad empresas
	 */
	public List<EmpresaEmpleadorModel> getEmpresas() {
		return empresas;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad empresas
	 * @param {List<EmpresaEmpleadorModel>} el nuevo valor para la propiedad empresas
	 */
	public void setEmpresas(List<EmpresaEmpleadorModel> empresas) {
		this.empresas = empresas;
	}
}
