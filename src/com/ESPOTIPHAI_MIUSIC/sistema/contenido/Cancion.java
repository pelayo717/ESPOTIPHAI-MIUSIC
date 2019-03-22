package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase Cancion con herencia de ContenidoComentable
 */
public class Cancion extends ContenidoComentable {
	private EstadoCancion estado;
	private Boolean reproducible;
	private Integer num_reprod;
	
	/**
	 *	Constructor de Cancion
	 *	@param estado  estado de la cancion
	 *	@param reproducible  si la cacion es o no reproducible
	 */
	public Cancion(Date anyo, String titulo, Integer duracion,  Integer id, Usuario autor, EstadoCancion estado,Boolean reproducible ) {
		super(anyo, titulo, duracion, id, autor, new ArrayList<Comentario>());
		this.setNum_reprod(num_reprod);
		this.setEstado(estado);
		this.setReproducible(reproducible);
	}
	
	/**
	 *	Funcion de reproducir
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status reproducir() {
		return Status.OK;
	}

	/**
	 *	Funcion de reportarPlagio
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status reportarPlagio() {
		return Status.OK;
	}
	
	/**
	 *	Funcion de validarCancion
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status validarCancion() {
		this.setEstado(EstadoCancion.PendienteAprobacion);
		return Status.OK;
	}
	
	/**
	 *	Funcion de cancionRechazada
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status cancionRechazada() {
		this.setEstado(EstadoCancion.PendienteModificacion);
		return Status.OK;
	}
	
	/**
	 *	Funcion de cancionCorregida
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status cancionCorregida() {
		this.setEstado(EstadoCancion.PendienteAprobacion);
		return Status.OK;
	}

	
	
	//GETTERS Y SETTERS
	
	
	/**
	 * Getter de estado
	 * @return the estado
	 */
	public EstadoCancion getEstado() {
		return estado;
	}

	/**
	 * Setter de estado
	 * @param estado estado de la cancion
	 */
	public void setEstado(EstadoCancion estado) {
		this.estado = estado;
	}

	/**
	 * Getter de reproducible
	 * @return reproducible si una cancion es reproducible o no
	 */
	public Boolean getReproducible() {
		return reproducible;
	}

	/**
	 * Setter de reproducible
	 * @param reproducible si es reproducible o no la cancion
	 */
	public void setReproducible(Boolean reproducible) {
		this.reproducible = reproducible;
	}

	/**
	 * Getter de numero de reproducciones
	 * @return num_reprod numero de reproducciones de la cancion
	 */
	public Integer getNum_reprod() {
		return num_reprod;
	}

	/**
	 * Setter del numero de reproducciones
	 * @param num_reprod el numero de reproducciones de la cacion
	 */
	public void setNum_reprod(Integer num_reprod) {
		this.num_reprod = num_reprod;
	}
	
	
	

}
