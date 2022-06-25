package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.entities.UserRoles;
import com.corporativos_smartfit.util.HibernateSessionConfig;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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
