package co.com.smartfit.web.dao;
// default package

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import co.com.smartfit.web.entities.EmpresaEmpleador;

/**
 * DAO para obtener la entidad EmpresaEmpleador de BD
 * 
 * @see .EmpresaEmpleador
 * @author alejandro.areiza
 */
public class EmpresaEmpleadorDao extends GenericDao<EmpresaEmpleador> {

    private static final Logger LOG = Logger.getLogger(EmpresaEmpleadorDao.class);

    public EmpresaEmpleadorDao() {
        super(EmpresaEmpleador.class);
    }

    /**
     * Metodo que permite obtener una empresa por documento, tipo documento y membresia
     * 
     * @param
     * @return EmpresaEmpleador
     */
    public EmpresaEmpleador obtenerEmpresaPorDocumento(int idTipoDoc, String numeroDoc, int idMembresia) throws Exception {
        LOG.info("Obteniendo entidad EmpresaEmpleador para el numero de documento: "+numeroDoc+" con membresia:"+ idMembresia);
        EmpresaEmpleador empresaEmpleador = new EmpresaEmpleador();
        Session session = null;
        try {
            session = this.getSession();
            // indicamos los criterios de busqueda (criteria query)
            Criteria criteria = session.createCriteria(EmpresaEmpleador.class);
            // restricciones
            Criterion documento = Restrictions.eq("documentoNumero", numeroDoc);
            // agregamos criterio de clave foranea
            Criterion tipoDocFk = Restrictions.eq("tipoDocumentoIdentidad.id", idTipoDoc);
            Criterion membresiaFk = Restrictions.eq("membresia.id", idMembresia);
            criteria.add(documento);
            criteria.add(tipoDocFk);
            criteria.add(membresiaFk);
            // obtenemos la lista segun los criterios dados
            Object resUnique = criteria.uniqueResult();
            empresaEmpleador = (null != resUnique) ? (EmpresaEmpleador) criteria.uniqueResult() : null;
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en DAO, obtener empresa por documento." + "\n" + e.toString(), e);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        LOG.info("Se obtiene la entidad con nombre: "+empresaEmpleador.getRazonSocial());
        return empresaEmpleador;
    }
}
