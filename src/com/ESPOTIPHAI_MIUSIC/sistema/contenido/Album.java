package com.ESPOTIPHAI_MIUSIC.sistema.contenido;


import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase Album con herencia de ContenidoComentable
 */
public class Album extends ContenidoComentable {
	private ArrayList<Cancion> contenido = new ArrayList<Cancion>();

	/**
	 *	Constructor de Album con herencia de ContenidoComentable
	 *	@param contenido  canciones del album
	 */
	public Album (Date anyo, String titulo,  Usuario autor, ArrayList<Cancion> contenido) {
		super(anyo, titulo, autor, new ArrayList<Comentario>());
		this.setContenido(contenido);
		this.setDuracion(this.calcularTiempo());
		
	}
	/**
	 *	Funcion para calcular el tiempo que dura el album
	 * 	@return  duracion la suma de las duraciones de sus contenidos
	 */
	public double calcularTiempo() {
		double duracion = 0;
		for(Cancion cancion: contenido) {
			duracion += cancion.getDuracion();
		}
		return duracion;
	}
	
	/**
	 *	Funcion para anyadir un contenido al album
	 *	@param contenido  contenido a anyadir al album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirContenido(Cancion contenido) {
		if(this.contenido.contains(contenido)) {
			return Status.ERROR;
		} else {
			if (this.contenido.add(contenido)) {
				return Status.OK;
			} else {
				return Status.ERROR;
			}
		}
	}
	
	/**
	 *	Funcion para eliminar un contenido de el album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido(Cancion contenido) {
		if(this.contenido.contains(contenido)) {
			if(this.contenido.remove(contenido)) {
				return Status.OK;
			} else {
				return Status.ERROR;
			}
		} else {
			return Status.OK;
		}
	}
	
	
	
	
	//GETTERS Y SETTERS
	/**
	 *	Setter de contenido de la lista
	 * 	@param  contenido ArrayList del contenido de la lista
	 */
	public void setContenido(ArrayList<Cancion> contenido) {
		if (contenido == null) {
			this.contenido = new ArrayList<Cancion>();
		} else {
			this.contenido = contenido;
		}
	}
	

	/**
	 *	Getter de contenido de la lista
	 * 	@return  un ArrayList del contenido de la lista
	 */
	public ArrayList<Cancion> getContenido() {
		return contenido;
	}
	

	
	
}
