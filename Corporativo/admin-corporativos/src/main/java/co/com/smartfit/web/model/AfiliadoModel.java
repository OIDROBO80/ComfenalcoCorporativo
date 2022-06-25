package co.com.smartfit.web.model;

import java.io.Serializable;

public class AfiliadoModel implements Serializable {

    private static final long serialVersionUID = 1L;
    String tipoDoc;
    int idtipoDoc;
    String numeroDoc;
    String nombre;
    String codigo;
    String estado;
    String tipoTarifa;
    String eMail;

    public AfiliadoModel(String tipoDoc, int idtipoDoc, String numeroDoc, String nombre, String codigo, String estado, String tipoTarifa,
            String eMail) {
        super();
        this.tipoDoc = tipoDoc;
        this.idtipoDoc = idtipoDoc;
        this.numeroDoc = numeroDoc;
        this.nombre = nombre;
        this.codigo = codigo;
        this.estado = estado;
        this.tipoTarifa = tipoTarifa;
        this.eMail = eMail;
    }

    public AfiliadoModel() {
        super();
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getIdtipoDoc() {
        return idtipoDoc;
    }

    public void setIdtipoDoc(int idtipoDoc) {
        this.idtipoDoc = idtipoDoc;
    }

}
