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
	private final String ROL_ADMINISTRATIVO2 = "ROLE_ADMIN2";
	private final String ROL_EMPRESA = "ROLE_COMPANY";

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
	public boolean esRolAdministrativo2(String username) {
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.obtenerUsuario(username);
		UserRoles rolesUsuario = user.getUserRoles();
		boolean isRol = (null != rolesUsuario && rolesUsuario.getRole().equals(ROL_ADMINISTRATIVO2))? true : false;
		return isRol;
	}

	@Override
	public boolean esRolEmpresa(String username) {
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.obtenerUsuario(username);
		UserRoles rolesUsuario = user.getUserRoles();
		boolean isRol = (null != rolesUsuario && rolesUsuario.getRole().contains(ROL_EMPRESA))? true : false;
		return isRol;
	}


}
