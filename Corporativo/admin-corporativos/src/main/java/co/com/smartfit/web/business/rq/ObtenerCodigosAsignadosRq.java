package co.com.smartfit.web.business.rq;

import java.util.Date;

/**
 * @author alejandro.areiza
 * @since 15/09/2017
 * @version 1.0
 */
public class ObtenerCodigosAsignadosRq {
	// datos genericos
	private String rqUsuario;
	private String rqIpOrigen;
	private String sessionId;
	private Date rqFecha;
	// datos del negocio	
	private Date fechaInicial;
	private Date fechaFinal;
		
	/**
	 * Metodo que obtiene el valor asociado a la propiedad fechaInicial
	 * @return Date el valor asociado a la propiedad fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad fechaInicial
	 * @param {Date} el nuevo valor para la propiedad fechaInicial
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad fechaFinal
	 * @return Date el valor asociado a la propiedad fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad fechaFinal
	 * @param {Date} el nuevo valor para la propiedad fechaFinal
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad rqUsuario
	 * @return String el valor asociado a la propiedad rqUsuario
	 */
	public String getRqUsuario() {
		return rqUsuario;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad rqUsuario
	 * @param {String} el nuevo valor para la propiedad rqUsuario
	 */
	public void setRqUsuario(String rqUsuario) {
		this.rqUsuario = rqUsuario;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad rqIpOrigen
	 * @return String el valor asociado a la propiedad rqIpOrigen
	 */
	public String getRqIpOrigen() {
		return rqIpOrigen;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad rqIpOrigen
	 * @param {String} el nuevo valor para la propiedad rqIpOrigen
	 */
	public void setRqIpOrigen(String rqIpOrigen) {
		this.rqIpOrigen = rqIpOrigen;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad sessionId
	 * @return String el valor asociado a la propiedad sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad sessionId
	 * @param {String} el nuevo valor para la propiedad sessionId
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad rqFecha
	 * @return Date el valor asociado a la propiedad rqFecha
	 */
	public Date getRqFecha() {
		return rqFecha;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad rqFecha
	 * @param {Date} el nuevo valor para la propiedad rqFecha
	 */
	public void setRqFecha(Date rqFecha) {
		this.rqFecha = rqFecha;
	}
}
