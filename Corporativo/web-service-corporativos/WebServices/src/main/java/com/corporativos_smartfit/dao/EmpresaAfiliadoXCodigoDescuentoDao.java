package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.entities.EmpresaAfiliadoXCodigoDescuento;
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


public class EmpresaAfiliadoXCodigoDescuentoDao extends GenericDao<EmpresaAfiliadoXCodigoDescuento> {

    private static final Logger LOG = Logger.getLogger(EmpresaAfiliadoXCodigoDescuento.class);

    public EmpresaAfiliadoXCodigoDescuentoDao() {
        super(EmpresaAfiliadoXCodigoDescuento.class);
    }

    public List<EmpresaAfiliadoXCodigoDescuento> obtenerCodigosAsignadosPorEmpresaAfiliado(int afiliadoId, Date fechaInicial, Date fechaFinal)
            throws Exception {
        List<EmpresaAfiliadoXCodigoDescuento> codigosDescuento = null;
        Session session = null;
        try {
            session = this.getSession();
            Criteria criteria = session.createCriteria(EmpresaAfiliadoXCodigoDescuento.class);
            Criterion fkAfiliado = Restrictions.eq("empresaAfiliado.id", afiliadoId);
            criteria.add(fkAfiliado);
            if (null != fechaInicial && null != fechaFinal) {
                criteria.add(Restrictions.ge("fechaAsignacion", fechaInicial));
                criteria.add(Restrictions.le("fechaAsignacion", Util.addDays(fechaFinal, 1)));
            }
            criteria.addOrder(Order.asc("fechaAsignacion"));
            List<EmpresaAfiliadoXCodigoDescuento> entidades = criteria.list();
            session.getTransaction().commit();

            codigosDescuento = new ArrayList<>();
            for (EmpresaAfiliadoXCodigoDescuento entidad : entidades) {
                codigosDescuento.add(entidad);
            }
        } catch (Exception e) {
            LOG.error("Error en DAO, obtener codigos disponibles.", e);
            throw new Exception("Error en DAO, obtener codigos disponibles." + "\n" + e.toString(), e);
        } finally {
            this.closeSession(session);
        }
        return codigosDescuento;
    }

    public List<EmpresaAfiliadoXCodigoDescuento> obtenerCodigosAsignados(Date fechaInicial, Date fechaFinal) throws Exception {
        List<EmpresaAfiliadoXCodigoDescuento> codigosDescuento = null;
        Session session = null;
        try {
            session = this.getSession();
            Criteria criteria = session.createCriteria(EmpresaAfiliadoXCodigoDescuento.class);
            criteria.add(Restrictions.ge("asignado", true));
            if (null != fechaInicial && null != fechaFinal) {
                criteria.add(Restrictions.ge("fechaAsignacion", fechaInicial));
                criteria.add(Restrictions.le("fechaAsignacion", Util.addDays(fechaFinal, 1)));
            }
            criteria.addOrder(Order.asc("fechaAsignacion"));
            List<EmpresaAfiliadoXCodigoDescuento> entidades = criteria.list();
            session.getTransaction().commit();
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

	public EmpresaAfiliadoXCodigoDescuento obtenerPorAfiliadoCodigo(int idAfiliado, int idCodigoDescuento) throws Exception {
		EmpresaAfiliadoXCodigoDescuento afiliadoXcodDesc = null;
        Session session = null;
        try {
        	session = this.getSession();
			Criteria criteria = session.createCriteria(EmpresaAfiliadoXCodigoDescuento.class);
			Criterion afiliadoCr = Restrictions.eq("empresaAfiliado.id", idAfiliado);
			criteria.add(afiliadoCr);
			Criterion codigoCr = Restrictions.eq("codigoDescuento.id", idCodigoDescuento);
			criteria.add(codigoCr);
			Object resUnique = criteria.uniqueResult();
			afiliadoXcodDesc = (null != resUnique)? (EmpresaAfiliadoXCodigoDescuento) criteria.uniqueResult() : null;
			session.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error en DAO, obtener afiliadoXcodigoDescuento ." + "\n" + e.toString(), e);
        } finally {
            this.closeSession(session);
        }
        return afiliadoXcodDesc;
    }
}
