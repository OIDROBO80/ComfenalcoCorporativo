package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.EmpresaAfiliadoXCodigoDescuento;
import com.corporativos_smartfit.entities.EmpresaEmpleadorXPlan;
import com.corporativos_smartfit.util.Util;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EmpresaEmpleadorXPlanDao extends GenericDao<EmpresaEmpleadorXPlan> {

    private static final Logger LOG = Logger.getLogger(EmpresaEmpleadorXPlanDao.class);

    public EmpresaEmpleadorXPlanDao() {
        super(EmpresaEmpleadorXPlan.class);
    }

    public EmpresaEmpleadorXPlan getEmpresaEmpleadorXPlanById(int id) throws ErrorGeneral {
        Criterion IdFk = Restrictions.eq("id", id);
        this.filters.add(IdFk);
        return this.getRegisters("id").get(0);
    }

    public List<EmpresaEmpleadorXPlan> getEmpresaEmpleadorXPlanByIdEmpEmpleador(int idEmpresaEmpleador) throws ErrorGeneral {
        Criterion idEmpresaEmpleadorFk = Restrictions.eq("idEmpresaEmpleador", idEmpresaEmpleador);
        this.filters.add(idEmpresaEmpleadorFk);
        return this.getRegisters("id");
    }

    /*private List<EmpresaEmpleadorXPlan>  callEmpresaEmpleadorXPlan(Criterion IdFk) throws ErrorGeneral {
        List<EmpresaEmpleadorXPlan> listEmpresaEmpleadorXPlan = new ArrayList<>();
        // obtenemos la session
        Session session = null;
        try {
            session = this.getSession();
            Criteria criteria = session.createCriteria(EmpresaEmpleadorXPlan.class);
            criteria.add(IdFk);
            listEmpresaEmpleadorXPlan = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new ErrorGeneral(500,"Error en DAO, callEmpresaEmpleadorXPlan para id" +IdFk.toString()+ "\n" + e.toString());
        } finally {
            this.closeSession(session);
        }
        return listEmpresaEmpleadorXPlan;
    }*/
}
