package com.ESPOTIPHAI_MIUSIC.sistema.usuario;
/**
 * 
 */
import java.util.Date;
import java.util.Stack;

import com.ESPOTIPHAI_MIUSIC.sistema.status.Status;

import java.util.ArrayList;

/**
 * Clase Usuario
 */
public class Usuario {
	private String nombre_usuario;
	private String nombre_autor;
	private Date fecha_nacimiento;
	private Date fecha_registro;
	private String contrasena;
	private Date fecha_inicio_pro;
	private Boolean premium;
	private Date fecha_fin_pro;
	private Integer id;
	private ArrayList<Integer> seguidores;
	
	/**
	 * Constructor de la clase usuario
	 */
	public Usuario(String nombre_usuario, String nombre_autor, Date fecha_nacimiento, Date fecha_registro, String contrasena, Date fecha_inicio_pro, Boolean premium, Date fecha_fin_pro, Integer id, ArrayList<Integer> seguidores) {
		this.nombre_usuario = nombre_usuario;
		this.nombre_autor = nombre_autor;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_registro = fecha_registro;
		this.contrasena = contrasena;
		this.fecha_inicio_pro = fecha_inicio_pro;
		this.premium = premium;
		this.fecha_fin_pro = fecha_fin_pro;
		this.id = id;
		this.seguidores = seguidores;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public String getNombre_autor() {
		return nombre_autor;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public Date getFecha_registro() {
		return fecha_registro;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public String getContrasena() {
		return contrasena;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public Date getFecha_inicio_pro() {
		return fecha_inicio_pro;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public Boolean getPremium() {
		return premium;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public Date getFecha_fin_pro() {
		return fecha_fin_pro;
	}
	
	/**
	 *	Getter del nombre del usuario
	 * @return    el nombre del usuario (String)
	 */
	public Integer getId() {
		return id;
	}
	
	public void setNombre_usuario(String nombre) {
		this.nombre_usuario = nombre;
	}
	
	public void setNombre_autor(String nombre) {
		this.nombre_autor = nombre;
	}
	
	public void setFecha_nacimiento(Date fecha) {
		this.fecha_nacimiento = fecha;
	}
	
	public void setFecha_registro(Date fecha) {
		this.fecha_registro = fecha;
	}
	
	public void setcontrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public void setFecha_inicio_pro(Date fecha) {
		this.fecha_inicio_pro = fecha;
	}
	
	public void setPremium(Boolean premium) {
		this.premium = premium;
	}
	
	public void setFecha_fin_pro(Date fecha) {
		this.fecha_fin_pro = fecha;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Status seguiUsuario(Integer id, Boolean bool) {
		if(bool == true) { /*Seguimos al usuario*/
			if(seguidores.get(id) == null) {
				seguidores.add(id);
			}
		} else  {  /*Dejamos de seguir al usuario*/ 
			if(seguidores.get(id) != null) {
				seguidores.remove(id);			
			}else {
				System.out.println("No se puede dejar de seguir al usuario porque no le sigue\n");
			}
		}
		return Status.OK;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}