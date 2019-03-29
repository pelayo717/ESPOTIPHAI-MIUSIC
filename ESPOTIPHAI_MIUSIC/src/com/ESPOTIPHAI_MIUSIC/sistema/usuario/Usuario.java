package com.ESPOTIPHAI_MIUSIC.sistema.usuario;
import java.time.LocalDate;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.EstadoCancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Lista;

import java.util.ArrayList;
//import java.util.Stack;


/**
* Clase de Usuario con la que vamos a poner cambiar todos los atributos de la clase 
* y tendremos diferentes funciones las cuales ayudaran a darle a la aplicacion la funcionalidad requerida
* @author Manuel Salvador, Pelayo Rodriguez, Roberto Pirk
*/
public class Usuario {
	private static int nextID = 0;
	private String nombre_usuario;
	private String nombre_autor;
	private LocalDate fecha_nacimiento;
	private LocalDate fecha_registro;
	private String contrasena;
	private LocalDate fecha_inicio_pro;
	private boolean premium;
	private Integer numero_repro;
	private enumeracionBloqueado bloqueado;
	private LocalDate fecha_inicio_bloqueado;
	private Integer id;
	private ArrayList<Usuario> seguidores;
	private ArrayList<Usuario> seguidos;
	private ArrayList<Lista> listas;
	private ArrayList<Cancion> canciones;
	private ArrayList<Album> albumes;
	
	/**
	 * Constructor de la clase Usuario, donde se inicializan los diferentes atributos y se les asignas sus respectivos valores
	 * @param nombre_usuario: nombre que va a tener el usuario
	 * @param nombre_autor: nombre que va a tener el autor 
	 * @param fecha_nacimiento: fecha de nacimiento del usuario
	 * @param contraseña: contraseña que el usuario pone a su cuenta
	 * @param id: indica el id que se le asigna al usuario
	 * @return 
	 */
	public Usuario(String nombre_usuario, String nombre_autor, LocalDate fecha_nacimiento, String contrasena) {
		this.nombre_usuario = nombre_usuario;
		this.nombre_autor = nombre_autor;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_registro = LocalDate.now();
		this.contrasena = contrasena;
		this.numero_repro = 0;
		this.fecha_inicio_pro = null;
		this.fecha_inicio_bloqueado = null;
		this.premium = false;
		this.id = this.nextID++;
		this.seguidores = new ArrayList<Usuario>();
		this.seguidos = new ArrayList<Usuario>();
		this.listas = new ArrayList<Lista>();
		this.canciones = new ArrayList<Cancion>();
		this.albumes = new ArrayList<Album>();
	}
	
	/**
	 * Contructor de la clase libro
	 * @param id_interno: numero que identifica el libro del resto
	 * @return nombre_usuario: devuelve el nombre del usuario
	 */
	public String getNombreUsuario() {
		return nombre_usuario;
	}
	
