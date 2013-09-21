package com.mystufficaroitalo;

import java.util.List;

import com.mystufficaroitalo.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main2Activity extends Activity {

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
		String[] strListarEmprestimo = new String[listEmprestimo.size()];
		for (int i=0; i < strListarEmprestimo.length; ++i) {
			strListarEmprestimo[i] = listEmprestimo.get(i).getNomeObjeto();
		}
		ListView s = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strListarEmprestimo);
		s.setAdapter(adapter);
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
	        	
	        
	        case R.id.logout:
	        	
	        	//Fechar aplicação
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
