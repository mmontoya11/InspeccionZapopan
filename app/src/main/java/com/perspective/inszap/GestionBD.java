package com.perspective.inszap;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author Emmanuel
 *
 */

public class GestionBD extends SQLiteOpenHelper{
	
	private String result,tipo;
	private int aux = 0;
	private Connection conn = new Connection();
	private JSONArray jarray,jsonA;
	private JSONObject json_data,jObject;
	private StringBuilder sb = new StringBuilder();
	
	//String sqlCrearTablaDireccion = "create table Direcciones(id_direcciones int not null PRIMARY KEY, direccion TEXT)";
	String sqlCrearTablaDireccion = "create table C_Direccion(id_c_direccion INTEGER PRIMARY KEY AUTOINCREMENT, direccion TEXT,capturo Text,fecha numeric)";
	String sqlCrearTablaInfraccion = "create table C_infraccion(id_c_infraccion INTEGER PRIMARY KEY AUTOINCREMENT, id_c_direccion int, infraccion TEXT not null, unidad TEXT,codigo TEXT,zonificacion TEXT,reglamento TEXT,lpa TEXT,ordenamiento_eco TEXT,NAE TEXT,LEEEPA TEXT,inciso text,monto_minimo money,monto_maximo money,unidades text,id_c_salario int,vigente text,ano numeric, id_c_ley_ingresos int, capturo text, fecha text)";
	//String sqlCrearTablaInfraccion = "create table infracciones(id_infraccion int not null, id_direccion int, infraccion TEXT not null, unidad TEXT,codigo TEXT,zonificacion TEXT,ordenamiento TEXT,lpa TEXT,monto money, constraint pk_infraccion primary key(id_infraccion),constraint fk_infraccion_direccion foreign key(id_direccion) references Direcciones(id_direcciones))";
	String sqlCrearTablaInspectores = "create table C_inspector(id_c_inspector INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT not null,ife TEXT not null, vigente text, no_empleado int not null,vigencia NUMERIC,id_c_direccion int not null,contrasena text,capturo text,fecha numeric)";
	//String sqlCrearTablaInspectores = "create table Inspectores(id_inspector int not null PRIMARY KEY, nombre TEXT not null,ife TEXT not null,no_empleado int not null,vigencia NUMERIC,id_direccion int not null,contrasena text,constraint fk_inspector_direccion foreign key(id_direccion) references Direcciones(id_direcciones))";
	String sqlCrearTablaActas = "create table Acta(id_acta INTEGER PRIMARY KEY AUTOINCREMENT,numero_acta TEXT, id_c_direccion int,id_c_inspector int,fecha numeric,infraccion int,numero text, CONSTRAINT FK_acta_direccion foreign key(id_c_direccion) references Direcciones(id_direccion))";
	//String sqlCrearTablaOrdenamientos = "create table Ordenamientos(id_ordenamiento int PRIMARY KEY,ordenamiento TEXT,campo TEXT,id_direccion int,CONSTRAINT fk_ordenamiento_direcciones foreign key(id_direccion) references Direcciones(id_direcciones))";
	String sqlCrearTablaOrdenamientos = "create table C_ordenamiento(id_c_ordenamiento INTEGER PRIMARY KEY AUTOINCREMENT,ordenamiento TEXT,campo TEXT,id_c_direccion int,capturo text, fecha numeric)";
	String sqlCrearTablaUsuarios = "create table usuario(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT,password TEXT,ap_paterno text, ap_materno text,id_dependencia INTEGER,id_puesto INTEGER,email TEXT,fecha_creacion TEXT,fecha_baja TEXT, vigencia TEXT,estatus TEXT,disponible TEXT, hora_disponible TEXT,id_solicitud INTEGER, posicion TEXT, id_c_direccion INTEGER, apellido_p TEXT, apellido_m TEXT,nombre_usuario TEXT,puesto TEXT, contrasena TEXT)";
	//String sqlCrearTablaLevantamientos = "create table Levantamientos(id_levantamiento int not null,numero_acta int,infraccion int,id_direccion int,fecha numeric,hora_inicio time,longitud float,latitud float,orden_vista int,fecha_orden numeric,id_inspector1 int,id_inspector2 int,nombre_visitado text,se_identifica text,manifiesta_ser text,fraccionamiento text,calle text,numero_ext text,numrero_int text,apellidop_prop text,apellidom_prop text,nprop_razonsocial text,nombre_testigo1 text,ife_testigo1 text,designado_por1 text,nombre_testigo2 text,ife_testigo2 text,designado_por2 text,hechos text,infracciones text,id_infracciones text,uso_suelo text,densidad text,manifiesta text,gravedad int,dias_plazo int,fecha_plazo numeric,hora_termino time,id_fotografias int,CONSTRAINT pk_actas primary key(id_levantamiento),CONSTRAINT fk_actas_direccion foreign key(id_direccion) references Direcciones(id_direcciones))";
	String sqlCrearTablaLevantamientos = "create table Levantamiento(id_levantamiento INTEGER not null PRIMARY KEY AUTOINCREMENT,numero_acta text,numero_citatorio TEXT,infraccion int,tipo_acta text, id_c_direccion int,fecha numeric,hora_inicio time,longitud float,latitud float,orden_vista int,fecha_orden_v numeric,Zona text,id_c_inspector1 int,id_c_inspector2 int,nombre_visitado text,se_identifica text,manifiesta_ser text,fraccionamiento text,calle text,numero_ext text,numero_int text,apellidop_prop text,apellidom_prop text,nombre_razon text,nombre_testigo1 text,ife_testigo1 text,designado_por1 text,nombre_testigo2 text,ife_testigo2 text,designado_por2 text,uso_catalogo text,hechos text,infracciones text,id_c_infraccion text,uso_suelo text,densidad text,manifiesta text,gravedad int,dias_plazo int,fecha_plazo numeric,hora_termino time,tipo_visita TEXT,id_pago int,pago numeric, fecha_pago numeric, estatus text,condominio TEXT,manzana TEXT,lote TEXT,capturo text,fecha_atiende_juez numeric, fecha_cancelacion numeric,fecha_efectua_multa numeric, vigencia_multa int, fecha_vigencia numeric,multa text,observaciones text, referencia TEXT,status TEXT,id_c_inspector3 integer,id_c_inspector4 integer,id_c_inspector5 integer,id_c_inspector6 integer)";
	String sqlCrearTablaFotografias = "Create table Fotografia(id_fotografia INTEGER PRIMARY KEY AUTOINCREMENT, id_levantamiento int,numero_acta text,archivo text,descripcion text,enviado text,estatus TEXT)";
	String sqlCrearTablaDiasFestivos = "create table C_dia_no_habil(id_c_dia_no_habil INTEGER PRIMARY KEY AUTOINCREMENT,Dia_no_habil text,capturo TEXT,fecha numeric)";
	String sqlCrearTablaLey = "create table C_ley_ingresos(id_c_ley_ingresos INTEGER PRIMARY KEY AUTOINCREMENT,id_c_direccion int,nombre_completo text,articulo text,fraccion text,ano numeric,capturo text, fecha numeric)";
	String sqlCrearTablaZonas = "create table C_zonas(id_c_zonas INTEGER PRIMARY KEY AUTOINCREMENT,id_c_direccion int, zona text,descripcion text,capturo text, fecha numeric)";
	String sqlCrearTablsDetalleINFRACCION = "create table Detalle_infraccion(id_detalle_infraccion INTEGER PRIMARY KEY AUTOINCREMENT,id_levantamiento int, numero_acta text, id_c_infraccion int, cantidad numeric,tipo_sancion TEXT,sancion numeric,observaciones Text,estatus TEXT)";
	String sqlCrearTablaVisitadoM = "create table C_visitado_manifiesta(id_c_visitado_manifiesta INTEGER PRIMARY KEY AUTOINCREMENT,id_c_direccion INTEGER,manifiesta TEXT,capturo TEXT,fecha NUMERIC)";
	String sqlCrearTablaVisitadoI = "create table C_visitado_identifica(id_c_visitado_identifica INTEGER PRIMARY KEY AUTOINCREMENT,id_c_direccion INTEGER,identificacion TEXT,capturo TEXT,fecha NUMERIC)";
	String sqlCrearTablaUso = "create table C_uso_suelo(id_c_uso_suelo INTEGER PRIMARY KEY AUTOINCREMENT,id_c_direccion INTEGER,uso_suelo TEXT,capturo TEXT,fecha NUMERIC)";
	String sqlCrearTablaPoblacion = "Create table C_poblacion(id_c_poblacion INTEGER PRIMARY KEY AUTOINCREMENT, poblacion TEXT)";
	String sqlCrearTablaFraccionamiento ="Create table C_fraccionamiento(id_c_fraccionamiento INTEGER PRIMARY KEY AUTOINCREMENT, fraccionamiento TEXT)";
	String sqlCrearTablaNumeroActa = "Create table numero_acta(id_numero_acta INTEGER PRIMARY KEY AUTOINCREMENT,numero_acta TEXT, inspector TEXT, fecha TEXT)";
	
