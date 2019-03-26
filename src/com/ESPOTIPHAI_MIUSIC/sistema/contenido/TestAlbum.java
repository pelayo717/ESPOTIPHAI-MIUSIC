package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.*;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;

import java.util.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
//import org.junit.Assert;
import org.junit.Before;


class TestAlbum {
	Usuario z;
	Album album;
	Cancion x;
	ArrayList<Cancion> contenido;
	
	@Before
	public void before() {
		x = new Cancion(new Date(), "Hola", 7, z, nombremp3);
		contenido = new ArrayList<Cancion>();
		album = new Album(new Date(), "feo", 50, 7, z, contenido);
	}
	
	@Test
	public void TestAyadirContenido() {
		
		album.anyadirContenido(x);
		
	}
	
	@Test
	public void TestEliminarContenido() {
		x = new Cancion(new Date(), "Hola", 7, z, nombremp3);
		album.anyadirContenido(x);
		album.eliminarContenido(x);
		
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
