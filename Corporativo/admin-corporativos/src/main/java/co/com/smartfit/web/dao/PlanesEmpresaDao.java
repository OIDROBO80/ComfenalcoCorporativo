package co.com.smartfit.web.dao;


import co.com.smartfit.web.entities.Membresia;
import co.com.smartfit.web.entities.Planes;
import co.com.smartfit.web.entities.PlanesEmpresa;
import co.com.smartfit.web.model.PlanesPorEmpresaModel;
import com.corporativos_smartfit.dto.ErrorGeneral;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class PlanesEmpresaDao extends GenericDao<PlanesEmpresa> {

    private static final Logger LOG = Logger.getLogger(PlanesEmpresa.class);

    public PlanesEmpresaDao() {
        super(PlanesEmpresa.class);
    }
}
