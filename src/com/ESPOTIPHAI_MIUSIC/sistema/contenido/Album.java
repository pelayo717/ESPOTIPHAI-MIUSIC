package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

public class Album extends Contenido {
	private ArrayList<Cancion> contenido = new ArrayList<Cancion>();

	
	
	public Status calcularTiempo() {
		return Status.OK;
	}
	
	public Status añadirContenido(Cancion contenido) {
		if (this.contenido.add(contenido)) {
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}
	
	public Status eliminarContenido() {
		return Status.OK;
	}
}
