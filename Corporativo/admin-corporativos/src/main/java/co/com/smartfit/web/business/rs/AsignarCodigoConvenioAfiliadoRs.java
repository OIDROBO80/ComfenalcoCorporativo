package co.com.smartfit.web.business.rs;

import java.util.Date;

import co.com.smartfit.web.model.CodigoDescuentoModel;
import co.com.smartfit.web.model.EmpresaAfiliadoModel;

/**
 * Respuesta de la operación asociada
 * 
 * @author alejandro.areiza
 * @since 20/09/2017
 * @version 1.0
 */
public class AsignarCodigoConvenioAfiliadoRs {
	// codigo de respuesta que puede indicar el estado de la invocación (200 o 500), 
	// 700 en adelante para propios errores (700 error de negocio)
	private String codigoRespuesta;
	// codigo de error que puede indicar el tipo de error adquirido debido a una excepcion (o un error de negocio)
	private String codigoError;
	private String mensajeError;
	private Date fechaRespuesta;
	// datos de negocio
	private EmpresaAfiliadoModel afiliado;
	private CodigoDescuentoModel codigoDescuento;
	
	/**
	 * Metodo que obtiene el valor asociado a la propiedad mensajeError
	 * @return {String} el valor asociado a la propiedad mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad mensajeError
	 * @param {String} el nuevo valor para la propiedad mensajeError
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigoRespuesta
	 * @return String el valor asociado a la propiedad codigoRespuesta
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
	 * Metodo que obtiene el valor asociado a la propiedad afiliado
	 * @return EmpresaAfiliadoModel el valor asociado a la propiedad afiliado
	 */
	public EmpresaAfiliadoModel getAfiliado() {
		return afiliado;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad afiliado
	 * @param {EmpresaAfiliadoModel} el nuevo valor para la propiedad afiliado
	 */
	public void setAfiliado(EmpresaAfiliadoModel afiliado) {
		this.afiliado = afiliado;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigoDescuento
	 * @return CodigoDescuentoModel el valor asociado a la propiedad codigoDescuento
	 */
	public CodigoDescuentoModel getCodigoDescuento() {
		return codigoDescuento;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad codigoDescuento
	 * @param {CodigoDescuentoModel} el nuevo valor para la propiedad codigoDescuento
	 */
	public void setCodigoDescuento(CodigoDescuentoModel codigoDescuento) {
		this.codigoDescuento = codigoDescuento;
	}
}
