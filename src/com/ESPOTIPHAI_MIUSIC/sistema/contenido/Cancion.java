package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;

/**
 *	Clase Cancion
 */
public class Cancion {
	private EstadoCancion estado;
	private Boolean reproducible;
	private Integer num_reprod;
	
	/**
	 *	Constructor de Cancion
	 *	@param estado  estado de la cancion
	 *	@param reproducible  si la cacion es o no reproducible
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Cancion(EstadoCancion estado,Boolean reproducible ) {
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
		return Status.OK;
	}
	
	/**
	 *	Funcion de cancionRechazada
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status cancionRechazada() {
		return Status.OK;
	}
	
	/**
	 *	Funcion de cancionCorregida
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	Status cancionCorregida() {
		return Status.OK;
	}

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
