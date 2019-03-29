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
	 */
	public Comentario(Date fecha,String texto) {
		this.setFecha(fecha);
		this.setTexto(texto);
	}
	
	

	
	
	//GETTERS Y SETTERS
	
	
	
	/**
	 * Getter de fecha
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Setter de fecha
	 * @param fecha del comentario
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	/**
	 * Getter de texto
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}


	/**
	 * Setter de texto
	 * @param texto del comentario
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	

	
	
}