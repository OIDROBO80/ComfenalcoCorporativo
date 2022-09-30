package com.corporativos_smartfit.dao;
// default package

// Generated 15/09/2017 01:45:54 PM by Hibernate Tools 5.1.0.Alpha1


import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.entities.CodigoDescuento;
import com.sun.istack.Nullable;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para obtener la entidad EmpresaAfiliado de BD
 * 
 * @see .EmpresaAfiliado
 * @author alejandro.areiza
 */
public class CodigoDescuentoDao extends GenericDao<CodigoDescuento> {

    private static final Logger LOG = Logger.getLogger(CodigoDescuentoDao.class);

    public CodigoDescuentoDao() {
        super(CodigoDescuento.class);
    }

	public List<CodigoDescuento> getCodigoDescuentoByPlan(boolean disponibles, int idEmpresaPlan) throws ErrorGeneral {
        String message =disponibles ? "buscando codigos libres para  idEmpresaPlan:" +idEmpresaPlan :
                "buscando codigos asigandos para  idEmpresaPlan:"+idEmpresaPlan;
        LOG.info(message);
        Criterion noAsignado = Restrictions.eqOrIsNull("asignado", false);
        if (!disponibles) {noAsignado = Restrictions.not(noAsignado);}
        Criterion idEmpresaPlanFk = Restrictions.eq("idEmpresaPlan", idEmpresaPlan);
        this.filters = new ArrayList<>();
        this.filters.add(noAsignado);
        this.filters.add(idEmpresaPlanFk);
        List<CodigoDescuento> listCodigosDescuento = this.getRegisters("id");
        message = disponibles ? "hay "+listCodigosDescuento.size()+" codigos libres" :
                "hay "+listCodigosDescuento.size()+" codigos asignados";
        LOG.info(message);
        return listCodigosDescuento;
    }

	public CodigoDescuento obtenerCodigoDescuentoPorCodigo(String codigo) throws ErrorGeneral {
        LOG.info("Obteniendo el codigo de descuento: "+codigo);
        Criterion codigoCr = Restrictions.eq("codigo", codigo);
        this.filters.add(codigoCr);
        return this.getRegisters("id").get(0);
    }

    
}
