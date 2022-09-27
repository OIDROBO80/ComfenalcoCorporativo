package co.com.smartfit.web.model;

import java.util.Date;


public class CodigoDescuentoModel {

    private Integer id;
    private String codigo;
    private Boolean asignado;
    private Integer idEmpresaPlan;

    // Necesario para el front
    private String periodicidad;
    private Date fechaAsignacion;

    private EmpresaAfiliadoModel afiliado;
    private EmpresaEmpleadorXPlanModel EmpresaPlan;
    //private EmpresaEmpleadorModel empresaEmpleador;
    
    /**
     * Metodo que obtiene el valor asociado a la propiedad afiliado
     * 
     * @return EmpresaAfiliadoModel el valor asociado a la propiedad afiliado
     */
    public EmpresaAfiliadoModel getAfiliado() {
        return afiliado;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad afiliado
     * 
     * @param {EmpresaAfiliadoModel}
     *            el nuevo valor para la propiedad afiliado
     */
    public void setAfiliado(EmpresaAfiliadoModel afiliado) {
        this.afiliado = afiliado;
    }
    
    /**
     * Metodo que obtiene el valor asociado a la propiedad empresaEmpleador
     * 
     * @return {EmpresaEmpleadorModel} el valor asociado a la propiedad empresaEmpleador
     */
    /*public EmpresaEmpleadorModel getEmpresaEmpleador() {
        return empresaEmpleador;
    }*/

    /**
     * Metodo que permite modificar el valor de la propiedad empresaEmpleador
     * 
     * @param {EmpresaEmpleadorModel}
     *            el nuevo valor para la propiedad empresaEmpleador
     */
    /*public void setEmpresaEmpleador(EmpresaEmpleadorModel empresaEmpleador) {
        this.empresaEmpleador = empresaEmpleador;
    }*/

    /**
     * Metodo que obtiene el valor asociado a la propiedad id
     * 
     * @return Integer el valor asociado a la propiedad id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad id
     * 
     * @param {Integer}
     *            el nuevo valor para la propiedad id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad codigo
     * 
     * @return String el valor asociado a la propiedad codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad codigo
     * 
     * @param {String}
     *            el nuevo valor para la propiedad codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad asignado
     * 
     * @return Boolean el valor asociado a la propiedad asignado
     */
    public Boolean getAsignado() {
        return asignado;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad asignado
     * 
     * @param {Boolean}
     *            el nuevo valor para la propiedad asignado
     */
    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

    /**
     * Metodo que obtiene el valor asociado a la propiedad fechaAsignacion
     * 
     * @return Date el valor asociado a la propiedad fechaAsignacion
     */
    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * Metodo que permite modificar el valor de la propiedad fechaAsignacion
     * 
     * @param {Date}
     *            el nuevo valor para la propiedad fechaAsignacion
     */
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getPeriodicidad() { return periodicidad; }

    public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }

    public Integer getIdEmpresaPlan() {
        return idEmpresaPlan;
    }

    public void setIdEmpresaPlan(Integer idEmpresaPlan) {
        this.idEmpresaPlan = idEmpresaPlan;
    }

    public EmpresaEmpleadorXPlanModel getEmpresaPlan() {
        return EmpresaPlan;
    }

    public void setEmpresaPlan(EmpresaEmpleadorXPlanModel empresaPlan) {
        EmpresaPlan = empresaPlan;
    }

}
