package com.mystufficaroitalo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

	public Database(Context context) {
		super(context, "MYSTUFFICAROITALO", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(usuarioTable());
		db.execSQL(categoriaTable());
	}

	private String categoriaTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE Categoria (").
		append("id_categoria INTEGER PRIMARY KEY, ").
		append("id_usuario INTEGER, ").
		append("nome_categoria TEXT UNIQUE NOT NULL,").
		append("FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario)").
		append(");");
		
		return sql.toString();
	}

	private String usuarioTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE Usuario (").
		append("id_usuario INTEGER PRIMARY KEY, ").
		append("numeroTelefone TEXT UNIQUE NOT NULL, ").
		append("email TEXT UNIQUE NOT NULL ").
		append(");");
		return sql.toString();
	}
	
	private String emprestimoTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE Emprestimo (").
		append("id_emprestimo INTEGER PRIMARY KEY, ").
		append("data_emprestimo TEXT NOT NULL,").
		append("data_devolucao TEXT NOT NULL,").
		append("FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario),").
		append("FOREIGN KEY (id_objeto) REFERENCES Categoria (id_objeto),").
		append("FOREIGN KEY (id_contato) REFERENCES Contato (id_contato)").
		append(");");
		return sql.toString();
	}
	
	private String objetoTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE Objeto (").
		append("id_objeto INTEGER PRIMARY KEY,").
		append("nome_objeto TEXT UNIQUE NOT NULL,").
		append("FOREIGN KEY (id_categoria) REFERENCES Categoria (id_categoria)").
		append(");");
		return sql.toString();
	}
	
	private String contatoTable() {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE Contato (").
		append("id_contato INTEGER PRIMARY KEY,").
		append("numeroTelefone TEXT UNIQUE NOT NULL,").
		append("email TEXT UNIQUE NOT NULL").
		append(");");
		return sql.toString();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(dropTable("Usuario"));
			db.execSQL(dropTable("Categoria"));
		} catch (Exception e) {
			Log.i("INFO", e.getMessage());
		}
	}

	private String dropTable(String nomeTable) {
		return "DROP TABLE IF EXISTS " + nomeTable;
	}
}
