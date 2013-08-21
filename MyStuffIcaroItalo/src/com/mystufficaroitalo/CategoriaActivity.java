package com.mystufficaroitalo;

import java.util.List;

import com.mystufficaroitalo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriaActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		CategoriaDao categoriaDao = CategoriaDao.getInstance(getApplication());
		List<Categoria> listCategorias = categoriaDao.listarCategorias();
		ListView lvListarCategoria = (ListView) findViewById(R.id.listarCategoria);
		String[] strListarCategoria = new String[listCategorias.size()];
		for (int i=0; i < strListarCategoria.length; ++i) {
			strListarCategoria[i] = listCategorias.get(i).getNome();
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strListarCategoria);
		lvListarCategoria.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categoria, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        
	        case R.id.editCategory:
	        	Intent intent1 = new Intent(this, EditCategoryActivity.class);
	        	startActivity(intent1);
	        	return true;
	        	
	        case R.id.back:
	        	
	        	finish();
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
