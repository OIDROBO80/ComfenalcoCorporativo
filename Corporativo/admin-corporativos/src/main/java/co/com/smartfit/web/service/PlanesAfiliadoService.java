package co.com.smartfit.web.service;

import co.com.smartfit.web.dao.EmpresaEmpleadorXPlanDao;
import co.com.smartfit.web.dao.PlanesDao;
import co.com.smartfit.web.entities.EmpresaEmpleadorXPlan;
import co.com.smartfit.web.entities.ErrorGeneral;

import java.util.List;

public class PlanesAfiliadoService {

    private EmpresaEmpleadorXPlanDao empresaEmpleadorXPlanDao = new EmpresaEmpleadorXPlanDao();
    private PlanesDao  planesDao = new PlanesDao();

    public PlanesAfiliadoService( ){

    }

    public EmpresaEmpleadorXPlan getPlanByNombrePlan(Integer IdEmpresaEmpleador, String nombrePlan) throws ErrorGeneral {
        List<EmpresaEmpleadorXPlan> empresaEmpleadorXPlan = this.empresaEmpleadorXPlanDao.getEmpresaEmpleadorXPlanByIdEmpEmpleador(IdEmpresaEmpleador);
        for ( EmpresaEmpleadorXPlan empresaEmpleadorPlan:empresaEmpleadorXPlan) {
            if (empresaEmpleadorPlan.getPlan().getNombre().equals(nombrePlan)){
                return empresaEmpleadorPlan;
            }
        }
        throw new ErrorGeneral(404,"No Existe un plan con el nombre "+nombrePlan+" para el Empleador.");
    }

}
