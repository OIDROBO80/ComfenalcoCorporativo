package com.corporativos_smartfit.dao;

import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.*;
import com.sun.istack.Nullable;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import java.util.List;

public class PlanesDao extends GenericDao<Planes> {

    private static final Logger LOG = Logger.getLogger(PlanesDao.class);

    public PlanesDao() {
        super(Planes.class);
    }

    public Planes obtenerPlanById(int id ) throws ErrorGeneral {
        LOG.info("Obteniendo Plan para el codigo de descuento");
        // obtenemos la session
        List<Planes> planes = null;
        Planes plan = null;
        Session session = null;
        String hqlQuery = "from Planes u where u.id = :id";

        try {
            session = this.getSession();
            // indicamos los criterios de busqueda (criteria query)
            Query query = session.createQuery(hqlQuery);
            query.setParameter("id", id);
            planes = query.getResultList();
            plan =planes.get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorGeneral(500,"Error en DAO Planes, obtenerPlanByIdEmpresaEmpleadorXPlan ." + "\n" + e.getMessage());
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        LOG.info("El plan encontrado es "+ plan.getNombre()+ " con periocidad "+plan.getPeriocidad());
        return plan;
    }

}
