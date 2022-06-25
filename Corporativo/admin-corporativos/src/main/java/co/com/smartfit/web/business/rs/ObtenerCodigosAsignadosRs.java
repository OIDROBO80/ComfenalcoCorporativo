package co.com.smartfit.web.business.rs;

import java.util.Date;
import java.util.List;

import co.com.smartfit.web.model.CodigoDescuentoModel;

/**
 * @author alejandro.areiza
 * @since 23/05/2017
 * @version 1.0
 */
public class ObtenerCodigosAsignadosRs {

    // codigo de respuesta que puede indicar el estado de la invocación (200 o 500)
    private String codigoRespuesta;
    // codigo de error que puede indicar el tipo de error adquirido debido a una excepcion
    private String codigoError;
    private Date fechaRespuesta;
    // datos de negocio
    private List<CodigoDescuentoModel> codigosAsignados;
    private String csvCodigosAsignados;

    /**
     * Metodo que obtiene el valor asociado a la propiedad csvCodigosAsignados
     * 
     * @return String el valor asociado a la propiedad csvCodigosAsignados
     */
    public String getCsvCodigosAsignados() {
        return csvCodigosAsignados;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad csvCodigosAsignados
     * 
     * @param {String}
     *            el nuevo valor para la propiedad csvCodigosAsignados
     */
    public void setCsvCodigosAsignados(String csvCodigosAsignados) {
        this.csvCodigosAsignados = csvCodigosAsignados;
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
     * Metodo que obtiene el valor asociado a la propiedad codigosAsignados
     * 
     * @return {List<CodigoDescuentoModel>} el valor asociado a la propiedad codigosAsignados
     */
    public List<CodigoDescuentoModel> getCodigosAsignados() {
        return codigosAsignados;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigosAsignados
     * 
     * @param {List<CodigoDescuentoModel>}
     *            el nuevo valor para la propiedad codigosAsignados
     */
    public void setCodigosAsignados(List<CodigoDescuentoModel> codigosAsignados) {
        this.codigosAsignados = codigosAsignados;
    }

}
