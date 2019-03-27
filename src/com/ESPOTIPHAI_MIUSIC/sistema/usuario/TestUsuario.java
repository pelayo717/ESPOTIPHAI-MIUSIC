package com.ESPOTIPHAI_MIUSIC.sistema.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Lista;

//import org.junit.Assert;
import org.junit.BeforeClass;

class TestUsuario {
	
	Usuario x;
	Usuario y;
	Usuario z;
	Cancion cancion;
	Album album;
	Lista lista;
	
	@BeforeClass
	public void beforeClass() {
		System.out.println("BeforeClass");
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		y = new Usuario("Rober", "Roberto", LocalDate.now(), "robertofeo", 2);
		z = new Usuario("Manu", "Manu", LocalDate.now(), "manufeo", 3);
		System.out.println("BeforeClass");
	}

	@Test
	public void testSeguir() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		y = new Usuario("Rober", "Roberto", LocalDate.now(), "robertofeo", 2);
		x.seguirUsuario(y);
		
		assertEquals(true, x.getSeguidos().contains(y));
	}
	
	@Test
	public void testDejarDeSeguir() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		y = new Usuario("Rober", "Roberto", LocalDate.now(), "robertofeo", 2);
		x.seguirUsuario(y); //Primero seguimos al usuario
		x.dejarDeSeguirUsuario(y); //Le dejamos de seguir y comprobamos la salida que queda
		assertEquals(false, x.getSeguidos().contains(y));
	}
	
	@Test
	public void testSumarReproduccion() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		int repro = x.sumarReproduccion(500);
		
		assertEquals(1, repro);
	}
	
	@Test
	public void testMejorarCuentaPorReproducciones() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.mejorarCuentaPorReproducciones();
		
		assertEquals(true, x.getPremium());
	}
	
	@Test
	public void testEmpeorarCuenta() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.mejorarCuentaPorReproducciones();
		x.emperorarCuenta();
		
		assertEquals(false, x.getPremium());
		
	}
	
	@Test
	public void testBloquearCuenta() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.bloquearCuentaIndefinido();
		assertEquals(true, x.getEstadoBloqueado());
	}
	
	@Test
	public void testDesbloquearCuenta() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.desbloquearCuenta();
		//assertEquals();
		
	}
	
	@Test
	public void testBloquearCuentaIndefinido() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.bloquearCuentaIndefinido();
		//assertEquals();
		
	}
	
	@Test
	public void testDesbloquearCuentaTemporal() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.bloquearCuentaTemporal();
		x.desbloquearCuenta();
		//assertEquals();
		
	}
	
	@Test
	public void TestMejorarCuentaPorPago() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x.mejorarCuentaPorPago();
		
		assertEquals(true, x.getPremium());
	}
	
	@Test
	public void TestAnyadirAAlbumPersonal() {
		ArrayList<Cancion> contenido = new ArrayList<Cancion>();
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		album = new Album(new Date(), "feo", z, contenido);
		
		assertEquals(true, x.anyadirAAlbumPersonal(album));
	}
	
	@Test
	public void TestAnyadirACancionPersonal() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		cancion = new Cancion(new Date(),"Zapatillas", 0 , z,  "chicle3mp3");
		
		assertEquals(true, x.anyadirACancionPersonal(cancion));
	}
	

	@Test
	public void TestAnyadirAListaPersonal() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		lista = new Lista(new Date(), "Best lista EUW", 2, z, new  ArrayList<Contenido>());
		x.anyadirAListaPersonal(lista);
		
		assertEquals(true, x.getListas().contains(lista));
	}
	
	@Test
	public void TestEliminarDeCancionesPersonales() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		cancion = new Cancion(new Date(),"Zapatillas", 0 , z,  "chicle3mp3");
		
		x.anyadirACancionPersonal(cancion);
		x.eliminarDeCancionesPersonales(cancion);
		assertEquals(false, x.getCanciones().contains(cancion));
	}
	
	
	@Test
	public void TestEliminarDeAlbumesPersonales() {
		ArrayList<Cancion> contenido = new ArrayList<Cancion>();
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now() , "pelayofeo", 1);
		album = new Album(new Date(), "feo", z, contenido);
		
		x.anyadirACancionPersonal(cancion);
		x.eliminarDeCancionesPersonales(cancion);
		assertEquals(false, x.getCanciones().contains(cancion));
	}
	
	public void TestEliminarDeListasPersonales() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		lista = new Lista(new Date(), "Best lista EUW", 2, z, new  ArrayList<Contenido>());
		x.anyadirAListaPersonal(lista);
		x.eliminarDeListasPersonales(lista);
		
		assertEquals(false, x.getListas().contains(lista));
		
	}
	
	
}