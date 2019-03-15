package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import javax.net.ssl.SSLEngineResult.Status;

/**
 *	Clase ContenidoComentable con herencia de Contenido
 */
public abstract class ContenidoComentable extends Contenido {
	private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
	
	
	/**
	 *	Funcion para añadir un comentario al contenido comentable
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status comentario() {
		return Status.OK;
	}



	/**
	 *	Getter de comentarios del contenido omentable
	 * 	@return  un ArrayList de los comentarios que tiene el contenido comentable
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}


}

