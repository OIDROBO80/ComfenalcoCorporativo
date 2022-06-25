package co.com.smartfit.web.service;

import co.com.smartfit.web.dao.UsersDao;
import co.com.smartfit.web.entities.UserRoles;
import co.com.smartfit.web.entities.Users;

/**
 * @author alejandro.areiza
 * @since 19/05/2017
 * @version 1.0
 */


public class GestionadorServiceImpl implements GestionadorService {
	
	private final String ROL_ADMINISTRATIVO = "ROLE_ADMIN";
	private final String ROL_CORPORATIVO = "ROLE_CORP";
	private final String ROL_CORPORATIVO2 = "ROLE_CORP2";

	@Override
	public boolean esRolAdministrativo(String username) {
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.obtenerUsuario(username);
		UserRoles rolesUsuario = user.getUserRoles();
		boolean isRol = (null != rolesUsuario && rolesUsuario.getRole().equals(ROL_ADMINISTRATIVO))? true : false;		
		return isRol;
	}

	@Override
	public boolean esRolCorporativo(String username) {
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.obtenerUsuario(username);
		UserRoles rolesUsuario = user.getUserRoles();
		boolean isRol = (null != rolesUsuario && rolesUsuario.getRole().equals(ROL_CORPORATIVO))? true : false;		
		return isRol;
	}

	@Override
	public boolean esRolCorporativo2(String username) {
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.obtenerUsuario(username);
		UserRoles rolesUsuario = user.getUserRoles();
		boolean isRol = (null != rolesUsuario && rolesUsuario.getRole().equals(ROL_CORPORATIVO2))? true : false;
		return isRol;
	}

}
