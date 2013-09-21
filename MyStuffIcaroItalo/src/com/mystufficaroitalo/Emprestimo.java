package com.mystufficaroitalo;

import java.util.Date;

public class Emprestimo {
	private long id;
	private String nomeObjeto;
	private String dataEmprestimo;
	private String dataDevolucao;
	private String telefoneContato;
	private String urlFoto;
	private Usuario usuario;
	private Categoria categoria;
	
	public Emprestimo() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNomeObjeto() {
		return nomeObjeto;
	}

	public void setNomeObjeto(String nomeObjeto) {
		this.nomeObjeto = nomeObjeto;
	}
	
	public String getDataEmprestimo() {
		return dataEmprestimo;
	}
	
	public void setDataEmprestimo(String date) {
		this.dataEmprestimo = date;
	}
	
	public String getDataDevolucao() {
		return dataDevolucao;
	}
	
	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
