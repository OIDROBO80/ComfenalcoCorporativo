package co.com.smartfit.web.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import co.com.smartfit.web.util.HibernateSessionConfig;

public class GenericDao<E> {

	private Class<E> clazz;
	private Session session;
	private static HibernateSessionConfig hbSessionConfig = HibernateSessionConfig.getInstance();

	public GenericDao(Class<E> clazzE) {
		this.clazz = clazzE;
	}

	public Session getHibernateSession() throws Exception {
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

	public boolean actualizar(E entidad) throws Exception {
		boolean actualizado = false;
		this.getHibernateSession().update(entidad);
		session.getTransaction().commit();
		hbSessionConfig.closeSession();
		actualizado = true;
		return actualizado;
	}

	public boolean guardarOActualizar(E entidad) throws Exception {
		boolean guardado = false;
		this.getHibernateSession().saveOrUpdate(entidad);
		session.getTransaction().commit();
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

	/**
	 * Metodo que permite obtener una conexion de base de datos mediante HibernateSessionConfig 
	 * 
	 * @return Session org.hibernate.Session session para acceso a BD
	 * @throws Exception
	 */
	public Session getSession() throws Exception {
		try {
			session = hbSessionConfig.getSession();
			session.beginTransaction();
		} 
		catch (Exception e) {
			// cerramos la sesión de BD
			hbSessionConfig.closeSession();
			throw new Exception("Error en DAO, al obtener la sesion"+"\n" 
					+ e.toString(), e);			
		}
		return session;
	}
	
	/**
	 * Metodo que cierra una conexion de base de datos mediante HibernateSessionConfig 
	 * 
	 * @param session org.hibernate.Session session para acceso a BD
	 * @return boolean que indica si la sesion fue cerrada correctamente
	 * @throws Exception
	 */
	public boolean closeSession(Session sesion) throws Exception {
		boolean close = false;
		try {			
			hbSessionConfig.closeSession();
			close = true;
		}
		catch (Exception e) {
			throw new Exception("Error en DAO, al cerrar la session ."+"\n" 
					+ e.toString(), e);
		}
		return close;
	}

	/**
	 * Metodo que permite guardar una entidad en BD y directamente obtener su id
	 * 
	 * @param entidad E a ser gaurdada en BD
	 * @return int id en base de datos tras ser guardada la entidad (siempre que sea autoincremental)
	 * @throws Exception
	 */
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
			// cerramos la sesión de BD
			hbSessionConfig.closeSession();
		}
		return idEntidad;
	}
	
	/**
	 * Metodo que permite actualizar la entidad en BD
	 * 
	 * @param entidad E a ser actualizada en BD
	 * @return boolean indica si el dato fue actualizado exitosamente
	 * @throws Exception
	 */
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
			// cerramos la sesión de BD
			hbSessionConfig.closeSession();
		}
		return actualizado;
	}
		
	public List<E> obtenerTodosEntidades() throws Exception {
		List<E> list = new ArrayList<>();
		try {
			Session sesion = this.getHibernateSession();
			list = sesion.createCriteria(clazz).list();
			sesion.getTransaction().commit();
		}
		catch (Exception e) {
			throw new Exception("Error en DAO, al obtener las entidades."+"\n" 
					+ e.toString(), e);
		}
		finally {
			// cerramos la sesión de BD
			hbSessionConfig.closeSession();
		}
		return list;
	}
	
}
