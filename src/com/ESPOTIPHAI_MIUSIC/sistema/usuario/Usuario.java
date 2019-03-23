/*package com.ESPOTIPHAI_MUSIC.sistema.usuario;*/
package Padsof;
/**
 * 
 */

    
//package com.ESPOTIPHAI_MUSIC.sistema.usuario;
/**
package SPOTI;
/**
 * 
 */
import java.time.LocalDate;
import java.util.Stack;


import java.util.ArrayList;

/**
 * Clase de Usuario con la que vamos a poner cambiar todos los atributos de la clase 
 * y tendremos diferentes funciones las cuales ayudaran a darle a la aplicacion la funcionalidad requerida
 * @author Manuel Salvador, Pelayo Rodriguez, Roberto Pirk
 *
 */
public class Usuario {
	private String nombre_usuario;
	private String nombre_autor;
	private LocalDate fecha_nacimiento;
	private LocalDate fecha_registro;
	private String contrasena;
	private LocalDate fecha_inicio_pro;
	private Boolean premium;
	private Integer numero_repro;
	private boolean bloqueado;
	private Integer id;
	private ArrayList<Usuario> seguidores;
	private ArrayList<Usuario> seguidos;
	private ArrayList<Lista> listas;
	
	
	/**
	 * Constructor de la clase Usuario, donde se inicializan los diferentes atributos y se les asignas sus respectivos valores
	 * @param nombre_usuario: nombre que va a tener el usuario
	 * @param nombre_autor: nombre que va a tener el autor 
	 * @param fecha_nacimiento: fecha de nacimiento del usuario
	 * @param contraseña: contraseña que el usuario pone a su cuenta
	 * @param id: indica el id que se le asigna al usuario
	 * @return 
	 */
	public Usuario(String nombre_usuario, String nombre_autor, LocalDate fecha_nacimiento, String contrasena, Integer id) {
		this.nombre_usuario = nombre_usuario;
		this.nombre_autor = nombre_autor;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_registro = LocalDate.now();
		this.contrasena = contrasena;
		this.fecha_inicio_pro = null;
		this.premium = false;
		this.id = id;
		this.seguidores = new ArrayList<Usuario>();
		this.seguidos = new ArrayList<Usuario>();
		this.listas = new ArrayList<Listas>();
	}
	
	/**
	 * Contructor de la clase libro
	 * @param id_interno: numero que identifica el libro del resto
	 * @return nombre_usuario: devuelve el nombre del usuario
	 */
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	
	/**
	 * Funcion que devuelve el nombre del autor
	 * @return nombre_autor: devuelve el nombre del autor 
	 */
	public String getNombre_autor() {
		return nombre_autor;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que el usario nacio 
	 * @return fecha_nacimiento: fecha en la que el usuario nacio
	 */
	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que el usuario se regitro
	 * @return fecha_registro: fecha en la que el usuario se registro en la aplicacion
	 */
	public LocalDate getFecha_registro() {
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
	public LocalDate getFecha_inicio_pro() {
		return fecha_inicio_pro;
	}
	
	/**
	 * Funcion que devuelve un boolean indicando si es premium o no 
	 * @return premium: boolean que indica si el usuario es prrmium o no 
	 */
	public Boolean getPremium() {
		return premium;
	}
	
	/**
	 * Funcion que devuelve el id del usuario
	 * @return id: devuelve el id del usuario 
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Funcion que devuelve el numero de reproducciones del usuario
	 * @return numero_repro: atributo del usuario que indica el numero de reproducciones 
	 */
	public Integer getNumeroReproducciones() {
		return numero_repro;
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
	 * 
	 * @param id: Id del usuario al que queremos seguir o dejar de seguir
	 * @param bool: Boolean que dependiendo de si es true o false indica si se quiere seguir al 
	 * usuario o dejar de seguirle
	 * @return true si se ejecuta correctamente, false si hay algun error
	 */
	public Boolean seguirUsuario(Usuario x, Boolean bool) { //La funcion es status 
		/*Seguimos al usuario*/
			
				seguidores.add(x);
				System.out.println("Se ha seguido al usuario correctamente");
				return true;  //Status.OK
			
				System.out.println("No se puede seguir al usuario porque ya le sigue\n");
				return false;
			}
		//Status.OK
	
	/**
	 * Funcion que mejora la cuenta de un usuario modificando los atributos necesarios 
	 * y poniendo la fecha de inicio pro a la fecha actual
	 * @param id_interno: numero que identifica el libro del resto
	 * @return true: si se ejecuta correctamente 
	 */
	public Boolean mejorarCuentaPorReproducciones() {
		
		fecha_inicio_pro = LocalDate.now();
		premium = true;
		return true;
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
	 * 
	 */
	public static void main(String[] args) {
		//Boolean b = true;
		//Boolean x = true; 
		//ArrayList<Integer> seguidores = new ArrayList<Integer>();
		//Usuario n = new Usuario("javier", "rafa", LocalDate.now(), LocalDate.now(), "hola", LocalDate.now(), false, LocalDate.now(), 5, seguidores);

		//for (int i=1; i<=10; i++){
			//seguidores.add(i); 
		//}
		//x = n.seguirUsuario(11, b);
	//}
	}
}

