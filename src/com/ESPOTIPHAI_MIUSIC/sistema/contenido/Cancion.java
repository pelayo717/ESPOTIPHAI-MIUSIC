package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.io.FileNotFoundException;
import java.util.*;


import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;


import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3InvalidFileException;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 *	Clase Cancion con herencia de ContenidoComentable
 */


public class Cancion extends ContenidoComentable {
	private EstadoCancion estado;
	private String nombreMP3; 
	private int num_reprod;

	
	/**
	 *	Constructor de Cancion
	 *	@param estado  estado de la cancion
	 *	@param reproducible  si la cacion es o no reproducible
	 */
	public Cancion(Date anyo, String titulo, Usuario autor,  String nombreMP3){
		super(anyo, titulo, autor, new ArrayList<Comentario>());
		this.setNum_reprod(num_reprod);
		this.setDuracion(this.devolverDuracion(nombreMP3));
		this.setNombreMP3(nombreMP3);
		this.setEstado(EstadoCancion.PendienteAprobacion);
	}
	
	/**
	 *	Funcion para saber si es tipo MP3 o no
	 *	@param cancion string de la cancion
	 *	return true si es de tipo MP3 y false de lo contrario
	 */
	public boolean esMP3(String cancion) {
		if(Mp3Player.isValidMp3File(cancion) == true) {
			return true;
		}
		return false;
	}
	
	/**
	 *	Funcion de MP3 para devolver la duracion de la cancion
	 *	@param cancion string de la cancion
	 *	return double de la duracion de la cancion
	 */
	public double devolverDuracion(String cancion) {
		try {
			double duracion = Mp3Player.getDuration(cancion);
			return duracion;
		}catch(FileNotFoundException fe) {
			System.out.println("Error file not found");
			fe.toString();
			return -1.0;
		}
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
	 * Getter de numero de reproducciones
	 * @return num_reprod numero de reproducciones de la cancion
	 */
	public int getNum_reprod() {
		return num_reprod;
	}

	/**
	 * Setter del numero de reproducciones
	 * @param num_reprod el numero de reproducciones de la cacion
	 */
	public void setNum_reprod(int num_reprod) {
		this.num_reprod = num_reprod;
	}
	
	/**
	 * Getter deL nombre del MP3
	 * @return nombreMP3 nombre de la cancion
	 */
	public String getNombreMP3() {
		return nombreMP3;
	}

	/**
	 * Setter del nombre del MP3
	 * @param nombreMP3 el nombre de la cacion
	 */
	public void setNombreMP3(String nombreMP3) {
		this.nombreMP3 = nombreMP3;
	}
	
	

}
