package co.com.smartfit.web.entities;

// Generated 12-may-2017 13:55:34 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;


public class Users implements java.io.Serializable {

    private Integer idUser;
    private UserRoles userRoles;
    private String username;
    private String password;
    private byte enabled;
    private Set empresas = new HashSet(0);

    public Users() {
        super();
    }

    public Users(UserRoles userRoles, String username, String password, byte enabled) {
        this.userRoles = userRoles;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public Users(UserRoles userRoles, String username, String password, byte enabled, Set empresas) {
        this.userRoles = userRoles;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.empresas = empresas;
    }

    public Integer getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public UserRoles getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getEnabled() {
        return this.enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public Set getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set empresas) {
        this.empresas = empresas;
    }

}
