package com.mystufficaroitalo;

public class Categoria {

	private long id;
	private String nome;
	private Usuario usuario;

	public Categoria() {
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public String toString() {
		return this.getNome();
	}

}
