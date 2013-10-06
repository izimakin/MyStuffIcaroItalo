package com.mystufficaroitalo;

import java.util.List;

import com.mystufficaroitalo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoriaActivity extends Activity {

	Context context = this;
	ListView lvListarCategoria;
	List<Categoria> listCategorias;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
		listCategorias = categoriaDAO.listarCategorias(this, UsuarioLogado.getInstance().getUsuario());
		lvListarCategoria = (ListView) findViewById(R.id.listarCategoria);
		String[] strListarCategoria = new String[listCategorias.size()];
		for (int i=0; i < strListarCategoria.length; ++i) {
			strListarCategoria[i] = listCategorias.get(i).getNome();
		}
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strListarCategoria);
		lvListarCategoria.setAdapter(arrayAdapter);
		
		lvListarCategoria.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
								
				Intent intent = new Intent(context, EditCategoryActivity.class);
				Bundle bundle = new Bundle();
				Categoria cat = listCategorias.get(pos);
				bundle.putString("Nome", cat.getNome());
				bundle.putLong("id", cat.getId());
				intent.putExtras(bundle);
				startActivity(intent);
			
			}			

        });
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
