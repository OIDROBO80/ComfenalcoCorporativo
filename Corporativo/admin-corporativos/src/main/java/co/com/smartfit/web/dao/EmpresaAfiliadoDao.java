package co.com.smartfit.web.dao;
// default package
// Generated 15/09/2017 01:45:54 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import co.com.smartfit.web.entities.EmpresaAfiliado;

/**
 * DAO para obtener la entidad EmpresaAfiliado de BD
 * 
 * @see .EmpresaAfiliado
 * @author alejandro.areiza
 */
public class EmpresaAfiliadoDao extends GenericDao<EmpresaAfiliado> {

	private static final Logger LOG = Logger.getLogger(EmpresaAfiliadoDao.class);
	
	public EmpresaAfiliadoDao() {
		super(EmpresaAfiliado.class);
	}

	/**
	 * Metodo que permite 
	 * 
	 * @param 
	 * @return EmpresaAfiliado
	 * @throws Exception 
	 */
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
			// cerramos la sesión de BD
			if(null != session) {
				this.closeSession(session);
			}
		}
		return entidad;
	}
	
	/**
	 * Metodo que permite obtener una lista de afiliados por empresa
	 * 
	 * @param  
	 * @return EmpresaAfiliado
	 * @throws Exception 
	 */
	public List<EmpresaAfiliado> obtenerPorEmpresa(int empresaId, Date fechaInicial, Date fechaFinal) throws Exception {
		List<EmpresaAfiliado> entidades = null;
		Session session = null;
		try {
			session = this.getSession();
			// indicamos los criterios de busqueda (criteria query)
			Criteria criteria = session.createCriteria(EmpresaAfiliado.class);
			// restricciones						
			// agregamos crterio de clave foranea
			criteria.add(Restrictions.eq("empresaEmpleador.id", empresaId));
			if(null != fechaInicial && null != fechaFinal) {
				criteria.add(Restrictions.ge("fechaCreacion", fechaInicial));
				criteria.add(Restrictions.le("fechaCreacion", fechaFinal));				
			}
			criteria.addOrder(Order.asc("fechaCreacion"));
			// obtenemos la lista segun los criterios dados
			entidades = criteria.list();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			String errorMsg = "Error en DAO, problemas al obtener la entidad por id."+"\n";
			LOG.error(errorMsg + e.toString(), e);
			throw new Exception(errorMsg + "(" + e.toString()+ ")", e);
		}
		finally {
			// cerramos la sesión de BD
			if(null != session) {
				this.closeSession(session);
			}
		}
		return entidades;
	}
	
	public EmpresaAfiliado obtenerAfiliadoPorDocumentoYEmpresa(int idTipoDoc, String numeroDoc, int idEmpresaEmpleador) throws Exception {
		LOG.info("Obteniendo la entidad EmpresaAfiliado para el numero de documento: "+numeroDoc+" para el idEmpresaEmpleador: "+idEmpresaEmpleador);
		EmpresaAfiliado empresaAfiliado = null;
		Session session = null;
		try {
			session = this.getSession();
			// indicamos los criterios de busqueda (criteria query)
			Criteria criteria = session.createCriteria(EmpresaAfiliado.class);
			// restricciones
			Criterion documento = Restrictions.eq("documentoNumero", numeroDoc);
			criteria.add(documento);
			// agregamos crterio de clave foranea
			Criterion tipoDocFk = Restrictions.eq("tipoDocumentoIdentidad.id", idTipoDoc);
			criteria.add(tipoDocFk);
			if(-1 != idEmpresaEmpleador) {
				Criterion empresaFk = Restrictions.eq("empresaEmpleador.id", idEmpresaEmpleador);
				criteria.add(empresaFk);
			}
			// obtenemos la lista segun los criterios dados
			Object resUnique = criteria.uniqueResult();
			empresaAfiliado = (null != resUnique)? (EmpresaAfiliado) criteria.uniqueResult() : null;
			session.getTransaction().commit();
		}
		catch (Exception e) {
			throw new Exception("Error en DAO, obtener empresa por documento." + "\n" + e.toString(), e);
		} 
		finally {
			// cerramos la sesión de BD
			this.closeSession(session);
		}
		return empresaAfiliado;
	}

}
