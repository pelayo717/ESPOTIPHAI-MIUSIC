
	
	
	package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

	import java.io.FileNotFoundException;
import java.time.LocalDate;
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
		private Mp3Player repro_mp3;
		private boolean es_explicita=false;
		private LocalDate fecha_modificar;
		/**
		 *	Constructor de Cancion
		 *	@param estado  estado de la cancion
		 *	@param reproducible  si la cacion es o no reproducible
		 * @throws Mp3PlayerException 
		 * @throws FileNotFoundException 
		 */
		public Cancion(Date anyo, String titulo, Usuario autor,  String nombreMP3, boolean explicita) throws FileNotFoundException, Mp3PlayerException{
			super(anyo, titulo, autor, new ArrayList<Comentario>());
			this.repro_mp3 = new Mp3Player();
			this.setNombreMP3(nombreMP3);
			this.setDuracion(this.devolverDuracion());
			this.setEstado(EstadoCancion.PendienteAprobacion);
			this.setEsExplicita(explicita);
			this.fecha_modificar = LocalDate.now();
		}
		
		
		
		public boolean getEsExplicita() {
			return this.es_explicita;
		}
		
		public void setEsExplicita(boolean explicita2) {
			this.es_explicita = explicita2;
		}


		/**
		 *	Funcion para anyadir a la cola de reproduccion
		 *	@param cancion_a_anyadir  string de la cancion a anyadir
		 */
		public void aniadirCola() {
			try {
				this.repro_mp3.add(this.nombreMP3);
				return;
			}catch(Mp3InvalidFileException ie) {
				System.out.println("Error add file");
				ie.toString();
				return;
			}
		}
		
		/**
		 *	Funcion para reproducir una cancion
		 */
		public void reproducir() {
			try {
				this.repro_mp3.play();
				return;
			}catch(Mp3PlayerException pe) {
				System.out.println("Error play song");
				pe.toString();
				return;
			}
		}
		
		
		/**
		 *	Funcion para parar una cancion
		 */
		public void parar() {
				this.repro_mp3.stop();
		}
		
		/**
		 *	Funcion para saber si es tipo MP3 o no
		 *	@param cancion string de la cancion
		 *	return true si es de tipo MP3 y false de lo contrario
		 */
		public boolean esMP3() {
			if(Mp3Player.isValidMp3File(this.nombreMP3) == true) {
				return true;
			}
			return false;
		}
		
		/**
		 *	Funcion de MP3 para devolver la duracion de la cancion
		 *	@param cancion string de la cancion
		 *	return double de la duracion de la cancion
		 */
		public double devolverDuracion() {
			try {
				double duracion = Mp3Player.getDuration(this.nombreMP3);
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
		
		public LocalDate getFechaModificacion() {
			return this.fecha_modificar;
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
			if(estado == EstadoCancion.PendienteModificacion) {
				this.fecha_modificar = LocalDate.now();
			}
			this.estado = estado;
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