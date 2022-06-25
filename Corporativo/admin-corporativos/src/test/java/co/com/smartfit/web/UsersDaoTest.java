package co.com.smartfit.web;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import co.com.smartfit.web.dao.UsersDao;
import co.com.smartfit.web.entities.Users;

public class UsersDaoTest {

    private UsersDao userDao;

    @Before
    public void setUp() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

        try {
            InitialContext ic = new InitialContext();
            ic.createSubcontext("java:");
            ic.createSubcontext("java:/jboss");
            ic.createSubcontext("java:/jboss/datasources");

            SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/corporativos_smartfit?useSSL=false");
            ds.setUsername("root");
            ds.setPassword("");

            ic.bind("java:jboss/datasources/smartfitDS", ds);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        this.userDao = new UsersDao();
    }

    @Test
    public void obtenerUsuarioTest() {
        Users user = this.userDao.obtenerUsuario("smartfit");
        assertEquals("smartfit", user.getUsername());
    }
}