	String sqlD = "DROP TABLE Direcciones;DROP TABLE infracciones;DROP TABLE Inspectores;DROP TABLE Ordenamientos;";
	
	
	public GestionBD(Context context, String name, CursorFactory factory,int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCrearTablaDireccion);
		db.execSQL(sqlCrearTablaInfraccion);
		db.execSQL(sqlCrearTablaInspectores);
		db.execSQL(sqlCrearTablaActas);
		db.execSQL(sqlCrearTablaOrdenamientos);
		db.execSQL(sqlCrearTablaUsuarios);
		db.execSQL(sqlCrearTablaLevantamientos);
		db.execSQL(sqlCrearTablaFotografias);
		db.execSQL(sqlCrearTablaDiasFestivos);
		db.execSQL(sqlCrearTablaZonas);
		db.execSQL(sqlCrearTablsDetalleINFRACCION);
		db.execSQL(sqlCrearTablaVisitadoM);
		db.execSQL(sqlCrearTablaVisitadoI);
		db.execSQL(sqlCrearTablaUso);
		db.execSQL(sqlCrearTablaLey);
		db.execSQL(sqlCrearTablaPoblacion);
		db.execSQL(sqlCrearTablaFraccionamiento);
		db.execSQL(sqlCrearTablaNumeroActa);
		
		Log.i("Gestionar", sqlCrearTablaDireccion);
		Log.i("Gestionar", sqlCrearTablaInfraccion);
		Log.i("Gestionar", sqlCrearTablaInspectores);
		Log.i("Gestionar", sqlCrearTablaOrdenamientos);
		Log.i("Gestionar", sqlCrearTablaLevantamientos);
		Log.i("Gestionar", sqlCrearTablaFotografias);
		Log.i("Gestionar", sqlCrearTablaDiasFestivos);
		Log.i("Gestionar", sqlCrearTablaActas);
		Log.i("Gestionar", sqlCrearTablaZonas);
		Log.i("Gestionar", sqlCrearTablsDetalleINFRACCION);
		Log.i("Gestionar", sqlCrearTablaVisitadoM);
		Log.i("Gestionar", sqlCrearTablaVisitadoI);
		Log.i("Gestionar", sqlCrearTablaUso);
		Log.i("Gestionar", sqlCrearTablaLey);
		Log.i("Gestionar", sqlCrearTablaNumeroActa);
		
		ingresarDireccion(db);
		ingresarInfracciones(db);
		ingresarInspectores(db);
		ingresarOrdenamientos(db);
		ingresarZonas(db);
		ingresarVisitadoM(db);
		ingresarVisitadoI(db);
		ingresarUsoSuelo(db);
		ingresarDiaNo(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Se elimina la version anterior de la tabla
		/*try {
			db.execSQL("DROP TABLE IF EXISTS C_dia_no_habil");
			db.execSQL("DROP TABLE IF EXISTS C_Direccion");
			db.execSQL("DROP TABLE IF EXISTS C_infracciones");
			db.execSQL("DROP TABLE IF EXISTS C_inspector");
			db.execSQL("DROP TABLE IF EXISTS C_ordenamientos");
			db.execSQL("DROP TABLE IF EXISTS c_puestos");
			db.execSQL("DROP TABLE IF EXISTS C_zonas");
			db.execSQL("DROP TABLE IF EXISTS C_ley_ingresos");
		
		}catch (SQLiteException e) {
			System.err.println(e.getMessage());
		}
		finally {
			db.close();
		}*/
	}
	
