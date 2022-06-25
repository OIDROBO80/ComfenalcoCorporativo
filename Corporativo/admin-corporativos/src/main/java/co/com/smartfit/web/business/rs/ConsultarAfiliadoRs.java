package co.com.smartfit.web.business.rs;


import co.com.smartfit.web.model.AfiliadoModel;
/**
 * @author alejandro.areiza
 * @since 23/05/2017
 * @version 1.0
 */
public class ConsultarAfiliadoRs {

    private String codigoRespuesta;
    private AfiliadoModel afiliado;

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
     * Metodo que obtiene el valor asociado a la propiedad afiliado
     * 
     * @return {AfiliadoModel} el valor asociado a la propiedad afiliado
     */
    public AfiliadoModel getAfiliado() {
        return afiliado;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad afiliado
     * 
     * @param {AfiliadoModel}
     *            el nuevo valor para la propiedad afiliado
     */
    public void setAfiliado(AfiliadoModel afiliado) {
        this.afiliado = afiliado;
    }
}
