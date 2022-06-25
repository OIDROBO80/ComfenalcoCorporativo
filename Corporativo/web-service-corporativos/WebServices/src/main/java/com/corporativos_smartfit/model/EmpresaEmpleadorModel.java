package com.corporativos_smartfit.model;

import java.util.Date;

/**
 * @author alejandro.areiza
 * @since 15/09/2017
 * @version 1.0
 */
public class EmpresaEmpleadorModel {

	private Integer idEmpresa;
	private int membresia;
	private String membresiaNombre;
	private int documentoTipo;
	private String documentoTipoNombre;
	private String documentoNumero;	
	private String representanteLegal;
	private String razonSocial;
	private String email;
	private String telefono;
	private boolean activo;
	private Date fechaInicioConvenio;
	private String logo;
	private String textoEmail;
	
	/**
	 * Metodo que obtiene el valor asociado a la propiedad idEmpresa
	 * @return Integer el valor asociado a la propiedad idEmpresa
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad idEmpresa
	 * @param {Integer} el nuevo valor para la propiedad idEmpresa
	 */
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad documentoTipo
	 * @return String el valor asociado a la propiedad documentoTipo
	 */
	public int getDocumentoTipo() {
		return documentoTipo;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad documentoTipo
	 * @param {String} el nuevo valor para la propiedad documentoTipo
	 */
	public void setDocumentoTipo(int documentoTipo) {
		this.documentoTipo = documentoTipo;
	}
	public String getDocumentoTipoNombre() {
		return documentoTipoNombre;
	}
	public void setDocumentoTipoNombre(String documentoTipoNombre) {
		this.documentoTipoNombre = documentoTipoNombre;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad documentoNumero
	 * @return String el valor asociado a la propiedad documentoNumero
	 */
	public String getDocumentoNumero() {
		return documentoNumero;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad documentoNumero
	 * @param {String} el nuevo valor para la propiedad documentoNumero
	 */
	public void setDocumentoNumero(String documentoNumero) {
		this.documentoNumero = documentoNumero;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad representanteLegal
	 * @return String el valor asociado a la propiedad representanteLegal
	 */
	public String getRepresentanteLegal() {
		return representanteLegal;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad representanteLegal
	 * @param {String} el nuevo valor para la propiedad representanteLegal
	 */
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad razonSocial
	 * @return String el valor asociado a la propiedad razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad razonSocial
	 * @param {String} el nuevo valor para la propiedad razonSocial
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad email
	 * @return String el valor asociado a la propiedad email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad email
	 * @param {String} el nuevo valor para la propiedad email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad telefono
	 * @return String el valor asociado a la propiedad telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad telefono
	 * @param {String} el nuevo valor para la propiedad telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	
	/**
	 * Metodo que obtiene el valor asociado a la propiedad activo
	 * @return boolean el valor asociado a la propiedad activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad activo
	 * @param {boolean} el nuevo valor para la propiedad activo
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad fechaInicioConvenio
	 * @return Date el valor asociado a la propiedad fechaInicioConvenio
	 */
	public Date getFechaInicioConvenio() {
		return fechaInicioConvenio;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad fechaInicioConvenio
	 * @param {Date} el nuevo valor para la propiedad fechaInicioConvenio
	 */
	public void setFechaInicioConvenio(Date fechaInicioConvenio) {
		this.fechaInicioConvenio = fechaInicioConvenio;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad membresia
	 * @return String el valor asociado a la propiedad membresia
	 */
	public int getMembresia() {
		return membresia;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad membresia
	 * @param {String} el nuevo valor para la propiedad membresia
	 */
	public void setMembresia(int membresia) {
		this.membresia = membresia;
	}
	
	public String getMembresiaNombre() {
		return membresiaNombre;
	}
	public void setMembresiaNombre(String membresiaNombre) {
		this.membresiaNombre = membresiaNombre;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTextoEmail() {
		return textoEmail;
	}
	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}
	
}
