package co.com.smartfit.web.dao;
// default package

// Generated 15/09/2017 01:45:54 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.ArrayList;
import java.util.List;

import co.com.smartfit.web.entities.EmpresaEmpleadorXPlan;
import com.corporativos_smartfit.dto.ErrorGeneral;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import co.com.smartfit.web.entities.CodigoDescuento;

/**
 * DAO para obtener la entidad EmpresaAfiliado de BD
 * 
 * @see .EmpresaAfiliado
 * @author alejandro.areiza
 */
public class CodigoDescuentoDao extends GenericDao<CodigoDescuento> {

    private static final Logger LOG = Logger.getLogger(CodigoDescuentoDao.class);
    private  EmpresaEmpleadorXPlanDao empresaEmpleadorXPlanDao;

    public CodigoDescuentoDao() {
        super(CodigoDescuento.class);
        empresaEmpleadorXPlanDao = new EmpresaEmpleadorXPlanDao();
    }

    public List<CodigoDescuento> obtenerCodigosDisponiblesPorPlan(boolean disponibles, int idEmpresaPlan) throws ErrorGeneral {
        String message =disponibles ? "Obteniendo listado de codigos disponibles para el IdEmpresaEmpleadorPlan:"+idEmpresaPlan :
                "Obteniendo listado de codigos NO disponibles para el IdEmpresaEmpleadorPlan:"+idEmpresaPlan;
        Criterion empresaEmpleadorFk = Restrictions.eq("idEmpresaPlan", idEmpresaPlan);
        Criterion noAsignado = Restrictions.eqOrIsNull("asignado", false);
        if (!disponibles) {
            // negamos el criterio de no asignado (si asignado) en caso de que se hallan solictado lo contrario
            noAsignado = Restrictions.not(noAsignado);
        }
        this.filters.add(noAsignado);
        List<CodigoDescuento> codigosDescuento = this.getRegisters("id");
        return codigosDescuento;
    }

    public List<CodigoDescuento> obtenerCodigosDisponiblesPorEmpresaEmpleador(boolean disponibles, int idEmpresaEmpleador) throws ErrorGeneral {
        String message =disponibles ? "Obteniendo listado de codigos disponibles para el idEmpresaEmpleador:"+idEmpresaEmpleador :
                "Obteniendo listado de codigos NO disponibles para el idEmpresaEmpleador:"+idEmpresaEmpleador;
        LOG.info(message);
        List<EmpresaEmpleadorXPlan> ListEmpresaEmpleadorXPlan = empresaEmpleadorXPlanDao.getEmpresaEmpleadorXPlanByIdEmpEmpleador(idEmpresaEmpleador);
        List<CodigoDescuento> allListCodigoDescuento = new ArrayList<CodigoDescuento>();
        for (EmpresaEmpleadorXPlan empresaEmpleadorXPlan: ListEmpresaEmpleadorXPlan) {
            allListCodigoDescuento.addAll(this.obtenerCodigosDisponiblesPorPlan(disponibles,empresaEmpleadorXPlan.getId()));
        }
        return allListCodigoDescuento;
    }

	public CodigoDescuento obtenerCodigoDescuentoPorCodigo(String codigo) throws Exception {
        LOG.info("Obteniendo codigos de descuento");
        CodigoDescuento codigosDescuento = null;
        // obtenemos la session
        Session session = null;
        try {
        	session = this.getSession();
			// indicamos los criterios de busqueda (criteria query)
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(CodigoDescuento.class);
			
			// restricciones
			Criterion codigoCr = Restrictions.eq("codigo", codigo);
			criteria.add(codigoCr);
			
			// obtenemos la lista segun los criterios dados
			Object resUnique = criteria.uniqueResult();
			codigosDescuento = (null != resUnique)? (CodigoDescuento) criteria.uniqueResult() : null;
			session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en DAO, obtener codigo ." + "\n" + e.toString(), e);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        return codigosDescuento;
    }
	
}
