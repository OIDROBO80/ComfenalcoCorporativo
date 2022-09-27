package co.com.smartfit.web.entities;


import java.util.HashSet;
import java.util.Set;

public class Membresia implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Set empresaEmpleador = new HashSet(0);

	public Membresia() {
	}

	public Membresia(String nombre) {
		this.nombre = nombre;
	}

	public Membresia(String nombre, Set empresaEmpleador) {
		this.nombre = nombre;
		this.empresaEmpleador = empresaEmpleador;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getEmpresaEmpleador() {
		return this.empresaEmpleador;
	}

	public void setEmpresaEmpleador(Set empresaEmpleador) {
		this.empresaEmpleador = empresaEmpleador;
	}

}
