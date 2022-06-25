package co.com.smartfit.web.dao;
// default package

// Generated 15/09/2017 01:45:54 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.NOPLogger;
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

    public CodigoDescuentoDao() {
        super(CodigoDescuento.class);
    }
    
    /**
     * Metodo que permite obtener entidades de CodigoDescuento que pertenecen a una EmpresaEmpleador
     * 
     * @param {disponibles} indica si se desea obtener los codigos disponibles
     * @param {idEmpresaEmpleador} Identificador de la entidad de EmpresaEmpleador
     * @return {List<CodigoDescuento>} codigos de descuento disponibles o no en base de datos (según el criterio)
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<CodigoDescuento> obtenerCodigosDisponiblesPorEmpresaEmpleador(boolean disponibles, int idEmpresaEmpleador) throws Exception {
        String message =disponibles ? "Obteniendo listado de codigos disponibles para el idEmpresaEmpleador:"+idEmpresaEmpleador :
                "Obteniendo listado de codigos NO disponibles para el idEmpresaEmpleador:"+idEmpresaEmpleador;
        LOG.info(message);
        List<CodigoDescuento> codigosDescuento = null;
        // obtenemos la session
        Session session = null;
        try {
            session = this.getSession();
            // indicamos los criterios de busqueda (criteria query)
            @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(CodigoDescuento.class);
            // restricciones
            Criterion noAsignado = Restrictions.eqOrIsNull("asignado", false);
            if (!disponibles) {
                // negamos el criterio de no asignado (si asignado) en caso de que se hallan solictado lo contrario
                noAsignado = Restrictions.not(noAsignado);
            }
            // agregamos criterio de clave foranea
            Criterion empresaEmpleadorFk = Restrictions.eq("empresaEmpleador.id", idEmpresaEmpleador);
            criteria.add(noAsignado);
            criteria.add(empresaEmpleadorFk);
            // obtenemos la lista segun los criterios dados
            codigosDescuento = criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en DAO, obtener codigos disponibles." + "\n" + e.toString(), e);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        message = disponibles ? "se tienen "+codigosDescuento.size()+" codigos disponibles" :
                "se tienen "+codigosDescuento.size()+" codigos No disponibles";
        LOG.info(message);
        return codigosDescuento;
    }
	
	/**
	 * Método que permite obtener un codigo de descuento por medio del filtro de codigo
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
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
