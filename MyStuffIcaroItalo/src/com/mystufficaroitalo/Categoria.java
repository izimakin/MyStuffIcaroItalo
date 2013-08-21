package com.mystufficaroitalo;

public class Categoria {
	
	private long id;
	private String nome;
	
	public Categoria(long _id, String _nome){
		
		nome = _nome;
		id = _id;
		
	}
	
	public Categoria(String _nome){
		
		nome = _nome;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
