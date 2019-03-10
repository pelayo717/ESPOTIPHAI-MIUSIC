package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;

public class Album extends Contenido {
	private ArrayList<Cancion> contenido = new ArrayList<Cancion>();

	
	
	public Status calcularTiempo() {
		return Status.OK;
	}
	
	public Status anyadirContenido(Cancion contenido) {
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
