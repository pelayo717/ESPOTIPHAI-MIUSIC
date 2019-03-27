package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.*;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;

import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
//import org.junit.Assert;
import org.junit.BeforeClass;


class TestAlbum {
	Usuario z;
	Album album;
	Cancion x;
	ArrayList<Cancion> contenido;
	
	@BeforeClass
	public void before() {
		z = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x = new Cancion(new Date(),"Zapatillas", 0 , z,  "chicle3mp3");
		contenido = new ArrayList<Cancion>();
		album = new Album(new Date(), "feo", z, contenido);
		album = new Album(new Date(), "Sin faldas y a lo lokooo", z, new ArrayList<Cancion>());
		//Lista lista = new Lista(new Date(), "Best lista EUW", 2, z, new  ArrayList<Contenido>());
		
		
	}
	
	@Test
	public void TestAyadirContenido() {
		z = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		album = new Album(new Date(), "feo", z, contenido);
		x = new Cancion(new Date(),"Zapatillas", 0 , z,  "ladirecciondelmp3");
		album.anyadirContenido(x);
		assertEquals(true, album.getContenido().contains(x));
		
	}
	
	@Test
	public void TestEliminarContenido() {
		z = new Usuario("Pelayo", "Pelayo", LocalDate.now(), "pelayofeo", 1);
		x = new Cancion(new Date(), "Hola", 7, z, "nombremp3");
		album = new Album(new Date(), "Sin faldas y a lo lokooo", z, new ArrayList<Cancion>());
		album.anyadirContenido(x);
		album.eliminarContenido(x);
		assertEquals(false, album.getContenido().contains(x));
		
	}	
}
