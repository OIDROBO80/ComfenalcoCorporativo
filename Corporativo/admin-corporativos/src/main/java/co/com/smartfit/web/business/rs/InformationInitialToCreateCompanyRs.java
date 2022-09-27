package co.com.smartfit.web.business.rs;

import co.com.smartfit.web.entities.EmpresaEmpleador;
import co.com.smartfit.web.entities.Planes;
import co.com.smartfit.web.entities.PlanesEmpresa;
import co.com.smartfit.web.model.EmpresaEmpleadorModel;
import co.com.smartfit.web.model.MembresiaModel;
import co.com.smartfit.web.model.PlanesPorEmpresaModel;

import java.util.List;

/**
 * @author alejandro.areiza
 * @since 20/09/2017
 * @version 1.0
 */
public class InformationInitialToCreateCompanyRs {

	private int codigoRespuesta;
	private String description;
	private List<PlanesEmpresa> listPlanesPorEmpresa;
	private List<Planes> listPlanes;
	private List<MembresiaModel> listMembresias;
	public InformationInitialToCreateCompanyRs() {

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

	public List<PlanesEmpresa> getListPlanesPorEmpresa() {
		return listPlanesPorEmpresa;
	}

	public void setListPlanesPorEmpresa(List<PlanesEmpresa> listPlanesPorEmpresa) {
		this.listPlanesPorEmpresa = listPlanesPorEmpresa;
	}

	public List<Planes> getListPlanes() {
		return listPlanes;
	}

	public void setListPlanes(List<Planes> listPlanes) {
		this.listPlanes = listPlanes;
	}

	public List<MembresiaModel> getListMembresias() {
		return listMembresias;
	}

	public void setListMembresias(List<MembresiaModel> listMembresias) {
		this.listMembresias = listMembresias;
	}

}
