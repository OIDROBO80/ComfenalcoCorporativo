package co.com.smartfit.web.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.com.smartfit.web.entities.EmpresaAfiliadoXCodigoDescuento;
import co.com.smartfit.web.util.Util;

/**
 * DAO para obtener la relacion entre afiliado y codigo descuento
 * 
 * @see .EmpresaEmpleador
 * @author alejandro.areiza
 */
public class EmpresaAfiliadoXCodigoDescuentoDao extends GenericDao<EmpresaAfiliadoXCodigoDescuento> {

    private static final Logger LOG = Logger.getLogger(EmpresaAfiliadoXCodigoDescuento.class);

    public EmpresaAfiliadoXCodigoDescuentoDao() {
        super(EmpresaAfiliadoXCodigoDescuento.class);
    }

    /**
     * Metodo que permite obtener el listado de codigos asignados a un afiliado
     * 
     * @param
     * @return List<EmpresaAfiliadoXCodigoDescuento>
     */
    public List<EmpresaAfiliadoXCodigoDescuento> obtenerCodigosAsignadosPorEmpresaAfiliado(int afiliadoId, Date fechaInicial, Date fechaFinal)
            throws Exception {
        List<EmpresaAfiliadoXCodigoDescuento> codigosDescuento = null;
        // obtenemos la session
        Session session = null;
        try {
            session = this.getSession();
            // indicamos los criterios de busqueda (criteria query)
            Criteria criteria = session.createCriteria(EmpresaAfiliadoXCodigoDescuento.class);
            // agregamos crterio de clave foranea
            Criterion fkAfiliado = Restrictions.eq("empresaAfiliado.id", afiliadoId);
            criteria.add(fkAfiliado);
            // criterios por fecha
            if (null != fechaInicial && null != fechaFinal) {
                criteria.add(Restrictions.ge("fechaAsignacion", fechaInicial));
                criteria.add(Restrictions.le("fechaAsignacion", Util.addDays(fechaFinal, 1)));
            }
            criteria.addOrder(Order.asc("fechaAsignacion"));
            // obtenemos la lista segun los criterios dados
            List<EmpresaAfiliadoXCodigoDescuento> entidades = criteria.list();
            session.getTransaction().commit();

            // recorremos el resultado
            codigosDescuento = new ArrayList<>();
            for (EmpresaAfiliadoXCodigoDescuento entidad : entidades) {
                codigosDescuento.add(entidad);
            }
        } catch (Exception e) {
            LOG.error("Error en DAO, obtener codigos disponibles.", e);
            throw new Exception("Error en DAO, obtener codigos disponibles." + "\n" + e.toString(), e);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        return codigosDescuento;
    }

    /**
     * Metodo que permite
     * 
     * @param
     * @return List<EmpresaAfiliadoXCodigoDescuento>
     */
    public List<EmpresaAfiliadoXCodigoDescuento> obtenerCodigosAsignados(Date fechaInicial, Date fechaFinal) throws Exception {
        List<EmpresaAfiliadoXCodigoDescuento> codigosDescuento = null;
        Session session = null;
        try {
            session = this.getSession();
            // indicamos los criterios de busqueda (criteria query)
            Criteria criteria = session.createCriteria(EmpresaAfiliadoXCodigoDescuento.class);
            criteria.add(Restrictions.ge("asignado", true));

            // criterios por fecha
            if (null != fechaInicial && null != fechaFinal) {
                criteria.add(Restrictions.ge("fechaAsignacion", fechaInicial));
                criteria.add(Restrictions.le("fechaAsignacion", Util.addDays(fechaFinal, 1)));
            }
            criteria.addOrder(Order.asc("fechaAsignacion"));

            // obtenemos la lista segun los criterios dados
            List<EmpresaAfiliadoXCodigoDescuento> entidades = criteria.list();
            session.getTransaction().commit();

            // recorremos el resultado
            codigosDescuento = new ArrayList<>();
            for (EmpresaAfiliadoXCodigoDescuento entidad : entidades) {
                codigosDescuento.add(entidad);
            }
        } catch (Exception e) {
            throw new Exception("Error en DAO, obtener codigos disponibles." + "\n" + e.toString(), e);
        } finally {
            this.closeSession(session);
        }
        return codigosDescuento;
    }
    
    
    /**
	 * Método que permite obtener un codigo de descuento por medio del filtro de codigo
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	public EmpresaAfiliadoXCodigoDescuento obtenerPorAfiliadoCodigo(int idAfiliado, int idCodigoDescuento) throws Exception {
		EmpresaAfiliadoXCodigoDescuento afiliadoXcodDesc = null;
        // obtenemos la session
        Session session = null;
        try {
        	session = this.getSession();
			// indicamos los criterios de busqueda (criteria query)
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(EmpresaAfiliadoXCodigoDescuento.class);
			
			// restricciones
			Criterion afiliadoCr = Restrictions.eq("empresaAfiliado.id", idAfiliado);
			criteria.add(afiliadoCr);
			
			Criterion codigoCr = Restrictions.eq("codigoDescuento.id", idCodigoDescuento);
			criteria.add(codigoCr);
			
			// obtenemos la lista segun los criterios dados
			Object resUnique = criteria.uniqueResult();
			afiliadoXcodDesc = (null != resUnique)? (EmpresaAfiliadoXCodigoDescuento) criteria.uniqueResult() : null;
			session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en DAO, obtener afiliadoXcodigoDescuento ." + "\n" + e.toString(), e);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        return afiliadoXcodDesc;
    }
}