	/**
	 * Funcion que devuelve el nombre del autor
	 * @return nombre_autor: devuelve el nombre del autor 
	 */
	public String getNombreAutor() {
		return nombre_autor;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que el usario nacio 
	 * @return fecha_nacimiento: fecha en la que el usuario nacio
	 */
	public LocalDate getFechaNacimiento() {
		return fecha_nacimiento;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que el usuario se regitro
	 * @return fecha_registro: fecha en la que el usuario se registro en la aplicacion
	 */
	public LocalDate getFechaRegistro() {
		return fecha_registro;
	}
	
	/**
	 * Funcion que devuelve la constraseña del usuario
	 * @return contraseña: string donde se ha guardado la contraseña del usuario 
	 */
	public String getContrasena() {
		return contrasena;
	}
	
	/**
	 * Funcion que devuelve la fecha de cuando el usuario se hizo premium
	 * @return fecha_inicio_pro: indica la fecha en la que el usuario empezo a ser premium 
	 */
	public LocalDate getFechaInicioPro() {
		return fecha_inicio_pro;
	}
	
	/**
	 * Funcion que devuelve un boolean indicando si es premium o no 
	 * @return premium: boolean que indica si el usuario es prrmium o no 
	 */
	public boolean getPremium() {
		return premium;
	}
	
	/**
	 * Funcion que devuelve el estado del usuario
	 */
	public enumeracionBloqueado getEstadoBloqueado() {
		return bloqueado;
		
	}
	
	/**
	 * Funcion que devuelve el id del usuario
	 * @return id: devuelve el id del usuario 
	 */
	public Integer getId() {
		return id;
	}
	
	public ArrayList<Usuario> getSeguidos() {
		return seguidos;
	}
	
	/**
	 * Funcion que devuelve un tipo boolean indicando si la cuenta esta en estado bloqueado o no
	 * @return bloqueado: el status de la cuenta en ese momento, si esta bloqueada o no	 
	 */
	public LocalDate getFechaInicioBloqueado() {
		return fecha_inicio_bloqueado;
	}
	/**
	 * Funcion que devuelve el numero de reproducciones del usuario
	 * @return numero_repro: atributo del usuario que indica el numero de reproducciones 
	 */
	public Integer getNumeroReproducciones() {
		return numero_repro;
	}
	
	/**
	 * Funcion que devuelve el arrayList de listas de reproduccion
	 */
	public ArrayList<Lista> getListas() {
		return listas;
	}
	
	/**
	 * Funcion que devuelve el ArrayList de canciones de la aplicacion
	 */
	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}
	
	/**
	 * Funcion que devuelve el ArrayList de albumes de la aplicacion;
	 */
	public ArrayList<Album> getAlbumes() {
		return albumes;
	}
	
	/**
	 * Funcion que aumenta el numero de reproducciones del usuario en 1
	 * @param x: umbral de reproducciones que si se supera, el usuario se hace premium durante un mes gratis
	 * @return numero_repro: devolvemos el numero de reproducciones del usuario actualizado
	 */
	public Integer sumarReproduccion(Integer x) {
		numero_repro = numero_repro + 1;
		if(numero_repro >= x) {
			mejorarCuentaPorReproducciones();
		}
		return numero_repro;
	}
	
	
	/**
	 * Funcion con la que un usuario puede seguir a otro usuario
	 * @param x: Usuario al que queremos seguir
	 * @return true si se ejecuta correctamente, false si hay algun error
	 */
	public boolean seguirUsuario(Usuario x) { 
			if(seguidos.contains(x)) //Ya se sigue al usuario
				return false;
			else {
				seguidos.add(x);
				x.seguidores.add(this);
				System.out.println("Se ha seguido al usuario correctamente");
				return true;
			}
	}
	
	/**
	 * Funcion con la que un usuario puede dejar de seguir a otro usuario
	 * @param x: Usuario al que queremos dejar de seguir
	 * @return true si se ejecuta correctamente la funcion y le ha dejado de seguir, false si hay algun error
	 */
	public boolean dejarDeSeguirUsuario(Usuario x) {
		if(seguidos.contains(x)) {
			seguidos.remove(x);
			x.seguidores.remove(this);
			return true;
		} else {
			return false;
		}	
	}
	
	/**
	 * Funcion que mejora la cuenta de un usuario modificando los atributos necesarios 
	 * y poniendo la fecha de inicio pro a la fecha actual
	 */
	public void mejorarCuentaPorReproducciones() {
		fecha_inicio_pro = LocalDate.now();
		premium = true;
	}
	
	/**
	 * Funcion que baja la cuenta del estatus de premium a una cuenta normal 
	 * que no paga mensualmente
	 */
	public void emperorarCuenta() {
		premium = false;
		fecha_inicio_pro = null;
	}
	
	/**
	 * Funcion que bloquea la cuenta del usuario indefinidamente modificando los atributos correspondientes
	 */		
	public void bloquearCuentaIndefinido() {
		bloqueado = enumeracionBloqueado.INDEFINIDO;
		fecha_inicio_bloqueado = LocalDate.now();
	}
	
	
	/**
	 * Funcion que bloquea la cuenta temporalmente del usuario modificando los atributos correspondientes
	 */
	public void bloquearCuentaTemporal() {
		bloqueado = enumeracionBloqueado.TEMPORAL;
		fecha_inicio_bloqueado = LocalDate.now();
		
	}
	
	/**
	 * Funcion que desbloquea la cuenta del usuario modificando los atributos correspondientes
	 */
	public void desbloquearCuenta() {
		bloqueado = enumeracionBloqueado.NOBLOQUEADO;
		fecha_inicio_bloqueado = null;
	}
	
	/**
	 * Funcion convierte la cuenta del usuario en premium a traves del pago
	 */
	public void mejorarCuentaPorPago() {
		premium = true;
		fecha_inicio_pro = LocalDate.now();
	}
	
	/**
	 * Funcion que añade al album de usuario el contenido pasado como argumento
	 * @param c: Contenido que se va a añadir al album 
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean anyadirAAlbumPersonal(Album c) {
		if(albumes.contains(c)) {
			return false; //Ya existe ese album en el array
		} else {
			albumes.add(c);
		}
		return true; 
	}
	
	/**
	 * Funcion que añade el contenido a la listas de canciones
	 * @param c: Contenido que se va a añadir a la listas de canciones 
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean anyadirACancionPersonal(Cancion c) {
		if(canciones.contains(c)) {
			return false; //Ya esta contenida la cancion
		} else {
			canciones.add(c);
		}
		return true;
	}
	
	
	/**
	 * Funcion que va a añadir el contenido a la lista Listas 
	 * @param c: Contenido que se va a añadir a la lista de Listas  
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean anyadirAListaPersonal(Lista c) {
		if(listas.contains(c)) {
			return false; //Ya esta esa lista
		}
		else {
			listas.add(c);
		}
		return true;
	}
	
	/**
	 * Funcion que elimina el contenido de la lista de albumes 
	 * @param c: Contenido que se va a eliminar al album 
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean eliminarDeAlbumesPersonales(Album c) {
		if(albumes.contains(c)) {
			albumes.remove(c);
			return true;
		} else
			return false;
	}
	
	
	/**
	 * Funcion elimina el contenido pasado de la lista de canciones
	 * @param c: Contenido que se va a eliminar de la lista de canciones
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean eliminarDeCancionesPersonales(Cancion c) {
		if(canciones.contains(c)) {
			c.setEstado(EstadoCancion.Eliminada);
			return true;
		} else
			return false;
	}
	
	
	/**
	 * Funcion que elimina el contenido de la lista de Listas
	 * @param c: Contenido que se va a eliminar de la listas
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean eliminarDeListasPersonales(Lista c) {
		if(listas.contains(c)) {
			listas.remove(c);
			return true;
		} else
			return false;
	}
	
	
}