package com.perspective.inszap;


//,manifiesta_ser text,fraccionamiento text,calle text,numero_ext text,numero_int text,apellidop_prop text,apellidom_prop text,nombre_razon text,nombre_testigo1 text,ife_testigo1 text,designado_por1 text,nombre_testigo2 text,ife_testigo2 text,designado_por2 text,uso_catalogo text,hechos text,infracciones text,id_c_infraccion text,uso_suelo text,densidad text,manifiesta text,gravedad int,dias_plazo int,fecha_plazo numeric,hora_termino time,tipo_visita TEXT,id_pago int,pago numeric, fecha_pago numeric, estatus text,condominio TEXT,manzana TEXT,lote TEXT,capturo text,fecha_atiende_juez numeric, fecha_cancelacion numeric,fecha_efectua_multa numeric, vigencia_multa int, fecha_vigencia numeric,multa text,observaciones text, referencia TEXT

public class Levantamiento {
	
	private int idLevantamiento,infraccion,tipoActa,idCDireccion,ordenVista,id_c_inspector1,id_c_inspector2,id_c_inspector3,id_c_inspector4,id_c_inspector5,id_c_inspector6,gravedad,dias_plazo,id_pago,idComp,axo_licencia;
	private String numeroActa,numeroCitatorio,fecha,horaInicio,fecha_orden_v,Zona,nombre_visitado,se_identifica,manifiesta_ser,fraccionamiento,calle,numero_ext,numero_int,apellidop_prop,apellidom_prop,nombre_razon,nombre_testigo1,ife_testigo1,designado_por1,nombre_testigo2,ife_testigo2,designado_por2,uso_catalogo,hechos,infracciones,id_c_infraccion,uso_suelo,densidad,manifiesta,fecha_plazo,hora_termino,tipo_visita,pago,fecha_pago,estatus,condominio,manzana,lote,capturo,referencia,entre_calle1,entre_calle2,responsable_obra,registro_responsable,identifica,peticion,v_firma,motivo_orden,medida_seguridad,articulo_medida,l_construccion,licencia_giro,actividad_giro,nombre_comercial,sector;
	private double latitud,longitud;
	
	
	public Levantamiento(int idCDireccion,int id_c_inspector1,int id_c_inspector2,String numeroActa,String nombre_visitado,String se_identifica,
			String manifiesta_ser,String fraccionamiento,String calle,String numero_ext,String numero_int,String nombre_razon,
			String apellidop_prop,String apellidom_prop,int idComp,String fecha,String entre_calle1,String entre_calle2,
			String responsable_obra,String registro_responsable,String identifica,String peticion,String v_firma,String motivo_orden,
			String medida_seguridad,String articulo_medida,int id_c_inspector3,int id_c_inspector4,int id_c_inspector5,int id_c_inspector6,
			String zona,String referencia,String construccion,String condominio,String citatorio,String licencia_giro, String actividad_giro, int axo_licencia) {
		this.idCDireccion = idCDireccion;
		this.id_c_inspector1 = id_c_inspector1;
		this.id_c_inspector2 = id_c_inspector2;
		this.numeroActa = numeroActa;
		this.nombre_visitado = nombre_visitado;
		this.se_identifica = se_identifica;
		this.manifiesta_ser = manifiesta_ser;
		this.fraccionamiento = fraccionamiento;
		this.calle = calle;
		this.numero_ext = numero_ext;
		this.numero_int = numero_int;
		this.nombre_razon = nombre_razon;
		this.apellidop_prop = apellidop_prop;
		this.apellidom_prop = apellidom_prop;
		this.idComp = idComp;
		this.fecha = fecha;
		this.responsable_obra = responsable_obra;
		this.registro_responsable = registro_responsable;
		this.entre_calle1 = entre_calle1;
		this.entre_calle2 = entre_calle2;
		this.identifica = identifica;
		this.peticion = peticion;
		this.v_firma = v_firma;
		this.motivo_orden = motivo_orden;
		this.medida_seguridad = medida_seguridad;
		this.articulo_medida = articulo_medida;
		this.id_c_inspector3 = id_c_inspector3;
		this.id_c_inspector4 = id_c_inspector4;
		this.id_c_inspector5 = id_c_inspector5;
		this.id_c_inspector6 = id_c_inspector6;
		this.Zona = zona;
		this.referencia = referencia;
		this.l_construccion = construccion;
		this.condominio = condominio;
		this.numeroCitatorio = citatorio;
		this.licencia_giro = licencia_giro;
		this.actividad_giro = actividad_giro;
		this.axo_licencia = axo_licencia;
	}
	
