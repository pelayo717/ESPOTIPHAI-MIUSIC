package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

public class Lista extends Contenido{
	
	private ArrayList<Contenido> contenido = new ArrayList<Contenido>();
	
	public Lista() {
		
	}


	/**
	 * @return the contenido
	 */
	public ArrayList<Contenido> getContenido() {
		return contenido;
	}
	

	public Status calcularTiempo() {
		return Status.OK;
	}
	
	public Status añadirContenido(Contenido contenido) {
		if (this.contenido.add(contenido)) {
			this.contenido.
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}
	
	public Status eliminarContenido() {
		return Status.OK;
	}


}
