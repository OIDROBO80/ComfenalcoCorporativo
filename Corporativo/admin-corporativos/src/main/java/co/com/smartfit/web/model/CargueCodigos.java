package co.com.smartfit.web.model;

public class CargueCodigos {
    private String codigo;
    private String nombrePlan;
    public CargueCodigos(String codigo, String nombrePlan){
        this.codigo = codigo;
        this.nombrePlan = nombrePlan;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }
}
