package com.ESPOTIPHAI_MUSIC.system.usuario;
/**
 * 
 */
import java.util.Date;
import java.util.Stack;
import java.util.ArrayList;

/**
 * @author eps
 *
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
	
	
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	
	public String getNombre_autor() {
		return nombre_autor;
	}
	
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	
	public Date getFecha_registro() {
		return fecha_registro;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public Date getFecha_inicio_pro() {
		return fecha_inicio_pro;
	}
	
	public Boolean getPremium() {
		return premium;
	}
	
	public Date getFecha_fin_pro() {
		return fecha_fin_pro;
	}
	
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
		for(Integer c : seguidores) {
			if(c == id) {
				return Error;
			}
		}
		
		return ok;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
