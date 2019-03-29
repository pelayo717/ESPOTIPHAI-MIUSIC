package com.ESPOTIPHAI_MIUSIC.sistema;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Comentario;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Period;

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
	private Usuario usuario_actual;
	private boolean es_administrador;
	private static Sistema sistema = null;

	//Variables de configuracion
	private int umbral_reproducciones;
	private double precio_premium;
	private int max_reproducciones_usuarios_no_premium;
	private int reproducciones_usuarios_no_premium;
	
	//Almacenamiento de la cancion que suena en este momento
	private Cancion cancion_reproduciendose;
	
	public static Sistema getSistema() throws Mp3PlayerException, IOException {
		if(sistema == null) {
			sistema = new Sistema();
		}
		return sistema;
	}
	
	/**
     * Constructor de la clase.
     * @param numeroItems El parametro numeroItems define el numero de elementos que va a tener la serie aleatoria
	 * @throws IOException 
     */
	
	public Sistema() throws Mp3PlayerException, IOException {
		File archivo = new File("datos.temp");
		File archivo_configuracion = new File("configuracion.txt");
		
		if (!archivo.exists()) { //DATOS PREDETERMINADOS		
			sistema.usuarios_totales = new ArrayList<Usuario>();
			sistema.canciones_totales = new ArrayList<Cancion>();
			sistema.albumes_totales = new ArrayList<Album>();
			sistema.es_administrador = false;
			sistema.usuario_actual = null;
			sistema.usuarios_totales.add(new Usuario("root1967","ADMINISTRADOR",LocalDate.of(1968, 12, 27),"ADMINISTRADOR",-1));
			sistema.umbral_reproducciones = 30;
			sistema.precio_premium = 9.9;
			sistema.max_reproducciones_usuarios_no_premium = 8;
			sistema.reproducciones_usuarios_no_premium = 0;
		}else {
			
			this.cargarDatosGenerales();
			
			if(archivo_configuracion.exists() == true) { //Si hay archivo de configuracion se modifican los datos, si desde que se creo el objeto el admin modifico los parametros
				this.leerDatosConfiguracion();
			}
			
			this.empeorarCuentaPrincipal();
			this.desbloquearUsuario();
		}
		
	}
	
	/**
	 * Esta funcion establece el umbral de reproducciones que debe superar un autor para pasar al estado de premium, y solo es aplicable a aquellos usuarios que estan registrados
	 * @param umbral
	 * @return
	 */
	public Status setUmbralReproducciones(int umbral) {
		if(umbral < 0) {
			return Status.ERROR;
		}
		sistema.umbral_reproducciones = umbral;
		return Status.OK;
	}
	
	/**
	 * Esta funcion establece el precio que se cobra a un usuario para hacerse premium
	 * @param precio
	 * @return
	 */
	public Status setPrecioPremium(double precio) {
		if(precio < 0.0) {
			return Status.ERROR;
		}
		sistema.precio_premium = precio;
		return Status.OK;
	}
	
	/**
	 * Esta funcion establece el numero maximo de reproducciones que puede realizar un usuario no registrado o un usuario registrado pero no premium
	 * @param x
	 * @return
	 */
	public Status setMaxReproduccionesUsuarioNoPremium(int x) {
		if(x < 0) {
			return Status.ERROR;
		}
		sistema.max_reproducciones_usuarios_no_premium = x;
		return Status.OK;
	}

	/**
	 * Devuelve todos los usuarios que estan registrados en el sistema
	 * @return
	 */
	public ArrayList<Usuario> getUsuariosTotales(){
		return sistema.usuarios_totales;
	}
	
	/**
	 * Devuelve todas las canciones que estan en el sistema
	 * @return
	 */
	public ArrayList<Cancion> getCancionTotales(){
		return sistema.canciones_totales;
	}
	
	/**
	 * Devuelve todos los albumes que estan en el sistema
	 * @return
	 */
	public ArrayList<Album> getAlbumTotales(){
		return sistema.albumes_totales;
	}
	
	/**
	 * Devuelve el usuario que ha iniciado sesion actualmente
	 * @return
	 */
	public Usuario getUsuarioActual() {
		return (Usuario) sistema.usuario_actual;
	}
	
	/**
	 * Devuelve el umbral de reproducciones que deben superar los usuarios
	 * @return
	 */
	public int getUmbralReproducciones() {
		return sistema.umbral_reproducciones;
	}
	
	/**
	 * Devuelve la cuota que se debe pagar para hacerse premium
	 * @return
	 */
	public double getPrecioPremium() {
		return sistema.precio_premium;
	}
	
	/**
	 * Permite registrarse a un usuario creando un objeto de tipo usuario y almacenandolo en el array de usuarios totales
	 * @param nombre_usuario
	 * @param nombre_autor
	 * @param fecha_nacimiento
	 * @param contrasenia
	 * @return
	 */
	public Status registrarse(String nombre_usuario,String nombre_autor,LocalDate fecha_nacimiento, String contrasenia) {
		
		int i=0;
		
		if(nombre_usuario == null || contrasenia == null || fecha_nacimiento == null) {
			return Status.ERROR;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getNombreAutor().equals(nombre_autor) == true) {
				break;
			}
			i++;
		}
		
		if(i < sistema.usuarios_totales.size()) {
			return Status.ERROR;
		}		
		
		Usuario usuario_registrado_nuevo = new Usuario(nombre_usuario,nombre_autor,fecha_nacimiento, contrasenia,sistema.usuarios_totales.size()+1); 
		sistema.usuarios_totales.add(usuario_registrado_nuevo);
		return Status.OK;
	}
	
	/**
	 * Permite iniciar sesion en la aplicacion, ya sea el administrador o un usuario convencional
	 * @param nombre_usuario
	 * @param contrasenia
	 * @return
	 */
	public Status iniciarSesion(String nombre_usuario, String contrasenia) {

		if(nombre_usuario == null || contrasenia == null) {
			return Status.ERROR;
		}
			
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreUsuario().equals("root1967") == true && usuario.getContrasena().equals("ADMINISTRADOR")) {
				sistema.usuario_actual = usuario;
				sistema.es_administrador = true;
				return Status.OK;
			}
			
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia) == true) {
				sistema.usuario_actual = usuario;
				return Status.OK;
			}
		}
			
		return Status.ERROR;
	}
	
	/**
	 * Comprueba sin un usuario ha iniciado sesion y de ser asi la cierra
	 * @return
	 */
	public Status cerrarSesion() {
		if(sistema.usuario_actual != null) {
			if(this.es_administrador == true) {
				this.es_administrador = false;
				sistema.usuario_actual = null;
				this.guardarDatosGenerales();
			}
			sistema.usuario_actual = null;
			this.guardarDatosGenerales();
			return Status.OK;
		}
		return Status.ERROR;
	}

	/**
	 * Permite mejorar la cuenta de un usuario a premium introduciendo su tarjeta de credito y actualizando sus datos al nuevo estado de PREMIUM
	 * @param numero_tarjeta
	 * @return
	 */
	public Status mejorarCuentaPago(String numero_tarjeta){
		try {
			TeleChargeAndPaySystem.charge(numero_tarjeta, "Mejora de la cuenta a estado PREMIUM", sistema.getPrecioPremium());
			sistema.usuario_actual.mejorarCuentaPorPago();
			return Status.OK;
		}catch(FailedInternetConnectionException fe) {
			fe.toString();
			return Status.ERROR;
		}catch(InvalidCardNumberException ie) {
			ie.toString();
			return Status.ERROR;
		}catch(OrderRejectedException re) {
			re.toString();
			return Status.ERROR;
		}
	}
	
	/**
	 * Permite realizar una busqueda en todas las canciones al introducir una cadena
	 * @param palabra
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<Cancion> buscadorPorTitulos(String palabra) {
		LocalDate fecha_actual = LocalDate.now();
		
		if(palabra == null) {
			return null;
		}
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			if(intervalo.getYears() >= 18) {
				ArrayList<Cancion> canciones_incluido_explicitas = new ArrayList<Cancion>();
				for(Cancion cancion: sistema.canciones_totales) {
					if((cancion.getEstado() == EstadoCancion.Explicita || cancion.getEstado() == EstadoCancion.Valida) && cancion.getTitulo().equals(palabra) == true) {
						canciones_incluido_explicitas.add(cancion);
					}
				}
				
				return canciones_incluido_explicitas;
			}
		}
		
		ArrayList<Cancion> canciones_no_registrado = new ArrayList<Cancion>();
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getEstado() == EstadoCancion.Valida && cancion.getTitulo().equals(palabra) == true) {
				canciones_no_registrado.add(cancion);
			}
		}
		
		return canciones_no_registrado;
		
		
		
	}

	/**
	 * Permite ralizar una busqueda en todos los albumes al introducir una cadena
	 * @param palabra
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<Album> buscadorPorAlbumes(String palabra){
		LocalDate fecha_actual = LocalDate.now();
		ArrayList<Album> albumes_incluidas_explicitas = new ArrayList<Album>();
		
		if(palabra == null) {
			return null;
		}
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			if(intervalo.getYears() >= 18) {
				
				for(Album album_totales:sistema.albumes_totales) {
					if(album_totales.getTitulo().equals(palabra) == true) {
						Album album_filtrado = new Album(album_totales.getAnyo(),album_totales.getTitulo(),album_totales.getAutor(),null);
						for(Cancion canciones_album: album_totales.getContenido()) {
							if(canciones_album.getEstado() == EstadoCancion.Explicita || canciones_album.getEstado() == EstadoCancion.Valida) {
								album_filtrado.anyadirContenido(canciones_album);
							}
						}
						albumes_incluidas_explicitas.add(album_filtrado);
					}
				}
				return albumes_incluidas_explicitas;
			}
		}
		
		for(Album album_totales:sistema.albumes_totales) {
			if(album_totales.getTitulo().equals(palabra) == true) {
				Album album_filtrado = new Album(album_totales.getAnyo(),album_totales.getTitulo(),album_totales.getAutor(),null);
				for(Cancion canciones_album: album_totales.getContenido()) {
					if(canciones_album.getEstado() == EstadoCancion.Valida) {
						album_filtrado.anyadirContenido(canciones_album);
					}
				}
				albumes_incluidas_explicitas.add(album_filtrado);
			}
		}
		return albumes_incluidas_explicitas;
		
	}

	/**
	 * Permite para un autor dado buscar todas sus canciones
	 * @param palabra
	 * @return
	 */
	public ArrayList<Cancion> buscadorPorAutores_DevolvemosCanciones(String palabra){
		
		int ide = 0;
		ArrayList<Cancion> lista_autor_canciones = new ArrayList<Cancion>();
		LocalDate fecha_actual = LocalDate.now();
		
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
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			
			if(intervalo.getYears() >= 18) {
				for(Cancion cancion: sistema.canciones_totales) {
					if((cancion.getEstado() == EstadoCancion.Explicita || cancion.getEstado() == EstadoCancion.Valida) && cancion.getAutor().getId() == ide) {
						lista_autor_canciones.add(cancion);
					}
				}
				return lista_autor_canciones;
			}
		}
		
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getEstado() == EstadoCancion.Valida && cancion.getAutor().getId() == ide) {
				lista_autor_canciones.add(cancion);
			}
		}
		return lista_autor_canciones;
		
	}
	
	/**
	 * Permite para un autor dado buscar todos sus albumes
	 * @param palabra
	 * @return
	 */
	public ArrayList<Album> buscadorPorAutores_DevolvemosAlbumes(String palabra){
		
		int ide = 0;
		ArrayList<Album> albumes_incluidas_explicitas = new ArrayList<Album>();
		LocalDate fecha_actual = LocalDate.now();
		
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
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			
			if(intervalo.getYears() >= 18) {
				for(Album album_totales:sistema.albumes_totales) {
					if(album_totales.getAutor().getId() == ide) {
						Album album_filtrado = new Album(album_totales.getAnyo(),album_totales.getTitulo(),album_totales.getAutor(),null);
						for(Cancion canciones_album: album_totales.getContenido()) {
							if(canciones_album.getEstado() == EstadoCancion.Explicita || canciones_album.getEstado() == EstadoCancion.Valida) {
								album_filtrado.anyadirContenido(canciones_album);
							}
						}
						albumes_incluidas_explicitas.add(album_filtrado);
					}
				}
				return albumes_incluidas_explicitas;
			}
		}
		
		for(Album album_totales:sistema.albumes_totales) {
			if(album_totales.getAutor().getId() == ide) {
				Album album_filtrado = new Album(album_totales.getAnyo(),album_totales.getTitulo(),album_totales.getAutor(),null);
				for(Cancion canciones_album: album_totales.getContenido()) {
					if(canciones_album.getEstado() == EstadoCancion.Valida) {
						album_filtrado.anyadirContenido(canciones_album);
					}
				}
				albumes_incluidas_explicitas.add(album_filtrado);
			}
		}
		return albumes_incluidas_explicitas;
		
	}
	
	
	/**
	 * Buscador general que permite en base a un criterio y una cadena especificar que tipo de dato desea visualizar(retornar en este caso)
	 * @param palabra
	 * @param criterio
	 * @return
	 */
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
	
	/**
	 * Permite a un usuario registrado o registrado premium crear una cancion, a la espera de que el administrador las valide y las haga visibles al publico
	 * @param anyo
	 * @param titulo
	 * @param nombreMP3
	 * @return
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 */
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
				sistema.usuario_actual.anyadirACancionPersonal(c);
				sistema.canciones_totales.add(c);
				return Status.OK;
			}
			
		}else {
			return Status.ERROR;
		}
		
	}
	
	/**
	 * Permite a un usuario eliminar una de sus propias canciones(realmente en vez de eliminar el objeto le cambiamso el estado a Eliminado para que luego no se reproduzca ni se reconozca)
	 * @param cancion_eliminar
	 * @return
	 */
	public Status eliminarCancion(Cancion cancion_eliminar) {
		boolean existe_cancion_en_usuario = false;
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(cancion_eliminar.getTitulo()) == true && cancion.getNombreMP3().equals(cancion_eliminar.getNombreMP3()) == true) {
					existe_cancion_en_usuario = true;
				}
			}
			if(existe_cancion_en_usuario == true) {
				sistema.usuario_actual.eliminarDeCancionesPersonales(cancion_eliminar);
				return Status.OK;
			}else {
				return Status.ERROR;
			}
		}else {
			return Status.ERROR;
		}
		
	}
	
	/**
	 * Permite al usuario registrado o registrado premium crear un album que inicialmente estara vacio
	 * @param anyo
	 * @param titulo
	 * @param id
	 * @param contenido
	 * @return
	 */
	public Status crearAlbum(Date anyo,String titulo,int id,ArrayList<Cancion> contenido) {
		boolean album_repetido_en_usuario = false; 
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			Album a = new Album(anyo,titulo,sistema.usuario_actual,new ArrayList<Cancion>());
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(titulo)== true) {
					album_repetido_en_usuario = true;
				}
			}
			
			if(album_repetido_en_usuario  == true) {
				return Status.ERROR;
			}else {
				sistema.usuario_actual.anyadirAAlbumPersonal(a);
				sistema.albumes_totales.add(a);
				return Status.OK;
			}
		}else {
			return Status.ERROR;
		}
	
	}
	
	/**
	 * Permite a un usuario eliminar uno de sus propios albumes(a diferencia de las canciones que mantendremos las referencias en este caso eliminaremos directamente la referencia al objeto en ambos arraylists de albumes)
	 * @param album_eliminar
	 * @return
	 */
	public Status eliminarAlbum(Album album_eliminar) {
		boolean existe_album_en_usuario = false;
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(album_eliminar.getTitulo()) == true) {
					existe_album_en_usuario = true;
				}
			}
			
			if(existe_album_en_usuario == true) { //SE PUEDE ELIMINAR DEL ARRAY DE CANCIONES PERSONALES
				sistema.usuario_actual.eliminarDeAlbumesPersonales(album_eliminar);
				sistema.albumes_totales.remove(album_eliminar);
				return Status.OK;
			}else {
				return Status.ERROR;
			}
		}else {
			return Status.ERROR;
		}
	}
	
	//**ANIADIR_A_ALBUM
	public Status aniadirAAlbum(Album a, Cancion c) {
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
				if(sistema.usuario_actual.getCanciones().contains(c) == true) {
					if(sistema.usuario_actual.getAlbumes().contains(a) == true) {
						
						ArrayList<Cancion> canciones_en_albumes = a.getContenido();
						if(canciones_en_albumes.contains(c) != true) { //NO ESTA EN EL ALBUM Y SE PROCEDE A AÑADIR
							a.anyadirContenido(c);
							/*if(sistema.albumes_totales.contains(a) != true) {
								sistema.albumes_totales.add(a);
							}*/
							return Status.OK;
			
						}
					}
				}
				
				
				
			
		}else {
			return Status.ERROR;
		}
		return null;
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
	
	/**
	 * Permte al usuario registrado o registrado premium crear una lista de contenidos que inicialmente estara vacia y unicamente sera visible para el
	 * @param anyo
	 * @param titulo
	 * @param id
	 * @param autor
	 * @param contenido
	 * @return
	 */
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
				sistema.usuario_actual.anyadirAListaPersonal(l);
				return Status.OK;
			}
		}else {
			return Status.ERROR;
		}
	}
	
	/**
	 * Permite al usuario eliminar una de sus propias listas(eliminamos en el arraylist de listas en usuario la referencia al objeto correspondiente)
	 * @param lista_eliminar
	 * @return
	 */
	public Status eliminarLista(Lista lista_eliminar) {
		boolean existe_lista_en_usuario = false;
		
		if(sistema.usuario_actual.getPremium() == true || sistema.usuario_actual != null) {
			for(Lista lista:sistema.usuario_actual.getListas()) {
				if(lista.getTitulo().equals(lista_eliminar.getTitulo()) == true) {
					existe_lista_en_usuario = true;
				}
			}
			
			if(existe_lista_en_usuario == true) { //SE PUEDE ELIMINAR DEL ARRAY DE CANCIONES PERSONALES
				sistema.usuario_actual.eliminarDeListasPersonales(lista_eliminar);
				return Status.OK;
			}else {
				return Status.ERROR;
			}
		}else {
			return Status.ERROR;
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
	
	/**
	 * Funcion que se ejcutara de manera periodica y que comprobara todos aquellos usuarios que han excedido el tiempo de usuario premium y los degrada a usuarios registrados normales
	 */
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
	
	
	/**public Status bloquearUsuarioTotalmente() {

		
	}
	
	public Status bloquearUsuarioTemporalmente() {

		
	}
	
	public Status bloquearCancionPorPlagio() {
		
		
	}
	
	public Status validarCancion() {
		
	}**/
	
	/**
	 * Funcion que se ejecuta de manera periodica y que comprobara todos aqullos usuarios que han superado el tiempo de bloqueo establecido procediendo al desbloqueo
	 */
	public void desbloquearUsuario() {
		LocalDate fecha_actual = LocalDate.now();
		for(Usuario usuario: this.sistema.usuarios_totales) {
			LocalDate fecha_inicio_bloqueado = usuario.getFechaInicioBloqueado();
			if(fecha_actual.minusDays(30).equals(fecha_inicio_bloqueado) == true && usuario.getEstadoBloqueado() == enumeracionBloqueado.TEMPORAL) {
				usuario.desbloquearCuenta();
			}
		}
	}
	
	/**
	 * Funcion encargada que nada mas cerrar sesion de un usuario se guarda el estado del sistema en un fichero en disco
	 * @return
	 */
	public Status guardarDatosGenerales() {
		try {
			FileOutputStream fileOut=new FileOutputStream("datos.temp");
			ObjectOutputStream oos =new ObjectOutputStream(fileOut);
			oos.writeObject(this.sistema);
			oos.close();
			return Status.OK;
		}catch(IOException ie) {
			ie.toString();
			return Status.ERROR;
		}
	}
	
	/**
	 * Funcion encargada de cargar datos leeyendo de un fichero almacenado en disco
	 */
	public void cargarDatosGenerales(){
		try {
			FileInputStream in = new FileInputStream("datos.temp");
			ObjectInputStream oin = new ObjectInputStream(in);
			Sistema sistema_retornado = (Sistema) oin.readObject();
			sistema.usuario_actual = sistema_retornado.usuario_actual;
			sistema.usuarios_totales = sistema_retornado.usuarios_totales;
			sistema.precio_premium = sistema_retornado.precio_premium;
			sistema.canciones_totales = sistema_retornado.canciones_totales;
			sistema.umbral_reproducciones = sistema_retornado.umbral_reproducciones;
			sistema.albumes_totales = sistema_retornado.albumes_totales;
			sistema.max_reproducciones_usuarios_no_premium = sistema_retornado.max_reproducciones_usuarios_no_premium;
			sistema.es_administrador = sistema_retornado.es_administrador;
			oin.close();
		}catch(IOException ie) {
			ie.toString();
			return;
		}catch(ClassNotFoundException ce) {
			ce.toString();
			return;
		}
	}
	
	/**
	 * Esta funcion permite al administrador a modificar los datos basicos de configuracion de la aplicacion
	 * @param precio
	 * @param umbral
	 * @param reproducciones
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public Status guardarDatosConfiguracion(double precio,int umbral,int reproducciones) throws IOException {
			
		String ruta = "configuracion.txt";
		File fichero = new File(ruta);
		FileWriter archivo = new FileWriter(new File(ruta));
		if(sistema.usuario_actual != null && sistema.es_administrador == true) {
			if(fichero.exists() == true) {
				if(fichero.delete() == true) {
					if(fichero.createNewFile()) {
						archivo.write((int)precio);
						archivo.write(umbral);
						archivo.write(reproducciones);
						archivo.close();
						return Status.OK;
					}
				}
				
			}else {
				throw new IOException("Error al escribir en el fichero");
			}
		}
		
		return Status.ERROR;
			
	}
	
	/**
	 * Esta funcion que se ejecuta de manera periodica nada mas inicializar el objeto sistema y carga los datos si los hubiese
	 * @throws FileNotFoundException 
	 */
	public void leerDatosConfiguracion() throws FileNotFoundException, IOException {
		String ruta = "configuracion.txt";
		
		try{
			FileReader fichero = new FileReader(ruta);
			BufferedReader b = new BufferedReader(fichero);
			Sistema.sistema.precio_premium = (double)b.read();
			Sistema.sistema.umbral_reproducciones = (int) b.read();
			Sistema.sistema.max_reproducciones_usuarios_no_premium = (int)b.read();
			b.close();
			fichero.close();
			return;
		}catch(FileNotFoundException fi) {
			fi.toString();
			return;
		}catch(IOException ie) {
			ie.toString();
			return;
		}
		
	}
	
	/**
	 * Esta funcion que la puede utilizar cualquier tipo de usuario les permite escribir un comentario sobre una cancion
	 * @param comentario
	 * @param cancion
	 * @return
	 */
	public Status aniadirComentarioCancion(Comentario comentario, Cancion cancion) {
		if(comentario == null || cancion == null) {
			return Status.ERROR;
		}else {
			return cancion.anyadirComentario(comentario);
		}
	}

	/**
	 * Esta funcion que la puede utilizar cualquier tipo de usuario les permite escribir un comentario sobre un album
	 * @param comentario
	 * @param album
	 * @return
	 */
	public Status aniadirComentarioAlbum(Comentario comentario, Album album) {
		if(comentario == null  || album == null) {
			return Status.ERROR;
		}else {
			return album.anyadirComentario(comentario);
		}
	}

	
	//**----------REPRODUCCIONES__CONCRETAS----------------
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir una cancion que se pase como argumento
	 * @param c
	 * @throws InterruptedException ExcesoReproduccionesExcepcion
	 */
	public void reproducirCancion(Cancion c) throws InterruptedException,ExcesoReproduccionesExcepcion { //se supone que la cancion ha sido subida, valida y a la hora de buscar se devuelve en base a criterios ya comprobados
		
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {	
			if(sistema.usuario_actual.getCanciones().contains(c) != true) {
				sistema.cancion_reproduciendose = c;
				sistema.cancion_reproduciendose.reproducir();
				Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
				sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
			}else {
				sistema.cancion_reproduciendose = c;
				sistema.cancion_reproduciendose.reproducir();
				Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
			}
			
		}else{
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				if(sistema.usuario_actual.getCanciones().contains(c) != true) {
					sistema.cancion_reproduciendose = c;
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
					sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
					sistema.reproducciones_usuarios_no_premium++;
				}else{
					sistema.cancion_reproduciendose = c;
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
					sistema.reproducciones_usuarios_no_premium++;
				}
			}else {
				throw new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
		
	}
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir un album de cancion en cancion si es un usuario premium el que lo realiza o de manera limita si no lo es
	 * @param a
	 * @throws InterruptedException
	 * @throws ExcesoReproduccionesExcepcion
	 */
	public void reproducirAlbum(Album a) throws InterruptedException, ExcesoReproduccionesExcepcion {
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {
			if(sistema.usuario_actual.getAlbumes().contains(a) != true) {
				for(Cancion canciones_reproduciendose:a.getContenido()) {
					sistema.cancion_reproduciendose = canciones_reproduciendose;
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
					sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
				}
			}else {
				for(Cancion canciones_reproduciendose:a.getContenido()) {
					sistema.cancion_reproduciendose = canciones_reproduciendose;
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
				}
			}
		}else {
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				if(sistema.usuario_actual.getAlbumes().contains(a) != true) {
					for(Cancion canciones_reproduciendose:a.getContenido()) {
						if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
							break;
						}
						sistema.cancion_reproduciendose = canciones_reproduciendose;
						sistema.cancion_reproduciendose.reproducir();
						Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
						sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
						sistema.reproducciones_usuarios_no_premium++;
					}
				}else {
					for(Cancion canciones_reproduciendose:a.getContenido()) {
						if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
							break;
						}
						sistema.cancion_reproduciendose = canciones_reproduciendose;
						sistema.cancion_reproduciendose.reproducir();
						Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
						sistema.reproducciones_usuarios_no_premium++;
						
					}
				}
			}else {
				throw new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
	}
	
	/**
	 * 
	 * @param l
	 * @throws ExcesoReproduccionesExcepcion 
	 * @throws InterruptedException 
	 */
	public void reproducirLista(Lista l) throws ExcesoReproduccionesExcepcion, InterruptedException {
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {
			if(sistema.usuario_actual.getListas().contains(l) == true) {
				
				ArrayList<Contenido> contenido_en_lista = l.getContenido();
				for(Contenido contenido:contenido_en_lista) {
					if(sistema.usuario_actual.getAlbumes().contains((Album)contenido) == true) {
						Album temporal = (Album) contenido;
						for(Cancion canciones_en_album:temporal.getContenido()) {
							sistema.cancion_reproduciendose = (Cancion)canciones_en_album;
							sistema.cancion_reproduciendose.reproducir();
							Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
							if(sistema.usuario_actual.getCanciones().contains(sistema.cancion_reproduciendose) == true) {
								sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
							}
						}
						
					}else if(sistema.usuario_actual.getCanciones().contains((Cancion)contenido) == true) {
						sistema.cancion_reproduciendose = (Cancion)contenido;
						sistema.cancion_reproduciendose.reproducir();
						Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
						if(sistema.usuario_actual.getCanciones().contains(sistema.cancion_reproduciendose) == true) {
							sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
						}
					}
				}
				
			}
		}else {
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				if(sistema.usuario_actual.getListas().contains(l) == true) {
					
					ArrayList<Contenido> contenido_en_lista = l.getContenido();
					for(Contenido contenido:contenido_en_lista) {
						
						if(sistema.usuario_actual.getAlbumes().contains((Album)contenido) == true) { //ALBUM
							Album temporal = (Album) contenido;
							for(Cancion canciones_en_album:temporal.getContenido()) {
								if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
									throw new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
								}	
								sistema.cancion_reproduciendose = (Cancion)canciones_en_album;
								sistema.cancion_reproduciendose.reproducir();
								Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
								if(sistema.usuario_actual.getCanciones().contains(sistema.cancion_reproduciendose) == true) {
									sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
								}
								
								sistema.reproducciones_usuarios_no_premium++;
							}
							
						}else if(sistema.usuario_actual.getCanciones().contains((Cancion)contenido) == true) { //CANCION
							if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
								throw new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
							}
							sistema.cancion_reproduciendose = (Cancion)contenido;
							sistema.cancion_reproduciendose.reproducir();
							Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
							if(sistema.usuario_actual.getCanciones().contains(sistema.cancion_reproduciendose) == true) {
								sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
							}
							
							sistema.reproducciones_usuarios_no_premium++;
						}
					}
				
				
				
				}
			}else {
				throw new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
	}
	
	/**
	 * Esta funcion permite parar la reproduccion de la cancion que esta actualmente sonando
	 */
	public void pararReproductor() {
		sistema.cancion_reproduciendose.parar();
	}
	
	
	
	
	//**-------------REPRODUCCIONES__GENERALES------------


	
	/*public static void main(String args[]) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		Date d = new Date();
		Cancion c = new Cancion(d,"chicle",1,null,"chicle3.mp3");
		
		System.out.println(c.getNombreMP3() + "sss");
		c.anyadirCola(c.getNombreMP3());
		c.reproducirCancion();
		Thread.sleep(10000);
	}*/
	
	public static void main(String args[]) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		Date d = new Date();
		
		Cancion c = new Cancion(d,"pajaritos",null,"chicle3.mp3");
		c.aniadirCola();
		c.reproducir();
		Thread.sleep(5000);
		c.parar();
		
		Cancion c1 = new Cancion(d,"invalid",null,"np.mp3");
		c1.aniadirCola();
		c1.reproducir();
	}
	
}



