package co.com.smartfit.web.service;

import co.com.smartfit.web.entities.ErrorGeneral;
import co.com.smartfit.web.entities.Planes;
import co.com.smartfit.web.entities.PlanesEmpresa;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AsignarPlanAEmpresaService {
    List<PlanesEmpresa>  asignarPlanAEmpresa(Integer idPlan, Integer IdEmpresa)  throws ErrorGeneral;
}
