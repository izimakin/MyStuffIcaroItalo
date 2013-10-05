package com.mystufficaroitalo;

import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class EmprestimoService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// Adicionar dados no banco de dados
		Categoria categoria = new Categoria();
		categoria.setNome("Moveis");
		categoria.setUsuario(UsuarioLogado.getInstance().getUsuario());
		
		categoria = CategoriaDAO.getInstance().inserirCategoria(this, categoria);
		
		Emprestimo emprestimo = new Emprestimo();
		
		emprestimo.setNomeObjeto("Cadeira");
		emprestimo.setDataEmprestimo((new Date(System.currentTimeMillis())).toString());
		emprestimo.setDataDevolucao("12/06/2015");
		emprestimo.setTelefoneContato("88124125");
		emprestimo.setUrlFoto("inserirURLFoto");
		emprestimo.setFlagEmprestimo(0);
		emprestimo.setUsuario(UsuarioLogado.getInstance().getUsuario());
		emprestimo.setCategoria(categoria);
		
		emprestimo = EmprestimoDAO.getInstance().inserirEmprestimo(this, emprestimo);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}