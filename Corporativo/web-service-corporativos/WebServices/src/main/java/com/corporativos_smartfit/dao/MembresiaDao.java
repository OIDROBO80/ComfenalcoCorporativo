package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.entities.Membresia;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
//import co.com.smartfit.web.util.HibernateSessionConfig;

public class MembresiaDao extends GenericDao<Membresia> {
	
	public MembresiaDao() {
		super(Membresia.class);
	}
	
	public Membresia obtenerMembresiaPorId(int id) {
		Membresia membresia = null;
		List<Membresia> membresiaLista = new ArrayList<Membresia>();

		Session session = null;

		String hqlQuery = "from Membresia m where m.id = :id";

		try {
			session = this.getSession();
			Query query = session.createQuery(hqlQuery);
			query.setParameter("id", id);
			membresiaLista = query.getResultList();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			System.err.println("Error en MembresiaDao " + e.toString());
			e.printStackTrace();
		} 
		finally {
			try {
				this.closeSession(session);
			}
			catch (Exception e) {
				System.err.println("Error en MembresiaDao al cerrar sesion " + e.toString());
			}
		}
		if (membresiaLista.size() > 0) {
			membresia = membresiaLista.get(0);
		}
		return membresia;
	}

	public Membresia obtenerMembresiaPorNombre(String nombre) {
		Membresia membresia = null;
		List<Membresia> membresiaLista = new ArrayList<Membresia>();

		Session session = null;

		String hqlQuery = "from Membresia m where m.nombre = :nombre";

		try {
			session = this.getSession();
			Query query = session.createQuery(hqlQuery);
			query.setParameter("nombre", nombre);
			membresiaLista = query.getResultList();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			System.err.println("Error en MembresiaDao " + e.toString());
			e.printStackTrace();
		} 
		finally {
			try {
				this.closeSession(session);
			}
			catch (Exception e) {
				System.err.println("Error en MembresiaDao al cerrar sesion " + e.toString());
			}
		}
		if (membresiaLista.size() > 0) {
			membresia = membresiaLista.get(0);
		}
		return membresia;
	}

}
