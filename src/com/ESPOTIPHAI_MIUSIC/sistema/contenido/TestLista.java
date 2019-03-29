package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import static org.junit.jupiter.api.Assertions.*;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.*;

import java.time.LocalDate;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestLista {
		
		Cancion x;
		Usuario z;
		Contenido c;
		@BeforeClass
		public void BeforeClass() {
			
		}
		@Test
		public void TestAnyadirContenido() {
			x = new Cancion(new Date(),"Zapatillas", 8 , z,  "chicle3.mp3");
			c = x; 
			assertEquals(1, 5);
		}
		
		@Test
		public void TestEliminarContenido() {
			z = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
			x = new Cancion(new Date(),"Zapatillas", 8 , z,  "chicle3.mp3");
			
			
		}
}