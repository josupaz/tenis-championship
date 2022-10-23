package com.init.championship.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="torneo")
public class Torneo {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="sexo", nullable=false)
	private String  sexo;
	
	@Column(name="fecha", nullable=false)
	private Date fecha;

	@Column(name="ganador", nullable=false)
	private String ganador;

	@Column(name="cant_jugadores", nullable=false)
	private int cant_jugadores;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}
	
	public int getCant_jugadores() {
		return cant_jugadores;
	}

	public void setCant_jugadores(int cant_jugadores) {
		this.cant_jugadores = cant_jugadores;
	}

	public String toString(){
		StringBuffer sbf = new StringBuffer("Resultado Torneo!");
		sbf.append("\nId del torneo: " + id);
		sbf.append("\nCantidad de jugadores: " + cant_jugadores);
		sbf.append("\nFecha: " + fecha);
		sbf.append("\nTipo de torneo: " + sexo);
		sbf.append("\nGanador/a: " + ganador);
		

		return sbf.toString();
		
	}
	
}
