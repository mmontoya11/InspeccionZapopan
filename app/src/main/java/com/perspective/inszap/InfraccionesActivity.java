package com.perspective.inszap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONObject;
import com.bixolon.printer.BixolonPrinter;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InfraccionesActivity extends Activity implements OnClickListener, Runnable, OnCheckedChangeListener, OnItemSelectedListener {
	
	private Button btnFecha,btnInicio,btnaceptar,btnTomarF,btnGuardar,btnImprimir,btnConsultar,btnSi,btnNo,btnVisualizar,btnMostrar,btnSalir,tveliminar,tveliminar1,tveliminar2,tveliminar3,tveliminar4,btnmodificar,btnFtp,btnB,btnOrden1,btnVista,btnver1,btnver2,btnver3,btnver4,btnver5,btnver6,btnver7,btnver8,btnver9,btnver10,btnver11,btnver12,btnver13,btnver14,btnver15,btnver16,btnImprimirResum;
	private TextView tvuni,tvuni1,tvuni2,tvuni3,tvuni4,tvTitle,tvTipo,tvEspe,tvOV,tvC,tvEvidencia,tvReg,tvActa,tvMotivo,tvAcomp,tvCondominio,tvNombreComercial,tvALicencia;
	private String s, archivo = "",name,us,ifeI,noI,vigI,ifeA,ifeA1,ifeA2,ifeA3,ifeA4,noA,noA1,noA2,noA3,noA4,vigA,vigA1,vigA2,vigA3,vigA4,AnombreTestigo,ifeTestigo,unidad,/*codigo = "",zonificacion,reglamento,lap,ordenamientoEco,nae,leeepa,*/des,des1="",des2="",des3="",des4="",/*cod="",zon="",reg="",la="",ordeco="",na="",lee="", codi="",zoni="",regla="",l="",oe="",ne = "",leeep = "",*/text = "",regex=",",title,seleccion = "",fecha,hora,id_hechos = "",numero = "", hr,c_fecha = "",tipoActa,result = "",dato,usoCatalogo = "S",msj = "",orde,direccion,ante = "IN",formato = "infraccion",numeroOV="",fechaOV="",competencias = "",regla= "",zon="",ident = "",firma="",idT = "",idT1 = "",medidas1 = "",mConnectedDeviceName = "",competencias1 = "";
	private final String DECLARA = "A su vez, el visitado en ejercicio de su derecho y en uso de la voz declara:"; 
	private int mYear,mMonth,mDay,a,m,di,diaPlazo=0,con = 0,contc = 0,contz = 0,contl = 0,conto = 0, co = 0,foto = 0,id,infrac = 1,id_inspector1,id_inspector2,id_infra,nuevo = 0,pos = 0,infraccion=0,id_inspector3 = 0,id_inspector4 = 0,id_inspector5 = 0,id_inspector6 = 0,idCompetencia1 = 0,idCompetencia2 = 0,idCompetencia3 = 0,idCompetencia4 = 0,idCompetencia5 = 0,conf = 0;
	private Spinner spnombre,spNombreA,spNombreA1,spNombreA2,spNombreA3,spNombreA4,spIdentifica,spManifiesta,spuso,spgravedad,spZona,spdesignado,spdesignado1,spInfraccion,spconsultar,spPoblacion,spFraccionamiento,spIdentificaT,spIdentificaT1,spReglamento,spMedida,spInspectorT,spInspectorT1,spPeticion,spNE;
	private EditText etNum,etFecham,etfecha,etDiaPlazo,etIfeI,etNoI,etVigI,etIfeA,etIfeA1,etIfeA2,etIfeA3,etIfeA4,etNoA,etNoA1,etNoA2,etNoA3,etNoA4,etVigA,etVigA1,etVigA2,etVigA3,etVigA4,etNombreT,etIfeT,etInfraccion,etDesc,etDesc1,etDesc2,etDesc3,etDesc4,etdato,etdato1,etdato2,etdato3,etdato4,desf,desf1,desf2,etSeleccion,etNombreV,etFraccionamiento,etCalle,etNumero,etPropietario,etNombreT1,etIfeT2,etManifiesta,etNuemroInterior,etApellidoP,etApellidoM,etCitatorio,etNumeroActa,etEspecificacion,etDFoto,etDFoto1,etDFoto2,etDFoto3,etVManifiesta,etVIdentifica,etLatitud,etLongitud,etAnoCitatorio,etAnoOrden,etCondominio/*etDensidad*/,etManzana,etLote,etReferencia,etBuscar,etCorreo,etfolio,/*etAlineamiento,*/etConstruccion, etGiro, etMotivo,etOrden1,etEntreC,etEntreC1,etResponsable,etRegistro,etMedida,etArticulo,etInspccionFue,etDFoto4,etDFoto5,etDFoto6,etDFoto7,etDFoto8,etDFoto9,etDFoto10,etDFoto11,etDFoto12,etDFoto13,etDFoto14,etDFoto15,etDFoto16,etDFoto17,etDFoto18,etDFoto19,etLGiro,etAGiro,etAlicencia,etSector,etNombreComercial;
	private LinearLayout lldiv,cons,llNota,llplazo,llreincidencia,llcomp;
	private RelativeLayout rlcampo,rlProp,rlTestA,rlVisita,rlLicencias;
	private RadioGroup /*radiogroup,*/rgReincidencia;
	static final int DATE_DIALOG_ID = 0;
	private boolean desc=false,desc1=false,desc2=false,desc3=false,desc4=false,citatorio,inicio = false, res = false,consu = false,resu = false,resov = false,guarda = false;
	final Calendar c = Calendar.getInstance();
	final Calendar cal = Calendar.getInstance();
	final ArrayList<String> arregloLista = new ArrayList<String>();
	private ArrayList<String> arregloLista1 = new ArrayList<String>();
	private ArrayList<String> arregloLista2 = new ArrayList<String>();
	final ArrayList<String> arregloInfraccion = new ArrayList<String>();
	final ArrayList<String> consultar = new ArrayList<String>();
	final ArrayList<Integer> id_hecho = new ArrayList<Integer>();
	final ArrayList<String> arreglo = new ArrayList<String>();
	final ArrayList<Integer> id_i1 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i2 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i3 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i4 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i5 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i6 = new ArrayList<Integer>();
	final ArrayList<String> vManifiesta = new ArrayList<String>();
	final ArrayList<String> vIdentifica = new ArrayList<String>();
	final ArrayList<String> usoSuelo = new ArrayList<String>();
	final ArrayList<String> poblacion = new ArrayList<String>();
	final ArrayList<String> fraccionamiento = new ArrayList<String>();
	final ArrayList<String> zonas = new ArrayList<String>();
	private ArrayList<String> campos = new ArrayList<String>();
	private ArrayList<String> cmedida = new ArrayList<String>();
	private ArrayList<String> art = new ArrayList<String>();
	private ArrayList<String> numero_acta = new ArrayList<String>();
	private ArrayList<String> orden = new ArrayList<String>();
	private ArrayList<String> peticion = new ArrayList<String>();
	private ArrayList array = new ArrayList();
	private ArrayList<String> zona = new ArrayList<String>();
	private HashSet<Integer> hs = new HashSet<Integer>();
	private double latitud = 0, longitud = 0 ;
	LocationManager mLocationManager;
	Location mLocation;
	MyLocationListener mLocationListener;
	private Location currentLocation = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private JSONArray jArray;
	private JSONObject json_data;
	private Connection conn = new Connection();
	private int idl = 0,idComp = 0,il = 0;
	private ProgressDialog pd;
	private Thread thread = null;
	private ArrayList<String> campo = new ArrayList<String>();
	private String campo1="",campo2="",campo3="",campo4="",campo5="",campo6="",campo7="",campo8="",campo9="",campo0="",campo11="",campo12="",campo13="",campo14 = "",campo15 = "",campo16 = "",campo17 = "",campo18 = "",campo19 = "",campo20 = "",campo21 = "",c1="",c2="",c3="",c4="",c5="",c6="",c7="",c8="",c9="",c0="",c11="",c12="",c13="",c14="",c15="",c16="",c17="",c18="",c19="",c20="",camp1="",camp2="",camp3="",camp4="",camp5="",camp6="",camp7="",camp8="",camp9="",camp0="",camp11="",camp12="",camp13="",camp14="",camp15="",camp16="",camp17="",camp18="",camp19="",camp20="",hech = "los hechos antes descritos, constituyen una infracciÔøΩn a lo dispuesto por los artÔøΩculos:",conti = "Los cuales constituyen infracciÔøΩn de conformidad con lo dispuesto por los artÔøΩculos:",na = "";
	private CheckBox cbFlag,cbFirma;
	private Button rbaper,rborden,rbcitatorio,rbHechos,radioInfraccion;
	private List<Levantamiento> lev = new ArrayList<Levantamiento>();
	private List<String> reglamento = new ArrayList<String>();
	private List<String> competencia = new ArrayList<String>();
	private List<String> campoReg = new ArrayList<String>();
	//private List<String> reg = new ArrayList<String>();campoReg
	private List<Integer> idCompetencia = new ArrayList<Integer>();
	private List<MedidaSeguridad> medidas;
	private List<String> medida = new ArrayList<String>();
	private PopupWindow popupWindow;
	private CheckBox cb;
	private String [] comp;
	private int [] iComp;
	private ArrayAdapter<String> adapter,adapter1;
	public static BixolonPrinter mBixolonPrinter;
	public static final String TAG = "BixolonPrinterSample";
	private AlertDialog mSampleDialog;
	private Switch swReincidencia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infracciones);
		
		Typeface helvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeueLTStd-Bd.otf");
		
		Log.e("lala", "Hola Mundo!");
        savedInstanceState = getIntent().getExtras();
        title = savedInstanceState.getString("direccion");
        this.us = savedInstanceState.getString("usuario");
        this.id = savedInstanceState.getInt("id");
        this.direccion = savedInstanceState.getString("direccion");
        this.conf = savedInstanceState.getInt("con");
        
        System.out.println("id " + id);
        System.err.println(conf + " con");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        String n1 = "09";
        int n2 = Integer.parseInt(n1) + 1;
        n1 = String.valueOf(n2);
        if(n1.length() == 1) 
        	Log.i("numero s ", "0" + n1);
        else
        	Log.i("numero n ", n1);
        
		foto();

		System.out.println(subirFoto());
		idl = consultarLevantamientoID();
		if(idl == 0){
			idl = 1;
		}
        
        arreglo.add(us);
        
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        a = c.get(Calendar.YEAR);
        m = c.get(Calendar.MONTH);
        di = c.get(Calendar.DAY_OF_MONTH);
        
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int hor = cal.get(Calendar.HOUR_OF_DAY);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        Date dat = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Log.i("hr del dia", hor + " dia: " + dia + " " + diaSemana);
        if(hor > 16 | (diaSemana == 1 | diaSemana == 7) | diaNoHabiles(sdf.format(dat)) > 0) {
        	tipoActa = "Verificación";
        }
        else {
        	tipoActa = "Inspección";
        }
		Log.i("Tipo Acta", tipoActa);
		
		int n = mMonth + 1;
	    Log.i("Fecha", "dia: " + mDay + " mes: " + n + " ano: " + mYear);
		
        Log.i("usuario", us + " id " + id);
        
        validarFecha();
        if(!c_fecha.equalsIgnoreCase("")) {
        	Log.i("FECHA CON", c_fecha + " ");
            String reg = "/";
            String[] fe = c_fecha.split(reg);
            
            if((Integer.parseInt(fe[0]) < mDay & Integer.parseInt(fe[1]) <= n) & Integer.parseInt(fe[2]) == mYear) {
            	Log.i("Levantamiento", "True");
            }
            else {
            	Log.i("Levantamiento", "False");
    		}
        }
        
        this.btnFecha = (Button)findViewById(R.id.btnFecha);
        this.spIdentifica = (Spinner)findViewById(R.id.spIdentifica);
        this.spManifiesta = (Spinner)findViewById(R.id.spManifiesta);
        this.spuso = (Spinner)findViewById(R.id.spuso);
        this.spgravedad = (Spinner)findViewById(R.id.spgravedad);
        this.etFecham = (EditText)findViewById(R.id.etfecham);
        this.btnInicio = (Button)findViewById(R.id.btnInicio);
        this.spZona = (Spinner)findViewById(R.id.spZona);
        this.spdesignado = (Spinner)findViewById(R.id.spdesignado);
        this.spdesignado1 = (Spinner)findViewById(R.id.spdesignado1);
        this.etfecha = (EditText)findViewById(R.id.etfecha);
        this.etDiaPlazo = (EditText)findViewById(R.id.etdiasplazo);
        this.spnombre = (Spinner)findViewById(R.id.spNombre);
        this.spNombreA = (Spinner)findViewById(R.id.spNombreAcompanante);
        this.spNombreA1 = (Spinner)findViewById(R.id.spNombreAcompanante1);
        this.spNombreA2 = (Spinner)findViewById(R.id.spNombreAcompanante2);
        this.spNombreA3 = (Spinner)findViewById(R.id.spNombreAcompanante3);
        this.spNombreA4 = (Spinner)findViewById(R.id.spNombreAcompanante4);
        this.etIfeI = (EditText)findViewById(R.id.etIfe);
        this.etNoI = (EditText)findViewById(R.id.etnoempleado);
        this.etVigI = (EditText)findViewById(R.id.etvigenciaIns);
        this.etIfeA = (EditText)findViewById(R.id.etIfeAcompanante);
        this.etIfeA1 = (EditText)findViewById(R.id.etIfeAcompanante1);
        this.etIfeA2 = (EditText)findViewById(R.id.etIfeAcompanante2);
        this.etIfeA3 = (EditText)findViewById(R.id.etIfeAcompanante3);
        this.etIfeA4 = (EditText)findViewById(R.id.etIfeAcompanante4);
        this.etNoA = (EditText)findViewById(R.id.etnoempleadoAcompanante);
        this.etNoA1 = (EditText)findViewById(R.id.etnoempleadoAcompanante1);
        this.etNoA2 = (EditText)findViewById(R.id.etnoempleadoAcompanante2);
        this.etNoA3 = (EditText)findViewById(R.id.etnoempleadoAcompanante3);
        this.etNoA4 = (EditText)findViewById(R.id.etnoempleadoAcompanante4);
        this.etVigA = (EditText)findViewById(R.id.etvigenciaAcom);
        this.etVigA1 = (EditText)findViewById(R.id.etvigenciaAcom1);
        this.etVigA2 = (EditText)findViewById(R.id.etvigenciaAcom2);
        this.etVigA3 = (EditText)findViewById(R.id.etvigenciaAcom3);
        this.etVigA4 = (EditText)findViewById(R.id.etvigenciaAcom4);
        this.etNombreT = (EditText)findViewById(R.id.etnombretestigo);
        this.etIfeT = (EditText)findViewById(R.id.etifetestigo);
        this.spInfraccion = (Spinner)findViewById(R.id.spHechos);
        this.etInfraccion = (EditText)findViewById(R.id.etinfraccion);
        this.etDesc = (EditText)findViewById(R.id.etdescripcion);
        this.etDesc1 = (EditText)findViewById(R.id.etdescripcion1);
        this.etDesc2 = (EditText)findViewById(R.id.etdescripcion2);
        this.etDesc3 = (EditText)findViewById(R.id.etdescripcion3);
        this.etDesc4 = (EditText)findViewById(R.id.etdescripcion4);
        this.etdato = (EditText)findViewById(R.id.etdato);
        this.tvuni = (TextView)findViewById(R.id.tvuni);
        this.tveliminar = (Button)findViewById(R.id.tveliminar);
        this.etdato1 = (EditText)findViewById(R.id.etdato1);
        this.tvuni1 = (TextView)findViewById(R.id.tvuni1);
        this.tveliminar1 = (Button)findViewById(R.id.tveliminar1);
        this.etdato2 = (EditText)findViewById(R.id.etdato2);
        this.tvuni2 = (TextView)findViewById(R.id.tvuni2);
        this.tveliminar2 = (Button)findViewById(R.id.tveliminar2);
        this.etdato3 = (EditText)findViewById(R.id.etdato3);
        this.tvuni3 = (TextView)findViewById(R.id.tvuni3);
        this.tveliminar3 = (Button)findViewById(R.id.tveliminar3);
        this.etdato4 = (EditText)findViewById(R.id.etdato4);
        this.tvuni4 = (TextView)findViewById(R.id.tvuni4);
        this.tveliminar4 = (Button)findViewById(R.id.tveliminar4);
        this.btnaceptar = (Button)findViewById(R.id.btnaceptar);
        this.lldiv = (LinearLayout)findViewById(R.id.llall);
        this.cons = (LinearLayout)findViewById(R.id.cons);
        this.tvTitle = (TextView)findViewById(R.id.tvTitle);
        this.desf = (EditText)findViewById(R.id.etDesFoto);
        this.desf1 = (EditText)findViewById(R.id.etDesFoto1);
        this.desf2 = (EditText)findViewById(R.id.etDesFoto2);
        this.btnTomarF = (Button)findViewById(R.id.btnTomarF);
        this.btnGuardar = (Button)findViewById(R.id.btnguardar);
        this.btnImprimir = (Button)findViewById(R.id.btnImprimir);
        this.etSeleccion = (EditText)findViewById(R.id.etSelecion);
        this.rlcampo = (RelativeLayout)findViewById(R.id.rlcampos);
        //this.radiogroup = (RadioGroup)findViewById(R.id.ragroup);
        this.etNombreV = (EditText)findViewById(R.id.etNombreV);
        this.etFraccionamiento = (EditText)findViewById(R.id.etfraccionamiento);
        this.etCalle = (EditText)findViewById(R.id.etcalle);
        this.etNumero = (EditText)findViewById(R.id.etnumerocalle);
        this.etPropietario = (EditText)findViewById(R.id.etpropietario);
        this.etNombreT1 = (EditText)findViewById(R.id.etnombretestigo1);
        this.etIfeT2 = (EditText)findViewById(R.id.etifetestigo1);
        //this.etDensidad = (EditText)findViewById(R.id.etdensidad);
        this.etManifiesta = (EditText)findViewById(R.id.etvisitadom);
        this.btnConsultar = (Button)findViewById(R.id.btnConsultar);
        this.etNuemroInterior = (EditText)findViewById(R.id.etnumerointerior);
        this.etApellidoP = (EditText)findViewById(R.id.etApellidoP);
        this.etApellidoM = (EditText)findViewById(R.id.etApellidoM);
        this.btnSi = (Button)findViewById(R.id.btnSi);
        this.btnNo = (Button)findViewById(R.id.btnNo);
        this.etCitatorio = (EditText)findViewById(R.id.etCitatorio);
        this.etNumeroActa = (EditText)findViewById(R.id.etnActa);
        this.etEspecificacion = (EditText)findViewById(R.id.etespecificacion);
        
        this.etDFoto = (EditText)findViewById(R.id.etDesFoto);
        this.etDFoto1 = (EditText)findViewById(R.id.etDesFoto1);
        this.etDFoto2 = (EditText)findViewById(R.id.etDesFoto2);
        this.etDFoto3 = (EditText)findViewById(R.id.etDesFoto3);
        
        
        this.etDFoto4 = (EditText)findViewById(R.id.etDesFoto4);
        this.etDFoto5 = (EditText)findViewById(R.id.etDesFoto5);
        this.etDFoto6 = (EditText)findViewById(R.id.etDesFoto6);
        this.etDFoto7 = (EditText)findViewById(R.id.etDesFoto7);
        this.etDFoto8 = (EditText)findViewById(R.id.etDesFoto8);
        this.etDFoto9 = (EditText)findViewById(R.id.etDesFoto9);
        this.etDFoto10 = (EditText)findViewById(R.id.etDesFoto10);
        this.etDFoto11 = (EditText)findViewById(R.id.etDesFoto11);
        this.etDFoto12 = (EditText)findViewById(R.id.etDesFoto12);
        this.etDFoto13 = (EditText)findViewById(R.id.etDesFoto13);
        this.etDFoto14 = (EditText)findViewById(R.id.etDesFoto14);
        //this.etDFoto15 = (EditText)findViewById(R.id.etDesFoto15);
        
        this.etVManifiesta = (EditText)findViewById(R.id.etspecifique);
        this.etVIdentifica = (EditText)findViewById(R.id.etnumeroife);
        this.etLatitud = (EditText)findViewById(R.id.etLatitud);
        this.etLongitud = (EditText)findViewById(R.id.etLongitud);
        this.etNum = (EditText)findViewById(R.id.etnumero);
        this.btnVisualizar = (Button)findViewById(R.id.btnVFecha);
        this.etFecham.setEnabled(false);
        this.spconsultar = (Spinner)findViewById(R.id.spConsulta);
        this.tvTipo = (TextView)findViewById(R.id.tvTipo);
        this.btnMostrar = (Button)findViewById(R.id.btnDatos);
        this.tvEspe = (TextView)findViewById(R.id.tvEspecificacion);
        this.btnSalir = (Button)findViewById(R.id.btnSalirIn);
        this.spPoblacion = (Spinner)findViewById(R.id.spPoblacion);
        this.spFraccionamiento = (Spinner)findViewById(R.id.spFraccionamiento);
        this.tvOV = (TextView)findViewById(R.id.tvOV);
        this.etAnoCitatorio = (EditText)findViewById(R.id.etAnoCitatorio);
        this.tvC = (TextView)findViewById(R.id.tvC);
        this.etAnoOrden = (EditText)findViewById(R.id.etAnoNumero);
        this.spIdentificaT = (Spinner)findViewById(R.id.spIdentificaT);
        this.spIdentificaT1 = (Spinner)findViewById(R.id.spIdentificaT1);
        this.etCondominio = (EditText)findViewById(R.id.etCondominio);
        this.btnmodificar = (Button)findViewById(R.id.btnModificar);
        this.etManzana = (EditText)findViewById(R.id.etManzana);
        this.etLote = (EditText)findViewById(R.id.etLote);
        this.tvEvidencia = (TextView)findViewById(R.id.evidencia);
        this.etReferencia = (EditText)findViewById(R.id.etReferencia);
        this.etBuscar = (EditText)findViewById(R.id.etBuscar);
        btnB = (Button)findViewById(R.id.btnBuscar);
        etCorreo = (EditText)findViewById(R.id.etEmail);
        rlProp = (RelativeLayout)findViewById(R.id.rlPropietar);
        rlTestA = (RelativeLayout)findViewById(R.id.rlTestAsist);
        rlVisita = (RelativeLayout)findViewById(R.id.rlVisita);
        llNota = (LinearLayout)findViewById(R.id.rlNota);
        llplazo = (LinearLayout)findViewById(R.id.llPlazo);
        llreincidencia = (LinearLayout)findViewById(R.id.llreincidencia);
        rgReincidencia = (RadioGroup)findViewById(R.id.rgreincidencia);
        etfolio = (EditText)findViewById(R.id.etFolio);
        cbFlag = (CheckBox)findViewById(R.id.cbFlag);
        //etAlineamiento = (EditText)findViewById(R.id.etalineamiento);
        etConstruccion = (EditText)findViewById(R.id.etcontruccion);
        rlLicencias = (RelativeLayout)findViewById(R.id.rllicencias);
        spMedida = (Spinner)findViewById(R.id.spMedida);
        btnVista = (Button)findViewById(R.id.btnVista);
        spInspectorT = (Spinner)findViewById(R.id.spInspectorT);
        spInspectorT1 = (Spinner)findViewById(R.id.spInspectorT1);
        this.btnFtp = (Button)findViewById(R.id.btnEnviaFTP);
       
        this.rbaper = (Button)findViewById(R.id.radioApercibimiento);
        this.rbcitatorio = (Button)findViewById(R.id.radioCitatorio);
        this.rborden = (Button)findViewById(R.id.radioOrdenV);
        this.rbHechos = (Button)findViewById(R.id.radioHechos);
        
        etGiro = (EditText)findViewById(R.id.etGiro);
        etMotivo = (EditText)findViewById(R.id.etMotivo);
        this.etOrden1 = (EditText)findViewById(R.id.etOrden1);
        this.btnOrden1 = (Button)findViewById(R.id.btnBorden);
        spReglamento = (Spinner)findViewById(R.id.spReglamento);
        tvReg = (TextView)findViewById(R.id.tvReg);
        etEntreC = (EditText)findViewById(R.id.etEntreC);
        etEntreC1 = (EditText)findViewById(R.id.etEntreC1);
        etResponsable = (EditText)findViewById(R.id.etResponsable);
        etRegistro = (EditText)findViewById(R.id.etRegistro);
        tvActa = (TextView)findViewById(R.id.tvActa);
        etMedida = (EditText)findViewById(R.id.etMedida);
        etArticulo = (EditText)findViewById(R.id.etArticulos);
        cbFirma = (CheckBox)findViewById(R.id.cbFirma);
        etInspccionFue = (EditText)findViewById(R.id.etInpeccionFue);
        radioInfraccion = (Button)findViewById(R.id.radioInfraccion);
        
        tvCondominio = (TextView)findViewById(R.id.tvCondominio);
        
        tvNombreComercial = (TextView)findViewById(R.id.tvNombreComercial);
        
        
        etSector = (EditText)findViewById(R.id.etSector);
        etNombreComercial = (EditText)findViewById(R.id.etNombreComercia);
        
        btnver1 = (Button)findViewById(R.id.btnVer1);
        btnver2 = (Button)findViewById(R.id.btnVer2);
        btnver3 = (Button)findViewById(R.id.btnVer3);
        btnver4 = (Button)findViewById(R.id.btnVer4);
        
        
        btnver5 = (Button)findViewById(R.id.btnVer5);
        btnver6 = (Button)findViewById(R.id.btnVer6);
        btnver7 = (Button)findViewById(R.id.btnVer7);
        btnver8 = (Button)findViewById(R.id.btnVer8);
        btnver9 = (Button)findViewById(R.id.btnVer9);
        btnver10 = (Button)findViewById(R.id.btnVer10);
        btnver11 = (Button)findViewById(R.id.btnVer11);
        btnver12 = (Button)findViewById(R.id.btnVer12);
        btnver13 = (Button)findViewById(R.id.btnVer13);
        btnver14 = (Button)findViewById(R.id.btnVer14);
        btnver15 = (Button)findViewById(R.id.btnVer15);
        //btnver16 = (Button)findViewById(R.id.btnVer16);
        
        spPeticion = (Spinner)findViewById(R.id.spPeticion);
        
        this.tvAcomp = (TextView)findViewById(R.id.tvAcomp);
        
        tvMotivo = (TextView)findViewById(R.id.tvMotivo);
        
        llcomp = (LinearLayout)findViewById(R.id.llcomp);
        
        etLGiro = (EditText)findViewById(R.id.etLicGiro);
        etAGiro = (EditText)findViewById(R.id.etGiro1);
        etAlicencia = (EditText)findViewById(R.id.etALicencia);
        tvALicencia = (TextView)findViewById(R.id.tvALicencia);
        
        btnImprimirResum = (Button)findViewById(R.id.btnImprimirResum);

        spNE = findViewById(R.id.spNE);
        swReincidencia = findViewById(R.id.swReincidencia);

        
        tvuni.setTypeface(helvetica);
        tvuni1.setTypeface(helvetica);
        tvuni2.setTypeface(helvetica);
        tvuni3.setTypeface(helvetica);
        tvuni4.setTypeface(helvetica);
        tvTitle.setTypeface(helvetica);
        tvTipo.setTypeface(helvetica);
        tvEspe.setTypeface(helvetica);
        tvOV.setTypeface(helvetica);
        tvC.setTypeface(helvetica);
        tvEvidencia.setTypeface(helvetica);
        tvReg.setTypeface(helvetica);
        tvActa.setTypeface(helvetica);
        tvMotivo.setTypeface(helvetica);
        tvAcomp.setTypeface(helvetica);
        tvCondominio.setTypeface(helvetica);
        tvNombreComercial.setTypeface(helvetica);
        tvALicencia.setTypeface(helvetica);


        btnFecha.setTypeface(helvetica);
        btnInicio.setTypeface(helvetica);
        btnaceptar.setTypeface(helvetica);
        btnTomarF.setTypeface(helvetica);
        btnGuardar.setTypeface(helvetica);
        btnImprimir.setTypeface(helvetica);
        btnConsultar.setTypeface(helvetica);
        btnSi.setTypeface(helvetica);
        btnNo.setTypeface(helvetica);
        btnVisualizar.setTypeface(helvetica);
        btnMostrar.setTypeface(helvetica);
        btnSalir.setTypeface(helvetica);
        tveliminar.setTypeface(helvetica);
        tveliminar1.setTypeface(helvetica);
        tveliminar2.setTypeface(helvetica);
        tveliminar3.setTypeface(helvetica);
        tveliminar4.setTypeface(helvetica);
        btnmodificar.setTypeface(helvetica);
        btnFtp.setTypeface(helvetica);
        btnB.setTypeface(helvetica);
        btnOrden1.setTypeface(helvetica);
        btnVista.setTypeface(helvetica);
        btnver1.setTypeface(helvetica);
        btnver2.setTypeface(helvetica);
        btnver3.setTypeface(helvetica);
        btnver4.setTypeface(helvetica);
        btnver5.setTypeface(helvetica);
        btnver6.setTypeface(helvetica);
        btnver7.setTypeface(helvetica);
        btnver8.setTypeface(helvetica);
        btnver9.setTypeface(helvetica);
        btnver10.setTypeface(helvetica);
        btnver11.setTypeface(helvetica);
        btnver12.setTypeface(helvetica);
        btnver13.setTypeface(helvetica);
        btnver14.setTypeface(helvetica);
        btnver15.setTypeface(helvetica);
        //btnver16.setTypeface(helvetica);
        
        if(id == 2) {
        	etAlicencia.setVisibility(View.GONE);
        	tvALicencia.setVisibility(View.GONE);
        }
        
        
        //this.rbaper = (Button)findViewById(R.id.radioApercibimiento);
        //this.rbcitatorio = (Button)findViewById(R.id.radioCitatorio);
        //this.rborden = (Button)findViewById(R.id.radioOrdenV);
        
        spNombreA1.setOnItemSelectedListener(this);
        spNombreA2.setOnItemSelectedListener(this);
        spNombreA3.setOnItemSelectedListener(this);
        spNombreA4.setOnItemSelectedListener(this);
        spReglamento.setOnItemSelectedListener(this);
        spMedida.setOnItemSelectedListener(this);
        
        spInspectorT.setOnItemSelectedListener(this);
        spInspectorT1.setOnItemSelectedListener(this);
        spdesignado1.setOnItemSelectedListener(this);
        
        btnver1.setOnClickListener(this);
        btnver2.setOnClickListener(this);
        btnver3.setOnClickListener(this);
        btnver4.setOnClickListener(this);
        
        
        btnver5.setOnClickListener(this);
        btnver6.setOnClickListener(this);
        btnver7.setOnClickListener(this);
        btnver8.setOnClickListener(this);
        btnver9.setOnClickListener(this);
        btnver10.setOnClickListener(this);
        btnver11.setOnClickListener(this);
        btnver12.setOnClickListener(this);
        btnver13.setOnClickListener(this);
        btnver14.setOnClickListener(this);
        btnver15.setOnClickListener(this);
        //btnver16.setOnClickListener(this);
        btnImprimirResum.setOnClickListener(this);
        
        if(id == 1) {
        	rlLicencias.setVisibility(View.VISIBLE);
        	this.rbaper.setVisibility(View.GONE);
            this.rbcitatorio.setVisibility(View.GONE);
            this.rborden.setVisibility(View.VISIBLE);
            this.rbHechos.setVisibility(View.GONE);
    	}
        else {
        	rlLicencias.setVisibility(View.VISIBLE);
        	/*this.rbaper.setVisibility(View.VISIBLE);
            this.rbcitatorio.setVisibility(View.VISIBLE);*/
            this.rborden.setVisibility(View.VISIBLE);
            //this.rbHechos.setVisibility(View.VISIBLE);
        }
        
        if(getIntent().getExtras().getString("na") != null) {
        	na = getIntent().getExtras().getString("na");
        	il = getIntent().getExtras().getInt("il");
        	btnInicio.setVisibility(View.GONE);
        	rborden.setVisibility(View.GONE);
        	//rbinfra.setChecked(true);
        	infrac = 1;
			etDiaPlazo.setText("20");
			etDiaPlazo.setEnabled(false);
			rlProp.setVisibility(View.VISIBLE);
			rlTestA.setVisibility(View.VISIBLE);
			rlVisita.setVisibility(View.VISIBLE);
			llNota.setVisibility(View.VISIBLE);
			llplazo.setVisibility(View.VISIBLE);
			llreincidencia.setVisibility(View.GONE);
			ante = "IN";
			formato = "infraccion";
			etGiro.setVisibility(View.GONE);
			etMotivo.setVisibility(View.GONE);
			spNombreA1.setVisibility(View.GONE);
			spNombreA2.setVisibility(View.GONE);
			etIfeA1.setVisibility(View.GONE);
			etIfeA2.setVisibility(View.GONE);
			etNoA2.setVisibility(View.GONE);
			etNoA3.setVisibility(View.GONE);
			etVigA1.setVisibility(View.GONE);
			etVigA2.setVisibility(View.GONE);
			/*tva1.setVisibility(View.GONE);
			tva2.setVisibility(View.GONE);*/
        	if (!inicio) {
				consu = false;
				Calendar calendar = Calendar.getInstance();
				String h,m;
				h = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
				hora = h + ":" + m;
				int day = calendar.get(Calendar.MONTH);
				day += 1;
				fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + day + "/" + calendar.get(Calendar.YEAR);
				Log.i("Fecha/Hora", fecha + "/" + hora);
				lldiv.setVisibility(View.VISIBLE);
				btnInicio.setVisibility(View.GONE);
				btnConsultar.setVisibility(View.GONE);
				etEspecificacion.setVisibility(View.VISIBLE);
				citatorio = false;
				spconsultar.setVisibility(View.GONE);
				mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					thread = new Thread(InfraccionesActivity.this);
					thread.start();
				} else {
					builAlert();
				}
				String [] fechas = fecha.split("/");
				int dia1, mes,a;
				String me;
				dia1 = Integer.parseInt(fechas[0]);
				mes = Integer.parseInt(fechas[1]);
				a = Integer.parseInt(fechas[2].substring(2, 4));
				me = Justificar.mes(mes);
				Log.i("fecha", dia + " " + me + "  " + a);
				//thread.start();
				if(id == 2)
		        	etOrden1.setText("OV" + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/");
			}
			else {
				InfraccionesActivity.this.finish();
				consu = false;
				Intent intent = new Intent(InfraccionesActivity.this, InfraccionesActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("direccion", title);
				bundle.putString("usuario", us);
				bundle.putInt("id", id);
				intent.putExtras(bundle);
				startActivity(intent);
				if(id == 2)
		        	etOrden1.setText("OV" + "/" + InfraccionesActivity.this.id + "/");
				
			}
        }
        
        System.err.println(na + " na " + il + " il");
        
        btnB.setOnClickListener(this);
        rborden.setOnClickListener(this);
        radioInfraccion.setOnClickListener(this);
        cbFlag.setOnCheckedChangeListener(this);
        this.btnOrden1.setOnClickListener(this);
        btnVista.setOnClickListener(this);
        
        this.btnFtp = (Button)findViewById(R.id.btnEnviaFTP);
        
        btnFtp.setVisibility(View.GONE);
        
        this.tvEvidencia.setVisibility(View.GONE);
        this.btnTomarF.setVisibility(View.VISIBLE);
        
        //spReglamento.setVisibility(View.GONE);
		//tvReg.setVisibility(View.GONE);
        
        this.btnImprimir.setEnabled(true);
        
        this.tvTitle.setText(direccion);
        
        Calendar calendar = Calendar.getInstance();
        String ano = String.valueOf(calendar.get(Calendar.YEAR));
        ano = ano.substring(2,4);
        
        etAnoCitatorio.setEnabled(false);
        etAnoOrden.setEnabled(false);
        
        etAnoCitatorio.setText(ano);
        etAnoOrden.setText(ano);
        
        etLongitud.setEnabled(false);
        etLatitud.setEnabled(false);
        
        
        this.btnSalir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salir();
			}
		});
        
        this.spdesignado.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(spdesignado.getItemAtPosition(arg2).toString().equalsIgnoreCase("inspector")) {
					spInspectorT.setSelection(0);
					spInspectorT.setEnabled(true);
					/*etIfeA.setText(ifeA);
					etNoA.setText(noA);
					etVigA.setText(vigA);
					etNombreT.setText(spNombreA.getSelectedItem().toString());
					etIfeT.setText(ifeA);*/
				}
				else {
					spInspectorT.setSelection(0);
					spInspectorT.setEnabled(false);
					etIfeT.setText("");
					/*etIfeA.setText("");
					etNoA.setText("");
					etVigA.setText("");
					etNombreT.setText("");
					etIfeT.setText("");*/
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        this.btnMostrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				etCitatorio.setText("12");
				etNum.setText("69");
				etNombreV.setText("Alberto Aguirre GarcÔøΩa");
				etFraccionamiento.setText("Rinconada Del Sol");
				etCalle.setText("LÔøΩpez M.");
				etNumero.setText("9346");
				etNuemroInterior.setText("A");
				etApellidoP.setText("Cortes");
				etApellidoM.setText("Silva");
				etPropietario.setText("Alberto Aguirre GarcÔøΩa");
				etNombreT1.setText("Juan Saavedra Barajas");
				etIfeT2.setText("Juan-Ceiwn-8436");
				//etDensidad.setText("Media");
				etManifiesta.setText("No");
			}
		});
        
        //btnImprimir.setEnabled(false);
        
        tvTipo.setText(tvTipo.getText().toString() + " " + tipoActa);
        this.etNumeroActa.setEnabled(false);
        
        listar();
        listarInf();
        /*buscarCodigo();
        buscarLap();
        buscarOrdenamiento();
        buscarZonificacion();
        buscarOrdenamientoEco();
        buscarNAE();
        buscarLEEPA();*/
        listarZona(this.id);
        manifiesta();
        identifica();
        usoSuelo();
        poblacion();
        fraccionamiento();
        buscarNombreCampo();
        buscarOrdenamientos();
        competencia();
        medidas("");
        etDiaPlazo.setText("20");
		etDiaPlazo.setEnabled(false);
		etNombreT.setText("HO");
        etIfeT.setText("LA");
        
        Log.i("ordenamientos", "o1 " + campo1 + " o2 " + campo2 + " o3 " + campo3 + " o4 " + campo4 + " o5 " + campo5 + "o6 " + campo6 + " o7 " + campo7 + " o8 " + campo8 + " o9 " + campo9 + " o0 " + campo0 + campo11 + " o11 " + campo12 + " o12 " + campo12 + " o13 " + campo13 + " o14 " + campo14 + "015" + campo15 + "o16 " + campo16 + " o17 " + campo17 + " o18 " + campo18 + " o19 " + campo19 + " o20 " + campo20);
		
        if(!zona.isEmpty()){
        	spZona.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, zona));
        }
        
        spnombre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arreglo));
        spnombre.setEnabled(false);
        
        if(!fraccionamiento.isEmpty()) 
        	spFraccionamiento.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, fraccionamiento));
        
        if(!reglamento.isEmpty())
        	spReglamento.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,reglamento));
        
        if(!poblacion.isEmpty()) 
        	spPoblacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,poblacion));
        
        arregloLista1 = arregloLista;
        arregloLista2 = arregloLista;
        if(!arregloLista.isEmpty()){
        	spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        
        if(!arregloLista1.isEmpty()){
        	spInspectorT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista1));
        }
        
        if(!arregloLista2.isEmpty()){
        	spInspectorT1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista2));
        }
        
        if(!arregloLista.isEmpty()){
        	spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloLista.isEmpty()){
        	spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloLista.isEmpty()){
        	spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloLista.isEmpty()){
        	spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        
        if(!arregloInfraccion.isEmpty()){
        	spInfraccion.setAdapter(new ArrayAdapter<String>(this,R.layout.multiline_spinner_dropdown_item,arregloInfraccion));
        }
        
        spManifiesta.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spManifiesta.getItemAtPosition(position).toString().equals("")) {
					etVManifiesta.setText(spManifiesta.getItemAtPosition(position).toString());
				}
				else {
					etVManifiesta.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spPoblacion.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
				if(!spPoblacion.getItemAtPosition(position).toString().equals("")) 
					etFraccionamiento.setText(spPoblacion.getItemAtPosition(position).toString());
				else
					etFraccionamiento.setText("");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
        
        spFraccionamiento.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
				if (!spFraccionamiento.getItemAtPosition(position).toString().equals("")) {
					etFraccionamiento.setText(spFraccionamiento.getItemAtPosition(position).toString());
					zon = zonas.get(position);
				}
				else {
					etFraccionamiento.setText("");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        btnVisualizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!validarCampos(etDiaPlazo)) {
					diaPlazo = Integer.parseInt(etDiaPlazo.getText().toString());
					Calendar c = calcular("", diaPlazo);
					a = c.get(Calendar.YEAR);
					m = c.get(Calendar.MONTH);
					di = c.get(Calendar.DAY_OF_MONTH);
					int nim = c.get(Calendar.MONTH) + 1;
					Log.i("Calendario", "Dia: " + c.get(Calendar.DAY_OF_MONTH) + " Mes: " + nim + " AÃ±o: " + c.get(Calendar.YEAR));
					updateDisplay1();
				}
				else {
					Toast toast = Toast.makeText(getApplication(), "INGRESE EL NUMERO DE DIAS DEL PLAZO", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        spIdentifica.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spIdentifica.getItemAtPosition(position).toString().equals("")) {
					//etVIdentifica.setText(spIdentifica.getItemAtPosition(position).toString() + ": ");
				}
				else {
					etVIdentifica.setText("");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spIdentificaT.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spIdentificaT.getItemAtPosition(position).toString().equals("")) {
					if(!spIdentificaT.getItemAtPosition(position).toString().equalsIgnoreCase("Inspector")) {
						etIfeT.setText(idT1);
						etIfeT.setEnabled(true);
					}
					else {
						spIdentificaT.setEnabled(false);
						etIfeT.setEnabled(true);
					}
				}
				else {
					etIfeT.setText("");
					etIfeT.setEnabled(true);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spIdentificaT1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spIdentificaT1.getItemAtPosition(position).toString().equals("")) {
					etIfeT2.setText(idT1);
				}
				else {
					etIfeT2.setText("");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spnombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				Object sel = spnombre.getItemAtPosition(position);
				//buscarInspector(sel.toString());
				
				buscarIdInspector(sel.toString());
				
				etIfeI.setText(ifeI);
				etNoI.setText(noI);
				etVigI.setText(vigI);
				id_inspector1 = id_i1.get(position);
				Log.i("id inspector", id_inspector1+ "");
				int n;
				
				if(!citatorio){
					String [] na;
					if(consultarActa() == 0){
						Log.i("consultar", "si");
						numero = "01";
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
						
						buscarNumeroActa();
						if (!numero_acta.isEmpty()) {
							if (na()) {
								//aqui consultar el ultimo y asignar
								na = ultimo().split("/");
								n = Integer.parseInt(na[6]) + 1;
								numero = String.valueOf(n);
								if (numero.length() == 1)
									numero = "0" + n;
								etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
							}
						}
					}
					else{
						Log.i("consultar", "no");
						asignarActa();
						n = Integer.valueOf(numero)+1;
						Log.i("Numero1", numero);
						if(n > 0 & n <= 9){
							numero = "0"+String.valueOf(n);
							etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/"  + numero);
							Log.i("numeros", "n " + etNumeroActa.getText().toString().substring(0, 16) + " v " + s.substring(0, 16));
							if(!etNumeroActa.getText().toString().substring(0, 16).equalsIgnoreCase(s.substring(0, 16))){
								Log.i("numero acta", "si");
								numero = "01";
								etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
								Log.i("nueva ", InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
							}
						}
						else{
							numero = String.valueOf(n);
							etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/"  + numero);
						}
						
						buscarNumeroActa();
						if (!numero_acta.isEmpty()) {
							if (na()) {
								//aqui consultar el ultimo y asignar
								na = ultimo().split("/");
								n = Integer.parseInt(na[6]) + 1;
								numero = String.valueOf(n);
								if (numero.length() == 1)
									numero = "0" + n;
								etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
							}
						}
					}
				}
			}
			

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
        	
		});
        
        this.spNombreA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				etIfeA.setEnabled(false);
				etNoA.setEnabled(false);
				etVigA.setEnabled(false);
				etNombreT.setEnabled(true);
				
				Object sel = spNombreA.getItemAtPosition(position);
				buscarAcompanante(sel.toString());
				etIfeA.setText(ifeA);
				etNoA.setText(noA);
				etVigA.setText(vigA);
				etNombreT.setText(sel.toString());
				etIfeT.setText(ifeA);
				if(position != 0){
					etIfeT.setEnabled(false);
					etIfeA.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA);
					etIfeT.setText(ifeA);
					int p = position-1;
					id_inspector2 = id_i2.get(p);
					System.out.println(id_inspector2);
				}
					
				else{
					if(resov) {
						etIfeT.setEnabled(false);
						etIfeA.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA);
						etIfeT.setText(ifeA);
						int p = position-1;
						id_inspector2 = id_i2.get(position);
						System.out.println(id_inspector2 + " <2");
					} else {
						etIfeA.setText("");
						etNoA.setText("");
						etVigA.setText("");
						etNombreT.setText("");
						etIfeT.setText("");
						/*etIfeA.setEnabled(true);
						etNoA.setEnabled(true);
						etVigA.setEnabled(true);*/
						etNombreT.setEnabled(true);
						//etIfeT.setEnabled(true);
					}
				}
				if(consu) {
					etIfeA.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA);
					etIfeT.setText(ifeA);
					etIfeA.setEnabled(false);
					etNoA.setEnabled(false);
					etVigA.setEnabled(false);
					etNombreT.setEnabled(false);
					etIfeT.setEnabled(false);
				}
				pos ++;
				
				Log.i("idinspector2", id_inspector2 + "");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        btnFecha.setVisibility(View.GONE);
        etfecha.setEnabled(false);
        
        etIfeA.setVisibility(View.GONE);
        etIfeA1.setVisibility(View.GONE);
        etIfeA2.setVisibility(View.GONE);
        etIfeA3.setVisibility(View.GONE);
        etIfeA4.setVisibility(View.GONE);
        
        etNoA.setVisibility(View.GONE);
        etNoA1.setVisibility(View.GONE);
        etNoA2.setVisibility(View.GONE);
        etNoA3.setVisibility(View.GONE);
        etNoA4.setVisibility(View.GONE);
        
        etVigA.setVisibility(View.GONE);
        etVigA1.setVisibility(View.GONE);
        etVigA2.setVisibility(View.GONE);
        etVigA3.setVisibility(View.GONE);
        etVigA4.setVisibility(View.GONE);
        
        this.btnConsultar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pos++;
				consu = true;
				cons.setVisibility(View.VISIBLE);
				inicio = true;
				btnConsultar.setVisibility(View.GONE);
				spconsultar.setVisibility(View.VISIBLE);
				lldiv.setVisibility(View.VISIBLE);
				boolean r = consultarLevantamiento();
				etEspecificacion.setVisibility(View.GONE);
				citatorio = true;
				btnSi.setEnabled(false);
				btnNo.setEnabled(false);
				tvEspe.setVisibility(View.GONE);
				btnTomarF.setEnabled(true);
				btnGuardar.setEnabled(false);
				btnVista.setEnabled(false);
				btnMostrar.setEnabled(false);
				btnFecha.setEnabled(false);
				spPoblacion.setEnabled(false);
				spFraccionamiento.setEnabled(false);
				etPropietario.setEnabled(false);
				etManifiesta.setEnabled(false);
				etVManifiesta.setEnabled(false);
				etVIdentifica.setEnabled(false);
				etCondominio.setEnabled(false);
				btnImprimir.setEnabled(true);
				etManzana.setEnabled(false);
				etReferencia.setEnabled(false);
				etNoA.setEnabled(false);
				etIfeA.setEnabled(false);
				etVigA.setEnabled(false);
				etNombreT.setEnabled(false);
				etLote.setEnabled(false);
				etOrden1.setEnabled(false);
				etConstruccion.setEnabled(false);
				etResponsable.setEnabled(false);
				etRegistro.setEnabled(false);
				etEntreC.setEnabled(false);
				etEntreC1.setEnabled(false);
				etCorreo.setEnabled(false);
				etMedida.setEnabled(false);
				etArticulo.setEnabled(false);
				/*btnFtp.setEnabled(true);
				btnFtp.setVisibility(View.VISIBLE);*/
				InfraccionesActivity.this.btnTomarF.setVisibility(View.VISIBLE);
				if(!consultar.isEmpty()){
					spconsultar.setAdapter(new ArrayAdapter<String>(InfraccionesActivity.this, android.R.layout.simple_spinner_dropdown_item, consultar));
				}
				if (!r) {
					Toast toast = Toast.makeText(InfraccionesActivity.this, "No hay infracciones", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        spconsultar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				lldiv.setVisibility(View.GONE);
				if (!spconsultar.getItemAtPosition(position).equals("")) {
					lldiv.setVisibility(View.VISIBLE);
					consultarLevantamiento(spconsultar.getItemAtPosition(position).toString());
					System.out.println("entro else consult");
					System.err.println(spconsultar.getSelectedItem().toString().substring(0, 2));
					String ante = spconsultar.getSelectedItem().toString().substring(0, 2);
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spInfraccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if(con == 0){
					Log.i("Ento al if", (String)spInfraccion.getSelectedItem());
					con ++;
				}
				else{
					infraccion++;
					btnaceptar.setVisibility(View.VISIBLE);
					Log.i("Ento al else", (String)spInfraccion.getItemAtPosition(position));
					String t = spInfraccion.getItemAtPosition(position).toString();
					buscarInfraccion(spInfraccion.getSelectedItem().toString());
					if(!desc){
						etDesc.setVisibility(View.VISIBLE);
						/*if(!unidad.trim().equalsIgnoreCase("")){
							Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
							etdato.setVisibility(View.VISIBLE);
							etdato.setEnabled(true);
							tvuni.setVisibility(View.VISIBLE);
							tvuni.setText(unidad);
							tvuni.setFocusable(true);
						}*/
						des = (String)spInfraccion.getItemAtPosition(position);
						etDesc.setText((String)spInfraccion.getItemAtPosition(position));
						tveliminar.setVisibility(View.VISIBLE);
						etDesc.setText(t);
						desc = true;
						co++;
					}
					else if(!desc1){
						etDesc1.setVisibility(View.VISIBLE);
						/*if(!unidad.trim().equalsIgnoreCase("")){
							Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
							etdato1.setVisibility(View.VISIBLE);
							etdato1.setEnabled(true);
							tvuni1.setVisibility(View.VISIBLE);
							tvuni1.setText(unidad);
							tvuni1.setFocusable(true);
						}*/
						des1 = (String)spInfraccion.getItemAtPosition(position);
						tveliminar1.setVisibility(View.VISIBLE);
						etDesc1.setText(t);
						desc1 = true;
						co++;
					}
					else if(!desc2){
						etDesc2.setVisibility(View.VISIBLE);
						/*if(!unidad.trim().equalsIgnoreCase("")){
							Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
							etdato2.setVisibility(View.VISIBLE);
							etdato2.setEnabled(true);
							tvuni2.setVisibility(View.VISIBLE);
							tvuni2.setText(unidad);
							tvuni2.setFocusable(true);
						}*/
						des2 = (String)spInfraccion.getItemAtPosition(position);
						tveliminar2.setVisibility(View.VISIBLE);
						etDesc2.setText(t);
						desc2 = true;
						co++;
					}
					else if(!desc3){
						etDesc3.setVisibility(View.VISIBLE);
						/*if(!unidad.trim().equalsIgnoreCase("")){
							Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
							etdato3.setVisibility(View.VISIBLE);
							etdato3.setEnabled(true);
							tvuni3.setVisibility(View.VISIBLE);
							tvuni3.setText(unidad);
							tvuni3.setFocusable(true);
						}*/
						des3 = (String)spInfraccion.getItemAtPosition(position);
						tveliminar3.setVisibility(View.VISIBLE);
						etDesc3.setText(des3);
						desc3 = true;
						co++;
					}
					else{
						etDesc4.setVisibility(View.VISIBLE);
						/*if(!unidad.trim().equalsIgnoreCase("")){
							Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
							etdato4.setVisibility(View.VISIBLE);
							etdato4.setEnabled(true);
							tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);
						}*/
						des4 = (String)spInfraccion.getItemAtPosition(position);
						tveliminar4.setVisibility(View.VISIBLE);
						etDesc4.setText(t);
						desc4 = true;
						co++;
					}
					
				}
				if (infraccion == 5)
					spInfraccion.setEnabled(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//try {
					
					if (!validarCampos(etDiaPlazo)) {
						diaPlazo = Integer.parseInt(etDiaPlazo.getText().toString());
						Calendar c = calcular("", diaPlazo);
						a = c.get(Calendar.YEAR);
						m = c.get(Calendar.MONTH);
						di = c.get(Calendar.DAY_OF_MONTH);
						int nim = c.get(Calendar.MONTH) + 1;
						Log.i("Calendario", "Dia: " + c.get(Calendar.DAY_OF_MONTH) + " Mes: " + nim + " AÃ±o: " + c.get(Calendar.YEAR));
						updateDisplay1();
					}
					else {
						Toast toast = Toast.makeText(getApplication(), "INGRESE EL NUMERO DE DIAS DEL PLAZO", Toast.LENGTH_SHORT);
						toast.setGravity(0, 0, 15);
						toast.show();
					}
					
				
				
				
				
					/*if (validarCampos(etCitatorio)) {
						AlertDialog.Builder al = new AlertDialog.Builder(InfraccionesActivity.this)
						.setMessage("El campo Citatorio no se lleno. ÔøΩEs corecto?")
						.setPositiveButton("Si", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								guardar();
							}
						}).setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
						AlertDialog alert = al.create();
						alert.show();
					}
					else {*/
						guardar();
					//}
				/*}catch(Exception e) {
					
					btnGuardar.setEnabled(false);
					btnImprimir.setEnabled(true);
				}*/
				/*btnGuardar.setEnabled(false);
				btnImprimir.setEnabled(true);
				InfraccionesActivity.this.finish();*/
			}
		});
        
        this.btnImprimir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					
					
					//imprimir();
					//imprimir(formato);
					//Intent intent = new Intent();
					 if (!mBluetoothAdapter.isEnabled()) {
						 
				            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				            startActivityForResult(enableIntent, 3);
					 }
					
					try{
						//if(id == 1) {
							File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
							
							if(file.exists()) {
								Intent i = new Intent(Intent.ACTION_VIEW);
						    	i.setPackage("com.dynamixsoftware.printershare");
						    	i.setDataAndType(Uri.fromFile(file), "application/pdf");
						    	startActivity(i);
							}
					}catch (Exception e) {
						Log.e("Error al", e.getMessage());
					}
				}catch(Exception e) {
					System.out.println(e.getMessage() + " lll");
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Hubo un error al imprimir", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        this.btnTomarF.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					foto ++;
					Log.i("fot", foto+"");
					
					name = "";
					name = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
					File folder = new File(name);
					folder.mkdirs();
					String nom = etNumeroActa.getText().toString()+"-"+foto+".jpg";
					name+=nom.replace("/", "_");
					
					showMsg(name);
					Log.i("Foto", name);
					if (foto == 15) {
						btnTomarF.setVisibility(View.GONE);
					}
					try{
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						Uri out = Uri.fromFile(new File(name));
						intent.putExtra(MediaStore.EXTRA_OUTPUT,out);
						startActivityForResult(intent, 1);
					}catch (Exception e) {
						Toast toast = Toast.makeText(InfraccionesActivity.this, "Hubo un error con la camara", Toast.LENGTH_SHORT);
						toast.setGravity(0, 0, 15);
						toast.show();
					}
				}catch(Exception e) {
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Hubo un error con la camara", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        this.btnFecha.setOnClickListener(this);
        
        this.btnInicio.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//int n;
				if (!inicio) {
					consu = false;
					Calendar calendar = Calendar.getInstance();
					String h,m;
					h = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
					m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
					hora = h + ":" + m;
					int day = calendar.get(Calendar.MONTH);
					day += 1;
					fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + day + "/" + calendar.get(Calendar.YEAR);
					Log.i("Fecha/Hora", fecha + "/" + hora);
					lldiv.setVisibility(View.VISIBLE);
					btnInicio.setVisibility(View.GONE);
					btnConsultar.setVisibility(View.GONE);
					etEspecificacion.setVisibility(View.VISIBLE);
					citatorio = false;
					spconsultar.setVisibility(View.GONE);
					mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
					if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
						thread = new Thread(InfraccionesActivity.this);
						thread.start();
					} else {
						builAlert();
					}
					String [] fechas = fecha.split("/");
					int dia, mes,a;
					String me;
					dia = Integer.parseInt(fechas[0]);
					mes = Integer.parseInt(fechas[1]);
					a = Integer.parseInt(fechas[2].substring(2, 4));
					me = Justificar.mes(mes);
					Log.i("fecha", dia + " " + me + "  " + a);
					//thread.start();
				}
				else {
					InfraccionesActivity.this.finish();
					consu = false;
					Intent intent = new Intent(InfraccionesActivity.this, InfraccionesActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("direccion", title);
					bundle.putString("usuario", us);
					bundle.putInt("id", id);
					intent.putExtras(bundle);
					startActivity(intent);
					
				}
				
			}
		});
        
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.identificacion_array, android.R.layout.simple_spinner_dropdown_item);
        spIdentifica.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        
        spIdentificaT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        
        spIdentificaT1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        
        //ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.manifiesta_array, android.R.layout.simple_spinner_dropdown_item);
        spManifiesta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vManifiesta));
        
        //ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.uso_array, android.R.layout.simple_spinner_dropdown_item);
        spuso.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, usoSuelo));
        
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.gravedad_array, android.R.layout.simple_spinner_dropdown_item);
        spgravedad.setAdapter(adapter3);
        spNE.setAdapter(adapter3);
        
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.designado_array, android.R.layout.simple_spinner_dropdown_item);
        spdesignado.setAdapter(adapter5);
        spdesignado1.setAdapter(adapter5);
        
        this.tveliminar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 5) 
					spInfraccion.setEnabled(true);
				co--;
				etDesc.setText("");
				etDesc.setVisibility(View.GONE);
				etdato.setText("");
				etdato.setVisibility(View.GONE);
				tvuni.setVisibility(View.GONE);
				tvuni.setText("");
				tveliminar.setVisibility(View.GONE);
				desc = false;
				des = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);
			}
		});
        
        this.tveliminar1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 5) 
					spInfraccion.setEnabled(true);
				co--;
				etDesc1.setText("");
				etDesc1.setVisibility(View.GONE);
				etdato1.setText("");
				etdato1.setVisibility(View.GONE);
				tvuni1.setVisibility(View.GONE);
				tvuni1.setText("");
				tveliminar1.setVisibility(View.GONE);
				desc1 = false;
				des1 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);
			}
		});
        
        this.tveliminar2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 5) 
					spInfraccion.setEnabled(true);
				co--;
				etDesc2.setText("");
				etDesc2.setVisibility(View.GONE);
				etdato2.setText("");
				etdato2.setVisibility(View.GONE);
				tvuni2.setVisibility(View.GONE);
				tvuni2.setText("");
				tveliminar2.setVisibility(View.GONE);
				desc2 = false;
				des2 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);
			}
		});
        
        this.tveliminar3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 5) 
					spInfraccion.setEnabled(true);
				co--;
				etDesc3.setText("");
				etDesc3.setVisibility(View.GONE);
				etdato3.setText("");
				etdato3.setVisibility(View.GONE);
				tvuni3.setVisibility(View.GONE);
				tvuni3.setText("");
				tveliminar3.setVisibility(View.GONE);
				desc3 = false;
				des3 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);
			}
		});
        
        this.tveliminar4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 5) 
					spInfraccion.setEnabled(true);
				co--;
				etDesc4.setText("");
				etDesc4.setVisibility(View.GONE);
				etdato4.setText("");
				etdato4.setVisibility(View.GONE);
				tvuni4.setVisibility(View.GONE);
				tvuni4.setText("");
				tveliminar4.setVisibility(View.GONE);
				desc4 = false;
				des4 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);
			}
		});
        
        this.btnaceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validar()>0){
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Le falta ingresar la cantidad", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
				
				//->validar especificacion
				else if (etEspecificacion.getText().toString().equalsIgnoreCase("")) {
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Le falta ingresar la especificaciÔøΩn", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
				else  {
					Log.i("Validar", "no esta visible");
					AlertDialog.Builder dialog = new AlertDialog.Builder(InfraccionesActivity.this);
					dialog.setTitle("Al Aceptar los Hechos no podran ser Modificados");
					dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
					});
					dialog.setMessage("ÔøΩEsta seguro?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							rlcampo.setVisibility(View.GONE);
							dato = "";
							String descrip = etEspecificacion.getText().toString()+".";
							/*if (!Double.toString(latitud).equals("0.0") & !Double.toString(longitud).equals("0.0"))   
								descrip += ". LAS COORDENADAS APROXIMADAS SON: LONGITUD: " + longitud + " LATITUD: " + latitud +".";*/
							
							Log.i("Descripcion", descrip);
							if(!des.equals("")){
								infraccion();
								buscarInfraccion(des);
								tveliminar.setEnabled(false);
								if (!tvuni.getText().toString().equalsIgnoreCase("")) {
									seleccion += des + " (" + etdato.getText().toString().trim() + " " + tvuni.getText().toString().trim() + "); ";
								}
								else{
									seleccion += des + "; ";
								}
								dato += etdato.getText().toString().trim() + ","; 
							}
							if(!des1.equals("")){
								infraccion();
								buscarInfraccion(des1);
								tveliminar1.setEnabled(false);
								if (!tvuni1.getText().toString().equals("")) {
									seleccion += des1 + " (" + etdato1.getText().toString().trim() + " " + tvuni1.getText().toString().trim() + "); ";
								}
								else {
									seleccion += des1 + "; ";
								}
								dato += etdato1.getText().toString().trim()+ ",";
							}
							if(!des2.equals("")){
								infraccion();
								buscarInfraccion(des2);
								tveliminar2.setEnabled(false);
								if (!tvuni2.getText().toString().equals("")) {
									seleccion += des2 + " (" + etdato2.getText().toString().trim() + " " + tvuni2.getText().toString() + "); ";
								}
								else {
									seleccion += des2 + "; ";
								}
								dato += etdato2.getText().toString().trim()+ ",";
							}
							if(!des3.equals("")){
								infraccion();
								buscarInfraccion(des3);
								tveliminar3.setEnabled(false);
								if (!tvuni3.getText().toString().equals("")) {
									seleccion += des3 + " (" + etdato.getText().toString().trim() + " " + tvuni3.getText().toString() + "); ";
								}
								else {
									seleccion += des3 + "; ";
								}
								dato += etdato3.getText().toString().trim()+ ",";
							}
							if(!des4.equals("")){
								infraccion();
								buscarInfraccion(des4);
								tveliminar4.setEnabled(false);
								if (!tvuni4.getText().toString().equals("")) {
									seleccion += des4 + " (" + etdato.getText().toString().trim() + " " + tvuni4.getText().toString() + "); ";
								}
								else {
									seleccion += des4 + "; ";
								}
								dato += etdato4.getText().toString().trim()+ ",";
							}
							
							seleccion += descrip;
							Log.i("Dato", dato);
							Log.i("seleccion", seleccion);
							Log.i("id", id_hechos);
							Log.i("text", text + " 1 " + camp19);
							
							/*camp1 = (!camp1.equals("")) ? ordenar(camp1) : "";
							camp2 = (!camp2.equals("")) ? ordenar(camp2) : "";
							camp3 = (!camp3.equals("")) ? ordenar(camp3) : "";
							camp4 = (!camp4.equals("")) ? ordenar(camp4) : "";
							camp5 = (!camp5.equals("")) ? ordenar(camp5) : "";
							
							camp6 = (!camp6.equals("")) ? ordenar(camp6) : "";
							camp7 = (!camp7.equals("")) ? ordenar(camp7) : "";
							camp8 = (!camp8.equals("")) ? ordenar(camp8) : "";
							camp9 = (!camp9.equals("")) ? ordenar(camp9) : "";
							camp0 = (!camp0.equals("")) ? ordenar(camp0) : "";
							
							camp11 = (!camp11.equals("")) ? ordenar(camp11) : "";
							camp12 = (!camp12.equals("")) ? ordenar(camp12) : "";
							camp13 = (!camp13.equals("")) ? ordenar(camp13) : "";
							camp14 = (!camp14.equals("")) ? ordenar(camp14) : "";
							
							
							camp15 = (!camp15.equals("")) ? ordenar(camp15) : "";
							camp16 = (!camp16.equals("")) ? ordenar(camp16) : "";
							camp17 = (!camp17.equals("")) ? ordenar(camp17) : "";
							camp18 = (!camp18.equals("")) ? ordenar(camp18) : "";
							camp19 = (!camp19.equals("")) ? ordenar(camp19) : "";
							camp20 = (!camp20.equals("")) ? ordenar(camp20) : "";*/
							
							
							camp1 = (!camp1.equals("")) ? camp1 : "";
							camp2 = (!camp2.equals("")) ? camp2 : "";
							camp3 = (!camp3.equals("")) ? camp3 : "";
							camp4 = (!camp4.equals("")) ? camp4 : "";
							camp5 = (!camp5.equals("")) ? camp5 : "";
							
							camp6 = (!camp6.equals("")) ? camp6 : "";
							camp7 = (!camp7.equals("")) ? camp7 : "";
							camp8 = (!camp8.equals("")) ? camp8 : "";
							camp9 = (!camp9.equals("")) ? camp9 : "";
							camp0 = (!camp0.equals("")) ? camp0 : "";
							
							camp11 = (!camp11.equals("")) ? camp11 : "";
							camp12 = (!camp12.equals("")) ? camp12 : "";
							camp13 = (!camp13.equals("")) ? camp13 : "";
							camp14 = (!camp14.equals("")) ? camp14 : "";
							
							
							camp15 = (!camp15.equals("")) ? camp15 : "";
							camp16 = (!camp16.equals("")) ? camp16 : "";
							camp17 = (!camp17.equals("")) ? camp17 : "";
							camp18 = (!camp18.equals("")) ? camp18 : "";
							camp19 = (!camp19.equals("")) ? camp19 : "";
							camp20 = (!camp20.equals("")) ? camp20 : "";
							
							/*codi = (!codi.equals("")) ? ordenar(codi) : "";
							zoni = (!zoni.equals("")) ? ordenar(zoni) : "";
							regla = (!regla.equals("")) ? ordenar(regla) : "";
							l = (!l.equals("")) ? ordenar(l) : "";
							oe = (!oe.equals("")) ? ordenar(oe) : "";
							ne = (!ne.equals("")) ? ordenar(ne) : "";
							leeep = (!leeep.equals("")) ? ordenar(leeep) : "";*/
							
							/*text += (!camp1.equals("")) ? "Articulo(s) " + camp1 + " " + campo1 + ";" : "";
							text += (!camp2.equals("")) ? "Articulo(s) " + camp2 + " " + campo2 + ";" : "";
							text += (!camp3.equals("")) ? "Articulo(s) " + camp3 + " " + campo3 + ";" : "";
							text += (!camp4.equals("")) ? "Articulo(s) " + camp4 + " " + campo4 + ";" : "";
							text += (!camp5.equals("")) ? "Articulo(s) " + camp5 + " " + campo5 + ";" : "";
							
							text += (!camp6.equals("")) ? "Articulo(s) " + camp6 + " " + campo6 + ";" : "";
							text += (!camp7.equals("")) ? "Articulo(s) " + camp7 + " " + campo7 + ";" : "";
							text += (!camp8.equals("")) ? "Articulo(s) " + camp8 + " " + campo8 + ";" : "";
							text += (!camp9.equals("")) ? "Articulo(s) " + camp9 + " " + campo9 + ";" : "";
							text += (!camp0.equals("")) ? "Articulo(s) " + camp0 + " " + campo0 + ";" : "";
							
							text += (!camp11.equals("")) ? "Articulo(s) " + camp11 + " " + campo11 + ";" : "";
							text += (!camp12.equals("")) ? "Articulo(s) " + camp12 + " " + campo12 + ";" : "";
							text += (!camp13.equals("")) ? "Articulo(s) " + camp13 + " " + campo13 + ";" : "";
							text += (!camp14.equals("")) ? "Articulo(s) " + camp14 + " " + campo14 + ";" : "";
							
							text += (!camp15.equals("")) ? "Articulo(s) " + camp15 + " " + campo15 + ";" : "";
							text += (!camp16.equals("")) ? "Articulo(s) " + camp16 + " " + campo16 + ";" : "";
							text += (!camp17.equals("")) ? "Articulo(s) " + camp17 + " " + campo17 + ";" : "";
							text += (!camp18.equals("")) ? "Articulo(s) " + camp18 + " " + campo18 + ";" : "";
							text += (!camp19.equals("")) ? "Articulo(s) " + camp19 + " " + campo19 + ";" : "";
							text += (!camp20.equals("")) ? "Articulo(s) " + camp20 + " " + campo20 + ";" : "";*/
							
							text += (!camp1.equals("")) ? camp1 + " " + campo1 + ";" : "";
							text += (!camp2.equals("")) ? camp2 + " " + campo2 + ";" : "";
							text += (!camp3.equals("")) ? camp3 + " " + campo3 + ";" : "";
							text += (!camp4.equals("")) ? camp4 + " " + campo4 + ";" : "";
							text += (!camp5.equals("")) ? camp5 + " " + campo5 + ";" : "";
							
							text += (!camp6.equals("")) ? camp6 + " " + campo6 + ";" : "";
							text += (!camp7.equals("")) ? camp7 + " " + campo7 + ";" : "";
							text += (!camp8.equals("")) ? camp8 + " " + campo8 + ";" : "";
							text += (!camp9.equals("")) ? camp9 + " " + campo9 + ";" : "";
							text += (!camp0.equals("")) ? camp0 + " " + campo0 + ";" : "";
							
							text += (!camp11.equals("")) ? camp11 + " " + campo11 + ";" : "";
							text += (!camp12.equals("")) ? camp12 + " " + campo12 + ";" : "";
							text += (!camp13.equals("")) ? camp13 + " " + campo13 + ";" : "";
							text += (!camp14.equals("")) ? camp14 + " " + campo14 + ";" : "";
							
							text += (!camp15.equals("")) ? camp15 + " " + campo15 + ";" : "";
							text += (!camp16.equals("")) ? camp16 + " " + campo16 + ";" : "";
							text += (!camp17.equals("")) ? camp17 + " " + campo17 + ";" : "";
							text += (!camp18.equals("")) ? camp18 + " " + campo18 + ";" : "";
							text += (!camp19.equals("")) ? camp19 + " " + campo19 + ";" : "";
							text += (!camp20.equals("")) ? camp20 + " " + campo20 + ";" : "";
							
							/*text += (!codi.equals("")) ? "Articulo(s) " + codi + " " + cod + ";" : "";
							text += (!zoni.equals("")) ? "Articulo(s) " + zoni + " "  + zon + ";" : "";
							text += (!regla.equals("")) ? "Articulo(s) " + regla + " "  + reg +";" : "";
							text += (!l.equals("")) ? "Articulo(s) " + l + la + ";": "";
							text += (!oe.equals("")) ? "Articulo(s) " + oe + ordeco + ";" : "";
							text += (!ne.equals("")) ? "Articulo(s) " + ne + na + ";" : "";
							text += (!leeep.equals("")) ? "Articulo(s) " + leeep + lee + ";" : "";*/
							
							
							System.out.println(text);
							Log.i("text", text);
							etEspecificacion.setEnabled(false);
							etInfraccion.setText(text);
							btnaceptar.setVisibility(View.GONE);
							btnmodificar.setVisibility(View.VISIBLE);
							spInfraccion.setEnabled(false); 
							etSeleccion.setVisibility(View.VISIBLE);
							etSeleccion.setEnabled(false);
							etSeleccion.setText(seleccion);
							btnSi.setEnabled(false);
							btnNo.setEnabled(false);
							etManifiesta.setEnabled(true);
							/*if(id == 2) {
								etInfraccion.setEnabled(true);
								etSeleccion.setEnabled(true);
							}*/
							
							int len = 0;
							String txt[];
							txt = Justificar.justifocarTexto(seleccion);
							len += txt.length;
							txt = Justificar.justifocarTexto(hech);
							len += txt.length;
							txt = Justificar.justifocarTexto(text);
							len += txt.length;
							txt = Justificar.justifocarTexto(DECLARA);
							len += txt.length;
							txt = Justificar.justifocarTexto(etManifiesta.getText().toString());
							len += txt.length;
							Log.i("lineas", len + "");
							/*if (len > 13) {
								AlertDialog.Builder al = new AlertDialog.Builder(InfraccionesActivity.this);
								al.setMessage("El nÔøΩmero de caracteres utilizados excede la cantidad de lineas disponibles para impresiÔøΩn; le sujerimos modificar la selecciÔøΩn de Hechos");
								al.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										modificar();
									}
								});
								AlertDialog dia = al.create();
								dia.show();
							}*/
							
							//btnmodificar.setVisibility(View.GONE);
						}
					});
					AlertDialog alert = dialog.create();
					alert.show();
				}
			}
		});
        
        btnmodificar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				modificar();
			}
		});
       
        updateDisplay();
        
        rgReincidencia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbNReincidencia:
					etfolio.setText("");
					break;
				case R.id.rbApercebimiento:
					etfolio.setText("AP/" + id + "/");
					break;
				case R.id.rbOrdenvisita:
					etfolio.setText("OV/" + id + "/");
					break;
				case R.id.rbCitatorio:
					etfolio.setText("CI/" + id + "/");
					break;
				default:
					break;
				}
				
			}
		});
        
        /*this.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				InfraccionesActivity.this.btnOrden1.setVisibility(View.GONE);
				etOrden1.setVisibility(View.GONE);
				
				switch (checkedId) {
				
				case R.id.radioApercibimiento:
					infrac = 0;
					etDiaPlazo.setText("");
					etDiaPlazo.setEnabled(true);
					rlProp.setVisibility(View.VISIBLE);
					rlTestA.setVisibility(View.VISIBLE);
					rlVisita.setVisibility(View.VISIBLE);
					llNota.setVisibility(View.VISIBLE);
					llplazo.setVisibility(View.VISIBLE);
					llreincidencia.setVisibility(View.GONE);
					ante = "AP";
					formato = "apercibimiento";
					etGiro.setVisibility(View.VISIBLE);
					etMotivo.setVisibility(View.GONE);
					break;
				case R.id.radioInfraccion:
					infrac = 1;
					etDiaPlazo.setText("20");
					etDiaPlazo.setEnabled(false);
					rlProp.setVisibility(View.VISIBLE);
					rlTestA.setVisibility(View.VISIBLE);
					rlVisita.setVisibility(View.VISIBLE);
					llNota.setVisibility(View.VISIBLE);
					llplazo.setVisibility(View.VISIBLE);
					llreincidencia.setVisibility(View.GONE);
					ante = "IN";
					formato = "infraccion";
					InfraccionesActivity.this.btnOrden1.setVisibility(View.VISIBLE);
					etOrden1.setVisibility(View.VISIBLE);
					etGiro.setVisibility(View.GONE);
					etMotivo.setVisibility(View.GONE);
					//spReglamento.setVisibility(View.VISIBLE);
					//spReglamento.setVisibility(View.GONE);
					//tvReg.setVisibility(View.GONE);
					competencias = "";
					regla = "";
					idComp = 0;
					etOrden1.setText("");
					tvActa.setText("NÔøΩmero Acta");
					break;
				case R.id.radioOrdenV:
					infrac = 2;
					if(id == 1)
						rlProp.setVisibility(View.VISIBLE);
					else
						rlProp.setVisibility(View.GONE);
					rlTestA.setVisibility(View.GONE);
					rlVisita.setVisibility(View.GONE);
					llNota.setVisibility(View.GONE);
					llplazo.setVisibility(View.GONE);
					llreincidencia.setVisibility(View.GONE);
					ante = "OV";
					formato = "orden";
					etGiro.setVisibility(View.GONE);
					etMotivo.setVisibility(View.VISIBLE);
					spReglamento.setVisibility(View.VISIBLE);
					tvReg.setVisibility(View.VISIBLE);
					tvActa.setText("NÔøΩmero Orden");
					break;
					
				case R.id.radioCitatorio:
					infrac = 3;
					rlProp.setVisibility(View.GONE);
					rlTestA.setVisibility(View.GONE);
					rlVisita.setVisibility(View.GONE);
					llNota.setVisibility(View.GONE);
					llplazo.setVisibility(View.GONE);
					llreincidencia.setVisibility(View.GONE);
					ante = "CI";
					formato = "citatorio";
					etGiro.setVisibility(View.GONE);
					etMotivo.setVisibility(View.VISIBLE);
					break;
					
				case R.id.radioHechos:
					infrac = 4;
					rlProp.setVisibility(View.GONE);
					rlTestA.setVisibility(View.GONE);
					rlVisita.setVisibility(View.VISIBLE);
					llNota.setVisibility(View.GONE);
					llplazo.setVisibility(View.GONE);
					llreincidencia.setVisibility(View.GONE);
					//ante = "CI";
					formato = "hechos";
					etGiro.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				numeroA();
				Log.i("infraccion", infrac + "");
			}
		});*/
        
        this.btnSi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				spInfraccion.setEnabled(true);
				etInfraccion.setEnabled(false);
				etSeleccion.setEnabled(false);
				etInfraccion.setText("");
				etSeleccion.setText("");
				usoCatalogo = "S";
				Log.i("Uso catalogo S", usoCatalogo);
				/*if(id == 2) {
					etInfraccion.setEnabled(true);
					etSeleccion.setEnabled(true);
				}*/
			}
		});
        
        this.btnNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				spInfraccion.setEnabled(false);
				etInfraccion.setEnabled(true);
				etSeleccion.setEnabled(true);
				etdato.setVisibility(View.GONE);
				tveliminar.setVisibility(View.GONE);
				tvuni.setVisibility(View.GONE);
				etDesc.setText("");
				etDesc.setVisibility(View.GONE);
				etdato1.setVisibility(View.GONE);
				tvuni1.setVisibility(View.GONE);
				tveliminar1.setVisibility(View.GONE);
				etDesc1.setText("");
				etDesc1.setVisibility(View.GONE);
				tvuni2.setVisibility(View.GONE);
				etdato2.setVisibility(View.GONE);
				tveliminar2.setVisibility(View.GONE);
				etDesc2.setText("");
				etDesc2.setVisibility(View.GONE);
				tvuni3.setVisibility(View.GONE);
				etdato3.setVisibility(View.GONE);
				tveliminar3.setVisibility(View.GONE);
				etDesc3.setText("");
				etDesc3.setVisibility(View.GONE);
				tvuni4.setVisibility(View.GONE);
				etdato4.setVisibility(View.GONE);
				tveliminar4.setVisibility(View.GONE);
				etDesc4.setText("");
				etDesc4.setVisibility(View.GONE);
				btnaceptar.setVisibility(View.GONE);
				usoCatalogo = "N";
				Log.i("Uso catalogo N", usoCatalogo);
			}
		});
        
        spuso.setSelection(1);
        
        Log.i("iddmv nv", id + " " + "id");
        /*if(id == 2) {
        	etInfraccion.setEnabled(true);
        	etSeleccion.setEnabled(true);
        }*/
        
        medidas = buscarMedida("");
        //spIdentifica.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        for (int i = 0; i < medidas.size(); i++) {
			medida.add(medidas.get(i).getMedida());
		}
        spMedida.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, reglamento));
        spReglamento.setVisibility(View.GONE);
        
        if(id == 12) {
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cmedida);
        	etNombreComercial.setVisibility(View.VISIBLE);
        	spMedida.setAdapter(adapter);
        }
        
        if(id == 2) {
        	tvMotivo.setText(getResources().getString(R.string.verificar));
        	etMotivo.setHint(getResources().getString(R.string.verificar));
        	
        	tvNombreComercial.setText(getResources().getString(R.string.donde_ubica));
        	etNombreComercial.setHint(getResources().getString(R.string.donde_ubica));
        	
        	
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cmedida);
        	etNombreComercial.setVisibility(View.VISIBLE);
        	spMedida.setAdapter(adapter);
        }
        
        if(id == 3) {
        	tvCondominio.setText(getResources().getString(R.string.coto) + " o Mercado Tianguis");
        }
        
        if(!reglamento.isEmpty()) {
        	iComp = new int [reglamento.size() - 1];
        	comp = new String [reglamento.size() - 1];
        }
        
        for (int i = 0; i < reglamento.size(); i++) {
        	System.err.println(reglamento.get(i));
        	if(!TextUtils.isEmpty(reglamento.get(i))) {
        		comp[i-1] = "";
        		iComp[i-1] = 0;
				cb = new CheckBox(this);
				cb.setId(i);
				cb.setText(reglamento.get(i));
				cb.setTextColor(getResources().getColor(R.color.gray));
				cb.setOnClickListener(getOnClick(cb));
				
				llcomp.addView(cb);
        	}
		}
        
        if(id == 13) {
        	rborden.setVisibility(View.GONE);
        }
        
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, peticion);
    	spPeticion.setAdapter(adapter1);
    	
    	buscarPeticion();
        
       /* if(formato == "infraccion") { 
        	llcomp.setVisibility(View.GONE);
        }*/
        
    }
    
    View.OnClickListener getOnClick(final CheckBox b) {
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(b.isChecked()) {
					
					medidas(campoReg.get(v.getId()));
					/*if(id != 3)
						adapter.notifyDataSetChanged();*/
		    		
					//comp[id] = competencia.get(v.getId()) + " " + b.getText().toString();
					//iComp[id] = idCompetencia.get(v.getId());
				}
				else {
					comp[(v.getId())-1] = "";
					iComp[(v.getId())-1] = 0;
                }
				
				for (int i = 0; i < iComp.length; i++) {
					System.err.println(iComp[i] + " " + i);
				}
			}
		};
    	
    }
    
    public void inicio() {
    	if (!inicio) {
			consu = false;
			Calendar calendar = Calendar.getInstance();
			String h,m;
			h = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
			hora = h + ":" + m;
			int day = calendar.get(Calendar.MONTH);
			day += 1;
			fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + day + "/" + calendar.get(Calendar.YEAR);
			Log.i("Fecha/Hora", fecha + "/" + hora);
			lldiv.setVisibility(View.VISIBLE);
			btnInicio.setVisibility(View.GONE);
			btnConsultar.setVisibility(View.GONE);
			etEspecificacion.setVisibility(View.VISIBLE);
			citatorio = false;
			spconsultar.setVisibility(View.GONE);
			mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				thread = new Thread(InfraccionesActivity.this);
				thread.start();
			} else {
				builAlert();
			}
			String [] fechas = fecha.split("/");
			int dia, mes,a;
			String me;
			dia = Integer.parseInt(fechas[0]);
			mes = Integer.parseInt(fechas[1]);
			a = Integer.parseInt(fechas[2].substring(2, 4));
			me = Justificar.mes(mes);
			Log.i("fecha", dia + " " + me + "  " + a);
			//thread.start();
		}
		else {
			InfraccionesActivity.this.finish();
			consu = false;
			Intent intent = new Intent(InfraccionesActivity.this, InfraccionesActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("direccion", title);
			bundle.putString("usuario", us);
			bundle.putInt("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
			
		}
		/*if(consultarActa() == 0){
			Log.i("consultar", "si");
			asignarActa();
			etNumeroActa.setText(id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + "01");
		}
		else{
			Log.i("consultar", "no");
			asignarActa();
			n = Integer.valueOf(numero)+1;
			Log.i("Numero1", numero);
			if(Integer.parseInt(numero) > 0 & Integer.parseInt(numero) < 9){
				numero = "0"+String.valueOf(n);
				etNumeroActa.setText(id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
			}
			else{
				numero = String.valueOf(n);
				etNumeroActa.setText(id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
			}
		}*/
	}
    
    public String getMes(int mes) {
		switch (mes) {
		case 1:
			return "Enero";
			
		case 2:
			return "Febrero";
			
		case 3:
			return "Marzo";
			
		case 4:
			return "Abril";
			
		case 5:
			return "Mayo";
			
		case 6:
			return "Junio";
			
		case 7:
			return "Julio";
			
		case 8:
			return "Agosto";
			
		case 9:
			return "Septiembre";
			
		case 10:
			return "Octubre";
			
		case 11:
			return "Noviembre";

		default:
			return "Diciembre";
		}
	}
    
    public void numeroA() {
    	int n;
    	if(!citatorio){
			String [] na;
			if(consultarActa() == 0){
				Log.i("consultar", "si");
				numero = "01";
				etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
				
				buscarNumeroActa();
				if (!numero_acta.isEmpty()) {
					if (na()) {
						//aqui consultar el ultimo y asignar
						na = ultimo().split("/");
						n = Integer.parseInt(na[6]) + 1;
						numero = String.valueOf(n);
						if (numero.length() == 1)
							numero = "0" + n;
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
					}
				}
			}
			else{
				Log.i("consultar", "no");
				asignarActa();
				n = Integer.valueOf(numero)+1;
				Log.i("Numero1", numero);
				if(n > 0 & n <= 9){
					numero = "0"+String.valueOf(n);
					etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
					Log.i("numeros", "n " + etNumeroActa.getText().toString().substring(0, 16) + " v " + s.substring(0, 16));
					if(!etNumeroActa.getText().toString().substring(0, 16).equalsIgnoreCase(s.substring(0, 16))){
						Log.i("numero acta", "si");
						numero = "01";
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
						Log.i("nueva ", InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
					}
				}
				else{
					numero = String.valueOf(n);
					etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
				}
				
				buscarNumeroActa();
				if (!numero_acta.isEmpty()) {
					if (na()) {
						//aqui consultar el ultimo y asignar
						na = ultimo().split("/");
						n = Integer.parseInt(na[6]) + 1;
						numero = String.valueOf(n);
						if (numero.length() == 1)
							numero = "0" + n;
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
					}
				}
			}
		}
    }
    
    public void guardar() {
    	//try {
	    	if (validarI()) {
	    		int idLevantamiento, idLevantamientoSQL = 0;
					if(!etInfraccion.getText().toString().equalsIgnoreCase("") | infrac == 2 | infrac == 3 | infrac == 4) {
						Calendar calendar = Calendar.getInstance();
						String m,h;
						m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
						h = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
						hr = h + ":" + m;
						Log.i("hr", hr);
						
						String citatorio = "", orden = "";
						Formatter fmt = new Formatter();
						Formatter fmt1 = new Formatter();
						
							System.err.println("entro primero");
						
						if (!etCitatorio.getText().toString().equalsIgnoreCase("")) {
							int c = Integer.parseInt(etCitatorio.getText().toString());
							fmt.format("%04d", c);
							citatorio = tvC.getText().toString() + fmt + "-" + etAnoOrden.getText().toString();
							System.err.println("entro");
						}else
							System.err.println("entro else");
						if (!etNum.getText().toString().equalsIgnoreCase("")) {
							int o = Integer.parseInt(etNum.getText().toString());
							fmt1.format("%04d", o);
							orden = tvOV.getText().toString() + fmt1;
							System.err.println("entro1");
						}else
							System.err.println("entro else1");
						
						
						System.out.println("citatorio: " + fmt + " orden: " + fmt1);
						System.out.println("citatorio: " + citatorio + " orden: " + orden);
						
						if(insertarActa(etNumeroActa.getText().toString(), id, id_inspector1, fecha, infrac, numero) > 0) {
							Log.i("Inserto", "si");
						}
						else {
							Log.i("Inserto", "no");
						}
						
						if(ingresarNumeroActa(etNumeroActa.getText().toString(), spnombre.getSelectedItem().toString(), fecha) > 0)
							Log.i("Acta", "si");
						else
							Log.i("Acta", "no");
						
					String identifica,firmas;
					
					if(spIdentifica.getSelectedItem().toString().equalsIgnoreCase(""))
						identifica = "No";
					else 
						identifica = "Si";
					
					if(cbFirma.isChecked())
						firmas = "Si";
					else
						firmas = "No";
					
					for (int i = 0; i < iComp.length; i++) {
						if(i == 0) {
							if(iComp[i] != 0) {
								idComp = iComp[i];
							}
						}
						if(i == 1) {
							if(iComp[i] != 0) {
								idCompetencia1 = iComp[i];
							}
						}
						if(i == 2) {
							if(iComp[i] != 0) {
								idCompetencia2 = iComp[i];
							}
						}
						if(i == 3) {
							if(iComp[i] != 0) {
								idCompetencia3 = iComp[i];
							}
						}
						if(i == 4) {
							if(iComp[i] != 0) {
								idCompetencia4 = iComp[i];
							}
						}
						if(i == 5) {
							if(iComp[i] != 0) {
								idCompetencia5 = iComp[i];
							}
						}
					}
					int axo;
					if(TextUtils.isEmpty(etAlicencia.getText().toString().trim())){
						axo = 0;
					}else {
						axo = Integer.parseInt(etAlicencia.getText().toString().trim());
					}
					String reincidencia;
					if(swReincidencia.isChecked())
					    reincidencia = "SI";
					else
					    reincidencia = "NO";
						Log.i("levanta", ingresar(etNumeroActa.getText().toString(), tvC.getText().toString() + fmt + "-" + etAnoCitatorio.getText().toString(),infrac, tipoActa,id, fecha, hora, longitud, latitud, 
								orden, etFecham.getText().toString(),zon, id_inspector1, id_inspector2, 
								etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(), 
								etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(), 
								etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(), 
								etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(), 
								spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" +  etIfeT2.getText().toString(), 
								spdesignado1.getSelectedItem().toString(), usoCatalogo,etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos, 
								spuso.getSelectedItem().toString(), ""/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(), 
								Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(), hr, etCondominio.getText().toString(), etLote.getText().toString(), etManzana.getText().toString(), etReferencia.getText().toString(), etCorreo.getText().toString(), "", etConstruccion.getText().toString(),idComp,etEntreC.getText().toString(),etEntreC1.getText().toString(),etResponsable.getText().toString(),etRegistro.getText().toString(),"N",identifica,
								spPeticion.getSelectedItem().toString(),firmas,etMotivo.getText().toString(),etMedida.getText().toString(),etArticulo.getText().toString(),
								id_inspector3,id_inspector4,id_inspector5,id_inspector6,idCompetencia1,idCompetencia2,idCompetencia3,idCompetencia4,idCompetencia5,etLGiro.getText().toString().trim(),etAGiro.getText().toString(),axo,etNombreComercial.getText().toString(),etSector.getText().toString(),spNE.getSelectedItem().toString(),reincidencia) + "");
					
						//String peticion,String v_firma,String motivo_orden,String medida_seguridad,String articulo_medida
						String[] iHecho = null;
						String[] iCantidad = null;
						if(id_hechos != "") {
							iHecho = id_hechos.split(regex);
						}
						if(formato == "infraccion") {
							if(dato != "") {
								iCantidad = dato.split(regex);
							}
						}
						
						//Log.i("long", iHecho.length + "<- " + iCantidad.length);
						
						resu = false;
						
						/*archivo = "";
						archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
						
						for (int j = 1; j <= foto; j++) {
							String nom = etNumeroActa.getText().toString()+"-"+j+".jpg";
							nom = nom.replace("/", "_");
							String desc = "";
							if (j == 1) {
								desc = etDFoto.getText().toString();
							}
							else if (j == 2) {
								desc = etDFoto1.getText().toString();
							}
							else if (j == 3) {
								desc = etDFoto2.getText().toString();
							}
							else {
								desc = etDFoto3.getText().toString();
							}
							archivo+=nom;
							Log.i("Archivo", nom);
							Log.i("Inserto foto", insertFotrografia(idl, etNumeroActa.getText().toString(), nom, desc,"N")+"");
							//conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, "http://10.0.2.2:8080/serverSQL/insertFoto.php")
						}*/
						
						idLevantamiento = consultarLevantamientoID();
						
						if(formato == "infraccion") {
							for(int i = 0; i < iHecho.length; i++) {
								int can = 0;
								int iHec;
								if(idLevantamiento == 0) {
									idLevantamiento++;
								}
								if(iHecho[i].equalsIgnoreCase("")) {
									iHec = 0;
								}
								else {
									iHec = Integer.parseInt(iHecho[i]);
								}
								for (int j = 0; j < iCantidad.length; j++) {
									if (j == i) {
										if(!iCantidad[i].equalsIgnoreCase("")) {
											can = Integer.parseInt(iCantidad[i]);
										}
									}
								}
								Log.i("Detalle infacciÔøΩn", idLevantamiento + " " + etNumeroActa.getText().toString() + " " + iHec + " " + can);
								ingresarDetalleInfraccion(idLevantamiento, etNumeroActa.getText().toString(), iHec, can,"N");
							}
						}
						
						archivo = "";
						archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
						File folder = new File(archivo);
						folder.mkdirs();
						System.out.println("antes");
						//imprimir();
						imprimir(formato);
						System.out.println("despues");
						
						//10.84.35.153
						if (conn.validarConexion(InfraccionesActivity.this)) {
							Log.i("sii", "internet " + id_inspector2);
							//nv
							if (Connection.inserta(etNumeroActa.getText().toString(), citatorio,infrac, tipoActa,id, fecha, fecha + " " + hora, 
									longitud, latitud, orden, etFecham.getText().toString(),zon, id_inspector1, id_inspector2, 
									etNombreV.getText().toString(),spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(), 
									etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(), 
									etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(), 
									etPropietario.getText().toString(), etNombreT.getText().toString(),spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(), 
									spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(),spIdentificaT1.getSelectedItem().toString() + ":" + etIfeT2.getText().toString(), 
									spdesignado1.getSelectedItem().toString(), usoCatalogo, etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos, 
									spuso.getSelectedItem().toString(), ""/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(), 
									Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(), 
									fecha + " " + hr, "POR CALIFICAR",etCondominio.getText().toString() + " ",etManzana.getText().toString(),etLote.getText().toString(), etReferencia.getText().toString(), etCorreo.getText().toString(), /*etAlineamiento.getText().toString()*/"", etConstruccion.getText().toString(), etEntreC.getText().toString(),etEntreC1.getText().toString(),etResponsable.getText().toString(),etRegistro.getText().toString(),idComp,
									etMedida.getText().toString(),etArticulo.getText().toString().trim(),etMotivo.getText().toString().trim(),id_inspector3,id_inspector4,id_inspector5,id_inspector6,
									idCompetencia1,idCompetencia2,idCompetencia3,idCompetencia4,idCompetencia5
									,etLGiro.getText().toString().trim(),etAGiro.getText().toString(),axo,etNombreComercial.getText().toString(),etSector.getText().toString(),conf,spPeticion.getSelectedItem().toString(),spNE.getSelectedItem().toString(),reincidencia,/*"http://172.16.1.21/serverSQL/insertLevantamiento.php"*/"http://10.10.23.54/infracciones/serversql/insertLevantamientoas.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php"/"http://192.168.0.15/serverSQL/insertLevantamiento.php"*/).equalsIgnoreCase("S")) {
								
								resu = true;
								
								Log.i("inserto", "true");
							}
							else
								Log.i("inserto", "false");
						}
					
						
												
						if (conn.validarConexion((getApplicationContext())) & resu) 
							idLevantamientoSQL = getIdLevantamiento();
					
					if(formato.equalsIgnoreCase("infraccion")) {
						for(int i = 0; i < iHecho.length; i++) {
							int can = 0;
							int iHec;
							if(idLevantamiento == 0) {
								idLevantamiento++;
							}
							if(iHecho[i].equalsIgnoreCase("")) {
								iHec = 0;
							}
							else {
								iHec = Integer.parseInt(iHecho[i]);
							}
							for (int j = 0; j < iCantidad.length; j++) {
								if (j == i) {
									if(!iCantidad[i].equalsIgnoreCase("")) {
										can = Integer.parseInt(iCantidad[i]);
									}
								}
							}
							if (conn.validarConexion(getApplicationContext()) & resu) 
								conn.insertDetalle(idLevantamientoSQL, etNumeroActa.getText().toString(), iHec, can, /*"http://172.16.1.21/serverSQL/insertDetalle.php"*/"http://10.10.23.54/infracciones/serverSQL/insertDetalle.php"/*"http://pgt.no-ip.biz/serverSQL/insertDetalle.php"/"http://192.168.0.11/serverSQL/insertDetalle.php"*/);
						}
					}
					
					
						
						btnGuardar.setEnabled(false);
						guarda = true;
						llcomp.setEnabled(false);
						llcomp.setVisibility(View.GONE);
						//btnImprimir.setEnabled(true);
						btnmodificar.setEnabled(false);
						this.tvEvidencia.setVisibility(View.VISIBLE);
				        this.btnTomarF.setVisibility(View.VISIBLE);
				        //btnFtp.setEnabled(true);
				        btnVista.setEnabled(false);
				        btnTomarF.setEnabled(true);
				        if(guarda) {
				        	if(foto >= 1 ) {
				        		btnImprimir.setEnabled(true);
				        	} else {
				        		btnImprimir.setEnabled(false);
				        	}
				        }
					
						
					//}
						msj = (conn.validarConexion(getApplicationContext()) & resu) ? "Los datos se han guardado en la base de datos local y enviados al servidor" : "Los datos e imagenes se han guardado en la base de datos local";
						Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
						toast.setGravity(0, 0, 15);
						toast.show();
						deshabilitar();
					}else {
						Toast toast = Toast.makeText(getApplicationContext(), "EL CAMPO INFRACCION ESTA VACIO", Toast.LENGTH_LONG);
						toast.setGravity(0, 0, 15);
						toast.show();
					}
			}
    	/*}catch (Exception e) {
			Log.e("Guardar", e.getMessage() + " l");
			/*btnGuardar.setEnabled(false);
			btnImprimir.setEnabled(true);
			btnmodificar.setEnabled(false);
			this.tvEvidencia.setVisibility(View.VISIBLE);
	        this.btnTomarF.setVisibility(View.VISIBLE);
	        //btnFtp.setEnabled(true);
	        btnTomarF.setEnabled(true);
			Toast toast = Toast.makeText(getApplicationContext(), "Los datos se han guardado en la base de datos local", Toast.LENGTH_LONG);
			toast.setGravity(0, 0, 15);
			toast.show();*
		}*/
    }
    
    public void guardarFotoLocal() {
    	//
    	archivo = "";
		archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
    	
    	for (int j = 1; j <= foto; j++) {
			String nom = etNumeroActa.getText().toString()+"-"+j+".jpg";
			nom = nom.replace("/", "_");
			String desc = "";
			if (j == 1) {
				desc = etDFoto.getText().toString();
			}
			else if (j == 2) {
				desc = etDFoto1.getText().toString();
			}
			else if (j == 3) {
				desc = etDFoto2.getText().toString();
			}
			else {
				desc = etDFoto3.getText().toString();
			}
			archivo+=nom;
			Log.i("Archivo", nom);
			Log.i("Inserto foto", insertFotrografia(idl, etNumeroActa.getText().toString(), nom, desc,"N","N")+"");
			//conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, "http://10.0.2.2:8080/serverSQL/insertFoto.php")
		}
    	//
    	
    }
    
    public void guardarFoto() {
    	
    	archivo = "";
		archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
		File folder = new File(archivo);
		folder.mkdirs();
		//imprimir();
		int idLevantamientoSQL = 0;
		if (conn.validarConexion((getApplicationContext())) & resu) 
			idLevantamientoSQL = getIdLevantamiento();
		
		for (int j = 1; j <= foto; j++) {
			String nom = etNumeroActa.getText().toString()+"-"+j+".jpg";
			nom = nom.replace("/", "_");
			String desc = "";
			if (j == 1) {
				desc = etDFoto.getText().toString();
			}
			else if (j == 2) {
				desc = etDFoto1.getText().toString();
			}
			else if (j == 3) {
				desc = etDFoto2.getText().toString();
			}
			else {
				desc = etDFoto3.getText().toString();
			}
			archivo+=nom;
			Log.i("Archivo", nom);
			//conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, "http://10.0.2.2:8080/serverSQL/insertFoto.php");
			if (conn.validarConexion(getApplicationContext()) & resu)
				conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, /*"http://172.16.1.21/serverSQL/insertFoto.php"*/"http://10.10.23.54/infracciones/serverSQL/insertFoto.php"/*"http://pgt.no-ip.biz/serverSQL/insertFoto.php"/"http://192.168.0.11/serverSQL/insertFoto.php"*/);
		}
		archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
		System.out.println(new File(archivo + etNumeroActa.getText().toString().replace("/", "_") + ".txt").exists());
		
		///*
		pd = new ProgressDialog(InfraccionesActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Espere...");
		pd.setTitle("Subiendo FotografÔøΩa(s)");
		pd.setCancelable(true);
		pd.show();
		Thread t = new Thread(new Runnable() {
			boolean r = false;
			
			
			@Override
			public void run() {
				
				Log.i("Ento", "Run");
				
				//if (conn.validarConexion(getApplicationContext()) & resu) {
				if(r) {
				
					if (subirFoto()) {
						String dir = etNumeroActa.getText().toString();
						dir = dir.replace("/", "_");
						Log.i("Ento", "metodo");
						Looper.prepare();
						//String mFTP = "pgt.no-ip.biz";
						//String mFTP = "201.130.205.141";
						String mFTP = "172.16.1.21";
						FTPClient client = null;
						try {
							client = new FTPClient();
							//client.connect(InetAddress.getByName(mFTP));
							client.connect(InetAddress.getByName(mFTP), 21);
							showMsg("logeo: " + client.login("pgm", "pgm2012"));
							Log.i("Status", client.isConnected()+"");
							Log.i("Dir", client.printWorkingDirectory());
							Log.i("Crear dir", client.mkd(dir)+"");
							client.changeWorkingDirectory("/"+dir);
							Log.i("Dir",client.printWorkingDirectory());
							
							String n = etNumeroActa.getText().toString().replace("/", "_") + ".txt",a = archivo;
							a+=n;
							
							if (new File(a).exists()) {
								client.setFileType(FTP.BINARY_FILE_TYPE);
								BufferedInputStream buffInt=null;
								buffInt=new BufferedInputStream(new FileInputStream(a));
								client.enterLocalPassiveMode();
								System.out.println(client.storeFile(n, buffInt));
							}
						
							for (int i = 1; i <= foto; i++) {						
								String nom = etNumeroActa.getText().toString()+"-"+i+".jpg";
								String arch = archivo;
								nom = nom.replace("/", "_");
								arch+=nom;
								
								if (new File(arch).exists()) {
						
									Log.i("Archivo1", nom);
									client.setFileType(FTP.BINARY_FILE_TYPE);
									BufferedInputStream buffIn=null;
									buffIn=new BufferedInputStream(new FileInputStream(arch));
									client.enterLocalPassiveMode();
									System.out.println(client.storeFile(nom, buffIn));
								}
				        
								updateFoto(nom);
							}
							//showMsg("Deslogueo " + client.logout()+"");
							client.disconnect();
							//showMsg("Status " + client.isConnected()+"");
							Log.i("Status1", client.isConnected()+"");
							pd.dismiss();
							Toast toast = Toast.makeText(getApplicationContext(), "Las imagenes se han guardado en la base de datos local y enviados al servidor", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
						} catch (IOException e) {
							pd.dismiss();
							Log.e("Error en", e.getMessage());
							
						}
						Looper.loop();
						Looper.myLooper().quit();
						pd.dismiss();
					}
					else {
						pd.dismiss();
					}
				}
				pd.dismiss();
			}
			
		});
		t.start();//*/
    	
    }
    
    public void modificar() {
    	infraccion = 0;
    	co = 0;
		rlcampo.setVisibility(View.VISIBLE);
		etEspecificacion.setEnabled(true);
		etSeleccion.setText("");
		etInfraccion.setText("");
		spInfraccion.setEnabled(true);
		etManifiesta.setEnabled(true);
		
		
		camp1 = "";
		camp2 = "";
		camp3 = "";
		camp4 = "";
		camp5 = "";
		camp6 = "";
		camp7 = "";
		camp8 = "";
		camp9 = "";
		camp0 = "";
		camp11 = "";
		camp12 = "";
		camp13 = "";
		camp14 = "";
		camp15 = "";
		camp16 = "";
		camp17 = "";
		camp18 = "";
		camp19 = "";
		camp20 = "";
		
		c1 = "";
		c2 = "";
		c3 = "";
		c4 = "";
		c5 = "";
		c6 = "";
		c7 = "";
		c8 = "";
		c9 = "";
		c0 = "";
		c11 = "";
		c12 = "";
		c13 = "";
		c14 = "";
		
		text = "";
		seleccion = "";
		dato = "";
		id_hechos  = "";
		
		etDesc.setText("");
		etDesc.setVisibility(View.GONE);
		etdato.setText("");
		etdato.setVisibility(View.GONE);
		tvuni.setVisibility(View.GONE);
		tvuni.setText("");
		tveliminar.setVisibility(View.GONE);
		tveliminar.setEnabled(true);
		
		desc = false;
		des = "";
		
		etDesc1.setText("");
		etDesc1.setVisibility(View.GONE);
		etdato1.setText("");
		etdato1.setVisibility(View.GONE);
		tvuni1.setVisibility(View.GONE);
		tvuni1.setText("");
		tveliminar1.setVisibility(View.GONE);
		tveliminar1.setEnabled(true);
		desc1 = false;
		des1 = "";
		
		etDesc2.setText("");
		etDesc2.setVisibility(View.GONE);
		etdato2.setText("");
		etdato2.setVisibility(View.GONE);
		tvuni2.setVisibility(View.GONE);
		tvuni2.setText("");
		tveliminar2.setVisibility(View.GONE);
		tveliminar2.setEnabled(true);
		desc2 = false;
		des2 = "";
		
		etDesc3.setText("");
		etDesc3.setVisibility(View.GONE);
		etdato3.setText("");
		etdato3.setVisibility(View.GONE);
		tvuni3.setVisibility(View.GONE);
		tvuni3.setText("");
		tveliminar3.setVisibility(View.GONE);
		tveliminar3.setEnabled(true);
		desc3 = false;
		des3 = "";
		
		etDesc4.setText("");
		etDesc4.setVisibility(View.GONE);
		etdato4.setText("");
		etdato4.setVisibility(View.GONE);
		tvuni4.setVisibility(View.GONE);
		tvuni4.setText("");
		tveliminar4.setVisibility(View.GONE);
		tveliminar4.setEnabled(true);
		desc4 = false;
		des4 = "";
		
		btnSi.setEnabled(true);
		btnNo.setEnabled(true);
		
		btnmodificar.setVisibility(View.GONE);
    }
    
    
    public void deshabilitar() {
    	cons.setVisibility(View.VISIBLE);
		btnConsultar.setVisibility(View.GONE);
		spconsultar.setVisibility(View.VISIBLE);
		lldiv.setVisibility(View.VISIBLE);
		etEspecificacion.setVisibility(View.GONE);
		citatorio = true;
		btnSi.setEnabled(false);
		btnNo.setEnabled(false);
		tvEspe.setVisibility(View.GONE);
		//btnTomarF.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnMostrar.setEnabled(false);
		btnFecha.setEnabled(false);
		spPoblacion.setEnabled(false);
		spFraccionamiento.setEnabled(false);
		spnombre.setEnabled(false);
		spNombreA.setEnabled(false);
		etLongitud.setEnabled(false);
		etLatitud.setEnabled(false);
		etNum.setEnabled(false);
		etCitatorio.setEnabled(false);
		spZona.setEnabled(false);
		etFecham.setEnabled(false);
		etNombreV.setEnabled(false);
		spIdentifica.setEnabled(false);
		spManifiesta.setEnabled(false);
		etFraccionamiento.setEnabled(false);
		etCalle.setEnabled(false);
		etNumero.setEnabled(false);
		etNuemroInterior.setEnabled(false);
		etApellidoP.setEnabled(false);
		etApellidoM.setEnabled(false);
		spdesignado.setEnabled(false);
		etNombreT.setEnabled(false);
		etIfeT2.setEnabled(false);
		spdesignado1.setEnabled(false);
		etSeleccion.setEnabled(false);
		etInfraccion.setEnabled(false);
		spuso.setEnabled(false);
		//etDensidad.setEnabled(false);
		etManifiesta.setEnabled(false);
		spgravedad.setEnabled(false);
		etDiaPlazo.setEnabled(false);
		etfecha.setEnabled(false);
		etNombreT1.setEnabled(false);
		etIfeT.setEnabled(false);
		etPropietario.setEnabled(false);
		etManifiesta.setEnabled(false);
		etVManifiesta.setEnabled(false);
		etVIdentifica.setEnabled(false);
		spconsultar.setVisibility(View.GONE);
		etCondominio.setEnabled(false);
		spIdentifica.setEnabled(false);
		spIdentificaT.setEnabled(false);
		spIdentificaT1.setEnabled(false);
		btnVisualizar.setEnabled(false);
		/*etDesc.setEnabled(false);
		etDesc1.setEnabled(false);
		etDesc2.setEnabled(false);
		etDesc3.setEnabled(false);
		etDesc4.setEnabled(false);
		etDFoto.setEnabled(false);
		etDFoto1.setEnabled(false);
		etDFoto2.setEnabled(false);
		etDFoto3.setEnabled(false);*/
		etManzana.setEnabled(false);
		etNoA.setEnabled(false);
		etIfeA.setEnabled(false);
		etVigA.setEnabled(false);
		etLote.setEnabled(false);
		etReferencia.setEnabled(false);
		etNombreT.setEnabled(false);
		etOrden1.setEnabled(false);
		etConstruccion.setEnabled(false);
		etResponsable.setEnabled(false);
		etRegistro.setEnabled(false);
		etEntreC.setEnabled(false);
		etEntreC1.setEnabled(false);
		etCorreo.setEnabled(false);
		etMedida.setEnabled(false);
		etArticulo.setEnabled(false);
		
		cbFirma.setEnabled(false);
		
		spNombreA1.setEnabled(false);
		spNombreA2.setEnabled(false);
		spNombreA3.setEnabled(false);
		spNombreA4.setEnabled(false);
		
		etMotivo.setEnabled(false);
		etInspccionFue.setEnabled(false);
		
		spPeticion.setEnabled(false);
		
		spReglamento.setEnabled(false);
		
    }
    
    public String colCadena(String cadena, int col){

        

        for(int i=0;i<col;i++){

        	cadena = " "+cadena;

        }

        

        return cadena;

        

        }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	if(requestCode == 1){
    		System.err.println(requestCode + " " + resultCode);
    		if(resultCode == RESULT_OK) {
    				
    				if (foto == 1) {
    					etDFoto.setVisibility(View.VISIBLE);
    					btnver1.setVisibility(View.VISIBLE);
    					
    				}
    				else if (foto == 2) {
    					etDFoto1.setVisibility(View.VISIBLE);
    					btnver2.setVisibility(View.VISIBLE);
    				}
    				else if (foto == 3) {
    					etDFoto2.setVisibility(View.VISIBLE);
    					btnver3.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 4){
    					etDFoto3.setVisibility(View.VISIBLE);
    					btnver4.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 5){
    					etDFoto4.setVisibility(View.VISIBLE);
    					btnver5.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 6){
    					etDFoto5.setVisibility(View.VISIBLE);
    					btnver6.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 7){
    					etDFoto6.setVisibility(View.VISIBLE);
    					btnver7.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 8){
    					etDFoto7.setVisibility(View.VISIBLE);
    					btnver8.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 9){
    					etDFoto8.setVisibility(View.VISIBLE);
    					btnver9.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 10){
    					etDFoto9.setVisibility(View.VISIBLE);
    					btnver10.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 11){
    					etDFoto10.setVisibility(View.VISIBLE);
    					btnver11.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 12){
    					etDFoto11.setVisibility(View.VISIBLE);
    					btnver12.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 13){
    					etDFoto12.setVisibility(View.VISIBLE);
    					btnver13.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 14){
    					etDFoto13.setVisibility(View.VISIBLE);
    					btnver14.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 15){
    					etDFoto14.setVisibility(View.VISIBLE);
    					btnver15.setVisibility(View.VISIBLE);
    				}
    				
    				else {
    					etDFoto15.setVisibility(View.VISIBLE);
    					btnver16.setVisibility(View.VISIBLE);
    				}
    				
    				if(foto == 15)
    					btnTomarF.setVisibility(View.GONE);
    				else
    					btnTomarF.setVisibility(View.VISIBLE);
    				
    				System.out.println(foto + " foto");
    				if(foto >= 1) {
    					System.err.println("guarda " + guarda);
    				}else {
    					btnImprimir.setEnabled(false);
    				}
					
    				if(guarda)
						btnImprimir.setEnabled(true);
					else
						btnImprimir.setEnabled(false);
    				
    				if(resultCode == RESULT_OK) {
    					//btnImprimir.setEnabled(true);
    					String nom = etNumeroActa.getText().toString()+"-"+foto+".jpg";
        				nom = nom.replace("/", "_");
        				String desc = "";
        				archivo+=nom;
        				Log.i("Archivo", nom);
        				Log.i("Inserto foto", insertFotrografia(idl, etNumeroActa.getText().toString(), nom, desc,"N","N")+"");
    				}
    			}else if(resultCode == RESULT_CANCELED) {
    				System.err.println(RESULT_CANCELED);
    				foto--;
    				if(foto == 15)
    					btnTomarF.setVisibility(View.GONE);
    				else
    					btnTomarF.setVisibility(View.VISIBLE);
    				
    				if(foto > 1)
    					btnImprimir.setEnabled(true);
    				else {
    					btnImprimir.setEnabled(false);
    				}
    				
    				if(guarda)
						btnImprimir.setEnabled(true);
					else
						btnImprimir.setEnabled(false);
    			}
    	}
    	else
    		foto--;
    	if (requestCode == 0 & resultCode == RESULT_OK) {
    		String provider = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(provider != null){
                Log.v("yes", " Location providers: "+provider);
                Thread thread = new Thread(InfraccionesActivity.this);
				thread.start();
            }
    	}
    }
    
    public boolean subirFoto() {
    	boolean r = false;
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.rawQuery("SELECT * FROM Fotografia WHERE enviado = '" + "N" + "'", null);
    		if (c.moveToFirst()) {
				do {
					System.out.println(c.getString(3));
					r = true;
				} while (c.moveToNext());
			}
    		c.close();
    	}
    	db.close();
    	return r;
    }
    
    public void updateFoto(String archivo) {
    	GestionBD gestionaBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionaBD.getWritableDatabase();
    	String update = "UPDATE Fotografia SET enviado = 'S' WHERE archivo = '" + archivo + "'";
    	if (db != null) {
    		try {
    			Log.i("SQL", update);
    			db.execSQL(update);
    		}catch(SQLiteException e) {
    			Log.e("Error ", e.getMessage());
    		}
    		finally {
    			db.close();
    		}
    	}
    	
    }
    
    public Calendar calcular(String fecha,int dias){
		int i = 1,dia;
		final Calendar c = Calendar.getInstance();
		Date d;
		/*String [] f = fecha.split("/");
		c.set(Integer.parseInt(f[2]), Integer.parseInt(f[1])-1, Integer.parseInt(f[0]));*/
		while (i <= dias) {
			c.add(Calendar.DATE, 1);
			d = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dia = c.get(Calendar.DAY_OF_WEEK);
			if (!(dia == 1 | dia == 7)) {
				if (diaNoHabiles(sdf.format(d)) == 0) {
					i++;
				}
			 }
		}
		return c;
	}
    
    public int diaNoHabiles(String fecha) {
    	int d = 0;
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1); 
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	
    	if (db != null) {
    		
    		Cursor c = db.rawQuery("select count(*) from C_dia_no_habil where dia_no_habil = '"+fecha+"'", null);/*db.query("C_dia_no_habil", null, "id_c_dia_no_habil", null, null, null, null);*/
    		if (c.moveToFirst()) {
    			do {
					d = c.getInt(0);
				} while (c.moveToNext());
    			c.close();
    		}
    	}
    	db.close();
    	return d;
    }
    
    public int getIdLevantamiento() {
    	int id = 0;
    	//this.result = conn.search("http://192.168.0.11/serverSQL/getIdLevantamientos.php");
    	//this.result = conn.search("http://pgt.no-ip.biz/serverSQL/getIdLevantamientos.php");
    	this.result = conn.search("http://10.10.23.54/infracciones/serverSQL/getIdLevantamientos.php");
    	//this.result = conn.search("http://172.16.1.21/serverSQL/getIdLevantamientos.php");
    	
    		try {
    			this.jArray = new JSONArray(result);
    			for (int i = 0; i < jArray.length(); i++) {
    				this.json_data = this.jArray.getJSONObject(i);
    				id = json_data.getInt("id_levantamiento");
    			}
			return id;
    		} catch (Exception e) {
    			Log.e("ERROR", e.getMessage());
    		}
    	
    	return id;
    }
    
    public int getIdDetalle() {
    	//this.result = conn.search("http://192.168.0.11/serverSQL/getDetalleInfaccion.php");
    	//this.result = conn.search("http://10.0.2.2/serverSQL/getDetalleInfaccion.php");
    	this.result = conn.search("http://10.10.23.54/infracciones/serverSQL/getDetalleInfraccion.php");
    	//this.result = conn.search("http://172.16.1.21/serverSQL/getDetalleInfaccion.php");
    	
    	int id = 0;
    	try {
    		this.jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				this.json_data = this.jArray.getJSONObject(i);
				id = json_data.getInt("id_levantamiento");
			}
		} catch (Exception e) {
			Log.e("ERROR", "Error en: " + e.getMessage());
		}
    	return id;
    }
    
    public void poblacion() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.query("C_poblacion", null, "id_c_poblacion", null, "poblacion", null, "poblacion ASC");
    		try {
    			if (c.moveToFirst()){
    				poblacion.add("");
    				do {
    					poblacion.add(c.getString(1));
    				} while (c.moveToNext());
    			}
    		}catch (SQLiteException e) {
    			Log.e("Error", e.getMessage());
			}
    		finally {
    			c.close();
    			db.close();
    		}
    	}
    }
    
    public void fraccionamiento() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.query("C_fraccionamiento", null, "id_c_fraccionamiento", null, "fraccionamiento", null, "fraccionamiento ASC");
    		try {
    			if (c.moveToFirst()){
    				fraccionamiento.add("");
    				zonas.add("");
    				do {
    					fraccionamiento.add(c.getString(1));
    					zonas.add(c.getString(c.getColumnIndex("zona")));
    				} while (c.moveToNext());
    			}
    		}catch (SQLiteException e) {
				Log.e("Error", e.getMessage());
			}
    		finally{
    			c.close();
    			db.close();
    		}
    	}
    	
    }
    
    public void medidas(String condicion) {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if(db != null) {
    		String sql = "select * from c_medida_precautoria where ";
    		if(condicion.equalsIgnoreCase("")) {
    			sql += " 1 = 1";
    		}else {
    			sql += " campo = '" + condicion + "'";
    		}
    		System.err.println(sql);
    		Cursor cursor = db.rawQuery(sql, null);
    		try {
				if(cursor.moveToFirst()) {
					campos.clear();
					cmedida.clear();
					art.clear();
					orden.clear();
					
					campos.add("");
					cmedida.add("");
					art.add("");
					orden.add("");
					
					do {
						campos.add(cursor.getString(cursor.getColumnIndex("campo")));
						cmedida.add(cursor.getString(cursor.getColumnIndex("medida_precautoria")));
						art.add(cursor.getString(cursor.getColumnIndex("articulos")));
						orden.add(cursor.getString(cursor.getColumnIndex("ordenamiento")));
					} while (cursor.moveToNext());
				}
			} catch (SQLiteException e) {
				System.out.println(e.getMessage());
			}finally{
				cursor.close();
				db.close();
			}
    	}
    }
    
    public void competencia() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if(db != null) {
    		Cursor cursor = db.rawQuery("select * from C_ordenamiento where id_c_direccion = " + id, null);
    		try {
				if(cursor.moveToFirst()) {
					reglamento.add("");
					competencia.add("");
					idCompetencia.add(0);
					campoReg.add("");
					do {
						reglamento.add(cursor.getString(1));
						competencia.add(cursor.getString(cursor.getColumnIndex("competencia")));
						idCompetencia.add(cursor.getInt(0));
						campoReg.add(cursor.getString(2));
					} while (cursor.moveToNext());
				}
			} catch (SQLiteException e) {
				System.out.println(e.getMessage());
			}finally{
				cursor.close();
				db.close();
			}
    	}
    }
    
    public void manifiesta() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		System.err.println(" visitado m ");
    		Cursor c = db.query("C_visitado_manifiesta", null, " id_c_direccion = 2", null, null, null, "manifiesta");
    		if (c.moveToFirst()){
    			do {
					vManifiesta.add(c.getString(2));
				} while (c.moveToNext());
    			vManifiesta.add("");
    		}
    		c.close();
    	}
    	db.close();
    }
    
    public void identifica() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.query("C_visitado_identifica", null, "id_c_direccion = '" + 2 + "'", null, null, null, "identificacion");
    		if (c.moveToFirst()){
    			do {
					vIdentifica.add(c.getString(2));
				} while (c.moveToNext());
    			vIdentifica.add("");
    		}
    		c.close();
    	}
    	db.close();
    }
    
    public void usoSuelo() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.query("C_uso_suelo", null, "id_c_uso_suelo", null, null, null, "uso_suelo");
    		if (c.moveToFirst()){
    			do {
					usoSuelo.add(c.getString(2));
				} while (c.moveToNext());
    			usoSuelo.add("");
    		}
    		c.close();
    	}
    	db.close();
    }
    
    public void foto () {
    	GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestion.getReadableDatabase();
    	try {
			if (db != null) {
				Cursor c = db.query("Fotografia", null, "id_fotografia", null, null, null, null);
				if (c.moveToFirst()){
					do {
						Log.i("FOTOGRAFIA", c.getInt(0) + " " + c.getInt(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4));
					} while (c.moveToNext());
				}
				c.close();
			}
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
    	finally {
    		db.close();
    	}
    }
    
    public long insertFotrografia(int levantamiento, String numeroActa,String archivo, String descripcion,String com,String estatus) {
    	long n = 0;
    	GestionBD gestionarBD = new GestionBD(this, "inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getWritableDatabase();
    	
    	if (db != null) {
    		try {
    			ContentValues cv = new ContentValues();
    			cv.put("id_levantamiento", levantamiento);
    			cv.put("numero_acta", numeroActa);
    			cv.put("archivo", archivo);
    			cv.put("descripcion", descripcion);
    			cv.put("enviado", com);
    			cv.put("estatus", estatus);
    			n = db.insert("Fotografia", null, cv);
    		}catch (SQLiteException e) {
    			Log.e("ERROR en:", e.getMessage());
    		}
    		finally {
    			db.close();
    		}
    	}
    	return n;
    }
    
    public long ingresarDetalleInfraccion(int idLevantamiento,String numeroActa,int idInfraccion, int cantidad,String estatus ) {
     
    	long n = 0;
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getWritableDatabase();
    	
    	if(db != null) {
    		ContentValues cv = new ContentValues();
    		cv.put("id_levantamiento", idLevantamiento);
    		cv.put("numero_acta", numeroActa);
    		cv.put("id_c_infraccion", idInfraccion);
    		cv.put("cantidad",cantidad);
    		cv.put("estatus", estatus);
    	
    		n = db.insert("Detalle_infraccion", null, cv);
    	}
    	db.close();
    	return n;
    }
    
    public void validarFecha(){
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	
    	try {
			if(db != null){
				Cursor c = db.rawQuery("Select * from Levantamiento ORDER BY id_levantamiento DESC LIMIT 0,1", null);
				//Cursor cursor = db.query("Levantamiento", null, "id_levantamiento", null, null, null, "id_levantamiento DESC", "0,1");
				if(c.moveToFirst()) {
					do {
						c_fecha = c.getString(6);
					} while (c.moveToNext());
				}
				c.close();
			}
		} catch (Exception e) {
			
		}
    	finally {
    		db.close();
    	}
    }
    
    public String consultarFoto(){
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	String str = "";
    	try {
			if(db != null){
				Cursor c  = db.query("Fotografia", null, "id_levantamiento", null, null, null, null);
				if(c.moveToFirst()){
					do {
						Log.i("RESULT", "Id: " + c.getInt(0) + " levantamiento: " + c.getInt(1) + " No Acta: " + c.getString(2) + " Archivo: " + c.getString(3) + " descripcion: " + c.getString(4));
						str = "Id: " + c.getInt(0) + " levantamiento: " + c.getInt(1) + " No Acta: " + c.getString(2) + " Archivo: " + c.getString(3) + " descripcion: " + c.getString(4);
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException e) {
			Log.e("ERROR", "en: " + e.getMessage());
		}
    	finally {
    		db.close();
    	}
    	return str;
    }
    
    public boolean consultarLevantamiento(){
    	boolean r = false;
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	consultar.add("");
    	
    	try {
			if(db != null){
				//Cursor c  = db.query("Levantamiento", null, "id_levantamiento", null, null, null, null);
				Cursor c = db.rawQuery("select * from Levantamiento where id_c_direccion = '" + id + "'", null);
				if(c.moveToFirst()){
					do {
						r = true;
						consultar.add(c.getString(1));
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException e) {
			
		}
    	finally {
    		db.close();
    	}
    	return r;
    }
    
    public String consultarUsuario(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
				Cursor c = db.rawQuery("SELECT nombre FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario1(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i2.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
						id_i2.add(c.getInt(1));
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario2(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i3.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
						id_i3.add(c.getInt(1));
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario3(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i4.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
						id_i4.add(c.getInt(1));
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario4(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i5.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						id_i5.add(c.getInt(1));
						System.err.println(nombre);
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario5(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i6.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						id_i6.add(c.getInt(1));
						System.err.println(nombre);
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public void consultarLevantamiento(String numero_acta) {
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	
    	if(db != null) {
    		try {
    			ArrayList<String> array = new ArrayList<String>();
    			ArrayList<String> nombreI1 = new ArrayList<String>();
    			ArrayList<String> nombreI2 = new ArrayList<String>();
    			ArrayList<String> nombreI3 = new ArrayList<String>();
    			ArrayList<String> nombreI4 = new ArrayList<String>();
    			ArrayList<String> nombreI5 = new ArrayList<String>();
    			ArrayList<String> nombreI6 = new ArrayList<String>();
    			ArrayList<String> identifica = new ArrayList<String>();
    			ArrayList<String> manifiesta = new ArrayList<String>();
    			ArrayList<String> designado = new ArrayList<String>();
    			ArrayList<String> designado1 = new ArrayList<String>();
    			ArrayList<String> uso = new ArrayList<String>();
    			ArrayList<String> gravedad = new ArrayList<String>();
    			
				Cursor c = db.rawQuery("SELECT * FROM Levantamiento WHERE numero_acta = '" + numero_acta + "'", null);
				
				if(c.moveToFirst()) {
					for (int i = 0; i < c.getColumnCount(); i++) {
						System.out.println(c.getColumnName(i) + " " + i);
						//System.out.println("hola");
					}
					do {
						
						spInfraccion.setEnabled(false);
						btnVisualizar.setEnabled(false);
						Log.i("SI", "SI ENTRO " + c.getString(10));
						etVIdentifica.setEnabled(false);
						etVManifiesta.setEnabled(false);
						array.add(c.getString(12));
						identifica.add(c.getString(16));
						manifiesta.add(c.getString(17));
						designado.add(c.getString(27));
						designado1.add(c.getString(30));
						uso.add(c.getString(35));
						gravedad.add(c.getString(38));
						nombreI1.add(consultarUsuario(c.getInt(13)));
						nombreI2.add(consultarUsuario(c.getInt(14)));
						
						if(c.getInt(c.getColumnIndex("id_c_inspector3")) > 0) 
							nombreI3.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector3"))));
						if(c.getInt(c.getColumnIndex("id_c_inspector4")) > 0) 
							nombreI4.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector4"))));
						if(c.getInt(c.getColumnIndex("id_c_inspector5")) > 0) 
							nombreI5.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector5"))));
						if(c.getInt(c.getColumnIndex("id_c_inspector6")) > 0) 
							nombreI6.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector6"))));
						
						hora = c.getString(7);
						fecha = c.getString(6);
						hr = c.getString(41);
						
						spnombre.setAdapter(null);
						spnombre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI1));
						spnombre.setEnabled(false);
						spNombreA.setAdapter(null);
						spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI2));
						spNombreA.setEnabled(false);
						
						spNombreA1.setAdapter(null);
						spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI3));
						spNombreA1.setEnabled(false);
						spNombreA2.setAdapter(null);
						spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI4));
						spNombreA2.setEnabled(false);
						
						spNombreA3.setAdapter(null);
						spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI5));
						spNombreA3.setEnabled(false);
						spNombreA4.setAdapter(null);
						spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI6));
						spNombreA4.setEnabled(false);
						
						
						etLongitud.setText(c.getString(8));
						etLongitud.setEnabled(false);
						etLatitud.setText(c.getString(9));
						etLatitud.setEnabled(false);
						etNum.setText(c.getString(10));
						orde = c.getString(10);
						etNum.setEnabled(false);
						etNumeroActa.setText(c.getString(1));
						etCitatorio.setText(c.getString(2));
						etCitatorio.setEnabled(false);
						spZona.setAdapter(null);
						spZona.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array));
						spZona.setEnabled(false);
						etFecham.setText(c.getString(6));
						etFecham.setEnabled(false);
						etNombreV.setText(c.getString(15));
						etNombreV.setEnabled(false);
						spIdentifica.setAdapter(null);
						spIdentifica.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,identifica));
						spIdentifica.setEnabled(false);
						spManifiesta.setAdapter(null);
						spManifiesta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, manifiesta));
						spManifiesta.setEnabled(false);
						etFraccionamiento.setText(c.getString(18));
						etFraccionamiento.setEnabled(false);
						etCalle.setText(c.getString(19));
						etCalle.setEnabled(false);
						etNumero.setText(c.getString(20));
						etNumero.setEnabled(false);
						etNuemroInterior.setText(c.getString(21));
						etNuemroInterior.setEnabled(false);
						etApellidoP.setText(c.getString(22));
						etApellidoP.setEnabled(false);
						etApellidoM.setText(c.getString(23));
						etApellidoM.setEnabled(false);
						etPropietario.setText(c.getString(24));
						etPropietario.setEnabled(false);
						spdesignado.setAdapter(null);
						spdesignado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,designado));
						spdesignado.setEnabled(false);
						etNombreT1.setText(c.getString(28));
						etNombreT1.setEnabled(false);
						etIfeT2.setText(c.getString(29));
						etIfeT2.setEnabled(false);
						spdesignado1.setAdapter(null);
						spdesignado1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,designado1));
						spdesignado1.setEnabled(false);
						etSeleccion.setText(c.getString(32));
						etSeleccion.setEnabled(false);
						etInfraccion.setText(c.getString(33));
						etInfraccion.setEnabled(false);
						spuso.setAdapter(null);
						spuso.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,uso));
						spuso.setEnabled(false);
						/*aqui etDensidad.setText(c.getString(36));
						etDensidad.setEnabled(false);aqui*/
						etManifiesta.setText(c.getString(37));
						etManifiesta.setEnabled(false);
						spgravedad.setAdapter(null);
						spgravedad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,gravedad));
						spgravedad.setEnabled(false);
						etDiaPlazo.setText(c.getString(39));
						etDiaPlazo.setEnabled(false);
						etfecha.setText(c.getString(40));
						etfecha.setEnabled(false);
						etNombreT.setText(c.getString(25));
						etNombreT.setEnabled(false);
						etIfeT.setText(c.getString(26));
						etCondominio.setText(c.getString(47));
						etIfeT.setEnabled(false);
						spIdentificaT.setEnabled(false);
						spIdentificaT1.setEnabled(false);
						etIfeA.setText(c.getString(29));
						ifeA = c.getString(29);
						etIfeA.setEnabled(false);
						etNoA.setEnabled(false);
						etVigA.setEnabled(false);
						etLote.setText(c.getString(49));
						etManzana.setText(c.getString(48));
						
						
						etResponsable.setText(c.getString(c.getColumnIndex("responsable_obra")));
						etRegistro.setText(c.getString(c.getColumnIndex("registro_responsable")));
						etConstruccion.setText(c.getString(c.getColumnIndex("l_construccion")));
						etEntreC.setText(c.getString(c.getColumnIndex("entre_calle1")));
						etEntreC1.setText(c.getString(c.getColumnIndex("entre_calle2")));
						etMedida.setText(c.getString(c.getColumnIndex("medida_seguridad")));
						etArticulo.setText(c.getString(c.getColumnIndex("articulo_medida")));
						
						etInspccionFue.setText(c.getString(c.getColumnIndex("peticion")));
						etMotivo.setText(c.getString(c.getColumnIndex("motivo_orden")));
						etMedida.setText(c.getString(c.getColumnIndex("medida_seguridad")));
						etArticulo.setText(c.getString(c.getColumnIndex("articulo_medida")));
						
						
						if (c.getString(c.getColumnIndex("v_firma")) != null) {
							System.out.println(c.getString(c.getColumnIndex("v_firma")) + " FIRMA");
							System.out.println(c.getString(c.getColumnIndex("v_firma")).equalsIgnoreCase("si") + " FIRMA");
							if(c.getString(c.getColumnIndex("v_firma")).equalsIgnoreCase("si"))
								cbFirma.setChecked(true);
							else
								cbFirma.setChecked(false);
						} else {
							cbFirma.setChecked(false);
						}
						
						etInspccionFue.setText(c.getString(c.getColumnIndex("peticion")));
						
						
						cbFirma.setEnabled(false);
						
						spNombreA1.setEnabled(false);
						spNombreA2.setEnabled(false);
						spNombreA3.setEnabled(false);
						spNombreA4.setEnabled(false);
						
						etMotivo.setEnabled(false);
						etInspccionFue.setEnabled(false);
						
						spPeticion.setEnabled(false);
						
						
						
						
						//etReferencia.setText(c.getString(c.getColumnIndex("referencia")));
						System.out.println(c.getString(49) + " " + c.getColumnName(49));
						//Log.e("lote ", c.getString(50) + " " + c.getColumnName(50));
						Log.i("ifee", c.getString(26));
						
					} while (c.moveToNext());
					c.close();
				}
			} catch (SQLiteException e) {
				Log.e("error bd", e.getMessage().toString() + " marco nulo");
			}
    		finally {
    			db.close();
    		}
    	}
    }
    
    public int validar(){
    	int r = 0;
    	r += (etdato.getVisibility() == View.VISIBLE && validarCampos(this.etdato)) ?  1 : 0;
    	r += (etdato1.getVisibility() == View.VISIBLE && validarCampos(this.etdato1)) ? 1 : 0;
    	r += (etdato2.getVisibility() == View.VISIBLE && validarCampos(this.etdato2)) ? 1 : 0;
    	r += (etdato3.getVisibility() == View.VISIBLE && validarCampos(this.etdato3)) ? 1 : 0;
    	r += (etdato4.getVisibility() == View.VISIBLE && validarCampos(this.etdato4)) ? 1 : 0;
    	Log.i("r", String.valueOf(r));
    	//return r;
    	return 0;
    }
    
    public static boolean validarCampos(EditText et){
		return((et == null) || (et.getText().toString() == null) || et.getText().toString().equalsIgnoreCase(""));
	}
    
    public boolean validarSpinner(Spinner sp){
    	return((sp.getSelectedItem().toString() == null) || (sp.getSelectedItem() == null) || sp.getSelectedItem().toString().equals(""));
    }
    
    protected boolean validarI() {
    	System.err.println(infrac);
    	boolean valid = true;
    	StringBuffer sb = new StringBuffer();
    	sb.append("Los siguientes campos son requeridos: \n");
    	if(infrac == 1) {
	    	
	    	if(validarCampos(this.etfecha)){
	    		sb.append("Ingrese la fecha. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)){
	    		sb.append("Ingrese el nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etFraccionamiento)){
	    		sb.append("Ingrese el fraccionamiento. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etCalle)){
	    		sb.append("Ingrese la calle. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etPropietario)){
	    		sb.append("Ingrese el propietario o razÔøΩn social.\n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreT)){
	    		sb.append("Ingrese el nombre del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT)){
	    		sb.append("Ingrese el ife del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT2)){
	    		sb.append("Ingrese el ife del segundo testigo. \n");
	    		valid = false;
	    	}
	    	
	    	if(validarCampos(this.etManifiesta)){
	    		sb.append("Ingrese lo que el visitado manifiesta. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etDiaPlazo)) {
	    		sb.append("Ingrese los dias de plazo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)) {
	    		sb.append("Ingrese en nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVManifiesta)){
	    		sb.append("Ingrese lo que el visitado manifiesta. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVIdentifica)){
	    		sb.append("Ingrese la identificacion del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spdesignado)){
	    		sb.append("Seleccione por quien fue designado el primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spdesignado1)){
	    		sb.append("Seleccione por quien fue designado el segundo testigo. \n");
	    		valid = false;
	    	}
	    	/*if(validarSpinner(this.spuso)){
	    		sb.append("Seleccione el tipo de suelo. \n");
	    		valid = false;
	    	}*/
	    	if(validarSpinner(this.spgravedad)){
	    		sb.append("Seleccione la gravedad \n");
	    		valid = false;
	    	}
    	} else if(infrac == 3) {
	    	
	    	if(validarCampos(this.etfecha)){
	    		sb.append("Ingrese la fecha. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)){
	    		sb.append("Ingrese el nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etFraccionamiento)){
	    		sb.append("Ingrese el fraccionamiento. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etCalle)){
	    		sb.append("Ingrese la calle. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etPropietario)){
	    		sb.append("Ingrese el propietario o razÔøΩn social.\n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreT)){
	    		sb.append("Ingrese el nombre del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT)){
	    		sb.append("Ingrese el ife del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT2)){
	    		sb.append("Ingrese el ife del segundo testigo. \n");
	    		valid = false;
	    	}
	    	
	    	if(validarCampos(this.etManifiesta)){
	    		sb.append("Ingrese lo que el visitado manifiesta. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)) {
	    		sb.append("Ingrese en nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVManifiesta)){
	    		sb.append("Ingrese lo que el visitado manifiesta. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVIdentifica)){
	    		sb.append("Ingrese la identificacion del visitado. \n");
	    		valid = false;
	    	}
	    	/*if(validarSpinner(this.spuso)){
	    		sb.append("Seleccione el tipo de suelo. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spgravedad)){
	    		sb.append("Seleccione la gravedad \n");
	    		valid = false;
	    	}*/
    	}else if(infrac == 4) {
	    	
	    	if(validarCampos(this.etfecha)){
	    		sb.append("Ingrese la fecha. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)){
	    		sb.append("Ingrese el nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etFraccionamiento)){
	    		sb.append("Ingrese el fraccionamiento. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etCalle)){
	    		sb.append("Ingrese la calle. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etPropietario)){
	    		sb.append("Ingrese el propietario o razÔøΩn social.\n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreT)){
	    		sb.append("Ingrese el nombre del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT)){
	    		sb.append("Ingrese el ife del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT2)){
	    		sb.append("Ingrese el ife del segundo testigo. \n");
	    		valid = false;
	    	}
	    	
	    	if(validarCampos(this.etManifiesta)){
	    		sb.append("Ingrese lo que el visitado manifiesta. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)) {
	    		sb.append("Ingrese en nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVManifiesta)){
	    		sb.append("Ingrese lo que el visitado manifiesta. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVIdentifica)){
	    		sb.append("Ingrese la identificacion del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spdesignado)){
	    		sb.append("Seleccione por quien fue designado el primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spdesignado1)){
	    		sb.append("Seleccione por quien fue designado el segundo testigo. \n");
	    		valid = false;
	    	}
	    	/*if(validarSpinner(this.spuso)){
	    		sb.append("Seleccione el tipo de suelo. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spgravedad)){
	    		sb.append("Seleccione la gravedad \n");
	    		valid = false;
	    	}*/
    	}
    	else {
	    	if(validarCampos(this.etfecha)){
	    		sb.append("Ingrese la fecha. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)){
	    		sb.append("Ingrese el nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etFraccionamiento)){
	    		sb.append("Ingrese el fraccionamiento. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etCalle)){
	    		sb.append("Ingrese la calle. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etDiaPlazo)) {
	    		sb.append("Ingrese los dias de plazo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etNombreV)) {
	    		sb.append("Ingrese en nombre del visitado. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etVIdentifica)){
	    		sb.append("Ingrese la identificacion del visitado. \n");
	    		valid = false;
	    	}
	    	/*if(validarSpinner(this.spuso)){
	    		sb.append("Seleccione el tipo de suelo. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spgravedad)){
	    		sb.append("Seleccione la gravedad \n");
	    		valid = false;
	    	}*/
    	}
    	
    	if(!valid){
    		showMsg(sb.toString());
    	}
    	return valid;
    }
    
    public String ordenar(String var){
    	hs.clear();
    	array.clear();
    	String[] num = var.split(regex);
		for (int i = 0; i < num.length; i++) {
			array.add(num[i]);
		}
		hs.addAll(array);
		array.clear();
		array.addAll(hs);
		Collections.sort(array);
		var="";
		for (int j = 0; j < array.size(); j++) {
			var += array.get(j) + ","; 
		}
		return var;
    }
    
    public void infraccion(){
    	if(id_infra!=0){
    		id_hechos += id_infra + ",";
    	}
    	if(!c1.trim().equalsIgnoreCase("")) 
			camp1 += c1+",";
		if(!c2.trim().equalsIgnoreCase(""))
			camp2 += c2+",";
		if(!c3.trim().equalsIgnoreCase(""))
			camp3 += c3+","; 
		if(!c4.trim().equalsIgnoreCase(""))
			camp4 += c4+",";
		if(!c5.trim().equalsIgnoreCase(""))
			camp5 += c5+",";
		
		if(!c6.trim().equalsIgnoreCase("")) 
			camp6 += c6+",";
		if(!c7.trim().equalsIgnoreCase(""))
			camp7 += c7+",";
		if(!c8.trim().equalsIgnoreCase(""))
			camp8 += c8+","; 
		if(!c9.trim().equalsIgnoreCase(""))
			camp9 += c9+",";
		if(!c0.trim().equalsIgnoreCase(""))
			camp0 += c0+",";
		
		if(!c11.trim().equalsIgnoreCase(""))
			camp11 += c11+",";
		if(!c12.trim().equalsIgnoreCase(""))
			camp12 += c12+","; 
		if(!c13.trim().equalsIgnoreCase(""))
			camp13 += c13+",";
		if(!c14.trim().equalsIgnoreCase(""))
			camp14 += c14+",";
		
		if(!c15.trim().equalsIgnoreCase(""))
			camp15 += c15+",";
		if(!c16.trim().equalsIgnoreCase(""))
			camp16 += c16+","; 
		if(!c17.trim().equalsIgnoreCase(""))
			camp17 += c17+",";
		if(!c18.trim().equalsIgnoreCase(""))
			camp18 += c18+",";
		if(!c19.trim().equalsIgnoreCase(""))
			camp19 += c19+",";
		if(!c20.trim().equalsIgnoreCase(""))
			camp20 += c20+",";
		
		System.out.println("CAMPOS " + c1 + " " + c2 + " " + c3 + " " + c4 + " "  + c5 + c6 + " " + c7 + " " + c8 + " " + c9 + " "  + c0 + " " + c11 + " " + c12 + " " + c13 + " "  + c14);
		
    }
    
    public void listar(){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
	    	Cursor  c = db.rawQuery("SELECT * FROM C_inspector WHERE id_c_direccion = '" + id + "' AND nombre <> '" + us + "' AND nombre <> 'administrador' and trim(vigente) = 'S' order by nombre", null);
	    	//Cursor c = db.query("Inspectores", null, "id_inspector", null, null, null, null);
	    	id_i2.clear();
			id_i3.clear();
			id_i4.clear();
			id_i5.clear();
			id_i6.clear();
	    	arregloLista.add("");
	    	if(c.moveToFirst()){
	    		do{
	    			arregloLista.add(c.getString(1));
	    			id_i2.add(c.getInt(0));
	    			id_i3.add(c.getInt(0));
	    			id_i4.add(c.getInt(0));
	    			id_i5.add(c.getInt(0));
	    			id_i6.add(c.getInt(0));
	    			Log.i("listadoinspa", "nombre: " + c.getString(1));
	    		}while(c.moveToNext());
	    	}
	    	else{
	    		//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
	    	}
	    	c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void listarInf() {
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	Cursor c;
    	try{
    		if(id != 2) {
    			Log.i("consulta", "select * from C_infraccion where id_c_direccion = '" + id + "' and trim(vigente) = 'S'  order by infraccion");
    			c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' and trim(vigente) = 'S'  order by infraccion", null);
    			//c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' order by infraccion", null);
    		}
    		else {
    			Log.i("consulta", "select * from C_infraccion where id_c_direccion = '" + id + "' and trim(vigente) = 'S' order by id_c_infraccion");
    			c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' and trim(vigente) = 'S' order by id_c_infraccion", null);
    			//c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' order by infraccion", null);
    		}
    		System.out.println("select * from C_infraccion where id_c_direccion = '" + id + "'" + /* and vigente = 'S'*/ " order by id_c_infraccion");
    		
    		System.out.println(c.getCount() + " inf");
    		//Cursor c = db.query("C_infraccion", null, "id_c_infraccion", null, null, null, "infraccion");
    	//Cursor c = db.query("infracciones", null, "id_infraccion", null, null, null, null);
	    	if(c.moveToFirst()){
	    		arregloInfraccion.add("");
	    		do{
	    			//System.out.println(c.getString(2) + " " + c.getString(c.getColumnIndex("id_c_direccion")));
	    			//if(c.getString(c.getColumnIndex("vigente")).trim().equalsIgnoreCase("S")) {
		    			id_hecho.add(c.getInt(0));
		    			arregloInfraccion.add(c.getString(2));
		    			//System.out.println(c.getString(2) + " do " + c.getString(c.getColumnIndex("id_c_direccion")));
		    			//Log.i("listado", "Infraccion: " + c.getString(2));
	    			//}
	    		}while(c.moveToNext());
	    	}
	    	else{
	    		Toast toast = Toast.makeText(this, "No hay infracciones en la bd", Toast.LENGTH_SHORT);
	    		toast.setGravity(0, 0, 15);
				toast.show();
	    	}
	    	c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}
    	finally {
    		db.close();
    	}
    	
    }
    
    public void buscarIdInspector(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i1.add(c.getInt(0));
					ifeI = c.getString(2);
    				noI = c.getString(c.getColumnIndex("no_empleado"));
    				vigI = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    
    public void buscarIdInspector1(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i3.add(c.getInt(0));
					ifeA1 = c.getString(2);
    				noA1 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA1 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    public void buscarIdInspector2(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i4.add(c.getInt(0));
					ifeA2 = c.getString(2);
    				noA2 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA2 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    public void buscarIdInspector3(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i5.add(c.getInt(0));
					ifeA3 = c.getString(2);
    				noA3 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA3 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    
    public void buscarIdInspector5(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i6.add(c.getInt(0));
					ifeA4 = c.getString(2);
    				noA4 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA4 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    
    public void buscarInspector(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "'", null);
    		System.out.println("lalalala SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and (trim(vigente) = 'S' or trim(vigente) = 's') lalalalalala");
    		if(c.moveToFirst()){
    			do{
    				ifeI = c.getString(2);
    				noI = c.getString(c.getColumnIndex("no_empleado"));
    				vigI = c.getString(c.getColumnIndex("vigencia"));
    				Log.i("inspector", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    			c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    	}catch (SQLiteException e) {
			Log.i("ERROR FATAL", e.getMessage());
		}
    	finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				ifeA = c.getString(2);
    				noA = c.getString(c.getColumnIndex("no_empleado"));
    				vigA = c.getString(c.getColumnIndex("vigencia"));
    				Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante1(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				ifeA1 = c.getString(2);
    				noA1 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA1 = c.getString(c.getColumnIndex("vigencia"));
    				Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante2(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				ifeA2 = c.getString(2);
    				noA2 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA2 = c.getString(c.getColumnIndex("vigencia"));
    				Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante3(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				ifeA3 = c.getString(2);
    				noA3 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA3 = c.getString(c.getColumnIndex("vigencia"));
    				Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante4(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				ifeA4 = c.getString(2);
    				noA4 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA4 = c.getString(c.getColumnIndex("vigencia"));
    				Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    
    public void buscarInfraccion(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	String cam = "";
    	
    	try{
    	Cursor c = db.rawQuery("SELECT * FROM C_infraccion WHERE infraccion = '" + nom + "'", null);
    	if(c.moveToFirst()){
    		do{
    			id_infra = c.getInt(0);
    			unidad = c.getString(3);
    			
    			for (int i = 0; i < campo.size(); i++) {
    				if (!campo.get(i).equalsIgnoreCase("")) {
    					System.out.println(c.getColumnIndex(campo.get(i)));
        				if (c.getColumnIndex(campo.get(i)) >= 0) {
        					System.err.println(c.getString(c.getColumnIndex(campo.get(i))));
        					if (i==0) {
        						c1 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==1) {
        						c2 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==2) {
        						c3 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==3) {
        						c4 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==4) {
        						cam = campo.get(i);
        						c5 = c.getString(c.getColumnIndex(campo.get(i)));
        					}
        					else if (i==5) {
        						c6 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==6) {
        						c7 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==7) {
        						c8 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==8) {
        						c9 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==9) {
        						c0 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==10) {
        						c11 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==11) {
        						c12 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==12) {
        						c13 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					
        					else if (i==13) {
        						c14 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==14){
        						c15 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==15){
        						cam = c.getString(c.getColumnIndex(campo.get(i)));
        						c16 = c.getString(c.getColumnIndex(campo.get(i)));
        					}
        					else if (i==16){
        						c17 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==17){
        						c18 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else if (i==18){
        						c19 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        					else{
        						c20 = c.getString(c.getColumnIndex(campo.get(i)));
        						if(!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
        							cam = campo.get(i);
        					}
        				}

    				}
    			}
    			Log.i("codigo", "c1 " + c1  + " c2 " + c2 + " c3 " + c3 + " c4 " + c4 + " c5 " + c5 + "c6 " + c6  + " c7 " + c7 + " c8 " + c8 + " c9 " + c9 + " c0 " + c0  + " c11 " + c11 + " c12 " + c12 + " c13 " + c13 + " c14 " + c14 + " c15 " + c15 + " c16 " + c16 + " c17 " + c17  + " c18 " + c18 + " c19 " + c19 + " c20 " + c20);
    			Log.i("Info", "cod: " + c.getString(4) + " ord: " + c.getString(6) + " lap: " + c.getString(7) + " ordenamiento_ " + c.getString(8) + " n " + c.getString(9) + " l " + c.getString(10));
    		}while(c.moveToNext());
    		c.close();
    		if(id == 12) {
	    		medidas(cam);
	    		adapter.notifyDataSetChanged();
    		}
    		if(id == 2) {
	    		medidas(cam);
	    		adapter.notifyDataSetChanged();
    		}
    		String sql = "select competencia,ordenamiento from c_ordenamiento where campo = '" + cam + "' and id_c_direccion = " + id;
    		System.err.println(sql);
    		c = db.rawQuery(sql, null);
    		if(c.moveToFirst()) {
    			do {
					System.err.println(c.getString(0) + " " + c.getString(1));
					competencias = c.getString(0) + " " + c.getString(1);
				} while (c.moveToNext());
    		}
    	}
    	else{
    		Toast toast = Toast.makeText(this, "No hay infracciones en la bd", Toast.LENGTH_SHORT);
    		toast.setGravity(0, 0, 15);
			toast.show();
    	}
    }catch (SQLiteException e) {
		Log.i("ERROR FATAL", e.getMessage());
	}
    	db.close();
    	
    }
    
    public void buscarNombreCampo() {
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    	Cursor c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE id_c_direccion = " + id, null);
    	Log.i("que", "SELECT * FROM C_Ordenamiento WHERE id_c_direccion = " + id);
    	if(c.moveToFirst()){
    		Log.i("no", "no");
    		campo.clear();
    		do{
    			campo.add(c.getString(2));
    			Log.i("campo ", c.getString(2));
    		}while(c.moveToNext());
    	}
    	c.close();
    }catch (SQLiteException e) {
		Log.i("ERROR FATAL", e.getMessage());
	}
    	finally{
    		db.close();
    	}
    }
    public void buscarOrdenamientos(){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try {
	    	for (int i = 0; i < campo.size(); i++) {
				
	    		try{
			    	Cursor c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) + "' and id_c_direccion = '" + id + "'", null);
			    	Log.i("quer", "SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) + "' and id_c_direccion = '" + id + "'");
			    	Log.i("i", i + "");
			    	if(c.moveToFirst()){
			    		do{
			    			if (i == 0)
			    				campo1 = c.getString(1);
			    			else if (i ==1 ) 
			    				campo2 = c.getString(1);
			    			else if (i == 2)
			    				campo3 = c.getString(1);
			    			else if(i == 3)
			    				campo4 = c.getString(1);
			    			else if(i == 4)
			    				campo5 = c.getString(1);
			    			else if (i == 5) 
			    				campo6 = c.getString(1);
			    			else if (i == 6)
			    				campo7 = c.getString(1);
			    			else if(i == 7)
			    				campo8 = c.getString(1);
			    			else if(i == 8)
			    				campo9 = c.getString(1);
			    			else if(i == 9)
			    				campo0 = c.getString(1);
			    			else if (i == 10)
			    				campo11 = c.getString(1);
			    			else if(i == 11)
			    				campo12 = c.getString(1);
			    			else if(i == 12)
			    				campo13 = c.getString(1);
			    			else if(i == 13)
			    				campo14 = c.getString(1);
			    			
			    			else if(i == 14)
			    				campo15 = c.getString(1);
			    			else if(i == 15)
			    				campo16 = c.getString(1);
			    			else if (i == 16)
			    				campo17 = c.getString(1);
			    			else if(i == 17)
			    				campo18 = c.getString(1);
			    			else if(i == 18)
			    				campo19 = c.getString(1);
			    			else if(i == 19)
			    				campo20 = c.getString(1);
			    			
			    			System.out.println(campo5);
			    		}while(c.moveToNext());
			    	}
			    	c.close();
			    }catch (SQLiteException e) {
			    	Log.i("ERROR FATAL", e.getMessage());
			    }
	    	}
    	}catch (Exception e) {
			Log.e("error", e.getMessage());
		}finally {
			db.close();
		}
    	
    }
    
    public int consultarLevantamientoID(){ 
    	int idLevanta = 0;
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		if(db != null){
			String sentencia = "select * from  Acta ORDER BY id_acta DESC LIMIT 0,1";
			Cursor c = db.rawQuery(sentencia, null);
			try {
				if(c.moveToFirst()){
					do {
						idLevanta = c.getInt(0);
					} while (c.moveToNext());
				}
			} catch (Exception e) {
				Log.e("ERROR FATAL", e.getMessage());
			}
			finally{
				db.close();
				c.close();
			}
		}
		return idLevanta;
	}
    
    public void asignarActa(){ 
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		if(db != null){
			String sentencia = "select * from  Acta where id_c_direccion = '" + id + "' and id_c_inspector = '" + id_inspector1 + "' and numero <> '0' and numero_acta like '%" + ante + "%' ORDER BY id_acta";
			Log.i("Sentencia asignar", sentencia);
			Cursor c = db.rawQuery(sentencia, null);
			try {
				if(c.moveToFirst()){
					do {
						s = c.getString(1);
						numero = c.getInt(6)+"";
						Log.i("NUMERO", numero + " n2");
					} while (c.moveToNext());
				}
			} catch (Exception e) {
				Log.e("ERROR FATAL", e.getMessage());
			}
			finally{
				db.close();
				c.close();
			}
		}
	}
    
    public int consultarActa(){
    	int numero = 0; 
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		if(db != null){
			String Sentencia = "select * from  Acta where id_c_direccion = '" + id + "' and id_c_inspector = '" + id_inspector1 + "' and numero_acta like '%" + ante + "%'";
			Log.i("Sentencia acta", Sentencia);
			Cursor c = db.rawQuery(Sentencia, null);
			try {
				if(c.moveToFirst()){
					numero  = c.getCount();
					Log.i("NUMERO", numero + " n1");
				}
			} catch (Exception e) {
				
			}
			finally{
				c.close();
				db.close();
			}
		}
		return numero;
	}
    
    public void listarZona(int id_c){
		GestionBD gestinarBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
		SQLiteDatabase db = gestinarBD.getReadableDatabase();
		try {
			if(db != null){
				//Cursor c = db.query("C_zonas", null, "id_c_zonas", null, null, null, null);
				Cursor c = db.rawQuery("SELECT * FROM C_zonas WHERE id_c_direccion = '" + id_c + "'", null);
				if(c.moveToFirst()){
					do{
						zona.add(c.getString(2) + "     " + c.getString(3));
					}while(c.moveToNext());
				}
				c.close();
			}
		} catch (Exception e) {
			
		}
		finally{
			db.close();
		}
		
		
	}
    
    private void updateDisplay() {
        etFecham.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("/")
                    .append(mMonth + 1).append("/")
                    .append(mYear).append(" "));
    }
    
    private void updateDisplay1() {
    	m+=1;
        etfecha.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append(di).append("/")
                    .append(m).append("/")
                    .append(a).append(" "));
    }
    
 // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                	
                	mYear = year;
                	mMonth = monthOfYear;
                	mDay = dayOfMonth;
                	
                }
            };

            @Override
            protected Dialog onCreateDialog(int id) {
                switch (id) {
                case DATE_DIALOG_ID:
                	Log.i("Fecha", "dia: " + mDay + " mes: " + mMonth + " ano: " + mYear);
                    return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                }
                return null;
            }
            
	@Override
	public void onClick(View v) {
		
		name = "";
		name = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
		File folder = new File(name);
		folder.mkdirs();
		String nom;
		
		switch (v.getId()) {
		case R.id.btnBuscar:
			con = 0;
			mostrarResutados(etBuscar.getText().toString());
			break;
		case R.id.btnFecha:
			showDialog(DATE_DIALOG_ID);
			//updateDisplay1();
			updateDisplay();
			break;
		case R.id.btnBorden:
			//System.out.println("hola");
			if(!TextUtils.isEmpty(etOrden1.getText().toString())) {
				lev = buscarLevantameinto("numero_acta = '" + etOrden1.getText().toString().toUpperCase() + "'");
				mostrarCampos();
			}
			else {
				Toast toast = Toast.makeText(getApplicationContext(), "Ingrese la orden de visita a buscar", Toast.LENGTH_LONG);
				toast.setGravity(0, 0, 15);
				toast.show();
			}
			break;
			
		case R.id.radioOrdenV:
			inicio();
			infrac = 2;
			if(id == 1 || id == 2 || id == 3 || id == 12 || id == 13)
				rlProp.setVisibility(View.VISIBLE);
			else
				rlProp.setVisibility(View.GONE);
			rlTestA.setVisibility(View.GONE);
			rlVisita.setVisibility(View.GONE);
			llNota.setVisibility(View.GONE);
			llplazo.setVisibility(View.GONE);
			llreincidencia.setVisibility(View.GONE);
			ante = "OV";
			formato = "orden";
			etGiro.setVisibility(View.GONE);
			
			etMotivo.setVisibility(View.VISIBLE);
			tvMotivo.setVisibility(View.VISIBLE);
			spReglamento.setVisibility(View.GONE);
			tvReg.setVisibility(View.VISIBLE);
			tvActa.setText("Número Orden");
			
			radioInfraccion.setSelected(true);
			
			btnConsultar.setVisibility(View.VISIBLE);
			radioInfraccion.setVisibility(View.GONE);
			rborden.setVisibility(View.GONE);
			
			break;
			
		case R.id.radioInfraccion:
			inicio();
			infrac = 1;
			etDiaPlazo.setText("20");
			etDiaPlazo.setEnabled(false);
			rlProp.setVisibility(View.VISIBLE);
			rlTestA.setVisibility(View.VISIBLE);
			rlVisita.setVisibility(View.VISIBLE);
			llNota.setVisibility(View.GONE);
			llplazo.setVisibility(View.VISIBLE);
			llreincidencia.setVisibility(View.GONE);
			ante = "IN";
			formato = "infraccion";
			InfraccionesActivity.this.btnOrden1.setVisibility(View.VISIBLE);
			etOrden1.setVisibility(View.VISIBLE);
			etGiro.setVisibility(View.GONE);
			etMotivo.setVisibility(View.GONE);
			tvMotivo.setVisibility(View.GONE);
			//spReglamento.setVisibility(View.VISIBLE);
			//spReglamento.setVisibility(View.GONE);
			//tvReg.setVisibility(View.GONE);
			competencias = "";
			regla = "";
			idComp = 0;
			etOrden1.setText("");
			tvActa.setText("Número Acta");
			
			btnConsultar.setVisibility(View.VISIBLE);
			radioInfraccion.setVisibility(View.GONE);
			rborden.setVisibility(View.GONE);
			
			spNombreA.setVisibility(View.GONE);
			spNombreA1.setVisibility(View.GONE);
			spNombreA2.setVisibility(View.GONE);
			spNombreA3.setVisibility(View.GONE);
			spNombreA4.setVisibility(View.GONE);
			
			tvAcomp.setVisibility(View.GONE);
			
			break;
			
		case R.id.btnVista:
			
			imprimirPrevia(formato);
			
			File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
			
			if(file.exists()) {
				Intent i = new Intent(Intent.ACTION_VIEW);
		    	i.setPackage("com.dynamixsoftware.printershare");
		    	i.setDataAndType(Uri.fromFile(file), "application/pdf");
		    	startActivity(i);
			}
			break;
			
		case R.id.btnVer1:
			nom = etNumeroActa.getText().toString()+"-1.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			//new DialogoPersonalizado();
			break;
		case R.id.btnVer2:
			nom = etNumeroActa.getText().toString()+"-2.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
		case R.id.btnVer3:
			nom = etNumeroActa.getText().toString()+"-3.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
		case R.id.btnVer4:
			nom = etNumeroActa.getText().toString()+"-4.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer5:
			nom = etNumeroActa.getText().toString()+"-5.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer6:
			nom = etNumeroActa.getText().toString()+"-6.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer7:
			nom = etNumeroActa.getText().toString()+"-7.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer8:
			nom = etNumeroActa.getText().toString()+"-8.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer9:
			nom = etNumeroActa.getText().toString()+"-9.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer10:
			nom = etNumeroActa.getText().toString()+"-10.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer11:
			nom = etNumeroActa.getText().toString()+"-11.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer12:
			nom = etNumeroActa.getText().toString()+"-12.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer13:
			nom = etNumeroActa.getText().toString()+"-13.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer14:
			nom = etNumeroActa.getText().toString()+"-14.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer15:
			nom = etNumeroActa.getText().toString()+"-15.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnImprimirResum:
			mBixolonPrinter = new BixolonPrinter(this, mHandler, null);
			mBixolonPrinter.findBluetoothPrinters();
			printSampleGDL();
			break;

		default:
			break;
		}
	}
	
	public void mostrarImagen(String foto){
		
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(InfraccionesActivity.this);
		dialog.setTitle("Imagen");
		ImageView iv = new ImageView(getApplicationContext());
		
		Bitmap bm = BitmapFactory.decodeFile(foto);
		iv.setImageBitmap(bm);
		
		dialog.setView(iv);
		dialog.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();

		
	}
	
	/*public class DialogoPersonalizado extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View popupWindow;
	    popupWindow = inflater.inflate(R.layout.mostrar_foto, null);
	 
	    builder.setView(popupWindow).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
	           
	    	public void onClick(DialogInterface dialog, int id) {
	                  dialog.cancel();
	    	}
	    	
	    });
	 
	    return builder.create();
	    }
	}*/
	
	private void selectValue(Spinner spinner, String val) {
	    for (int i = 0; i < spinner.getCount(); i++) {
	        if (spinner.getItemAtPosition(i).toString().equals(val.trim())) {
	            spinner.setSelection(i);
	            break;
	        }
	    }
	}
	
	public void mostrarCampos() {
		if(!lev.isEmpty()) {
			resov = true;
			
			etCalle.setText(lev.get(0).getCalle());
			etNumero.setText(lev.get(0).getNumero_ext());
			etNuemroInterior.setText(lev.get(0).getNumero_int());
			etPropietario.setText(lev.get(0).getNombre_razon());
			etApellidoM.setText(lev.get(0).getApellidom_prop());
			etApellidoP.setText(lev.get(0).getApellidop_prop());
			etFraccionamiento.setText(lev.get(0).getFraccionamiento());
			numeroOV = lev.get(0).getNumeroActa();
			fechaOV = lev.get(0).getFecha();
			idComp = lev.get(0).getIdComp();
			etEntreC.setText(lev.get(0).getEntre_calle1());
			etEntreC1.setText(lev.get(0).getEntre_calle2());
			etResponsable.setText(lev.get(0).getResponsable_obra());
			etRegistro.setText(lev.get(0).getRegistro_responsable());
			ident = lev.get(0).getIdentifica();
			zon = lev.get(0).getZona();
			etNombreV.setText(lev.get(0).getNombre_visitado());
			
			etResponsable.setText(lev.get(0).getResponsable_obra());
			etReferencia.setText(lev.get(0).getReferencia());
			etRegistro.setText(lev.get(0).getRegistro_responsable());
			etCondominio.setText(lev.get(0).getCondominio());
			etConstruccion.setText(lev.get(0).getL_construccion());
			
			String [] cam;
			
			System.err.println(lev.get(0).getSe_identifica());
			
			cam = lev.get(0).getSe_identifica().split(":");
			
			selectValue(spIdentifica, cam[0]);
			
			etVIdentifica.setText(cam[1]);
			
			etNombreComercial.setText(lev.get(0).getNombre_comercial());
			etSector.setText(lev.get(0).getSector());
			
			//System.err.println(lev.get(0).getNumeroCitatorio());
			
			String cita[] = lev.get(0).getNumeroCitatorio().split("-");
			
			for (int i = 0; i < cita.length; i++) {
				System.err.println(cita[i]);
			}
			
			if(cita.length > 0)
				etCitatorio.setText(cita[1]);
			
			etInspccionFue.setText(lev.get(0).getPeticion());
			etMotivo.setText(lev.get(0).getMotivo_orden());
			etMedida.setText(lev.get(0).getMedida_seguridad());
			etArticulo.setText(lev.get(0).getArticulo_medida());
			etVManifiesta.setText(lev.get(0).getManifiesta_ser());
			
			//id_inspector1 = lev.get(0).getId_c_inspector1();
			id_inspector2 = lev.get(0).getId_c_inspector2();
			id_inspector3 = lev.get(0).getId_c_inspector3();
			id_inspector4 = lev.get(0).getId_c_inspector4();
			id_inspector5 = lev.get(0).getId_c_inspector5();
			id_inspector6 = lev.get(0).getId_c_inspector6();
			
			System.out.println(lev.get(0).getV_firma());
			
			if(lev.get(0).getV_firma() != null) {
				firma = lev.get(0).getV_firma();
				if(lev.get(0).getV_firma().equalsIgnoreCase("si"))
					cbFirma.setChecked(true);
				else
					cbFirma.setChecked(false);
			} else {
				cbFirma.setChecked(false);
				firma = "No";
			}
			
			etLGiro.setText(lev.get(0).getLicencia_giro());
			etAGiro.setText(lev.get(0).getActividad_giro());
			etAlicencia.setText(lev.get(0).getAxo_licencia() + "");
			
			System.err.println((id_inspector3 > 0) + " INSPECTOR");
			
			ArrayList<String> id2 = new ArrayList<String>();
			ArrayList<String> id3 = new ArrayList<String>();
			ArrayList<String> id4 = new ArrayList<String>();
			ArrayList<String> id5 = new ArrayList<String>();
			ArrayList<String> id6 = new ArrayList<String>();
			
			
			
			/*if(id_inspector2 > 0) {
				spNombreA.setEnabled(false);
				id2.add(consultarUsuario1(id_inspector2));
				spNombreA.setAdapter(null);
				spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id2));
			}
			
			
			if(id_inspector3 > 0) {
				spNombreA1.setEnabled(false);
				id3.add(consultarUsuario2(id_inspector3));
				spNombreA1.setAdapter(null);
				spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id3));
			} 
			
			
			if(id_inspector4 > 0) {
				spNombreA2.setEnabled(false);
				id4.add(consultarUsuario3(id_inspector4));
				spNombreA2.setAdapter(null);
				spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id4));
			} 
			
			
			if(id_inspector5 > 0) {
				spNombreA3.setEnabled(false);
				id5.add(consultarUsuario4(id_inspector5));
				spNombreA3.setAdapter(null);
				spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id5));
			} 
			
			
			if(id_inspector6 > 0) {
				spNombreA4.setEnabled(false);
				id6.add(consultarUsuario5(id_inspector6));
				spNombreA4.setAdapter(null);
				spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id6));
			}*/
			
			System.out.println(lev.get(0).getEntre_calle2() + " " + lev.get(0).getEntre_calle1());
			
			System.err.println(idComp + " PP");
			mostrarCompetencia(idComp);
			
			System.err.println(competencias + " COM " + regla);
			
		} else {
			resov = false;
			etCalle.setText("");
			etNumero.setText("");
			etNuemroInterior.setText("");
			etPropietario.setText("");
			etApellidoM.setText("");
			etApellidoP.setText("");
			numeroOV = "";
			fechaOV = "";
			idComp = 0;
			etEntreC.setText("");
			etEntreC1.setText("");
			etResponsable.setText("");
			etRegistro.setText("");
			ident = "";
			zon = "";
			
			
			etInspccionFue.setText("");
			etMotivo.setText("");
			etMedida.setText("");
			etArticulo.setText("");
			etVManifiesta.setText("");
			
			etResponsable.setText("");
			etReferencia.setText("");
			etRegistro.setText("");
			etCondominio.setText("");
			etConstruccion.setText("");
			etVIdentifica.setText("");
			
			etNombreComercial.setText("");
			etSector.setText("");
			
			cbFirma.setChecked(false);
			
			spNombreA.setEnabled(true);
			spNombreA.setAdapter(null);
			spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
			
			
			spNombreA1.setEnabled(true);
			spNombreA1.setAdapter(null);
			spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista));
		 
			spNombreA2.setEnabled(true);
			spNombreA2.setAdapter(null);
			spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista)); 
			
			spNombreA3.setEnabled(true);
			spNombreA3.setAdapter(null);
			spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista));
			
			
			spNombreA4.setEnabled(true);
			spNombreA4.setAdapter(null);
			spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista));
			
			
			Toast toast = Toast.makeText(getApplicationContext(), "No se encontro orden de visita", Toast.LENGTH_LONG);
			toast.setGravity(0, 0, 15);
			toast.show();
		}
	}
	
	public void mostrarCompetencia(int idCompetencia) {
		
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	
    	if(db != null) {
    		Cursor cursor = db.rawQuery("select * from c_ordenamiento where id_c_ordenamiento = '" + idCompetencia +"'", null);
    		if(cursor.moveToFirst()) {
    			do {
					competencias = cursor.getString(cursor.getColumnIndex("competencia"));
					regla = cursor.getString(cursor.getColumnIndex("ordenamiento"));
				} while (cursor.moveToNext());
    		}
    		cursor.close();
    	}
    	db.close();
		
	}
	
	public List<Levantamiento> buscarLevantameinto(String condicion){
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	
    	if(TextUtils.isEmpty(condicion)) {
			condicion = "1=1";
		}
		List<Levantamiento> levantamiento = new ArrayList<Levantamiento>();
		String sql = "select * from Levantamiento where " + condicion;
		System.out.println(sql + " 1");
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(db != null) {
			System.out.println(sql + " 2");
			if(cursor.moveToFirst()) {
				System.out.println(sql + " 3");
				do {
					System.out.println(sql + " 4");
					Levantamiento levantamientos = cursorToLevantamiento(cursor);
					levantamiento.add(levantamientos);
				} while (cursor.moveToNext());
			}
		}
		return levantamiento;
	}
	
	public List<MedidaSeguridad> buscarMedida(String condicion){
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	
    	if(TextUtils.isEmpty(condicion)) {
			condicion = "1=1";
		}
		List<MedidaSeguridad> medida = new ArrayList<MedidaSeguridad>();
		String sql = "select * from c_medida_seguridad where " + condicion;
		System.out.println(sql + " 1");
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(db != null) {
			System.out.println(sql + " 2");
			if(cursor.moveToFirst()) {
				System.out.println(sql + " 3");
				medida.add(new MedidaSeguridad());
				do {
					System.out.println(sql + " 4");
					MedidaSeguridad medidas = cursorToMedida(cursor);
					medida.add(medidas);
				} while (cursor.moveToNext());
			}
		}
		return medida;
	}
	
	public MedidaSeguridad cursorToMedida(Cursor cursor) {
		return new MedidaSeguridad(cursor.getString(cursor.getColumnIndex("medida_seguridad")), cursor.getString(cursor.getColumnIndex("articulos")));
	}
	
	public Levantamiento cursorToLevantamiento(Cursor cursor) {
		//numero_citatorio TEXT,infraccion int,tipo_acta text,fecha numeric,hora_inicio time,longitud float,latitud float,orden_vista int,fecha_orden_v numeric,Zona text,nombre_visitado text,se_identifica text,manifiesta_ser text,fraccionamiento text,calle text,numero_ext text,numero_int text,apellidop_prop text,apellidom_prop text,nombre_razon text,nombre_testigo1 text,ife_testigo1 text,designado_por1 text,nombre_testigo2 text,ife_testigo2 text,designado_por2 text,uso_catalogo text,hechos text,infracciones text,id_c_infraccion text,uso_suelo text,densidad text,manifiesta text,gravedad int,dias_plazo int,fecha_plazo numeric,hora_termino time,tipo_visita TEXT,id_pago int,pago numeric, fecha_pago numeric, estatus text,condominio TEXT,manzana TEXT,lote TEXT,capturo text,fecha_atiende_juez numeric, fecha_cancelacion numeric,fecha_efectua_multa numeric, vigencia_multa int, fecha_vigencia numeric,multa text,observaciones text, referencia TEXT
		System.out.println(cursor.getString(cursor.getColumnIndex("licencia_giro")));
		System.out.println(cursor.getString(cursor.getColumnIndex("actividad_giro")));
		return new Levantamiento(cursor.getInt(cursor.getColumnIndex("id_c_direccion")), cursor.getInt(cursor.getColumnIndex("id_c_inspector1")), cursor.getInt(cursor.getColumnIndex("id_c_inspector2")), cursor.getString(cursor.getColumnIndex("numero_acta")), cursor.getString(cursor.getColumnIndex("nombre_visitado")), cursor.getString(cursor.getColumnIndex("se_identifica")), cursor.getString(cursor.getColumnIndex("manifiesta_ser")), cursor.getString(cursor.getColumnIndex("fraccionamiento")), cursor.getString(cursor.getColumnIndex("calle")), cursor.getString(cursor.getColumnIndex("numero_ext")), cursor.getString(cursor.getColumnIndex("numero_int")), cursor.getString(cursor.getColumnIndex("nombre_razon")), cursor.getString(cursor.getColumnIndex("apellidop_prop")), cursor.getString(cursor.getColumnIndex("apellidom_prop")), cursor.getInt(cursor.getColumnIndex("id_c_competencia")),cursor.getString(cursor.getColumnIndex("fecha")),cursor.getString(cursor.getColumnIndex("entre_calle1")),cursor.getString(cursor.getColumnIndex("entre_calle2")),cursor.getString(cursor.getColumnIndex("responsable_obra")),cursor.getString(cursor.getColumnIndex("registro_responsable")),cursor.getString(cursor.getColumnIndex("identifica")),cursor.getString(cursor.getColumnIndex("peticion")),cursor.getString(cursor.getColumnIndex("v_firma")),cursor.getString(cursor.getColumnIndex("motivo_orden")),cursor.getString(cursor.getColumnIndex("medida_seguridad")),cursor.getString(cursor.getColumnIndex("articulo_medida")),cursor.getInt(cursor.getColumnIndex("id_c_inspector3")),cursor.getInt(cursor.getColumnIndex("id_c_inspector4")),cursor.getInt(cursor.getColumnIndex("id_c_inspector5")),cursor.getInt(cursor.getColumnIndex("id_c_inspector6")),cursor.getString(cursor.getColumnIndex("Zona")),cursor.getString(cursor.getColumnIndex("referencia")),cursor.getString(cursor.getColumnIndex("l_construccion")),cursor.getString(cursor.getColumnIndex("condominio")),cursor.getString(cursor.getColumnIndex("numero_citatorio")),cursor.getString(cursor.getColumnIndex("licencia_giro")),cursor.getString(cursor.getColumnIndex("actividad_giro")),cursor.getInt(cursor.getColumnIndex("axo_licencia")),cursor.getString(cursor.getColumnIndex("nombre_comercial")),cursor.getString(cursor.getColumnIndex("sector")));
	}
	
	public void validarFec(){
		
	}
	
	public long insertarActa(String numero_acta, int id_c_direccion, int id_c_inspectores, String fecha, int infraccion, String numero){
		long n = 0;
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
    	
    	if(db != null) {
    		try {
				ContentValues cv = new ContentValues();
				cv.put("numero_acta", numero_acta);
				cv.put("id_c_direccion", id_c_direccion);
				cv.put("id_c_inspector", id_c_inspectores);
				cv.put("fecha", fecha);
				cv.put("infraccion", infraccion);
				cv.put("numero", numero);
				
				n = db.insert("Acta", null, cv);
			} catch (Exception e) {
				Log.e("Error in Acta", e.getMessage());
			}
    		finally{
    			db.close();
    		}
    	}
    	
		return n;
	}
	
	public long ingresar(String numero_acta, String numero_citatorio, int infraccion, String tipo_acta, int id_c_direccion, String fechas, String hora_inicio,
			double longitud, double latitud, String orden_vista, String fecha_orden, String zona, int id_c_inspector1, int id_c_inspector2
			,String nombre_visitado, String se_identifica, String manifiesta_ser, String fraccionamiento, String calle,
			String numero_ext, String numrero_int, String apellidop_prop, String apellidom_prop, String nprop_razonsocial,
			String nombre_testigo1, String ife_testigo1, String designado_por1, String nombre_testigo2, String ife_testigo2, 
			String designado_por2, String usoC,String hechos, String infracciones, String id_c_infracciones, String uso_suelo, String densidad,
			String manifiesta, int gravedad, int dias_plazo, String fecha_plazo, String hora_termino,String condominio, String lote, String manzana, String referencia, String correo, String alineamiento, String construccion,int competencia,
			String entre_calle1,String entre_calle2,String responsable_obra,String registro_responsable,String status,
			String identifica,String peticion,String v_firma,String motivo_orden,String medida_seguridad,String articulo_medida,
			int id_c_inspector3,int id_c_inspector4,int id_c_inspector5,int id_c_inspector6,int id_competencia1,int id_competencia2,
			int id_competencia3,int id_competencia4,int id_competencia5,String licencia_giro,String actividad_giro,int axo_licencia,
			String nombre_comercial,String sector,String ne,String reincidencia){
		
		
		
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
		
		long n = 0;
		
		if(db!=null){
			
			try{
		
				ContentValues cv = new ContentValues();
				cv.put("numero_acta", numero_acta);
				cv.put("numero_citatorio", numero_citatorio);
				cv.put("infraccion", infraccion);
				cv.put("tipo_acta", tipo_acta);
				cv.put("id_c_direccion", id_c_direccion);
				cv.put("fecha", fechas);
				cv.put("hora_inicio", hora_inicio);
				cv.put("longitud", longitud);
				cv.put("latitud", latitud);
				cv.put("orden_vista",orden_vista);
				cv.put("fecha_orden_v", fecha_orden);
				cv.put("Zona", zona);
				cv.put("id_c_inspector1", id_c_inspector1);
				cv.put("id_c_inspector2", id_c_inspector2);
				cv.put("nombre_visitado", nombre_visitado);
				cv.put("se_identifica", se_identifica);
				cv.put("manifiesta_ser", manifiesta_ser);
				cv.put("fraccionamiento", fraccionamiento);
				cv.put("calle", calle);
				cv.put("numero_ext", numero_ext);
				cv.put("numero_int", numrero_int);
				cv.put("apellidop_prop",apellidop_prop);
				cv.put("apellidom_prop", apellidom_prop);
				cv.put("nombre_razon", nprop_razonsocial);
				cv.put("nombre_testigo1", nombre_testigo1);
				cv.put("ife_testigo1", ife_testigo1);
				cv.put("designado_por1", designado_por1);
				cv.put("nombre_testigo2", nombre_testigo2);
				cv.put("ife_testigo2",ife_testigo2);
				cv.put("designado_por2", designado_por2);
				cv.put("uso_catalogo", usoC);
				cv.put("hechos", hechos);
				cv.put("infracciones", infracciones);
				cv.put("id_c_infraccion", id_c_infracciones);
				cv.put("uso_suelo", uso_suelo);
				cv.put("densidad", densidad);
				cv.put("manifiesta", manifiesta);
				cv.put("gravedad", gravedad);
				cv.put("dias_plazo", dias_plazo);
				cv.put("fecha_plazo", fecha_plazo);
				cv.put("hora_termino", hora_termino);
				cv.put("id_pago", 0);
				cv.put("pago", 0);
				cv.put("fecha_pago", "");
				cv.put("estatus", "POR CALIFICAR");
				cv.put("capturo", "");
				cv.put("fecha_atiende_juez", "");
				cv.put("fecha_cancelacion", "");
				cv.put("fecha_efectua_multa", "");
				cv.put("vigencia_multa", 0);
				cv.put("fecha_vigencia", "");
				cv.put("multa", "");
				cv.put("observaciones", "");
				cv.put("condominio", condominio);
				cv.put("lote", lote);
				cv.put("manzana", manzana);
				cv.put("referencia", referencia);
				cv.put("correo", correo);
				cv.put("l_alineamiento", alineamiento);
				cv.put("l_construccion", construccion);
				cv.put("id_c_competencia", competencia);
				
				cv.put("entre_calle1", entre_calle1);
				cv.put("entre_calle2", entre_calle2);
				cv.put("responsable_obra", responsable_obra);
				cv.put("registro_responsable", registro_responsable);
				
				cv.put("status", status);
				
				cv.put("identifica", identifica);
				
				cv.put("peticion", peticion);
				cv.put("v_firma", v_firma);
				cv.put("motivo_orden", motivo_orden);
				cv.put("medida_seguridad", medida_seguridad);
				cv.put("articulo_medida", articulo_medida);
				
				cv.put("id_c_inspector3", id_c_inspector3);
				cv.put("id_c_inspector4", id_c_inspector4);
				cv.put("id_c_inspector5", id_c_inspector5);
				cv.put("id_c_inspector6", id_c_inspector6);
				
				cv.put("id_c_competencia1", id_competencia1);
				cv.put("id_c_competencia2", id_competencia2);
				cv.put("id_c_competencia3", id_competencia3);
				cv.put("id_c_competencia4", id_competencia4);
				cv.put("id_c_competencia5", id_competencia5);
				
				cv.put("licencia_giro",licencia_giro);
				cv.put("actividad_giro",actividad_giro);
				cv.put("axo_licencia",axo_licencia);
				
				cv.put("nombre_comercial",nombre_comercial);
				cv.put("sector",sector);
		
				n = db.insert("Levantamiento", null, cv);				
			}catch (SQLiteException e) {
				Log.e("Insert", e.getMessage());
			}
			
		}
		db.close();
		return n;
	}
	
	public void showMsg(String message) {  
	       Toast toast = Toast.makeText(getApplicationContext(), message, (Toast.LENGTH_LONG));
	       toast.setGravity(0, 0, 15);
	       toast.show();   
	}

	private void setCurrentLocation(Location loc) {
    	currentLocation = loc;
    }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		this.finish();
	}
	
	@Override
	public void run() {
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		try{
			if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			
				Looper.prepare();
			
				mLocationListener = new MyLocationListener();

				try {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                }catch(SecurityException e) {

                }
				Looper.loop(); 
				Looper.myLooper().quit(); 
			
			} else {
				Looper.prepare();
				Toast toast = Toast.makeText(InfraccionesActivity.this,"SeÔøΩal GPS no encontrada", Toast.LENGTH_LONG);
				toast.setGravity(0, 0, 15);
				toast.show();
				builAlert();
			}
		}catch (Exception e) {
			Log.e("gps", e.getMessage());
		}
	}
	
	public void builAlert() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(InfraccionesActivity.this);
		alert.setMessage("El GPS  esta deshabilitado. Entrar a configuraciÔøΩn")
		.setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),0);
				onDestroy();
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog al = alert.create();
		al.show();
		
	}
	
	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mLocationManager.removeUpdates(mLocationListener);
	    	if (currentLocation!=null) {
	    		//Toast.makeText(getBaseContext(), "Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
	    		Log.i("Coordenadas", "Latitude: " + currentLocation.getLatitude() * 1E6 + " Longitude: " + (int)currentLocation.getLongitude() * 1E6);
	    		latitud = currentLocation.getLatitude();
	    		longitud = currentLocation.getLongitude();
	    		etLatitud.setText(Double.toString(latitud));
	    		etLongitud.setText(Double.toString(longitud));
	    	}
		}
	};
	
	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			if (location != null) {
                Toast toast =Toast.makeText(getBaseContext(), "SeÔøΩal GPS encontrada", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
				toast.show();
                setCurrentLocation(location);
                handler.sendEmptyMessage(0);
            }
		}

		public void onProviderDisabled(String provider) {
			
		}

		public void onProviderEnabled(String provider) {
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
        
    }
	
	public boolean salir() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(InfraccionesActivity.this);
		dialog.setTitle("¿Esta seguro de salir?");
		dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog.setMessage("¿Esta seguro de salir del llenado de la infracción?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				res = true;
				InfraccionesActivity.this.onDestroy();
				if(thread != null)
					handler.sendEmptyMessage(0);
				
				if(!consu)
					almacenarFoto(etNumeroActa.getText().toString());
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();
		return res;
	}
	
	public void almacenarFoto(String numeroActa) {
		boolean d1 = false,d2 = false,d3 = false,d4 = false;
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		if(db != null) {
			Cursor c = db.rawQuery("Select * from Fotografia where numero_acta = '" + numeroActa + "'", null);
			try {
				if(c.moveToFirst())
				do {
					ContentValues cv = new ContentValues();
					
					if(etDFoto.getVisibility() == View.VISIBLE & !d1) {
						cv.put("descripcion", etDFoto.getText().toString());
						d1 = true;
					}
					else if(etDFoto1.getVisibility() == View.VISIBLE & !d2) {
						cv.put("descripcion", etDFoto1.getText().toString());
						d2 = true;
					}
					else if(etDFoto2.getVisibility() == View.VISIBLE & !d3) {
						cv.put("descripcion", etDFoto2.getText().toString());
						d3 = true;
					}
					else if(etDFoto3.getVisibility() == View.VISIBLE & !d4) {
						cv.put("descripcion", etDFoto3.getText().toString());
						d3 = true;
					}
					
					System.err.println(cv.get("descripcion"));
					
					db.update("Fotografia", cv, "id_fotografia = " + c.getInt(1), null);
				}while (c.moveToNext());
			}catch(SQLiteException e){
				Log.e("ERROR in foto", e.getMessage());
			}
			finally {
				db.close();
				c.close();
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			salir();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public ArrayList<String>  buscarNumeroActa() {
		this.numero_acta.clear();
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		if(db != null) {
			Cursor c = db.rawQuery("Select * from numero_acta", null);
			try {
				if(c.moveToFirst())
				do {
					this.numero_acta.add(c.getString(1));
				}while (c.moveToNext());
			}catch(SQLiteException e){
				Log.e("ERROR in num_act", e.getMessage());
			}
			finally {
				db.close();
				c.close();
			}
		}
		return this.numero_acta;
	}
	
	public boolean na() {
		boolean r  = false;
		for (int i = 0; i < numero_acta.size(); i++) {
			if (numero_acta.get(i).equalsIgnoreCase(etNumeroActa.getText().toString())) {
					r = true;
			}
		}
		return r;
	}
	
	public String ultimo() {
		String srt = "";
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		if (db != null) {
			Cursor c = db.query("numero_acta", null, "id_numero_acta", null, null, null, "id_numero_acta DESC LIMIT 0,1");
			try {
				if(c.moveToFirst()) {
					do {
						srt = c.getString(1);
					}while(c.moveToNext());
				}
			} catch (SQLiteException e) {
				Log.e("ERROR in ultimo", e.getMessage());
			}
		}
		return srt;
	}
	
	public long ingresarNumeroActa(String numero_acta, String inspector, String fecha) {
		long n = 0;
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
    	
    	if(db != null) {
    		try {
				ContentValues cv = new ContentValues();
				cv.put("numero_acta", numero_acta);
				cv.put("inspector", inspector);
				cv.put("fecha", fecha);
				
				n = db.insert("numero_acta", null, cv);
			} catch (Exception e) {
				Log.e("Error in numero_acta", e.getMessage());
			}
    		finally{
    			db.close();
    		}
    	}
    	
		return n;
	}
	
	public void imprimir() {
		int len;
		final int MITAD = 40;
		String src;
		String [] txt;
		String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_");
		File f = new File(path);
		f.mkdirs();
		
		try{
			
			File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
			//file.mkdirs();
			//File f = new File(path, etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
			
			
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			String  ext = file.getName().substring(file.getName().indexOf(".")+1);
			String tipo = mime.getMimeTypeFromExtension(ext);
			
			/*intent.setType(tipo);
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			//intent.setDataAndType(Uri.fromFile(file), tipo);
			startActivity(Intent.createChooser(intent, "Send"));
			//startActivity(intent);*/
			String [] na = etNumeroActa.getText().toString().split("/");
			Log.i("fecha", na[3] + "/" + na[4] + "/" + na[5]);
			fecha = na[3] + "/" + na[4] + "/" + na[5];
			String [] fechas = fecha.split("/");
			int dia, mes,a;
			String me;
			for (int i = 0; i < fechas.length; i++) {
				System.out.println(fechas[i]);
			}
			
			dia = Integer.parseInt(fechas[0]);
			mes = Integer.parseInt(fechas[1]);
			a = Integer.parseInt(fechas[2].substring(2, 4));
			System.out.println("entro");
			me = Justificar.mes(mes);
			
			OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file),"cp1252");
			os.write(".");
			os.write(Justificar.PY(4));
			os.write(Justificar.PX(58, Justificar.Conversion(etNumeroActa.getText().toString())));
			os.write(Justificar.PY(2));
			os.write(Justificar.PX(33, Justificar.Conversion(hora + " horas del " +  dia + " de " + me + " del aÔøΩo 20" + a)));
			os.write(Justificar.PY(1));
			//os.write("     " + arrayfecha[0]+ "\t\t" + mes + "\t\t" + arrayfecha[2] );
			String str = (etCondominio.getText().toString().trim().equalsIgnoreCase("")) ? etFraccionamiento.getText().toString() : etFraccionamiento.getText().toString() + ", Condominio "  + etCondominio.getText().toString();
			//txt = Justificar.justifocarTexto(Justificar.Conversion(HECH));
			txt = Justificar.justifocarTexto(Justificar.Conversion(str));
			
			if(txt.length != 1)
				txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),31);
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(Justificar.PX(31, Justificar.Conversion(txt[i]))));
				os.write(Justificar.PY(1));
			}
			
			txt = Justificar.justifocarTexto(Justificar.Conversion(etCalle.getText().toString() + " nÔøΩmero " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() ),24);
			
			if (id == 3 | id == 4) {
				for (int i = 0; i < txt.length; i++) {
					os.write(Justificar.PX(24, Justificar.Conversion(txt[i])));
					os.write(Justificar.PY(1));
				}
			
			}else{
				String dato = "";
				dato += " calle " + Justificar.Conversion(etCalle.getText().toString()); 
				if (!etNumero.getText().toString().equalsIgnoreCase("")) 
					dato = Justificar.Conversion("nÔøΩmero " + etNumero.getText().toString()) + " " + Justificar.Conversion(etNuemroInterior.getText().toString());
				if (!etLote.getText().toString().equalsIgnoreCase(""))
					dato += " lote " + Justificar.Conversion(etLote.getText().toString());
				if (!etManzana.getText().toString().equalsIgnoreCase(""))
					dato += " manzana " + Justificar.Conversion(etManzana.getText().toString());
				if(!etReferencia.getText().toString().equalsIgnoreCase(""))
					dato += " " + Justificar.Conversion(etReferencia.getText().toString());
				
				txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
				if(txt.length != 1)
					txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);
				for (int i = 0; i < txt.length; i++) {
					os.write(Justificar.PX(24, Justificar.Conversion(txt[i])));
					os.write(Justificar.PY(1));
				}
				
			}
			str = Justificar.Conversion(etApellidoP.getText().toString() + " " + etApellidoM.getText().toString() + " " + etPropietario.getText().toString());
			if (str.length() > 79) 
				str = str.substring(0, 79);
			txt = Justificar.justifocarTexto(str);
			System.err.println(txt.length + " completo " + str.length() + " caracteres");
			if(txt.length != 1) 
				txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(str)),20);
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.PX(20, Justificar.Conversion(txt[i])));
				os.write(Justificar.PY(1));
			}
			
			
			os.write(Justificar.PX(30, Justificar.Conversion(etNombreV.getText().toString())));
			//os.write("Nombre del Visitante");
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(18, Justificar.Conversion(etVIdentifica.getText().toString())));
			//os.write("Identifica");
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(15, Justificar.Conversion(spManifiesta .getSelectedItem().toString())));
			//os.write("Manifiesta ser");
			
			int l = 0,ln = 0,lno = 0,lt= 0,s = 0,sn = 0;
			if (/*id == 3 |*/ id == 4) {
				l = 1;
				ln = 26;
				lno = 24;
				lt = 51;
				s = 1; 
				sn = 0;
			}
			else { 
				l = 66;
				ln = 12;
				lno = 9;
				lt = 34;
				s = 0;
				sn = 1;
			}
			os.write(Justificar.PY((7+s)));
			if (!consu) {
				int orden;
				if(etNum.getText().toString().equalsIgnoreCase("")) {
					orden = 0;
				}else {
					orden = Integer.parseInt(etNum.getText().toString());
				}
				Formatter fmt = new Formatter();
				
				
				fmt.format("%04d", orden);
				//if(!String.valueOf(orden).equalsIgnoreCase("")){
				
					os.write(Justificar.PX(l, tvOV.getText().toString() + fmt));
				//}
				fmt.close();
			}
			else {
				os.write(Justificar.PX(l, orde));
			}
			os.write(Justificar.PY(sn));
			os.write(Justificar.PX(ln, Justificar.Conversion(spnombre.getSelectedItem().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(lno, Justificar.Conversion(etNoI.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(lt, Justificar.Conversion(tipoActa))); 
			os.write(Justificar.PY(4));
			//os.write(Justificar.Conversion(etSeleccion.getText().toString()));
			int salto = 0;
			
			txt = Justificar.justifocarTexto(etSeleccion.getText().toString());
			len = txt.length;
			src = etSeleccion.getText().toString().trim();
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			txt = Justificar.justifocarTexto(Justificar.Conversion(hech));
			len += txt.length;
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			//os.write(Justificar.Conversion(etInfraccion.getText().toString()));
			src = etInfraccion.getText().toString();
			txt = Justificar.justifocarTexto(Justificar.Conversion(etInfraccion.getText().toString()));
			len += txt.length;
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			
			
			txt = Justificar.justifocarTexto(Justificar.Conversion(DECLARA));
			len += txt.length;
			
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			src = etManifiesta.getText().toString();
			txt = Justificar.justifocarTexto(Justificar.Conversion(etManifiesta.getText().toString() + ", Identificadores " + id_hechos.replace(",", "|") + " gravedad " + spgravedad.getSelectedItem().toString()));
			len += txt.length;
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			salto = (13-len);
			System.out.println("salto " + salto);
			
			//os.write("MANIFFIESTACION .. .   ----> Aqui va uno de los textos grandes que hay qeu tener que justificar o alinearlos a la izquierda; aqui le escribo para haber el ejemplo de las impresiones; seguimos en contacto");
			os.write(Justificar.PY(7+salto));
			os.write(Justificar.PX(25, hr));
			
			//	FIRMAS
			os.write(Justificar.PY(3));
			os.write(Justificar.PX(1, Justificar.Conversion(spnombre.getSelectedItem().toString().trim())));
			len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etNombreV.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, Justificar.Conversion(etIfeI.getText().toString().trim())));
			len = MITAD - etIfeI.getText().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etVIdentifica.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, Justificar.Conversion("Inspector".trim())));
			len = MITAD - "Inspector".trim().length();
			os.write(Justificar.PX(len, "Visitado"));
			os.write(Justificar.PY(2));
			os.write(Justificar.PX(1, Justificar.Conversion(etNombreT.getText().toString().trim())));
			len = MITAD - etNombreT.getText().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etNombreT1.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, Justificar.Conversion(etIfeT.getText().toString().trim())));
			len = MITAD - etIfeT.getText().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etIfeT2.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, "Testigo".trim()));
			len = MITAD - "Testigo".trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion("Testigo")));
			os.flush();
			os.close();
		}catch (IOException e) {
			Log.e("Error al abrir", e.getMessage());
		}
	}
	
	
	
	public void imprimir(String formato) {
		
		int len =0;
		final int MITAD = 135;
		String src;
		String [] txt;
		String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_");
		File f = new File(path);
		f.mkdirs();
		
		Document doc = null;
		File file = null;
		FileOutputStream ficheroPdf = null;
		PdfWriter write = null;
		BaseFont bf = null;
		
		try {
			doc = new Document(PageSize.LEGAL);
		    file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
		    ficheroPdf = new FileOutputStream(file.getAbsoluteFile());
		    
		    write = PdfWriter.getInstance(doc, ficheroPdf);
		    
		    
		} catch (Exception e) {
			
		}
		
		
		String [] na = etNumeroActa.getText().toString().split("/");
		Log.i("fecha", na[3] + "/" + na[4] + "/" + na[5]);
		fecha = na[3] + "/" + na[4] + "/" + na[5];
		String [] fechas = fecha.split("/");
		int dia, mes,a;
		String me;
		for (int i = 0; i < fechas.length; i++) {
			System.out.println(fechas[i]);
		}
		
		dia = Integer.parseInt(fechas[0]);
		mes = Integer.parseInt(fechas[1]);
		String an = fechas[2];
		//a = Integer.parseInt(fechas[2].substring(2, 4));
		a = Integer.parseInt(fechas[2]);
		System.out.println("entro");
		me = Justificar.mes(mes);
		String medida = etMedida.getText().toString() + " del " + medidas1;
		
		System.err.println(medida);
		
		Font font1 = new Font(Font.HELVETICA,8,Color.BLACK);
	    
			if (formato.equalsIgnoreCase("infraccion")) {
					
					//construccion
					try {
						
						//File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
						
						
						MimeTypeMap mime = MimeTypeMap.getSingleton();
						String  ext = file.getName().substring(file.getName().indexOf(".")+1);
						String tipo = mime.getMimeTypeFromExtension(ext);
						
						
						Paragraph p;
						
						doc.open();
						
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
					    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.inspeccion);
					    bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
					    Image img;
					    
					    try {
					    	
					    	img = Image.getInstance(stream.toByteArray());
					        img.setAbsolutePosition(0, 0);
					        
					        float width = doc.getPageSize().getWidth();
					        float height = doc.getPageSize().getHeight();
					        
					        img.scaleToFit(width, height);
					        doc.add(img);
					        
						} catch (BadElementException e) {
							System.err.println(e.getMessage() + " BadElementException");
						} catch (MalformedURLException e) {
							System.err.println(e.getMessage() + " MalformedURLException");
						} catch (IOException e) {
							System.err.println(e.getMessage() + " IOException");
						} catch (DocumentException e) {
							System.err.println(e.getMessage() + " DocumentException");
						}
					    
					    doc.add(new Paragraph(" "));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.BOLD,21,Color.BLACK)));
					    
					    Font font = new Font(Font.BOLD,10,Color.BLACK);
					    
					    
					    PdfContentByte canvas = write.getDirectContent();
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 11);
				        canvas.moveText(30, 865);
				        canvas.showText(direccion);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 11);
				        canvas.moveText(200, 865);
				        canvas.showText(zon);
				        canvas.endText();
				        canvas.restoreState();
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 11);
				        canvas.moveText(450, 865);
				        canvas.showText(etNumeroActa.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(220, 845);
				        canvas.showText(hora);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(320, 845);
				        canvas.showText(dia + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 845);
				        canvas.showText(me.toUpperCase());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(510, 845);
				        canvas.showText(a + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 830);
				        canvas.showText(spnombre.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(360, 830);
				        canvas.showText(etNoI.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(220, 810);
				        canvas.showText(numeroOV);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(60, 800);
				        canvas.showText(fechaOV);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 790);
				        canvas.showText(etNombreV.getText().toString());
				        canvas.endText();
				        canvas.restoreState();

                        if(this.id == 2) {

                            String uso = "";

                            if(spuso.getSelectedItem().toString().contains("H1") | spuso.getSelectedItem().toString().contains("H2") | spuso.getSelectedItem().toString().contains("H3") | spuso.getSelectedItem().toString().contains("H4"))
                                uso = "HABITACIONAL " + spuso.getSelectedItem().toString();
                            else if (spuso.getSelectedItem().toString().contains("MB") | spuso.getSelectedItem().toString().contains("MD") | spuso.getSelectedItem().toString().contains("MC"))
                                uso = "MIXTAS " + spuso.getSelectedItem().toString();
                            else if(spuso.getSelectedItem().toString().contains("CSV") | spuso.getSelectedItem().toString().contains("CSB") | spuso.getSelectedItem().toString().contains("CSD") | spuso.getSelectedItem().toString().contains("CSC") | spuso.getSelectedItem().toString().contains("CSR") | spuso.getSelectedItem().toString().contains("SI"))
                                uso = "COMERCIAL Y SERVICIOS " + spuso.getSelectedItem().toString();
                            else
                                uso = "INDUSTRIAL " + spuso.getSelectedItem().toString();

                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(300, 780);
                            canvas.showText(uso);
                            canvas.endText();
                            canvas.restoreState();

                        }
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 770);
				        canvas.showText(etNumero.getText().toString()+" "+etNuemroInterior.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(255, 770);
				        canvas.showText(etCalle.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 760);
				        canvas.showText(etEntreC.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(300, 760);
				        canvas.showText(etEntreC1.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 750);
				        canvas.showText(etFraccionamiento.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        String vig = etVigI.getText().toString();
					    
					    String [] vig1 = vig.split("-");
					    
					    int d,m,ax;
					    
					    d=Integer.parseInt(vig1[2]);
					    m=Integer.parseInt(vig1[1]);
					    ax=Integer.parseInt(vig1[0]);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 705);
				        canvas.showText(getMes(m));
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(500, 705);
				        canvas.showText(String.valueOf(ax).substring(2, 4));
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(390, 680);
				        canvas.showText(etVIdentifica.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(490, 670);
				        canvas.showText(etVManifiesta.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(90, 640);
				        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(60, 605);
				        canvas.showText(etNombreT.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(340, 605);
				        canvas.showText(etNombreT1.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 595);
				        canvas.showText(spdesignado.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(430, 595);
				        canvas.showText(etIfeT.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(50, 585);
				        canvas.showText(etIfeT.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    txt = Justificar.justifocarTexto1(etSeleccion.getText().toString() + ". En atencion a " + spPeticion.getSelectedItem().toString() + " " + competencia, 110);
					    int li = 550;
					    
					    for (int i = 0; i < txt.length; i++) {
					    	
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, li);
					        canvas.showText(txt[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        li-=10;
						}
					    
					    txt = Justificar.justifocarTexto1(etInfraccion.getText().toString(), 110);
					    li = 455;
					    
					    for (int i = 0; i < txt.length; i++) {
					    	
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, li);
					        canvas.showText(txt[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        li-=10;
						}
					    
					    txt = Justificar.justifocarTexto1(etMedida.getText().toString(), 110);
					    li = 390;
					    
					    for (int i = 0; i < txt.length; i++) {
					    	
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, li);
					        canvas.showText(txt[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        li-=10;
						}
					    
					    txt = Justificar.justifocarTexto1(etArticulo.getText().toString(), 110);
					    li = 335;
					    
					    for (int i = 0; i < txt.length; i++) {
					    	
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, li);
					        canvas.showText(txt[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        li-=10;
						}
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(35, 280);
				        canvas.showText(etManifiesta.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(210, 230);
				        canvas.showText(hr + " ");
				        canvas.endText();
				        canvas.restoreState();
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(320, 230);
				        canvas.showText(dia + " de " + me.toUpperCase() + " del " + a);
				        canvas.endText();
				        canvas.restoreState();

				        if(spgravedad.getSelectedItem().toString().equalsIgnoreCase("1")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(75, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else if(spgravedad.getSelectedItem().toString().equalsIgnoreCase("2")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(80, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else if(spgravedad.getSelectedItem().toString().equalsIgnoreCase("3")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(85, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else if(spgravedad.getSelectedItem().toString().equalsIgnoreCase("4")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(90, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        }else  {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(95, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        }

                        if(spNE.getSelectedItem().toString().equalsIgnoreCase("1")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(220, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else if(spNE.getSelectedItem().toString().equalsIgnoreCase("2")) {
                            canvas.saveState();
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(225, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else if(spNE.getSelectedItem().toString().equalsIgnoreCase("3")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(230, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else if(spNE.getSelectedItem().toString().equalsIgnoreCase("4")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(235, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        }else  {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(240, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        }

                        if(swReincidencia.isChecked()) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(360, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        } else {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(365, 145);
                            canvas.showText("X");
                            canvas.endText();
                            canvas.restoreState();
                        }
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 10);
				        canvas.moveText(450, 120);
				        canvas.showText(etNumeroActa.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(110, 90);
				        canvas.showText(dia + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(150, 90);
				        canvas.showText(me.toUpperCase());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(260, 90);
				        canvas.showText(a + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        
					    
					    //p = new Paragraph(etNumeroActa.getText().toString(),font);
				        /*p = new Paragraph(" ",font);
					    p.setAlignment(Paragraph.ALIGN_RIGHT);
					    doc.add(p);
					    
					    p = new Paragraph("",new Font(Font.BOLD,4,Color.BLACK));
					    p.setAlignment(Paragraph.ALIGN_RIGHT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",new Font(Font.BOLD,4,Color.BLACK)));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 875);
				        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                      " + etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString(),font1);
					    p = new Paragraph(" ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    String dato = ""; 
					    dato += " " + etCalle.getText().toString() + " ";
					    
						if (!etNumero.getText().toString().equalsIgnoreCase("")) 
							dato += etNumero.getText().toString() + " " + Justificar.Conversion(etNuemroInterior.getText().toString());
						
						if (!etLote.getText().toString().equalsIgnoreCase(""))
							dato += " lote " + etLote.getText().toString();
						if (!etManzana.getText().toString().equalsIgnoreCase(""))
							dato += " manzana " + etManzana.getText().toString();
						if(!etReferencia.getText().toString().equalsIgnoreCase(""))
							dato += " " + etReferencia.getText().toString();
						txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
						if(txt.length != 1)
							txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);
						
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 865);
				        canvas.showText(dato);
				        canvas.endText();
				        canvas.restoreState();
				        
						//p = new Paragraph("                         " + dato,font1);
						p = new Paragraph("                         ",font1);
						doc.add(p);
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 855);
				        canvas.showText(etFraccionamiento.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
						
						//p = new Paragraph("                       " + etFraccionamiento.getText().toString(),font1);
				        p = new Paragraph("                       ",font1);
						doc.add(p);
						
						doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10,Color.BLACK)));
						
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(145, 825);
				        canvas.showText(hora);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(240, 825);
				        canvas.showText(dia + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 825);
				        canvas.showText(me.toUpperCase());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(544, 825);
				        canvas.showText(a + "");
				        canvas.endText();
				        canvas.restoreState();
						
						//p = new Paragraph("                                                           " + hora + "                                         " +  dia + "                                           " + me + "                                                                  " + a,font1);
				        p = new Paragraph("                                                           ",font1);
				        p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(120, 815);
				        canvas.showText(spnombre.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
					    
					    //p = new Paragraph("                                           " + spnombre.getSelectedItem().toString(),font1);
				        p = new Paragraph("                                           ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(80, 805);
				        canvas.showText(etNoI.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                     " + etNoI.getText().toString(),font1);
				        p = new Paragraph("                     " ,font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
						
					    doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(250, 780);
				        canvas.showText(numeroOV);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 780);
				        canvas.showText(fechaOV);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                                                                                                   " + numeroOV + "                                                  " + fechaOV,font1);
					    p = new Paragraph("                                                                                                   ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,15,Color.BLACK)));
					    
					    int count = 0;
					    
					    double lin;
					    
					    lin = competencias.length() + regla.length();
				    	
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/180);
				    	System.out.println(count + " COUNT");
				    	
					    doc.add(new Paragraph("" +competencias /*+ " " +regla*,font1));
					    
					    if(count <= 1)
					    	doc.add(new Paragraph(" ",font1));
					    
					    //doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(310, 695);
				        canvas.showText(spuso.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph("                                                                                                                                              " + spuso.getSelectedItem().toString(),font1));
				        doc.add(new Paragraph("                                                                                                                                              " ,font1));
					    
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 683);
				        canvas.showText(etNumero.getText().toString()+" "+etNuemroInterior.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(255, 683);
				        canvas.showText(etCalle.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
					    //p = new Paragraph(" " + etNumero.getText().toString()+" "+etNuemroInterior.getText().toString() + "                                                                                                         " + etCalle.getText().toString(),font1);
				        p = new Paragraph(" ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 670);
				        canvas.showText(etEntreC.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(320, 670);
				        canvas.showText(etEntreC1.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph(" " + etEntreC.getText().toString() + "                                                                                                                             " + etEntreC1.getText().toString() ,font1));
				        doc.add(new Paragraph(" ",font1));
					    //doc.add(new Paragraph(" ",font1));
					    
					    int caracter = 125;
					    int totalc = ("                   " + etFraccionamiento.getText().toString()).length();
					    caracter = caracter-totalc;
					    String espacio = "";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 660);
				        canvas.showText(etFraccionamiento.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(325, 660);
				        canvas.showText(zon);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                   " + etFraccionamiento.getText().toString() + espacio + zon,font1);
				        p = new Paragraph("                   ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    //doc.add(new Paragraph(" ",font1));
					    
					    caracter = 150;
					    totalc = ("  " + etNombreV.getText().toString()).length();
					    caracter = caracter-totalc;
					    espacio = " ";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 645);
				        canvas.showText(etNombreV.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 645);
				        canvas.showText(etVManifiesta.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("  " + etNombreV.getText().toString() + espacio + etVManifiesta.getText().toString(),font1);
				        p = new Paragraph("  ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    if(!etVIdentifica.getText().toString().equals("")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(120, 635);
					        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(420, 635);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    } else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(120, 635);
					        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(420, 635);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    if(spIdentifica.getSelectedItem().toString().equals("")) {
						    //p = new Paragraph("                                              " + etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString() + "                                                                                        " + "No",font1);
					    	p = new Paragraph("                                              ",font1);
						    p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    } else {
					    	//p = new Paragraph("                                              " + etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString() + "                                                                                       " + "Si" ,font1);
					    	p = new Paragraph("                                              "  ,font1);
					    	p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    }
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 625);
				        canvas.showText(etVIdentifica.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
				        p = new Paragraph("     ",new Font(Font.HELVETICA,7,Color.BLACK));
					    //p = new Paragraph("     " + etVIdentifica.getText().toString(),new Font(Font.HELVETICA,7,Color.BLACK));
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(387, 610);
				        canvas.showText(numeroOV);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(555, 610);
				        canvas.showText(firma);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                                                                                                                                                               " + numeroOV + "                                        " + firma ,font1);
				        p = new Paragraph("                                                                                                                                                               ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph("",font1));
					    doc.add(new Paragraph("",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
					    
					    
					    if(spdesignado1.getSelectedItem().toString().equalsIgnoreCase("visitado")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(150, 577);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    } else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(150, 577);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    
					    if(spdesignado1.getSelectedItem().toString().equalsIgnoreCase("visitado")) {
					    	//doc.add(new Paragraph("                                            Si",new Font(Font.HELVETICA,8,Color.BLACK)));
					    	doc.add(new Paragraph("                                            ",new Font(Font.HELVETICA,8,Color.BLACK)));
					    } else {
					    	//doc.add(new Paragraph("                                            No",new Font(Font.HELVETICA,8,Color.BLACK)));
					    	doc.add(new Paragraph("                                            ",new Font(Font.HELVETICA,8,Color.BLACK)));
					    }
					    
					    System.err.println((etNombreT.getText().toString() + "                                                                                                                                          " + "No").length() + " CANTIDAD");
					    
					    caracter = 160;
					    totalc = etNombreT.getText().toString().length();
					    caracter = 160-totalc;
					    espacio = "      ";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    
					    if(!etIfeT.getText().toString().equalsIgnoreCase("")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 567);
					        canvas.showText(etNombreT.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 567);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    }else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 567);
					        canvas.showText(etNombreT.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 567);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    
					    if(spIdentificaT.getSelectedItem().toString().equalsIgnoreCase("")) {
						    //p = new Paragraph("          " + etNombreT.getText().toString() + espacio + "   No",font1);
					    	p = new Paragraph("          ",font1);
						    p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    }else {
					    	//p = new Paragraph("          " + etNombreT.getText().toString() + espacio + "   Si",font1);
					    	p = new Paragraph("          " ,font1);
						    p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    }
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(40, 555);
				        canvas.showText(etIfeT.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("           " + etIfeT.getText().toString(),font1);
				        p = new Paragraph("           ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    //doc.add(new Paragraph(" ",font1));
					    
					    caracter = 160;
					    totalc = etNombreT1.getText().toString().toUpperCase().length();
					    caracter = 160-totalc;
					    espacio = "    ";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    
					    if(etIfeT2.getText().toString().equals("")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 545);
					        canvas.showText(etNombreT1.getText().toString().toUpperCase());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 545);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    } else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 545);
					        canvas.showText(etNombreT1.getText().toString().toUpperCase());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 545);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    
					    if(spIdentificaT1.getSelectedItem().toString().equals("")) {
						     //p = new Paragraph("          " + etNombreT1.getText().toString().toUpperCase() + espacio + "   No",font1);
					    	 p = new Paragraph("          ",font1);
						     p.setAlignment(Paragraph.ALIGN_LEFT);
						     doc.add(p);
					    } else {
					    	 //p = new Paragraph("          " + etNombreT1.getText().toString().toUpperCase() + espacio + "   Si",font1);
					    	p = new Paragraph("          ",font1);
							 p.setAlignment(Paragraph.ALIGN_LEFT);
							 doc.add(p);
					    }
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(40, 535);
				        canvas.showText( etIfeT2.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("           " + etIfeT2.getText().toString(),font1);
				        p = new Paragraph("           ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,14,Color.BLACK)));
					    doc.add(new Paragraph(" ",font1));
					    //doc.add(new Paragraph(" ",font1));
					    count = 0;
					    lin = ("                         " + etInfraccion.getText().toString()).length();
				    	
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/180);
				    	System.out.println(count + " COUNT 2 " + (count <= 1));
				    	
				    	p = new Paragraph("                         " + etInfraccion.getText().toString().toUpperCase(),font1);
						p.setAlignment(Paragraph.ALIGN_LEFT);
						doc.add(p);
						
				    	if(count <= 1) {
				    		if(count >= 2)
				    			doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    	}
					    
					  
					    
					    lin = ("                                    " + etSeleccion.getText().toString()).length();
				    	count = 0;
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/160);
				    	System.out.println(count);
				    	
				    	count = 5-count;
				    	p = new Paragraph("                                    " + etSeleccion.getText().toString(),font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
				    	for (int i = 0; i < count; i++) {
				    		doc.add(new Paragraph(" ",font1));
						}
				    	
				    	System.out.println(count);
					    
					    
					    
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
					    //doc.add(new Paragraph(" ",font1));
					    
					    lin = (" " + etMedida.getText().toString()).length();
				    	count = 0;
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/150);
				    	System.out.println(count);
				    	
				    	//String medida = etMedida.getText().toString().trim() + " del " +  spMedida.getSelectedItem().toString();
					    doc.add(new Paragraph(" " + etMedida.getText().toString().trim(),font1));
					    if(count <= 1)
					    	doc.add(new Paragraph(" ",font1));
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(200, 390);
				        canvas.showText(etArticulo.getText().toString() + " del " + spMedida.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph("                                                                               " + etArticulo.getText().toString(),font1));
				        doc.add(new Paragraph("                                                                               ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,12,Color.BLACK)));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(295, 345);
				        canvas.showText(etManifiesta.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    p = new Paragraph("                                                                                                                      " ,font1);
				        //p = new Paragraph("                                                                                                                      " + etManifiesta.getText().toString(),font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(35, 290);
				        canvas.showText(etResponsable.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph("       " + etResponsable.getText().toString() ,font1));
				        doc.add(new Paragraph("       "  ,font1));
				        
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 280);
				        canvas.showText(etRegistro.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph(" " + etRegistro.getText().toString(),new Font(Font.HELVETICA,8,Color.BLACK)));
				        doc.add(new Paragraph(" " ,new Font(Font.HELVETICA,8,Color.BLACK)));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,5,Color.BLACK)));
					    doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(450, 245);
				        canvas.showText(hr + " ");
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                                                                                                                                                                                         " + hr  ,font1);
				        p = new Paragraph("                                                                                                                                                                                         " ,font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 235);
				        canvas.showText(dia + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(200, 235);
				        canvas.showText(me.toUpperCase());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 235);
				        canvas.showText(a + "");
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph(" " + dia + "                                                       " + me + "                                                                         " + a,font1);
				        p = new Paragraph(" " ,font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 160);
				        canvas.showText(spnombre.getSelectedItem().toString().trim());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 160);
				        canvas.showText(etNombreV.getText().toString().trim());
				        canvas.endText();
				        canvas.restoreState();
				        
					   
					    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
				    	//p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString(),font1);
					    p = new Paragraph(" ",font1);
				    	//p.setFont(font1);
				    	doc.add(p);
				    	
						
				    	if(spIdentifica.getSelectedItem().toString().equalsIgnoreCase("")) {
							len = MITAD - "".length();/*etIfeI.getText().toString().trim().length();* 
					    	//p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len),font1);
					    	//p = new Paragraph(" " + mitad(len),font1);
							p = new Paragraph(" ",font1);
					    	//p.setFont(font1);
					    	doc.add(p);
				    	} else {
				    		len = MITAD - etIfeI.getText().toString().trim().length();
					    	//p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString(),font1);
				    		//p = new Paragraph(" " + etVIdentifica.getText().toString(),font1);
				    		p = new Paragraph(" ",font1);
					    	//p.setFont(font1);
					    	doc.add(p);
				    	}
						
						doc.add(new Paragraph("  ",font1));
						doc.add(new Paragraph("  ",font1));
						doc.add(new Paragraph("  ",new Font(Font.HELVETICA,7,Color.BLACK)));
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 100);
				        canvas.showText(etNombreT.getText().toString().trim());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 100);
				        canvas.showText(etNombreT1.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
						
						len = MITAD - etNombreT.getText().toString().trim().length();
				    	//p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString(),font1);
						p = new Paragraph(" ",font1);
				    	//p.setFont(font1);
				    	doc.add(p);
				    	
				    	
						
				    	if(spIdentificaT.getSelectedItem().toString().equalsIgnoreCase("")) {
				    		if(spIdentificaT1.getSelectedItem().toString().equalsIgnoreCase("")) {
								len = MITAD - etIfeT.getText().toString().trim().length();
						    	p = new Paragraph(" " /*+ etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString()*,font1);
						    	//p.setFont(font1);
						    	doc.add(p);
				    		}else {
				    			len = MITAD - "".length();
						    	//p = new Paragraph(" " + mitad(len) +"          " + etIfeT2.getText().toString(),font1);
				    			p = new Paragraph(" " ,font1);
						    	//p.setFont(font1);
						    	doc.add(p);
				    		}
				    	} else {
				    		if(spIdentificaT1.getSelectedItem().toString().equalsIgnoreCase("")) {
								len = MITAD - etIfeT.getText().toString().trim().length();
						    	//p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + "          " /*+ etIfeT2.getText().toString()*,font1);
								p = new Paragraph(" " ,font1);
						    	//p.setFont(font1);
						    	doc.add(p);
				    		}else {
				    			len = MITAD - etIfeT.getText().toString().length();
						    	//p = new Paragraph(" " + etIfeT.getText().toString() + mitad(len) + "          " + etIfeT2.getText().toString(),font1);
				    			p = new Paragraph(" ",font1);
						    	//p.setFont(font1);
						    	doc.add(p);
				    		}
				    	}*/
						
					    doc.close();
					
						
						
					}catch (DocumentException e) {
						System.err.println(e.getMessage() + " doc ");
					}catch (Exception e) {
						Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
						toast.setGravity(0, 0, 15);
						toast.show();
						Log.e("Error al abrir", e.getMessage() + " c ");
						System.err.println(e.getMessage() + " n ");
					}
		} else if(formato.equalsIgnoreCase("orden")) {
			//construccion
				try {
					
					//ambiental
					//File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
					
					int c = 10;
					
					MimeTypeMap mime = MimeTypeMap.getSingleton();
					String  ext = file.getName().substring(file.getName().indexOf(".")+1);
					String tipo = mime.getMimeTypeFromExtension(ext);
					System.err.println(tipo);
					
					
					Paragraph p;
					
					doc.open();
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
				    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ov);
				    bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
				    Image img;
				    
				    try {
				    	
				    	img = Image.getInstance(stream.toByteArray());
				        img.setAbsolutePosition(0, 0);
				        
				        float width = doc.getPageSize().getWidth();
				        float height = doc.getPageSize().getHeight();
				        
				        img.scaleToFit(width, height);
				        doc.add(img);
				        
					} catch (BadElementException e) {
						System.err.println(e.getMessage() + " BadElementException");
					} catch (MalformedURLException e) {
						System.err.println(e.getMessage() + " MalformedURLException");
					} catch (IOException e) {
						System.err.println(e.getMessage() + " IOException");
					} catch (DocumentException e) {
						System.err.println(e.getMessage() + " DocumentException");
					}
				    
				    PdfContentByte canvas = write.getDirectContent();
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(30, 855+c);
			        canvas.showText(this.direccion);
			        canvas.endText();
			        canvas.restoreState();
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 12);
			        canvas.moveText(450, 855+c);
			        canvas.showText(etNumeroActa.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10,Color.BLACK)));
				    
				    Font font = new Font(Font.BOLD,10,Color.BLACK);
				    
				    
				    p = new Paragraph("  " ,font);
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,3,Color.BLACK)));
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 815+c);
			        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
			        
			        p = new Paragraph("                    " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    String dato = ""; 
				    dato += " " + etCalle.getText().toString();
				    
					if (!etNumero.getText().toString().equalsIgnoreCase("")) 
						dato += " " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString();
					
					if (!etLote.getText().toString().equalsIgnoreCase(""))
						dato += " lote " + etLote.getText().toString();
					if (!etManzana.getText().toString().equalsIgnoreCase(""))
						dato += " manzana " + etManzana.getText().toString();
					if(!etReferencia.getText().toString().equalsIgnoreCase(""))
						dato += " " + etReferencia.getText().toString();
					txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
					if(txt.length != 1)
						txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);
					
					
					canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 795+c);
			        canvas.showText(dato);
			        canvas.endText();
			        canvas.restoreState();
			        
			        p = new Paragraph("                        ",new Font(Font.HELVETICA,7,Color.BLACK));
					doc.add(p);
					
					canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 780+c);
			        canvas.showText(etFraccionamiento.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
					
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
					doc.add(new Paragraph(" ",font1));
					
					int count = 0;
				    
				    double lin;
				    
				    lin = ("  " + competencias+ " " + regla).length();
			    	
			    	//System.err.println(porc);
			    	count += Math.ceil(lin/180);
			    	System.out.println(count);
			    	/*String c = "";
			    	for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
				    		if(i == 0)
				    			c += comp[i] + "\n";
				    		else
				    			c += ", " + comp[i];
			    		}
					}*/
			    	int l = 720;
			    	/*for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
			    			
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(20, l+c);
					        canvas.showText(comp[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        l -= 10; 
			    		}
			    	}*/
			    	
				    //doc.add(new Paragraph("  " + competencias + " " + regla,font1));
			        doc.add(new Paragraph("  ",font1));
				    
				    if(count <= 1)
				    	doc.add(new Paragraph(" ",font1));
					
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
				    doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
					//doc.add(new Paragraph(" ",font1));
					
					count = 0;
					lin = ("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString()).length();
				    count += Math.ceil(lin/120);
				    
				    System.out.println(count);
				    
				    String insp = spnombre.getSelectedItem().toString().trim();
				    String noInsp = etNoI.getText().toString().trim();
				    
				    if(!TextUtils.isEmpty(spNombreA.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA1.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA1.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA1.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA2.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA2.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA2.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA3.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA3.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA3.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA4.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA4.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA4.getText().toString().trim();
				    }
					
				    doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
				    //p = new Paragraph("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString(),font1);
				    p = new Paragraph("" + insp,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    if(count <= 1)
				    	doc.add(new Paragraph(" ",new Font(Font.HELVETICA,6,Color.BLACK)));
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    
				    
				    p = new Paragraph("                                                          " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    String vig = etVigI.getText().toString();
				    
				    String [] vig1 = vig.split("-");
				    
				    int d1,m,ax;
				    
				    d1=Integer.parseInt(vig1[2]);
				    m=Integer.parseInt(vig1[1]);
				    ax=Integer.parseInt(vig1[0]);
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(300, 460+c);
			        canvas.showText(getMes(m));
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(450, 460+c);
			        canvas.showText(String.valueOf(ax).substring(2, 4));
			        canvas.endText();
			        canvas.restoreState();
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(150, 450+c);
			        canvas.showText(noInsp);
			        canvas.endText();
			        canvas.restoreState();
			        
			        txt = Justificar.justifocarTexto1(etMotivo.getText().toString(), 150);
				    int li = 425+c;
				    
				    for (int i = 0; i < txt.length; i++) {
				    	
				    	canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(35, li);
				        canvas.showText(txt[i]);
				        canvas.endText();
				        canvas.restoreState();
				        
				        li-=10;
					}
				    
				    int d = 5;
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(220, 165);
			        canvas.showText(dia + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(300, 165);
			        canvas.showText(me.toUpperCase());
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(445, 165);
			        canvas.showText(String.valueOf(a).substring(2,4) + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 70);
			        canvas.showText(etNombreV.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(450, 70);
			        canvas.showText(hr);
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(60, 60);
			        canvas.showText(dia + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(150, 60);
			        canvas.showText(me.toUpperCase(Locale.getDefault()));
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(300, 60);
			        canvas.showText(a + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        if(cbFirma.isChecked()) {
			        	canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(145, 45);
				        canvas.showText("Si");
				        canvas.endText();
				        canvas.restoreState();
			        } else {
			        	canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(145, 45);
				        canvas.showText("No");
				        canvas.endText();
				        canvas.restoreState();
			        }
			        doc.close();	
				}catch (DocumentException e) {
					System.err.println(e.getMessage() + " doc ");
				}catch (Exception e) {
					Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
					Log.e("Error al abrir", e.getMessage() + " c ");
					System.err.println(e.getMessage() + " n ");
				}
		} else if(formato.equalsIgnoreCase("citatorio")) {
			
			Paragraph p;
			
			doc.open();
			
			
			if(id == 1) {
				try {
					
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					
				    p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("          " + String.valueOf(a).substring(0, 2) + "                                      " + spnombre.getSelectedItem().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    System.out.println("citatoriocitatorio");
				    
				    p = new Paragraph("                      " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() + "      ");
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                        " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                             " + etPropietario.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                   " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                            " + spManifiesta .getSelectedItem().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                  " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph("                                  " + hora);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etMotivo.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					
					doc.add(new Paragraph(" "));
				    p = new Paragraph("                                                  " + hora + "                     " + dia + "            " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph("  "));
					doc.add(new Paragraph("  "));
					doc.add(new Paragraph("  "));
					
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
					
				} catch(Exception e) {
					 System.out.println(e.getMessage() + " aqui");
				}
			} else {
				
				try {
					
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph("                                                                                 " + hora + "                   " + dia);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                      " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
					
				} catch (Exception e) {
					
				}
				
			}
			
		} else if(formato.equalsIgnoreCase("apercibimiento")) {
			
			Paragraph p;
			
			doc.open();
			
			try {
				
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
			    p = new Paragraph(etNumeroActa.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_RIGHT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                         " + spnombre.getSelectedItem().toString() + "                                                                       " + etNoI.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                                                      " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                      " + etCalle.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                                    " + etFraccionamiento.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                         " + etNombreV .getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                                    " + etVIdentifica.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + spManifiesta.getSelectedItem().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + etGiro.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                " + etNombreT.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + spnombre.getSelectedItem().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                               " + etIfeT2.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    
			    p = new Paragraph("   " + etSeleccion.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("   " + conti + " " + etInfraccion.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    p = new Paragraph("                                                                 20");
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    String fe [] = etfecha.getText().toString().split("/");
			    
			    p = new Paragraph("                                                       " + fe[0] + "                         " + getMes(Integer.parseInt(fe[1])));
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    
			    p = new Paragraph("          " + spnombre.getSelectedItem().toString().trim() + mitad(len) + " " + etNombreV.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    len = MITAD - etNombreT.getText().toString().trim().length();
			    
			    p = new Paragraph("          " + etNombreT.getText().toString().trim() + mitad(len) + " " + etNombreT1.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.close();
				
			} catch(DocumentException e) {
				
			}
			
		} else if(formato.equalsIgnoreCase("hechos")) {
			
			Paragraph p;
			
			doc.open();
		    
			if(id == 2) {
			
				try {
					
				
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                    " + hora + "           " +  dia);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("    " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("   "  + etNum.getText().toString() + "         " + etFecham.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("             " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    
				    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                        " + etManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                           " + hr);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph("  "));
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
				    
				} catch (Exception e) {
					
				}
			} else {
				
				try {
					
					
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                    " + hora + "           " +  dia );
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   " + getMes(mes) + "   "  +   String.valueOf(a).substring(0,2) );
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                  "  + etNum.getText().toString() + "            " + etFecham.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                    " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etPropietario.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etMotivo.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    
				    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                        " + etManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                           " + hr);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph("  "));
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
				    
				}catch (DocumentException e) {
					System.err.println(e.getMessage() + " doc ");
				}catch (Exception e) {
					Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
					Log.e("Error al abrir", e.getMessage() + " c ");
					System.err.println(e.getMessage() + " n ");
				}
				
			}
			
		}
		
	}
	
	public void imprimirPrevia(String formato) {
		
		int len =0;
		final int MITAD = 130;
		String src;
		String [] txt;
		String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_");
		File f = new File(path);
		f.mkdirs();
		
		Document doc = null;
		File file = null;
		FileOutputStream ficheroPdf = null;
		PdfWriter writer = null;
		BaseFont bf = null;
		
		try {
			doc = new Document(PageSize.LEGAL);
		    file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
		    ficheroPdf = new FileOutputStream(file.getAbsoluteFile());
		    
		    writer = PdfWriter.getInstance(doc, ficheroPdf);
		    
		    
		} catch (Exception e) {
			
		}
		
		
		String [] na = etNumeroActa.getText().toString().split("/");
		Log.i("fecha", na[3] + "/" + na[4] + "/" + na[5]);
		fecha = na[3] + "/" + na[4] + "/" + na[5];
		String [] fechas = fecha.split("/");
		int dia, mes,a;
		String me;
		for (int i = 0; i < fechas.length; i++) {
			System.out.println(fechas[i]);
		}
		
		dia = Integer.parseInt(fechas[0]);
		mes = Integer.parseInt(fechas[1]);
		String an = fechas[2];
		a = Integer.parseInt(fechas[2].substring(2, 4));
		//a = Integer.parseInt(fechas[2]);
		System.out.println("entro");
		me = Justificar.mes(mes);
		
		
	    
		
		Font font1 = new Font(Font.HELVETICA,8,Color.BLACK);
	    
			if (formato.equalsIgnoreCase("infraccion")) {
				
				//if(id == 1) {
					
					//construccion
					try {
						
						//File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
						
						
						MimeTypeMap mime = MimeTypeMap.getSingleton();
						String  ext = file.getName().substring(file.getName().indexOf(".")+1);
						String tipo = mime.getMimeTypeFromExtension(ext);
						
						
						Paragraph p;
						
						doc.open();
						
						
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
					    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.inspeccion);
					    bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
					    Image img;
					    
					    try {
					    	
					    	img = Image.getInstance(stream.toByteArray());
					        img.setAbsolutePosition(0, 0);
					        
					        float width = doc.getPageSize().getWidth();
					        float height = doc.getPageSize().getHeight();
					        
					        img.scaleToFit(width, height);
					        doc.add(img);
					        
						} catch (BadElementException e) {
							System.err.println(e.getMessage() + " BadElementException");
						} catch (MalformedURLException e) {
							System.err.println(e.getMessage() + " MalformedURLException");
						} catch (IOException e) {
							System.err.println(e.getMessage() + " IOException");
						} catch (DocumentException e) {
							System.err.println(e.getMessage() + " DocumentException");
						}
					    
					    Font fontm = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD, harmony.java.awt.Color.black);
					    ColumnText.showTextAligned(writer.getDirectContentUnder(),Element.ALIGN_CENTER,
					    new Paragraph( "Vista previa de prueba", fontm), 297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
						
					    
					    doc.add(new Paragraph(" "));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.BOLD,21,Color.BLACK)));
					    
					    Font font = new Font(Font.BOLD,10,Color.BLACK);
					    
					    
					    PdfContentByte canvas = writer.getDirectContent();
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 11);
				        canvas.moveText(480, 895);
				        canvas.showText(" "/*etNumeroActa.getText().toString()*/);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph(etNumeroActa.getText().toString(),font);
				        p = new Paragraph(" ",font);
					    p.setAlignment(Paragraph.ALIGN_RIGHT);
					    doc.add(p);
					    
					    p = new Paragraph("",new Font(Font.BOLD,4,Color.BLACK));
					    p.setAlignment(Paragraph.ALIGN_RIGHT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",new Font(Font.BOLD,4,Color.BLACK)));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 875);
				        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                      " + etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString(),font1);
					    p = new Paragraph(" ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    String dato = ""; 
					    dato += " " + etCalle.getText().toString() + " ";
					    
						if (!etNumero.getText().toString().equalsIgnoreCase("")) 
							dato += etNumero.getText().toString() + " " + Justificar.Conversion(etNuemroInterior.getText().toString());
						
						if (!etLote.getText().toString().equalsIgnoreCase(""))
							dato += " lote " + etLote.getText().toString();
						if (!etManzana.getText().toString().equalsIgnoreCase(""))
							dato += " manzana " + etManzana.getText().toString();
						if(!etReferencia.getText().toString().equalsIgnoreCase(""))
							dato += " " + etReferencia.getText().toString();
						txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
						if(txt.length != 1)
							txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);
						
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 865);
				        canvas.showText(dato);
				        canvas.endText();
				        canvas.restoreState();
				        
						//p = new Paragraph("                         " + dato,font1);
						p = new Paragraph("                         ",font1);
						doc.add(p);
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 855);
				        canvas.showText(etFraccionamiento.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
						
						//p = new Paragraph("                       " + etFraccionamiento.getText().toString(),font1);
				        p = new Paragraph("                       ",font1);
						doc.add(p);
						
						doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10,Color.BLACK)));
						
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(145, 825);
				        canvas.showText(hora);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(240, 825);
				        canvas.showText(dia + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 825);
				        canvas.showText(me.toUpperCase());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(544, 825);
				        canvas.showText(a + "");
				        canvas.endText();
				        canvas.restoreState();
						
						//p = new Paragraph("                                                           " + hora + "                                         " +  dia + "                                           " + me + "                                                                  " + a,font1);
				        p = new Paragraph("                                                           ",font1);
				        p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(120, 815);
				        canvas.showText(spnombre.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
					    
					    //p = new Paragraph("                                           " + spnombre.getSelectedItem().toString(),font1);
				        p = new Paragraph("                                           ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(80, 805);
				        canvas.showText(etNoI.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                     " + etNoI.getText().toString(),font1);
				        p = new Paragraph("                     " ,font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
						
					    doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(250, 780);
				        canvas.showText(numeroOV);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 780);
				        canvas.showText(fechaOV);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                                                                                                   " + numeroOV + "                                                  " + fechaOV,font1);
					    p = new Paragraph("                                                                                                   ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,15,Color.BLACK)));
					    
					    int count = 0;
					    
					    double lin;
					    
					    lin = competencias.length() + regla.length();
				    	
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/180);
				    	System.out.println(count + " COUNT");
				    	
					    doc.add(new Paragraph("" +competencias /*+ " " +regla*/,font1));
					    
					    if(count <= 1)
					    	doc.add(new Paragraph(" ",font1));
					    
					    //doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(310, 695);
				        canvas.showText(spuso.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph("                                                                                                                                              " + spuso.getSelectedItem().toString(),font1));
				        doc.add(new Paragraph("                                                                                                                                              " ,font1));
					    
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 683);
				        canvas.showText(etNumero.getText().toString()+" "+etNuemroInterior.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(255, 683);
				        canvas.showText(etCalle.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
					    //p = new Paragraph(" " + etNumero.getText().toString()+" "+etNuemroInterior.getText().toString() + "                                                                                                         " + etCalle.getText().toString(),font1);
				        p = new Paragraph(" ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 670);
				        canvas.showText(etEntreC.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(320, 670);
				        canvas.showText(etEntreC1.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph(" " + etEntreC.getText().toString() + "                                                                                                                             " + etEntreC1.getText().toString() ,font1));
				        doc.add(new Paragraph(" ",font1));
					    //doc.add(new Paragraph(" ",font1));
					    
					    int caracter = 125;
					    int totalc = ("                   " + etFraccionamiento.getText().toString()).length();
					    caracter = caracter-totalc;
					    String espacio = "";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(70, 660);
				        canvas.showText(etFraccionamiento.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(325, 660);
				        canvas.showText(zon);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                   " + etFraccionamiento.getText().toString() + espacio + zon,font1);
				        p = new Paragraph("                   ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    //doc.add(new Paragraph(" ",font1));
					    
					    caracter = 150;
					    totalc = ("  " + etNombreV.getText().toString()).length();
					    caracter = caracter-totalc;
					    espacio = " ";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 645);
				        canvas.showText(etNombreV.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(380, 645);
				        canvas.showText(etVManifiesta.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("  " + etNombreV.getText().toString() + espacio + etVManifiesta.getText().toString(),font1);
				        p = new Paragraph("  ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    if(!etVIdentifica.getText().toString().equals("")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(120, 635);
					        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(420, 635);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    } else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(120, 635);
					        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(420, 635);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    if(spIdentifica.getSelectedItem().toString().equals("")) {
						    //p = new Paragraph("                                              " + etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString() + "                                                                                        " + "No",font1);
					    	p = new Paragraph("                                              ",font1);
						    p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    } else {
					    	//p = new Paragraph("                                              " + etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString() + "                                                                                       " + "Si" ,font1);
					    	p = new Paragraph("                                              "  ,font1);
					    	p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    }
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 625);
				        canvas.showText(etVIdentifica.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
				        p = new Paragraph("     ",new Font(Font.HELVETICA,7,Color.BLACK));
					    //p = new Paragraph("     " + etVIdentifica.getText().toString(),new Font(Font.HELVETICA,7,Color.BLACK));
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(387, 610);
				        canvas.showText(numeroOV);
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(555, 610);
				        canvas.showText(firma);
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                                                                                                                                                               " + numeroOV + "                                        " + firma ,font1);
				        p = new Paragraph("                                                                                                                                                               ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph("",font1));
					    doc.add(new Paragraph("",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
					    
					    
					    if(spdesignado1.getSelectedItem().toString().equalsIgnoreCase("visitado")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(150, 577);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    } else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(150, 577);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    
					    if(spdesignado1.getSelectedItem().toString().equalsIgnoreCase("visitado")) {
					    	//doc.add(new Paragraph("                                            Si",new Font(Font.HELVETICA,8,Color.BLACK)));
					    	doc.add(new Paragraph("                                            ",new Font(Font.HELVETICA,8,Color.BLACK)));
					    } else {
					    	//doc.add(new Paragraph("                                            No",new Font(Font.HELVETICA,8,Color.BLACK)));
					    	doc.add(new Paragraph("                                            ",new Font(Font.HELVETICA,8,Color.BLACK)));
					    }
					    
					    System.err.println((etNombreT.getText().toString() + "                                                                                                                                          " + "No").length() + " CANTIDAD");
					    
					    caracter = 160;
					    totalc = etNombreT.getText().toString().length();
					    caracter = 160-totalc;
					    espacio = "      ";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    
					    if(!etIfeT.getText().toString().equalsIgnoreCase("")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 567);
					        canvas.showText(etNombreT.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 567);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    }else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 567);
					        canvas.showText(etNombreT.getText().toString());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 567);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    
					    if(spIdentificaT.getSelectedItem().toString().equalsIgnoreCase("")) {
						    //p = new Paragraph("          " + etNombreT.getText().toString() + espacio + "   No",font1);
					    	p = new Paragraph("          ",font1);
						    p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    }else {
					    	//p = new Paragraph("          " + etNombreT.getText().toString() + espacio + "   Si",font1);
					    	p = new Paragraph("          " ,font1);
						    p.setAlignment(Paragraph.ALIGN_LEFT);
						    doc.add(p);
					    }
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(40, 555);
				        canvas.showText(etIfeT.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("           " + etIfeT.getText().toString(),font1);
				        p = new Paragraph("           ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    //doc.add(new Paragraph(" ",font1));
					    
					    caracter = 160;
					    totalc = etNombreT1.getText().toString().toUpperCase().length();
					    caracter = 160-totalc;
					    espacio = "    ";
					    
					    for (int i = 0; i < caracter; i++) {
							espacio += " ";
						}
					    
					    
					    if(etIfeT2.getText().toString().equals("")) {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 545);
					        canvas.showText(etNombreT1.getText().toString().toUpperCase());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 545);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					    } else {
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(35, 545);
					        canvas.showText(etNombreT1.getText().toString().toUpperCase());
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(490, 545);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					    }
					    
					    
					    if(spIdentificaT1.getSelectedItem().toString().equals("")) {
						     //p = new Paragraph("          " + etNombreT1.getText().toString().toUpperCase() + espacio + "   No",font1);
					    	 p = new Paragraph("          ",font1);
						     p.setAlignment(Paragraph.ALIGN_LEFT);
						     doc.add(p);
					    } else {
					    	 //p = new Paragraph("          " + etNombreT1.getText().toString().toUpperCase() + espacio + "   Si",font1);
					    	p = new Paragraph("          ",font1);
							 p.setAlignment(Paragraph.ALIGN_LEFT);
							 doc.add(p);
					    }
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(40, 535);
				        canvas.showText( etIfeT2.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    p = new Paragraph("           ",font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,14,Color.BLACK)));
					    
					    count = 0;
					    lin = ("                         " + etInfraccion.getText().toString()).length();
				    	
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/180);
				    	System.out.println(count + " COUNT 2 " + (count <= 1));
				    	
				    	p = new Paragraph("                         " + etInfraccion.getText().toString().toUpperCase(),font1);
						p.setAlignment(Paragraph.ALIGN_LEFT);
						doc.add(p);
						
				    	if(count <= 1) {
				    		if(count >= 2)
				    			doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    	}
					    
					  
					    
					    lin = ("                                    " + etSeleccion.getText().toString()).length();
				    	count = 0;
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/160);
				    	System.out.println(count);
				    	
				    	count = 5-count;
				    	p = new Paragraph("                                    " + etSeleccion.getText().toString(),font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
				    	for (int i = 0; i < count; i++) {
				    		doc.add(new Paragraph(" ",font1));
						}
				    	
				    	System.out.println(count);
					    
					    
					    
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
					    //doc.add(new Paragraph(" ",font1));
					    
					    lin = (" " + etMedida.getText().toString()).length();
				    	count = 0;
				    	//System.err.println(porc);
				    	count += Math.ceil(lin/150);
				    	System.out.println(count);
				    	
				    	//String medida = etMedida.getText().toString().trim() + " del " +  spMedida.getSelectedItem().toString();
					    doc.add(new Paragraph(" " + etMedida.getText().toString().trim(),font1));
					    if(count <= 1)
					    	doc.add(new Paragraph(" ",font1));
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(200, 390);
				        canvas.showText(etArticulo.getText().toString() + " del " + spMedida.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph("                                                                               " + etArticulo.getText().toString(),font1));
				        doc.add(new Paragraph("                                                                               ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,12,Color.BLACK)));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(295, 345);
				        canvas.showText(etManifiesta.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    p = new Paragraph("                                                                                                                      " ,font1);
				        //p = new Paragraph("                                                                                                                      " + etManifiesta.getText().toString(),font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(35, 290);
				        canvas.showText(etResponsable.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph("       " + etResponsable.getText().toString() ,font1));
				        doc.add(new Paragraph("       "  ,font1));
				        
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 280);
				        canvas.showText(etRegistro.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
					    
					    //doc.add(new Paragraph(" " + etRegistro.getText().toString(),new Font(Font.HELVETICA,8,Color.BLACK)));
				        doc.add(new Paragraph(" " ,new Font(Font.HELVETICA,8,Color.BLACK)));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,5,Color.BLACK)));
					    doc.add(new Paragraph(" ",font1));
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(450, 245);
				        canvas.showText(hr + " ");
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph("                                                                                                                                                                                         " + hr  ,font1);
				        p = new Paragraph("                                                                                                                                                                                         " ,font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 235);
				        canvas.showText(dia + "");
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(200, 235);
				        canvas.showText(me.toUpperCase());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 235);
				        canvas.showText(a + "");
				        canvas.endText();
				        canvas.restoreState();
					    
					    //p = new Paragraph(" " + dia + "                                                       " + me + "                                                                         " + a,font1);
				        p = new Paragraph(" " ,font1);
					    p.setAlignment(Paragraph.ALIGN_LEFT);
					    doc.add(p);
					    
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
					    
					    
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 160);
				        canvas.showText(spnombre.getSelectedItem().toString().trim());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 160);
				        canvas.showText(etNombreV.getText().toString().trim());
				        canvas.endText();
				        canvas.restoreState();
				        
					   
					    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
				    	//p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString(),font1);
					    p = new Paragraph(" ",font1);
				    	//p.setFont(font1);
				    	doc.add(p);
				    	
						
				    	if(spIdentifica.getSelectedItem().toString().equalsIgnoreCase("")) {
							len = MITAD - "".length();/*etIfeI.getText().toString().trim().length();*/
					    	p = new Paragraph(" ",font1);
					    	doc.add(p);
				    	} else {
				    		len = MITAD - etIfeI.getText().toString().trim().length();
					    	p = new Paragraph(" ",font1);
					    	doc.add(p);
				    	}
						
						doc.add(new Paragraph("  ",font1));
						doc.add(new Paragraph("  ",font1));
						doc.add(new Paragraph("  ",new Font(Font.HELVETICA,7,Color.BLACK)));
						
						canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(20, 100);
				        canvas.showText(etNombreT.getText().toString().trim());
				        canvas.endText();
				        canvas.restoreState();
				        
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(400, 100);
				        canvas.showText(etNombreT1.getText().toString());
				        canvas.endText();
				        canvas.restoreState();
						
						len = MITAD - etNombreT.getText().toString().trim().length();
				    	//p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString(),font1);
						p = new Paragraph(" ",font1);
				    	//p.setFont(font1);
				    	doc.add(p);
				    	
				    	
						
				    	if(spIdentificaT.getSelectedItem().toString().equalsIgnoreCase("")) {
				    		if(spIdentificaT1.getSelectedItem().toString().equalsIgnoreCase("")) {
								len = MITAD - etIfeT.getText().toString().trim().length();
						    	p = new Paragraph(" " /*+ etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString()*/,font1);
						    	//p.setFont(font1);
						    	doc.add(p);
				    		}else {
				    			len = MITAD - "".length();
						    	//p = new Paragraph(" " + mitad(len) +"          " + etIfeT2.getText().toString(),font1);
				    			p = new Paragraph(" " ,font1);
						    	doc.add(p);
				    		}
				    	} else {
				    		if(spIdentificaT1.getSelectedItem().toString().equalsIgnoreCase("")) {
								len = MITAD - etIfeT.getText().toString().trim().length();
						    	p = new Paragraph(" " ,font1);
						    	doc.add(p);
				    		}else {
				    			len = MITAD - etIfeT.getText().toString().length();
						    	p = new Paragraph(" ",font1);
						    	doc.add(p);
				    		}
				    	}
						
					    doc.close();
					
						
						
					}catch (Exception e) {
						Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
						toast.setGravity(0, 0, 15);
						toast.show();
						Log.e("Error al abrir", e.getMessage() + " n ");
						System.err.println(e.getMessage() + " n ");
					}
		} else if(formato.equalsIgnoreCase("orden")) {
			//construccion
			
			if(id == 1) {
			
				try {
					
					//ambiental
					//File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
					
					
					//int c = 10;
					
					MimeTypeMap mime = MimeTypeMap.getSingleton();
					String  ext = file.getName().substring(file.getName().indexOf(".")+1);
					String tipo = mime.getMimeTypeFromExtension(ext);
					System.err.println(tipo);
					
					
					Paragraph p;
					
					doc.open();
					
					Font fontm = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD, harmony.java.awt.Color.black);
				    ColumnText.showTextAligned(writer.getDirectContentUnder(),Element.ALIGN_CENTER,
				    new Paragraph( "Vista previa de prueba", fontm), 297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
				    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ov);
				    bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
				    Image img;
				    
				    try {
				    	
				    	img = Image.getInstance(stream.toByteArray());
				        img.setAbsolutePosition(0, 0);
				        
				        float width = doc.getPageSize().getWidth();
				        float height = doc.getPageSize().getHeight();
				        
				        img.scaleToFit(width, height);
				        doc.add(img);
				        
					} catch (BadElementException e) {
						System.err.println(e.getMessage() + " BadElementException");
					} catch (MalformedURLException e) {
						System.err.println(e.getMessage() + " MalformedURLException");
					} catch (IOException e) {
						System.err.println(e.getMessage() + " IOException");
					} catch (DocumentException e) {
						System.err.println(e.getMessage() + " DocumentException");
					}
				    
				    PdfContentByte canvas = writer.getDirectContent();
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 11);
			        canvas.moveText(480, 895);
			        canvas.showText(" "/*etNumeroActa.getText().toString()*/);
			        canvas.endText();
			        canvas.restoreState();
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10,Color.BLACK)));
				    
				    Font font = new Font(Font.BOLD,10,Color.BLACK);
				   
				    p = new Paragraph("  " ,font);
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,3,Color.BLACK)));
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(70, 870);
			        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    p = new Paragraph("                    " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    String dato = ""; 
				    dato += " " + etCalle.getText().toString();
				    
					if (!etNumero.getText().toString().equalsIgnoreCase("")) 
						dato += " " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString();
					
					if (!etLote.getText().toString().equalsIgnoreCase(""))
						dato += " lote " + etLote.getText().toString();
					if (!etManzana.getText().toString().equalsIgnoreCase(""))
						dato += " manzana " + etManzana.getText().toString();
					if(!etReferencia.getText().toString().equalsIgnoreCase(""))
						dato += " " + etReferencia.getText().toString();
					txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
					if(txt.length != 1)
						txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);
					
					
					canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(70, 860);
			        canvas.showText(dato);
			        canvas.endText();
			        canvas.restoreState();
			        
			        p = new Paragraph("                        ",new Font(Font.HELVETICA,7,Color.BLACK));
					doc.add(p);
					
					canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(70, 850);
			        canvas.showText(etFraccionamiento.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
					
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
					doc.add(new Paragraph(" ",font1));
					
					int count = 0;
				    
				    double lin;
				    
				    lin = ("  " + competencias+ " " + regla).length();
			    	
			    	//System.err.println(porc);
			    	count += Math.ceil(lin/180);
			    	System.out.println(count);
			    	/*String c = "";
			    	for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
				    		if(i == 0)
				    			c += comp[i] + "\n";
				    		else
				    			c += ", " + comp[i];
			    		}
					}*/
			    	int l = 730;
			    	for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
			    			
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(20, l);
					        canvas.showText(comp[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        l -= 10; 
			    		}
			    	}
			    	
				    //doc.add(new Paragraph("  " + competencias + " " + regla,font1));
			        doc.add(new Paragraph("  ",font1));
				    
				    if(count <= 1)
				    	doc.add(new Paragraph(" ",font1));
					
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,5,Color.BLACK)));
				    doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
					doc.add(new Paragraph(" ",font1));
					
					count = 0;
					lin = ("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString()).length();
				    count += Math.ceil(lin/120);
				    
				    System.out.println(count);
				    
				    String insp = spnombre.getSelectedItem().toString().trim();
				    String noInsp = etNoI.getText().toString().trim();
				    
				    if(!TextUtils.isEmpty(spNombreA.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA1.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA1.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA1.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA2.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA2.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA2.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA3.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA3.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA3.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA4.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA4.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA4.getText().toString().trim();
				    }
					
					
				    p = new Paragraph("" + insp,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    if(count <= 1)
				    	doc.add(new Paragraph(" ",new Font(Font.HELVETICA,6,Color.BLACK)));
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    
				    
				    p = new Paragraph("                                                          " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(150, 640);
			        canvas.showText(noInsp);
			        canvas.endText();
			        canvas.restoreState();
					
	
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,8,Color.BLACK)));
				   
				    
				    p = new Paragraph("                                                                                                                                                       " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(370, 620);
			        canvas.showText(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() + " " + etReferencia.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    
				    p = new Paragraph("",font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    //int d = 5;
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(25, 610);
			        canvas.showText(etCalle.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 595);
			        canvas.showText(etEntreC.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    Font font2 = new Font(Font.HELVETICA,7,Color.BLACK);
				    
				    doc.add(new Paragraph("                        ",font2));
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 585);
			        canvas.showText(etEntreC1.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
			        doc.add(new Paragraph(" ",font2));
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(85, 575);
			        canvas.showText(etFraccionamiento.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
			        
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(400, 575);
			        canvas.showText(zon);
			        canvas.endText();
			        canvas.restoreState();
				    
			        
			        p = new Paragraph("                                                                                                                                 ",font2);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(170, 565);
			        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    
			        
			        p = new Paragraph("                                                            ",font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,9,Color.BLACK)));
				    
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(20, 535);
			        canvas.showText(etMotivo.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    p = new Paragraph("   ",font1);
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    
				    //doc.add(new Paragraph(" " + etInspccionFue.getText().toString(),font1));
				    doc.add(new Paragraph(" " ,font1));
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(20, 480);
			        canvas.showText(spPeticion.getSelectedItem().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    /*doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));*/
				    
				    /*p = new Paragraph("                                                                                                         " + dia + "                                  " + me.toUpperCase() + "                                " + a,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);*/
				    p = new Paragraph("                                                                                                         ",font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    //int d = 5;
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(280, 235);
			        canvas.showText(dia + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(350, 235);
			        canvas.showText(me.toUpperCase());
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(440, 235);
			        canvas.showText(a + "");
			        canvas.endText();
			        canvas.restoreState();
				    
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,5,Color.BLACK)));
				    //doc.add(new Paragraph(" ",font1));
				    
				    /*p = new Paragraph("                     " + hora + "                                                        " + dia + "                                                            " + me.toUpperCase() + "                                                                    " + a,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);*/
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 145);
			        canvas.showText(hora);
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(200, 145);
			        canvas.showText(dia + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(350, 145);
			        canvas.showText(me.toUpperCase());
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(390, 145);
			        canvas.showText(a + "");
			        canvas.endText();
			        canvas.restoreState();
				    
			        
			        p = new Paragraph("                     ",font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    p = new Paragraph("                          " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 137);
			        canvas.showText(etNombreV.getText().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    p = new Paragraph(" ",new Font(Font.HELVETICA,5,Color.BLACK));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    if(cbFirma.isChecked()) {
				    	if(spIdentifica.getSelectedItem().toString().equalsIgnoreCase("")) {
				    		//doc.add(new Paragraph("                                                                                                                                                                                                       Si                               No",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		
				    		canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(410, 125);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(485, 125);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
				    	} else {
				    		//doc.add(new Paragraph("                                                                                                                                                                                                       Si                               Si",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		
				    		canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(410, 125);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(485, 125);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
					        
				    	}
				    } else {
				    	if(spIdentifica.getSelectedItem().toString().equalsIgnoreCase("")) {
				    		//doc.add(new Paragraph("                                                                                                                                                                                                       No                               No",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		
				    		doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		
				    		canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(410, 125);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(485, 125);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
				    	}
				    	else {
				    		//doc.add(new Paragraph("                                                                                                                                                                                                       No                               Si",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		
				    		doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    		
				    		canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(410, 125);
					        canvas.showText("No");
					        canvas.endText();
					        canvas.restoreState();
					        
					        canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(485, 125);
					        canvas.showText("Si");
					        canvas.endText();
					        canvas.restoreState();
				    		
				    	}
				    }
				    
				    p = new Paragraph(" " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(30, 110);
			        canvas.showText(etVIdentifica.getText().toString().trim());
			        canvas.endText();
			        canvas.restoreState();
				    
					
				    doc.close();
				
					
					
				}catch (DocumentException e) {
					System.err.println(e.getMessage() + " doc ");
				}catch (Exception e) {
					Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
					Log.e("Error al abrir", e.getMessage() + " c ");
					System.err.println(e.getMessage() + " n ");
				} 
			} 
		} else if(formato.equalsIgnoreCase("citatorio")) {
			
			Paragraph p;
			
			doc.open();
			
			
			if(id == 1) {
				try {
					
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					
				    p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("          " + String.valueOf(a).substring(0, 2) + "                                      " + spnombre.getSelectedItem().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    System.out.println("citatoriocitatorio");
				    
				    p = new Paragraph("                      " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() + "      ");
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                        " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                             " + etPropietario.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                   " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                            " + spManifiesta .getSelectedItem().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                  " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph("                                  " + hora);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etMotivo.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					
					doc.add(new Paragraph(" "));
				    p = new Paragraph("                                                  " + hora + "                     " + dia + "            " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph("  "));
					doc.add(new Paragraph("  "));
					doc.add(new Paragraph("  "));
					
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
					
				} catch(Exception e) {
					 System.out.println(e.getMessage() + " aqui");
				}
			} else {
				
				try {
					
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph("                                                                                 " + hora + "                   " + dia);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                      " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
					
				} catch (Exception e) {
					
				}
				
			}
			
		} else if(formato.equalsIgnoreCase("apercibimiento")) {
			
			Paragraph p;
			
			doc.open();
			
			try {
				
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
			    p = new Paragraph(etNumeroActa.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_RIGHT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                         " + spnombre.getSelectedItem().toString() + "                                                                       " + etNoI.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                                                      " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                      " + etCalle.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                                    " + etFraccionamiento.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                         " + etNombreV .getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                                    " + etVIdentifica.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + spManifiesta.getSelectedItem().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + etGiro.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                " + etNombreT.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + spnombre.getSelectedItem().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                               " + etIfeT2.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    
			    p = new Paragraph("   " + etSeleccion.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("   " + conti + " " + etInfraccion.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    p = new Paragraph("                                                                 20");
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    String fe [] = etfecha.getText().toString().split("/");
			    
			    p = new Paragraph("                                                       " + fe[0] + "                         " + getMes(Integer.parseInt(fe[1])));
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    
			    p = new Paragraph("          " + spnombre.getSelectedItem().toString().trim() + mitad(len) + " " + etNombreV.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    len = MITAD - etNombreT.getText().toString().trim().length();
			    
			    p = new Paragraph("          " + etNombreT.getText().toString().trim() + mitad(len) + " " + etNombreT1.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.close();
				
			} catch(DocumentException e) {
				
			}
			
		} else if(formato.equalsIgnoreCase("hechos")) {
			
			Paragraph p;
			
			doc.open();
		    
			if(id == 2) {
			
				try {
					
				
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                    " + hora + "           " +  dia);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("    " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("   "  + etNum.getText().toString() + "         " + etFecham.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("             " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    
				    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                        " + etManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                           " + hr);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph("  "));
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
				    
				} catch (Exception e) {
					
				}
			} else {
				
				try {
					
					
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                    " + hora + "           " +  dia );
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   " + getMes(mes) + "   "  +   String.valueOf(a).substring(0,2) );
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                  "  + etNum.getText().toString() + "            " + etFecham.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                    " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etPropietario.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etMotivo.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    
				    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                        " + etManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                           " + hr);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph("  "));
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
				    
				} catch (Exception e) {
					
				}
				
			}
			
		}
		
	}
	
	public String mitad(int lenght) {
		String str = "";
		for (int i = 0; i < lenght; i++) {
			str += " "; 
		}
		return str;
	}
	
	public void mostrarResutados(String query) {
		arregloInfraccion.clear();
		buscarInfraccionL(query);
		if(!arregloInfraccion.isEmpty()){
	       spInfraccion.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.multiline_spinner_dropdown_item,arregloInfraccion));
	    }
		else
			spInfraccion.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item));
		
		
	}
	
	public void buscarInfraccionL(String search) {
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE infraccion  LIKE '%" + search + "%' and id_c_direccion = '" + id + "' AND vigente = 'S'; ", null);
		if(cursor.moveToFirst()){
    		arregloInfraccion.add("");
    		do{
    			if(cursor.getString(cursor.getColumnIndex("vigente")).trim().equalsIgnoreCase("S")) {
	    			id_hecho.add(cursor.getInt(0));
	    			arregloInfraccion.add(cursor.getString(2));
	    			Log.i("listado", "Infraccion: " + cursor.getString(2));
    			}
    		}while(cursor.moveToNext());
    	}
		cursor.close();
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.cbFlag:
			if(isChecked) 
				hech = "los hechos antes descritos, son detectados en flagrancia y constituyen una infracciÔøΩn a lo dispuesto por los artÔøΩculos:";
			else 
				hech = "los hechos antes descritos, constituyen una infracciÔøΩn a lo dispuesto por los artÔøΩculos:";
			break;
			
		case R.id.cbFirma:
			System.err.println(cbFirma.isChecked());
			if (isChecked) {
				System.err.println("si");
			} else {
				System.err.println("no");
			}
			break;

		default:
			break;
		}
		
		System.out.println(hech);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		String sel = "";
		
		switch (arg0.getId()) {
		case R.id.spNombreAcompanante1:
			
			etIfeA1.setEnabled(false);
			etNoA1.setEnabled(false);
			etVigA1.setEnabled(false);
			etNombreT1.setEnabled(true);
			sel = spNombreA1.getItemAtPosition(arg2).toString();
			buscarAcompanante1(sel.toString());
			etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
			etNoA1.setText(noA1);
			etVigA1.setText(vigA1);
			
			
			if(arg2 != 0){
				etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
				etNoA1.setText(noA1);
				etVigA1.setText(vigA1);
				int p = arg2-1;
				id_inspector3 = id_i3.get(p);
			}
				
			else{
				if(resov) {
					etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
					etNoA1.setText(noA1);
					etVigA1.setText(vigA1);
					id_inspector3 = id_i3.get(arg2);
				} else {
					etIfeA1.setText("");
					etNoA1.setText("");
					etVigA1.setText("");
					/*etIfeA1.setEnabled(true);
					etNoA1.setEnabled(true);
					etVigA1.setEnabled(true);*/
					id_inspector3 = 0;
				}
			}
			
			if(consu) {
				etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
				etNoA1.setText(noA1);
				etVigA1.setText(vigA1);
				etIfeA1.setEnabled(false);
				etNoA1.setEnabled(false);
				etVigA1.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector3", id_inspector3 + "");
			break;
		case R.id.spNombreAcompanante2:
			etIfeA2.setEnabled(false);
			etNoA2.setEnabled(false);
			etVigA2.setEnabled(false);
			sel = spNombreA2.getItemAtPosition(arg2).toString();
			buscarAcompanante2(sel.toString());
			etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
			etNoA2.setText(noA2);
			etVigA2.setText(vigA2);
			
			if(arg2 != 0){
				etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
				etNoA2.setText(noA2);
				etVigA2.setText(vigA2);
				id_inspector4 = id_i4.get(arg2-1);
			}
				
			else{
				if(resov){
					etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
					etNoA2.setText(noA2);
					etVigA2.setText(vigA2);
					id_inspector4 = id_i4.get(arg2-1);
				} else {
					etIfeA2.setText("");
					etNoA2.setText("");
					etVigA2.setText("");
					/*etIfeA2.setEnabled(true);
					etNoA2.setEnabled(true);
					etVigA2.setEnabled(true);*/
					id_inspector4 = 0;
				}
			}
			if(consu) {
				etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
				etNoA2.setText(noA2);
				etVigA2.setText(vigA2);
				etIfeA2.setEnabled(false);
				etNoA2.setEnabled(false);
				etVigA2.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector4", id_inspector4 + "");
			break;
		case R.id.spNombreAcompanante3:
			etIfeA3.setEnabled(false);
			etNoA3.setEnabled(false);
			etVigA3.setEnabled(false);
			sel = spNombreA3.getItemAtPosition(arg2).toString();
			buscarAcompanante3(sel.toString());
			etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
			etNoA3.setText(noA3);
			etVigA3.setText(vigA3);
			
			if(arg2 != 0){
				etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
				etNoA3.setText(noA3);
				etVigA3.setText(vigA3);
				int p = arg2-1;
				id_inspector5 = id_i5.get(p);
			}
				
			else{
				if(resov) {
					etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
					etNoA3.setText(noA3);
					etVigA3.setText(vigA3);
					id_inspector5 = id_i5.get(arg2-1);
				} else {
					etIfeA3.setText("");
					etNoA3.setText("");
					etVigA3.setText("");
					/*etIfeA3.setEnabled(true);
					etNoA3.setEnabled(true);
					etVigA3.setEnabled(true);*/
					id_inspector5 = 0;
				}
			}
			
			if(consu) {
				etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
				etNoA3.setText(noA3);
				etVigA3.setText(vigA3);
				etIfeA3.setEnabled(false);
				etNoA3.setEnabled(false);
				etVigA3.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector5", id_inspector5 + "");
			break;
		case R.id.spNombreAcompanante4:
			etIfeA4.setEnabled(false);
			etNoA4.setEnabled(false);
			etVigA4.setEnabled(false);
			sel = spNombreA4.getItemAtPosition(arg2).toString();
			buscarAcompanante4(sel.toString());
			etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" +  ifeA4);
			etNoA4.setText(noA4);
			etVigA4.setText(vigA4);
			
			if(arg2 != 0) {
				etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA4);
				etNoA4.setText(noA4);
				etVigA4.setText(vigA4);
				int p = arg2-1;
				id_inspector6 = id_i6.get(p);
			}
				
			else{
				if(resov) {
					etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA4);
					etNoA4.setText(noA4);
					etVigA4.setText(vigA4);
					id_inspector6 = id_i6.get(arg2-1);
				}else {
					etIfeA4.setText("");
					etNoA4.setText("");
					etVigA4.setText("");
					/*etIfeA4.setEnabled(true);
					etNoA4.setEnabled(true);
					etVigA4.setEnabled(true);*/
					id_inspector6 = 0;
				}
			}
			if(consu) {
				etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA4);
				etNoA4.setText(noA4);
				etVigA4.setText(vigA4);
				etIfeA4.setEnabled(false);
				etNoA4.setEnabled(false);
				etVigA4.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector6", id_inspector6 + "");
			break;
			
		case R.id.spReglamento:
			if(arg2 != 0) {
				idComp = idCompetencia.get(arg2);
				competencias = competencia.get(arg2);
				regla = reglamento.get(arg2);
			}
			break;
			
		case R.id.spMedida:
			if(!spMedida.getSelectedItem().toString().equalsIgnoreCase("")) {
				medidas1 = spMedida.getSelectedItem().toString();
				if(id == 12) {
					etMedida.setText(spMedida.getSelectedItem().toString());
					etArticulo.setText(art.get(spMedida.getSelectedItemPosition()).trim() + " del " + orden.get(spMedida.getSelectedItemPosition()).trim());
				}
				
				if(id == 2) {
					etMedida.setText(spMedida.getSelectedItem().toString());
					etArticulo.setText(art.get(spMedida.getSelectedItemPosition()).trim() + " del " + orden.get(spMedida.getSelectedItemPosition()).trim());
				}
			}
			break;
			
		case R.id.spInspectorT:
			buscarTestigo(spInspectorT);
			
			etNombreT.setText(spInspectorT.getSelectedItem().toString().trim());
			etIfeT.setText(idT);
			spIdentificaT.setSelection(1);
			break;
			
		case R.id.spInspectorT1:
			buscarTestigo(spInspectorT1);
			
			etNombreT1.setText(spInspectorT1.getSelectedItem().toString().trim());
			etIfeT2.setText(idT1);
			spIdentificaT1.setSelection(1);
			break;
			
		case R.id.spdesignado1:
			if(spdesignado1.getItemAtPosition(arg2).toString().equalsIgnoreCase("inspector")) {
				spInspectorT1.setSelection(0);
				spInspectorT1.setEnabled(true);
				/*etIfeA.setText(ifeA);
				etNoA.setText(noA);
				etVigA.setText(vigA);
				etNombreT.setText(spNombreA.getSelectedItem().toString());
				etIfeT.setText(ifeA);*/
			}
			else {
				spInspectorT1.setSelection(0);
				spInspectorT1.setEnabled(false);
				etIfeT2.setText("");
				/*etIfeA.setText("");
				etNoA.setText("");
				etVigA.setText("");
				etNombreT.setText("");
				etIfeT.setText("");*/
			}
			break;

		default:
			break;
		}
	}
	
	public void buscarTestigo(Spinner sp){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + sp.getSelectedItem().toString().trim() + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				if(sp.getId() == R.id.spInspectorT) {
    					idT = c.getString(2);
    				} else {
    					idT1 = c.getString(2);
    				}
    				/*ifeA = c.getString(2);
    				noA = c.getString(c.getColumnIndex("no_empleado"));
    				vigA = c.getString(c.getColumnIndex("vigencia"));*/
    				Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			if(sp.getId() == R.id.spInspectorT) {
					idT = "";
				} else {
					idT1 = "";
				}
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	
	
	public final Handler mHandler = new Handler(new Handler.Callback() {
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean handleMessage(Message msg) {
			Log.d(TAG, "mHandler.handleMessage(" + msg + ")");
			
			switch (msg.what) {
			
			case BixolonPrinter.MESSAGE_DEVICE_NAME:
				mConnectedDeviceName = msg.getData().getString(BixolonPrinter.KEY_STRING_DEVICE_NAME);
				Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
				return true;
				
				case BixolonPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
					/*Set<BluetoothDevice> bluetoothDevicesSet = (Set<BluetoothDevice>)msg.obj;
					for (BluetoothDevice device : bluetoothDevicesSet) {
						if(device.getName().equals("SPP-R200II")) {
							mBixolonPrinter.connect(device.getAddress());
							break;
						}
					}*/
					if (msg.obj == null) {
						Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
					} else {
						DialogManager.showBluetoothDialog(InfraccionesActivity.this, (Set<BluetoothDevice>) msg.obj);
					}
					return true;
				
				case BixolonPrinter.MESSAGE_STATE_CHANGE:
					
					if(msg.arg1 == BixolonPrinter.STATE_CONNECTED) {
						//isConected = true;
						
						/*BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(com.pgm.transito.R.drawable.logo1);
						Bitmap bitmap = drawable.getBitmap();
						mBixolonPrinter.printBitmap(bitmap, BixolonPrinter.ALIGNMENT_CENTER, 150, 50, true,true,true);
						String texto = "hola";
						mBixolonPrinter.printText(texto, BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_UNDERLINE1, BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, true);
						mBixolonPrinter.printText(texto, BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.TEXT_ATTRIBUTE_FONT_B | BixolonPrinter.TEXT_ATTRIBUTE_UNDERLINE2, BixolonPrinter.TEXT_SIZE_HORIZONTAL2 | BixolonPrinter.TEXT_SIZE_VERTICAL2, true);
						mBixolonPrinter.cutPaper(true);
						System.out.println("conectado");*/
					}
					break;
				case BixolonPrinter.STATE_NONE:
					//isConected = false;
					break;
				case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
					//mBixolonPrinter.disconnect();
					//mHandler.sendEmptyMessage(0);
					break;
			}
			
			
			return true;
			}
	});
	
	@SuppressLint({ "InflateParams", "DefaultLocale" })
	private void printSampleGDL() {
		if (mSampleDialog == null) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			final View view = inflater.inflate(R.layout.order, null);
			
			TextView tvMulta = (TextView)view.findViewById(R.id.tvText);
			
			final String cuerpo = "Zapopan, Jalisco, a las 14:30 catorce horas con treinta minutos del día 10 diez de septiembre del año 2019 dos mil diecinueve. El suscrito, SAÚL LÉMUS GUERRERO, Inspector Multimodal con clave operativa IC-199, en términos de lo dispuesto por los artículos 70 y 73, segundo párrafo, de la Ley del Procedimiento Administrativo del Estado de Jalisco, me constituyo física y legalmente en la calle Hidalgo frente al número 100, entre las calles Emiliano Zapata y 20 de Enero, colonia y/o fraccionamiento Centro, cerciorado de lo anterior por haber tenido a la vista la placa de nomenclatura de la calle más próxima, y porque así lo corrobora quien manifiesta llamarse JACOBO MEDRANO CÁRDENAS, visitado, ante quien me identifico con credencial oficial con fotografía folio número 000, vigente del 1° primero de agosto al 30 treinta de septiembre del 2019 dos mil diecinueve, expedida por el Director de Inspección y Vigilancia del Ayuntamiento de Zapopan, Jalisco; haciéndole saber que el motivo de mi presencia es en atención al reporte ZAPMX377, y enterado de los alcances de la diligencia que por este acto se practica le requiero por una identificación, presentando INE 3005089789185, igualmente le hago saber el derecho que tiene de nombrar a dos personas que fungirán como testigos y estén presentes durante el desahogo de la visita y que de no designar a persona alguna para ello, el suscrito lo haré en rebeldía; en consecuencia, fueron designados por el suscrito los C.C. MA DEL ROSARIO SEDANO GONZÁLEZ, quien se identifica con credencial oficial folio número 0062, respectivamente. Acto seguido, una vez practicada la inspección, resultaron los siguientes hechos: QUE AL MOMENTO DE LA INSPECCIÓN SE CONSTATA QUE REBASA LOS METROS AUTORIZADOS EN SU PERMISO DE FOLIO NÚMERO 131584DE, FECHADO EL 22 VEINTIDÓS DE AGOSTO DEL 2019 DOS MIL DIECINUEVE. Hechos que constituyen infracción a lo dispuesto por los artículos 58, FRACCIÓN IV, DEL REGLAMENTO DE TIANGUIS Y COMERCIO EN ESPACIOS PÚBLICOS DEL MUNICIPIO DE ZAPOPAN, JALISCO. Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: SE LEVANTA LA PRESENTER ACTA PARA LOS FINES LEGALES Y ADMINISTRATIVOS QUE HAYA LUGAR Y SE PROCEDE CON EL INCAUTO PRECAUTORIO DE 2 DOS CAJAS DE CARTÓN EN CUYO INTERIOR SE ALAMCENA PLÁTANO. Lo anterior de conformidad a lo dispuesto por los artículos 5, FRACCIONES IV Y V, 10, 112, FRACCIONES II Y III, 116, 117, 118, FRACCIONES II Y III Y 123, SEGUNDO PÁRRAFO, DEL REGLAMENTO DE TIANGUIS Y COMERCIO EN ESPACIOS PÚBLICOS DEL MUNICIPIO DE ZAPOPAN, JALISCO. Se concede el uso de la voz al visitado para que a los hechos señalados manifieste lo que a su derecho convenga y aporte pruebas, enterado señala: QUE SE RESERVA EL DERECHO. Finalmente, se le informa que el acta resultado de esta diligencia podrá ser impugnada a través del RECURSO DE REVISIÓN, previsto por el artículo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, para lo cual tendrá un plazo de 20 veinte días hábiles, contados a partir del día siguiente de la fecha en que se levante el acta correspondiente; debiendo interponer dicho recurso por escrito que presente en la oficia de la Dirección Jurídica Contenciosa dependiente de Sindicatura, en avenida Hidalgo número 151, colonia Centro de esta Ciudad. Se da por concluida esta diligencia a las 15.00 quince horas del 10 diez de septiembre del presente año, levantándose acta en presencia del visitado y testigos que intervinieron, firmando para constancia los que quisieron y supieron hacerlo, quedando copia legible en poder del interesado para los efectos conducentes. Lo anterior, en términos de lo dispuesto por el artículo 74 de la Ley invocada";
			
			//extracto pruebas
			/*final String cuerpo = "En la ciudad de Zapopan, Jalisco, siendo las 16:30 horas del día 20 de Septiembre del año 2019, el suscrito FRANCISCO JAVIER VÁZQUEZ GARCÍA, Inspector Municipal con clave 0037, facultado para llevar a cabo la inspección y vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, mediante y en cumplimiento de la Orden de Visita folio número OV-2019 dictada por el Director de Inspección y Vigilancia de Zapopan, Jalisco el día 20 de Agosto, misma que en original exhibo y en copia legible entrego al visitado, Javier Contreras Gonzalez, me constituí física y legalmente en " +
					" marcada (o) con el número 2345 de la calle Estudientes, entre las calles pintores y Escultores, en la colonia y/o fraccionamiento Jardenes de Guadalupe, cerciorándome de ser este el domicilio , e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco y sus Municipios con credencial oficial con fotografía folio número 1234, vigente de Agosto de 2019 a Agosto del 2021, expedida por el Director de Inspección y Vigilancia del Gobierno municipal de Zapopan, Jalisco, ante Javier Contreras Gonzalez quien se identifica con INE , manifiesta ser  Encargado de la propiedad de Gloria Camarena Lara, le informo el derecho que le asiste para designar a dos testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a ello el suscrito lo haría en rebeldía  fueron designados los C.C. Testigo de asistencia 1 y Testigo de asistencia 2 por el Inspector, mismo que se identifican con INE 12345678, INE 07654321 respectivamente; así, como de la prerrogativa que en todo momento tiene de manifestar lo que a su derecho convenga y aportar las pruebas que considere pertinentes. Acto seguido le hago saber al visitado, una vez practicada la diligencia, los hechos encontrados y que consisten en: Falta de Licencia de Giro Comercial, industrial o de prestación de servicios los cuales constituyen infracción a lo dispuesto por los artículos: 4,1 Reglamento para el Funcionamiento de Giros Comerciales, Industriales y de Prestación de Servicios en el Municipio de Guadalajar Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: ACTA DE VERIFICACION Lo anterior de conformidad a lo dispuesto por los artículos.12,13,15 del reglamento de pruebas En uso de su derecho al visitado: Su propio Dicho Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el artículo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco y sus municipios, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 veinte días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No. 151)."+
					"Se da por concluida esta diligencia, siendo las 16:35 horas del 20 de Septiembre del año 2019 levantándose la presente acta en presencia de los testigos que se mencionan, quedando copia legible en poder del interesado y firmando para constancia los que en ella intervinieron, quisieron y supieron hacerlo.";
			
			//extracto con el ejemplo
			final String cuerpo = "En la ciudad de Zapopan, Jalisco, siendo las 09:57 horas del día 2 de Septiembre del año 2019, el suscrito SAUL LEMUS GUERRERO, Inspector Municipal con clave IC-199, facultado para llevar a cabo la inspección y vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, misma que en original exhibo y en copia legible entrego al visitado, JACOBO MEDRANO CARDENAS, me constituí física y legalmente en EL LUGAR DONDE ILEGIBLE" +
					" marcada (o) con el número S/N de la calle LAS PALMAS, entre las calles RIO BLANCO y , en la colonia y/o fraccionamiento RINCONADA DE LOS SAUCES, cerciorándome de ser este el domicilio CORRECTO A INSPECCIONAR EN ATENCIÓN AL REPORTE ZAPMX377, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número 0173, vigente de 01 AGOSTO DE 2019 a 30 SEPTIEMBRE del 2019, expedida por el Director de Inspección y Vigilancia del Gobierno municipal de Zapopan, Jalisco, ante JACOBO MEDRANO CARDENAS quien se identifica con INE 3005089789185, manifiesta ser  PROPIETARIO de JACOBO MEDRANO CARDENAS, le informo el derecho que le asiste para designar a dos testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a ello el suscrito lo haría en rebeldía ACTO SEGUIDO fueron designados los C.C. MA. DEL ROSARIO SEDANO GONZALEZ por el SUSCRITO, mismo que se identifican con CREDENCIAL OFICIAL FOLIO 0062 respectivamente; así, como de la prerrogativa que en todo momento tiene de manifestar lo que a su derecho convenga y aportar las pruebas que considere pertinentes. Acto seguido le hago saber al visitado, una vez practicada la diligencia, los hechos encontrados y que consisten en: QUE AL MOMENTO DE LA INSPECCIÓN SE CONSTA DE QUE REBASA LOS METROS PERMITIDOS EN SU PERMISO QUE MOSTRO CON FOLIO131584DE FECHA 22 DE AGOSTO DE 2019POR LO QUE SE LEVANTA LA PRESENTE ACTA POR REBASAR LOS LIMITES DE MEDIDAS PERMITIDAS los cuales constituyen infracción a lo dispuesto por los artículos: 58 FRACCIÓN IV DEL REGLAMENTO DE TIANGUIS Y EN ESPACIOS PUBLICOS DEL MUNICIPIO DE ZAPOPAN JALISCO Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: SE LEVANTA LA PRESENTE ACTA PARA LOS FINES LEGALES Y ADMINISTRATIVOS QUE HAYA LUGAR Y SE PROCEDE CON EL INCAUTO PRECAUTORIO CONSISTENTE EN 2 CAJAS DE CARTON CUYO INTERIOR CONTIENE PLATANO Lo anterior de conformidad a lo dispuesto por los artículos. 5 FRACCIÓN IV Y V ARTICULO 10 112 FRACCIÓN II Y III, 116 , 117 ,118 , FRACCIONES II Y III Y 123 SEGUNDO PARAFO DEL REGLAMENTO DE TIANGUIS Y COMERCIO EN ESPACIOS PUBLICOS DEL MUNICIPIO DE ZAPOPAN. En uso de su derecho al visitado: SE RESERVA EL DERECHO. Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el artículo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Sindico Municipal de Zapopan, Jalisco dentro del plazo de 20 veinte días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No. 151)."+
					"Se da por concluida esta diligencia, siendo las 10:15 horas del 2 de Septiembre 2019 levantándose la presente acta en presencia de los testigos que se mencionan, quedando copia legible en poder del interesado y firmando para constancia los que en ella intervinieron, quisieron y supieron hacerlo.";*/
                                                           

/*PARA LA CALIFICACIÓN CORRESPONDIENTE DE LA PRESENTE ACTA DEBERÁ ACUDIR ANTE EL C. JUEZ CALIFICADOR DEL DEPARTAMENTO DE CALIFICACIÓN DE ESTE H. AYUNTAMIENTO DE ZAPOPAN, JALISCO, EN LA PLANTA BAJA DEL EDIFICIO QUE OCUPA LA UNIDAD ADMINISTRATIVA BASÍLICA.
Zapopan, Jalisco, a _________ de _______________________ del año _________.
Por recibida el Acta número ____________________________________ por la cual según consta en el cuerpo de la misma, el infractor se hace acreedor a la siguiente sanción de conformidad a lo dispuesto por los artículos __________________________________________________ de la Ley de Ingresos Vigente del Municipio de Zapopan, Jalisco, que consiste en la cantidad de: $_____________________________________________________________________";*/
			
			tvMulta.setText(cuerpo);
					
			
			mSampleDialog = new AlertDialog.Builder(InfraccionesActivity.this).setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@SuppressLint("NewApi")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String [] str;
					
					
					mBixolonPrinter.setSingleByteFont(BixolonPrinter.CODE_PAGE_1252_LATIN1);
					
					mBixolonPrinter.setPageMode();
					InfraccionesActivity.mBixolonPrinter.setPrintArea(0, 0, 576, 200);

					Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

					mBixolonPrinter.setPrintDirection(BixolonPrinter.DIRECTION_0_DEGREE_ROTATION);
					mBixolonPrinter.setAbsoluteVerticalPrintPosition(200);
					mBixolonPrinter.setAbsolutePrintPosition(20);
					//mBixolonPrinter.printBitmap(bm, BixolonPrinter.ALIGNMENT_LEFT, 200, 65, false);
					mBixolonPrinter.printBitmap(bm, BixolonPrinter.ALIGNMENT_LEFT, 200, 65, false);
					

					

					mBixolonPrinter.formFeed(true);

					//normal mode
					
					mBixolonPrinter.lineFeed(2, false);
					
					str = Justificar.justifocarTexto(cuerpo, "a");
					
					String n = "";
					
					for (int i = 0; i < str.length; i++) {
						System.out.println(str[i]);
						n += str[i] + "\n";
						
					}
					System.err.println("n " + n);
					
					mBixolonPrinter.printText("IN/12/1/2/9/2019/01\n\n", BixolonPrinter.ALIGNMENT_RIGHT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.printText("ACTA DE INSPECCIÓN COMERCIO \n\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.printText(n + "\n", BixolonPrinter.ALIGNMENT_LEFT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					/*String cuerpo2 = "Para la presente infracción se incautaron 20 sillas, 5 mesas, 1 olla, 30 botellas de refresco, un carrito y ";
					
					str = Justificar.justifocarTexto(cuerpo2, "a");
					
					n = "";
					
					for (int i = 0; i < str.length; i++) {
						System.out.println(str[i]);
						n += str[i] + "\n";
						
					}
					
					mBixolonPrinter.printText(n + " \n", BixolonPrinter.ALIGNMENT_LEFT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);*/
					
					mBixolonPrinter.printText("El Inspector" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("El Visitado" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("Testigo" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("Testigo" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("G 1  \t  NE 1  \t R 1" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(3, false);
					
					String cuerpo1 = "PARA LA CALIFICACIÓN CORRESPONDIENTE DE LA PRESENTE ACTA DEBERÁ ACUDIR ANTE EL C. JUEZ MUNICIPAL DEL DEPARTAMENTO DE LA UNIDAD DE JUECES CALIFICADORES DE ESTE H. AYUNTAMIENTO DE ZAPOPAN, JALISCO, EN LA PLANTA BAJA DEL EDIFICIO QUE OCUPA LA UNIDAD ADMINISTRATIVA BASÍLICA. Zapopan, Jalisco, a           de                                  del año        . Por recibida el Acta número por la cual según consta en el cuerpo de la misma, el infractor se hace acreedor a la siguiente sanción de conformidad a lo dispuesto por los artículos                                                                                                                                                              de la Ley de Ingresos Vigente del Municipio de Zapopan, Jalisco, que consiste en la cantidad de: $ ";
					
					str = Justificar.justifocarTexto(cuerpo1, "a");
					
					n = "";
					
					for (int i = 0; i < str.length; i++) {
						System.out.println(str[i]);
						n += str[i] + "\n";
						
					}
					System.err.println("n " + n);
					
					mBixolonPrinter.printText(n + " \n", BixolonPrinter.ALIGNMENT_LEFT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					

					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.disconnect();
				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create();
		}
		
		mSampleDialog.show();
	}
	
	public void buscarPeticion() {
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
	    	Cursor c = db.rawQuery("SELECT * FROM c_peticion", null);
	    	Log.i("que", "SELECT * FROM c_peticion");
	    	if(c.moveToFirst()){
	    		Log.i("no", "no");
	    		peticion.clear();
	    		peticion.add("");
	    		do{
	    			peticion.add(c.getString(1));
	    			Log.i("peticion ", c.getString(1));
	    		}while(c.moveToNext());
	    		adapter1.notifyDataSetChanged();
	    	}
	    	c.close();
	    }catch (SQLiteException e) {
			Log.i("ERROR FATAL", e.getMessage());
		}finally{
	    		db.close();
		}
	}

    public void buscarCompetencia() {
	    competencias1 = "";
        GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarBD.getReadableDatabase();
        try{
            Cursor c = db.rawQuery("SELECT competencia FROM c_ordenamiento where id_c_direccion = " + id, null);
            Log.i("que", "SELECT competencia FROM c_ordenamiento where id_c_direccion = " + id + " order by id_c_ordenamiento limit 1");
            if(c.moveToFirst()){
                Log.i("no", "no");
                do{
                    competencias1 = c.getString(c.getColumnIndex("competencia"));
                }while(c.moveToNext());
            }
            c.close();
        }catch (SQLiteException e) {
            Log.i("ERROR FATAL", e.getMessage());
        }finally{
            db.close();
        }
    }
}
