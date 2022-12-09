package co.com.smartfit.web.business.rs;

import co.com.smartfit.web.entities.Planes;
import java.util.List;


public class CrearPlanRs {

	private int codigoRespuesta;
	private String description;
	private List<Planes> listPlanesActuales;

	public CrearPlanRs() {

	}

	public CrearPlanRs(int codigoRespuesta,
					   String description,
					   List<Planes> listPlanesActuales) {
		this.codigoRespuesta = codigoRespuesta;
		this.description = description;
		this.listPlanesActuales = listPlanesActuales;
	}

	public int getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(int codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Planes> getListPlanesActuales() {
		return listPlanesActuales;
	}

	public void setListPlanesActuales(List<Planes> listPlanesActuales) {
		this.listPlanesActuales = listPlanesActuales;
	}
}
