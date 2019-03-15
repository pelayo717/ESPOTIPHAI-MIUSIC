package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

/**
 *	Clase Comentario
 */
public class Comentario {
	private Date fecha;
	private String texto;
	
	/**
	 *	Constructor de Comentario
	 *	@param fecha  fecha del comentario (Date)
	 *	@param texto  texto del comentario (String)
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Comentario(Date fecha,String texto) {
		this.fecha = fecha;
		this.texto = texto;
	}
}
