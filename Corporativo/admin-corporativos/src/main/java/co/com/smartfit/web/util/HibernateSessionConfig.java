package co.com.smartfit.web.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionConfig {

    private static HibernateSessionConfig instancia = null;
    private static SessionFactory factory = null;
    private static Configuration configuration = null;
    private Session session = null;

    public static HibernateSessionConfig getInstance() {
        if (null == instancia) {
            instancia = new HibernateSessionConfig();
        }
        return instancia;
    }

    public Session getSession() throws Exception {
        try {
            if (null == factory) {
                configuration = new Configuration();
                configuration.configure("/co/com/smartfit/web/configuration/hibernate.cfg.xml");
                factory = configuration.buildSessionFactory();
            }

            try {
                session = factory.getCurrentSession();
            } catch (HibernateException ex) {
                session = factory.openSession();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return session;
    }

    public void closeSession() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
