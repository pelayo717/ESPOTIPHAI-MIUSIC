package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.util.*;

import com.ESPOTIPHAI_MUSIC.sistema.usuario.Usuario;

/**
 *	Clase Contenido
 */
public abstract class Contenido  {
	
	private Date Anyo;
	private String titulo;
	private Integer duracion;
	private Integer id;
	private Usuario autor;
	
	
	
	/**
	 *	Getter de anyo
	 * 	@return  anyo de el contenido (Date)
	 */
	public Date getAnyo() {
		return Anyo;
	}
	
	
	/**
	 *	Setter de anyo
	 *	@param anyo (Date) anyo del contenido
	 */
	public void setAnyo(Date anyo) {
		Anyo = anyo;
	}


	/**
	 *	Getter de titullo
	 * 	@return  titulo del Contenido (String)
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 *	Setter del titulo
	 *	@param titulo del contenido (String)
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 *	Getter de duracion
	 * 	@return duracion del Contenido (Integer)
	 */
	public Integer getDuracion() {
		return duracion;
	}


	/**
	 *	Setter de duraccion
	 *	@param duracion del contenido (Integer)
	 */
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}


	/**
	 *	Getter de id
	 * 	@return  id del Contenido (Integer)
	 */
	public Integer getId() {
		return id;
	}


	/**
	 *	Setter de Id
	 *	@param id del contenido (Integer)
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
