package com.mystufficaroitalo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.mystufficaroitalo.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	int result;
	EditText phoneBox;
	EditText passBox;
	Button lgnBtn;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		
		TextView link = (TextView) findViewById(R.id.signUp);
		link.setOnClickListener(handlerSignup);

		Button login = (Button) findViewById(R.id.loginBtn);
		login.setOnClickListener(handlerLogin);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == result) {
			String phone = data.getStringExtra("phone");
			((EditText)findViewById(R.id.phoneBox)).setText(phone);
		}
	}

	OnClickListener handlerSignup = new OnClickListener() {

		public void onClick(View v) {

			Intent it = new Intent(MainActivity.this, SignUpActivity.class);

			startActivityForResult(it, result);

		}

	};

	OnClickListener handlerLogin = new OnClickListener() {

		public void onClick(View v) {

			String params[] = new String[2];

			EditText phoneBox = (EditText) findViewById(R.id.phoneBox);
			EditText passBox = (EditText) findViewById(R.id.passBox);

			params[0] = phoneBox.getText().toString();
			params[1] = passBox.getText().toString();

			try {
				LoginTask login = new LoginTask();
				login.execute(params);
			} catch (Exception e) {
				Toast.makeText(MainActivity.this, e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}

		}

	};

	public void doStuff() {

		Intent it = new Intent(this, Main2Activity.class);

		startActivity(it);

	}

	public class LoginTask extends AsyncTask<String, Integer, String> {
		
		private ProgressDialog progressDialog;

		@Override
		protected String doInBackground(String... data) {

			HttpClient httpClient = new DefaultHttpClient();

			JSONStringer js = new JSONStringer();
			String param;
			HttpResponse response = null;

			try {
				js.object().key("numeroTelefone").value(data[0]).key("senha")
						.value(data[1]).endObject();

			} catch (JSONException e) {

				e.printStackTrace();
			}

			param = js.toString();

			try {
				HttpPost request = new HttpPost(
						"http://mystuff.michef.com.br/login");
				StringEntity params = new StringEntity(param);
				request.setHeader("Accept", "application/json");
				request.addHeader("content-type", "application/json");
				request.setEntity(params);
				response = httpClient.execute(request);

				// handle response here...
			} catch (Exception ex) {
				Log.i("Erro feio", "Não conseguiu enviar o troço!!");
				ex.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}

			String answer = null;

			try {
				answer = EntityUtils.toString(response.getEntity());
				
			} catch (ParseException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

			return answer;
		}

		@Override
		protected void onPostExecute(String param) {
			progressDialog.dismiss();

			Log.i("AVISO DO RESULTADO", param);

			insertUsuarioDB(param);
			doStuff();

		}
		
		@Override
	    protected void onPreExecute() {
	        progressDialog = ProgressDialog.show(context, "Aguarde...", "Envio de dados para servidor", true, true);
	    }
		
		private void insertUsuarioDB(String response) {
			Usuario usuario;
			try {
				usuario = Util.jsonParaUsuario(new JSONObject((new JSONObject(response)).getString("data")));
				UsuarioDAO.getInstance().insert(context, usuario);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

	}

}
