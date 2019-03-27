package com.ESPOTIPHAI_MIUSIC.sistema;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.EstadoCancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Lista;
import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.enumeracionBloqueado;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3InvalidFileException;
import pads.musicPlayer.exceptions.Mp3PlayerException;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

/**
 * La clase sistema es la encargada de trabajar con objetos de la clase Contenido que permite reproducirlos, pararlos asi como comentarlos y otras m�ltiples funcionalidades. Por otra parte
 * esta clase es la encargada de gestionar los usuarios. Permite iniciar sesi�n y registrarse a todos los que no lo hicieron y les proporciona diferentes actividades dependiendo del tipo de usuario
 * que sea cada uno de este modo, aquellos interesados podran disponer de una versi�n mejorada pagando de manera menusal o obtenerla mediante unos requisitos.
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
	private int max_reproducciones_usuarios_no_premium = 8; //Recordemos que son los premium los unicos que pueden reproducir de manera indefinida
	
	
	public static Sistema getSistema() throws FileNotFoundException, Mp3PlayerException {
		if(sistema == null) {
			sistema = new Sistema();
		}
		return sistema;
	}
	
	/**
     * Constructor de la clase.
     * @param numeroItems El parametro numeroItems define el numero de elementos que va a tener la serie aleatoria
     */
	
	public Sistema() throws FileNotFoundException, Mp3PlayerException {
		File archivo = new File("datos.temp");
		
		if (!archivo.exists()) {
			sistema.usuarios_totales = new ArrayList<Usuario>();
			sistema.canciones_totales = new ArrayList<Cancion>();
			sistema.albumes_totales = new ArrayList<Album>();
			sistema.administrador = null;
			sistema.usuario_actual = null;
			sistema.usuarios_totales.add(new Usuario("root1967","ADMINISTRADOR",null,"ADMINISTRADOR",-1));
			
		}else {
			cargarDatosGenerales();
		}
		
		this.empeorarCuentaPrincipal();
		this.desbloquearUsuario();
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
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getNombreAutor().equals(nombre_autor) == true) {
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
			if(usuario.getNombreUsuario().equals("root1967") == true && usuario.getContrasena().equals("ADMINISTRADOR")) {
				sistema.administrador = usuario;
				return true;
			}
			
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia) == true) {
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
	
	//TARJETA FALTA
	public boolean mejorarCuentaPago(String numero_tarjeta){
		try {
			TeleChargeAndPaySystem.charge(numero_tarjeta, "Mejora de la cuenta a estado PREMIUM", sistema.getPrecioPremium());
			sistema.usuario_actual.mejorarCuentaPorPago();
			return true;
		}catch(FailedInternetConnectionException fe) {
			fe = new FailedInternetConnectionException(numero_tarjeta);
			fe.toString();
			return false;
		}catch(InvalidCardNumberException ie) {
			ie = new InvalidCardNumberException(numero_tarjeta);
			ie.toString();
			return false;
		}catch(OrderRejectedException re) {
			re.toString();
			return false;
		}
	}
	
	
	//-----------------------CONTENIDO-----------------------------------
	
	//**BUSQUEDA SIEMPRE BUSCAMOS POR UN STRING
	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<Cancion> buscadorPorTitulos(String palabra) {
		
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
	public ArrayList<Album> buscadorPorAlbumes(String palabra){
	
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
	
	public ArrayList<Cancion> buscadorPorAutores_DevolvemosCanciones(String palabra){
		
		int ide = 0;
		ArrayList<Cancion> lista_autor_canciones = new ArrayList<Cancion>();
		
		if(palabra == null) {
			return null;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreAutor().equals(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		if(ide == 0) {
			return null;
		}
		
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getAutor().getId() == ide) {
				lista_autor_canciones.add(cancion);
			}
		}
		
		return lista_autor_canciones;
	}
	
	public ArrayList<Album> buscadorPorAutores_DevolvemosAlbumes(String palabra){
		
		int ide = 0;
		ArrayList<Album> lista_autor_albumes = new ArrayList<Album>();
		
		if(palabra == null) {
			return null;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreAutor().equals(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		if(ide == 0) {
			return null;
		}
		
		for(Album album: sistema.albumes_totales) {
			if(album.getAutor().getId() == ide) {
				lista_autor_albumes.add(album);
			}
		}
		
		return lista_autor_albumes;
	}
	
	public ArrayList<Contenido> buscador_general(String palabra,int criterio) {
		
		if(palabra == null) {
			return null;
		}
		ArrayList<Contenido> retorno = new ArrayList<Contenido>();
		switch(criterio) {
			case(1)://TITULO
				ArrayList<Cancion> canciones_buscadas = sistema.buscadorPorTitulos(palabra);
				for(Cancion cancion:canciones_buscadas) {
					retorno.add(cancion);
				}
				break;
			
			case(2)://ALBUM
				ArrayList<Album> albumes_buscados = sistema.buscadorPorAlbumes(palabra);
				for(Album album: albumes_buscados) {
					retorno.add(album);
				}
				break;
			
			case(3)://USUARIO
				ArrayList<Cancion> canciones_autor = sistema.buscadorPorAutores_DevolvemosCanciones(palabra);
				ArrayList<Album> albumes_autor = sistema.buscadorPorAutores_DevolvemosAlbumes(palabra);
				for(Cancion cancion:canciones_autor) {
					retorno.add(cancion);
				}
				for(Album album: albumes_autor) {
					retorno.add(album);
				}
				break;
		}
		
		return retorno;
	}
	
	//---------------------CONTENIDO------------------------------------

	//**SUBIR_CANCION
	public Status crearCancion(Date anyo,String titulo,String nombreMP3) throws FileNotFoundException, Mp3PlayerException{
		boolean cancion_repetida_en_usuario = false; 
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			Cancion c = new Cancion(anyo,titulo,sistema.usuario_actual,nombreMP3);
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(titulo) == true && cancion.getNombreMP3().equals(nombreMP3) == true) {
					cancion_repetida_en_usuario = true;
				}
			}
			if(cancion_repetida_en_usuario == true) {
				return Status.ERROR;
			}else {
				sistema.usuario_actual.aniadirCancionPersonal(c);
				return Status.OK;
			}
		}else {
			return Status.ERROR;
		}
		
	}
	
	//**BORRAR_CANCION UN USUARIO BORRA LAS SUYAS CLARO ESTA
	public void eliminarCancion(Cancion cancion_eliminar) {
		boolean existe_cancion_en_usuario = false;
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(cancion_eliminar.getTitulo()) == true && cancion.getNombreMP3().equals(cancion_eliminar.getNombreMP3()) == true) {
					existe_cancion_en_usuario = true;
				}
			}
			if(existe_cancion_en_usuario == true) { //SE PUEDE ELIMINAR DEL ARRAY DE CANCIONES PERSONALES
				sistema.usuario_actual.eliminarDeCancionesPersonales(cancion_eliminar);
				return;
			}
		}else {
			return;
		}
	}
	
	//**CREAR_ALBUM
	public Status crearAlbum(Date anyo,String titulo,int id,ArrayList<Cancion> contenido) {
		boolean album_repetido_en_usuario = false; 
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			Album a = new Album(anyo,titulo,0,sistema.usuario_actual,new ArrayList<Cancion>());
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(titulo)== true) {
					album_repetido_en_usuario = true;
				}
			}
			
			if(album_repetido_en_usuario  == true) {
				return Status.ERROR;
			}else {
				sistema.usuario_actual.aniadirAlbumPersonal(a);
				sistema.albumes_totales.add(a);
				return Status.OK;
			}
		}else {
			return Status.ERROR;
		}
	
	}
	
	//**BORRAR_ALBUM
	public void eliminarAlbum(Album album_eliminar) {
		boolean existe_album_en_usuario = false;
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(album_eliminar.getTitulo()) == true) {
					existe_album_en_usuario = true;
				}
			}
			
			if(existe_album_en_usuario == true) { //SE PUEDE ELIMINAR DEL ARRAY DE CANCIONES PERSONALES
				sistema.usuario_actual.eliminarDeAlbumesPersonales(album_eliminar);
				return;
			}else {
				return;
			}
		}else {
			return;
		}
	}
	
	//**ANIADIR_A_ALBUM
	public Status aniadirAAlbum(Album a, Cancion c) {
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			if(c.getEstado() == EstadoCancion.Valida || c.getEstado() == EstadoCancion.Explicita) {
			if(sistema.usuario_actual.getCanciones().contains(c) == true) {
				if(sistema.usuario_actual.getAlbumes().contains(a) == true) {
					ArrayList<Cancion> canciones_en_albumes = a.getContenido();
					if(canciones_en_albumes.contains(c) != true) { //NO ESTA EN EL ALBUM Y SE PROCEDE A AÑADIR
						a.anyadirContenido(c);
						/*if(sistema.albumes_totales.contains(a) != true) {
							sistema.albumes_totales.add(a);
						}*/
						return Status.OK;
		
					}else {
						return Status.ERROR;
					}
				}else {
					return Status.ERROR;
				}
			}else {
				return Status.ERROR;
			}
			}else {
				return Status.ERROR;
			}
		}else {
			return Status.ERROR;
		}
	}
	
	//**QUITAR_DE_ALBUM
	public void quitarDeAlbum(Album a, Cancion c) {
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			if(c.getEstado() == EstadoCancion.Valida || c.getEstado() == EstadoCancion.Explicita) {
				if(sistema.usuario_actual.getCanciones().contains(c) == true) {
					if(sistema.usuario_actual.getAlbumes().contains(a) == true) {
						ArrayList<Cancion> canciones_en_albumes = a.getContenido();
							if(canciones_en_albumes.contains(c) == true) { //NO ESTA EN EL ALBUM Y SE PROCEDE A AÑADIR
								a.eliminarContenido(c);
								return;
							}else {
								return;
							}
					}else{
						return;
					}
				}else {
					return;
				}
			}else {
				return;
			}
		}else {
			return;
		}
	}
	
	//**CREAR_LISTA
	public Status crearLista(Date anyo, String titulo,int id, Usuario autor, ArrayList<Contenido> contenido) {
		
		boolean lista_repetida_en_usuario = false; 
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			Lista l = new Lista(anyo,titulo,sistema.usuario_actual,new ArrayList<Contenido>());
			for(Lista lista:sistema.usuario_actual.getListas()) {
				if(lista.getTitulo().equals(titulo) == true) {
					lista_repetida_en_usuario = true;
				}
			}
			
			if(lista_repetida_en_usuario == true) {
				return Status.ERROR;
			}else {
				sistema.usuario_actual.aniadirListaPersonal(l);
				return Status.OK;
			}
		}else {
			return Status.ERROR;
		}
	}
	
	//**BORAR_LISTA
	public void eliminarLista(Lista lista_eliminar) {
		boolean existe_lista_en_usuario = false;
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			for(Lista lista:sistema.usuario_actual.getListas()) {
				if(lista.getTitulo().equals(lista_eliminar.getTitulo()) == true) {
					existe_lista_en_usuario = true;
				}
			}
			
			if(existe_lista_en_usuario == true) { //SE PUEDE ELIMINAR DEL ARRAY DE CANCIONES PERSONALES
				sistema.usuario_actual.eliminarDeListasPersonales(lista_eliminar);
				return;
			}
		}else {
			return;
		}
	}
	
	//**ANIADIR_A_LISTA
	public Status aniadirALista(Lista l, Contenido c) {
		return Status.ERROR;
	}
	//**QUITAR_DE_LISTA
	public void quitarDeLista(Lista l,Contenido c) {
		return;
	}
	
	//---------------------ADMINISTRADOR------------------------------------
	
	//**EMPEORAR_CUENTA
	public void empeorarCuentaPrincipal() {

		LocalDate fecha_actual= LocalDate.now();
		for(Usuario usuario:sistema.usuarios_totales) {
			LocalDate fecha_inicio_premium = usuario.getFechaInicioPro();
			if(fecha_actual.minusDays(15).equals(fecha_inicio_premium) == true)  {
				usuario.emperorarCuenta();
			}
			
		}
		return;
	}
	
	//**BLOQUEAR USUARIO CON NOTIFICACIONES
	public void bloquearUsuarioTotalmente() {

		
	}
	
	public void bloquearUsuarioTemporalmente() {


		
	}
	
	//**DESBLOQUEAR USUARIO TERMINADO
	public boolean desbloquearUsuario() {
		LocalDate fecha_actual = LocalDate.now();
		for(Usuario usuario: this.sistema.usuarios_totales) {
			LocalDate fecha_inicio_bloqueado = usuario.getFechaInicioBloqueado();
			if(fecha_actual.minusDays(30).equals(fecha_inicio_bloqueado) == true && usuario.getEstadoBloqueado() == enumeracionBloqueado.INDEFINIDO.TEMPORAL) {
				usuario.desbloquearCuenta();
			}
		}
		
		return true;
	}
	
	//**GUARDAR DATOS GENERALES TERMINADO
	public boolean guardarDatosGenerales() {
		try {
			FileOutputStream fileOut=new FileOutputStream("datos.temp");
			ObjectOutputStream oos =new ObjectOutputStream(fileOut);
			oos.writeObject(this.sistema);
			oos.close();
			return true;
		}catch(IOException ie) {
			System.out.println("Error interrupted I/O operations");
			ie.toString();
			return false;
		}
	}
	
	//**CARGAR DATOS GENERALES  TERMINADO
	public void cargarDatosGenerales(){
		try {
			FileInputStream in = new FileInputStream("datos.temp");
			ObjectInputStream oin = new ObjectInputStream(in);
			Sistema sistema_retornado = (Sistema) oin.readObject();
			Sistema.sistema.administrador = sistema_retornado.administrador;
			Sistema.sistema.usuario_actual = sistema_retornado.usuario_actual;
			Sistema.sistema.usuarios_totales = sistema_retornado.usuarios_totales;
			Sistema.sistema.precio_premium = sistema_retornado.precio_premium;
			Sistema.sistema.canciones_totales = sistema_retornado.canciones_totales;
			Sistema.sistema.umbral_reproducciones = sistema_retornado.umbral_reproducciones;
			Sistema.sistema.albumes_totales = sistema_retornado.albumes_totales;
		}catch(IOException ie) {
			System.out.println("Error interrupted I/0 operations");
			ie.toString();
			return;
		}catch(ClassNotFoundException ce) {
			System.out.println("Error class not found");
			ce.toString();
			return;
		}
	}
	
	
	
	//**----------REPRODUCCIONES__CONCRETAS----------------
	
	public void reproducirCancion(Cancion c) {
		c.reproducir();
	}
	
	public void reproducirAlbum_Premium_Aleatoria(Album a) {
		int x=0;
		ArrayList<int[]> valores_aleatorios= new ArrayList<int[]>();
		if(sistema.usuario_actual.getPremium() == true) {
			ArrayList<Cancion> canciones_en_album = (ArrayList<Cancion>) a.getContenido();
			for(x=0; x < canciones_en_album.size(); x++) {
				int value = (int) (Math.random() * canciones_en_album.size());
			
				
			}
		}
		return;
	}
	
	public void reproducirAlbum_Premium_Todas(Album a) {
		if(sistema.usuario_actual.getPremium() == true) {
			ArrayList<Cancion> canciones_en_album = (ArrayList<Cancion>) a.getContenido();
			for(Cancion cancion:canciones_en_album) {
				cancion.aniadirCola();
			}
			for(Cancion cancion:canciones_en_album) {
				cancion.reproducir();
				//HILO DE DORMIR
			}
		}
	}
	
	/*public void reproducirAlbum_NoPremium(Album a, ) {
		
	}*/
	
	
	//**-------------REPRODUCCIONES__GENERALES------------
	
	
	
	//**PARAR PARA USUARIOS DISTINTOS
	
		//**VALIDAR_CANCION
		
		//** + FUNCIONES DE ADMINISTRADOR

	
	/*public static void main(String args[]) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		Date d = new Date();
		Cancion c = new Cancion(d,"chicle",1,null,"chicle3.mp3");
		
		System.out.println(c.getNombreMP3() + "sss");
		c.anyadirCola(c.getNombreMP3());
		c.reproducirCancion();
		Thread.sleep(10000);
	}*/
	
}



