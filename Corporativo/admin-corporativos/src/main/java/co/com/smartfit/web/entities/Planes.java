package co.com.smartfit.web.entities;

public class Planes implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // Campos
    private Integer id;
    private String nombre;
    private Integer periocidad;
    private Integer diasValidacion;

    public Planes() {
        super();
    }

    public Planes(Integer id, String nombre, Integer periocidad,
                  Integer diasValidacion) {
        this.id = id;
        this.nombre = nombre;
        this.periocidad = periocidad;
        this.diasValidacion = diasValidacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPeriocidad() {
        return periocidad;
    }

    public void setPeriocidad(Integer periocidad) {
        this.periocidad = periocidad;
    }

    public Integer getDiasValidacion() {
        return diasValidacion;
    }

    public void setDiasValidacion(Integer diasValidacion) {
        this.diasValidacion = diasValidacion;
    }
}
