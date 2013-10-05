package com.mystufficaroitalo;

import java.util.ArrayList;
import java.util.List;

import com.mystufficaroitalo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Main2Activity extends Activity {

	List<Emprestimo> empDoUsu;
	List<Emprestimo> empAoUsu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
				
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		EmprestimoDAO emprestimoDao = EmprestimoDAO.getInstance();
		List<Emprestimo> listEmprestimo = emprestimoDao.listarEmprestimo(this);
		
		empDoUsu = new ArrayList<Emprestimo>();
		empAoUsu = new ArrayList<Emprestimo>();
		
		for (int i = 0; i < listEmprestimo.size(); i++){
			
			if (listEmprestimo.get(i).getFlagEmprestimo() == 0)
				empAoUsu.add(listEmprestimo.get(i));
			else empDoUsu.add(listEmprestimo.get(i));
			
		}
		
		String[] strListarEmprestimoDoUsuario = new String[empDoUsu.size()];
		for (int i=0; i < strListarEmprestimoDoUsuario.length; ++i) {
			strListarEmprestimoDoUsuario[i] = empDoUsu.get(i).getNomeObjeto();
		}
		
		String[] strListarEmprestimoAoUsuario = new String[empAoUsu.size()];
		for (int i=0; i < strListarEmprestimoAoUsuario.length; ++i) {
			strListarEmprestimoAoUsuario[i] = empAoUsu.get(i).getNomeObjeto();
		}
		
		ListView s1 = (ListView) findViewById(R.id.listView1);
		ListView s2 = (ListView) findViewById(R.id.listView2);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strListarEmprestimoDoUsuario);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strListarEmprestimoAoUsuario);
		s1.setAdapter(adapter1);
		s2.setAdapter(adapter2);
		
		s1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Emprestimo emp = empDoUsu.get(pos);
				Intent intent = new Intent();

				
			}			

        });
		
		// Listar emprestimo ao usu�rio
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main2, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.novoEmprestimo:
	            Intent intent1 = new Intent(this, NovoEmprestimoActivity.class);
	            startActivity(intent1);
	            return true;
	        
	        case R.id.categoria:
	        	Intent intent2 = new Intent(this, CategoriaActivity.class);
	        	startActivity(intent2);
	        	return true;
	        	
	        case R.id.sincronizar:
	        	startService(new Intent(this, EmprestimoService.class));
	        	return true;
	        
	        case R.id.logout:
	        	
	        	//Fechar aplicação
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
