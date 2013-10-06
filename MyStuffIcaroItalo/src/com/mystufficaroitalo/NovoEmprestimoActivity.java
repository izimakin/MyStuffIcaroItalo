package com.mystufficaroitalo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mystufficaroitalo.R;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class NovoEmprestimoActivity extends Activity implements OnItemSelectedListener {
	private static final int TIRAR_FOTO = 0;
	private ImageView fotoView;
	private Uri fotoUri;
	
	private boolean temFoto;

	private String nomeObjeto = null;
	private Categoria categoria = null;
	private String contato = null;
	private String dtDevolucao = null;
	private Context context = null;
	private boolean edit;
	private long id;

	//Elementos gr�ficos
	EditText etNomeObjeto;
	Spinner s;
	EditText etContato;
	EditText etDtDevolucao;
	CharSequence etEmprestimo;
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
		temFoto = false;

		preencherSpinnerCategoria();

		Intent i = getIntent();
		Bundle x = i.getExtras();

		if (x == null || x.isEmpty()) edit = false;
		else {
			edit = true;
			id = x.getLong("id");
			etNomeObjeto = (EditText) findViewById(R.id.etNomeObjeto);
			etNomeObjeto.setText(x.getCharSequence("NomeObjeto"));
			etContato = (EditText) findViewById(R.id.etContato);
			etContato.setText(x.getCharSequence("TelefoneContato"));
			etDtDevolucao = (EditText) findViewById(R.id.etDtDevolucao);
			etDtDevolucao.setText(x.getCharSequence("DataDevolucao"));
			etEmprestimo = x.getCharSequence("DataEmprestimo");
			
			if (! x.getCharSequence("UrlFoto").toString().isEmpty()) {
				fotoView = (ImageView) findViewById(R.id.img_foto);
				fotoUri = Uri.parse(x.getCharSequence("UrlFoto").toString());
				scaleFoto(fotoUri);
			}
			
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

		Button baixarEmprestimo = (Button) findViewById(R.id.baixarEmprestimo);
		baixarEmprestimo.setOnClickListener(handlerBaixarEmprestimo);
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
			
			if (temFoto) {
				emprestimo.setUrlFoto(fotoUri.toString());
			}else {
				emprestimo.setUrlFoto("");
			}
			
			emprestimo.setFlagEmprestimo(1);
			emprestimo.setUsuario(UsuarioLogado.getInstance().getUsuario());


			if (edit){
				emprestimo.setId(id);
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

	OnClickListener handlerBaixarEmprestimo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (edit){
				Emprestimo emprestimo = new Emprestimo();

				emprestimo.setId(id);	
				EmprestimoDAO.getInstance().delete(context, emprestimo);

			}
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
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	public void tirarFoto(View v) {
		fotoView = (ImageView) v;

		File f = null;

		try {
			f = createImageFile();

		} catch (IOException e) {
			Toast.makeText(this, "Erro na criação do arquivo", Toast.LENGTH_SHORT).show();
		}

		fotoUri = Uri.fromFile(f);

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);

		startActivityForResult(cameraIntent, TIRAR_FOTO);
	}

	/**
	 * Cria uma arquivo para a foto a ser capturada
	 * @return
	 * @throws IOException
	 */
	private File createImageFile() throws IOException {
		// Nome da foto a ser salva
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "img_" + timeStamp;

		//Cria a imagem temporário com tamanho padrão da camera
		File image = File.createTempFile(imageFileName, ".temp", getAlbumDir());

		return image;
	}

	/**
	 * Retorna o diretório onde as fotos serão salvas
	 * 
	 * @return
	 */
	private File getAlbumDir() {
		File result = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toURI());
		Log.i("Diretorio", result.toURI().toString());

		if (!result.exists()) {
			result.mkdir();
		}

		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK) {
			//Escala a foto para um tamanho de 100x100
			scaleFoto(fotoUri);

		} else if (resultCode == RESULT_CANCELED) {
			//Usuario cancelou a ação
		}
	}

	private void scaleFoto(Uri uri) {
		Bitmap foto = BitmapFactory.decodeFile(uri.getPath());
		foto = Bitmap.createScaledBitmap(foto, 150, 150, true);

		ExifInterface ei = null;
		try {
			ei = new ExifInterface(uri.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		switch (orientation) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			foto = rotateImage(90, foto);
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			foto = rotateImage(180, foto);
			break;
		}

		temFoto = true;
		fotoView.setImageBitmap(foto);
	}
	
	public Bitmap rotateImage(final int degree, Bitmap photo) {
		// Rotaciona a imagem em 90 graus
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);
		
		return Bitmap.createBitmap(photo, 0, 0, 150, 150, matrix, true);
	}

}
