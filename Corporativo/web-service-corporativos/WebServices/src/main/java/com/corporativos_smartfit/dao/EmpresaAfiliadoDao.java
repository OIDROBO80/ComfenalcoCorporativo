package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.EmpresaAfiliado;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class EmpresaAfiliadoDao extends GenericDao<EmpresaAfiliado> {

	private static final Logger LOG = Logger.getLogger(EmpresaAfiliadoDao.class);
	
	public EmpresaAfiliadoDao() {
		super(EmpresaAfiliado.class);
	}


	public EmpresaAfiliado obtenerPorId(int id) throws Exception {
		EmpresaAfiliado entidad = new EmpresaAfiliado();
		Session session = null;
		try {
			session = this.getSession();
			entidad = session.get(EmpresaAfiliado.class, id);
		}
		catch (Exception e) {
			String errorMsg = "Error en DAO, problemas al obtener la entidad por id."+"\n";
			LOG.error(errorMsg + e.toString(), e);
			throw new Exception(errorMsg + "(" + e.toString()+ ")", e);
		}
		finally {
			if(null != session) {
				this.closeSession(session);
			}
		}
		return entidad;
	}
	

	public List<EmpresaAfiliado> obtenerPorEmpresa(int empresaId, Date fechaInicial, Date fechaFinal) throws Exception {
		List<EmpresaAfiliado> entidades = null;
		Session session = null;
		try {
			session = this.getSession();
			Criteria criteria = session.createCriteria(EmpresaAfiliado.class);
			criteria.add(Restrictions.eq("empresaEmpleador.id", empresaId));
			if(null != fechaInicial && null != fechaFinal) {
				criteria.add(Restrictions.ge("fechaCreacion", fechaInicial));
				criteria.add(Restrictions.le("fechaCreacion", fechaFinal));				
			}
			criteria.addOrder(Order.asc("fechaCreacion"));
			entidades = criteria.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			String errorMsg = "Error en DAO, problemas al obtener la entidad por id."+"\n";
			LOG.error(errorMsg + e.toString(), e);
			throw new Exception(errorMsg + "(" + e.toString()+ ")", e);
		}
		finally {
			if(null != session) {
				this.closeSession(session);
			}
		}
		return entidades;
	}
	
	public EmpresaAfiliado obtenerAfiliadoPorDocumentoYEmpresa(int idTipoDoc, String numeroDoc, int idEmpresaEmpleador) throws ErrorGeneral {
		LOG.info("Getting the Affiliate Company entity for the document number: "+numeroDoc+" and idEmpresaEmpleador: "+idEmpresaEmpleador);
		EmpresaAfiliado empresaAfiliado = null;
		Session session = null;
		try {
			session = this.getSession();
			Criteria criteria = session.createCriteria(EmpresaAfiliado.class);
			Criterion documento = Restrictions.eq("documentoNumero", numeroDoc);
			criteria.add(documento);
			Criterion tipoDocFk = Restrictions.eq("tipoDocumentoIdentidad.id", idTipoDoc);
			criteria.add(tipoDocFk);
			if(-1 != idEmpresaEmpleador) {
				Criterion empresaFk = Restrictions.eq("empresaEmpleador.id", idEmpresaEmpleador);
				criteria.add(empresaFk);
			}
			Object resUnique = criteria.uniqueResult();
			empresaAfiliado = (null != resUnique)? (EmpresaAfiliado) criteria.uniqueResult() : null;
			session.getTransaction().commit();
		}
		catch (Exception e) {
			System.err.println("Error getting affiliate by company");
			e.printStackTrace();
			throw new ErrorGeneral(500,e.getMessage());
		}
		finally {
			this.closeSession(session);
		}
		return empresaAfiliado;
	}
}
