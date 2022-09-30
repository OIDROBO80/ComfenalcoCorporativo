package com.corporativos_smartfit.services;


import com.corporativos_smartfit.dao.*;
import com.corporativos_smartfit.dto.*;
import com.corporativos_smartfit.entities.*;
import java.util.Set;

public class AffiliateQueryService extends AffiliateService {
    private AffiliateDocumentRequest affiliateDocumentRequest;

    public AffiliateQueryService(AffiliateDocumentRequest affiliateDocumentRequest) {
        super();
        this.affiliateDocumentRequest=affiliateDocumentRequest;
        this.empresaEmpleadorDao = new EmpresaEmpleadorDao();
        this.tipoDocumentoIdentidadDao = new TipoDocumentoIdentidadDao();
        this.empresaAfiliadoDao = new EmpresaAfiliadoDao();
        this.empresaAfiliadoXCodigoDescuentoDao = new EmpresaAfiliadoXCodigoDescuentoDao();
        this.codigoDescuentoDao = new CodigoDescuentoDao();
    }

    public ResponseCodeQuery getInfomationCodeAsigned() throws ErrorGeneral{
        TipoDocumentoIdentidad tipoDocumentoIdentidad;
        EmpresaAfiliado empresaAfiliado;
        EmpresaAfiliadoXCodigoDescuento lastEmpresaAfiliadoXCodigoDescuentoAsignado;
        ResponseCodeQuery responseCodeQuery = new ResponseCodeQuery();
        tipoDocumentoIdentidad = tipoDocumentoIdentidadDao
                .obtenerTipoDocumentoPorId(this.affiliateDocumentRequest.getTipoDocumento());
        empresaAfiliado = this.getEmpresaAfiliado(tipoDocumentoIdentidad.getId(),
                affiliateDocumentRequest.getnDocumento());
        Set codingsAssigned = empresaAfiliado.getEmpresaAfiliadoXCodigoDescuentos();
        if (codingsAssigned.size() == 0 ) {
            throw new ErrorGeneral(404,"No Existen codigos asignados");
        } else {
            lastEmpresaAfiliadoXCodigoDescuentoAsignado = this.getLastCodeAsigned(codingsAssigned);
            CodigoDescuento lastCodigoDescuentoAsignado = lastEmpresaAfiliadoXCodigoDescuentoAsignado.getCodigoDescuento();
            responseCodeQuery.setMessage("Descripcion del Ultimo codigo asignado");
            responseCodeQuery.setFechaAsignacion(lastEmpresaAfiliadoXCodigoDescuentoAsignado.getFechaAsignacion().toString());
            responseCodeQuery.setCode(lastCodigoDescuentoAsignado.getCodigo());
            responseCodeQuery.setPeriodicidad(lastCodigoDescuentoAsignado.getEmpresaEmpleadorXPlan().getPlan().getNombre());
        }
        return responseCodeQuery;
    }

    private EmpresaAfiliado getEmpresaAfiliado(int idTipoDoc, String numeroDoc) {
        EmpresaAfiliado empresaAfiliado = empresaAfiliadoDao
                .obtenerAfiliadoPorDocumentoYEmpresa(idTipoDoc,numeroDoc,-1);
        if (empresaAfiliado==null){
            throw new ErrorGeneral( 400, "Usuario No existe");
        }
        return empresaAfiliado;
    }

}
