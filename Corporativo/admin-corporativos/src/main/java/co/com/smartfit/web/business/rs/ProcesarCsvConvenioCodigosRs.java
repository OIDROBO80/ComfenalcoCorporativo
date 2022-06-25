package co.com.smartfit.web.business.rs;

import java.util.Date;
import java.util.List;

import co.com.smartfit.web.model.CodigoDescuentoModel;
/**
 * @author alejandro.areiza
 * @since 20/09/2017
 * @version 1.0
 */
public class ProcesarCsvConvenioCodigosRs {
    // codigo de respuesta que puede indicar el estado de la invocación (200 o 500)
    private String codigoRespuesta;
    // codigo de error que puede indicar el tipo de error adquirido debido a una excepcion
    private String codigoError;
    private Date fechaRespuesta;
    // objetos de negocio
    private List<CodigoDescuentoModel> codigosAsignados;
    private List<CodigoDescuentoModel> codigosError;
    private List<String> mensajesError;
    // CSVs que resumen las listas anteriores
    private String csvCodigosFallidos;
    private String csvCodigosCompletos;

    /**
     * Metodo que obtiene el valor asociado a la propiedad codigosAsignados 
     * @return List<CodigoDescuentoModel> el valor asociado a la propiedad codigosAsignados
     */
    public List<CodigoDescuentoModel> getCodigosAsignados() {
        return codigosAsignados;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigosAsignados 
     * @param {List<CodigoDescuentoModel>} el nuevo valor para la propiedad codigosAsignados
     */
    public void setCodigosAsignados(List<CodigoDescuentoModel> codigosAsignados) {
        this.codigosAsignados = codigosAsignados;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad codigosError
     * 
     * @return List<CodigoDescuentoModel> el valor asociado a la propiedad codigosError
     */
    public List<CodigoDescuentoModel> getCodigosError() {
        return codigosError;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigosError
     * 
     * @param {List<CodigoDescuentoModel>} el nuevo valor para la propiedad codigosError
     */
    public void setAfiliadosError(List<CodigoDescuentoModel> codigosError) {
        this.codigosError = codigosError;
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
     * Metodo que obtiene el valor asociado a la propiedad csvCodigosFallidos
     * 
     * @return String el valor asociado a la propiedad csvCodigosFallidos
     */
    public String getCsvCodigosFallidos() {
        return csvCodigosFallidos;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad csvCodigosFallidos
     * 
     * @param {String} el nuevo valor para la propiedad csvCodigosFallidos
     */
    public void setCsvCodigosFallidos(String csvCodigosFallidos) {
        this.csvCodigosFallidos = csvCodigosFallidos;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad csvCodigosCompletos
     * 
     * @return String el valor asociado a la propiedad csvCodigosCompletos
     */
    public String getCsvCodigosCompletos() {
        return csvCodigosCompletos;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad csvCodigosCompletos
     * 
     * @param {String} el nuevo valor para la propiedad csvCodigosCompletos
     */
    public void setCsvCodigosCompletos(String csvCodigosCompletos) {
        this.csvCodigosCompletos = csvCodigosCompletos;
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
     * @return String el valor asociado a la propiedad codigoRespuesta
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
