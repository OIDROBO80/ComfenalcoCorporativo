package co.com.smartfit.web.service;

import co.com.smartfit.web.entities.ErrorGeneral;
import co.com.smartfit.web.entities.Planes;

import java.util.List;

public interface PlanesPorEmpresaService {
    List<Planes> createPlan(String nombrePlan, Integer dias)  throws ErrorGeneral;
}
