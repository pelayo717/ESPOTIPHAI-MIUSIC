package com.ESPOTIPHAI_MIUSIC.sistema.usuario;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;

//import org.junit.Assert;
import org.junit.BeforeClass;

class pruebaUsuario {
	
	Usuario x;
	Usuario y;
	Usuario z;
	Contenido c;
	
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
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		c = new Album(new Date(), "Saludos", 50, 5, x, x.getCanciones());
		
		assertEquals(true, x.anyadirAAlbumPersonal(c));
	}
	
	
	
	
	
}