package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.entities.Users;
import com.corporativos_smartfit.util.HibernateSessionConfig;
import org.hibernate.Session;

import org.apache.log4j.Logger;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {

    static final Logger logger = Logger.getLogger(UsersDao.class);

    GenericDao<Users> genericDao = new GenericDao<Users>(Users.class);

    private static HibernateSessionConfig hbSessionConfig = HibernateSessionConfig.getInstance();

    @SuppressWarnings("unchecked")
    public Users obtenerUsuario(String username) {
        Users user = new Users();
        List<Users> users = new ArrayList<Users>();

        Session session = null;

        String hqlQuery = "from Users u where u.username = :username";

        try {
            session = hbSessionConfig.getSession();
            session.beginTransaction();
            Query query = session.createQuery(hqlQuery);
            query.setParameter("username", username);
            users = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hbSessionConfig.closeSession();
        }
        if (users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }

    public boolean guardarUser(Users user) {
        boolean guardado = false;
        Users userDB = this.obtenerUsuario(user.getUsername());
        if (userDB != null) {
            user.setIdUser(userDB.getIdUser());
        }

        try {
            guardado = genericDao.guardarOActualizar(user);
        } catch (Exception e) {
            guardado = false;
            e.printStackTrace();
        }

        return guardado;
    }

    public boolean actualizarPassword(String username, String oldPassword, String newPassword) {
        boolean actualizado = false;
        Users user = this.obtenerUsuario(username);
        if (oldPassword == user.getPassword()) {
            user.setPassword(newPassword);
            try {
                actualizado = genericDao.guardarOActualizar(user);
            } catch (Exception e) {
                actualizado = false;
                e.printStackTrace();
            }
        } else {
            actualizado = false;
        }
        return actualizado;
    }
}