	public Levantamiento(int idCDireccion,int id_c_inspector1,int id_c_inspector2,String numeroActa,String nombre_visitado,String se_identifica,
			String manifiesta_ser,String fraccionamiento,String calle,String numero_ext,String numero_int,String nombre_razon,
			String apellidop_prop,String apellidom_prop,int idComp,String fecha,String entre_calle1,String entre_calle2,
			String responsable_obra,String registro_responsable,String identifica,String peticion,String v_firma,String motivo_orden,
			String medida_seguridad,String articulo_medida,int id_c_inspector3,int id_c_inspector4,int id_c_inspector5,int id_c_inspector6,
			String zona,String referencia,String construccion,String condominio,String citatorio,String licencia_giro, String actividad_giro, int axo_licencia,
			String nombre_comercial,String sector) {
		this.idCDireccion = idCDireccion;
		this.id_c_inspector1 = id_c_inspector1;
		this.id_c_inspector2 = id_c_inspector2;
		this.numeroActa = numeroActa;
		this.nombre_visitado = nombre_visitado;
		this.se_identifica = se_identifica;
		this.manifiesta_ser = manifiesta_ser;
		this.fraccionamiento = fraccionamiento;
		this.calle = calle;
		this.numero_ext = numero_ext;
		this.numero_int = numero_int;
		this.nombre_razon = nombre_razon;
		this.apellidop_prop = apellidop_prop;
		this.apellidom_prop = apellidom_prop;
		this.idComp = idComp;
		this.fecha = fecha;
		this.responsable_obra = responsable_obra;
		this.registro_responsable = registro_responsable;
		this.entre_calle1 = entre_calle1;
		this.entre_calle2 = entre_calle2;
		this.identifica = identifica;
		this.peticion = peticion;
		this.v_firma = v_firma;
		this.motivo_orden = motivo_orden;
		this.medida_seguridad = medida_seguridad;
		this.articulo_medida = articulo_medida;
		this.id_c_inspector3 = id_c_inspector3;
		this.id_c_inspector4 = id_c_inspector4;
		this.id_c_inspector5 = id_c_inspector5;
		this.id_c_inspector6 = id_c_inspector6;
		this.Zona = zona;
		this.referencia = referencia;
		this.l_construccion = construccion;
		this.condominio = condominio;
		this.numeroCitatorio = citatorio;
		this.licencia_giro = licencia_giro;
		this.actividad_giro = actividad_giro;
		this.axo_licencia = axo_licencia;
		this.nombre_comercial = nombre_comercial;
		this.sector = sector;
	}
	public String getIdentifica() {
		return identifica;
	}
	public void setIdentifica(String identifica) {
		this.identifica = identifica;
	}
	public String getEntre_calle1() {
		return entre_calle1;
	}
	
	public void setEntre_calle1(String entre_calle1) {
		this.entre_calle1 = entre_calle1;
	}
	
	public String getEntre_calle2() {
		return entre_calle2;
	}
	
	public void setEntre_calle2(String entre_calle2) {
		this.entre_calle2 = entre_calle2;
	}
	
