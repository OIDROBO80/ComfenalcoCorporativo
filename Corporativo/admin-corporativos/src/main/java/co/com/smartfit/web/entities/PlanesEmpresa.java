package co.com.smartfit.web.entities;

public class PlanesEmpresa implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private Integer idEmpresa;
    private String nombreEmpresa;
    private Integer idPlan;
    private String nombrePlan;


    public  PlanesEmpresa(String id,
                          Integer idEmpresa,
                          Integer idPlan,
                          String nombrePlan) {
        this.id=id;
        this.idEmpresa = idEmpresa;
        this.idPlan = idPlan;
        this.nombrePlan = nombrePlan;

    }

    public PlanesEmpresa() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }
}
