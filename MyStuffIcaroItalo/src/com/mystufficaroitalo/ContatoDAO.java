package com.mystufficaroitalo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ContatoDAO {
	private static ContatoDAO instance;

	private ContatoDAO() {
	}

	public static ContatoDAO getInstance() {
		if (instance == null) {
			instance = new ContatoDAO();
		}

		return instance;
	}

	public boolean delete(Context context, Contato contato) {
		boolean result = false;

		Database database = new Database(context);

		String[] args = new String[1];
		args[0] = String.valueOf(contato.getId());

		database.getWritableDatabase().delete("Contato", "id=?", args);

		database.close();

		return result;
	}

	public Contato inserirContato(Context context, Contato contato) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("numeroTelefone", contato.getNumeroTelefone());
		cv.put("email", contato.getEmail());

		long idContato = database.getWritableDatabase().insert("Contato", null, cv);

		database.close();

		contato.setId(idContato);

		return contato;
	}

	public void update(Context context, Contato contato) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("numeroTelefone", contato.getNumeroTelefone());
		cv.put("email", contato.getEmail());

		String[] args = new String[1];
		args[0] = String.valueOf(contato.getId());

		long idContato = database.getWritableDatabase().update("Contato", cv, "id=?", args);

		database.close();

		contato.setId(idContato);
	}

	public List<Contato> listarContato(Context context) {
		List<Contato> result = new ArrayList<Contato>();
		Contato contato = null;

		Database database = new Database(context);
		String sql = "SELECT id_contato, numero_telefone, email FROM Contato;";

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, null);

		while (cursor.moveToNext()) {
			contato = new Contato();

			contato.setId(cursor.getLong(0));
			contato.setNumeroTelefone(cursor.getString(1));
			contato.setEmail(cursor.getString(2));

			result.add(contato);

		}

		cursor.close();
		database.close();

		return result;
	}
	
	public Contato getContato(Context context, long id) {
		Database database = new Database(context);
		
		String sql = "SELECT id_contato, numero_telefone, email FROM Contato where id_contato = ?;";
		
		String[] args = new String[1];
		args[0] = (Long.valueOf(id)).toString();
		
		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);
		
		Contato contato = new Contato();
		contato.setId(cursor.getLong(0));
		contato.setNumeroTelefone(cursor.getString(1));
		contato.setEmail(cursor.getString(2));
		
		return contato;
	}
}
