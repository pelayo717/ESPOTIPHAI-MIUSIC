package com.ESPOTIPHAI_MUSIC.sistema.usuario;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
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
		
		//Como hago assert para comprobar que el se sigue bien al usuario
	}
	@Test
	public void testDejarDeSeguir() {
		
		x.seguirUsuario(y); //Primero seguimos al usuario
		x.dejarDeSeguirUsuario(y); //Le dejamos de seguir y comprobamos la salida que queda
		
	}
	@Test
	public void testSumarReproduccion() {
		int repro = x.sumarReproduccion(500);
		
		assertEquals(repro, 1);
	}
	
	@Test
	public void testMejorarCuentaPorReproducciones() {
		x.mejorarCuentaPorReproducciones();
		
		assertEquals(true, x.getPremium());
	}
	
	@Test
	public void testEmpeorarCuenta() {
		x.emperorarCuenta();
		
		assertEquals(true, x.getPremium());
		
	}
	
	@Test
	public void testBloquearCuenta() {
		x.bloquearcuenta();
		assertEquals(true, x.getBloqueado());
	}
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
