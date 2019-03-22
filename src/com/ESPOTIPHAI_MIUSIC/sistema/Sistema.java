package com.ESPOTIPHAI_MIUSIC.sistema;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

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

	
	private ArrayList<Usuario> usuarios_totales;
	private ArrayList<Cancion> canciones_totales;
	private ArrayList<Album> albumes_totales;
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
		sistema.canciones_totales = new ArrayList<Cancion>();
		sistema.albumes_totales = new ArrayList<Album>();
		sistema.administrador = null;
		sistema.usuario_actual = null;
	}
	
	//GETTERS
	
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
	
	//----------------USUARIO--------------------
	
	//**REGISTRARSE
	//DEBERIA SER STATUS Y NO BOOLEAN
	public boolean registrarse() {
		
		Scanner input = new Scanner(System.in);
		String nombre_usuario=null;
		String nombre_autor=null;
		LocalDate fecha_nacimiento=null;
		
		String contrasenia=null;
		boolean registrado_correcto = false;
		int i=0;
		
		while(registrado_correcto == false) {
			
			System.out.println("Inserte su nombre de usuario: ");
			nombre_usuario = input.next();
			System.out.println("Inserte su nombre de autor: ");
			nombre_autor = input.next();
			System.out.println("Inserte su dia de nacimiento: ");
			dia 
			
			
			
			
			LocalDate.now().withDayOfMonth(1).withMonth(3).withYear(2017)
			
			System.out.println("Inserte su contraseña: ");
			contrasenia = input.next();
			
				for(Usuario usuario: usuarios_totales) {
					if(usuario.getNombre_usuario().equals(nombre_usuario) == true && usuario.getNombre_autor().equals(nombre_autor) == true) {
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
		
		Usuario usuario_registrado_nuevo = new Usuario(nombre_usuario,nombre_autor,fecha_nacimiento, contrasenia,sistema.usuarios_totales.size()); 
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
				if(usuario.getNombre_usuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia) == true) {
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
	/*public boolean mejorarCuentaRepro() {
		FechaSimulada fecha_avanzada = null;
		fecha_avanzada.avanzar(15);
		int ide=-1;
				
		for(Contenido contenido: contenidos_totales) {
			if(contenido.getReproducciones() >= 30) {
				ide = contenido.getId();
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
	}*/
	//------------------------------------------------------------------------
	
	
	//-----------------------CONTENIDO-----------------------------------
	//**BUSQUEDA
	public ArrayList<Cancion> buscadorTitulos(String palabra) {
		
		//TITULO ==> BUSCAMOS POR CANCIONES Y PONEMOS UNICAMENTE OBJETOS DE ESTE TIPO
		ArrayList<Cancion> lista_filtrada = new ArrayList<Cancion>();
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getTitulo().contains(palabra) == true || cancion.getTitulo().equals(palabra) == true) {
				lista_filtrada.add(cancion);
			}
		}
		return lista_filtrada;
	}
	
	public ArrayList<Album> buscadorAlbumes(String palabra){
	
		ArrayList<Album> lista_filtrada = new ArrayList<Album>();
		for(Album album:albumes_totales) {
			if(album.getTitulo().contains(palabra) == true || album.getTitulo().equals(palabra) == true) {
				lista_filtrada.add(album);
			}
		}
		
		return lista_filtrada;
	}
	
	public ArrayList<Contenido> buscadorAutores(String palabra){
		
		
	}
	
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
			if(usuario.getFecha_inicio_pro().avanzar()) {
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
		int atras = 0;
		Scanner input = new Scanner(System.in);
		
		
		while(true) {
		//VEMOS DIFERENCIA CUANDO EL USUARIO ACTUAL HA INICIADO SESION POR TANTO
		if(sistema.usuario_actual == null) { //CASO DE PODER REGISTRAR E INICIAR SESION Y BUSCAR
			
			while(sistema.usuario_actual == null /*&& mouselistener pressed*/) { //PAGINA PRINCIPAL NO REGISTRADO
				eleccion_menu_1 = input.nextInt();
				if(eleccion_menu_1 == 1) { //BUSQUEDA
					
				}else if(eleccion_menu_1 == 2) { //REGISTRO
					
				}else if(eleccion_menu_1 == 3) { //INICIO SESION
					break;
					
				}else if(eleccion_menu_1 == 0) { //SALIR DE APPLICACION
					salir = 1;
					break;
				}
			}
			
		}
		
		if(sistema.usuario_actual != null) { //CASO EN EL QUE SE DAN LAS FUNCIONES DE UN USUARIO QUE HA INICIADO SESION
			while(sistema.usuario_actual != null /*&& mouselistener pressed*/) {
				eleccion_menu_1 = input.nextInt();
				
				if(eleccion_menu_1 == 1) { //BUSQUEDA
					
				}else if(eleccion_menu_1 == 2) { //AÑADIR/ELIMINAR/REPRODUCIR CANCION
					while((eleccion_menu_2 = input.nextInt())!= 0) {
						if(eleccion_menu_2 == 1) { //REPRODUCIR CANCION
							
						}else if(eleccion_menu_2 == 2) { //AÑADIR CANCION
							
						}else if(eleccion_menu_2 == 3) { //ELIMINAR CANCION
							
						}else if(eleccion_menu_2 == 0) { //ATRAS -- RESETEO
							eleccion_menu_2 = -1;
							break;
						}
					}
				}else if(eleccion_menu_1 == 3) { //AÑADIR/ELIMINAR/REPRODUCIR ALBUM
					while((eleccion_menu_2 = input.nextInt())!= 0) {
						if(eleccion_menu_2 == 1) { //REPRODUCIR ALBUM
							
						}else if(eleccion_menu_2 == 2) { //AÑADIR ALBUM
							
						}else if(eleccion_menu_2 == 3) { //ELIMINAR ALBUM
							
						}else if(eleccion_menu_2 == 0) { //ATRAS -- RESETEO
							eleccion_menu_2 = -1;
							break;
						}
					}
				}else if(eleccion_menu_1 == 4) { //AÑADIR/ELIMINAR/REPRODUCIR LISTA
					
				}else if(eleccion_menu_1 == 5) { //PERFIL
 					while((eleccion_menu_2 = input.nextInt()) != 0) {
 						if(eleccion_menu_2 == 1) { //MEJORAR CUENTA
 							
 						}else if(eleccion_menu_2 == 2) { //CERRAR SESION
 							boolean estado_sesion = cerrarSesion();
 						}else if(eleccion_menu_2 == 3) { //ELIMINAR CUENTA
 							
 						}else if(eleccion_menu_2 == 0) { //ATRAS
 							eleccion_menu_2 = -1;
 							break;
 						}
 						
 					}
 					
				}else if(eleccion_menu_1 == 6) { //SALIR DE LA APPLICACION
					salir = 1;
					break;
				}
			}
		}
		
		if(salir == 1) { //SALIDA CORRECTA 
			return 0;
		}
		
		return -1; //PARA AVISAR DE QUE SE HA FORZADO LA SALIDA
		}
	}
}

