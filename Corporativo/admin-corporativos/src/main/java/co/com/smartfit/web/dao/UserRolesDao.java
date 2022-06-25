package co.com.smartfit.web.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import co.com.smartfit.web.entities.UserRoles;
import co.com.smartfit.web.util.HibernateSessionConfig;

public class UserRolesDao {

	GenericDao<UserRoles> genericDao = new GenericDao<UserRoles>(UserRoles.class);
	private static HibernateSessionConfig hbSessionConfig = HibernateSessionConfig.getInstance();


	public UserRoles obtenerUserRole(String username) {
		UserRoles userR = new UserRoles();
		List<UserRoles> userRoles = new ArrayList<UserRoles>();
		Session session = null;
		String hqlQuery = "from UserRoles u where u.username = :username";

		try {
			session = hbSessionConfig.getSession();
			session.beginTransaction();
			Query query = session.createQuery(hqlQuery);
			query.setParameter("username", username);
			userRoles = query.getResultList();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hbSessionConfig.closeSession();
		}
		if (userRoles.size() > 0) {
			userR = userRoles.get(0);
		}
		return userR;
	}
	
	public boolean guardarUserRole(UserRoles userRoles){
		boolean guardado = false;
		UserRoles userRolesDB = this.obtenerUserRole(userRoles.getUsername());
		if(userRolesDB != null){
			userRoles.setUserRoleId(userRolesDB.getUserRoleId());
		}
		try {
			guardado = genericDao.guardarOActualizar(userRoles);
		} catch (Exception e) {
			guardado = false;
			e.printStackTrace();
		}

		return guardado;
	}
}
