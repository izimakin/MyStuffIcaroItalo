package com.mystufficaroitalo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mystufficaroitalo.CategoriaDAO;
import com.mystufficaroitalo.R;

public class EditCategoryActivity extends Activity {
	
	Context context = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);
		context = this;

		Button signup = (Button) findViewById(R.id.criaCategoriaBtn);
		signup.setOnClickListener(handlerGravaCategoria);

		Button cancel = (Button) findViewById(R.id.voltaBtn);
		cancel.setOnClickListener(handlerVolta);

	}

	OnClickListener handlerVolta = new OnClickListener() {

		public void onClick(View v) {

			finish();

		}

	};

	OnClickListener handlerGravaCategoria = new OnClickListener() {

		public void onClick(View v) {

			EditText etNomeCategoria = (EditText) findViewById(R.id.categoryBox);
			String strNomeCategoria = etNomeCategoria.getText().toString();
			Categoria categoria = new Categoria();
			CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();
			categoria.setNome(strNomeCategoria);
			categoria.setUsuario(Usuario.getInstance());
			categoriaDAO.inserirCategoria(context, categoria);
			finish();

		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_category, menu);
		return true;
	}

}
