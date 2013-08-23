package com.mystufficaroitalo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoriaDAO {

	private static CategoriaDAO instance;

	private CategoriaDAO() {
	}

	public static CategoriaDAO getInstance() {
		if (instance == null) {
			instance = new CategoriaDAO();
		}

		return instance;
	}

	public boolean delete(Context context, Categoria categoria) {
		boolean result = false;

		Database database = new Database(context);

		String[] args = new String[1];
		args[0] = String.valueOf(categoria.getId());

		database.getWritableDatabase().delete("Categoria", "id=?", args);

		database.close();

		return result;
	}

	public Categoria inserirCategoria(Context context, Categoria categoria) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("nome", categoria.getNome());
		cv.put("id_usuario", categoria.getUsuario().getId());

		long idCategoria = database.getWritableDatabase().insert("Categoria", null, cv);

		database.close();

		categoria.setId(idCategoria);

		return categoria;
	}

	public void update(Context context, Categoria categoria) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("nome_categoria", categoria.getNome());

		String[] args = new String[1];
		args[0] = String.valueOf(categoria.getId());

		long idCategoria = database.getWritableDatabase().update("Categoria", cv, "id=?", args);

		database.close();

		categoria.setId(idCategoria);
	}

	public List<Categoria> listarCategorias(Context context, Usuario usuario) {
		List<Categoria> result = new ArrayList<Categoria>();
		Categoria categoria = null;

		Database database = new Database(context);
		String sql = "SELECT id, nome FROM Categoria where id_usuario = ?;";

		String[] args = new String[1];
		args[0] = String.valueOf(usuario.getId());

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);

		while (cursor.moveToNext()) {
			categoria = new Categoria();

			categoria.setId(cursor.getLong(0));
			categoria.setNome(cursor.getString(1));

			result.add(categoria);

		}

		cursor.close();
		database.close();

		return result;
	}
}
