package co.com.smartfit.web.business.rs;

import co.com.smartfit.web.entities.Planes;
import co.com.smartfit.web.entities.PlanesEmpresa;

import java.util.List;

/**
 * @author alejandro.areiza
 * @since 15/09/2017
 * @version 1.0
 */
public class AsignarPlanAEmpresaRs {

	private int codigoRespuesta;
	private String description;

	private List<PlanesEmpresa> listPlanesPorEmpresaActualizado;

	public AsignarPlanAEmpresaRs() {

	}

	public AsignarPlanAEmpresaRs(int codigoRespuesta,
                                 String description,
								 List<PlanesEmpresa> listPlanesPorEmpresaActualizado) {
		this.codigoRespuesta = codigoRespuesta;
		this.description = description;
		this.listPlanesPorEmpresaActualizado = listPlanesPorEmpresaActualizado;
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

	public List<PlanesEmpresa> getListPlanesPorEmpresaActualizado() {
		return listPlanesPorEmpresaActualizado;
	}

	public void setListPlanesPorEmpresaActualizado(List<PlanesEmpresa> listPlanesPorEmpresaActualizado) {
		this.listPlanesPorEmpresaActualizado = listPlanesPorEmpresaActualizado;
	}
}
