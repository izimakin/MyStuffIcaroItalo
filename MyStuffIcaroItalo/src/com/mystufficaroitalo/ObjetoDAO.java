package com.mystufficaroitalo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ObjetoDAO {
	private static ObjetoDAO instance;
	
	private ObjetoDAO () {
	}
	
	public static ObjetoDAO getInstance() {
		if (instance == null)
			instance = new ObjetoDAO();
		return instance;
	}
	
	public boolean delete(Context context, Objeto objeto) {
		boolean result = false;
		
		Database database = new Database(context);
		
		String[] args = new String[1];
		args[0] = String.valueOf(objeto.getId());
		
		database.getWritableDatabase().delete("Objeto", "id=?", args);

		database.close();

		return result;
	}
	
	public Objeto inserirObjeto(Context context, Objeto objeto) {
		Database database = new Database(context);
		
		ContentValues cv = new ContentValues();
		cv.put("nome", objeto.getNome());
		cv.put("id_categoria", objeto.getCategoria().getId());

		long idObjeto = database.getWritableDatabase().insert("Objeto", null, cv);

		database.close();

		objeto.setId(idObjeto);

		return objeto;
	}
	
	public void update(Context context, Objeto objeto) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("nome_objeto", objeto.getNome());

		String[] args = new String[1];
		args[0] = String.valueOf(objeto.getId());

		long idObjeto = database.getWritableDatabase().update("Objeto", cv, "id=?", args);

		database.close();

		objeto.setId(idObjeto);
	}
	
	public List<Objeto> listarObjetos(Context context, Categoria categoria) {
		List<Objeto> result = new ArrayList<Objeto>();

		Database database = new Database(context);
		String sql = "SELECT id_objeto, nome_objeto, id_categoria FROM Objeto where id_categoria = ?;";

		String[] args = new String[1];
		args[0] = String.valueOf(categoria.getId());

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);

		while (cursor.moveToNext()) {
			Objeto objeto = new Objeto();
			objeto.setId(cursor.getLong(0));
			objeto.setNome(cursor.getString(1));
			objeto.setCategoria(CategoriaDAO.getInstance().getCategoria(context, cursor.getLong(2)));
			
			result.add(objeto);

		}

		cursor.close();
		database.close();

		return result;
	}
	
	public Objeto getObjeto(Context context, long id) {
		Database database = new Database(context);
		
		String sql = "SELECT id_objeto, nome_objeto, id_categoria FROM Objeto where id_objeto = ?;";
		
		String[] args = new String[1];
		args[0] = (Long.valueOf(id)).toString();
		
		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);
		
		Objeto objeto = new Objeto();
		objeto.setId(cursor.getLong(0));
		objeto.setNome(cursor.getString(1));
		objeto.setCategoria(CategoriaDAO.getInstance().getCategoria(context, cursor.getLong(2)));
		
		return objeto;
	}
}
