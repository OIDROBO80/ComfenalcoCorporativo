package com.corporativos_smartfit.dto;

import java.util.Date;

public class ResponseCodeQuery extends ResponseCode {

	private String periodicidad;
	private String fechaAsignacion;

	public ResponseCodeQuery() {
		super();
		this.periodicidad = "";
		this.fechaAsignacion = "";
	}

	public ResponseCodeQuery(String code, String message,String periodicidad, String fechaAsignacion) {
		super(code,message);
		this.periodicidad = periodicidad;
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public String getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
}
