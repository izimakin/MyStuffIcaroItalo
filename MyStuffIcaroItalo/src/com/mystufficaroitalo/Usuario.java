package com.mystufficaroitalo;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 8353398218393434035L;
	private static Usuario usuario = null;
	private long id;
	private String numeroTelefone;
	private String email;
	private String senha;

	private Usuario () {
	}
	
	public static Usuario getInstance() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
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

