package com.corporativos_smartfit.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EmpresaEmpleador generated by hbm2java
 */
public class EmpresaEmpleador implements java.io.Serializable {

	private Integer id;
	private Membresia membresia;
	private TipoDocumentoIdentidad tipoDocumentoIdentidad;
	private String razonSocial;
	private String documentoNumero;
	private String telefono;
	private String email;
	private boolean activa;
	private String representanteNombre;
	private Date fechaCreacion;
	private String logo;
	private String textoEmail;
	private Set empresaAfiliados = new HashSet(0);
	private Set codigoDescuento = new HashSet(0);

	public EmpresaEmpleador() {
		super();
	}

	public EmpresaEmpleador(Membresia membresia, TipoDocumentoIdentidad tipoDocumentoIdentidad,
			String razonSocial, String documentoNumero, String telefono, String email, boolean activa,
			Date fechaCreacion, String logo, String textoEmail) {
		this.membresia = membresia;
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
		this.razonSocial = razonSocial;
		this.documentoNumero = documentoNumero;
		this.telefono = telefono;
		this.email = email;
		this.activa = activa;
		this.fechaCreacion = fechaCreacion;
		this.logo = logo;
		this.textoEmail = textoEmail;
	}

	public EmpresaEmpleador(Membresia membresia, TipoDocumentoIdentidad tipoDocumentoIdentidad,
			String razonSocial, String documentoNumero, String telefono, String email, boolean activa,
			String representanteNombre, Date fechaCreacion, String logo, String textoEmail, Set empresaAfiliados, Set codigoDescuento) {
		this.membresia = membresia;
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
		this.razonSocial = razonSocial;
		this.documentoNumero = documentoNumero;
		this.telefono = telefono;
		this.email = email;
		this.activa = activa;
		this.representanteNombre = representanteNombre;
		this.fechaCreacion = fechaCreacion;
		this.empresaAfiliados = empresaAfiliados;
		this.codigoDescuento = codigoDescuento;
		this.logo = logo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Membresia getMembresia() {
		return this.membresia;
	}

	public void setMembresia(Membresia membresia) {
		this.membresia = membresia;
	}

	public TipoDocumentoIdentidad getTipoDocumentoIdentidad() {
		return this.tipoDocumentoIdentidad;
	}

	public void setTipoDocumentoIdentidad(TipoDocumentoIdentidad tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDocumentoNumero() {
		return this.documentoNumero;
	}

	public void setDocumentoNumero(String documentoNumero) {
		this.documentoNumero = documentoNumero;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActiva() {
		return this.activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public String getRepresentanteNombre() {
		return this.representanteNombre;
	}

	public void setRepresentanteNombre(String representanteNombre) {
		this.representanteNombre = representanteNombre;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public Set getEmpresaAfiliados() {
		return this.empresaAfiliados;
	}

	public void setEmpresaAfiliados(Set empresaAfiliados) {
		this.empresaAfiliados = empresaAfiliados;
	}

	public Set getCodigoDescuento() {
		return codigoDescuento;
	}

	public void setCodigoDescuento(Set codigoDescuento) {
		this.codigoDescuento = codigoDescuento;
	}

}
