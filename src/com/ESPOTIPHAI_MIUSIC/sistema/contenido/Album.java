package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;

/**
 *	Clase Album
 */
public class Album extends Contenido {
	private ArrayList<Cancion> contenido = new ArrayList<Cancion>();

	
	/**
	 *	Funcion para calcular el tiempo que dura el album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status calcularTiempo() {
		return Status.OK;
	}
	
	/**
	 *	Funcion para anyadir un contenido al album
	 *	@param contenido  contenido a anyadir al album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirContenido(Cancion contenido) {
		if (this.contenido.add(contenido)) {
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}
	
	/**
	 *	Funcion para eliminar un contenido de el album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido() {
		return Status.OK;
	}
}
