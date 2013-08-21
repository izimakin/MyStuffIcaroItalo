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

import com.mystufficaroitalo.MainActivity.LoginTask;
import com.mystufficaroitalo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	String PHONE = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		Button signup = (Button) findViewById(R.id.btnSignUp);
		signup.setOnClickListener(handlerSignup);

		Button cancel = (Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(handlerCancel);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	OnClickListener handlerSignup = new OnClickListener() {

		public void onClick(View v) {

			EditText loginVal = (EditText) findViewById(R.id.loginBox);
			EditText emailVal = (EditText) findViewById(R.id.emailBox);
			EditText pass1Val = (EditText) findViewById(R.id.pass1Box);
			EditText pass2Val = (EditText) findViewById(R.id.pass2Box);

			if (pass1Val.getText().toString()
					.contentEquals(pass2Val.getText().toString())) {

				// Do the signup

				String params[] = new String[3];

				params[0] = loginVal.getText().toString();
				params[1] = pass1Val.getText().toString();
				params[2] = emailVal.getText().toString();
				PHONE = params[0];

				try {
					SignUpTask signup = new SignUpTask();
					signup.execute(params);
				} catch (Exception e) {
					Toast.makeText(SignUpActivity.this, e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			} else {

				// Alternativa até ter outro recurso.
				Log.i(ACCOUNT_SERVICE, "Senhas são diferentes.");

			}
		}

	};

	OnClickListener handlerCancel = new OnClickListener() {

		public void onClick(View v) {

			Intent it = getIntent();

			setResult(0, it);
			finish();

		}

	};

	public void doStuff(String param) {

		boolean isOk = true;

		JSONObject obj = null;
		try {
			obj = new JSONObject(param);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String status = null;
		try {
			status = obj.getString("status");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		if (status.contains("ERROR"))
			isOk = false;
		else {
			try {
				JSONObject temp = obj.getJSONObject("data");

				PHONE = temp.getString("numeroTelefone");

			} catch (JSONException e) {

				e.printStackTrace();
			}
		}

		if (isOk) {
			Intent it = getIntent();
			it.putExtra("phone", PHONE);
			setResult(-1, it);

		}

		finish();

	}

	public class SignUpTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... data) {

			HttpClient httpClient = new DefaultHttpClient();

			JSONStringer js = new JSONStringer();
			String param;
			HttpResponse response = null;

			try {
				js.object().key("numeroTelefone").value(data[0]).key("senha")
						.value(data[1]).key("email").value(data[2]).endObject();

			} catch (JSONException e) {

				e.printStackTrace();
			}

			param = js.toString();

			try {
				HttpPost request = new HttpPost(
						"http://mystuff.michef.com.br/signin");
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

			Log.i("AVISO DO RESULTADO", param);
			doStuff(param);

		}

	}

}
