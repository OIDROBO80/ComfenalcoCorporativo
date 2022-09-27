package com.corporativos_smartfit.dao;
// default package


import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.EmpresaEmpleador;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

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
    public EmpresaEmpleador obtenerEmpresaPorDocumento(int idTipoDoc, String numeroDoc, int idMembresia) throws ErrorGeneral {
        LOG.info("Obtaining Employer Company entity for the document number: "+numeroDoc+" with membership:"+ idMembresia);
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
            throw new ErrorGeneral(500,"Error obtaining company information for the  number document :"+numeroDoc);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        if (empresaEmpleador == null){
            throw new ErrorGeneral(404,"There is no type of company associated with id:"+numeroDoc
                    +" ,idMembresia: "+idMembresia+" and identification type "+idTipoDoc);
        }
        LOG.info("Get the named entity: "+empresaEmpleador.getRazonSocial());
        return empresaEmpleador;
    }

    public EmpresaEmpleador obtenerEmpresaEmpleadorPorId(int id) throws ErrorGeneral {
        LOG.info("Obteniendo Empresa Empleador por Id: "+id);
        EmpresaEmpleador empresaEmpleador = new EmpresaEmpleador();
        Session session = null;
        try {
            session = this.getSession();
            // indicamos los criterios de busqueda (criteria query)
            Criteria criteria = session.createCriteria(EmpresaEmpleador.class);
            // restricciones
            Criterion idFk = Restrictions.eq("id", id);
            // agregamos criterio de clave foranea
            criteria.add(idFk);
            // obtenemos la lista segun los criterios dados
            Object resUnique = criteria.uniqueResult();
            empresaEmpleador = (null != resUnique) ? (EmpresaEmpleador) criteria.uniqueResult() : null;
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorGeneral(500,"Error Obteniendo Empresa Empleador :"+id);
        } finally {
            // cerramos la sesión de BD
            this.closeSession(session);
        }
        return empresaEmpleador;
    }

}
