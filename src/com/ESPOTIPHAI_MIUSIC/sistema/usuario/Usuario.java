package com.ESPOTIPHAI_MUSIC.sistema.usuario;
/**
package SPOTI;

/**
 * 
 */
import java.time.LocalDate;
import java.util.Stack;


import java.util.ArrayList;

/**
 * @author eps
 *
 */
public class Usuario {
	private String nombre_usuario;
	private String nombre_autor;
	private String fecha_nacimiento;
	private LocalDate fecha_registro;
	private String contrasena;
	private LocalDate fecha_inicio_pro;
	private LocalDate fecha_fin_pro;
	private Boolean premium;
	private Integer numero_repro;
	private Integer id;
	private ArrayList<Integer> seguidores;
	//private ArrayList<Lista> listas;
	
	
	/**
	 * Constructor de la clase usuario
	 */
	public Usuario(String nombre_usuario, String nombre_autor, String fecha_nacimiento, String contrasena, Integer id) {
		this.nombre_usuario = nombre_usuario;
		this.nombre_autor = nombre_autor;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_registro = LocalDate.now();
		this.contrasena = contrasena;
		this.fecha_inicio_pro = null;
		this.fecha_fin_pro = null;
		this.premium = false;
		this.id = id;
		this.seguidores = new ArrayList<Integer>();
		//this.listas = new ArrayList<Listas>();
	}
	
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	
	public String getNombre_autor() {
		return nombre_autor;
	}
	
	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	
	public LocalDate getFecha_registro() {
		return fecha_registro;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public LocalDate getFecha_inicio_pro() {
		return fecha_inicio_pro;
	}
	
	public Boolean getPremium() {
		return premium;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getNumeroReproducciones() {
		return numero_repro;
	}
	
	public Integer sumarReproduccion() {
		numero_repro = numero_repro + 1;
		if(numero_repro >= umbral) {
			mejorarCuentaPorReproducciones();
		}
		return numero_reproducciones;
	}
	
	
	public Boolean seguirUsuario(Integer id, Boolean bool) { //La funcion es status 

		if(bool == true) { /*Seguimos al usuario*/
			if(seguidores.indexOf(id) == -1) {
				seguidores.add(id);
				System.out.println("Se ha seguido al usuario correctamente");
				return true;  //Status.OK
			}else {
				System.out.println("No se puede seguir al usuario porque ya le sigue\n");
				return false;
			}
		} else  {  /*Dejamos de seguir al usuario*/ 
			if(seguidores.indexOf(id) != -1) {
				seguidores.remove(seguidores.indexOf(id));
				System.out.println("Se ha dejado de seguir al usuario correctamente\n");
				return true; //Status.OK
			}else {
				System.out.println("No se puede dejar de seguir al usuario porque no le sigue\n");
				return false;
			}
		}
		//Status.OK
	}
	public Boolean mejorarCuentaPorReproducciones() {
		
		fecha_inicio_pro = LocalDate.now();
		premium = true;
		fecha_fin_pro = fecha_inicio_pro.plusDays(30);
		return true;
	}
	public Boolean emperorarCuenta() {
		premium = false;
		fecha_inicio_pro = null;
		fecha_fin_pro = null;
		return false;
	}
	
	
	
	/**
	 * @param args
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