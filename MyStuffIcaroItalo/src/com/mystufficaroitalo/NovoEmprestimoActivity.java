package com.mystufficaroitalo;

import java.util.List;

import com.mystufficaroitalo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NovoEmprestimoActivity extends Activity {
	String nomeObjeto = null;
	String categoria = null;
	String contato = null;
	String dtDevolucao = null;
	
	private void preencherSpinnerCategoria() {
		CategoriaDAO categoriaDao = CategoriaDAO.getInstance();
		List<Categoria> listCategorias = categoriaDao.listarCategorias(this, Usuario.getInstance());
		String[] strListarCategoria = new String[listCategorias.size()];
		for (int i=0; i < strListarCategoria.length; ++i) {
			strListarCategoria[i] = listCategorias.get(i).getNome();
		}
		Spinner s = (Spinner) findViewById(R.id.spinnerCategoria);
		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, strListarCategoria);
		s.setAdapter(adapter);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_emprestimo);
		
		preencherSpinnerCategoria();
		
		Button cadastrarEmprestivo = (Button) findViewById(R.id.cadastrarEmprestimo);
		cadastrarEmprestivo.setOnClickListener(handlerCadastrarEmprestimo);
		
		Button cancelarEmprestimo = (Button) findViewById(R.id.cancelarEmprestimo);
		cancelarEmprestimo.setOnClickListener(handlerCadastrarEmprestimo);
	}
	
	OnClickListener handlerCadastrarEmprestimo = new OnClickListener() {
		@Override
		public void onClick(View v) {
			EditText etNomeObjeto = (EditText) findViewById(R.id.etNomeObjeto);
			nomeObjeto = etNomeObjeto.getText().toString();
			
			Spinner s = (Spinner) findViewById(R.id.spinnerCategoria);
			categoria = etNomeObjeto.getText().toString();
			
			EditText etContato = (EditText) findViewById(R.id.etContato);
			contato = etContato.getText().toString();
			
			EditText etDtDevolucao = (EditText) findViewById(R.id.etDtDevolucao);
			dtDevolucao = etDtDevolucao.getText().toString();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novo_emprestimo, menu);
		return true;
	}

}
