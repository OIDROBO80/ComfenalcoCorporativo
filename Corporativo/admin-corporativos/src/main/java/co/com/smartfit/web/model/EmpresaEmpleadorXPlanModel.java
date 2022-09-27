package co.com.smartfit.web.model;


import co.com.smartfit.web.dao.EmpresaEmpleadorDao;
import co.com.smartfit.web.dao.PlanesDao;
import co.com.smartfit.web.entities.EmpresaEmpleador;
import co.com.smartfit.web.entities.Planes;

public class EmpresaEmpleadorXPlanModel implements java.io.Serializable {


    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idEmpresaEmpleador;
    private Integer idPlan;

    // Relaciones
    private EmpresaEmpleadorDao empresaEmpleadorDao = new EmpresaEmpleadorDao();
    private EmpresaEmpleador empresaEmpleador;
    private PlanesDao planesDao = new PlanesDao();
    private Planes plan;

    public EmpresaEmpleadorXPlanModel() {
        super();
    }

    public EmpresaEmpleadorXPlanModel(Integer idEmpresaEmpleador,
                                      Integer idPlan ) {
         this.idEmpresaEmpleador = idEmpresaEmpleador;
         this.idPlan = idPlan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEmpresaEmpleador() {
        return idEmpresaEmpleador;
    }

    public void setIdEmpresaEmpleador(Integer idEmpresaEmpleador) {
        this.idEmpresaEmpleador = idEmpresaEmpleador;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public EmpresaEmpleador getEmpresaEmpleador() {
        return this.empresaEmpleadorDao.obtenerEmpresaEmpleadorPorId(this.idEmpresaEmpleador);
    }

    public void setEmpresaEmpleador(EmpresaEmpleador empresaEmpleador) {
        this.empresaEmpleador = empresaEmpleador;
    }

    public Planes getPlan() {
        return this.planesDao.obtenerPlanById(this.idPlan);
    }

    public void setPlan(Planes idPlan) {
        this.plan = plan;
    }
}
