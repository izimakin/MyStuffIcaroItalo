package com.mystufficaroitalo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoriaDao {
	private static CategoriaDao categoriaDao = null;

	protected SQLiteDatabase db = null;

	private static final String CREATE_TABLE_CATEGORIA = "CREATE TABLE categoria ("
			+ "idCategoria INTEGER primary key autoincrement,"
			+ "nome varchar(255)" + ");";
	private static final String DROP_TABLE_CATEGORIA =
			"DROP TABLE IF EXISTS 'categoria'";

	private CategoriaDao(Context ctx) {
		db = ctx.openOrCreateDatabase("mystuff", Context.MODE_PRIVATE, null);
		if (! isTableExists("categoria")) {
			db.execSQL(CREATE_TABLE_CATEGORIA);
		}

	}
	
	public static CategoriaDao getInstance(Context ctx) {
		if (categoriaDao == null) {
			categoriaDao = new CategoriaDao(ctx);
		}
		return categoriaDao;
	}

	public long inserirCategoria(Categoria cat) {

		ContentValues values = new ContentValues();

		values.put("nome", cat.getNome());
		return db.insert("categoria", "", values);

	}

	public int atualizarCategoria(Categoria cat) {

		ContentValues values = new ContentValues();

		values.put("Nome", cat.getNome());

		String id = String.valueOf(cat.getId());
		String where = "idCategoria =?";
		String whereArgs[] = new String[] { id };
		int count = db.update("categoria", values, where, whereArgs);

		return count;

	}

	public List<Categoria> listarCategorias() {

		Cursor c = getCursor();
		List<Categoria> list = new ArrayList<Categoria>();

		if (c.moveToFirst()) {

			int idxId = c.getColumnIndex("idCategoria");
			int idxNome = c.getColumnIndex("nome");

			do {

				Categoria cat = new Categoria(c.getLong(idxId),
						c.getString(idxNome));
				list.add(cat);

			} while (c.moveToNext());

		}

		return list;

	}

	public Cursor getCursor() {

		try {

			return db.query(true, "categoria", null, null, null, null, null,
					null, null, null);

		} catch (SQLException e) {
			Log.e("ERRO FEIO", "Erro ao buscar categorias");
			return null;
		}

	}

	public boolean isTableExists(String tableName) {

		Cursor c = null;
		try {
			c = db.query(tableName, null, null, null, null, null, null);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
