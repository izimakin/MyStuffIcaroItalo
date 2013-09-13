package com.mystufficaroitalo;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 8353398218393434035L;
	private long id;
	private String numeroTelefone;
	private String email;
	private String senha;

	public Usuario () {
	}
	
	public Usuario (long id, String numeroTelefone, String email, String senha) {
		this.id = id;
		this.numeroTelefone = numeroTelefone;
		this.email = email;
		this.senha = senha;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}

