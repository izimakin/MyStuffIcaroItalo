package com.mystufficaroitalo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		cv.put("nome_objeto", emprestimo.getNomeObjeto());
		cv.put("data_emprestimo", emprestimo.getDataEmprestimo().toString());
		cv.put("data_devolucao", emprestimo.getDataDevolucao().toString());
		cv.put("telefone_contato", emprestimo.getTelefoneContato());
		cv.put("url_foto", emprestimo.getUrlFoto());
		cv.put("id_usuario", emprestimo.getUsuario().getId());
		cv.put("id_categoria", emprestimo.getCategoria().getId());

		long idEmprestimo = database.getWritableDatabase().insert("Emprestimo", null, cv);

		database.close();

		emprestimo.setId(idEmprestimo);

		return emprestimo;
	}
	
	public void update(Context context, Emprestimo emprestimo) {
		Database database = new Database(context);

		ContentValues cv = new ContentValues();
		cv.put("nome_objeto", emprestimo.getNomeObjeto());
		cv.put("data_emprestimo", emprestimo.getDataEmprestimo().toString());
		cv.put("data_devolucao", emprestimo.getDataDevolucao().toString());
		cv.put("telefone_contato", emprestimo.getTelefoneContato());
		cv.put("url_foto", emprestimo.getUrlFoto());
		cv.put("id_usuario", emprestimo.getUsuario().getId());
		cv.put("id_categoria", emprestimo.getCategoria().getId());

		String[] args = new String[1];
		args[0] = String.valueOf(emprestimo.getId());

		long idEmprestimo = database.getWritableDatabase().update("Emprestimo", cv, "id=?", args);

		database.close();

		emprestimo.setId(idEmprestimo);
	}
	
	public List<Emprestimo> listarEmprestimo(Context context) {
		List<Emprestimo> result = new ArrayList<Emprestimo>();
		Emprestimo emprestimo = null;

		Database database = new Database(context);
		String sql = 
				"SELECT id_emprestimo, " +
					   "nome_objeto, " +
	                   "data_emprestimo, " +
	                   "data_devolucao, " +
	                   "telefone_contato, " +
	                   "url_foto, " +
	                   "id_usuario, " +
	                   "id_categoria " +
	            "FROM Emprestimo;";

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, null);

		while (cursor.moveToNext()) {
			emprestimo = new Emprestimo();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			
			emprestimo.setId(cursor.getLong(0));
			emprestimo.setNomeObjeto(cursor.getString(1));
			emprestimo.setDataEmprestimo(cursor.getString(2));
			emprestimo.setDataDevolucao(cursor.getString(3));
			emprestimo.setTelefoneContato(cursor.getString(4));
			emprestimo.setUrlFoto(cursor.getString(5));
			emprestimo.setUsuario(UsuarioDAO.getInstance().getUsuario(context, cursor.getLong(6)));
			emprestimo.setCategoria(CategoriaDAO.getInstance().getCategoria(context, cursor.getLong(7)));

			result.add(emprestimo);
		}

		cursor.close();
		database.close();

		return result;
	}
	
	public Emprestimo getEmprestimo(Context context, long id) {

		Database database = new Database(context);
		String sql = 
				"SELECT id_emprestimo, " +
						   "nome_objeto, " +
		                   "data_emprestimo, " +
		                   "data_devolucao, " +
		                   "telefone_contato, " +
		                   "url_foto, " +
		                   "id_usuario, " +
		                   "id_categoria " +
	            "FROM Emprestimo " +
	            "where id_emprestimo = ?;";

		String[] args = new String[1];
		args[0] = String.valueOf(id);

		Cursor cursor = database.getReadableDatabase().rawQuery(sql, args);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Emprestimo emprestimo = new Emprestimo();

		emprestimo.setId(cursor.getLong(0));
		emprestimo.setNomeObjeto(cursor.getString(1));
		emprestimo.setDataEmprestimo(cursor.getString(2));
		emprestimo.setDataDevolucao(cursor.getString(3));
		emprestimo.setTelefoneContato(cursor.getString(4));
		emprestimo.setUrlFoto(cursor.getString(5));
		emprestimo.setUsuario(UsuarioDAO.getInstance().getUsuario(context, cursor.getLong(6)));
		emprestimo.setCategoria(CategoriaDAO.getInstance().getCategoria(context, cursor.getLong(7)));

		cursor.close();
		database.close();

		return emprestimo;
	}
}
