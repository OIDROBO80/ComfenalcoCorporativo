package com.corporativos_smartfit.dao;


import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.Membresia;
import com.corporativos_smartfit.entities.TipoDocumentoIdentidad;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

	public TipoDocumentoIdentidad obtenerTipoDocumentoPorId(int id) throws ErrorGeneral {
		Criterion IdFk = Restrictions.eq("id", id);
		this.filters.add(IdFk);
		List<TipoDocumentoIdentidad> listTipoDocumentoIdentidad =this.getRegisters("id");
		if (listTipoDocumentoIdentidad.size() >0) {
			return listTipoDocumentoIdentidad.get(0);
		} else {
			throw new ErrorGeneral(404,"There is no type of identification associated with id: "+id);
		}
	}
}
