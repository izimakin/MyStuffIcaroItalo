package com.mystufficaroitalo;

import com.mystufficaroitalo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NovoEmprestimoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_emprestimo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novo_emprestimo, menu);
		return true;
	}

}
