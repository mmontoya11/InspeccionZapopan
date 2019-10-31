package com.perspective.inszap;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Configurar extends Activity implements OnClickListener {
	
	private EditText etSerie,etSerie1;
	private List<String> direccion = new ArrayList<String>();
	private List<Integer> idDir = new ArrayList<Integer>();
	private int res = 0/*,numero = 0*/,id_ = 0;
	private String serie = "",serie1 = "",direccio = "",usuario = "";
	private boolean isSelected,comm;
	private ProgressDialog pd;
	private JSONParser jParser = new JSONParser();;
	private Button btnEnvia;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configurar);
		
		etSerie = (EditText)findViewById(R.id.etSerie);
		etSerie1 = (EditText)findViewById(R.id.etSerie1);
		//spDir = (Spinner)findViewById(R.id.spDir);
		btnEnvia = (Button)findViewById(R.id.btnEnvia);
		
		btnEnvia.setOnClickListener(this);
		//spDir.setOnItemSelectedListener(this);
		
		listarDireccion();
		
		//spDir.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.multiline_spinner_dropdown_item,direccion));
		
		sp = getSharedPreferences("infracciones", Context.MODE_PRIVATE);
		
		editor = sp.edit();
		
		savedInstanceState = getIntent().getExtras();
        this.usuario = savedInstanceState.getString("usuario");
        this.id_ = savedInstanceState.getInt("id");
        this.direccio = savedInstanceState.getString("direccion");
	}
	
public void validarFrom() {
		
		etSerie.setError(null);
		etSerie1.setError(null);
		
		boolean cancel = false;
		View focusView = null;
		
		serie = etSerie.getText().toString();
		serie1 = etSerie1.getText().toString();
		
		//Toast toast;
		/*if(spDir.getSelectedItemPosition() == 0) {
			toast = Toast.makeText(getApplicationContext(), "Debe seleccionar la dirección", Toast.LENGTH_SHORT);
			toast.setGravity(0, 0, 15);
			toast.show();
			cancel = true;
			isSelected = true;;
		}*/
		
		if(TextUtils.isEmpty(serie)) {
			etSerie.setError(getString(R.string.serie));
			focusView = etSerie;
			cancel = true;
			isSelected = false;
		}
		
		if(TextUtils.isEmpty(serie1)) {
			etSerie1.setError(getString(R.string.serie));
			focusView = etSerie1;
			cancel = true;
			isSelected = false;
		}
		
		
		
		if(cancel) {
			if(!isSelected)
				focusView.requestFocus();
		}else {
			new GetNum().execute(serie,serie1);
		}
		
	}
	
	public class GetNum extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(Configurar.this);
			pd.setCancelable(false);
			pd.setTitle("Mensaje");
			pd.setMessage("Conectando...");
			pd.show();
		}

		@Override
		protected String doInBackground(String... params) {
			
			ArrayList<NameValuePair> conf = new ArrayList<NameValuePair>();
			
			conf.add(new BasicNameValuePair("serie", params[0]));
			conf.add(new BasicNameValuePair("serie1", params[1]));
			
			
			JSONObject jObject = jParser.realizarHttpRequest("http://10.10.23.54/infracciones/serversql/getTableta.php", "POST", conf);
			
			try {
				res = jObject.getInt("status");
				
				if(res > 0)
					return "S";
				else 
					return "N";
			} catch(Exception e) {
				System.err.println(e.getMessage() + " j ");
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pd.dismiss();
			Toast toast;
			
			bundle.putString("direccion", direccio);
			bundle.putString("usuario", usuario.trim());
			bundle.putInt("id", id_);
			if(result.equalsIgnoreCase("S")) {
				//String loc = String.format(Locale.getDefault(), "%04d", res);
				System.err.println(res + " num");
				editor.putInt("config", res);
				comm = editor.commit();
				if(comm) 
					toast = Toast.makeText(getApplicationContext(), "Se registro número de tableta", Toast.LENGTH_LONG);
				else
					toast = Toast.makeText(getApplicationContext(), "No se registro número de tableta", Toast.LENGTH_LONG);
				
				startActivity(new Intent(getApplicationContext(), Descarga.class).putExtras(bundle));
				onDestroy();
			}
			else {
				if(res == -2)
					toast = Toast.makeText(getApplicationContext(), "No coinciden los números de serie", Toast.LENGTH_LONG);
				else
					toast = Toast.makeText(getApplicationContext(), "No se encontro", Toast.LENGTH_LONG);
			}
			toast.setGravity(0, 0, 15);
			toast.show();
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.finish();
	}
	
	public void listarDireccion(){
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	
    	try{
    		Cursor c = db.rawQuery("Select * from c_direccion order by direccion", null);
    		if(c.moveToFirst()){
    			direccion.clear();
    			idDir.clear();
    			direccion.add("");
    			idDir.add(0);
    			do{
    				System.err.println("id " + c.getInt(0));
    				idDir.add(c.getInt(0));
    				direccion.add(c.getString(1));
    			}while(c.moveToNext());
    			c.close();
    		}
    	}catch (SQLiteException e) {
    		Log.e("bd", e.getMessage());
		}finally {
    		db.close();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnEnvia:
			validarFrom();
			break;

		default:
			break;
		}
	}
}
