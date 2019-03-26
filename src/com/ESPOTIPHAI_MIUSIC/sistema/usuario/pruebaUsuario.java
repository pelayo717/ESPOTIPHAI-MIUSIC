package com.ESPOTIPHAI_MIUSIC.sistema.usuario;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
//import org.junit.Assert;
import org.junit.Before;

class pruebaUsuario {
	
	Usuario x;
	Usuario y;
	Usuario z;
	
	@Before
	public void before() {
		x = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		y = new Usuario("Rober", "Roberto", LocalDate.now(), "robertofeo", 2);
		z = new Usuario("Manu", "Manu", LocalDate.now(), "manufeo", 3);
	}

	@Test
	public void testSeguir() {
		x.seguirUsuario(y);
		
		assertEquals(true, x.getSeguidos().contains(y));
	}
	
	@Test
	public void testDejarDeSeguir() {
		
		x.seguirUsuario(y); //Primero seguimos al usuario
		x.dejarDeSeguirUsuario(y); //Le dejamos de seguir y comprobamos la salida que queda
		assertEquals(false, x.getSeguidos().contains(y));
	}
	
	@Test
	public void testSumarReproduccion() {
		int repro = x.sumarReproduccion(500);
		
		assertEquals(1, repro);
	}
	
	@Test
	public void testMejorarCuentaPorReproducciones() {
		x.mejorarCuentaPorReproducciones();
		
		assertEquals(true, x.getPremium());
	}
	
	@Test
	public void testEmpeorarCuenta() {
		x.emperorarCuenta();
		
		assertEquals(false, x.getPremium());
		
	}
	
	@Test
	public void testBloquearCuenta() {
		x.bloquearCuentaIndefinido();
		assertEquals(true, x.getEstadoBloqueado());
	}
	
	@Test
	public void testDesbloquearCuenta() {
		x.desbloquearCuenta();
		//assertEquals();
		
	}
	
	@Test
	public void testBloquearCuentaIndefinido() {
		x.bloquearCuentaIndefinido();
		//assertEquals();
		
	}
	
	@Test
	public void testDesbloquearCuentaTemporal() {
		x.bloquearCuentaTemporal();
		//assertEquals();
		
	}
}


