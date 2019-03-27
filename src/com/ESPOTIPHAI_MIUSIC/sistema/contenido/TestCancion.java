package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.*;

import java.time.LocalDate;
import java.util.Date;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TestCancion {
	
	Cancion x;
	Usuario z;
	
	@Test
	public void TestCancionRechazada() {
		x = new Cancion(new Date(),"Zapatillas", 8 , z,  "chicle3.mp3");
		x.setEstado(EstadoCancion.PendienteModificacion);
		assertEquals("PendienteModificado", x.getEstado());
	}
	
	@Test
	public void TestAnyadirCola() {
		z = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x = new Cancion(new Date(),"Zapatillas", 8 , z,  "chicle3.mp3");
		
		
	}
	
	@Test
	public void TestValidarCancion() {
		x = new Cancion(new Date(),"Zapatillas", 8 , z,  "chicle3.mp3");
		assertEquals("PendienteModificacion", x.getEstado());
	}
}
