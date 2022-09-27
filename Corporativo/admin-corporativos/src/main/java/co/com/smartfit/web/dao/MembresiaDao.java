package co.com.smartfit.web.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.corporativos_smartfit.dto.ErrorGeneral;
import org.hibernate.Session;

import co.com.smartfit.web.entities.Membresia;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class MembresiaDao extends GenericDao<Membresia> {
	
	public MembresiaDao() {
		super(Membresia.class);
	}

	public Membresia getMembresiaPorId(int id) throws ErrorGeneral {
		Criterion IdFk = Restrictions.eq("id", id);
		this.filters.add(IdFk);
		return this.getRegisters("id").get(0);
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
