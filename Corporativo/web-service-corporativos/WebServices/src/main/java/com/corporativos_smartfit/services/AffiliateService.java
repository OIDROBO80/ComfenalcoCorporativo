package com.corporativos_smartfit.services;


import com.corporativos_smartfit.dao.*;
import com.corporativos_smartfit.dto.AffiliateRequest;
import com.corporativos_smartfit.dto.ErrorGeneral;
import com.corporativos_smartfit.dto.ResponseCode;
import com.corporativos_smartfit.entities.*;
import com.corporativos_smartfit.enums.Periodicidad;
import com.corporativos_smartfit.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class AffiliateService {

    protected EmpresaEmpleadorDao empresaEmpleadorDao;
    protected AffiliateRequest affiliateRequest;
    protected TipoDocumentoIdentidadDao tipoDocumentoIdentidadDao;
    protected EmpresaAfiliadoDao empresaAfiliadoDao;
    protected EmpresaAfiliadoXCodigoDescuentoDao empresaAfiliadoXCodigoDescuentoDao;
    protected CodigoDescuentoDao codigoDescuentoDao;

    public AffiliateService(AffiliateRequest affiliateRequest) {
        this.affiliateRequest = affiliateRequest;
        this.empresaEmpleadorDao = new EmpresaEmpleadorDao();
        this.tipoDocumentoIdentidadDao = new TipoDocumentoIdentidadDao();
        this.empresaAfiliadoDao = new EmpresaAfiliadoDao();
        this.empresaAfiliadoXCodigoDescuentoDao = new EmpresaAfiliadoXCodigoDescuentoDao();
        this.codigoDescuentoDao = new CodigoDescuentoDao();
    }
    public AffiliateService() {

    }


    public ResponseCode assignCode() throws ErrorGeneral {
        TipoDocumentoIdentidad tipoDocumentoIdentidad;
        EmpresaEmpleador empresaEmpleador;
        EmpresaAfiliado empresaAfiliado;
        CodigoDescuento codigoDescuentoAsignado;
        ResponseCode response = new ResponseCode();
        tipoDocumentoIdentidad = tipoDocumentoIdentidadDao
                .obtenerTipoDocumentoPorId(affiliateRequest.getTipoDocumento());
        empresaEmpleador = empresaEmpleadorDao.obtenerEmpresaPorDocumento(affiliateRequest.getTipoDocumentEmpresaEmpleador(),
                affiliateRequest.getnDocumentoEmpresaEmpleador(),
                affiliateRequest.getIdMembresiaEmpresaEmpleador());
        empresaAfiliado = this.getAffiliateByCompany(tipoDocumentoIdentidad, empresaEmpleador);
        Set codingsAssigned = empresaAfiliado.getEmpresaAfiliadoXCodigoDescuentos();
        if (codingsAssigned.size() == 0 || !this.IsValidCodeAssigned(codingsAssigned)) {
            codigoDescuentoAsignado = this.assignCodeFree(empresaAfiliado,empresaEmpleador);
            response.setMessage("Nuevo codigo asignado");
        } else {
            codigoDescuentoAsignado = this.getLastCodeAsigned(codingsAssigned).getCodigoDescuento();
            response.setMessage("El codigo esta vigente con periodicidad:"+codigoDescuentoAsignado.getPeriodicidad());
        }
        response.setCode(codigoDescuentoAsignado.getCodigo());
        return response;
    }

    private EmpresaAfiliado getAffiliateByCompany(TipoDocumentoIdentidad tipoDocumentoIdentidad,
                                                  EmpresaEmpleador empresaEmpleador) throws ErrorGeneral {
        EmpresaAfiliado empresaAfiliado;
        empresaAfiliado = empresaAfiliadoDao.obtenerAfiliadoPorDocumentoYEmpresa(tipoDocumentoIdentidad.getId(),
                affiliateRequest.getnDocumento(),
                empresaEmpleador.getId());
        if (empresaAfiliado == null) {
            return this.createAffiliate(tipoDocumentoIdentidad, empresaEmpleador);
        }
        return empresaAfiliado;
    }

    private EmpresaAfiliado createAffiliate(TipoDocumentoIdentidad tipoDocumentoIdentidad,
                                            EmpresaEmpleador empresaEmpleador) throws ErrorGeneral {
        EmpresaAfiliado empresaAfiliado = new EmpresaAfiliado();
        empresaAfiliado.setTipoDocumentoIdentidad(tipoDocumentoIdentidad);
        empresaAfiliado.setDocumentoNumero(affiliateRequest.getnDocumento());
        empresaAfiliado.setFechaCreacion(new Date());
        empresaAfiliado.setNombre(affiliateRequest.getName());
        empresaAfiliado.setEmpresaEmpleador(empresaEmpleador);
        empresaAfiliado.setEmail(empresaEmpleador.getEmail());
        empresaAfiliadoDao.guardarOActualizar(empresaAfiliado);
        return empresaAfiliado;

    }

    private CodigoDescuento getCodeFree(EmpresaEmpleador empresaEmpleador) {
        List<CodigoDescuento> codigoDescuentoList = this.codigoDescuentoDao.obtenerCodigosDisponiblesPorEmpresaEmpleador(true, empresaEmpleador.getId(), affiliateRequest.getPeriodo());
        if (codigoDescuentoList.size() > 0) {
            return codigoDescuentoList.get(0);
        } else {
            throw new ErrorGeneral(404, "No hay codigos disponibles.");
        }
    }

    private CodigoDescuento assignCodeFree( EmpresaAfiliado empresaAfiliado,EmpresaEmpleador empresaEmpleador) {
        EmpresaAfiliadoXCodigoDescuento empresaAfiliadoXCodigoDescuento = new EmpresaAfiliadoXCodigoDescuento();
        CodigoDescuento codigolibre= this.getCodeFree(empresaEmpleador);
        codigolibre.setAsignado(true);
        empresaAfiliadoXCodigoDescuento.setCodigoDescuento(codigolibre);
        empresaAfiliadoXCodigoDescuento.setEmpresaAfiliado(empresaAfiliado);
        empresaAfiliadoXCodigoDescuento.setAsignado(true);
        empresaAfiliadoXCodigoDescuento.setFechaAsignacion(new Date());
        this.empresaAfiliadoXCodigoDescuentoDao.guardarOActualizar(empresaAfiliadoXCodigoDescuento);
        this.codigoDescuentoDao.guardarOActualizar(codigolibre);
        return  codigolibre;
    }

    private boolean  IsValidCodeAssigned( Set codigosAsignados) throws ErrorGeneral {
        boolean result =false;
        Date hoy = new Date();
        Date fechaInicial = Util.addDays(hoy,-365);
        Date fechaFinal = Util.addDays(hoy,0);
        List<EmpresaAfiliadoXCodigoDescuento> listCodigosAsignados = (List<EmpresaAfiliadoXCodigoDescuento>)codigosAsignados.stream().collect(Collectors.toList());
        List<EmpresaAfiliadoXCodigoDescuento> listCodigosActivos = listCodigosAsignados.stream().filter(code -> code.getAsignado().equals(true)).collect(Collectors.toList());
        EmpresaAfiliadoXCodigoDescuento lastEmpresaAfiliadoXCodigoDescuentoAsignado = this.getLastCodeAsigned(codigosAsignados);
        if (lastEmpresaAfiliadoXCodigoDescuentoAsignado.getCodigoDescuento().getAsignado() == true)
        {
            CodigoDescuento lastCodigoDescuentoAsignado = lastEmpresaAfiliadoXCodigoDescuentoAsignado.getCodigoDescuento();
            Date fechadeAsignado = lastEmpresaAfiliadoXCodigoDescuentoAsignado.getFechaAsignacion();
           /* LOG.info("El afiliado tiene el ultimo codigo asignado con periocidad de : "+lastCodigoDescuentoAsignado.getPeriodicidad()+
                    " y fecha de asignacion:"+fechadeAsignado);*/
            Periodicidad periodicidadEnum = Periodicidad.valueOf(lastCodigoDescuentoAsignado.getPeriodicidad());
            Date fechaInicialToValidate = Util.addDays(hoy,-(periodicidadEnum.getDaysToPeriod()-periodicidadEnum.getDaysToValidateAgreements()));
            Date fechaFinalToValidate = Util.addDays(hoy,0);
            //LOG.info("Las fecha utilizadas para validar fueron : "+fechaInicialToValidate+" y "+fechaFinalToValidate);
            result =    fechadeAsignado.after(fechaInicialToValidate) && fechadeAsignado.before(fechaFinalToValidate);
        }
        return result;
    }

    protected EmpresaAfiliadoXCodigoDescuento  getLastCodeAsigned( Set codigosAsignados) {
        EmpresaAfiliadoXCodigoDescuento lastEmpresaAfiliadoXCodigoDescuento = new EmpresaAfiliadoXCodigoDescuento();
        CodigoDescuento  lastCodeAsigned;
        List<EmpresaAfiliadoXCodigoDescuento> listCodigosAsignados = (List<EmpresaAfiliadoXCodigoDescuento>)codigosAsignados.stream().collect(Collectors.toList());
        List<EmpresaAfiliadoXCodigoDescuento> listCodigosActivos = listCodigosAsignados.stream().filter(code -> code.getAsignado().equals(true)).collect(Collectors.toList());
        if (listCodigosActivos.size()>0) {
            listCodigosActivos.sort((o1, o2) -> {
                if (o1.getFechaAsignacion() == null || o2.getFechaAsignacion() == null)
                    return 0;
                return o2.getFechaAsignacion().compareTo(o1.getFechaAsignacion());
            });
            lastEmpresaAfiliadoXCodigoDescuento = listCodigosActivos.get(0);
            // LOG.info("El afiliado tiene el ultimo codigo asignado con fecha de : "+lastEmpresaAfiliadoXCodigoDescuento.getFechaAsignacion());
            lastCodeAsigned = this.codigoDescuentoDao.obtenerCodigoDescuentoPorCodigo(lastEmpresaAfiliadoXCodigoDescuento.getCodigoDescuento().getCodigo());
            lastEmpresaAfiliadoXCodigoDescuento.setCodigoDescuento(lastCodeAsigned);
        }
        return lastEmpresaAfiliadoXCodigoDescuento;
    }
}