	public String getResponsable_obra() {
		return responsable_obra;
	}
	public void setResponsable_obra(String responsable_obra) {
		this.responsable_obra = responsable_obra;
	}
	public String getRegistro_responsable() {
		return registro_responsable;
	}
	public void setRegistro_responsable(String registro_responsable) {
		this.registro_responsable = registro_responsable;
	}
	public int getIdComp() {
		return idComp;
	}
	public void setIdComp(int idComp) {
		this.idComp = idComp;
	}
	public Levantamiento() {
		
	}
	public int getIdLevantamiento() {
		return idLevantamiento;
	}
	public void setIdLevantamiento(int idLevantamiento) {
		this.idLevantamiento = idLevantamiento;
	}
	public int getInfraccion() {
		return infraccion;
	}
	public void setInfraccion(int infraccion) {
		this.infraccion = infraccion;
	}
	public int getTipoActa() {
		return tipoActa;
	}
	public void setTipoActa(int tipoActa) {
		this.tipoActa = tipoActa;
	}
	public int getIdCDireccion() {
		return idCDireccion;
	}
	public void setIdCDireccion(int idCDireccion) {
		this.idCDireccion = idCDireccion;
	}
	public int getOrdenVista() {
		return ordenVista;
	}
	public void setOrdenVista(int ordenVista) {
		this.ordenVista = ordenVista;
	}
	public int getId_c_inspector1() {
		return id_c_inspector1;
	}
	public void setId_c_inspector1(int id_c_inspector1) {
		this.id_c_inspector1 = id_c_inspector1;
	}
	public int getId_c_inspector2() {
		return id_c_inspector2;
	}
	public void setId_c_inspector2(int id_c_inspector2) {
		this.id_c_inspector2 = id_c_inspector2;
	}
	public int getGravedad() {
		return gravedad;
	}
	public void setGravedad(int gravedad) {
		this.gravedad = gravedad;
	}
	public int getDias_plazo() {
		return dias_plazo;
	}
	public void setDias_plazo(int dias_plazo) {
		this.dias_plazo = dias_plazo;
	}
	public int getId_pago() {
		return id_pago;
	}
	public void setId_pago(int id_pago) {
		this.id_pago = id_pago;
	}
	public String getNumeroActa() {
		return numeroActa;
	}
	public void setNumeroActa(String numeroActa) {
		this.numeroActa = numeroActa;
	}
	public String getNumeroCitatorio() {
		return numeroCitatorio;
	}
	public void setNumeroCitatorio(String numeroCitatorio) {
		this.numeroCitatorio = numeroCitatorio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getFecha_orden_v() {
		return fecha_orden_v;
	}
	public void setFecha_orden_v(String fecha_orden_v) {
		this.fecha_orden_v = fecha_orden_v;
	}
	public String getZona() {
		return Zona;
	}
	public void setZona(String zona) {
		Zona = zona;
	}
	public String getNombre_visitado() {
		return nombre_visitado;
	}
	public void setNombre_visitado(String nombre_visitado) {
		this.nombre_visitado = nombre_visitado;
	}
	public String getSe_identifica() {
		return se_identifica;
	}
	public void setSe_identifica(String se_identifica) {
		this.se_identifica = se_identifica;
	}
	public String getManifiesta_ser() {
		return manifiesta_ser;
	}
	public void setManifiesta_ser(String manifiesta_ser) {
		this.manifiesta_ser = manifiesta_ser;
	}
	public String getFraccionamiento() {
		return fraccionamiento;
	}
	public void setFraccionamiento(String fraccionamiento) {
		this.fraccionamiento = fraccionamiento;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumero_ext() {
		return numero_ext;
	}
	public void setNumero_ext(String numero_ext) {
		this.numero_ext = numero_ext;
	}
	public String getNumero_int() {
		return numero_int;
	}
	public void setNumero_int(String numero_int) {
		this.numero_int = numero_int;
	}
	public String getApellidop_prop() {
		return apellidop_prop;
	}
	public void setApellidop_prop(String apellidop_prop) {
		this.apellidop_prop = apellidop_prop;
	}
	public String getApellidom_prop() {
		return apellidom_prop;
	}
	public void setApellidom_prop(String apellidom_prop) {
		this.apellidom_prop = apellidom_prop;
	}
	public String getNombre_razon() {
		return nombre_razon;
	}
	public void setNombre_razon(String nombre_razon) {
		this.nombre_razon = nombre_razon;
	}
	public String getNombre_testigo1() {
		return nombre_testigo1;
	}
	public void setNombre_testigo1(String nombre_testigo1) {
		this.nombre_testigo1 = nombre_testigo1;
	}
	public String getIfe_testigo1() {
		return ife_testigo1;
	}
	public void setIfe_testigo1(String ife_testigo1) {
		this.ife_testigo1 = ife_testigo1;
	}
	public String getDesignado_por1() {
		return designado_por1;
	}
	public void setDesignado_por1(String designado_por1) {
		this.designado_por1 = designado_por1;
	}
	public String getNombre_testigo2() {
		return nombre_testigo2;
	}
	public void setNombre_testigo2(String nombre_testigo2) {
		this.nombre_testigo2 = nombre_testigo2;
	}
	public String getIfe_testigo2() {
		return ife_testigo2;
	}
	public void setIfe_testigo2(String ife_testigo2) {
		this.ife_testigo2 = ife_testigo2;
	}
	public String getDesignado_por2() {
		return designado_por2;
	}
	public void setDesignado_por2(String designado_por2) {
		this.designado_por2 = designado_por2;
	}
	public String getUso_catalogo() {
		return uso_catalogo;
	}
	public void setUso_catalogo(String uso_catalogo) {
		this.uso_catalogo = uso_catalogo;
	}
	public String getHechos() {
		return hechos;
	}
	public void setHechos(String hechos) {
		this.hechos = hechos;
	}
	public String getInfracciones() {
		return infracciones;
	}
	public void setInfracciones(String infracciones) {
		this.infracciones = infracciones;
	}
	public String getId_c_infraccion() {
		return id_c_infraccion;
	}
	public void setId_c_infraccion(String id_c_infraccion) {
		this.id_c_infraccion = id_c_infraccion;
	}
	public String getUso_suelo() {
		return uso_suelo;
	}
	public void setUso_suelo(String uso_suelo) {
		this.uso_suelo = uso_suelo;
	}
	public String getDensidad() {
		return densidad;
	}
	public void setDensidad(String densidad) {
		this.densidad = densidad;
	}
	public String getManifiesta() {
		return manifiesta;
	}
	public void setManifiesta(String manifiesta) {
		this.manifiesta = manifiesta;
	}
	public String getFecha_plazo() {
		return fecha_plazo;
	}
	public void setFecha_plazo(String fecha_plazo) {
		this.fecha_plazo = fecha_plazo;
	}
	public String getHora_termino() {
		return hora_termino;
	}
	public void setHora_termino(String hora_termino) {
		this.hora_termino = hora_termino;
	}
	public String getTipo_visita() {
		return tipo_visita;
	}
	public void setTipo_visita(String tipo_visita) {
		this.tipo_visita = tipo_visita;
	}
	public String getPago() {
		return pago;
	}
	public void setPago(String pago) {
		this.pago = pago;
	}
	public String getFecha_pago() {
		return fecha_pago;
	}
	public void setFecha_pago(String fecha_pago) {
		this.fecha_pago = fecha_pago;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getCondominio() {
		return condominio;
	}
	public void setCondominio(String condominio) {
		this.condominio = condominio;
	}
	public String getManzana() {
		return manzana;
	}
	public void setManzana(String manzana) {
		this.manzana = manzana;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getCapturo() {
		return capturo;
	}
	public void setCapturo(String capturo) {
		this.capturo = capturo;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public String getPeticion() {
		return peticion;
	}
	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}
	public String getV_firma() {
		return v_firma;
	}
	public void setV_firma(String v_firma) {
		this.v_firma = v_firma;
	}
	public String getMotivo_orden() {
		return motivo_orden;
	}
	public void setMotivo_orden(String motivo_orden) {
		this.motivo_orden = motivo_orden;
	}
	public String getMedida_seguridad() {
		return medida_seguridad;
	}
	public void setMedida_seguridad(String medida_seguridad) {
		this.medida_seguridad = medida_seguridad;
	}
	public String getArticulo_medida() {
		return articulo_medida;
	}
	public void setArticulo_medida(String articulo_medida) {
		this.articulo_medida = articulo_medida;
	}
	public int getId_c_inspector3() {
		return id_c_inspector3;
	}
	public void setId_c_inspector3(int id_c_inspector3) {
		this.id_c_inspector3 = id_c_inspector3;
	}
	public int getId_c_inspector4() {
		return id_c_inspector4;
	}
	public void setId_c_inspector4(int id_c_inspector4) {
		this.id_c_inspector4 = id_c_inspector4;
	}
	public int getId_c_inspector5() {
		return id_c_inspector5;
	}
	public void setId_c_inspector5(int id_c_inspector5) {
		this.id_c_inspector5 = id_c_inspector5;
	}
	public int getId_c_inspector6() {
		return id_c_inspector6;
	}
	public void setId_c_inspector6(int id_c_inspector6) {
		this.id_c_inspector6 = id_c_inspector6;
	}
	public String getL_construccion() {
		return l_construccion;
	}
	public void setL_construccion(String l_construccion) {
		this.l_construccion = l_construccion;
	}
	public int getAxo_licencia() {
		return axo_licencia;
	}
	public void setAxo_licencia(int axo_licencia) {
		this.axo_licencia = axo_licencia;
	}
	public String getLicencia_giro() {
		return licencia_giro;
	}
	public void setLicencia_giro(String licencia_giro) {
		this.licencia_giro = licencia_giro;
	}
	public String getActividad_giro() {
		return actividad_giro;
	}
	public void setActividad_giro(String actividad_giro) {
		this.actividad_giro = actividad_giro;
	}

	public String getNombre_comercial() {
		return nombre_comercial;
	}

	public void setNombre_comercial(String nombre_comercial) {
		this.nombre_comercial = nombre_comercial;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
	
	
}
