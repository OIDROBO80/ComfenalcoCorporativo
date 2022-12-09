package com.corporativos_smartfit.dao;



import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.EmpresaEmpleador;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;


public class EmpresaEmpleadorDao extends GenericDao<EmpresaEmpleador> {

    private static final Logger LOG = Logger.getLogger(EmpresaEmpleadorDao.class);

    public EmpresaEmpleadorDao() {
        super(EmpresaEmpleador.class);
    }


    public EmpresaEmpleador obtenerEmpresaPorDocumento(int idTipoDoc, String numeroDoc, int idMembresia) throws ErrorGeneral {
        LOG.info("Obtaining Employer Company entity for the document number: "+numeroDoc+" with membership:"+ idMembresia);
        EmpresaEmpleador empresaEmpleador = new EmpresaEmpleador();
        Session session = null;
        try {
            session = this.getSession();
            Criteria criteria = session.createCriteria(EmpresaEmpleador.class);
            Criterion documento = Restrictions.eq("documentoNumero", numeroDoc);
            Criterion tipoDocFk = Restrictions.eq("documentoTipo", idTipoDoc);
            Criterion membresiaFk = Restrictions.eq("idMembresia", idMembresia);
            criteria.add(documento);
            criteria.add(tipoDocFk);
            criteria.add(membresiaFk);
            Object resUnique = criteria.uniqueResult();
            empresaEmpleador = (null != resUnique) ? (EmpresaEmpleador) criteria.uniqueResult() : null;
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new ErrorGeneral(500,"Error obtaining company information for the  number document :"+numeroDoc);
        } finally {
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
            Criteria criteria = session.createCriteria(EmpresaEmpleador.class);
            Criterion idFk = Restrictions.eq("id", id);
            criteria.add(idFk);
            Object resUnique = criteria.uniqueResult();
            empresaEmpleador = (null != resUnique) ? (EmpresaEmpleador) criteria.uniqueResult() : null;
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorGeneral(500,"Error Obteniendo Empresa Empleador :"+id);
        } finally {

            this.closeSession(session);
        }
        return empresaEmpleador;
    }

}
