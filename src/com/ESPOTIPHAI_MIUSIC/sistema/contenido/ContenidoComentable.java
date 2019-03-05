package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import javax.net.ssl.SSLEngineResult.Status;

public abstract class ContenidoComentable extends Contenido {
	private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
	
	
	
	public Status comentario() {
		return Status.OK;
	}



	/**
	 * @return the comentarios
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}


}

