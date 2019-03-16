package com.ESPOTIPHAI_MIUSIC.sistema;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Sistema {

	private FechaSimulada fecha;
	private ArrayList<Usuario> usuarios_totales;
	private ArrayList<Contenido> contenidos_totales;
	private Usuario administrador;
	private Usuario usuario_actual;
	private static Sistema sistema = null;

	
	public static Sistema getSistema() {
		if(sistema == null) {
			sistema = new Sistema();
		}
		return sistema;
	}
	
	public Sistema() {
		sistema.usuarios_totales = new ArrayList<Usuario>();
		sistema.contenidos_totales = new ArrayList<Contenido>();
		sistema.administrador = null;
		sistema.usuario_actual = null;
	}
	
	//GETTERS
	
	public FechaSimulada getFechaActual() {
		return sistema.fecha;
	}
	
	public ArrayList<Usuario> getUsuariosTotales(){
		return sistema.usuarios_totales;
	}
	
	public ArrayList<Contenido> getContenidosTotales(){
		return sistema.contenidos_totales;
	}
	
	public Usuario getAdministrador() {
		return (Usuario) sistema.administrador;
	}
	
	public Usuario getUsuarioActual() {
		return (Usuario) sistema.usuario_actual;
	}
	
	//----------------USUARIO--------------------
	
	//**REGISTRARSE
	//DEBERIA SER STATUS Y NO BOOLEAN
	public boolean registrarse() {
		
		Scanner input = new Scanner(System.in);
		String nombre_usuario=null;
		String nombre_autor=null;
		String fecha_nacimiento=null;
		String contrasenia=null;
		FechaSimulada fecha_registro = getFechaActual();
		boolean registrado_correcto = false;
		int i=0;
		
		while(registrado_correcto == false) {
			
			System.out.println("Inserte su nombre de usuario: ");
			nombre_usuario = input.next();
			System.out.println("Inserte su nombre de autor: ");
			nombre_autor = input.next();
			System.out.println("Inserte su fecha de nacimiento: ");
			fecha_nacimiento = input.next();
			System.out.println("Inserte su contraseña: ");
			contrasenia = input.next();
			
			//Hasta aqui un usuario puede decidir si rellenar para crear, salir del resgitro o iniciar sesion 
			//Rellenar datos y entonces mirar si existe o no el usuario
			//AQUI DAMOS A ACEPTAR Y BUSCA A VER SI YA ESTA ESE USUARIO CREADO Y SI NO ES ASI LO CREA Y LO INSERTA
			
				for(Usuario usuario: usuarios_totales) {
					if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getNombreAutor().equals(nombre_autor) == true ) {
						break;
					}
					i++;
				}
				
				if(i < sistema.usuarios_totales.size()) {
					System.out.println("Usuario ya existente, por favor introduzca datos validos para poder registrarse");
				}else if(i == sistema.usuarios_totales.size()){
					registrado_correcto = true;
				}
		}
		
		Usuario usuario_registrado_nuevo = new Usuario(nombre_usuario,nombre_autor,fecha_nacimiento,fecha_registro.getHoy(),contrasenia,null,false,null,sistema.usuarios_totales.size(), null); 
		sistema.usuarios_totales.add(usuario_registrado_nuevo);
		return true;
	}
	

	//**LOGIN //FALTA LOGIN PARA ADMINISTRADOR //FALTA CANCELAR INICIO_SESION
	public boolean iniciarSesion() {
		Scanner input = new Scanner(System.in);
		String nombre_usuario;
		String contrasenia;
		boolean iniciado_sesion_correcto=false;
		
		while(iniciado_sesion_correcto == false) {
			System.out.println("Inserte su nombre de usuario: ");
			nombre_usuario = input.next();
			System.out.println("Inserte su contraseña: ");
			contrasenia = input.next();
			
			for(Usuario usuario: usuarios_totales) {
				if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia) == true) {
					sistema.usuario_actual = usuario;
					iniciado_sesion_correcto = true;
					break;
				}
			}
			
		}
		
		return true;
	}
	
	//**CERRAR_SESION
	public boolean cerrarSesion() {
		if(sistema.usuario_actual != null) {
			sistema.usuario_actual = null;
			return true;
		}
		return false;
	}
	
	//**MEJORAR_CUENTA_POR_PAGO
	
	//**MEJORAR_CUENTA_POR_REPRO
	public boolean mejorarCuentaRepro() {
		FechaSimulada fecha_avanzada = null;
		fecha_avanzada.avanzar(15);
		int ide=-1;
				
		for(Contenido contenido: contenidos_totales) {
			if(contenido.getReproducciones() >= 30) {
				ide = contenido.getIdentificadorUsuario();
				for(Usuario usuario: usuarios_totales) {
					if(usuario.getId() == ide) {
						usuario.setFechaInicioPro(sistema.fecha.getHoy());
						usuario.setPremium(true);
						usuario.setFechaFinPro(fecha_avanzada.getHoy());
						break;
					}
				}
				
			}
		}
		
		return true;
	}
	//------------------------------------------------------------------------
	
	
	//-----------------------CONTENIDO-----------------------------------
	//**BUSQUEDA
	//**SUBIR_CANCION
	//**BORRAR_CANCION
	//**CREAR_ALBUM
	//**BORRAR_ALBUM
	//**CREAR_LISTA
	//**REPRODUCCION DE CANCION
	
	//---------------------ADMINISTRADOR------------------------------------
	
	//**EMPEORAR_CUENTA
	public void empeorarCuenta() {
		for(Usuario usuario:usuarios_totales) {
			if(usuario.getFechaFinPro().equals(sistema.fecha.getHoy()) == true) {
				usuario.setFechaInicioPro(null);
				usuario.setFechaFinPro(null);
				usuario.setPremium(false);
			}
		}
		
		return;
	}
	
	//**VALIDAR_CANCION
	
	//**INCLUIR_CANCION_EXPLICITA
	
	//**INCLUIR_CANCION_NO_EXPLICITA

	//---------------------------------------------------------------------
	
	public int main () {
		Sistema sistema = Sistema.getSistema();
		int eleccion_menu_1 = -1;
		int eleccion_menu_2 = -1;
		int salir=0;
		Scanner input = new Scanner(System.in);
		
		//VEMOS DIFERENCIA CUANDO EL USUARIO ACTUAL HA INICIADO SESION POR TANTO
		if(sistema.usuario_actual == null) { //CASO DE PODER REGISTRAR E INICIAR SESION Y BUSCAR
			
			while(sistema.usuario_actual != null /*&& mouselistener pressed*/) { //PAGINA PRINCIPAL NO REGISTRADO
				eleccion_menu_1 = input.nextInt();
				if(eleccion_menu_1 == 1) { //BUSQUEDA
					
				}else if(eleccion_menu_1 == 2) { //REGISTRO
					
				}else if(eleccion_menu_1 == 3) { //INICIO SESION
					break;
					
				}else if(eleccion_menu_1 == 0) { //SALIR
					salir = 1;
					break;
				}
			}
			
		}else if(sistema.usuario_actual != null) { //CASO EN EL QUE SE DAN LAS FUNCIONES DE UN USUARIO QUE HA INICIADO SESION
			
		}
		
		if(salir == 1) { //SALIDA CORRECTA 
			return 0;
		}
		
		return -1; //PARA AVISAR DE QUE SE HA FORZADO LA SALIDA
	}
	
}

