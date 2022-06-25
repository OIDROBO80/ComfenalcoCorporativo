package co.com.smartfit.web.business.rs;

import java.util.Date;
import java.util.List;

import co.com.smartfit.web.model.EmpresaAfiliadoModel;

/**
 * @author alejandro.areiza
 * @since 20/09/2017
 * @version 1.0
 */
public class ProcesarCsvConvenioAfiliadosRs {
    // codigo de respuesta que puede indicar el estado de la invocación (200 o 500)
    private String codigoRespuesta;
    // codigo de error que puede indicar el tipo de error adquirido debido a una excepcion
    private String codigoError;
    private Date fechaRespuesta;
    // objetos de negocio
    private List<EmpresaAfiliadoModel> afiliadosAsignados;
    private List<String> codigosAsignados;
    private List<EmpresaAfiliadoModel> afiliadosError;
    private List<String> mensajesError;
    // CSVs que resumen las listas anteriores
    private String csvAfiliadosFallidos;
    private String csvAfiliadosCompletos;

    /**
     * Metodo que obtiene el valor asociado a la propiedad afiliadosAsignados
     * 
     * @return List<EmpresaAfiliadoModel> el valor asociado a la propiedad afiliadosAsignados
     */
    public List<EmpresaAfiliadoModel> getAfiliadosAsignados() {
        return afiliadosAsignados;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad afiliadosAsignados
     * 
     * @param {List<EmpresaAfiliadoModel>}
     *            el nuevo valor para la propiedad afiliadosAsignados
     */
    public void setAfiliadosAsignados(List<EmpresaAfiliadoModel> afiliadosAsignados) {
        this.afiliadosAsignados = afiliadosAsignados;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad codigosAsignados
     * 
     * @return List<String> el valor asociado a la propiedad codigosAsignados
     */
    public List<String> getCodigosAsignados() {
        return codigosAsignados;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigosAsignados
     * 
     * @param {List<String>}
     *            el nuevo valor para la propiedad codigosAsignados
     */
    public void setCodigosAsignados(List<String> codigosAsignados) {
        this.codigosAsignados = codigosAsignados;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad afiliadosError
     * 
     * @return List<EmpresaAfiliadoModel> el valor asociado a la propiedad afiliadosError
     */
    public List<EmpresaAfiliadoModel> getAfiliadosError() {
        return afiliadosError;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad afiliadosError
     * 
     * @param {List<EmpresaAfiliadoModel>}
     *            el nuevo valor para la propiedad afiliadosError
     */
    public void setAfiliadosError(List<EmpresaAfiliadoModel> afiliadosError) {
        this.afiliadosError = afiliadosError;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad mensajesError
     * 
     * @return List<String> el valor asociado a la propiedad mensajesError
     */
    public List<String> getMensajesError() {
        return mensajesError;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad mensajesError
     * 
     * @param {List<String>}
     *            el nuevo valor para la propiedad mensajesError
     */
    public void setMensajesError(List<String> mensajesError) {
        this.mensajesError = mensajesError;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad csvAfiliadosFallidos
     * 
     * @return String el valor asociado a la propiedad csvAfiliadosFallidos
     */
    public String getCsvAfiliadosFallidos() {
        return csvAfiliadosFallidos;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad csvAfiliadosFallidos
     * 
     * @param {String}
     *            el nuevo valor para la propiedad csvAfiliadosFallidos
     */
    public void setCsvAfiliadosFallidos(String csvAfiliadosFallidos) {
        this.csvAfiliadosFallidos = csvAfiliadosFallidos;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad csvAfiliadosCompletos
     * 
     * @return String el valor asociado a la propiedad csvAfiliadosCompletos
     */
    public String getCsvAfiliadosCompletos() {
        return csvAfiliadosCompletos;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad csvAfiliadosCompletos
     * 
     * @param {String}
     *            el nuevo valor para la propiedad csvAfiliadosCompletos
     */
    public void setCsvAfiliadosCompletos(String csvAfiliadosCompletos) {
        this.csvAfiliadosCompletos = csvAfiliadosCompletos;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad fechaRespuesta
     * 
     * @return Date el valor asociado a la propiedad fechaRespuesta
     */
    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad fechaRespuesta
     * 
     * @param {Date}
     *            el nuevo valor para la propiedad fechaRespuesta
     */
    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad codigoRespuesta
     * 
     * @return {String} el valor asociado a la propiedad codigoRespuesta
     */
    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigoRespuesta
     * 
     * @param {String}
     *            el nuevo valor para la propiedad codigoRespuesta
     */
    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad codigoError
     * 
     * @return String el valor asociado a la propiedad codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigoError
     * 
     * @param {String}
     *            el nuevo valor para la propiedad codigoError
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }
}
