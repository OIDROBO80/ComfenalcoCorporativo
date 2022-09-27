package co.com.smartfit.web.entities;

// Generated 12-may-2017 13:55:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

public class TipoDocumentoIdentidad implements java.io.Serializable {

	private Integer id;
	private String codigo;
	private String descripcion;
	private Set afiliados = new HashSet(0);
	private Set empresas = new HashSet(0);
	private Set empleados = new HashSet(0);
	private Set representanteLegals = new HashSet(0);

	public TipoDocumentoIdentidad() {
	}

	public TipoDocumentoIdentidad(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public TipoDocumentoIdentidad(String codigo, String descripcion,
			Set afiliados, Set empresas, Set empleados, Set representanteLegals) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.afiliados = afiliados;
		this.empresas = empresas;
		this.empleados = empleados;
		this.representanteLegals = representanteLegals;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(Set afiliados) {
		this.afiliados = afiliados;
	}

	public Set getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Set empresas) {
		this.empresas = empresas;
	}

	public Set getEmpleados() {
		return this.empleados;
	}

	public void setEmpleados(Set empleados) {
		this.empleados = empleados;
	}

	public Set getRepresentanteLegals() {
		return this.representanteLegals;
	}

	public void setRepresentanteLegals(Set representanteLegals) {
		this.representanteLegals = representanteLegals;
	}

}
