package com.ESPOTIPHAI_MIUSIC.sistema;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3InvalidFileException;
import pads.musicPlayer.exceptions.Mp3PlayerException;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.time.LocalDate;

/**
 * La clase sistema es la encargada de trabajar con objetos de la clase Contenido que permite reproducirlos, pararlos asi como comentarlos y otras múltiples funcionalidades. Por otra parte
 * esta clase es la encargada de gestionar los usuarios. Permite iniciar sesión y registrarse a todos los que no lo hicieron y les proporciona diferentes actividades dependiendo del tipo de usuario
 * que sea cada uno de este modo, aquellos interesados podran disponer de una versión mejorada pagando de manera menusal o obtenerla mediante unos requisitos.
 * @author: Pelayo Rodriguez Aviles
 * @version: 24/03/2019
 */

public class Sistema implements java.io.Serializable{

	
	private ArrayList<Usuario> usuarios_totales;
	private ArrayList<Cancion> canciones_totales;
	private ArrayList<Album> albumes_totales;
	private Usuario administrador;
	private Usuario usuario_actual;
	private static Sistema sistema = null;
	private int umbral_reproducciones=30;
	private double precio_premium=9.99;
	private Mp3Player repro_mp3;
	
	
	public static Sistema getSistema() throws FileNotFoundException, Mp3PlayerException {
		if(sistema == null) {
			sistema = new Sistema();
		}
		return sistema;
	}
	
	/**
     * Constructor de la clase.
     * @param numeroItems El parámetro numeroItems define el número de elementos que va a tener la serie aleatoria
     */
	
	public Sistema() throws FileNotFoundException, Mp3PlayerException {
		sistema.usuarios_totales = new ArrayList<Usuario>();
		sistema.canciones_totales = new ArrayList<Cancion>();
		sistema.albumes_totales = new ArrayList<Album>();
		sistema.administrador = null;
		sistema.usuario_actual = null;
		repro_mp3 = new Mp3Player();
	}
	//----------------SETTERS------------------//
	
	public void setUmbralReproducciones(int umbral) {
		sistema.umbral_reproducciones = umbral;
	}
	
	public void setPrecioPremium(double precio) {
		sistema.precio_premium = precio;
	}
	
	//----------------GETTERS------------------//
	
	public ArrayList<Usuario> getUsuariosTotales(){
		return sistema.usuarios_totales;
	}
	
	public ArrayList<Cancion> getCancionTotales(){
		return sistema.canciones_totales;
	}
	
	public ArrayList<Album> getAlbumTotales(){
		return sistema.albumes_totales;
	}
	
	public Usuario getAdministrador() {
		return (Usuario) sistema.administrador;
	}
	
	public Usuario getUsuarioActual() {
		return (Usuario) sistema.usuario_actual;
	}
	
	public int getUmbralReproducciones() {
		return sistema.umbral_reproducciones;
	}
	
	public double getPrecioPremium() {
		return sistema.precio_premium;
	}
	
	//----------------USUARIO--------------------//
	
	public boolean registrarse(String nombre_usuario,String nombre_autor,LocalDate fecha_nacimiento, String contrasenia) {
		
		int i=0;
		
		if(nombre_usuario == null || contrasenia == null || fecha_nacimiento == null) {
			return false;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombre_usuario().equals(nombre_usuario) == true && usuario.getNombre_autor().equals(nombre_autor) == true) {
				break;
			}
			i++;
		}
		
		if(i < sistema.usuarios_totales.size()) {
			return false;
		}		
		
