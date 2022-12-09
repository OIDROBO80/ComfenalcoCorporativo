package co.com.smartfit.web.dao;


import co.com.smartfit.web.entities.CantidadCodigosPorPlan;
import co.com.smartfit.web.entities.CodigoDescuento;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class CantidadCodigosPorPlanDao extends GenericDao<CantidadCodigosPorPlan> {

    private static final Logger LOG = Logger.getLogger(CantidadCodigosPorPlanDao.class);

    public CantidadCodigosPorPlanDao() {
        super(CantidadCodigosPorPlan.class);
    }

    public List<CantidadCodigosPorPlan> getListCode(Boolean asignado, String documentoEmpresa) {
        this.filters =new ArrayList<>();
        Criterion idEmpresa_criterio = Restrictions.eq("documentoEmpresa", documentoEmpresa);
        Criterion asignado_criterio = Restrictions.eqOrIsNull("asignado", asignado);
        this.filters.add(idEmpresa_criterio);
        this.filters.add(asignado_criterio);
        return  this.getRegisters("id");
    }
}
