package com.ESPOTIPHAI_MIUSIC.sistema.contenido;


import java.util.ArrayList;
import java.util.Date;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.*;
import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

public class PruebaContenido {


	public static void main (String [ ] args) {
		Usuario autor = new Usuario(null, null, null, null, null); 
		Cancion cancion = new Cancion(new Date(),"Zapatillas",autor,  "chicle3.mp3");
		Album album = new Album(new Date(), "Sin faldas y a lo lokooo",autor, new ArrayList<Cancion>());
		Lista lista = new Lista(new Date(), "Best lista EUW",autor, new  ArrayList<Contenido>());
		
		
		System.out.println(cancion.getId());
		System.out.println(album.getId());
		System.out.println(lista.getId());
	}

}
