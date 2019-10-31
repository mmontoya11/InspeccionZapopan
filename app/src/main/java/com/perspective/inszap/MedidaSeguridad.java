package com.perspective.inszap;

public class MedidaSeguridad {
	
	private int medidaSeguridad;
	private String medida,articulo,capturo,fecha;
	
	public MedidaSeguridad() {
		this.medidaSeguridad = 0;
		this.medida = "";
		this.articulo = "";
		this.capturo = "";
		this.fecha = "";
	}
	
	public MedidaSeguridad(int medidaSeguridad,String medida,String articulo,String capturo,String fecha) {
		this.medidaSeguridad = medidaSeguridad;
		this.medida = medida;
		this.articulo = articulo;
		this.capturo = capturo;
		this.fecha = fecha;
	}
	
	public MedidaSeguridad(String medida,String articulo,String capturo,String fecha) {
		this.medida = medida;
		this.articulo = articulo;
		this.capturo = capturo;
		this.fecha = fecha;
	}
	
	public MedidaSeguridad(String medida,String articulo) {
		this.medida = medida;
		this.articulo = articulo;
	}
	
	public int getMedidaSeguridad() {
		return medidaSeguridad;
	}
	public void setMedidaSeguridad(int medidaSeguridad) {
		this.medidaSeguridad = medidaSeguridad;
	}
	public String getMedida() {
		return medida;
	}
	public void setMedida(String medida) {
		this.medida = medida;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}
	public String getCapturo() {
		return capturo;
	}
	public void setCapturo(String capturo) {
		this.capturo = capturo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