		Usuario usuario_registrado_nuevo = new Usuario(nombre_usuario,nombre_autor,fecha_nacimiento, contrasenia,sistema.usuarios_totales.size()+1); 
		sistema.usuarios_totales.add(usuario_registrado_nuevo);
		return true;
	}
	
	public boolean iniciarSesion(String nombre_usuario, String contrasenia) {

		if(nombre_usuario == null || contrasenia == null) {
			return false;
		}
			
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombre_usuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia) == true) {
				sistema.usuario_actual = usuario;
				return true;
			}
		}
			
		return false;
	}
	
	public boolean cerrarSesion() {
		if(sistema.usuario_actual != null) {
			sistema.usuario_actual = null;
			return true;
		}
		return false;
	}
	
	//DUDAS PARA EL MARTES
	public boolean mejorarCuentaPago(String numero_tarjeta,String concepto){
		try {
			TeleChargeAndPaySystem.charge(numero_tarjeta, concepto, sistema.getPrecioPremium());
		}catch(FailedInternetConnectionException fe) {
			fe = new FailedInternetConnectionException(numero_tarjeta);
			System.out.println("Error failed internet connection");
			System.out.println(fe.getCardNumberString());
			System.out.println(fe.getCause());
			return false;
		}catch(InvalidCardNumberException ie) {
			ie = new InvalidCardNumberException(numero_tarjeta);
			System.out.println("Error invalid card number");
			System.out.println(ie.getCardNumberString());
			System.out.println(ie.getMessage());
			return false;
		}catch(OrderRejectedException re) {
			re = new OrderRejectedException("Unknown error ",numero_tarjeta);
			System.out.println(re.getCardNumberString());
			System.out.println(re.getMessage());
			return false;
		}
		return false;
	}
	
	
	//-----------------------CONTENIDO-----------------------------------
	
	//**BUSQUEDA
	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<Cancion> buscadorTitulos(String palabra) {
		
		if(palabra == null) {
			return null;
		}
		
		//TITULO ==> BUSCAMOS POR CANCIONES Y PONEMOS UNICAMENTE OBJETOS DE ESTE TIPO
		ArrayList<Cancion> lista_filtrada = new ArrayList<Cancion>();
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getTitulo().contains(palabra) == true || cancion.getTitulo().equals(palabra) == true) {
				lista_filtrada.add(cancion);
			}
		}
		return lista_filtrada;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<Album> buscadorAlbumes(String palabra){
	
		if(palabra == null) {
			return null;
		}
			
		ArrayList<Album> lista_filtrada = new ArrayList<Album>();
		for(Album album: sistema.albumes_totales) {
			if(album.getTitulo().contains(palabra) == true || album.getTitulo().equals(palabra) == true) {
				lista_filtrada.add(album);
			}
		}
		return lista_filtrada;
	}
	
	public ArrayList<Cancion> buscadorAutores_Canciones(String palabra){
		
		int ide = 0;
		ArrayList<Cancion> lista_autor_canciones = new ArrayList<Cancion>();
		
		if(palabra == null) {
			return null;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombre_autor().equals(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		if(ide == 0) {
			return null;
		}
		
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getId() == ide) {
				lista_autor_canciones.add(cancion);
			}
		}
		
		return lista_autor_canciones;
	}
	
	public ArrayList<Album> buscadorAutores_Albumes(String palabra){
		
		int ide = 0;
		ArrayList<Album> lista_autor_albumes = new ArrayList<Album>();
		
		if(palabra == null) {
			return null;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombre_autor().equals(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		if(ide == 0) {
			return null;
		}
		
		for(Album album: sistema.albumes_totales) {
			if(album.getId() == ide) {
				lista_autor_albumes.add(album);
			}
		}
		
		return lista_autor_albumes;
	}
	
	public void buscador_general(String palabra,int criterio) {
		
		if(palabra == null) {
			return;
		}
		
		switch(criterio) {
			case(1)://TITULO
				ArrayList<Cancion> canciones_buscadas = sistema.buscadorTitulos(palabra);
				for(Cancion cancion:canciones_buscadas) {
					cancion.toString();
				}
				break;
			
			case(2)://ALBUM
				ArrayList<Album> albumes_buscados = sistema.buscadorAlbumes(palabra);
				for(Album album: albumes_buscados) {
					album.toString();
				}
				break;
			
			case(3)://USUARIO
				ArrayList<Cancion> canciones_autor = sistema.buscadorTitulos(palabra);
				ArrayList<Album> albumes_autor = sistema.buscadorAlbumes(palabra);
				for(Cancion cancion:canciones_autor) {
					cancion.toString();
				}
				for(Album album: albumes_autor) {
					album.toString();
				}
				break;
		}
		
		return;
	}
	
	//---------------------CONTENIDO------------------------------------
	
	//**AÑADIR A LA COLA DE REPRODUCCION
	public void añadirCola(String cancion_a_aniadir) {
		try {
			sistema.repro_mp3.add(cancion_a_aniadir);
			return;
		}catch(Mp3InvalidFileException ie) {
			System.out.println("Error add file");
			ie.toString();
			return;
		}
	}
	
	//**REPRODUCCION DE CANCION
	public void reproducirCancion() {
		try {
			sistema.repro_mp3.play();
			return;
		}catch(Mp3PlayerException pe) {
			System.out.println("Error play song†");
			pe.toString();
			return;
		}
	}
	
	//**PARAR LA REPRODUCCION
	public void pararCancion() {
		sistema.repro_mp3.stop();
	}
	
	//**COMPROBAR SI ES MP3
	public boolean esMP3(String cancion) {
		if(Mp3Player.isValidMp3File(cancion) == true) {
			return true;
		}
		return false;
	}
	
	//**RETORNAR LA DURACION DE LAS CANCIONES
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
	
	
	//**SUBIR_CANCION
	//**BORRAR_CANCION
	
	//**CREAR_ALBUM
	//**BORRAR_ALBUM
	
	//**CREAR_LISTA
	//**BORAR_LISTA
	
	//---------------------ADMINISTRADOR------------------------------------
	
	//**EMPEORAR_CUENTA
	public void empeorarCuenta_principal() {
		LocalDate fecha_actual= LocalDate.now();
		for(Usuario usuario:sistema.usuarios_totales) {
			LocalDate fecha_inicio_premium = usuario.getFecha_inicio_pro();
			if(fecha_actual.minusDays(15).equals(fecha_inicio_premium) == true)  {
				usuario.emperorarCuenta();
			}
			
		}
		return;
	}
	
	//**BLOQUEAR USUARIO
	public void bloquearUsuario(int id) {
		for(Usuario usuario:sistema.usuarios_totales) {
			if(usuario.getId() == id) {
				//usuario.bloquear();
			}
		}
		return;
	}
	
	//**DESBLOQUEAR USUARIO
	
	//**GUARDAR DATOS Y CARGAR DATOS
	
	//**VALIDAR_CANCION
	
	//**INCLUIR_CANCION_EXPLICITA
	
	//**INCLUIR_CANCION_NO_EXPLICITA

}
	