	public String tablas() {
		//result = conn.search("http://172.20.246.249:8080/serverSQL/getTablas.php");
		result = conn.search("http://10.84.35.153:8080/serverSQL/getTablas.php");
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
					//result = conn.buscarTabla(json_data.getString("Tabla"), "http://172.20.246.249:8080/serverSQL/getCunsultaTabla.php");
					result = conn.buscarTabla(json_data.getString("Tabla"), "http://10.84.35.153:8080/serverSQL/getCunsultaTabla.php");
					this.jsonA = new JSONArray(result);
					for (int j = 0; j < jsonA.length(); j++) {
						if (j != 0) {
							sb.append(",");
						}
						this.jObject = jsonA.getJSONObject(j);
						
						if(jObject.getString("Tipo").equalsIgnoreCase("int")) 
							tipo = "INTEGER";
						else if(jObject.getString("Tipo").equalsIgnoreCase("datetime") | jObject.getString("Tipo").equalsIgnoreCase("nvarchar") | jObject.getString("Tipo").equalsIgnoreCase("varchar") | jObject.getString("Tipo").equalsIgnoreCase("nvarchar")) 
							tipo = "TEXT";
						
						sb.append(jObject.getString("columna") + " " + tipo);
					}
				}
			}
			return sb.toString();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return sb.toString();
	}
	
	/*public List<Levantamiento> getLevantamiento(String condicion,SQLiteDatabase db) {
		if(TextUtils.isEmpty(condicion)) {
			condicion = "1=1";
		}
		List<Levantamiento> levantamiento = new ArrayList<Levantamiento>();
		String sql = "select * from Levantamiento where " + condicion;
		
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()) {
			System.out.println(sql);
			do {
				Levantamiento levantamientos = cursorToLevantamiento(cursor);
				levantamiento.add(levantamientos);
			} while (cursor.moveToNext());
		}
		return levantamiento;
	}
	
	public Levantamiento cursorToLevantamiento(Cursor cursor) {
		//numero_citatorio TEXT,infraccion int,tipo_acta text,fecha numeric,hora_inicio time,longitud float,latitud float,orden_vista int,fecha_orden_v numeric,Zona text,nombre_visitado text,se_identifica text,manifiesta_ser text,fraccionamiento text,calle text,numero_ext text,numero_int text,apellidop_prop text,apellidom_prop text,nombre_razon text,nombre_testigo1 text,ife_testigo1 text,designado_por1 text,nombre_testigo2 text,ife_testigo2 text,designado_por2 text,uso_catalogo text,hechos text,infracciones text,id_c_infraccion text,uso_suelo text,densidad text,manifiesta text,gravedad int,dias_plazo int,fecha_plazo numeric,hora_termino time,tipo_visita TEXT,id_pago int,pago numeric, fecha_pago numeric, estatus text,condominio TEXT,manzana TEXT,lote TEXT,capturo text,fecha_atiende_juez numeric, fecha_cancelacion numeric,fecha_efectua_multa numeric, vigencia_multa int, fecha_vigencia numeric,multa text,observaciones text, referencia TEXT
		return new Levantamiento(cursor.getInt(cursor.getColumnIndex("id_c_direccion")), cursor.getInt(cursor.getColumnIndex("id_c_inspector1")), cursor.getInt(cursor.getColumnIndex("id_c_inspector2")), cursor.getString(cursor.getColumnIndex("numero_acta")), cursor.getString(cursor.getColumnIndex("nombre_visitado")), cursor.getString(cursor.getColumnIndex("se_identifica")), cursor.getString(cursor.getColumnIndex("manifiesta_ser")), cursor.getString(cursor.getColumnIndex("fraccionamiento")), cursor.getString(cursor.getColumnIndex("calle")), cursor.getString(cursor.getColumnIndex("numero_ext")), cursor.getString(cursor.getColumnIndex("numero_int")), cursor.getString(cursor.getColumnIndex("nombre_razon")), cursor.getString(cursor.getColumnIndex("apellidop_prop")), cursor.getString(cursor.getColumnIndex("apellidom_prop")), cursor.getInt(cursor.getColumnIndex("id_c_competencia")),cursor.getString(cursor.getColumnIndex("fecha")),cursor.getString(cursor.getColumnIndex("entre_calle1")),cursor.getString(cursor.getColumnIndex("entre_calle2")),cursor.getString(cursor.getColumnIndex("responsable_obra")),cursor.getString(cursor.getColumnIndex("registro_responsable")),cursor.getString(cursor.getColumnIndex("identifica")));
	}*/
	
	public void ingresarDireccion(SQLiteDatabase db){
    	//db = this.getWritableDatabase();
    	try{
    		if(db != null){
    	
    			ContentValues cv = new ContentValues();
    			
    			cv.put("id_c_direccion", 1);
    			cv.put("direccion", "Administracion");
    			cv.put("capturo","");
    			cv.put("fecha","");
    			
    			db.insert("C_Direccion", null, cv);
    		
    		}
    	}catch (Exception e) {
    		Log.i("insertar", e.getMessage());
		}
    	
    	/*String msj = (n > 0) ? "Se registraron 0" : "No se registraron";
    	Log.i("Direccion1", msj);*/
    	
    }
    
    public void ingresarInspectores(SQLiteDatabase db){
    	//db = this.getWritableDatabase();
    	
    	if(db != null){
    		
    		ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_inspector", 1);
    		cv.put("nombre", "administrador");
    		cv.put("ife", "");
    		cv.put("no_empleado", 2);
    		cv.put("vigencia", "");
    		cv.put("id_c_direccion", 1);
    		cv.put("contrasena", "4dm1n");
    		cv.put("capturo","");
			cv.put("fecha","");
			cv.put("vigente", "S");
    	
    		db.insert("C_inspector", null, cv);
    		
    	}
    	
    	
    	//String msj = (n > 0) ? "Se registraron" : "No se registraron";
    	//Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    	
    	
    }
    
    /*public void ingresarUsuarios(){
    	long n = 0;
    	GestionBD gestionarDB =  new GestionBD(this, "Infraccion", null, 1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
    	
    	if(db != null){
    	
    		ContentValues cv = new ContentValues();
    		cv.put("id_usuario", 1);
    		cv.put("nomnre", "Roberto");
    		cv.put("ap_paterno", "Lopez");
    		cv.put("ap_materno", "Sanchez");
    		cv.put("usuario", "robls");
    		cv.put("contrasena", "12345");
    		cv.put("id_direccion", 1);
    	
    		n = db.insert("Usuarios", null, cv);
    		
    		cv.put("id_usuario", 2);
    		cv.put("nomnre", "Alvaro");
    		cv.put("ap_paterno", "Meneces");
    		cv.put("ap_materno", "Lira");
    		cv.put("usuario", "alml");
    		cv.put("contrasena", "12345");
    		cv.put("id_direccion", 2);
    	
    		n = db.insert("Usuarios", null, cv);
    		
    	}
    	
    	
    	String msj = (n > 0) ? "Se registraron" : "No se registraron";
    	Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    	db.close();
    	
    	
    }*/
    
    public void ingresarZonas(SQLiteDatabase db){
    	//db = this.getWritableDatabase();
    	
    	if(db != null){
    		
    		ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_zonas", 1);
    		cv.put("id_c_direccion", 1);
    		cv.put("zona", "1A");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 2);
    		cv.put("id_c_direccion", 1);
    		cv.put("zona", "1B");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 3);
    		cv.put("id_c_direccion", 1);
    		cv.put("zona", "1C");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 4);
    		cv.put("id_c_direccion", 1);
    		cv.put("zona", "2");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 5);
    		cv.put("id_c_direccion", 1);
    		cv.put("zona", "3");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 6);
    		cv.put("id_c_direccion", 2);
    		cv.put("zona", "4");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 7);
    		cv.put("id_c_direccion", 2);
    		cv.put("zona", "5");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 8);
    		cv.put("id_c_direccion", 2);
    		cv.put("zona", "6");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 9);
    		cv.put("id_c_direccion", 2);
    		cv.put("zona", "7");
    		cv.put("descripcion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_zonas", null, cv);
    		
    		cv.put("id_c_zonas", 10);
    		cv.put("id_c_direccion", 5);
    		cv.put("zona", "3");
    		cv.put("descripcion", "4");
    		cv.put("capturo", "USUARIO1");
    		cv.put("fecha", "4/08/2012");
    		
    		db.insert("C_zonas", null, cv);
    		
    	}
    	
    	
    }
    
    public void ingresarOrdenamientos(SQLiteDatabase db){
    	//db = this.getWritableDatabase();
    	
    	if(db != null){
    		
    		ContentValues cv = new ContentValues();
    	
    		cv.put("id_c_ordenamiento", 1);
    		cv.put("ordenamiento", "LEY DEL PROCEDIMIENTO ADMINISTRATIVO PARA EL ESTADO DE JALISCO");
    		cv.put("campo", "lap");
    		cv.put("id_c_direccion", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_ordenamiento", null, cv);
    		
    		cv.put("id_c_ordenamiento", 2);
    		cv.put("ordenamiento", "CODIGO URBANO PARA EL ESTADO DE JALISCO");
    		cv.put("campo", "codigo");
    		cv.put("id_c_direccion", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_ordenamiento", null, cv);
    		
    		cv.put("id_c_ordenamiento", 3);
    		cv.put("ordenamiento", "REGLAMENTO ESTATAL DE ZONIFICACION");
    		cv.put("campo", "zonificacion");
    		cv.put("id_c_direccion", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_ordenamiento", null, cv);
    		
    		cv.put("id_c_ordenamiento", 4);
    		cv.put("ordenamiento", "REGLAMENTO DE CONSTRUCCION DEL MUNICIPIO DE TLAJOMULCO DE ZUNIGA");
    		cv.put("campo", "reglamento");
    		cv.put("id_c_direccion", 1);
    		cv.put("capturo", " ");
    		cv.put("fecha", " ");
    	
    		db.insert("C_ordenamiento", null, cv);
    		
    		cv.put("id_c_ordenamiento", 5);
    		cv.put("ordenamiento", "83");
    		cv.put("campo", "82");
    		cv.put("id_c_direccion", 5);
    		cv.put("capturo", "USUARIO1");
    		cv.put("fecha", "");
    	
    		db.insert("C_ordenamiento", null, cv);
    		
    	}
    	
    	//String msj = (n > 0) ? "Se registraron" : "No se registraron";
    	//Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    	
    	
    }
    
    public void ingresarVisitadoM(SQLiteDatabase db) {
    	//db = this.getWritableDatabase();
    	
    	if (db != null) {
    		ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("manifiesta", "Encargado de obra");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_manifiesta", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("manifiesta", "Albañil");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_manifiesta", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("manifiesta", "Trabajador de obra");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_manifiesta", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("manifiesta", "Representante de obra");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_manifiesta", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("manifiesta", "Director Responsable de obra");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_manifiesta", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("manifiesta", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_manifiesta", null, cv);
    		
    	}
    	
    }
    
    public void ingresarVisitadoI(SQLiteDatabase db) {
    	//db = this.getWritableDatabase();
    	
    	if (db != null) {
    		
    		ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("identificacion", "IFE");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_identifica", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("identificacion", "Licencia de conducir");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_identifica", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("identificacion", "Cartilla");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_identifica", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("identificacion", "");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_visitado_identifica", null, cv);
    		
    	}
    	
    }
    
    public void ingresarUsoSuelo(SQLiteDatabase db) {
    	//db = this.getWritableDatabase();
    	
    	if (db != null) {
    		
    		ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("uso_suelo", "Habitacional");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_uso_suelo", null, cv);
    		
    		cv.put("id_c_direccion", 1);
    		cv.put("uso_suelo", "Comercial");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_uso_suelo", null, cv);
    		
    	}
    	
    }
    
    public void ingresarDiaNo(SQLiteDatabase db) {
    	//db = this.getWritableDatabase();
    	if(db != null) {
    		
    		ContentValues cv = new ContentValues();
    		
    		cv.put("dia_no_habil", "17/09/2012");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_dia_no_habil", null, cv);
    		
    		cv.put("dia_no_habil", "2/11/2012");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_dia_no_habil", null, cv);
    		
    		cv.put("dia_no_habil", "17/11/2012");
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_dia_no_habil", null, cv);
    		
    	}
    }
    
    public void ingresarInfracciones(SQLiteDatabase db){
    	//SQLiteDatabase db = gestion.getWritableDatabase();
    	
    	if(db != null){
    	
    		ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_infraccion", 1);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA LICENCIA DE MOVIMIENTO DE TIERRAS, AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "9,30,211,262,263 F-II");
    		cv.put("lpa", "");
    		cv.put("inciso", 1);
    		cv.put("monto_minimo", 1);
    		cv.put("monto_maximo", 3);
    		cv.put("unidades", "TANTOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 2);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA LICENCIA DE DEMOLICION, AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "279,375,376");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "9,30,211,262,263 F-II");
    		cv.put("lpa", "");
    		cv.put("inciso", 1);
    		cv.put("monto_minimo", 1);
    		cv.put("monto_maximo", 3);
    		cv.put("unidades", "TANTOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 3);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA LICENCIA DE CONSTRUCCION, AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "279,375,376");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "9,30,211,262,263 F-II");
    		cv.put("lpa", "");
    		cv.put("inciso", 1);
    		cv.put("monto_minimo", 1);
    		cv.put("monto_maximo", 3);
    		cv.put("unidades", "TANTOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 4);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR ALTERAR FINCAS QUE ESTEN SUJETAS A DISPOSICIONES SOBRE PROTECCION, CONSERVACION DE MONUMENTOS ARQUEOLOGICOS O HISTORICOS, SIN LA LICENCIA CORRESPONDIENTE");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "201,203,208");
    		cv.put("reglamento", "91,262,263 F-IV");
    		cv.put("lpa", "");
    		cv.put("inciso", 3);
    		cv.put("monto_minimo", 5000);
    		cv.put("monto_maximo", 20000);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 5);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR HABITAR O DAR USO A UNA CONSTRUCCION O PARTE DE ELLA SIN HABER OBTENIDO EL CERTIFICADO DE HABITABILIDAD");
    		cv.put("unidad", "");
    		cv.put("codigo", "290,375,376");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "75,262,263 F-VI");
    		cv.put("lpa", "");
    		cv.put("inciso", 4);
    		cv.put("monto_minimo", 1);
    		cv.put("monto_maximo", 1);
    		cv.put("unidades", "TANTOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 6);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR LLEVAR A CABO UNA EDIFICACION SIN CONTAR CON DIRECTOR RESPONSABLE DE OBRA");
    		cv.put("unidad", "");
    		cv.put("codigo", "278,375,376");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "45,46,262,263 F-XI");
    		cv.put("lpa", "");
    		cv.put("inciso", 5);
    		cv.put("monto_minimo", 750);
    		cv.put("monto_maximo", 1800);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 7);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO DAR AVISO DE CAMBIO DE DIRECTOR RESPONSABLE");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "63,262,263 F-XIV");
    		cv.put("lpa", "");
    		cv.put("inciso", 5);
    		cv.put("monto_minimo", 750);
    		cv.put("monto_maximo", 1800);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 8);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO DAR AVISO DE SUSPENSION DE OBRA");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "61,262,263 F-XIV");
    		cv.put("lpa", "");
    		cv.put("inciso", 6);
    		cv.put("monto_minimo", 750);
    		cv.put("monto_maximo", 1500);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 9);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO DAR AVISO DE REINICIO DE OBRA");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "62,262,263 F-XIV");
    		cv.put("lpa", "");
    		cv.put("inciso", 6);
    		cv.put("monto_minimo", 750);
    		cv.put("monto_maximo", 1500);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    		
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 10);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR REALIZAR CONSTRUCCIONES EN CONDICIONES DIFERENTES A LOS PLANOS AUTORIZADOS");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "51,60,262,263 F-X");
    		cv.put("lpa", "");
    		cv.put("inciso", 8);
    		cv.put("monto_minimo", 900);
    		cv.put("monto_maximo", 2300);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 11);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO ENTREGAR LAS AREAS DE CESION PARA DESTINOS");
    		cv.put("unidad", "");
    		cv.put("codigo", "175,176,375,376");
    		cv.put("zonificacion", "127,128,129,130,131,132,133,134,135,136,137,138,139,140");
    		cv.put("reglamento", "");
    		cv.put("lpa", "");
    		cv.put("inciso", 9);
    		cv.put("monto_minimo", 1);
    		cv.put("monto_maximo", 1);
    		cv.put("unidades", "TANTOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 12);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR DAR A UNA FINCA UN USO DE SUELO DISTINTO AL QUE FUE AUTORIZADO");
    		cv.put("unidad", "");
    		cv.put("codigo", "290,375,376");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "");
    		cv.put("lpa", "");
    		cv.put("inciso", 10);
    		cv.put("monto_minimo", 1000);
    		cv.put("monto_maximo", 5000);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 13);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR INCURRIR EN FALSEDAD DE DATOS EN LAS SOLICITUDES DE UNA LICENCIA");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "262,263 F-III");
    		cv.put("lpa", "");
    		cv.put("inciso", 11);
    		cv.put("monto_minimo", 2300);
    		cv.put("monto_maximo", 2900);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 14);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR IMPEDIR QUE EL PERSONAL DE LA DEPENDENCIA COMPETENTE, LLEVE A CABO EL CUMPLIMIENTO DE SUS FUNCIONES");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "256,257,258,262,263 F-I");
    		cv.put("lpa", "");
    		cv.put("inciso", 12);
    		cv.put("monto_minimo", 400);
    		cv.put("monto_maximo", 9000);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 15);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO PRESENTAR LA LICENCIA DE CONSTRUCCION AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,259,262,263 F-V");
    		cv.put("lpa", "");
    		cv.put("inciso", 14);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 16);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO PRESENTAR LOS PLANOS AUTORIZADOS AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,259,262,263 F-V");
    		cv.put("lpa", "");
    		cv.put("inciso", 14);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 17);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO PRESENTAR LA BITACORA OFICIAL DE OBRA AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,259,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 14);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 18);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE PANCARTA DE OBRA  AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,212,213,259,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 15);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 19);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE PLANOS AUTORIZADOS AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,259,262,263 F-V");
    		cv.put("lpa", "");
    		cv.put("inciso", 15);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 20);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE BITACORA OFICIAL DE OBRA AL MOMENTO DE LA INSPECCION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,259,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 15);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 21);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE FIRMAS EN LA BITACORA DE OBRA");
    		cv.put("unidad", "FIRMA(S)");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "211,259,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 16);
    		cv.put("monto_minimo", 150);
    		cv.put("monto_maximo", 300);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 22);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR ADELANTAR FIRMAS EN LA BITACORA DE OBRA");
    		cv.put("unidad", "FIRMA(S)");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "214,260,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 16);
    		cv.put("monto_minimo", 150);
    		cv.put("monto_maximo", 300);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 23);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FIRMAR LA BITACORA DE OBRA SIN SENALAR EL AVANCE DE LA MISMA");
    		cv.put("unidad", "FIRMA(S)");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "214,260,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 16);
    		cv.put("monto_minimo", 150);
    		cv.put("monto_maximo", 300);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 24);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO TENER ACTUALIZADA LA LICENCIA DE OBRAS RELATIVAS A EDIFICACION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "54,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 17);
    		cv.put("monto_minimo", 1);
    		cv.put("monto_maximo", 3);
    		cv.put("unidades", "TANTOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 25);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE NUMERO OFICIAL");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "42,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 18);
    		cv.put("monto_minimo", 140);
    		cv.put("monto_maximo", 240);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 26);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR NO TENER EL NUMERO OFICIAL EN LUGAR VISIBLE CERCA DE LA ENTRADA AL PREDIO");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "42,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 18);
    		cv.put("monto_minimo", 140);
    		cv.put("monto_maximo", 290);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 27);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE ACOTAMIENTO O BARDEADO");
    		cv.put("unidad", "M. L.");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "228,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 19);
    		cv.put("monto_minimo", 45);
    		cv.put("monto_maximo", 90);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 28);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE SERVICIOS SANITARIOS ADECUADOS EN EL PREDIO DONDE SE LLEVA A CABO UNA OBRA DE EDIFICACION");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "146,262");
    		cv.put("lpa", "");
    		cv.put("inciso", 20);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 600);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 29);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR FALTA DE TAPIALES EN LAS OBRAS DONDE ESTOS SEAN NECESARIOS");
    		cv.put("unidad", "M.L.");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "216,262,263 F-XIII");
    		cv.put("lpa", "");
    		cv.put("inciso", 21);
    		cv.put("monto_minimo", 50);
    		cv.put("monto_maximo", 100);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 30);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR CARECER DE ELEMENTOS DE PROTECCION A LOS CIUDADANOS");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "216,262,263 F-XIII");
    		cv.put("lpa", "");
    		cv.put("inciso", 22);
    		cv.put("monto_minimo", 1000);
    		cv.put("monto_maximo", 4300);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 31);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR ABRIR VANOS O HUECOS PARA PUERTAS O VENTANAS EN MUROS COLINDANTES O DE PROPIEDAD VECINA INVADIENDO PRIVACIA");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "102,262,263 F-II");
    		cv.put("lpa", "");
    		cv.put("inciso", 23);
    		cv.put("monto_minimo", 2000);
    		cv.put("monto_maximo", 8000);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 32);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR ABRIR VANO PARA INGRESO O SALIDA EN LA PARTE POSTERIOR DE UN PREDIO QUE COLINDE CON AREAS PUBLICAS DESTINADAS A ESPACIOS VERDES Y ABIERTOS PARA LA RECREACION Y EL DEPORTE");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "34 F-VI");
    		cv.put("reglamento", "262,263 F-II");
    		cv.put("lpa", "");
    		cv.put("inciso", 24);
    		cv.put("monto_minimo", 500);
    		cv.put("monto_maximo", 2500);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 33);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR TENER UN PREDIO, CONSTRUCCION, EXCAVACION, EDIFICACION O CUALQUIER ELEMENTO CONSTRUCTIVO QUE NO REUNA LAS CONDICIONES DE SEGURIDAD");
    		cv.put("unidad", "");
    		cv.put("codigo", "295,375,376");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "262,263 F-XIII");
    		cv.put("lpa", "");
    		cv.put("inciso", 26);
    		cv.put("monto_minimo", 800);
    		cv.put("monto_maximo", 1900);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 34);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR TENER UN PREDIO, CONSTRUCCION, EXCAVACION, EDIFICACION O CUALQUIER ELEMENTO CONSTRUCTIVO QUE CAUSE DANOS A FINCAS VECINAS");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "262,263 F-XII");
    		cv.put("lpa", "");
    		cv.put("inciso", 26);
    		cv.put("monto_minimo", 800);
    		cv.put("monto_maximo", 1900);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 35);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR TENER PATIOS CON DIMENSIONES MENORES A LAS AUTORIZADAS");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "142,262,263 F-X");
    		cv.put("lpa", "");
    		cv.put("inciso", 27);
    		cv.put("monto_minimo", 650);
    		cv.put("monto_maximo", 1000);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 36);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR TENER ESPACIOS SIN VENTILACION E ILUMINACION ADECUADA");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "141,262,263 F-X");
    		cv.put("lpa", "");
    		cv.put("inciso", 28);
    		cv.put("monto_minimo", 850);
    		cv.put("monto_maximo", 1950);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 37);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR DANAR O AFECTAR EN CUALQUIER FORMA CUALQUIER PARTE DE LA VIA PUBLICA");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "240,248,262,263 F-XII");
    		cv.put("lpa", "");
    		cv.put("inciso", 30);
    		cv.put("monto_minimo", 400);
    		cv.put("monto_maximo", 1900);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 38);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR OCUPAR CON CUALQUIER MATERIAL U OBJETO, LA VIA PUBLICA");
    		cv.put("unidad", "M2");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "240,248,262,263 F-XII");
    		cv.put("lpa", "");
    		cv.put("inciso", 31);
    		cv.put("monto_minimo", 110);
    		cv.put("monto_maximo", 450);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 39);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR INVASION DE VIA PUBLICA");
    		cv.put("unidad", "M2");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "240,248,262,263 F-XII");
    		cv.put("lpa", "");
    		cv.put("inciso", 33);
    		cv.put("monto_minimo", 1200);
    		cv.put("monto_maximo", 3200);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 40);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR INVASION EN AREAS DE RESTRICCION PARTICULAR");
    		cv.put("unidad", "M2");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,79,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,119,120,121,122,123,124,125");
    		cv.put("reglamento", "3 F-XLV,262,263 F-VII");
    		cv.put("lpa", "");
    		cv.put("inciso", 34);
    		cv.put("monto_minimo", 0);
    		cv.put("monto_maximo", 0);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 41);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR OMISION DE CAJONES DE ESTACIONAMIENTO CONFORME AL PROYECTO AUTORIZADO");
    		cv.put("unidad", "CAJON(ES)");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "341,351");
    		cv.put("reglamento", "170,262,263 F-IX");
    		cv.put("lpa", "");
    		cv.put("inciso", 36);
    		cv.put("monto_minimo", 0);
    		cv.put("monto_maximo", 0);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 42);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR LA OMISION DE CONSTRUCCION DE OBRAS DE CAPTACION PARA INFILTRAR LAS AGUAS PLUVIALES QUE SE PRECIPITEN EN EL PREDIO");
    		cv.put("unidad", "VECES EL SALARIO MINIMO POR LA ZONA GEOGRAFICA CORRESPONDIENTE");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("reglamento", "108,262,263 F-X");
    		cv.put("lpa", "");
    		cv.put("inciso", 37);
    		cv.put("monto_minimo", 5000);
    		cv.put("monto_maximo", 10000);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", 1);
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 43);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR ALTERAR, MODIFICAR, DESVIAR O INVADIR, CAUCE DE CANALES, ARROYOS, RIOS, LAGUNAS Y CUALQUIER OTRO CUERPO DE AGUA");
    		cv.put("unidad", "VECES EL SALARIO MINIMO POR LA ZONA GEOGRAFICA CORRESPONDIENTE");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "222 F-I");
    		cv.put("reglamento", "");
    		cv.put("lpa", "");
    		cv.put("inciso", 38);
    		cv.put("monto_minimo", 50);
    		cv.put("monto_maximo", 500);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", 1);
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 44);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR LA OMISION DE JARDINES EN ZONAS DE RESTRICCION PARTICULAR EN OBRAS NUEVAS");
    		cv.put("unidad", "M2");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,79,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,119,120,121,122,123,124,125");
    		cv.put("reglamento", "92,262,263 F-VIII");
    		cv.put("lpa", "");
    		cv.put("inciso", 39);
    		cv.put("monto_minimo", 300);
    		cv.put("monto_maximo", 750);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", "");
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		cv.put("id_c_infraccion", 45);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "POR VIOLAR O RETIRAR SELLOS DE CLAUSURA");
    		cv.put("unidad", "VECES EL SALARIO MINIMO POR LA ZONA GEOGRAFICA CORRESPONDIENTE");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,79,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,119,120,121,122,123,124,125");
    		cv.put("reglamento", "92,262,263 F-VIII");
    		cv.put("lpa", "");
    		cv.put("inciso", 42);
    		cv.put("monto_minimo", 100);
    		cv.put("monto_maximo", 500);
    		cv.put("unidades", "PESOS");
    		cv.put("id_c_salario", 1);
    		cv.put("vigente", "S");
    		cv.put("ano", "2012");
    		cv.put("id_c_ley_ingresos", 1);
    		cv.put("capturo", "");
    		cv.put("fecha", "");
    	
    		db.insert("C_infraccion", null, cv);
    		
    		
    		
    		
    		
    		/* ContentValues cv = new ContentValues();
    		
    		cv.put("id_c_infraccion", 1);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta licencia de movimiento de tierras, al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "10,132");
    		cv.put("zonificacion", "1");
    		cv.put("ordenamiento", "12,13,106");
    		cv.put("lpa", "67,69,76,123");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 2);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta licencia de demolici��n, al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "375,376,377");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "109,114,115,116,144");
    		cv.put("lpa", "123,127,128");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 3);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta licencia de construcci��n, al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "375,376,377");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "109,114,115,116,144");
    		cv.put("lpa", "123,127,128");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 4);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por alterar fincas que est��n sujetas a disposiciones sobre protecci��n, conservaci��n de monumentos arqueol��gicos o hist��ricos, sin la licencia correspondiente");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "68,69");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 5);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por habitar una construcci��n sin haber obtenido el certificado de habitabilidad");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "201,202,203,204,205,206,207,208,209,210,211");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 6);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por llevar a cabo una edificaci��n sin contar con director responsable de obra");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 7);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no dar aviso de cambio de director responsable");
    		cv.put("unidad", "");
    		cv.put("codigo", "289");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "6,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 8);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no dar aviso de suspensi��n de obra");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "34");
    		cv.put("ordenamiento", "97");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 9);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no dar aviso de reinicio de obra");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "34");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 10);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por realizar construcciones en condiciones diferentes a los planos autorizados");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "360,366,367,368");
    		cv.put("ordenamiento", "14,15,90,91");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 11);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no entregar las ��reas de cesi��n para destinos");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 12);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por incurrir en falsedad de datos en las solicitudes de una licencia");
    		cv.put("unidad", "");
    		cv.put("codigo", "129,279,375,376");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "1,6,14");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 13);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por impedir que el personal de la dependencia competente, lleve a cabo el cumplimiento de sus funciones");
    		cv.put("unidad", "");
    		cv.put("codigo", "277, 284, 375, 376");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14, 15, 19, 20, 72");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 14);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no presentar la licencia de construcci��n al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 15);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no presentar los planos autorizados al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "90");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 16);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no presentar la bit��cora oficial de obra al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 17);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de pancarta de obra  al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 18);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de planos autorizados al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 19);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de bit��cora oficial de obra al momento de la inspecci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 20);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de firmas en la bit��cora de obra");
    		cv.put("unidad", "c/u");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 21);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por adelantar firmas en la bit��cora de obra");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 22);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por firmar la bit��cora de obra sin se��alar el avance de la misma");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 23);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no tener actualizada la licencia de obras relativas a edificaci��n");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,23");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 24);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de n��mero oficial");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,21");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 25);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por no tener el n��mero oficial en lugar visible cerca de la entrada al predio");
    		cv.put("unidad", "m.l.");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "15,90,91,111,112");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 26);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de acotamiento o bardeado");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,20");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 27);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de tapiales en las obras donde ��stos sean necesarios");
    		cv.put("unidad", "m3");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 28);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por carecer de elementos de protecci��n a los ciudadanos");
    		cv.put("unidad", "");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "225,226,227");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 29);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por abrir vanos o huecos para puertas o ventanas en muros colindantes o de propiedad vecina invadiendo privac��a");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 30);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por abrir huecos o vanos para puertas de ingreso o salida en la parte posterior de un predio que colinde con ��reas p��blicas destinadas a espacios verdes y abiertos");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,90");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 31);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por tener un predio, construcci��n, excavaci��n, edificaci��n o cualquier elemento constructivo que no re��na las condiciones de seguridad");
    		cv.put("unidad", "m.l.");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "360,366,367,368");
    		cv.put("ordenamiento", "15,90,91,92,93,94,95");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 32);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por tener un predio, construcci��n, excavaci��n, edificaci��n o cualquier elemento constructivo que cause da��os a fincas vecinas");
    		cv.put("unidad", "");
    		cv.put("codigo", "290,291");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,123");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 33);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por tener patios con dimensiones menores a las autorizadas");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 34);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por tener espacios sin ventilaci��n e iluminaci��n natural");
    		cv.put("unidad", "");
    		cv.put("codigo", "129,279,375,376");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "1,6,14,89");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 35);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por da��ar o afectar en cualquier forma cualquier parte de la v��a p��blica");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "1,14");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 36);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por ocupar con cualquier material u objeto, la v��a p��blica");
    		cv.put("unidad", "");
    		cv.put("codigo", "278,375,376");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 37);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por invasi��n de v��a p��blica");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,104,105");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 38);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por invasi��n en ��reas de restricci��n particular");
    		cv.put("unidad", "");
    		cv.put("codigo", "175,176,177,178,179,180,181,182,183,184,185,186");
    		cv.put("zonificacion", "129,130,131,132,133,134,135,136,137,138,139,140");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 39);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por omisi��n de cajones de estacionamiento conforme al proyecto autorizado");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,100,101");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 40);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por la omisi��n de construcci��n de obras de captaci��n para infiltrar las aguas pluviales que se precipiten en el predio");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 41);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por la omisi��n de jardines en zonas de restricci��n particular");
    		cv.put("unidad", "");
    		cv.put("codigo", "129,375,376");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "1,6,14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 42);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por violar sellos de clausura sin autorizaci��n municipal");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 43);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por dar a una finca un uso de suelo distinto al que fue autorizado");
    		cv.put("unidad", "");
    		cv.put("codigo", "298,375,376");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 44);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por falta de servicios sanitarios en el predio donde se lleva a cabo una obra de edificaci��n");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "34,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 45);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por tener obras inconclusas sin los elementos de seguridad que impidan el ingreso a personas ajenas al predio");
    		cv.put("unidad", "c/u");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "340,341,351");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 46);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por colocar en la v��a p��blica cualquier elemento que resulte perjudicial o peligroso");
    		cv.put("unidad", "c/u");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "340,341,351");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 47);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por invasi��n de propiedad ajena");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_c_infraccion", 48);
    		cv.put("id_c_direccion", 1);
    		cv.put("infraccion", "Por alterar, modificar, desviar o invadir, cauce de canales, arroyos, rios, lagunas y cualquier otro cuerpo de agua");
    		cv.put("unidad", "c/u");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "217");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		 -------------------------------------------*/
    		
    		/*cv.put("id_infraccion", 49);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "tener espacios sin ventilaci��n o iluminaci��n adecuada");
    		cv.put("unidad", "");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "215, 216");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 50);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "tener un predio, construcci��n, excavaci��n, edificaci��n o cualquier elemento constructivo que no re��na las condiciones de seguridad o cause da��os a fincas vecinas");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 51);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "tener construcciones defectuosas, que no re��nan las condiciones de seguridad o causen da��os a fincas vecinas");
    		cv.put("unidad", "");
    		cv.put("codigo", "293,295");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,90,91");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 52);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "tener excavaciones, bardas, puertas, techos y banquetas en condiciones que pongan en peligro la integridad f��sica de personas y veh��culos o la estabilidad de fincas vecinas");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "293,295");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,90,91");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 53);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "tener fachada en mal estado");
    		cv.put("unidad", "");
    		cv.put("codigo", "289,295");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 54);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "tener obras inconclusas, abandonadas o suspendidas, sin los elementos de seguridad que impidan el ingreso a personas ajenas al predio");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15,90");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 55);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "violar o retirar sellos de clausura sin autorizaci��n municipal");
    		cv.put("unidad", "");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 56);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "invasi��n en ��rea de restricci��n en inmuebles de uso habitacional densidad alta, media, baja �� m��nima");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "280,285");
    		cv.put("zonificacion", "34,57,58,59,60");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 57);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "invasi��n en ��rea de restricci��n en inmuebles de uso industrial");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "280,285");
    		cv.put("zonificacion", "34,90");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 58);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "invasi��n en ��rea de restricci��n en inmuebles de uso Comercial, Tur��stico y Servicios");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "280,285");
    		cv.put("zonificacion", "34,69,70,71,72,73");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 59);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "invasi��n en ��rea de restricci��n en inmuebles de uso equipamiento y otros");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "280,285");
    		cv.put("zonificacion", "34,119");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 60);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "invasi��n de colindancia a predio vecino o propiedad ajena");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "280,285");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "14,15");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 61);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "ocupar con cualquier material u objeto, propiedad ajena");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "");
    		cv.put("zonificacion", "");
    		cv.put("ordenamiento", "");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 62);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "colocar en la v��a p��blica cualquier elemento que resulte perjudicial o peligroso");
    		cv.put("unidad", "");
    		cv.put("codigo", "295");
    		cv.put("zonificacion", "224");
    		cv.put("ordenamiento", "26");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 63);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "ocupar con cualquier material u objeto, la v��a p��blica");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "295");
    		cv.put("zonificacion", "224");
    		cv.put("ordenamiento", "26,27,146");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 64);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "ocupar con cualquier material u objeto, la v��a p��blica");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "295");
    		cv.put("zonificacion", "224");
    		cv.put("ordenamiento", "26,27,146");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);
    		
    		cv.put("id_infraccion", 65);
    		cv.put("id_direccion", 1);
    		cv.put("infraccion", "destruir, eliminar o disminuir ��rea de jard��n en banquetas, sin autorizaci��n de la dependencia municipal correspondiente");
    		cv.put("unidad", "m2");
    		cv.put("codigo", "129,279,289");
    		cv.put("zonificacion", "231");
    		cv.put("ordenamiento", "6,14,15,73");
    		cv.put("lpa", "");
    		cv.put("monto", "");
    	
    		n = db.insert("infracciones", null, cv);*/
    		
    	}
    	
    	//String msj = (n > 0) ? "Se registraron" : "No se registraron";
    	//Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    	
    	
    }

}
