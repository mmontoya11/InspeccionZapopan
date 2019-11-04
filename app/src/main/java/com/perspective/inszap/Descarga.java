package com.perspective.inszap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Emmanuel
 *
 */

public class Descarga extends Activity implements android.content.DialogInterface.OnClickListener, OnClickListener {
	
	private Button btnDescargarD, btnDescargarF,btnActualizar,btnInfraccion,btnSalir,btnPrueba,btnConfig,btnUpdate,btnConsultarL,btnConsultar,btnReimprimir1;
	private String mFTP = "172.16.1.21"/*"servicios.tlajomulco.gob.mx"/*"pgt.no-ip.biz"*/,dir,arch,result,us,msj,res,direccion,config = "";
	private int id, aux = 0,id_l,count = 0,countF = 0,ve = 0,con;
	private int v = 0,v1;
	private FTPClient client;
	private Connection conn = new Connection();
	private JSONArray jarray;
	private JSONArray jsonA;
	private JSONObject json_data,jObject;
	private StringBuilder sb = new StringBuilder();
	private Connection c;
	private ArrayList<String> foto = new ArrayList<String>();
	private ArrayList<String> archivo = new ArrayList<String>();
	private EditText mEdittText;
	private ProgressDialog pd;
	private SharedPreferences sp;
	private final static String NOMBRE_DIRECTORIO = "MiPdf";
	private final static String NOMBRE_DOCUMENTO = "prueba.txt";
	private final static String ETIQUETA_ERROR = "ERROR";
	private BluetoothAdapter mBluetoothAdapter = null;
	private Bundle bundle = null;
	private JSONParser jsonParser = new JSONParser();
	private String [] tablet;
	//private String token,id_user;
    private String liga,version;
    private HttpURLConnection urlConnection;
    private TextView titleD;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descarga);
		
		savedInstanceState = getIntent().getExtras();
        this.us = savedInstanceState.getString("usuario");
        this.id = savedInstanceState.getInt("id");
        this.direccion = savedInstanceState.getString("direccion");
        
        Typeface helvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeueLTStd-Bd.otf");
		
        this.btnInfraccion = (Button)findViewById(R.id.btnInfraccion);
		this.btnDescargarD = (Button)findViewById(R.id.btnDescargarDato);
		this.btnDescargarF = (Button)findViewById(R.id.btnDescargaFoto);
		this.btnActualizar = (Button)findViewById(R.id.btnActualizar);
		this.btnSalir = (Button)findViewById(R.id.btnSalir);
		btnPrueba = (Button)findViewById(R.id.btnPrueba);
		btnConfig = (Button)findViewById(R.id.btnConfig);
		this.btnUpdate = (Button)findViewById(R.id.btnDescargaApp);
		btnConsultarL = (Button)findViewById(R.id.btnConsultarLicencia);
		btnConsultar = (Button)findViewById(R.id.btnConsultar);
		btnReimprimir1 = (Button)findViewById(R.id.btnReimprimir1);
		
		titleD = (TextView)findViewById(R.id.titleD);
		
		titleD.setTypeface(helvetica);
		
		btnInfraccion.setTypeface(helvetica);
		btnDescargarD.setTypeface(helvetica);
		btnDescargarF.setTypeface(helvetica);
		btnActualizar.setTypeface(helvetica);
		btnSalir.setTypeface(helvetica);
		btnPrueba.setTypeface(helvetica);
		btnConfig.setTypeface(helvetica);
		btnUpdate.setTypeface(helvetica);
		btnConsultarL.setTypeface(helvetica);
		btnConsultar.setTypeface(helvetica);
		btnReimprimir1.setTypeface(helvetica);
		
		btnPrueba.setOnClickListener(this);
		btnConfig.setOnClickListener(this);
		this.btnUpdate.setOnClickListener(this);
		btnConsultarL.setOnClickListener(this);
		btnConsultar.setOnClickListener(this);
		btnReimprimir1.setOnClickListener(this);
		btnPrueba.setVisibility(View.GONE);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		 
		c = new Connection(getApplicationContext());
		
		//if(us.equalsIgnoreCase("administrador") | us.equalsIgnoreCase("subadministrador")) {
		
		System.out.println(direccion.equalsIgnoreCase("Administración"));

		if(direccion.equalsIgnoreCase("administracion") | direccion.equalsIgnoreCase("Administración")) {
			btnInfraccion.setEnabled(false);
			btnDescargarD.setEnabled(false);
			btnDescargarF.setEnabled(false);
			btnConsultarL.setEnabled(false);
			btnConsultar.setEnabled(false);
		}else {
			btnActualizar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnUpdate.setEnabled(false);
		}
		
		sp = getSharedPreferences("infracciones", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = sp.edit();
		
		
		
		
		int co = sp.getInt("c", 0);
		
		con = sp.getInt("config", 0);
		
		System.err.println(con + " c");
		
		if(con == 0) {
			btnInfraccion.setEnabled(false);
			
		} else {
			if(direccion.equalsIgnoreCase("administracion") | direccion.equalsIgnoreCase("administraci�n")) {
				btnInfraccion.setEnabled(false);
			}else {
				btnInfraccion.setEnabled(true);
			}
			
		}
		
		config = sp.getString("numt", "");
		System.out.println(co);
		System.out.println(config + " config");
		
		if (co == 0) {
			GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db = gestion.getReadableDatabase();
			db.execSQL("Alter table Levantamiento add correo TEXT");
			System.out.println(co + " if");
			editor.putInt("c", 1);
		}else 
			System.out.println(co + " else");
		
		editor.putInt("v", 1);
		editor.commit();
		
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			ve = info.versionCode;
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		v = sp.getInt("v", 0);
		/*if (!conn.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
		//if (!conn.search("http://192.168.0.11/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
			if(conn.validarConexion(getApplicationContext())) {
				if(!conn.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if(!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if(!conn.search("http://192.168.0.11/serverSQL/getC_Direccion.php").equalsIgnoreCase("No se pudo conectar con el servidor")) {
					result = conn.search("http://10.10.23.54/infracciones/serverSQL/getVersion.php");
					//result = conn.search("http://172.16.1.21/serversql/getVersion.php");
					//result = conn.search("http://192.168.0.11/serversql/getVersion.php");
					if (!result.trim().equalsIgnoreCase("null")) {
						try {
							this.jarray = new JSONArray(result);
							for (int i = 0; i < jarray.length(); i++) {
								this.json_data = this.jarray.getJSONObject(i);
								v1 = json_data.getInt("version_app");
							}
						} catch (JSONException e) {
							Log.e("idl", e.getMessage());
						}
						if (v != v1) {
							mostrarMsg();
						}else
							System.out.println("no actualizar");
					}
				}
			}
		}*/
		
		
		this.btnDescargarD.setOnClickListener(new OnClickListener() {			
			
			@Override
			public void onClick(View v) {
				new Descargas().execute();
				
				//if (verificar().equals("")) {
					/*if (!conn.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {	
						if (conn.validarConexion(getApplicationContext())) {
							descargarLevantamiento();
							descargarDetalle();
							descargarFotografia();
							msj = "Datos enviados al servidor";
						}
						else 
							msj =  "No se encontro conexion a internet";
					}
					else 
						msj = "No se pudo conectar con el servidor";*/
						
				/*}else 
					msj = "No hay datos guardados en el dispositivo";
				Toast toast = Toast.makeText(Descarga.this, msj, Toast.LENGTH_SHORT);
				toast.setGravity(0, 0, 15);
				toast.show();*/
			}
		});
		
		this.btnDescargarF.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new EFoto().execute();
			}
		});
		
		this.btnActualizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (conn.validarConexion(getApplicationContext())) {
					//if(comprobar()) {
						//insertar();
					new Ingresar().execute();
						//msj = "Datos Actualizados";
					//}
					/*else 
						msj = "Problemas con el servidor";*/
				}
				else
					msj = "No se encontro conexion a internet";
			}
		});
		
		this.btnInfraccion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Descarga.this, InfraccionesActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("usuario", us.trim());
				bundle.putInt("id", id);
				bundle.putString("direccion", direccion);
				bundle.putInt("con", con);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		this.btnSalir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onDestroy();
			}
		});
		
		consultaTabletas();
		
	}
	
	public void mostrarMsg() {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(Descarga.this);
		dialogo.setTitle("Message").setMessage("La versi�n de la aplicaci�n no esta actualizada").setPositiveButton("Ok", this);
		dialogo.create().show();
	}
	
	public void descargarLevantamiento() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Levantamiento WHERE status = 'N'", null);
		try {
			if(db != null){
				count = 0;
				if (c.moveToFirst()) {
					do {
						//System.out.println((buscarLevantamiento(c.getString(1)) == 0) + " l");
						//System.out.println(buscarLevantamiento(c.getString(1))  + " l");
						if (buscarLevantamiento(c.getString(1)) < 1) {
							/*System.err.println(c.getString(1) + "    " + c.getString(2)  + "    " +  c.getInt(3) + "    " +  c.getString(4) + "    " +  c.getInt(5) + "    " +  c.getString(6) + "    " +  c.getString(7) + "    " +  c.getDouble(8) + "    " +  c.getDouble(9) + "    " + 
								c.getString(10) + "    " +  c.getString(11) + "    " +  c.getString(12) + "    " +  c.getInt(13) + "    " +  c.getInt(14) + "    " +  c.getString(15) + "    " +  c.getString(16) + "    " +  c.getString(17) + "    " +  c.getString(18) + "    " + 
								c.getString(19) + "    " +  c.getString(20) + "    " +  c.getString(21) + "    " +  c.getString(22) + "    " +  c.getString(23) + "    " +  c.getString(24)  + "    " + c.getString(25) + "    " +  c.getString(26) + "    " +  c.getString(27) + "    " + 
								c.getString(28) + "    " +  c.getString(29) + "    " +  c.getString(30) + "    " +  c.getString(31) + "    " +  c.getString(32) + "    " +  c.getString(33) + "    " +  c.getString(34) + "    " +  c.getString(35) + "    " +  c.getString(36) + "    " + 
								c.getString(37) + "    " +  c.getInt(38) + "    " +  c.getInt(39) + "    " + c.getString(40) + "    " +  c.getString(41) + "    " +  c.getString(46) + "    " +  c.getString(47) + "    " + c.getString(48) + "    " + c.getString(49) + "    " + c.getString(58) + " " + c.getString(c.getColumnIndex("correo")) + " " + c.getString(c.getColumnIndex("l_alineamiento")) + " " +  c.getString(c.getColumnIndex("l_construccion")));*/
							if(Connection.inserta(c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7), c.getDouble(8), c.getDouble(9),
								c.getString(10), c.getString(11), c.getString(12), c.getInt(13), c.getInt(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18),
								c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getString(24),c.getString(25), c.getString(26), c.getString(27),
								c.getString(28), c.getString(29), c.getString(30), c.getString(31), c.getString(32), c.getString(33), c.getString(34), c.getString(35), c.getString(36),
								c.getString(37), c.getInt(38), c.getInt(39),c.getString(40), c.getString(41), c.getString(46), c.getString(47),c.getString(48),c.getString(49),c.getString(58)," " + c.getString(c.getColumnIndex("correo")), c.getString(c.getColumnIndex("l_alineamiento")), c.getString(c.getColumnIndex("l_construccion")),c.getString(c.getColumnIndex("entre_calle1")),c.getString(c.getColumnIndex("entre_calle2")),c.getString(c.getColumnIndex("responsable_obra")),c.getString(c.getColumnIndex("registro_responsable")),c.getInt(c.getColumnIndex("id_c_competencia")),
								c.getString(c.getColumnIndex("medida_seguridad")),c.getString(c.getColumnIndex("articulo_medida")),c.getString(c.getColumnIndex("motivo_orden")),
								c.getInt(c.getColumnIndex("id_c_inspector3")),c.getInt(c.getColumnIndex("id_c_inspector4")),c.getInt(c.getColumnIndex("id_c_inspector5")),c.getInt(c.getColumnIndex("id_c_inspector6")),
								c.getInt(c.getColumnIndex("id_c_competencia1")),c.getInt(c.getColumnIndex("id_c_competencia2")),c.getInt(c.getColumnIndex("id_c_competencia3")),c.getInt(c.getColumnIndex("id_c_competencia4")),c.getInt(c.getColumnIndex("id_c_competencia5")),
								c.getString(c.getColumnIndex("licencia_giro")),c.getString(c.getColumnIndex("actividad_giro")),c.getInt(c.getColumnIndex("axo_licencia")),
								c.getString(c.getColumnIndex("nombre_comercial")),c.getString(c.getColumnIndex("sector")),con,c.getString(c.getColumnIndex("peticion")),c.getString(c.getColumnIndex("nivel_economico")),c.getString(c.getColumnIndex("reincidencia")),/*"http://172.16.1.21/serverSQL/insertLevantamiento.php"*/ "http://10.10.23.54/infracciones/serversql/insertLevantamientoas.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php" "http://10.10.23.54/infracciones/serverSQL/insertLevantamiento.php"*/).equalsIgnoreCase("S")) {
								
								System.out.println("si");
								ContentValues cv = new ContentValues();
								cv.put("status", "S");
								db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
								count += 1;
								//db.delete("Levantamiento", "numero_acta = '" + c.getString(1) +  "'", null);
							}
							else {
								System.out.println("no");
								System.err.println(c.getColumnName(1) + " " + c.getString(1) + ",    " +c.getColumnName(2) + " " +  c.getString(2)  + ",    " + c.getColumnName(3) + " " +  c.getInt(3) + ",    " + c.getColumnName(4) + " " +  c.getString(4) + ",    " + c.getColumnName(5) + " " +  c.getInt(5) + ",    " + c.getColumnName(6) + " " +  c.getString(6) + ",    " + c.getColumnName(7) + " " +  c.getString(7) + ",    " + c.getColumnName(8) + " " +  c.getDouble(8) + ",    " + c.getColumnName(9) + " " +  c.getDouble(9) + ",    " + 
										c.getColumnName(10) + " " + c.getString(10) + ",    " + c.getColumnName(11) + " " +  c.getString(11) + ",    " + c.getColumnName(12) + " " +  c.getString(12) + ",    " + c.getColumnName(13) + " " +  c.getInt(13) + ",    " + c.getColumnName(14) + " " +  c.getInt(14) + ",    " + c.getColumnName(15) + " " +  c.getString(15) + ",    " + c.getColumnName(16) + " " +  c.getString(16) + ",    " + c.getColumnName(17) + " " +  c.getString(17) + ",    " + c.getColumnName(18) + " " +  c.getString(18) + ",    " + 
										c.getColumnName(19) + " " + c.getString(19) + ",    " + c.getColumnName(20) + " " +  c.getString(20) + ",    " + c.getColumnName(21) + " " +  c.getString(21) + ",    " + c.getColumnName(22) + " " +  c.getString(22) + ",    " + c.getColumnName(23) + " " +  c.getString(23) + ",    " + c.getColumnName(24) + " " +  c.getString(24)  + ",    " + c.getColumnName(25) + " " +  c.getString(25) + ",    " + c.getColumnName(26) + " " +  c.getString(26) + ",    " + c.getColumnName(27) + " " +  c.getString(27) + ",    " + 
										c.getColumnName(28) + " " + c.getString(28) + ",    " + c.getColumnName(29) + " " +  c.getString(29) + " ,   " + c.getColumnName(30) + " " +  c.getString(30) + ",    " + c.getColumnName(31) + " " +  c.getString(31) + ",    " + c.getColumnName(32) + " " +  c.getString(32) + ",    " + c.getColumnName(33) + " " +  c.getString(33) + ",    " + c.getColumnName(34) + " " +  c.getString(34) + ",    " + c.getColumnName(35) + " " +  c.getString(35) + ",    " +  c.getColumnName(36) + " " + c.getString(36) + ",    " + 
										c.getColumnName(37) + " " + c.getString(37) + ",    " + c.getColumnName(38) + " " +  c.getInt(38) + ",    " + c.getColumnName(39) + " " +  c.getInt(39) + ",    " + c.getColumnName(40) + " " + c.getString(40) + ",    " + c.getColumnName(41) + " " +  c.getString(41) + ",    " + c.getColumnName(46) + " " +  c.getString(46) + ",    " + c.getColumnName(47) + " " +  c.getString(47) + ",    " + c.getColumnName(48) + " " + c.getString(48) + ",    " + c.getColumnName(49) + " " + c.getString(49) + ",    " + c.getColumnName(58) + " " +  c.getString(58) + ", " + c.getColumnName(c.getColumnIndex("correo")) + " " +  c.getString(c.getColumnIndex("correo")) + ",  " +  c.getColumnName(c.getColumnIndex("l_alineamiento")) + " " + c.getString(c.getColumnIndex("l_alineamiento")) + ",  " + c.getColumnName(c.getColumnIndex("l_construccion")) + " " +  c.getString(c.getColumnIndex("l_construccion")) +
										c.getColumnName(c.getColumnIndex("entre_calle1"))	+ " " +	c.getString(c.getColumnIndex("entre_calle1")) +" " + c.getColumnName(c.getColumnIndex("entre_calle2"))	+ " " + c.getString(c.getColumnIndex("entre_calle2"))  +" " + c.getColumnName(c.getColumnIndex("entre_calle2"))	+ " " + c.getString(c.getColumnIndex("responsable_obra"))  +" " + c.getColumnName(c.getColumnIndex("registro_responsable"))	+ " " + c.getString(c.getColumnIndex("registro_responsable"))  +" " + c.getColumnName(c.getColumnIndex("id_c_competencia"))	+ " " + c.getInt(c.getColumnIndex("id_c_competencia")));
							}
							/*Connection.inserta(c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7), c.getDouble(8), c.getDouble(9),
									c.getString(10), c.getString(11), c.getString(12), c.getInt(13), c.getInt(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18),
									c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getString(24),c.getString(25), c.getString(26), c.getString(27),
									c.getString(28), c.getString(29), c.getString(30), c.getString(31), c.getString(32), c.getString(33), c.getString(34), c.getString(35), c.getString(36),
									c.getString(37), c.getInt(38), c.getInt(39),c.getString(40), c.getString(41), c.getString(46), c.getString(47),c.getString(48),c.getString(49),c.getString(58)," " + c.getString(c.getColumnIndex("correo")),/"http://172.16.1.21/serverSQL/insertLevantamiento.php""http://10.10.23.54/infracciones/serverSQL/insertLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php""http://192.168.0.11/serverSQL/insertLevantamiento.php");*/
						}
						//db.delete("Levantamiento", "numero_acta = '" + c.getString(1) +  "'", null);
					}while (c.moveToNext());
				}
				if(count > 0) {
					try {
						
					
						ArrayList<NameValuePair> carga = new ArrayList<NameValuePair>();
						
						carga.add(new BasicNameValuePair("tableta", config));
						carga.add(new BasicNameValuePair("registros", String.valueOf(count)));
						carga.add(new BasicNameValuePair("fotos", String.valueOf(0)));
						
						//JSONObject json = jsonParser.realizarHttpRequest("http://10.10.23.54/infracciones/serverSQL/insertCarga.php", "POST", carga);
						
						JSONObject json = jsonParser.realizarHttpRequest("http://10.10.23.54/infracciones/serverSQL/insertCarga.php", "POST", carga);
						
						int estatus = json.getInt("status");
						
						if(estatus == 1)
							System.err.println("inserto");
						else
							System.err.println("no inserto");
						
					}catch(JSONException e) {
						System.out.println(e.getMessage() + " mm");
					}
				}
				
			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}
	
	public void descargarDetalle() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Detalle_infraccion where estatus1 = 'N'", null);
		
		ContentValues cv = new ContentValues();
		cv.put("estatus1", "S");
		//db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
		try {
			if(db != null){
				if (c.moveToFirst()) {
					do {
						System.out.println((buscarDetalle(c.getString(2), c.getString(3), c.getString(4)) == 0) + " d");
						System.out.println(c.getString(2) + " " + c.getString(3) + " " + c.getString(4) + " d");
						if (buscarDetalle(c.getString(2), c.getString(3), c.getString(4)) == 0) {
							id_l = idLe(c.getString(2));
							conn.insertDetalle(id_l, c.getString(2), Integer.parseInt(c.getString(3)), Integer.parseInt(c.getString(4)), /*"http://172.16.1.21/serverSQL/insertDetalle.php"*/"http://10.10.23.54/infracciones/serverSQL/insertDetalle.php"/*"http://pgt.no-ip.biz/serverSQL/insertDetalle.php"/"http://192.168.0.15/serverSQL/insertDetalle.php"*/);
							db.update("Detalle_infraccion", cv, "id_detalle_infraccion = " + c.getInt(0), null);
						}
						else {
							db.update("Detalle_infraccion", cv, "id_detalle_infraccion = " + c.getInt(0), null);
						}
						//db.delete("Detalle_infraccion", "id_detalle_infraccion = '" + c.getInt(0) + "'", null);
					}while (c.moveToNext());
				}
				
			}
		} catch (SQLiteException e) {
			Log.e("SQLITE", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}
	
	public void descargarFotografia() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Fotografia where estatus1 = 'N'", null);
		
		ContentValues cv = new ContentValues();
		cv.put("estatus1", "S");
		//db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
		try {
			if(db != null) {
				System.out.println(c.getCount()+" F");
				if (c.moveToFirst()) {
					do {
						System.out.println((buscarFoto(c.getString(2), c.getString(3), c.getString(4)) == 0) + " f");
						if (buscarFoto(c.getString(2), c.getString(3), c.getString(4)) == 0) {
							id_l = idLe(c.getString(2));
							conn.insertFoto(id_l, c.getString(2), c.getString(3), c.getString(4), /*"http://172.16.1.21/serverSQL/insertFoto.php"*/"http://10.10.23.54/infracciones/serverSQL/insertFoto.php"/*"http://pgt.no-ip.biz/serverSQL/insertFoto.php"/"http://192.168.0.15/serverSQL/insertFoto.php"*/);
							db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);
						}else {
							db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);
						}
						
					}while (c.moveToNext());
				}
				
			}
		} catch (SQLiteException e) {
			Log.e("descargar foto", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}
	
	public int buscarLevantamiento(String numeroActa) {
		result = conn.search(numeroActa,"http://10.10.23.54/infracciones/serverSQL/getNumeroActaL.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaL.php"/"http://192.168.0.15/serverSQL/getNumeroActaL.php"*/);
		//result = conn.search(numeroActa,"http://172.16.1.21/serverSQL/getNumeroActaL.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaL.php""http://192.168.0.11/serverSQL/getNumeroActaL.php"*/);
		if (!result.trim().equalsIgnoreCase("null")) {
			try {
				this.jarray = new JSONArray(result);
				return jarray.length();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return 0;
	}
	
	public int buscarDetalle(String numeroActa,String id_c_infraccion,String cantidad) {
		result = conn.detalleInfraccion(numeroActa, id_c_infraccion, cantidad, "http://10.10.23.54/infracciones/serverSQL/getNumeroActaD.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaD.php"/"http://192.168.0.15/serverSQL/getNumeroActaD.php"*/);
		//result = conn.detalleInfraccion(numeroActa, id_c_infraccion, cantidad, "http://172.16.1.21/serverSQL/getNumeroActaD.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaD.php""http://192.168.0.11/serverSQL/getNumeroActaD.php"*/);
		if (!result.trim().equalsIgnoreCase("null")) {
			try {
				this.jarray = new JSONArray(result);
				System.err.println("aqui");
				return jarray.length();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return 0;
	}
	
	public int idLe(String numero_acta) {
		res = conn.idLevantamiento("http://10.10.23.54/infracciones/serverSQL/getIdLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getIdLevantamiento.php"/"http://192.168.0.15/serverSQL/getIdLevantamiento.php"*/, numero_acta);
		//res = conn.idLevantamiento("http://172.16.1.21/serverSQL/getIdLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getIdLevantamiento.php""http://192.168.0.11/serverSQL/getIdLevantamiento.php"*/, numero_acta);
		int id = 0;
		if (!res.trim().equalsIgnoreCase("null")) {
		
			try {
				this.jarray = new JSONArray(res);
				for (int i = 0; i < jarray.length(); i++) {
					this.json_data = this.jarray.getJSONObject(i);
					id = json_data.getInt("id_levantamiento");
				}
			} catch (JSONException e) {
				Log.e("idl", e.getMessage());
			}
		}
		return id;
	}
	
	public int buscarFoto(String numeroActa,String archivo,String descripcion) {
		result = conn.fotografia(numeroActa, archivo, descripcion, "http://10.10.23.54/infracciones/serverSQL/getNumeroActaF.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaF.php"/"http://192.168.0.15/serverSQL/getNumeroActaF.php"*/);
		//result = conn.fotografia(numeroActa, archivo, descripcion, "http://172.16.1.21/serverSQL/getNumeroActaF.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaF.php""http://192.168.0.11/serverSQL/getNumeroActaF.php"*/);
		
		if (!result.trim().equalsIgnoreCase("null")) {
			try {
				this.jarray = new JSONArray(result);
				return jarray.length();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	
	public void fotografias() {
		boolean res;
		countF = 0;
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		String s,dir,ar;
		dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
		File f;
		try {
			if (db != null) {
				Cursor c = db.rawQuery("SELECT * FROM Fotografia where estatus='N'", null);
				if(c.moveToFirst()) {
					foto.clear();
					archivo.clear();
					do {
						foto.add(c.getString(2));
						archivo.add(c.getString(3));
						s = c.getString(2).replace("/", "_");
						ar = c.getString(3);
						Log.i("Dato", s + " " + ar);
						f = new File(dir + s + "/" +  ar);
						System.out.println(f.exists());
						if (f.exists()) {
							Log.i("Mes", "if exist");
							File file = new File(f.getAbsolutePath());
							MultipartEntity mpEntity = new MultipartEntity();
							ContentBody foto = new FileBody(file, "image/jpeg");
							
							mpEntity.addPart("fotoUp", foto);
							mpEntity.addPart("foto", new StringBody(s));
							
							JSONObject json = jsonParser.subirImage("http://10.10.23.54/infracciones/sistema/pruebaI.php", "POST", mpEntity);
							
							//JSONObject json = jsonParser.subirImage("http://192.168.0.16:8080/sitios/pruebas/pruebaI.php", "POST", mpEntity);
							
							int success = json.getInt("status");
							System.out.println(success + " success");
							
							ContentValues cv = new ContentValues();
							String sql;
							
							if(success == 1) {
								System.out.println("envio movio");
								cv.put("estatus", "S");
								//sql = "update Fotografia set ";
								db.update("Fotografia", cv, " id_fotografia = " + c.getInt(0) , null);
							}
							else if(success == 0) 
								System.out.println("envio no movio");
							else if(success == 3) {
								System.out.println("existe");
								cv.put("estatus", "S");
								db.update("Fotografia", cv, " id_fotografia = " + c.getInt(0) , null);
							}
							else
								System.out.println("no envio f");
							
						}
						//db.delete("Fotografia", c.getColumnName(0) + " = '" + c.getString(0) + "'", null);
					} while (c.moveToNext());
					c.close();
					Thread.sleep(4000);
					if(countF > 0) {
						try {
							
							ArrayList<NameValuePair> carga = new ArrayList<NameValuePair>();
							
							carga.add(new BasicNameValuePair("tableta", config));
							carga.add(new BasicNameValuePair("registros", String.valueOf(0)));
							carga.add(new BasicNameValuePair("fotos", String.valueOf(countF)));
							
							//JSONObject json = jsonParser.realizarHttpRequest("http://10.10.23.54/infracciones/serverSQL/insertCarga.php", "POST", carga);
							
							JSONObject json = jsonParser.realizarHttpRequest("http://10.10.23.54/infracciones/serverSQL/insertCarga.php", "POST", carga);
							
							int estatus = json.getInt("status");
							
							if(estatus == 1)
								System.err.println("inserto");
							else
								System.err.println("no inserto");
						
						}catch(JSONException e) {
							System.out.println(e.getMessage() + " mm");
						}
					}
				}
				
			}
		} catch (Exception e) {
			
		}
	}
	
	public void foto() {
		countF = 0;
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		String s,dir,ar;
		dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
		File f;
		FTPFile [] fil;
		try {
			if (db != null) {
				Cursor c = db.rawQuery("SELECT * FROM Fotografia", null);
				if(c.moveToFirst()) {
					foto.clear();
					archivo.clear();
					do {
						foto.add(c.getString(2));
						archivo.add(c.getString(3));
						s = c.getString(2).replace("/", "_");
						ar = c.getString(3);
						Log.i("Dato", s + " " + ar);
						f = new File(dir + s + "/" +  ar);
						System.out.println(f.exists());
						if (f.exists()) {
							Log.i("Mes", "if exist");
							fil = ftp(s);
							if (fil.length > 0) {
								Log.i("Mes", "fil not null");
								for (int i = 0; i < fil.length; i++) {
									Log.i("Mes", "for");
									Log.i("Parametros", " s " + s + " ar " + ar);
									Log.i("Nombre", fil[i].getName() + " " + ar);
									Log.i("tama�o", fil[i].getSize() + " " + f.length());
									if (fil[i].getName().equalsIgnoreCase(ar)) {
										Log.i("Mes", "if son iguales");
										if (fil[i].getSize() != f.length()) {
											Log.i("Mes", "if diferente tama�o");
											subirFoto(s, ar, c.getInt(0));
										}	
									}
									else {
										Log.i("Mes", "else iguales name");
										subirFoto(s, ar, c.getInt(0));
									}
								}
							}
							else {
								Log.i("Mes", "else nulo");
								subirFoto(s, ar, c.getInt(0));
							}
						}
						//db.delete("Fotografia", c.getColumnName(0) + " = '" + c.getString(0) + "'", null);
					} while (c.moveToNext());
					c.close();
					Thread.sleep(4000);
					System.out.println(countF + " qqqqq");
					if(countF > 0) {
						try {
							
							ArrayList<NameValuePair> carga = new ArrayList<NameValuePair>();
							
							carga.add(new BasicNameValuePair("tableta", config));
							carga.add(new BasicNameValuePair("registros", String.valueOf(0)));
							carga.add(new BasicNameValuePair("fotos", String.valueOf(countF)));
							
							//JSONObject json = jsonParser.realizarHttpRequest("http://10.10.23.54/infracciones/serverSQL/insertCarga.php", "POST", carga);
							
							JSONObject json = jsonParser.realizarHttpRequest("http://10.10.23.54/infracciones/serverSQL/insertCarga.php", "POST", carga);
							
							int estatus = json.getInt("status");
							
							if(estatus == 1)
								System.err.println("inserto");
							else
								System.err.println("no inserto");
						
						}catch(JSONException e) {
							System.out.println(e.getMessage() + " mm");
						}
					}
				}
				
			}
		} catch (Exception e) {
			Log.e("foto", e.getMessage() + " ");
		}finally {
			db.close();
			//eliminarCarpetas();
		}
	}
	
	public FTPFile[] ftp (String ruta) {
		client = null;
		FTPFile[] files = null;
		try {
			client = new FTPClient();
			//client.connect(InetAddress.getByName(mFTP));
			client.connect(InetAddress.getByName(mFTP), 21);
			System.out.println("logueo "+client.login("pgm", "pgm2012"));
			//System.out.println("logueo "+client.login("prueba", "1234"));
			System.out.println(InetAddress.getByName(mFTP));
			System.out.println(client.printWorkingDirectory());
			System.out.println("Conectado: " + client.isConnected()+" " + client.printWorkingDirectory());
			System.out.println(client.mkd(ruta));
			if (client.changeWorkingDirectory("/"+ruta)) {
				
				files = client.listFiles();
				System.out.println(client.isConnected());
				for (FTPFile arch : files){
					System.out.println(arch.toString() + " t " + arch.getSize());
				}
			}
			System.out.println(client.logout());
			client.disconnect();
			System.out.println("Conectado: " + client.isConnected()+"");
		} catch (IOException e) {
			System.err.println("Error" + e.getMessage());
		}
		System.out.println(files.length);
		return files;
	}
	
	public void subirFoto(String dir, String name, final int idFoto) {
		this.dir = dir;
		this.arch = name;
		/*pd = new ProgressDialog(Descarga.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Espere...");
		pd.setTitle("Subiendo Fotograf�a(s)");
		pd.setCancelable(true);
		pd.show();*/
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				FTPClient client = null;
				try {
					client = new FTPClient();
					client.connect(InetAddress.getByName(mFTP));
					/*System.out.println(client.login("pgm", "pgm2012"));
					if (client.login("pgm", "pgm2012")) {*/
					System.out.println(client.login("prueba", "1234"));
					if (client.login("prueba", "1234")) {
						Log.i("Status", client.isConnected()+"");
						Log.i("Dir", client.printWorkingDirectory());
						Log.i("Crear dir", client.mkd(Descarga.this.dir)+"");
						client.changeWorkingDirectory("/"+Descarga.this.dir);
						client.printWorkingDirectory();
						
						if (new File(Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+Descarga.this.dir+"/"+arch).exists()) {
						
							client.setFileType(FTP.BINARY_FILE_TYPE);
							BufferedInputStream buffIn=null;
							buffIn=new BufferedInputStream(new FileInputStream(Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+Descarga.this.dir+"/"+arch));
							client.enterLocalPassiveMode();
							
							if(client.storeFile(Descarga.this.arch, buffIn)){
								countF += 1;
							}
							
						}
			        
						client.logout();
						client.disconnect();
						client.isConnected();
						eliminarFoto(idFoto);
					}
					//pd.dismiss();
				} catch (IOException e) {
					Log.e("Error en", e.getMessage());
					//pd.dismiss();
				}finally {
					//pd.dismiss();
				}
			}
		});
		t.start();
	}
	
	public void eliminarCarpetas() {
		dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
		File archivo;
		try {
			for (int i = 0; i < foto.size(); i++) {
				arch = foto.get(i).replace("/", "_");
				File f = new  File(dir + arch);
				Log.i("dir", f.getAbsolutePath());
				Log.i("dir", dir + arch);
				if(f.exists()) { 
					archivo = new File(f.getAbsolutePath() + "/" + arch + ".txt");
					Log.i("dir", archivo.getAbsolutePath());
					if (archivo.exists())
						Log.i("borrar txt", archivo.delete() + "") ;
					for (int j = 0; j < this.archivo.size(); j++) {
						archivo = new File(f.getAbsolutePath() + "/" + this.archivo.get(j));
						Log.i("dir", archivo.getAbsolutePath());
						if (archivo.exists()) 
							Log.i("borrar archivo", archivo.delete() + "") ;
					}
					Log.i("borrar ", f.delete() + "") ;
				}
					
			}
		}catch (Exception e) {
			Log.e("folder", e.getMessage());
		}
	}
	
	public String verificar() {
		StringBuilder sb = new StringBuilder();
		GestionBD gestionar = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		Cursor l = db.rawQuery("SELECT * FROM Levantamiento", null);
		Cursor d = db.rawQuery("SELECT * FROM Detalle_infraccion", null);
		if (l.getCount() == 0)
			sb.append(" Levantamientos");
		if(d.getCount() == 0)
			sb.append(" Detalle infraccion");
		db.close();
		return sb.toString();
	}
	
	public String VerificarFoto() {
		StringBuilder sb = new StringBuilder();
		GestionBD gestionar = new GestionBD(this, "inspeccion",null,1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		Cursor F = db.rawQuery("SELECT * FROM Fotografia where estatus = 'N'", null);
		if (F.getCount() == 0)
			sb.append(" Fotografia");
		db.close();
		return sb.toString();
	}
	
	public void eliminarFoto(int idfoto) {
		GestionBD gestionar = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		db.delete("Fotografia", "id_fotografia = '" + idfoto + "'", null);
		db.close();
	}
	
	public void tablas() {
		result = conn.search("http://172.20.246.89:8080/serverSQL/getTablas.php");
		try {
			this.jarray = new JSONArray(result);
			for (int i = 0; i < jarray.length(); i++) {
				this.json_data = this.jarray.getJSONObject(i);
				if (json_data.getString("Tabla").substring(0, 2).equalsIgnoreCase("C_")) {
					if (aux == 0) {
						sb.append("Create table " + json_data.getString("Tabla") + "(");
					}
					else {
						sb.append("); Create table " + json_data.getString("Tabla") + "(");
					}
					aux ++;
					result = conn.buscarTabla(json_data.getString("Tabla"), "http://172.20.246.89:8080/serverSQL/getCunsultaTabla.php");
					this.jsonA = new JSONArray(result);
					for (int j = 0; j < jsonA.length(); j++) {
						if (j != 0) {
							sb.append(",");
						}
						this.jObject = jsonA.getJSONObject(j);
						sb.append(jObject.getString("columna") + " " + jObject.getString("Tipo"));
					}
				}
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public boolean comprobar() {
		return c.insetarRegistros("http://pgt.no-ip.biz/serverSQL/getC_Direccion.php", "C_Direccion");
	}
	
	public void insertar() {
		if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
			/*if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_Direccion");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_Direccion.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_Direccion");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getc_insepctor.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_inspector");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getc_insepctor.php"/*"http://pgt.no-ip.biz/serverSQL/getc_insepctor.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_inspector");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_infraccion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_infraccion");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_infraccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_infraccion.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_infraccion");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getc_dia_no_habil.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_dia_no_habil");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getc_dia_no_habil.php"/*"http://pgt.no-ip.biz/serverSQL/getc_dia_no_habil.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_dia_no_habil");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_ley_ingesos.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_ley_ingresos");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_ley_ingesos.php"/*"http://pgt.no-ip.biz/serverSQL/getC_ley_ingesos.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_ley_ingresos");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getc_zonas.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_zonas");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getc_zonas.php"/*"http://pgt.no-ip.biz/serverSQL/getc_zonas.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_zonas");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_fraccionamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_fraccionamiento");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_fraccionamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_fraccionamiento.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_fraccionamiento");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_poblacion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_poblacion");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_poblacion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_poblacion.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_poblacion");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_visitado_identifica.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_visitado_identifica");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_visitado_identifica.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_identifica.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_visitado_identifica");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_visitado_manifiesta.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_visitado_manifiesta");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_visitado_manifiesta.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_visitado_manifiesta");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_uso_suelo.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_uso_suelo");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_uso_suelo.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_uso_suelo");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getC_ordenamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_ordenamiento");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getC_ordenamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "C_ordenamiento");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getTabletas.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("c_tabletas");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getTabletas.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "c_tabletas");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getCMedida.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("c_medida_seguridad");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getCMedida.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "c_medida_seguridad");
			}
			if (!c.search("http://10.10.23.54/infracciones/serversql/getc_medida.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("c_medida_precautoria");
				c.insetarRegistros("http://10.10.23.54/infracciones/serversql/getc_medida.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "c_medida_precautoria");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getc_medidaespacio.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("c_medida_precautoria_espacios");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getc_medidaespacio.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "c_medida_precautoria_espacios");
			}
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getCPeticion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("c_peticion");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getCPeticion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*, "c_peticion");
			}*/
			if (!c.search("http://10.10.23.54/infracciones/serverSQL/getLicenciasReglamentos.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("v_LicenciasReglamentos");
				c.insetarRegistros("http://10.10.23.54/infracciones/serverSQL/getLicenciasReglamentos.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "v_LicenciasReglamentos");
			}
			/*if (!c.search("http://10.10.23.54/infracciones/serversql/getVistaLevantamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("vista_levantamiento");
				c.insetarRegistros("http://10.10.23.54/infracciones/serversql/getVistaLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php"*, "vista_levantamiento");
			}*/
			msj = "Datos Actualizados";
		
		/*if (!c.search("http://192.168.0.11/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
			if (!c.search("http://192.168.0.11/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_Direccion");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_Direccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_Direccion.php""http://192.168.1.87/serverSQL/getC_Direccion.php", "C_Direccion");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getc_insepctor.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_inspector");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getc_insepctor.php"/*"http://pgt.no-ip.biz/serverSQL/getc_insepctor.php", "C_inspector");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_infraccion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_infraccion");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_infraccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_infraccion.php", "C_infraccion");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getc_dia_no_habil.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_dia_no_habil");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getc_dia_no_habil.php"/*"http://pgt.no-ip.biz/serverSQL/getc_dia_no_habil.php", "C_dia_no_habil");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_ley_ingesos.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_ley_ingresos");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_ley_ingesos.php"/*"http://pgt.no-ip.biz/serverSQL/getC_ley_ingesos.php", "C_ley_ingresos");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getc_zonas.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_zonas");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getc_zonas.php"/*"http://pgt.no-ip.biz/serverSQL/getc_zonas.php", "C_zonas");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_fraccionamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_fraccionamiento");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_fraccionamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_fraccionamiento.php", "C_fraccionamiento");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_poblacion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_poblacion");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_poblacion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_poblacion.php", "C_poblacion");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_visitado_identifica.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_visitado_identifica");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_visitado_identifica.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_identifica.php", "C_visitado_identifica");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_visitado_manifiesta.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_visitado_manifiesta");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_visitado_manifiesta.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php", "C_visitado_manifiesta");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_uso_suelo.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_uso_suelo");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_uso_suelo.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php", "C_uso_suelo");
			}
			if (!c.search("http://192.168.0.11/serverSQL/getC_ordenamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_ordenamiento");
				c.insetarRegistros("http://192.168.0.11/serverSQL/getC_ordenamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php", "C_ordenamiento");
			}
			msj = "Datos Actualizados";*/
		
		/*if (!c.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
			if (!c.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_Direccion");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_Direccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_Direccion.php""http://192.168.1.87/serverSQL/getC_Direccion.php", "C_Direccion");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getc_insepctor.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_inspector");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getc_insepctor.php"/*"http://pgt.no-ip.biz/serverSQL/getc_insepctor.php", "C_inspector");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_infraccion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_infraccion");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_infraccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_infraccion.php", "C_infraccion");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getc_dia_no_habil.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_dia_no_habil");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getc_dia_no_habil.php"/*"http://pgt.no-ip.biz/serverSQL/getc_dia_no_habil.php", "C_dia_no_habil");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_ley_ingesos.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_ley_ingresos");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_ley_ingesos.php"/*"http://pgt.no-ip.biz/serverSQL/getC_ley_ingesos.php", "C_ley_ingresos");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getc_zonas.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_zonas");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getc_zonas.php"/*"http://pgt.no-ip.biz/serverSQL/getc_zonas.php", "C_zonas");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_fraccionamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_fraccionamiento");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_fraccionamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_fraccionamiento.php", "C_fraccionamiento");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_poblacion.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_poblacion");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_poblacion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_poblacion.php", "C_poblacion");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_visitado_identifica.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_visitado_identifica");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_visitado_identifica.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_identifica.php", "C_visitado_identifica");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_visitado_manifiesta.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_visitado_manifiesta");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_visitado_manifiesta.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php", "C_visitado_manifiesta");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_uso_suelo.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_uso_suelo");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_uso_suelo.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php", "C_uso_suelo");
			}
			if (!c.search("http://172.16.1.21/serverSQL/getC_ordenamiento.php").trim().equalsIgnoreCase("null")) {
				eliminaRegistros("C_ordenamiento");
				c.insetarRegistros("http://172.16.1.21/serverSQL/getC_ordenamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php", "C_ordenamiento");
			}
			msj = "Datos Actualizados";*/
		}
		else 
			msj = "No se pudo conectar con el servidor";
		//eliminaRegistros("Acta");
		//c.insetarRegistros("http://pgt.no-ip.biz/serverSQL/getActas.php", "Acta");
	}
	
	
	
	public void eliminaRegistros(String tabla) {
    	GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion",null,1);
    	SQLiteDatabase db = gestion.getReadableDatabase();
    	try {
    		Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);
			if (c.moveToFirst()) {
				do {
					db.delete(tabla, c.getColumnName(0) + " = " + c.getString(0), null);
				} while (c.moveToNext());
			}
			c.close();
		} catch (SQLiteException e) {
			Log.e("SQLiteException ", e.getMessage());
		}
    	finally {
    		db.close();
    	}
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			onDestroy();
			//Intent intent = new Intent();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.finish();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("usuario", us.trim());
		bundle.putInt("id", id);
		bundle.putString("direccion", direccion);
		switch (v.getId()) {
		case R.id.btnConfig:
			bundle  = new Bundle();
			bundle.putString("usuario", us);
			bundle.putInt("id", id);
			bundle.putString("direccion", direccion);
			//mostrarVentana();
			startActivity(new Intent(getApplicationContext(), Configurar.class).putExtras(bundle));
			onDestroy();
			break;
			
		case R.id.btnDescargaApp:
			String version = String.valueOf(ve);
			new Update(Descarga.this).execute(version);
			break;
			
		case R.id.btnConsultarLicencia:
			startActivity(new Intent(getApplicationContext(), ConsultarLicencias.class));
			break;
			
		case R.id.btnConsultar:
			
			startActivity(new Intent(getApplicationContext(), ConsultarInfracciones.class).putExtras(bundle));
			break;
			
		case R.id.btnReimprimir1:
			startActivity(new Intent(getApplicationContext(), Reimprimir.class).putExtras(bundle));
			break;

		default:
			break;
		}
		
		/*System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + NOMBRE_DIRECTORIO + "/" + NOMBRE_DOCUMENTO);
		
		Document documento = new Document();

		try {

			// Creamos el fichero con el nombre que deseemos.
			File f = crearFichero(NOMBRE_DOCUMENTO);

			// Creamos el flujo de datos de salida para el fichero donde
			// guardaremos el pdf.
			FileOutputStream ficheroPdf = new FileOutputStream(
					f.getAbsolutePath());

			// Asociamos el flujo que acabamos de crear al documento.
			PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

			// Incluimos el p�e de p�gina y una cabecera
			HeaderFooter cabecera = new HeaderFooter(new Phrase(
					"Esta es mi cabecera"), false);
			HeaderFooter pie = new HeaderFooter(new Phrase(
					"Este es mi pie de p�gina"), false);

			documento.setHeader(cabecera);
			documento.setFooter(pie);

			// Abrimos el documento.
			documento.open();

			// A�adimos un t�tulo con la fuente por defecto.
			documento.add(new Paragraph("T�tulo 1"));

			// A�adimos un t�tulo con una fuente personalizada.
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD, Color.RED);
			documento.add(new Paragraph("T�tulo personalizado", font));

			// Insertamos una imagen que se encuentra en los recursos de la
			// aplicaci�n.
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.gob_mun_tlajomulco);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			Image imagen = Image.getInstance(stream.toByteArray());
			documento.add(imagen);

			// Insertamos una tabla.
			PdfPTable tabla = new PdfPTable(5);
			for (int i = 0; i < 15; i++) {
				tabla.addCell("Celda " + i);
			}
			documento.add(tabla);

			// Agregar marca de agua
			font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD, Color.GRAY);
			ColumnText.showTextAligned(writer.getDirectContentUnder(),Element.ALIGN_CENTER, new Paragraph("amatellanes.wordpress.com", font), 297.5f, 421,writer.getPageNumber() % 2 == 1 ? 45 : -45);
			
			if (!mBluetoothAdapter.isEnabled()) {
				 
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, 3);
			}
			
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + NOMBRE_DIRECTORIO + "/" + NOMBRE_DOCUMENTO);
				ArrayList<Uri> uris=new ArrayList<Uri>();
		    	Intent Int=new Intent();
		    	Int.setAction(android.content.Intent.ACTION_SEND_MULTIPLE);
		    	Int.setType("*");
		    	
		    	uris.add(Uri.fromFile(file));
		    			
		    	Int.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		    		
		    	startActivity(Int);
		    	
				
		} catch (DocumentException e) {

			Log.e(ETIQUETA_ERROR, e.getMessage());

		} catch (IOException e) {

			Log.e(ETIQUETA_ERROR, e.getMessage());

		} finally {

			// Cerramos el documento.
			documento.close();
		}*/
	}
	
	public void mostrarVentana() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.config));
		
		dialog.setItems(tablet, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				bundle =  new Bundle();
				bundle.putString("usuario", us);
				bundle.putInt("id", id);
				bundle.putString("direccion", direccion);
				
				SharedPreferences.Editor editor = sp.edit();
				
				System.out.println(tablet[which]);
				editor.putString("numt", tablet[which]);
				editor.commit();
				
				startActivity(new Intent(getApplicationContext(), Descarga.class).putExtras(bundle));
				onDestroy();
			}
			
		});
		/*final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.config));
		mEdittText = new EditText(getApplicationContext());
		mEdittText.setInputType(InputType.TYPE_CLASS_TEXT);
		mEdittText.setHint(getResources().getString(R.string.config));
		mEdittText.setTextColor(android.R.color.black);
		dialog.setView(mEdittText);
		
		dialog.setTitle(getResources().getString(R.string.config)).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(!mEdittText.getText().toString().equalsIgnoreCase("")) {
					
					bundle =  new Bundle();
					bundle.putString("usuario", us);
					bundle.putInt("id", id);
					bundle.putString("direccion", direccion);
					
					SharedPreferences.Editor editor = sp.edit();
					System.out.println(mEdittText.getText().toString());
					editor.putString("numt", mEdittText.getText().toString());
					editor.commit();
					
					startActivity(new Intent(getApplicationContext(), Descarga.class).putExtras(bundle));
					onDestroy();
				}
			}
		});*/
		dialog.create().show();
		
	}
	
	public static File crearFichero(String nombreFichero) throws IOException {
		File ruta = getRuta();
		File fichero = null;
		if (ruta != null)
			fichero = new File(ruta, nombreFichero);
		return fichero;
	}
	
	public static File getRuta() {

		// El fichero ser� almacenado en un directorio dentro del directorio
		// Descargas
		File ruta = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);

			if (ruta != null) {
				if (!ruta.mkdirs()) {
					if (!ruta.exists()) {
						return null;
					}
				}
			}
		} else {
		}

		return ruta;
	}
	
	public void consultaTabletas() {
		int co = 0;
		GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion",null,1);
    	SQLiteDatabase db = gestion.getReadableDatabase();
    	try {
    		Cursor c = db.rawQuery("SELECT * FROM c_tabletas", null);
			if (c.moveToFirst()) {
				tablet = new String[c.getCount()];
				do {
					tablet[co] = c.getString(2);
					co++;
				} while (c.moveToNext());
			}
			c.close();
		} catch (SQLiteException e) {
			Log.e("SQLiteException ", e.getMessage());
		}
    	finally {
    		db.close();
    	}
	}
	
	/*public class Update extends AsyncTask<String,String,String> {
	    

	    @Override
	    protected void onPreExecute (){
	        super.onPreExecute();
	        pd = new ProgressDialog(Descarga.this);
	        pd.setMessage("Buscando Actualizaciones, Espere");
	        pd.setTitle("Conectando al servidor");
	        pd.show();
	        pd.setCancelable(false);

	        try {
	            /*Cursor c = db.rawQuery("SELECT * FROM usuario", null);
	            c.moveToLast();
	            token = c.getString(1);
	            id_user = c.getString(2);
	            Log.e("TOKEN",token);*
	        }catch (CursorIndexOutOfBoundsException e){
	            Log.e("", "error al obtener token");
	        }

	    }

	    @SuppressLint("SdCardPath")
		protected String doInBackground(String... sUrl) {

	        String path = "/sdcard/Archivo.apk";
	        StringBuilder result = new StringBuilder();
	        //String url_base = "";
	        try {
	            //Realizamos la consulta a la API

	            URL link = new URL("http://10.10.23.54/infracciones/infracciones/sistema/update.php?foco=rojo");
	            urlConnection = (HttpURLConnection) link.openConnection();
	            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                result.append(line);
	            }
	            JSONObject obj = new JSONObject(result.toString());
	            JSONArray jsonArray = obj.optJSONArray("data");
	            int length = jsonArray.length();
	            for (int i = 0; i < length; i++) {
	                JSONObject jsonObject = jsonArray.getJSONObject(i);
	                 liga = jsonObject.optString("link").toString();
	                 version = jsonObject.optString("version").toString();
	                 System.err.println("Mensaje con datos " + liga+" "+version + " " + sUrl[0]);

	            }
	            if (new Double(sUrl[0]).compareTo(new Double(version))==-1){
	                Log.e("Mensaje con datos",liga+" "+version +sUrl[0]);
	                try {
	                    URL url = new URL(liga);
	                    URLConnection connection = url.openConnection();
	                    connection.connect();

	                    int fileLength = connection.getContentLength();

	                    // download the file
	                    InputStream input = new BufferedInputStream(url.openStream());
	                    OutputStream output = new FileOutputStream(path);

	                    byte data[] = new byte[1024];
	                    long total = 0;
	                    int count;
	                    while ((count = input.read(data)) != -1) {
	                        total += count;
	                        publishProgress(String.valueOf((int) (total * 100 / fileLength)));
	                        output.write(data, 0, count);
	                    }

	                    output.flush();
	                    output.close();
	                    input.close();
	                } catch (Exception e) {
	                    Log.d("Cargando", "Fallo conexion");
	                    path = "No";
	                }
	            }else {
	                path ="No";
	            }


	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
	            e.printStackTrace();
	        } catch (NullPointerException e){

	        }
	        return path;
	        }
	        // begin the installation by opening the resulting file
	    
	    @Override
	    protected void onPostExecute(String path) {
	    	pd.dismiss();
	        if (path.equals("No")){
	            Toast toast = Toast.makeText(getApplicationContext(), "No existen Actualizaciones", Toast.LENGTH_LONG);
	            toast.setGravity(Gravity.CENTER, 0, 0);
	            toast.show();
	        }else {
	            Intent i = new Intent();
	            i.setAction(Intent.ACTION_VIEW);
	            i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
	            Log.d("Cargando", "Se instalara el nuevo APK");
	            startActivity(i);
	        }

	    }

	    @Override
	    protected void onCancelled() {
	        super.onCancelled();
	    }
	}*/
	
	public class Ingresar extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			insertar();
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
			toast.setGravity(0, 0, 15);
			toast.show();
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			onDestroy();
		}
		
	}
	
	public class Descargas extends AsyncTask<String, String, String> {
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
			toast.setGravity(0, 0, 15);
			toast.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (!conn.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {	
					if (conn.validarConexion(getApplicationContext())) {
						descargarLevantamiento();
						descargarDetalle();
						descargarFotografia();
						msj = "Datos enviados al servidor";
					}
					else 
						msj =  "No se encontro conexion a internet";
				}
				else 
					msj = "No se pudo conectar con el servidor";
			return msj;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}
	
	/*
	 * if(VerificarFoto().equals("")){
					if (!conn.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
						if (conn.validarConexion(getApplicationContext())) {
							//foto();
							fotografias();
							msj = "La(s) Imagen(es) se ha(n) enviado al servidor";
						}
						else 
							msj = "No se encontro conexion a internet";
					}else
						msj = "No se pudo conectar con el servidor";
				}
				else 
					msj = "No hay datos guardados en el dispositivo";
				Toast toast = Toast.makeText(Descarga.this, msj, Toast.LENGTH_SHORT);
				toast.setGravity(0, 0, 15);
				toast.show();
	 */
	
	public class EFoto extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			if(VerificarFoto().equals("")){
				if (!conn.search("http://10.10.23.54/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					if (conn.validarConexion(getApplicationContext())) {
						//foto();
						fotografias();
						msj = "La(s) Imagen(es) se ha(n) enviado al servidor";
					}
					else 
						msj = "No se encontro conexion a internet";
				}else
					msj = "No se pudo conectar con el servidor";
			}
			else 
				msj = "No hay datos guardados en el dispositivo";
			return msj;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast toast = Toast.makeText(Descarga.this, result, Toast.LENGTH_SHORT);
			toast.setGravity(0, 0, 15);
			toast.show();
		}
		
	}
	
}
