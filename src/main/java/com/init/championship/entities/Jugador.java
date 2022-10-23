package com.init.championship.entities;


public class Jugador {
	
	private String nombre;
	
	private String sexo;
		
	private int habilidad;
	
	private int fuerza;
	
	private int vel_desplazamiento;
	
	private int tiempo_reaccion;
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(int habilidad) {
		this.habilidad = habilidad;
	}

	public int getFuerza() {
		return fuerza;
	}

	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	public int getVel_desplazamiento() {
		return vel_desplazamiento;
	}

	public void setVel_desplazamiento(int vel_desplazamiento) {
		this.vel_desplazamiento = vel_desplazamiento;
	}

	public int getTiempo_reaccion() {
		return tiempo_reaccion;
	}

	public void setTiempo_reaccion(int tiempo_reaccion) {
		this.tiempo_reaccion = tiempo_reaccion;
	}

}
