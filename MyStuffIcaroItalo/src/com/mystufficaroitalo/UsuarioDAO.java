package com.mystufficaroitalo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDAO {

	private static UsuarioDAO instance;

	private UsuarioDAO() {
	}

	public static UsuarioDAO getInstance() {

		if (instance == null) {
			instance = new UsuarioDAO();
		}

		return instance;
	}

	public boolean delete(Context context, Usuario usuario) {
		boolean result = false;

		Database database = new Database(context);

		String[] args = new String[1];
		args[0] = String.valueOf(usuario.getId());

		database.getWritableDatabase().delete("Usuario", "id=?", args);

		database.close();

		return result;
	}

	public Usuario insert(Context context, Usuario usuario) {
		if(!exists(context, usuario.getId())) {
			Database database = new Database(context);
			
			ContentValues cv = new ContentValues();
			cv.put("id_usuario", usuario.getId());
			cv.put("numeroTelefone", usuario.getNumeroTelefone());
			cv.put("email", usuario.getEmail());

			SQLiteDatabase db = database.getWritableDatabase();
			
			db.beginTransaction();
			try {
				long idUsuario = db.insert("Usuario", null, cv);
				usuario.setId(idUsuario);

				db.setTransactionSuccessful();
				
			} catch(Exception e) {
				Log.e("BD", e.getMessage());
			} finally {
				db.endTransaction();
			}

			database.close();	
		}
		
		return usuario;
	}
	
	public boolean exists(Context context, long id) {
		boolean result = false;
		
		Database database = new Database(context);
		String sql = "SELECT * FROM Usuario where id_usuario = ?;";

		String[] args = new String[1];
		args[0] = String.valueOf(id);
		
		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);
		
		result = cursor.moveToNext();

		cursor.close();
		database.close();
		
		return result;
	}

	public void update(Context context, Usuario usuario) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("email", usuario.getEmail());

		String[] args = new String[1];
		args[0] = String.valueOf(usuario.getId());

		long idAluno = database.getWritableDatabase().update("Usuario", cv, "id=?", args);

		database.close();

		usuario.setId(idAluno);
	}
	
	public Usuario getUsuario(Context context, long id) {
		Database database = new Database(context);
		
		String sql = "SELECT id_usuario, numeroTelefone, email FROM Usuario where id_usuario = ?;";
		
		String[] args = new String[1];
		args[0] = (Long.valueOf(id)).toString();
		
		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);
		
		Usuario usuario = new Usuario();
		usuario.setId(cursor.getLong(0));
		usuario.setNumeroTelefone(cursor.getString(1));
		usuario.setEmail(cursor.getString(2));
		
		return usuario;
	}
}