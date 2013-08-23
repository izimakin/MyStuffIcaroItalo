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
		append("id INTEGER PRIMARY KEY, ").
		append("id_usuario INTEGER, ").
		append("nome TEXT UNIQUE NOT NULL,").
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
