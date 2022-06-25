package co.com.smartfit.web.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import co.com.smartfit.web.service.ConvenioAdminServiceImpl;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import co.com.smartfit.web.entities.TipoDocumentoIdentidad;
import co.com.smartfit.web.util.HibernateSessionConfig;

public class TipoDocumentoIdentidadDao extends GenericDao<TipoDocumentoIdentidad> {

	private static final Logger LOG = Logger.getLogger(TipoDocumentoIdentidadDao.class);
	
	public TipoDocumentoIdentidadDao() {
		super(TipoDocumentoIdentidad.class);
	}

	public TipoDocumentoIdentidad obtenerTipoDocumentoPorCodigo(String codigo) {
		LOG.info("Obteniendo la entidad TipoDocumentoIdentidad para el codigo "+codigo);
		TipoDocumentoIdentidad tipoDocumento = null;
		List<TipoDocumentoIdentidad> tiposDocumento = new ArrayList<TipoDocumentoIdentidad>();
		Session session = null;
		String hqlQuery = "from TipoDocumentoIdentidad t where t.codigo = :codigo";
		try {
			session = this.getSession();
			Query query = session.createQuery(hqlQuery);
			query.setParameter("codigo", codigo);
			tiposDocumento = query.getResultList();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			System.err.println("Error en TipoDocumentoIdentidadDao " + e.toString());
			e.printStackTrace();
		} 
		finally {
			try {
				this.closeSession(session);
			}
			catch (Exception e) {
				System.err.println("Error en TipoDocumentoIdentidadDao al cerrar sesion " + e.toString());
			}
		}
		if (tiposDocumento.size() > 0) {
			tipoDocumento = tiposDocumento.get(0);
		}
		LOG.info("El valor recuperado es:"+tipoDocumento.getCodigo()+" con la descripcion "+tipoDocumento.getDescripcion());
		return tipoDocumento;
	}
	
	public TipoDocumentoIdentidad obtenerTipoDocumentoPorId(int codigoId) {

		TipoDocumentoIdentidad tipoDocumento = null;
		List<TipoDocumentoIdentidad> tiposDocumento = new ArrayList<TipoDocumentoIdentidad>();

		Session session = null;

		String hqlQuery = "from TipoDocumentoIdentidad t where t.id = :id";

		try {
			session = this.getSession();
			Query query = session.createQuery(hqlQuery);
			query.setParameter("id", codigoId);
			tiposDocumento = query.getResultList();
			session.getTransaction().commit();
		}
		catch (Exception e) {
			System.err.println("Error en TipoDocumentoIdentidadDao " + e.toString());
			e.printStackTrace();
		} 
		finally {
			try {
				this.closeSession(session);
			}
			catch (Exception e) {
				System.err.println("Error en TipoDocumentoIdentidadDao al cerrar sesion " + e.toString());
			}
		}
		if (tiposDocumento.size() > 0) {
			tipoDocumento = tiposDocumento.get(0);
		}
		return tipoDocumento;
	}

}
