package co.com.smartfit.web.business.rs;

import co.com.smartfit.web.entities.CantidadCodigosPorPlan;
import co.com.smartfit.web.entities.Planes;

import java.util.List;


public class CantidadCodigosPorPlanRs {

	private int codigoRespuesta;
	private String description;
	private List<CantidadCodigosPorPlan> listCantidadDeCodigosPorPlan;

	public CantidadCodigosPorPlanRs() {

	}

	public CantidadCodigosPorPlanRs(int codigoRespuesta,
                                    String description,
									List<CantidadCodigosPorPlan> listCantidadDeCodigosPorPlan) {
		this.codigoRespuesta = codigoRespuesta;
		this.description = description;
		this.listCantidadDeCodigosPorPlan = listCantidadDeCodigosPorPlan;
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

	public List<CantidadCodigosPorPlan> getListCantidadDeCodigosPorPlan() {
		return listCantidadDeCodigosPorPlan;
	}

	public void setListCantidadDeCodigosPorPlan(List<CantidadCodigosPorPlan> listCantidadDeCodigosPorPlan) {
		this.listCantidadDeCodigosPorPlan = listCantidadDeCodigosPorPlan;
	}
}
