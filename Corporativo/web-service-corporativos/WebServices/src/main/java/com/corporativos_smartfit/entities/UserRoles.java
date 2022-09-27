package com.corporativos_smartfit.entities;

import java.util.HashSet;
import java.util.Set;

public class UserRoles implements java.io.Serializable {

	private Integer userRoleId;
	private String username;
	private String role;
	private Set userses = new HashSet(0);

	public UserRoles() {
		super();
	}

	public UserRoles(String username, String role) {
		this.username = username;
		this.role = role;
	}

	public UserRoles(String username, String role, Set userses) {
		this.username = username;
		this.role = role;
		this.userses = userses;
	}

	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set getUserses() {
		return this.userses;
	}

	public void setUserses(Set userses) {
		this.userses = userses;
	}

}
