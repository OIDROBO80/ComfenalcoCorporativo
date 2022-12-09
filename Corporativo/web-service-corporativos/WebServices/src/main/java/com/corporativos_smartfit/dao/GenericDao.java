package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.util.HibernateSessionConfig;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<E> {

	private Class<E> clazz;
	private Session session;
	protected ArrayList<Criterion>  filters;
	private static HibernateSessionConfig hbSessionConfig = HibernateSessionConfig.getInstance();

	public GenericDao(Class<E> clazzE) {
		this.clazz = clazzE;
		this.filters=new ArrayList<>();
	}

	public Session getHibernateSession() throws ErrorGeneral {
		session = hbSessionConfig.getSession();
		session.beginTransaction();
		return session;
	}

	public boolean guardar(E entidad) throws Exception {
		boolean guardado = false;
		this.getHibernateSession().save(entidad);
		session.getTransaction().commit();
		hbSessionConfig.closeSession();
		guardado = true;
		return guardado;
	}

	public boolean actualizar(E entidad) throws ErrorGeneral {
		boolean actualizado = false;
		this.getHibernateSession().update(entidad);
		session.getTransaction().commit();
		hbSessionConfig.closeSession();
		actualizado = true;
		return actualizado;
	}

	public boolean guardarOActualizar(E entidad) throws ErrorGeneral {
		boolean guardado = false;
		try{
			this.getHibernateSession().saveOrUpdate(entidad);
			session.getTransaction().commit();
		} catch ( Exception e) {
			e.printStackTrace();
			throw new ErrorGeneral(500,"Error update and create Entity");
		}
		hbSessionConfig.closeSession();
		guardado = true;
		return guardado;
	}

	public void eliminar(E entidad) throws Exception {
		this.getHibernateSession().remove(entidad);
		session.getTransaction().commit();
		hbSessionConfig.closeSession();
	}

	public List<E> obtenerTodos() throws Exception {
		List<E> list = new ArrayList<>();
		list = this.getHibernateSession().createCriteria(clazz).list();
		session.getTransaction().commit();
		hbSessionConfig.closeSession();
		return list;
	}
	
	public List<E> obtenerPaginados(int paginaTamano, int paginaNumero) throws Exception {
		List<E> listE = null;
		int paginaNumAux = paginaNumero -1;
		Session session = null;
		try {			
			session = hbSessionConfig.getSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(clazz);
			criteria.setFirstResult(paginaNumAux*paginaTamano);
			criteria.setMaxResults(paginaTamano);
			listE = criteria.list();
			
			session.getTransaction().commit();
		}
		finally {
			hbSessionConfig.closeSession();
		}
		return listE;
	}

	public List<E> obtenerPaginadosOrdenados(int paginaTamano, int paginaNumero, String campoOrden) throws Exception {
		List<E> listE = null;
		int paginaNumAux = paginaNumero -1;
		Session session = null;
		try {			
			session = hbSessionConfig.getSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(clazz);
			criteria.addOrder(Order.asc(campoOrden));
			criteria.setFirstResult(paginaNumAux*paginaTamano);
			criteria.setMaxResults(paginaTamano);
			listE = criteria.list();
			
			session.getTransaction().commit();
		}
		finally {
			hbSessionConfig.closeSession();
		}
		return listE;
	}
	
	public int obtenerTotal() throws Exception {
		int totalRegistros = 0;
		Session session = null;		
		try {
			session = hbSessionConfig.getSession();
			session.beginTransaction();		
			Criteria criteria = session.createCriteria(clazz);
			Number total = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
			totalRegistros = total.intValue();
			session.getTransaction().commit();
		}
		finally {
			hbSessionConfig.closeSession();
		}		
		return totalRegistros;
	}

	// NUEVOS METODOS CON REFORMAS

	public Session getSession() throws ErrorGeneral {
		try {
			session = hbSessionConfig.getSession();
			session.beginTransaction();
		} 
		catch (Exception e) {
			hbSessionConfig.closeSession();
			throw new ErrorGeneral(500,"Error en DAO, al obtener la sesion"+"\n"
					+ e.getMessage());
		}
		return session;
	}

	public boolean closeSession(Session sesion) throws ErrorGeneral {
		boolean close = false;
		try {			
			hbSessionConfig.closeSession();
			close = true;
		}
		catch (Exception e) {
			System.err.println("Error Close Session");
			e.printStackTrace();
			throw new ErrorGeneral(500,"Error closing session in database connection"+e.getMessage());
		}
		return close;
	}

	public int guardarEntidad(E entidad) throws ConstraintViolationException, Exception {
		int idEntidad = -1;
		try {
			Serializable serialId = this.getSession().save(entidad);
			idEntidad = (int) serialId;			
			session.getTransaction().commit();
		}
		catch (ConstraintViolationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new Exception("Error en DAO, al guardar la entidad. (" + e.toString()+ ")", e);
		}
		finally {
			hbSessionConfig.closeSession();
		}
		return idEntidad;
	}

	public boolean actualizarEntidad(E entidad) throws Exception {

		boolean actualizado = false;
		try {
			Session sesion = this.getHibernateSession();
			sesion.update(entidad);
			sesion.getTransaction().commit();
			actualizado = true;			
		} 
		catch (Exception e) {
			throw new Exception("Error en DAO, al actualizar la entidad. (" + e.toString()+ ")", e);
		}
		finally {
			hbSessionConfig.closeSession();
		}
		return actualizado;
	}
		
	public List<E> getRegisters(String fieldOrder) throws ErrorGeneral {
		List<E> list = new ArrayList<>();
		try {
			Session sesion = this.getHibernateSession();
			Criteria criteria =  sesion.createCriteria(clazz);
			for (Criterion filter:filters) {
				criteria.add(filter);
			}
			criteria.addOrder(Order.asc(fieldOrder));
			list = criteria.list();
			sesion.getTransaction().commit();
		}
		catch (Exception e) {
			throw new ErrorGeneral(500,"Error en DAO, al obtener las entidades."+"\n"
					+ e.getMessage());
		}
		finally {
			hbSessionConfig.closeSession();
		}
		return list;
	}
	
}
