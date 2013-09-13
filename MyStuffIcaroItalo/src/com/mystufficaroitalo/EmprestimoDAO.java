package com.mystufficaroitalo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class EmprestimoDAO {
	private static EmprestimoDAO instance;
	
	private EmprestimoDAO () {
	}
	
	public static EmprestimoDAO getInstance() {
		if (instance == null)
			instance = new EmprestimoDAO();
		return instance;
	}
	
	public boolean delete(Context context, Emprestimo emprestimo) {
		boolean result = false;
		
		Database database = new Database(context);
		
		String[] args = new String[1];
		args[0] = String.valueOf(emprestimo.getId());
		
		database.getWritableDatabase().delete("Emprestimo", "id=?", args);

		database.close();

		return result;
	}
	
	public Emprestimo inserirEmprestimo(Context context, Emprestimo emprestimo) {
		Database database = new Database(context);
		
		ContentValues cv = new ContentValues();
		cv.put("data_emprestimo", emprestimo.getDataEmprestimo().toString());
		cv.put("data_devolucao", emprestimo.getDataDevolucao().toString());
		cv.put("id_usuario", emprestimo.getUsuario().getId());
		cv.put("id_objeto", emprestimo.getObjeto().getId());
		cv.put("id_contato", emprestimo.getContato().getId());

		long idEmprestimo = database.getWritableDatabase().insert("Emprestimo", null, cv);

		database.close();

		emprestimo.setId(idEmprestimo);

		return emprestimo;
	}
	
	public void update(Context context, Emprestimo emprestimo) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("data_emprestimo", emprestimo.getDataEmprestimo().toString());
		cv.put("data_devolucao", emprestimo.getDataDevolucao().toString());
		cv.put("id_usuario", emprestimo.getUsuario().getId());
		cv.put("id_objeto", emprestimo.getObjeto().getId());
		cv.put("id_contato", emprestimo.getContato().getId());

		String[] args = new String[1];
		args[0] = String.valueOf(emprestimo.getId());

		long idEmprestimo = database.getWritableDatabase().update("Emprestimo", cv, "id=?", args);

		database.close();

		emprestimo.setId(idEmprestimo);
	}
	
	public List<Emprestimo> listarEmprestimo(Context context, Usuario usuario) {
		List<Emprestimo> result = new ArrayList<Emprestimo>();
		Emprestimo emprestimo = null;

		Database database = new Database(context);
		String sql = 
				"SELECT id_emprestimo," +
	                   "data_emprestimo, " +
	                   "data_devolucao, " +
	                   "id_usuario, " +
	                   "id_objeto, " +
	                   "id_contato " +
	            "FROM Emprestimo " +
	            "where id_usuario = ?;";

		String[] args = new String[1];
		args[0] = String.valueOf(usuario.getId());

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);

		while (cursor.moveToNext()) {
			emprestimo = new Emprestimo();

			emprestimo.setId(cursor.getLong(0));
			emprestimo.setDataEmprestimo(cursor.getString(1));
			emprestimo.setDataDevolucao(cursor.getString(2));
			emprestimo.setUsuario(UsuarioDAO.getInstance().getUsuario(context, cursor.getLong(3)));
			emprestimo.setObjeto(ObjetoDAO.getInstance().getObjeto(context, cursor.getLong(4)));
			emprestimo.setContato(ContatoDAO.getInstance().getContato(context, cursor.getLong(5)));

			result.add(emprestimo);
		}

		cursor.close();
		database.close();

		return result;
	}
	
	public List<Emprestimo> getEmprestimo(Context context, long id) {
		List<Emprestimo> result = new ArrayList<Emprestimo>();

		Database database = new Database(context);
		String sql = 
				"SELECT id_emprestimo," +
	                   "data_emprestimo, " +
	                   "data_devolucao, " +
	                   "id_usuario, " +
	                   "id_objeto, " +
	                   "id_contato " +
	            "FROM Emprestimo " +
	            "where id_emprestimo = ?;";

		String[] args = new String[1];
		args[0] = String.valueOf(id);

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);

		
		Emprestimo emprestimo = new Emprestimo();

		emprestimo.setId(cursor.getLong(0));
		emprestimo.setDataEmprestimo(cursor.getString(1));
		emprestimo.setDataDevolucao(cursor.getString(2));
		emprestimo.setUsuario(UsuarioDAO.getInstance().getUsuario(context, cursor.getLong(3)));
		emprestimo.setObjeto(ObjetoDAO.getInstance().getObjeto(context, cursor.getLong(4)));
		emprestimo.setContato(ContatoDAO.getInstance().getContato(context, cursor.getLong(5)));

		cursor.close();
		database.close();

		return result;
	}
}
