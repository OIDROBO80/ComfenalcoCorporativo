package co.com.smartfit.web.business.rs;

import java.util.List;

import co.com.smartfit.web.model.MembresiaModel;

/**
 * @author alejandro.areiza
 * @since 20/09/2017
 * @version 1.0
 */
public class ObtenerMembresiasRs {
	// codigo de respuesta que puede indicar el estado de la invocación (200 o 500)
	private String codigoRespuesta;
	// codigo de error que puede indicar el tipo de error adquirido debido a una excepcion
	private String codigoError;
	private List<MembresiaModel> membresias;
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
	 * Metodo que obtiene el valor asociado a la propiedad membresias
	 * @return List<MembresiaModel> el valor asociado a la propiedad membresias
	 */
	public List<MembresiaModel> getMembresias() {
		return membresias;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad membresias
	 * @param {List<MembresiaModel>} el nuevo valor para la propiedad membresias
	 */
	public void setMembresias(List<MembresiaModel> tarifas) {
		this.membresias = tarifas;
	}
}
