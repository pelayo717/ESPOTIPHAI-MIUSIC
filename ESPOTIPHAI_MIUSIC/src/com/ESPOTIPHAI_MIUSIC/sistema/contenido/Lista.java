package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase Lista con herencia de Contenido
 */
public class Lista extends Contenido{
	
	private ArrayList<Contenido> contenido = new ArrayList<Contenido>();
	
	public Lista(Date anyo, String titulo,Usuario autor, ArrayList<Contenido> contenido) {
		super(anyo, titulo,autor);
		this.setContenido(contenido);
		this.setDuracion(this.calcularTiempo());

	}



	/**
	 *	Funcion para calcular el tiempo que dura la lista
	 * 	@return  duracion la suma de las duraciones de sus contenidos
	 */
	public double calcularTiempo() {
		double duracion = 0;
		for(Contenido contenido: contenido) {
			duracion += contenido.getDuracion();
		}
		return duracion;
	}
	
	
	/**
	 *	Funcion para anyadir contenido a la lista
	 *	@param contenido  contenido a anyadir a la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirContenido(Contenido contenido) {
		if (this.contenido.add(contenido)) {
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}
	
	/**
	 *	Funcion para eliminar un contenido de la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido(Contenido contenido) {
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
	public void setContenido(ArrayList<Contenido> contenido) {
		if (contenido == null) {
			this.contenido = new ArrayList<Contenido>();
		} else {
			this.contenido = contenido;
		}
	}
	

	/**
	 *	Getter de contenido de la lista
	 * 	@return  un ArrayList del contenido de la lista
	 */
	public ArrayList<Contenido> getContenido() {
		return contenido;
	}
	

}