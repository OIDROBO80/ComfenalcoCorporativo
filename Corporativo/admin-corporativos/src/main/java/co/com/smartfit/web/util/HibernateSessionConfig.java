package co.com.smartfit.web.util;

import com.corporativos_smartfit.dto.ErrorGeneral;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionConfig {

    private static HibernateSessionConfig instancia = null;
    private static SessionFactory factory = null;
    private static Configuration configuration = null;
    private static Session session = null;
    private static final Logger LOG = Logger.getLogger(HibernateSessionConfig.class);

    public static HibernateSessionConfig getInstance() {
        if (null == instancia) {
            LOG.info("HibernateSessionConfig CREADA");
            instancia = new HibernateSessionConfig();
        }
        LOG.info("HibernateSessionConfig ENTREGADA");
        return instancia;
    }

    public Session getSession() throws ErrorGeneral {
        try {
            if (null == factory) {
                LOG.info("HibernateSessionConfig CREA factory");
                configuration = new Configuration();
                configuration.configure("/co/com/smartfit/web/configuration/hibernate.cfg.xml");
                factory = configuration.buildSessionFactory();
            }

            try {
                LOG.info("SESSION CURRENT: ");
                session = factory.getCurrentSession();
            } catch (HibernateException ex) {
                LOG.info(" TRY OPEN SESSION: " + ex.getMessage());
                session = factory.openSession();
                LOG.info("OPEN SESSION: ");

            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return session;
    }

    public void closeSession() {
        try {
            if (session != null && session.isOpen()) {
                LOG.info("SESSION CLOSE: ");
                session.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
