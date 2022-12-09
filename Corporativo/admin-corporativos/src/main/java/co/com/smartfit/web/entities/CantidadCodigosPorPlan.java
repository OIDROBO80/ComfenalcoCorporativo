package co.com.smartfit.web.entities;

public class CantidadCodigosPorPlan implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private Integer cantidadDeCodigos;
    private String nombrePlan;
    private String documentoEmpresa;
    private Boolean asignado;

    public CantidadCodigosPorPlan(String id
            ,Integer cantidadDeCodigos
            ,String nombrePlan
            ,String documentoEmpresa
            ,Boolean asignado) {
        this.id=id;
        this.cantidadDeCodigos=cantidadDeCodigos;
        this.nombrePlan=nombrePlan;
        this.documentoEmpresa=documentoEmpresa;
        this.asignado=asignado;
    }

    public CantidadCodigosPorPlan() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCantidadDeCodigos() {
        return cantidadDeCodigos;
    }

    public void setCantidadDeCodigos(Integer cantidadDeCodigos) {
        this.cantidadDeCodigos = cantidadDeCodigos;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getDocumentoEmpresa() {
        return documentoEmpresa;
    }

    public void setDocumentoEmpresa(String documentoEmpresa) {
        this.documentoEmpresa = documentoEmpresa;
    }

    public Boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }
}
