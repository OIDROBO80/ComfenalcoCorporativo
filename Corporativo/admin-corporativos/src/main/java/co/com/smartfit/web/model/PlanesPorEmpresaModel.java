package co.com.smartfit.web.model;

import java.io.Serializable;

public class PlanesPorEmpresaModel implements Serializable {

    private static final long serialVersionUID = 1L;
    String id;
    Integer idEmpresa;


    public  PlanesPorEmpresaModel(String id,int idEmpresa) {
        this.id = id;
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
