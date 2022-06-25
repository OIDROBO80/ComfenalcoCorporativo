package com.corporativos_smartfit.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EmpresaAfiliado generated by hbm2java
 */
public class EmpresaAfiliado implements java.io.Serializable {

	private Integer id;
	private EmpresaEmpleador empresaEmpleador;
	private TipoDocumentoIdentidad tipoDocumentoIdentidad;
	private String documentoNumero;
	private String nombre;
	private String email;
	private Date fechaCreacion;
	private Set empresaAfiliadoXCodigoDescuentos = new HashSet(0);

	public EmpresaAfiliado() {
		super();
	}

	public EmpresaAfiliado(EmpresaEmpleador empresaEmpleador, TipoDocumentoIdentidad tipoDocumentoIdentidad,
			String documentoNumero, String nombre, String email) {
		this.empresaEmpleador = empresaEmpleador;
		this.tipoDocumentoIdentidad	 = tipoDocumentoIdentidad;
		this.documentoNumero = documentoNumero;
		this.nombre = nombre;
		this.email = email;
	}

	public EmpresaAfiliado(EmpresaEmpleador empresaEmpleador, TipoDocumentoIdentidad tipoDocumentoIdentidad,
			String documentoNumero, String nombre, String email, Character categoria, Date fechaCreacion,
			Set empresaAfiliadoXCodigoDescuentos) {
		this.empresaEmpleador = empresaEmpleador;
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
		this.documentoNumero = documentoNumero;
		this.nombre = nombre;
		this.email = email;
		this.fechaCreacion = fechaCreacion;
		this.empresaAfiliadoXCodigoDescuentos = empresaAfiliadoXCodigoDescuentos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmpresaEmpleador getEmpresaEmpleador() {
		return this.empresaEmpleador;
	}

	public void setEmpresaEmpleador(EmpresaEmpleador empresaEmpleador) {
		this.empresaEmpleador = empresaEmpleador;
	}

	public TipoDocumentoIdentidad getTipoDocumentoIdentidad() {
		return this.tipoDocumentoIdentidad;
	}

	public void setTipoDocumentoIdentidad(TipoDocumentoIdentidad tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}

	public String getDocumentoNumero() {
		return this.documentoNumero;
	}

	public void setDocumentoNumero(String documentoNumero) {
		this.documentoNumero = documentoNumero;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Set getEmpresaAfiliadoXCodigoDescuentos() {
		return this.empresaAfiliadoXCodigoDescuentos;
	}

	public void setEmpresaAfiliadoXCodigoDescuentos(Set empresaAfiliadoXCodigoDescuentos) {
		this.empresaAfiliadoXCodigoDescuentos = empresaAfiliadoXCodigoDescuentos;
	}

}
