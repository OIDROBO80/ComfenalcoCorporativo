package com.corporativos_smartfit.dto;


public class AffiliateRequest extends AffiliateDocumentRequest  {
    private String name;
    private String email;
    private String periodo;
    private int tipoDocumentEmpresaEmpleador;
    private String nDocumentoEmpresaEmpleador;
    private int idMembresiaEmpresaEmpleador;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getTipoDocumentEmpresaEmpleador() {
        return tipoDocumentEmpresaEmpleador;
    }

    public void setTipoDocumentEmpresaEmpleador(int tipoDocumentEmpresaEmpleador) {
        this.tipoDocumentEmpresaEmpleador = tipoDocumentEmpresaEmpleador;
    }

    public String getnDocumentoEmpresaEmpleador() {
        return nDocumentoEmpresaEmpleador;
    }

    public void setnDocumentoEmpresaEmpleador(String nDocumentoEmpresaEmpleador) {
        this.nDocumentoEmpresaEmpleador = nDocumentoEmpresaEmpleador;
    }

    public int getIdMembresiaEmpresaEmpleador() {
        return idMembresiaEmpresaEmpleador;
    }

    public void setIdMembresiaEmpresaEmpleador(int idMembresiaEmpresaEmpleador) {
        this.idMembresiaEmpresaEmpleador = idMembresiaEmpresaEmpleador;
    }
}
