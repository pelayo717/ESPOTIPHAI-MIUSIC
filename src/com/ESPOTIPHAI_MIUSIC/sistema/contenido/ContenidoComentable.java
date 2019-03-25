package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import javax.net.ssl.SSLEngineResult.Status;

import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase ContenidoComentable con herencia de Contenido
 */
public abstract class ContenidoComentable extends Contenido {
	private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
	
	
	public ContenidoComentable (Date anyo, String titulo, int duracion,  int id, Usuario autor, ArrayList<Comentario> comentarios) {
		super(anyo, titulo, duracion, id, autor);
		this.setComentarios(comentarios);
	}
	
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
	
	
	

	//GETTERS Y SETTERS
	
	
	
	/**
	 * Getter de coemntarios
	 * @return comentarios
	 */
	public ArrayList<Comentario> getComentarios(ArrayList<Comentario> comentarios) {
		return comentarios;
	}

	/**
	 * Setter de fecha
	 * @param fecha del comentario
	 */
	public void setComentarios(ArrayList<Comentario> comentarios) {
		if (comentarios == null){
			this.comentarios = new ArrayList<Comentario>();

		} else {
			this.comentarios = comentarios;
		}
	}
	


}

