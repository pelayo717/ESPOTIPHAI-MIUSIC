package com.ESPOTIPHAI_MIUSIC.sistema;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

import java.util.ArrayList;
import java.time.LocalDate;



public class Sistema {

	
	private ArrayList<Usuario> usuarios_totales;
	private ArrayList<Cancion> canciones_totales;
	private ArrayList<Album> albumes_totales;
	private Usuario administrador;
	private Usuario usuario_actual;
	private static Sistema sistema = null;
	private int umbral_reproducciones=30;
	private double precio_premium=9.99;
	
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
	
	public int getUmbralReproducciones() {
		return sistema.umbral_reproducciones;
	}
	
	public double getPrecioPremium() {
		return sistema.precio_premium;
	}
	
	//----------------USUARIO--------------------
	
	public boolean registrarse(String nombre_usuario,String nombre_autor,LocalDate fecha_nacimiento, String contrasenia) {
		
		int i=0;
		
		if(nombre_usuario == null || contrasenia == null || fecha_nacimiento == null) {
			return false;
		}
		
		for(Usuario usuario: usuarios_totales) {
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
		
			
		for(Usuario usuario: usuarios_totales) {
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
	public boolean mejorarCuentaPago(String numero_tarjeta,String concepto) throws FailedInternetConnectionException,InvalidCardNumberException,OrderRejectedException{
		try {
			if(TeleChargeAndPaySystem.isValidCardNumber(numero_tarjeta) == true) {
				TeleChargeAndPaySystem.charge(numero_tarjeta, concepto, sistema.getPrecioPremium());
			}else {
				throw new InvalidCardNumberException(numero_tarjeta);
			}
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
	
	//------------------------------------------------------------------------
	
	
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
		for(Album album:albumes_totales) {
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
		
		for(Usuario usuario: usuarios_totales) {
			if(usuario.getNombre_autor().equals(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		/*No existe el usuario para la cadena introducida en la busqueda, ya que no se ha encontrado previamente y no existe nadie con ide = 0*/
		if(ide == 0) {
			return null;
		}
		
		for(Cancion cancion: canciones_totales) {
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
		
		for(Usuario usuario: usuarios_totales) {
			if(usuario.getNombre_autor().equals(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		/*No existe el usuario para la cadena introducida en la busqueda, ya que no se ha encontrado previamente y no existe nadie con ide = 0*/
		if(ide == 0) {
			return null;
		}
		
		for(Album album: albumes_totales) {
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
	//**SUBIR_CANCION
	//**BORRAR_CANCION
	//**CREAR_ALBUM
	//**BORRAR_ALBUM
	//**CREAR_LISTA
	//**REPRODUCCION DE CANCION
	
	//---------------------ADMINISTRADOR------------------------------------
	
	//**EMPEORAR_CUENTA
	public void empeorarCuenta_principal() {
		
		LocalDate fecha_actual= LocalDate.now();
		for(Usuario usuario:usuarios_totales) {
			LocalDate fecha_inicio_premium = usuario.getFecha_inicio_pro();
			if(fecha_actual.minusDays(15).equals(fecha_inicio_premium) == true)  {
				usuario.emperorarCuenta();
			}
			
		}
		return;
	}
	
	public void bloquearUsuario(int id) {
		for(Usuario usuario:usuarios_totales) {
			if(usuario.getId() == id) {
				usuario.bloquear();
			}
		}
		return;
	}
	
	//**VALIDAR_CANCION
	
	//**INCLUIR_CANCION_EXPLICITA
	
	//**INCLUIR_CANCION_NO_EXPLICITA

}
	