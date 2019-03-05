package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.util.*;

public abstract class Contenido  {
	
	private Date Año;
	private String titulo;
	private Integer duracion;
	private Integer id;
	
	
	
	/**
	 * @return the año
	 */
	public Date getAño() {
		return Año;
	}
	
	
	/**
	 * @param año the año to set
	 */
	public void setAño(Date año) {
		Año = año;
	}


	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 * @return the duracion
	 */
	public Integer getDuracion() {
		return duracion;
	}


	/**
	 * @param duracion the duracion to set
	 */
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
