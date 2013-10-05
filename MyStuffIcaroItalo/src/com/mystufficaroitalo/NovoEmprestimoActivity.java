package com.mystufficaroitalo;

import java.util.Date;
import java.util.List;

import com.mystufficaroitalo.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NovoEmprestimoActivity extends Activity implements OnItemSelectedListener {
	private String nomeObjeto = null;
	private Categoria categoria = null;
	private String contato = null;
	private String dtDevolucao = null;
	private Context context = null;
	private boolean edit;
	private long id;
	
	//Elementos gráficos
	EditText etNomeObjeto;
	Spinner s;
	EditText etContato;
	EditText etDtDevolucao;
	CharSequence etEmprestimo;
	CharSequence urlFoto;
	Usuario usuario;
	Categoria categoriaObj;
	
	private void preencherSpinnerCategoria() {
		CategoriaDAO categoriaDao = CategoriaDAO.getInstance();
		List<Categoria> listCategorias = categoriaDao.listarCategorias(this, UsuarioLogado.getInstance().getUsuario());
		Spinner s = (Spinner) findViewById(R.id.spinnerCategoria);
		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listCategorias);
		s.setAdapter(adapter);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_emprestimo);
		
		context = this;
		
		preencherSpinnerCategoria();
		
		Intent i = getIntent();
		Bundle x = i.getExtras();
		
		if (x.isEmpty()) edit = false;
		else {
			edit = true;
			id = x.getLong("id");
			etNomeObjeto.setText(x.getCharSequence("NomeObjeto"));
			etContato.setText(x.getCharSequence("TelefoneContato"));
			etDtDevolucao.setText(x.getCharSequence("DataDevolucao"));
			etEmprestimo = x.getCharSequence("DataEmprestimo");
			urlFoto = x.getCharSequence("UrlFoto");
			categoriaObj = (Categoria) x.getSerializable("Categoria");
			s = (Spinner) findViewById(R.id.spinnerCategoria);
			
			int j = 0;
			while (j < s.getAdapter().getCount()){
				
				Categoria temp = (Categoria) s.getItemAtPosition(j);
				
				if (temp.getNome().contentEquals(categoriaObj.getNome())) {
					s.setSelection(j);
					break;
				}
				
				j++;
			}
		}
		
		Button cadastrarEmprestivo = (Button) findViewById(R.id.cadastrarEmprestimo);
		cadastrarEmprestivo.setOnClickListener(handlerCadastrarEmprestimo);
		
		Button cancelarEmprestimo = (Button) findViewById(R.id.cancelarEmprestimo);
		cancelarEmprestimo.setOnClickListener(handlerCancelarEmprestimo);
	}
	
	OnClickListener handlerCadastrarEmprestimo = new OnClickListener() {
		@Override
		public void onClick(View v) {
			etNomeObjeto = (EditText) findViewById(R.id.etNomeObjeto);
			nomeObjeto = etNomeObjeto.getText().toString();
			
			
			
			etContato = (EditText) findViewById(R.id.etContato);
			contato = etContato.getText().toString();
			
			etDtDevolucao = (EditText) findViewById(R.id.etDtDevolucao);
			dtDevolucao = etDtDevolucao.getText().toString();
			
			Emprestimo emprestimo = new Emprestimo();
			
			emprestimo.setNomeObjeto(nomeObjeto);
			
			emprestimo.setDataDevolucao(dtDevolucao);
			emprestimo.setTelefoneContato(contato);
			emprestimo.setUrlFoto("inserirURLFoto");
			emprestimo.setFlagEmprestimo(1);
			emprestimo.setUsuario(UsuarioLogado.getInstance().getUsuario());
			
			
			if (edit){
				
				emprestimo.setCategoria(categoriaObj);
				emprestimo.setDataEmprestimo(etEmprestimo.toString());	
				EmprestimoDAO.getInstance().update(context, emprestimo);
								
			} else{
				
				s = (Spinner) findViewById(R.id.spinnerCategoria);
				categoria = (Categoria) s.getSelectedItem();
				emprestimo.setCategoria(categoria);
				
				emprestimo.setDataEmprestimo((new Date(System.currentTimeMillis())).toString());
				emprestimo = EmprestimoDAO.getInstance().inserirEmprestimo(context, emprestimo);
				
			}
			finish();
		}
	};
	
	OnClickListener handlerCancelarEmprestimo = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novo_emprestimo, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
