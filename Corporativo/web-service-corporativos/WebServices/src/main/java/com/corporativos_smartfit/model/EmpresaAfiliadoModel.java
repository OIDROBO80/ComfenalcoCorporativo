package com.corporativos_smartfit.model;

import java.util.Date;
import java.util.List;

/**
 * @author alejandro.areiza
 * @since 15/09/2017
 * @version 1.0
 */
public class EmpresaAfiliadoModel{
	private Integer id;
	private EmpresaEmpleadorModel empresaEmpleador;
	private String tipoDocumentoIdentidad;
	private String documentoNumero;
	private String nombre;
	private String email;
	private String periodicidad;
	private Date fechaRegistro;
	private List<CodigoDescuentoModel> codigosAsignados;
	
	/**
	 * Metodo que obtiene el valor asociado a la propiedad codigosAsignados
	 * @return List<String> el valor asociado a la propiedad codigosAsignados
	 */
	public List<CodigoDescuentoModel> getCodigosAsignados() {
		return codigosAsignados;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad codigosAsignados
	 * @param {List<CodigoDescuentoModel>} el nuevo valor para la propiedad codigosAsignados
	 */
	public void setCodigosAsignados(List<CodigoDescuentoModel> codigosAsignados) {
		this.codigosAsignados = codigosAsignados;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad id
	 * @return Integer el valor asociado a la propiedad id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad id
	 * @param {Integer} el nuevo valor para la propiedad id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad empresaEmpleador
	 * @return EmpresaEmpleadorModel el valor asociado a la propiedad empresaEmpleador
	 */
	public EmpresaEmpleadorModel getEmpresaEmpleador() {
		return empresaEmpleador;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad empresaEmpleador
	 * @param {EmpresaEmpleadorModel} el nuevo valor para la propiedad empresaEmpleador
	 */
	public void setEmpresaEmpleador(EmpresaEmpleadorModel empresaEmpleador) {
		this.empresaEmpleador = empresaEmpleador;
	}
	/**
	 * Metodo que obtiene el valor asociado a la propiedad tipoDocumentoIdentidad
	 * @return String el valor asociado a la propiedad tipoDocumentoIdentidad
	 */
	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad tipoDocumentoIdentidad
	 * @param {String} el nuevo valor para la propiedad tipoDocumentoIdentidad
	 */
	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
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
	 * Metodo que obtiene el valor asociado a la propiedad nombre
	 * @return String el valor asociado a la propiedad nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad nombre
	 * @param {String} el nuevo valor para la propiedad nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * Metodo que obtiene el valor de la periodicidad
	 * @return String el valor asociado a la periodicidad
	 */
	public String getPeriodicidad() { return periodicidad; }
	/**
	 * Metodo que obtiene el valor de la periodicidad
	 * @return String el valor asociado a la periodicidad
	 */
	public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }

	/**
	 * Metodo que obtiene el valor asociado a la propiedad fechaRegistro
	 * @return Date el valor asociado a la propiedad fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * Metodo que permite modificar el valor de la propiedad fechaRegistro
	 * @param {Date} el nuevo valor para la propiedad fechaRegistro
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	
}
